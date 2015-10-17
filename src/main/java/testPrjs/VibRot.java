package testPrjs;

/***** Vibration-Rotator & Runge-Kutta-Integration *****
 ***** @author: Christoph Federrath                *****/

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class VibRot extends Applet
				implements Runnable, ActionListener, ItemListener
{
    private static final int Lx = 300, Ly = 300; //graphic-window
    private int px, py; //pixelcoordinates
    private double phi, omega; //coordinates and velocities
    private double r, rp; //coordinates and velocities
    private double D=100., m=1., r0=1.; //spring_strenght,mass,balance_distance
    private int reib; //friction
    private double phi0 = 0.; //initial value for phi
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k[] = new double[4]; //Runge-Kutta
    private double l[] = new double[4]; //Runge-Kutta
    private int xPoints_int[] = new int[11]; //pixelcoordinates for spring
    private int yPoints_int[] = new int[11]; //pixelcoordinates for spring
    private double startwert[] = new double[2]; //save initial values
    private int step;
    Canvas can;
    Button but_reib_plus, but_reib_min;
    Choice Chooser;
    Checkbox cb_stop;
	Scrollbar speed;
    Label descrip;
    Thread animator;
    private Graphics g, offGraphics;
	private Image offImage;

    public void init()
    {
        setLayout(null);
        can = new Canvas();
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setFont(new Font("Verdana",Font.PLAIN,10));
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
		cb_stop = new Checkbox("Stop/Go", false);
        Choice Chooser = new Choice();
        Chooser.add("Values_1");
        Chooser.add("Values_2");
        Chooser.add("Values_3");

        can.setBounds(1,1,Lx+100,Ly);
        can.setBackground(Color.white);
        pan.setBounds(Lx+101,1,100,Ly);
        pan.setBackground(new Color(0,200,100));

        Chooser.setBounds(10,20,80,30);
        but_reib_plus.setBounds(10,60,80,30);
        but_reib_min.setBounds(10,100,80,30);
        cb_stop.setBounds(10,145,80,30);
		cb_stop.setBackground(new Color(0,200,100));

		speed = new Scrollbar(Scrollbar.VERTICAL,40,20,0,80);
		speed.setSize(15,100);
		speed.setLocation(10,190);

		speed.addAdjustmentListener(new AdjustmentListener()
		{
			  public void adjustmentValueChanged(AdjustmentEvent e)
			  {
			  	  phi0 = phi; //save the current value for phi
			  	  startwert[1] = (60.-(double)e.getValue())/10.; //new omega
			  	  omega = startwert[1];
			  	  step = 0;
    		  	  pixels();
    		  	  update(g);
    		  }
		});

        descrip = new Label("Omega");
        descrip.setSize(50,30);
        descrip.setLocation(35,268);

		pan.add(speed); pan.add(descrip); pan.add(Chooser);
		pan.add(but_reib_plus); pan.add(but_reib_min);
        pan.add(cb_stop); add(can); add(pan);

        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
		Chooser.addItemListener(this);

        g = can.getGraphics();
        g.setFont(new Font("Verdana",Font.BOLD,10));

		startwert[0] = r0; //r
		startwert[1] = (60.-(double)speed.getValue())/10.; //omega
    }//init()

    public void startwerte()
    {
    	r = startwert[0];
    	omega = startwert[1];
		phi = 0.;
		phi0 = 0.;
    	rp = 0.;
    	step = 0;
    }//startwerte()

    public void run()
    {
        try{Thread.sleep(100);} catch(InterruptedException e){}
        startwerte();

        while(Thread.currentThread() == animator)
        {
			if(cb_stop.getState()) //Stop/Go
			{try{Thread.sleep(10);}catch(InterruptedException e){}continue;}

			phi = phi0 + omega*step*dt; //increase phi with an offset of phi0
	       	runge_step_r(); //Runge-Kutta-calculation
            r = r + (k[0]+2*k[1]+2*k[2]+k[3])/6; //new r
            rp = rp + (l[0]+2*l[1]+2*l[2]+l[3])/6; //new rp

        	if(step % 20 == 0) //paint frame not so often
        	{
	            try{Thread.sleep(12);} catch(InterruptedException e){}
                pixels(); //claculate pixelcoordinates
                repaint(); //paint new frame
        	}
			step++; //increase step
        }//while currentThread()==animator
    }//run()

    public void paint(Graphics g)  //double buffer
    {
        if(offImage != null)
        {
			g = can.getGraphics(); //initialize graphic window again
            g.drawImage(offImage, 0, 0, null);
        }
    } //paint(g)

    public void update(Graphics g)  //double buffer
    {
		g = can.getGraphics(); //initialize graphic window again
        if(offGraphics == null)
        {
            offImage = createImage(Lx+100,Ly);
            offGraphics = offImage.getGraphics();
        }
        offGraphics.setFont(new Font("Verdana",Font.BOLD,10));
        offGraphics.setColor(can.getBackground());
        offGraphics.fillRect(0, 0, Lx+100, Ly);
        paintFrame(offGraphics);
        g.drawImage(offImage, 0, 0, null);
    } //update(g)

    public void paintFrame(Graphics g)
    {
		print(g); //print values
        g.setColor(Color.black);
        g.drawLine(0,Ly/2,Lx,Ly/2);
        g.drawLine(Lx/2,0,Lx/2,Ly);
        g.drawString("Step " + step, 20, 30);
        g.setColor(Color.red);
        g.drawPolyline(xPoints_int,yPoints_int,11);
        g.setColor(Color.black);
        g.fillOval(px-7,py-7,15,15);
    } //paintFrame(g)

    public void print(Graphics g)
    {
		g.setColor(Color.blue);
		g.drawString("Friction:",Lx+20,Ly/2+25);
		g.drawString("" +reib,Lx+20,Ly/2+40);
		g.drawString("Omega:",Lx+20,Ly/2+70);
		g.drawString("" +(double)Math.round(100*omega)/100,Lx+20,Ly/2+85);
		g.drawString("Distance:",Lx+20,Ly/2+115);
		g.drawString("" +(double)Math.round(100*r)/100,Lx+20,Ly/2+130);
    }//print()

    public void pixels() //calculate pixelcoordinates
    {
        px = (int)Math.round((double)(Lx/2) + 80*r*Math.cos(phi));
        py = (int)Math.round((double)(Ly/2) - 80*r*Math.sin(phi));

		double d; //points for spring
        double xPoints[] = new double[11]; //x spring
        double yPoints[] = new double[11]; //y spring

		d = r/16;

		xPoints[0] = 0.;   yPoints[0] = 0.;
		xPoints[1] = d;    yPoints[1] = 0.03;
		xPoints[2] = 3*d;  yPoints[2] = -0.03;
		xPoints[3] = 5*d;  yPoints[3] = 0.03;
		xPoints[4] = 7*d;  yPoints[4] = -0.03;
		xPoints[5] = 9*d;  yPoints[5] = 0.03;
		xPoints[6] = 11*d; yPoints[6] = -0.03;
		xPoints[7] = 13*d; yPoints[7] = 0.03;
		xPoints[8] = 15*d; yPoints[8] = 0.;
		xPoints[9] = 16*d; yPoints[9] = 0.;

		double xnew, ynew;
		for(int i=0; i<10; i++)  //rotating coordinates
		{
			xnew = xPoints[i]*Math.cos(phi) + yPoints[i]*Math.sin(phi);
			ynew = - xPoints[i]*Math.sin(phi) + yPoints[i]*Math.cos(phi);
			xPoints[i] = xnew; yPoints[i] = ynew;
			xPoints_int[i] = (int)Math.round(80*xPoints[i]) + Lx/2;
			yPoints_int[i] = (int)Math.round(80*yPoints[i]) + Ly/2;
		}
		xPoints_int[10] = px; yPoints_int[10] = py;
    }//pixels()

    /* force in radial direction */
    public double force_r(double r, double rp)
    {
        return  D/m*(r0-r) + omega*omega*r - 0.1*reib*rp;
    }//force_r()

    public void runge_step_r()
    {
        k[0] = dt*rp;
        l[0] = dt*force_r(r, rp);
		k[1] = dt*(rp+l[0]/2);
        l[1] = dt*force_r(r+k[0]/2, rp+l[0]/2);
		k[2] = dt*(rp+l[1]/2);
        l[2] = dt*force_r(r+k[1]/2, rp+l[1]/2);
	    k[3] = dt*(rp+l[2]);
        l[3] = dt*force_r(r+k[2], rp+l[2]);
	}//runge_step_r()

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getActionCommand()==but_reib_plus.getActionCommand())
		{
			reib++;
			if(cb_stop.getState()) { update(g); }
		}
        if(evt.getActionCommand()==but_reib_min.getActionCommand())
        {
	        if(reib > 0)
            { reib--; if(cb_stop.getState()){ update(g); } }
        }
    }//actionPerformed(evt)

	public void itemStateChanged(ItemEvent evt) //Choice object
	{
        if(evt.getItem() == "Values_1")
        {
           	stop();
            startwert[0] = 1.; //r
			startwerte();
			pixels();
			update(g);
			start();
        }
        if(evt.getItem() == "Values_2")
        {
           	stop();
            startwert[0] = 0.7; //r
			startwerte();
			pixels();
			update(g);
			start();
        }
        if(evt.getItem() == "Values_3")
        {
           	stop();
            startwert[0] = 0.5; //r
			startwerte();
			pixels();
			update(g);
			start();
        }
	} //itemStateChanged(evt)

    public void start()
    {
	    animator = new Thread(this);
        animator.start();
    }//start()

    public void stop()
    {
		animator = null;
    	offImage = null;
		offGraphics = null;
    }//stop()
}

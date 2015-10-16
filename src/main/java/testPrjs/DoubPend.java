package testPrjs;



import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class DoubPend extends Applet implements Runnable, ActionListener
{
    private static final int Lx = 500, Ly = 330; //graphic window
    private int px1, px2, py1, py2; //pixelcoordinates
    private double phi1, omega1; //coordinates and velocities
    private double phi2, omega2; //coordinates and velocities
    private double m1, m2; //masses
    private int reib; //friction
    private static final double grav = 9.81; //Gravity
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[4]; //save initial values
    private int startwert_reib; //save initial friction
    private int step;
    Canvas can;
    Button but_new;
    Button but_phi1_plus, but_phi1_min;
    Button but_phi2_plus, but_phi2_min;
    Button but_reib_plus, but_reib_min;
    Button but_m1_plus, but_m1_min;
    Button but_m2_plus, but_m2_min;
    Checkbox cb_stop;
    Thread animator;
    Graphics g, offGraphics, ghead;
	Image offImage;

    public void init()
    {
        setLayout(null);

        Canvas header = new Canvas();
        header.setBounds(1,1,Lx,40);
        header.setBackground(Color.white);
        can = new Canvas();
        can.setBounds(1,41,Lx,Ly+50);
        can.setBackground(Color.white);
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setBounds(Lx+1,1,100,Ly+90);
        pan.setBackground(new Color(0,200,100));
        pan.setFont(new Font("Verdana",Font.PLAIN,10));

        but_new = new Button("New");
        but_phi1_plus = new Button("Phi1 +");
        but_phi1_min = new Button("Phi1 -");
        but_phi2_plus = new Button("Phi2 +");
        but_phi2_min = new Button("Phi2 -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        but_m1_plus = new Button("m1 +");
        but_m1_min = new Button("m1 -");
        but_m2_plus = new Button("m2 +");
        but_m2_min = new Button("m2 -");
		cb_stop = new Checkbox("Stop/Go", false);

        but_new.setBounds(10,12,80,27);
        but_phi1_plus.setBounds(10,50,80,27);
        but_phi1_min.setBounds(10,80,80,27);
        but_phi2_plus.setBounds(10,115,80,27);
        but_phi2_min.setBounds(10,145,80,27);
        but_reib_plus.setBounds(10,180,80,27);
        but_reib_min.setBounds(10,210,80,27);
        but_m1_plus.setBounds(10,245,80,27);
        but_m1_min.setBounds(10,275,80,27);
        but_m2_plus.setBounds(10,310,80,27);
        but_m2_min.setBounds(10,340,80,27);
        cb_stop.setBounds(10,380,70,30);
		cb_stop.setBackground(new Color(0,200,100));

        but_new.addActionListener(this);
        but_phi1_plus.addActionListener(this);
        but_phi1_min.addActionListener(this);
        but_phi2_plus.addActionListener(this);
        but_phi2_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
        but_m1_plus.addActionListener(this);
        but_m1_min.addActionListener(this);
        but_m2_plus.addActionListener(this);
        but_m2_min.addActionListener(this);

		pan.add(but_new); pan.add(cb_stop);
        pan.add(but_phi1_plus); pan.add(but_phi1_min);
		pan.add(but_phi2_plus); pan.add(but_phi2_min);
		pan.add(but_reib_plus); pan.add(but_reib_min);
		pan.add(but_m1_plus); pan.add(but_m1_min);
		pan.add(but_m2_plus); pan.add(but_m2_min);
        add(pan); add(can); add(header);

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,15));
        g = can.getGraphics();

		startwert[0] = Math.PI/180*170.; //phi1
        startwert[1] = Math.PI/180*130.; //phi2
		startwert[2] = 1.0; //m1
    	startwert[3] = 1.0; //m2
    	startwert_reib = 0; //reib
    }//init()

    public void startwerte() //initial values
    {
		phi1 = startwert[0];
        phi2 = startwert[1];
    	m1 = startwert[2];
    	m2 = startwert[3];
		reib = startwert_reib;
		omega1 = 0.0;
		omega2 = 0.0;
    	step = 0;
    }//startwerte()

    public void run()
    {
        try{Thread.sleep(500);} catch(InterruptedException e){}
		ghead.drawString("Double Pendulum",180,25);
        offImage = createImage(Lx,Ly+50);
        offGraphics = offImage.getGraphics();
        startwerte();

        while(Thread.currentThread() == animator)
        {
			if(cb_stop.getState()) //Stop/Go
			{try{Thread.sleep(10);}catch(InterruptedException e){}continue;}

	       	runge_step_phi1(); //Runge-Kutta
        	runge_step_phi2(); //Runge-Kutta
            phi1 = phi1 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //Runge-Kutta
            omega1 = omega1 + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //Runge-Kutta
            phi2 = phi2 + (k2[0]+2*k2[1]+2*k2[2]+k2[3])/6; //Runge-Kutta
            omega2 = omega2 + (l2[0]+2*l2[1]+2*l2[2]+l2[3])/6; //Runge-Kutta

        	if(step % 25 == 0) //paint not so often
        	{
				try{Thread.sleep(8);} catch(InterruptedException e){}
                pixels(); //calculate pixelcoordinates
                paintFrame(offGraphics); //paint new frame into Image
		        g.drawImage(offImage, 0, 0, null); //display Image on screen
        	}
			step++;
        }//while currentThread()==animator
    }//run()

    public void paintFrame(Graphics graph)
    {
        graph.setFont(new Font("Verdana",Font.BOLD,10));
        graph.setColor(can.getBackground());
        graph.fillRect(0, 0, Lx, Ly+50);
		print(graph); //print values
        graph.setColor(Color.black);
        graph.drawLine(0,Ly/2,Lx,Ly/2);
        graph.drawLine(Lx/2,0,Lx/2,Ly);
        graph.drawString("Step " + step, 20, 10);
        graph.setColor(Color.red);
        graph.drawLine(Lx/2,Ly/2,px1,py1);
        graph.drawLine(px1,py1,px2,py2);
        graph.setColor(Color.black);
        graph.fillOval((int)(px1-7*Math.pow(m1,1./3.)),(int)(py1-7*Math.pow(m1,1./3.)),
        	    	   (int)(15*Math.pow(m1,1./3.)),(int)(15*Math.pow(m1,1./3.)));
        graph.fillOval((int)(px2-7*Math.pow(m2,1./3.)),(int)(py2-7*Math.pow(m2,1./3.)),
        			   (int)(15*Math.pow(m2,1./3.)),(int)(15*Math.pow(m2,1./3.)));
    } //paint()

    public void print(Graphics graph)
    {
		graph.setColor(Color.blue);
		graph.drawString("Energy:",30,Ly+20);
		graph.drawString("" +(double)Math.round(10*energy())/10,30,Ly+40);
		graph.drawString("Friction:",120,Ly+20);
		graph.drawString("" +reib,120,Ly+40);
		graph.drawString("m1:",210,Ly+20);
		graph.drawString("" +(double)Math.round(100*m1)/100,210,Ly+40);
		graph.drawString("m2:",270,Ly+20);
		graph.drawString("" +(double)Math.round(100*m2)/100,270,Ly+40);
		graph.drawString("Phi1:",350,Ly+20);
		graph.drawString("" +(double)Math.round(10*phi1*180/Math.PI)/10 +"#",350,Ly+40);
		graph.drawString("Phi2:",420,Ly+20);
		graph.drawString("" +(double)Math.round(10*phi2*180/Math.PI)/10 +"#",420,Ly+40);
    }//print()

    public void pixels() //method for calculating pixelcoordinates
    {
        px1 = (int)Math.round((double)(Lx/2) + 80*Math.sin(phi1));
        py1 = (int)Math.round((double)(Ly/2) + 80*Math.cos(phi1));
        px2 = (int)Math.round((double)px1 + 80*Math.sin(phi2));
        py2 = (int)Math.round((double)py1 + 80*Math.cos(phi2));
    }//pixels()

    /* force on Phi_1 */
    public double forcephi1(double phi1,double phi2,double omega1,double omega2)
    {
        return -( grav*(   2*m1*Math.sin(phi1)
        		  	     + m2*Math.sin(phi1)
        		  	     + m2*Math.sin(phi1-2*phi2)
        		 	   )
        		  + m2*omega1*omega1*Math.sin(2*phi1-2*phi2)
        		  + 2*m2*omega2*omega2*Math.sin(phi1-phi2)
			    ) / ( 2*m1 + m2 - m2*Math.cos(2*phi1-2*phi2) )
        	    -0.1*reib*omega1;
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(double phi1,double phi2,double omega1,double omega2)
    {
        return (   2*Math.sin(phi1-phi2)
        			  *(   grav*(m1+m2)*Math.cos(phi1)
        			     + (m1+m2)*omega1*omega1
			    		 + m2*omega2*omega2*Math.cos(phi1-phi2)
			    	   )
        	   ) / ( 2*m1 + m2 - m2*Math.cos(2*phi1-2*phi2) )
        	   -0.1*reib*omega2;
    }//forcephi2()

    public double energy()
    {
    	return 0.5*m1*omega1*omega1 + 0.5*m2*omega2*omega2 + 0.5*m2*omega1*omega1
    		   + m2*omega1*omega2*Math.cos(phi1-phi2)
    		   - grav*(m1*Math.cos(phi1) + m2*Math.cos(phi1) + m2*Math.cos(phi2));
    }//energy()

    public void runge_step_phi1()
    {
        k1[0] = dt*omega1;
        l1[0] = dt*forcephi1(phi1,phi2,omega1,omega2);
        k1[1] = dt*(omega1+l1[0]/2);
        l1[1] = dt*forcephi1(phi1+k1[0]/2,phi2,omega1+l1[0]/2,omega2);
        k1[2] = dt*(omega1+l1[1]/2);
        l1[2] = dt*forcephi1(phi1+k1[1]/2,phi2,omega1+l1[1]/2,omega2);
        k1[3] = dt*(omega1+l1[2]);
        l1[3] = dt*forcephi1(phi1+k1[2],phi2,omega1+l1[2],omega2);
    }//runge_step_phi1()

    public void runge_step_phi2()
    {
        k2[0] = dt*omega2;
        l2[0] = dt*forcephi2(phi1,phi2,omega1,omega2);
        k2[1] = dt*(omega2+l2[0]/2);
        l2[1] = dt*forcephi2(phi1,phi2+k2[0]/2,omega1,omega2+l2[0]/2);
        k2[2] = dt*(omega2+l2[1]/2);
        l2[2] = dt*forcephi2(phi1,phi2+k2[1]/2,omega1,omega2+l2[1]/2);
        k2[3] = dt*(omega2+l2[2]);
        l2[3] = dt*forcephi2(phi1,phi2+k2[2],omega1,omega2+l2[2]);
    }//runge_step_phi2()

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getActionCommand()==but_new.getActionCommand())
        {
        	stop();
			startwerte();
            pixels();
			paintFrame(g);
			start();
        }
        if(evt.getActionCommand()==but_phi1_plus.getActionCommand())
        {
			stop();
			startwert[0] = startwert[0] + Math.PI/180*5;
			startwerte();
            pixels();
			paintFrame(g);
			start();
        }
        if(evt.getActionCommand()==but_phi1_min.getActionCommand())
        {
			stop();
			startwert[0] = startwert[0] - Math.PI/180*5;
			startwerte();
            pixels();
			paintFrame(g);
			start();
        }
        if(evt.getActionCommand()==but_phi2_plus.getActionCommand())
        {
        	stop();
        	startwert[1] = startwert[1] + Math.PI/180*5;
			startwerte();
            pixels();
			paintFrame(g);
			start();
		}
        if(evt.getActionCommand()==but_phi2_min.getActionCommand())
        {
	        stop();
	        startwert[1] = startwert[1] - Math.PI/180*5;
			startwerte();
            pixels();
			paintFrame(g);
			start();
        }
        if(evt.getActionCommand()==but_reib_plus.getActionCommand())
        {
	        startwert_reib++; reib = startwert_reib;
			if(cb_stop.getState()) { paintFrame(g); }
        }
        if(evt.getActionCommand()==but_reib_min.getActionCommand())
        {
            if(reib > 0)
            { startwert_reib--; reib = startwert_reib;
              if(cb_stop.getState()) { paintFrame(g); }
			}
        }
        if(evt.getActionCommand()==but_m1_plus.getActionCommand())
		{
        	startwert[2] = startwert[2]/0.5; m1 = startwert[2];
			if(cb_stop.getState()) { paintFrame(g); }
        }
        if(evt.getActionCommand()==but_m1_min.getActionCommand())
        {
	       	if(m1/m2 < 0.1) { }
            else { startwert[2] = startwert[2]*0.5; m1 = startwert[2];
            	   if(cb_stop.getState()) { paintFrame(g); }
				 }
        }
        if(evt.getActionCommand()==but_m2_plus.getActionCommand())
		{
        	if(m1/m2 < 0.1) { }
            else { startwert[3] = startwert[3]/0.5; m2 = startwert[3];
            	   if(cb_stop.getState()) { paintFrame(g); }
				 }
		}
        if(evt.getActionCommand()==but_m2_min.getActionCommand())
		{
			startwert[3] = startwert[3]*0.5; m2 = startwert[3];
			if(cb_stop.getState()) { paintFrame(g); }
        }
    }//actionPerformed(evt)

    public void start()
    {
	    animator = new Thread(this);
        animator.start();
    }//start()

    public void stop()
    {
		animator = null;
    	offGraphics = null;
    	offImage = null;
    }//stop()
}
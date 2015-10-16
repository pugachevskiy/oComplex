package testPrjs;

/***** 2 harmonic oscillators with coupling & Runge-Kutta-Integration *****
 ***** @author: Christoph Federrath                                   *****/

import java.awt.*;
import java.util.*;
import java.applet.*;
import java.awt.event.*;

public class OsciCoupling extends Applet
		implements Runnable, ActionListener, ItemListener
{
    private static final int W = 400, H = 200; //graphic window
    private int px1, px2; //pixelcoordinates
    private double x1, x1p, x2, x2p; //coordinates and velocities
    private double m = 1.; //mass
    private double D1 = 1., D2 = 1./16; //spring strengths
    private static final double l = 1./3; //spring lengths of balance
    private int reib; //friction
    private static final double dt = 0.005; //timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double k3[] = new double[4]; //Runge-Kutta
    private double k4[] = new double[4]; //Runge-Kutta
    private double l3[] = new double[4]; //Runge-Kutta
    private double l4[] = new double[4]; //Runge-Kutta
    private int xPoints1_int[] = new int[12]; //pixelcoordinates spring1
    private int yPoints1_int[] = new int[12]; //pixelcoordinates spring1
    private int xPoints2_int[] = new int[12]; //pixelcoordinates spring2
    private int yPoints2_int[] = new int[12]; //pixelcoordinates spring2
    private int xPoints3_int[] = new int[12]; //pixelcoordinates spring middle
    private int yPoints3_int[] = new int[12]; //pixelcoordinates spring middle
    private double startwert[] = new double[3]; //save initial values
    private int startwert_reib; //save initial friction value
    private int step;
    private boolean Choice[] = new boolean[3]; //check for selected item
    Canvas can, header;
    Button but_new;
    Button but_D_plus, but_D_min;
    Button but_reib_plus, but_reib_min;
    Checkbox cb_stop;
    Choice Chooser;
    Thread animator;
    Graphics g, offGraphics, ghead;
	Image offImage;

    public void init()
    {
        setLayout(null);
        header = new Canvas();
        header.setBounds(1,1,W,40);
        header.setBackground(Color.white);
        can = new Canvas();
        can.setBounds(1,41,W,H+50);
        can.setBackground(Color.white);
		can.addMouseListener(new ML());
		can.addMouseMotionListener(new ML());
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setBounds(W+1,1,100,H+90);
        pan.setBackground(new Color(0,200,100));
        pan.setFont(new Font("Verdana",Font.PLAIN,10));

        but_new = new Button("New");
        but_D_plus = new Button("Coupling +");
        but_D_min = new Button("Coupling -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
		cb_stop = new Checkbox("Stop/Go", false);
        Choice Chooser = new Choice();
        Chooser.add("Free");
        Chooser.add("Together");
        Chooser.add("Against");

        but_new.setBounds(10,15,80,27);
        but_D_plus.setBounds(10,55,80,27);
        but_D_min.setBounds(10,90,80,27);
        but_reib_plus.setBounds(10,130,80,27);
        but_reib_min.setBounds(10,165,80,27);
        Chooser.setBounds(10,215,80,27);
        cb_stop.setBounds(10,250,70,30);
		cb_stop.setBackground(new Color(0,200,100));

        pan.add(but_new); pan.add(cb_stop); pan.add(Chooser);
        pan.add(but_D_plus); pan.add(but_D_min);
        pan.add(but_reib_plus); pan.add(but_reib_min);
		add(header); add(pan); add(can);

        but_new.addActionListener(this);
        but_D_plus.addActionListener(this);
        but_D_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
		Chooser.addItemListener(this);

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,15));
        g = can.getGraphics();
		g.setFont(new Font("Verdana",Font.BOLD,10));

		Choice[0] = true; //Free
		startwert[0] = 0.15; //x1
		startwert[1] = 2./3; //x2
    	startwert[2] = D2; //D2
    	startwert_reib = 0; //reib
    }//init()

    public void startwerte()  //method for setting initial values
    {
		x1 = startwert[0];
		x2 = startwert[1];
		x1p = 0.;
		x2p = 0.;
		D2 = startwert[2];
		reib = startwert_reib;
    	step = 0;
    }//startwerte()

    public void run()
    {
        try{Thread.sleep(100);}catch(InterruptedException e){} //sleep
	    ghead.drawString("Drag and drop left mass ...",W/2-100,28);
        startwerte();

        while(Thread.currentThread() == animator)
        {
			if(cb_stop.getState()) //stop/go
			{try{Thread.sleep(10);}catch(InterruptedException e){}continue;}

			runge_step_x1(); //Runge-Kutta-calculation
			runge_step_x2(); //Runge-Kutta-calculation
            x1 = x1 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //new x1
            x1p = x1p + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //new x1p
            x2 = x2 + (k3[0]+2*k3[1]+2*k3[2]+k3[3])/6; //new x2
            x2p = x2p + (l3[0]+2*l3[1]+2*l3[2]+l3[3])/6; //new x2p

        	if(step % 4 == 0) //paint frame not so often
        	{
	            try{Thread.sleep(5);}catch(InterruptedException e){} //wait
                pixels(); //calculate pixelcoordinates
                repaint(); //paint new frame
        	}
			step++; //next step
        }//while currentThread()==animator
    }//run()

    public void paint(Graphics gr)  //double buffer
    {
        if(offImage != null)
        {
			gr = can.getGraphics(); //initialize graphic window
            gr.drawImage(offImage, 0, 0, null);
        }
    } //paint(gr)

    public void update(Graphics gr)  //double buffer
    {
		gr = can.getGraphics(); //initialize graphic window
        if(offGraphics == null)
        {
            offImage = createImage(W,H+50);
            offGraphics = offImage.getGraphics();
        }
		offGraphics.setFont(new Font("Verdana",Font.BOLD,10));
        offGraphics.setColor(can.getBackground());
        offGraphics.fillRect(0, 0, W, H+50);
        paintFrame(offGraphics);
        gr.drawImage(offImage, 0, 0, null);
    } //update(gr)

    public void paintFrame(Graphics gr)
    {
		print(gr); //print values
        gr.drawLine(0,H/2,W,H/2);
        gr.drawLine(W/2,0,W/2,H);
        gr.setColor(Color.black);
        gr.drawPolyline(xPoints1_int,yPoints1_int,12); //spring_left
        gr.drawPolyline(xPoints2_int,yPoints2_int,12); //spring_right
        gr.setColor(new Color(100,50,10));
        gr.drawPolyline(xPoints3_int,yPoints3_int,12); //spring_middle
        gr.setColor(Color.black);
        gr.fillOval(px1-9,H/2-9,19,19);
        gr.fillOval(px2-9,H/2-9,19,19);
    } //paintFrame(gr)

    public void print(Graphics gr) //method for printing values
    {
		gr.setColor(Color.blue);
		gr.drawString("Energy:",W/2-140,H+20);
		gr.drawString("" +(double)(Math.round(100*energy()))/100,W/2-140,H+40);
	    gr.drawString("Coupling:",W/2-30,H+20);
	    gr.drawString("" + D2,W/2-30,H+40);
	    gr.drawString("Friction:",W/2+80,H+20);
	    gr.drawString("" + reib,W/2+80,H+40);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoordinates
    {
        px1 = (int)Math.round(W*x1); //mass left
        px2 = (int)Math.round(W*x2); //mass right

		double currentLength1, currentLength2, currentLength3; //spring lengths
		double d1, d2, d3; //for pixels of springs
        double xPoints1[] = new double[12]; //x pix spring1
        double yPoints1[] = new double[12]; //y pix spring1
        double xPoints2[] = new double[12]; //x pix spring2
        double yPoints2[] = new double[12]; //y pix spring2
        double xPoints3[] = new double[12]; //x pix spring_middle
        double yPoints3[] = new double[12]; //y pix spring_middle

		currentLength1 = x1;
		currentLength2 = 1.-x2;
		currentLength3 = x2-x1;
		d1 = currentLength1/18.; //devide in 18 pieces
		d2 = currentLength2/18.; //devide
		d3 = currentLength3/18.; //devide

        xPoints1[0] = 0.;	yPoints1[0] = 0.; //spring pix
        xPoints1[1] = d1;	yPoints1[1] = 0.;
		xPoints2[0] = 0.;	yPoints2[0] = 0.;
		xPoints2[1] = -d2;  yPoints2[1] = 0.;
		xPoints3[0] = 0.;	yPoints3[0] = 0.;
        xPoints3[1] = d3;	yPoints3[1] = 0.;
		for(int i=2; i<10; i++) //spring pix
		{
            xPoints1[i] = (2*i-2)*d1;
            xPoints2[i] = -(2*i-2)*d2;
            xPoints3[i] = (2*i-2)*d3;
            if(i%2 == 0)
            {
            	yPoints1[i] = 0.02;
            	yPoints2[i] = -0.02;
            	yPoints3[i] = 0.02;
            }
            else
            {
            	yPoints1[i] = -0.02;
            	yPoints2[i] = 0.02;
            	yPoints3[i] = -0.02;
            }
		}
        xPoints1[10] = 17*d1;  yPoints1[10] = 0.; //spring pix
        xPoints1[11] = 18*d1;  yPoints1[11] = 0.;
        xPoints2[10] = -17*d2; yPoints2[10] = 0.;
        xPoints2[11] = -18*d2; yPoints2[11] = 0.;
        xPoints3[10] = 17*d3;  yPoints3[10] = 0.;
        xPoints3[11] = 18*d3;  yPoints3[11] = 0.;

		for(int i=0; i<12; i++) //set int coordinates
		{
			xPoints1_int[i] = (int)Math.round(W*xPoints1[i]);
			yPoints1_int[i] = (int)Math.round(H*yPoints1[i]) + H/2;
			xPoints2_int[i] = (int)Math.round(W*xPoints2[i]) + W;
			yPoints2_int[i] = (int)Math.round(H*yPoints2[i]) + H/2;
			xPoints3_int[i] = (int)Math.round(W*xPoints3[i]) + px1;
			yPoints3_int[i] = (int)Math.round(H*yPoints3[i]) + H/2;
		}
    }//pixels()

    /* force on x1 */
    public double forcex1(double x1)
    {
        return -((D1*(x1*x1 - l*Math.sqrt(x1*x1))*(x1 - x2)
        		+ D2*x1*(x1*x1 - l*Math.sqrt((x1 - x2)*(x1 - x2))
        		- 2*x1*x2 + x2*x2))/(m*x1*(x1 - x2)))
        		- reib*0.1*x1p; //friction
    }//forcex1()

    /* force on x2 */
    public double forcex2(double x2)
    {
        return (-(D1*(x1 - x2)*(1 - l*Math.sqrt((-1 + x2)*(-1 + x2))
        		- 2*x2 + x2*x2)) + D2*(-1 + x2)*(x1*x1
        		- l*Math.sqrt((x1 - x2)*(x1 - x2)) - 2*x1*x2
        		+ x2*x2))/(m*(x1 - x2)*(-1 + x2))
        		- reib*0.1*x2p; //friction
    }//forcex2()

    public double energy()
    {
        return D1/2 + D1*l*l + (D2*l*l)/2 + (D1*x1*x1)/2 + (D2*x1*x1)/2
        		- D1*l*Math.sqrt(x1*x1) + (m*x1p*x1p)/2
        		- D1*l*Math.sqrt((1 - x2)*(1 - x2)) - D1*x2 - D2*x1*x2
        		+ (D1*x2*x2)/2 + (D2*x2*x2)/2
        		- D2*l*Math.sqrt((-x1 + x2)*(-x1 + x2)) + (m*x2p*x2p)/2;
    }//energy()

    public void runge_step_x1()
    {
        k1[0] = dt*x1p;
        l1[0] = dt*forcex1(x1);
        k1[1] = dt*(x1p+l1[0]/2);
        l1[1] = dt*forcex1(x1+k1[0]/2);
        k1[2] = dt*(x1p+l1[1]/2);
        l1[2] = dt*forcex1(x1+k1[1]/2);
        k1[3] = dt*(x1p+l1[2]);
        l1[3] = dt*forcex1(x1+k1[2]);
    }//runge_step_x1()

    public void runge_step_x2()
    {
        k3[0] = dt*x2p;
        l3[0] = dt*forcex2(x2);
        k3[1] = dt*(x2p+l3[0]/2);
        l3[1] = dt*forcex2(x2+k3[0]/2);
        k3[2] = dt*(x2p+l3[1]/2);
        l3[2] = dt*forcex2(x2+k3[1]/2);
        k3[3] = dt*(x2p+l3[2]);
        l3[3] = dt*forcex2(x2+k3[2]);
    }//runge_step_x2()

    public void actionPerformed (ActionEvent evt) //buttons
    {
        if(evt.getActionCommand() == but_new.getActionCommand())
        {
        	stop();
			startwerte(); //set initial values
            pixels(); //calculate pixelcoords
			update(g); //paint initial state
			start();
        }

        if(evt.getActionCommand() == but_D_plus.getActionCommand())
        {
			if(startwert[2] < 4)
           	{ startwert[2] = 2*startwert[2]; D2 = startwert[2];
	          if(cb_stop.getState()) { update(g); }
        	}
        }
        if(evt.getActionCommand() == but_D_min.getActionCommand())
        {
           	startwert[2] = startwert[2]/2; D2 = startwert[2];
	        if(cb_stop.getState()) { update(g); }
        }
        if(evt.getActionCommand() == but_reib_plus.getActionCommand())
        {
            startwert_reib++;
            reib = startwert_reib;
	        if(cb_stop.getState()) { update(g); }
        }
        if(evt.getActionCommand() == but_reib_min.getActionCommand())
        {
	        if(reib > 0)
            { startwert_reib--; reib = startwert_reib;
              if(cb_stop.getState()) { update(g); }
			}
		}
    }//actionPerformed(evt)

	public void itemStateChanged(ItemEvent evt) //Choice object
	{
        if(evt.getItem() == "Free")
        {
			stop();
            Choice[0] = true; //Free
            Choice[1] = false; //Together
            Choice[2] = false; //Against
            startwert[0] = 0.15; //x1
            startwert[1] = 2./3; //x2
			startwerte();
			pixels();
			update(g);
			start();
        }
        if(evt.getItem() == "Together")
        {
			stop();
            Choice[0] = false;
            Choice[1] = true;
            Choice[2] = false;
            startwert[0] = 1./3-0.2; //x1
            startwert[1] = 2./3-0.2; //x2
			startwerte();
			pixels();
			update(g);
			start();
        }
        if(evt.getItem() == "Against")
        {
			stop();
            Choice[0] = false;
            Choice[1] = false;
            Choice[2] = true;
            startwert[0] = 1./3-0.1; //x1
            startwert[1] = 2./3+0.1; //x2
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

class ML extends MouseAdapter implements MouseMotionListener  //overwrite mouse
{
	public void Choose (MouseEvent evt) //to set mouse properties
	{
		if(Choice[0]) //Free
		{
            if(evt.getX() < 50)
            {
                startwert[0] = 50./W;
            }
            else if(evt.getX() > W/2+10)
            {
                startwert[0] = (double)(W/2+10)/W;
            }
            else
            {
                startwert[0] = (double)(evt.getX())/W;
            }
            startwert[1] = 2./3;
		} //Free
		if(Choice[1]) //Together
		{
            if(evt.getX() < 50)
            {
                startwert[0] = 50./W;
                startwert[1] = 50./W + 1./3;
            }
            else if(evt.getX() > W-50-W/3)
            {
                startwert[0] = (double)(W-50-W/3)/W;
                startwert[1] = (double)(W-50-W/3)/W + 1./3;
            }
            else
            {
                startwert[0] = (double)(evt.getX())/W;
                startwert[1] = (double)(evt.getX())/W + 1./3;
            }
		} //Together
		if(Choice[2]) //Against
		{
            if(evt.getX() < 100)
            {
                startwert[0] = 100./W;
                startwert[1] = -100./W + 1.;
            }
            else if(evt.getX() > W/2-40)
            {
                startwert[0] = (double)(W/2-40)/W;
                startwert[1] = -(double)(W/2-40)/W + 1.;
            }
            else
            {
                startwert[0] = (double)(evt.getX())/W;
                startwert[1] = -(double)(evt.getX())/W + 1.;
            }
		} //Against
	} //Choose()

	public void mousePressed (MouseEvent evt)
	{
		stop();
		Choose(evt);
        startwerte();
        pixels(); //pixelcoordinates
		update(g); //paint
	}//mousePressed

	public void mouseDragged (MouseEvent evt)
	{
		Choose(evt);
        startwerte();
        pixels(); //pixelcoordinates
		update(g); //paint
	}//mouseDragged

	public void mouseReleased (MouseEvent evt)
	{
		start();
	}//mouseReleased

	public void mouseMoved (MouseEvent evt)
	{  }//mouseMoved (simply implemented for Interface MouseMotionListener)

}//ML
}

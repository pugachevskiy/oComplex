package testPrjs;

/***** 2 anharmonic oscillators with coupling & Runge-Kutta-Integration *****
 ***** @author: Christoph Federrath                                     *****/

import java.awt.*;
import java.util.*;
import java.applet.*;
import java.awt.event.*;

public class Osci2Anharm extends Applet
		implements Runnable, ActionListener, ItemListener
{
    private static final int W = 400, H = 240; //graphic window
    private int px1, py1, px2, py2; //pixelcoordinates
    private double x1, x1p, y1, y1p; //coordinates and velocities
    private double x2, x2p, y2, y2p; //coordinates and velocities
    private double m1 = 1.; //mass1
    private double m2 = 1.; //mass2
    private double D = 1.; //spring
    private static final double l = 0.32; //spring balance length
    private int reib; //friction
    private static final double dt = 0.003; //timestep
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
    private int xPoints3_int[] = new int[12]; //pixelcoordinates spring_middle
    private int yPoints3_int[] = new int[12]; //pixelcoordinates spring_middle
    private double startwert[] = new double[9]; //save initial values
    private int startwert_reib; //save initial friction
    private int grav = 0; //gravity 0=off 1=on
    private int step;
    private boolean Choice[] = new boolean[3]; //check for selected item
    Canvas can, header;
    Button but_new;
    Button but_D_plus, but_D_min;
    Button but_reib_plus, but_reib_min;
    Checkbox cb_stop, cb_grav;
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
        but_D_plus = new Button("Spring +");
        but_D_min = new Button("Spring -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
		cb_grav = new Checkbox("Gravity", false);
		cb_stop = new Checkbox("Stop/Go", false);
        Choice Chooser = new Choice();
        Chooser.add("Free");
        Chooser.add("Together");
        Chooser.add("Against");

        but_new.setBounds(10,20,80,27);
        but_D_plus.setBounds(10,60,80,27);
        but_D_min.setBounds(10,95,80,27);
        but_reib_plus.setBounds(10,135,80,27);
        but_reib_min.setBounds(10,170,80,27);
        Chooser.setBounds(10,220,80,30);
        cb_grav.setBounds(10,255,70,30);
		cb_grav.setBackground(new Color(0,200,100));
        cb_stop.setBounds(10,290,70,30);
		cb_stop.setBackground(new Color(0,200,100));

        pan.add(but_new); pan.add(but_D_plus); pan.add(but_D_min);
        pan.add(but_reib_plus); pan.add(but_reib_min);
		pan.add(cb_grav); pan.add(cb_stop); pan.add(Chooser);
		add(pan); add(can); add(header);

        but_new.addActionListener(this);
        but_D_plus.addActionListener(this);
        but_D_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
		Chooser.addItemListener(this);

        g = can.getGraphics();
		g.setFont(new Font("Verdana",Font.BOLD,10));

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,14));

		Choice[0] = true; //Free
		startwert[0] = 0.2; //x1
		startwert[1] = 0.0; //x1p
		startwert[2] = 0.2; //y1
    	startwert[3] = 0.0; //y1p
		startwert[4] = 0.7; //x2
        startwert[5] = 0.0; //x2p
		startwert[6] = 0.0; //y2
    	startwert[7] = 0.0; //y2p
    	startwert[8] = D; //D
    	startwert_reib = 0; //reib
    }//init()

    public void startwerte()  //method for setting initial values
    {
		x1 = startwert[0]; x1p = startwert[1];
    	y1 = startwert[2]; y1p = startwert[3];
		x2 = startwert[4]; x2p = startwert[5];
		y2 = startwert[6]; y2p = startwert[7];
		D = startwert[8];
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
			if(cb_grav.getState()) //gravity
			{ grav = 1; } else { grav = 0; }

			runge_step_x1(); //Runge-Kutta
            runge_step_y1(); //Runge-Kutta
			runge_step_x2(); //Runge-Kutta
            runge_step_y2(); //Runge-Kutta
            x1 = x1 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //new values
            x1p = x1p + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6;
            y1 = y1 + (k2[0]+2*k2[1]+2*k2[2]+k2[3])/6;
            y1p = y1p + (l2[0]+2*l2[1]+2*l2[2]+l2[3])/6;
            x2 = x2 + (k3[0]+2*k3[1]+2*k3[2]+k3[3])/6;
            x2p = x2p + (l3[0]+2*l3[1]+2*l3[2]+l3[3])/6;
            y2 = y2 + (k4[0]+2*k4[1]+2*k4[2]+k4[3])/6;
            y2p = y2p + (l4[0]+2*l4[1]+2*l4[2]+l4[3])/6;

        	if(step % 10 == 0) //paint frame not so often
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
		gr = can.getGraphics();
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
        gr.drawPolyline(xPoints3_int,yPoints3_int,12); //spring_middle
        gr.fillOval(px1-12,py1-12,24,24);
        gr.fillOval(px2-12,py2-12,24,24);
    } //paintFrame(gr)

    public void print(Graphics gr) //method for printing values
    {
		gr.setColor(Color.blue);
		gr.drawString("Energy:",W/2-150,H+20);
		gr.drawString("" +(double)(Math.round(100*energy()))/100,W/2-150,H+40);
	    gr.drawString("Spring force:",W/2-40,H+20);
	    gr.drawString("" + D,W/2-40,H+40);
	    gr.drawString("Friction:",W/2+90,H+20);
	    gr.drawString("" + reib,W/2+90,H+40);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoordinates
    {
        px1 = (int)Math.round(W*x1); //x-value left mass
        py1 = H/2 + (int)Math.round(H*y1); //y-value left mass
        px2 = (int)Math.round(W*x2); //x-value right mass
        py2 = H/2 + (int)Math.round(H*y2); //y-value right mass

		double currentLength1, currentLength2, currentLength3; //springlengths
		double d1, d2, d3; //distance between spring pixels
		double alpha1, alpha2, alpha3; //angle to rotate
        double xPoints1[] = new double[12]; //x pix spring1
        double yPoints1[] = new double[12]; //y pix spring1
        double xPoints2[] = new double[12]; //x pix spring2
        double yPoints2[] = new double[12]; //y pix spring2
        double xPoints3[] = new double[12]; //x pix spring_middle
        double yPoints3[] = new double[12]; //y pix spring_middle

		currentLength1 = Math.sqrt(x1*x1 + y1*y1); //Pythagoras
		currentLength2 = Math.sqrt((1.-x2)*(1.-x2) + y2*y2); //Pythagoras
		currentLength3 = Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)); //Pyth.
		d1 = currentLength1/18.; //devide in 18 pieces
		d2 = currentLength2/18.; //devide
		d3 = currentLength3/18.; //devide

		alpha1 = Math.atan(y1/x1); //angle to rotate for spring_left
		alpha2 = Math.atan(y2/(1.-x2)); //angle to rotate for spring_right
		alpha3 = Math.atan((y2-y1)/(x2-x1)); //angle to rotate spring_middle

		if(px1 < 0) { alpha1 = alpha1 + Math.PI; }
		if(px1 == 0) { if(py1 > H/2) alpha1 = Math.PI/2.;
						 else alpha1 = -Math.PI/2.;}
		if(px2 > W) { alpha2 = alpha2 + Math.PI; }
		if(px2 == W) { if(py2 > H/2) alpha2 = Math.PI/2.;
						 else alpha2 = -Math.PI/2.;}
		if(px1 > px2) { alpha3 = alpha3 + Math.PI; }
		if(px1 == px2) { if(py1 < py2) alpha3 = Math.PI/2.;
						 else alpha3 = -Math.PI/2.;}

        xPoints1[0] = 0.;	yPoints1[0] = 0.; //set spring points
        xPoints1[1] = d1;	yPoints1[1] = 0.;
		xPoints2[0] = 0.;	yPoints2[0] = 0.;
		xPoints2[1] = -d2;  yPoints2[1] = 0.;
		xPoints3[0] = 0.;	yPoints3[0] = 0.;
        xPoints3[1] = d3;	yPoints3[1] = 0.;
		for(int i=2; i<10; i++) //set spring points
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
        xPoints1[10] = 17*d1;  yPoints1[10] = 0.; //set spring points
        xPoints1[11] = 18*d1;  yPoints1[11] = 0.;
        xPoints2[10] = -17*d2; yPoints2[10] = 0.;
        xPoints2[11] = -18*d2; yPoints2[11] = 0.;
        xPoints3[10] = 17*d3;  yPoints3[10] = 0.;
        xPoints3[11] = 18*d3;  yPoints3[11] = 0.;

		double xnew, ynew;
		for(int i=0; i<12; i++) //rotate spring coords and make int[]
		{
			xnew = xPoints1[i]*Math.cos(alpha1) - yPoints1[i]*Math.sin(alpha1);
			ynew = xPoints1[i]*Math.sin(alpha1) + yPoints1[i]*Math.cos(alpha1);
			xPoints1[i] = xnew; yPoints1[i] = ynew;
			xPoints1_int[i] = (int)Math.round(W*xPoints1[i]);
			yPoints1_int[i] = (int)Math.round(H*yPoints1[i]) + H/2;

			xnew = xPoints2[i]*Math.cos(alpha2) + yPoints2[i]*Math.sin(alpha2);
			ynew = -xPoints2[i]*Math.sin(alpha2) + yPoints2[i]*Math.cos(alpha2);
			xPoints2[i] = xnew; yPoints2[i] = ynew;
			xPoints2_int[i] = (int)Math.round(W*xPoints2[i]) + W;
			yPoints2_int[i] = (int)Math.round(H*yPoints2[i]) + H/2;

			xnew = xPoints3[i]*Math.cos(alpha3) - yPoints3[i]*Math.sin(alpha3);
			ynew = xPoints3[i]*Math.sin(alpha3) + yPoints3[i]*Math.cos(alpha3);
			xPoints3[i] = xnew; yPoints3[i] = ynew;
			xPoints3_int[i] = (int)Math.round(W*xPoints3[i]) + px1;
			yPoints3_int[i] = (int)Math.round(H*yPoints3[i]) + py1;
		}
    }//pixels()

    /* force on x1 */
    public double forcex1(double x1)
    {
        return (D*(-2*x1 + x2 + (l*x1)/Math.sqrt(x1*x1 + y1*y1)
        		+ (l*x1)/Math.sqrt((x1 - x2)*(x1 - x2)
        		+ (y1 - y2)*(y1 - y2)) - (l*x2)/Math.sqrt((x1 - x2)*(x1 - x2)
        		+ (y1 - y2)*(y1 - y2))))/m1
        		- reib*0.1*x1p; //friction
    }//forcex1()

    /* force on y1 */
    public double forcey1(double y1)
    {
        return (D*(-2*y1 + (l*y1)/Math.sqrt(x1*x1 + y1*y1)
        		+ (l*y1)/Math.sqrt((x1 - x2)*(x1 - x2)
        		+ (y1 - y2)*(y1 - y2)) + y2
        		- (l*y2)/Math.sqrt((x1 - x2)*(x1 - x2)
        		+ (y1 - y2)*(y1 - y2))))/m1
        		- reib*0.1*y1p //friction
        		+ grav*0.1; //gravity
    }//forcey1()

    /* force on x2 */
    public double forcex2(double x2)
    {
        return (D*(1 + x1 - 2*x2 - (l*x1)/Math.sqrt((x1 - x2)*(x1 - x2)
        		+ (y1 - y2)*(y1 - y2)) + (l*x2)/Math.sqrt((x1 - x2)*(x1 - x2)
        		+ (y1 - y2)*(y1 - y2)) - l/Math.sqrt((-1 + x2)*(-1 + x2)
        		+ y2*y2) + (l*x2)/Math.sqrt((-1 + x2)*(-1 + x2) + y2*y2)))/m2
        		- reib*0.1*x2p; //friction
    }//forcex2()

    /* force on y2 */
    public double forcey2(double y2)
    {
        return (D*(y1 - (l*y1)/Math.sqrt(x1*x1 - 2*x1*x2 + x2*x2
        		+ (y1 - y2)*(y1 - y2)) - 2*y2 + (l*y2)/Math.sqrt(x1*x1
        		- 2*x1*x2 + x2*x2 + (y1 - y2)*(y1 - y2))
        		+ (l*y2)/Math.sqrt(1 - 2*x2 + x2*x2 + y2*y2)))/m2
        		- reib*0.1*y2p //friction
        		+ grav*0.1; //gravity
    }//forcey2()

    public double energy()
    {
        return D/2 + (3*D*l*l)/2 + D*x1*x1 + (m1*x1p*x1p)/2 - D*x2 - D*x1*x2
				+ D*x2*x2 + (m2*x2p*x2p)/2 + D*y1*y1 - D*l*Math.sqrt(x1*x1
				+ y1*y1) + (m1*y1p*y1p)/2 - D*l*Math.sqrt((x1 - x2)*(x1 - x2)
				+ (y1 - y2)*(y1 - y2)) - D*y1*y2 + D*y2*y2
				- D*l*Math.sqrt(1 - 2*x2 + x2*x2 + y2*y2) + (m2*y2p*y2p)/2
				- grav*0.1*(m1*y1 + m2*y2);
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

    public void runge_step_y1()
    {
        k2[0] = dt*y1p;
        l2[0] = dt*forcey1(y1);
        k2[1] = dt*(y1p+l2[0]/2);
        l2[1] = dt*forcey1(y1+k2[0]/2);
        k2[2] = dt*(y1p+l2[1]/2);
        l2[2] = dt*forcey1(y1+k2[1]/2);
        k2[3] = dt*(y1p+l2[2]);
        l2[3] = dt*forcey1(y1+k2[2]);

    }//runge_step_y1()

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

    public void runge_step_y2()
    {
        k4[0] = dt*y2p;
        l4[0] = dt*forcey2(y2);
        k4[1] = dt*(y2p+l4[0]/2);
        l4[1] = dt*forcey2(y2+k4[0]/2);
        k4[2] = dt*(y2p+l4[1]/2);
        l4[2] = dt*forcey2(y2+k4[1]/2);
        k4[3] = dt*(y2p+l4[2]);
        l4[3] = dt*forcey2(y2+k4[2]);
    }//runge_step_y2()

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
			if(startwert[8] < 3)
           	{
        	    startwert[8] = 2*startwert[8];
				D = startwert[8];
	            if(cb_stop.getState()) { update(g); }
			}
        }
        if(evt.getActionCommand() == but_D_min.getActionCommand())
        {
           	startwert[8] = startwert[8]/2;
			D = startwert[8];
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
           	animator = null;
            Choice[0] = true; //Free
            Choice[1] = false; //Together
            Choice[2] = false; //Against
            startwert[0] = 0.2; //x1
            startwert[2] = 0.2; //y1
            startwert[4] = 0.7; //x2
            startwert[6] = 0.0; //y2
			startwerte();
			pixels();
			update(g);
			start();
        }
        if(evt.getItem() == "Together")
        {
           	animator = null;
            Choice[0] = false;
            Choice[1] = true;
            Choice[2] = false;
            startwert[0] = 1./3-0.2; //x1
            startwert[2] = 0.0; //y1
            startwert[4] = 2./3-0.2; //x2
            startwert[6] = 0.0; //y2
			startwerte();
			pixels();
			update(g);
			start();
        }
        if(evt.getItem() == "Against")
        {
           	animator = null;
            Choice[0] = false;
            Choice[1] = false;
            Choice[2] = true;
            startwert[0] = 1./3-0.1; //x1
            startwert[2] = 0.0; //y1
            startwert[4] = 2./3+0.1; //x2
            startwert[6] = 0.0; //y2
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

class ML extends MouseAdapter implements MouseMotionListener //overwrite Mouse
{
	public void Choose (MouseEvent evt) //to set mouse properties
	{
		if(Choice[0]) //Free
		{
            int r; //to avoid overlap of the 2 masses
            r = (int)Math.round(Math.sqrt((px2-evt.getX())*(px2-evt.getX())
            						+(py2-evt.getY())*(py2-evt.getY())));
            if(r > 40)
            {
                if(evt.getX() < 60) { startwert[0] = 60./W; } //boundary_x
                else if(evt.getX() > W-60) {startwert[0] = (double)(W-60)/W;}
                else { startwert[0] = (double)(evt.getX())/W; }

                if(evt.getY() < 20) { startwert[2] = 20./H - 0.5; } //boundary_y
                else if(evt.getY() > H-20)
                		{startwert[2] = (double)(H-20)/H - 0.5;}
                else { startwert[2] = (double)(evt.getY())/H - 0.5; }
			}
			if(r <= 40 & r != 0)
			{
                int Abst[] = new int[2];
                Abst[0] = evt.getX() - px2;
                Abst[1] = evt.getY() - py2;
				double laenge;
				laenge = Math.sqrt(Abst[0]*Abst[0] + Abst[1]*Abst[1]);
				Abst[0] = (int)Math.round((double)Abst[0]/laenge*40.);
				Abst[1] = (int)Math.round((double)Abst[1]/laenge*40.);
                startwert[0] = (double)(px2 + Abst[0])/W;
                startwert[2] = (double)(py2 + Abst[1])/H - 0.5;
			}
			if(r == 0) //exact overlap
			{
                startwert[0] = (double)(px2  - 40)/W;
                startwert[2] = (double)py2/H - 0.5;
			}
		} //Free
		if(Choice[1]) //Together
		{
            if(evt.getX() < 60)
            {
                startwert[0] = 60./W;
                startwert[4] = 60./W + 1./3;
            }
            else if(evt.getX() > W-W/3-60)
            {
                startwert[0] = (double)(W-W/3-60)/W;
                startwert[4] = (double)(W-W/3-60)/W + 1./3;
            }
            else
            {
                startwert[0] = (double)(evt.getX())/W;
                startwert[4] = (double)(evt.getX())/W + 1./3;
            }

            if(evt.getY() < 20)
            {
            	startwert[2] = 20./H - 0.5;
            	startwert[6] = startwert[2];
            }
            else if(evt.getY() > H-20)
            {
            	startwert[2] = (double)(H-20)/H - 0.5;
            	startwert[6] = startwert[2];
            }
            else
            {
	            startwert[2] = (double)(evt.getY())/H - 0.5;
            	startwert[6] = startwert[2];
            }
		} //Together
		if(Choice[2]) //Against
		{
            if(evt.getX() < 100)
            {
                startwert[0] = 100./W;
                startwert[4] = -100./W + 1.;
            }
            else if(evt.getX() > W/2-30)
            {
                startwert[0] = (double)(W/2-30)/W;
                startwert[4] = -(double)(W/2-30)/W + 1.;
            }
            else
            {
                startwert[0] = (double)(evt.getX())/W;
                startwert[4] = -(double)(evt.getX())/W + 1.;
            }

            if(evt.getY() < 20)
            {
            	startwert[2] = 20./H - 0.5;
            	startwert[6] = -startwert[2];
            }
            else if(evt.getY() > H-20)
            {
            	startwert[2] = (double)(H-20)/H - 0.5;
            	startwert[6] = -startwert[2];
            }
            else
            {
	            startwert[2] = (double)(evt.getY())/H - 0.5;
            	startwert[6] = -startwert[2];
            }
		} //Against
	} //Choose()

	public void mousePressed (MouseEvent evt)
	{
		animator = null;
		Choose(evt);
        startwerte();
        pixels();
		update(g);
	}//mousePressed

	public void mouseDragged (MouseEvent evt)
	{
		Choose(evt);
        startwerte();
        pixels();
		update(g);
	}//mouseDragged

	public void mouseReleased (MouseEvent evt)
	{
		start();
	}//mouseReleased

	public void mouseMoved (MouseEvent evt)
	{  }//mouseMoved (simply implemented for Interface MouseMotionListener)

}//ML
}

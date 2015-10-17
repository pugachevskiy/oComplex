package testPrjs;

/***** Gas in a 2-dimensional Box using Euler Algorithm *****
 ***** @author:  Christoph Federrath                    *****
 ***** @version: 13.01.2006                             *****/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.applet.*;

public class Gas extends Applet implements Runnable, ActionListener
{
	private static final int D = 256;        //graphic window dimension
	private int N = 30;                      //initial number of particles
	private static final double dt = 0.0013; //timestep
	private double x[] = new double[100];    //particle positions
	private double y[] = new double[100];    //particle positions
	private double vx[] = new double[100];   //particle velocities
	private double vy[] = new double[100];   //particle velocities
    private double r = 0.0;                  //distance between particles
    private static final double r0 = 0.001;  //Yukawa potential parameter
    private double pressure = 0.0;           //pressure
	private	int v_field[] = new int[50];     //needed for Maxwell-distrib.
    private int step = 0;
    private int maxstep = 1000000;           //run-time
    private int speed = 50;                  //speed for painting
    Canvas can = new Canvas();
    Button but_reset;
	TextField tf_step;
    Label lab_maxstep;
	Scrollbar Speedbar;
	Scrollbar NumberofParticles;
    Label DescriptionSpeedbar;
    Label DescriptionNumberofParticles;
    Thread animator;
    Graphics g, offGraphics;
	Image offImage;

    public void init()
    {
        setLayout(null);
        can.setBackground(Color.white);
        can.setBounds(1,1,D,D);

        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setBounds(D+1,1,100,D);
        pan.setBackground(new Color(0,200,100));
        pan.setFont(new Font("Verdana",Font.PLAIN,9));

		lab_maxstep = new Label("Run-time");
		lab_maxstep.setBounds(10,D/2-120,80,20);

		tf_step = new TextField("" + maxstep);
		tf_step.setBounds(10,D/2-100,70,30);
        tf_step.setFont(new Font("Verdana",Font.PLAIN,9));
		tf_step.addActionListener(this);

	    but_reset = new Button("Reset");
		but_reset.setBounds(10,D/2+90,80,25);
		but_reset.addActionListener(this);

		Speedbar = new Scrollbar(Scrollbar.VERTICAL,951,300,1,1300);
		Speedbar.setSize(15,100);
		Speedbar.setLocation(10,D/2-50);
		Speedbar.addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{ speed = Math.abs(e.getValue()-1001); }
		});

		NumberofParticles = new Scrollbar(Scrollbar.VERTICAL,71,30,1,130);
		NumberofParticles.setSize(15,100);
		NumberofParticles.setLocation(60,D/2-50);
		NumberofParticles.addAdjustmentListener(new AdjustmentListener()
		{
			public void adjustmentValueChanged(AdjustmentEvent e)
			{	stop(); N = Math.abs(e.getValue()-101);
				reset(); start();                               }
		});

        DescriptionSpeedbar = new Label("Speed");
        DescriptionSpeedbar.setSize(40,30);
        DescriptionSpeedbar.setLocation(10,D/2+50);
        DescriptionNumberofParticles = new Label("N");
        DescriptionNumberofParticles.setSize(40,30);
        DescriptionNumberofParticles.setLocation(60,D/2+50);

		pan.add(lab_maxstep); pan.add(tf_step); pan.add(but_reset);
		pan.add(Speedbar); pan.add(DescriptionSpeedbar);
		pan.add(NumberofParticles); pan.add(DescriptionNumberofParticles);
		add(pan); add(can);

        g = can.getGraphics();
        offImage = createImage(D,D); //double-buffer
        offGraphics = offImage.getGraphics(); //double-buffer
		reset();
    }

	public void reset() //method for setting initial values
	{
		step = 0;
		for(int i = 0; i < 50; i++) { v_field[i] = 0; } //clear field
		boolean reset_not_finished = true;
        double r; //particle distance
		int counter;
		while(reset_not_finished)
		{
            counter = 0;
            for(int i = 0; i < N; i++) //set initial particle pos. and vel.
            {
                  x[i] = 0.96*Math.random()+0.02;
                  y[i] = 0.96*Math.random()+0.02;
                 vx[i] = 0.1*(Math.random()-0.5);
                 vy[i] = 0.1*(Math.random()-0.5);
            }
            for(int i = 0; i < N; i++)
            {   for(int j = 0; j < N; j++)
                {   if(i!=j)
                    {   r = Math.sqrt((x[i]-x[j])*(x[i]-x[j])+(y[i]-y[j])*(y[i]-y[j]));
                        if(r < 0.01) continue; else counter++; //minimal distance
                    }
                }
            }
            if(counter == N*N-N) reset_not_finished = false; //all distances are ok
        }
	}//reset()

    public void run()
    {
       	try{Thread.sleep(100);} catch(InterruptedException exc){}
		double force[] = new double[2];
		double forcesum_x, forcesum_y;
		double pulse = 0.0;
		while(Thread.currentThread() == animator)
		{
			if(step >= maxstep) {stop();}
            if(step % speed == 0) //paint not so often
            {
            	paintFrame(offGraphics); //paint new frame into image
	        	g.drawImage(offImage, 0, 0, null); //display image on screen
			}
			for(int i = 0; i < N; i++) //Euler-step for every particle
			{
				x[i] = x[i] + vx[i]*dt;
				y[i] = y[i] + vy[i]*dt;
				forcesum_x = 0.0;
				forcesum_y = 0.0;
				for(int j = 0; j < N; j++)
				{
					if(i!=j)
					{ r = Math.sqrt((x[i]-x[j])*(x[i]-x[j])+(y[i]-y[j])*(y[i]-y[j]));
					  if(r < 0.1)
					  {	force = Force(x[i],y[i],x[j],y[j]);
						forcesum_x = forcesum_x + force[0];
						forcesum_y = forcesum_y + force[1];
					  }
					}
				}
				vx[i] = vx[i] + forcesum_x*dt;
				vy[i] = vy[i] + forcesum_y*dt;
			}
			for(int i = 0; i < N; i++) //boundary conditions
			{
		    	if(x[i] < 0.01) {vx[i] = -vx[i]; pulse = pulse + vx[i];}
		    	if(x[i] > 0.99) {vx[i] = -vx[i]; pulse = pulse - vx[i];}
		    	if(y[i] < 0.01) {vy[i] = -vy[i]; pulse = pulse + vy[i];}
		    	if(y[i] > 0.99) {vy[i] = -vy[i]; pulse = pulse - vy[i];}
			}
            if(step % 5000 == 0) //evaluate pressure
            { pressure = pulse/(5000*dt); pulse = 0.0; }
	    	PDF_velocity();
	    	step++;
	    }//while
    }//run

	public double[] Force(double x1, double y1, double x2, double y2)
	{
		double force[] = new double[2];
		double dummy = -1.0/(r*r)*Math.exp(-r/r0)*(1.0/r+1.0/r0);
		force[0] = dummy*(x2-x1);
		force[1] = dummy*(y2-y1);
		return force;
	}

	public float Energy()
	{
		float U = 0; //potential energy
		float T = 0; //kinetic energy
		float r;     //particle distance
		for(int i = 0; i < N; i++)
		{
			T = T + (float)(0.5*1.0*(vx[i]*vx[i]+vy[i]*vy[i]));
			for(int j = 0; j < N; j++)
			{	if(i!=j)
				{	r = (float)Math.sqrt((x[i]-x[j])*(x[i]-x[j])+(y[i]-y[j])*(y[i]-y[j]));
					U = U + (float)(1.0*1.0/r*Math.exp(-r/r0)); //Yukawa potential sum
				}
			}
		}
		return T+U;
	}

	public void PDF_velocity()
	{
		double v;
		for(int i = 0; i < N; i++)
		{
			v = Math.sqrt(vx[i]*vx[i]+vy[i]*vy[i]);
			for(int j = 0; j < 50; j++)
			{ if((v >= j*0.0019) && (v < (j+1)*0.0019)) v_field[j]++; }
		}
	}

	public void paintFrame(Graphics g)  //method for painting particles
	{
        g.setFont(new Font("Verdana",Font.BOLD,10));
		g.setColor(can.getBackground());
        g.fillRect(0,0,D,D);
        g.setColor(Color.black);
        g.drawRect(0,0,D-1,D-1);
		for(int i = 0; i < N; i++)
		{ g.fillOval((int)(D*x[i]-4),(int)(D*y[i]-4),7,7); //draw particle
        }
		for(int i = 0; i < 50; i++)
		{   g.fillRect(10+i*3,(int)(D-v_field[i]/8000.), //draw Maxwell-
       	                    2,(int)(v_field[i]/8000.));  //Boltzmann Distribution
		}
        g.setColor(Color.blue); //printing out values
		g.drawString("Time", D/2-120, 12);
		g.drawString("" + step, D/2-120, 24);
		g.drawString("Energy", D/2-35, 12);
		g.drawString("" + Math.round(Energy()*1e4)/1e4, D/2-35, 24);
		g.drawString("Pressure", D/2+50, 12);
		g.drawString("" + Math.round(pressure*1e4)/1e4, D/2+50, 24);
		g.drawString("Speed", D/2+80, D-20);
		g.drawString("" + speed, D/2+80, D-8);
		g.drawString("N", D/2+50, D-20);
		g.drawString("" + N, D/2+50, D-8);
	}//paintFrame()

    public void actionPerformed(ActionEvent evt)
    {
        maxstep = (int)Double.valueOf(tf_step.getText()).doubleValue();
		if(evt.getActionCommand() == but_reset.getActionCommand())
        {
        	stop(); reset();
   	       	try{Thread.sleep(100);} catch(InterruptedException exc){}
        	start();
        }
    }//actionPerformed()

    public void start()
    {
		if(animator == null)
	    {
            animator = new Thread(this);
            animator.start();
	    }
    }//start()

    public void stop()
    {
		animator = null;
    }//stop()
}
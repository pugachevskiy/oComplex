package testPrjs;

/***** Deterministic chaos           *****
 ***** @author: Christoph Federrath  *****/

import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Chaos extends Applet implements Runnable, ActionListener
{
    private static final int Lx = 260, Ly = 260; //graphic window
    private int px11, px12, py11, py12; //pixelcoordinates
    private int px21, px22, py21, py22; //pixelcoordinates
    private double phi11, omega11; //coordinates and velocities
    private double phi12, omega12; //coordinates and velocities
    private double phi21, omega21; //coordinates and velocities
    private double phi22, omega22; //coordinates and velocities
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double startwert; //save initial values
    private boolean stop = false; //go/pause
    private int step;
    Canvas can, header;
    Button but_new, but_go;
    Button but_phi22_plus, but_phi22_min;
    Thread animator;
    Graphics g, offGraphics, ghead;
	Image offImage;

    public void init()
    {
        setLayout(null);
        header = new Canvas();
        header.setBounds(1,1,2*Lx,40);
        header.setBackground(Color.white);
        can = new Canvas();
        can.setBounds(1,41,2*Lx,Ly+50);
        can.setBackground(Color.white);
        Panel pan = new Panel();
		pan.setLayout(null);
        pan.setBounds(2*Lx+1,1,100,Ly+90);
        pan.setBackground(new Color(0,200,100));
        pan.setFont(new Font("Verdana",Font.PLAIN,10));

        but_new = new Button("New");
        but_go = new Button("Go/Pause");
        but_phi22_plus = new Button("Phi_22 +");
        but_phi22_min = new Button("Phi_22 -");

        but_new.setBounds(10,Ly/2-35,80,27);
        but_phi22_plus.setBounds(10,Ly/2+10,80,27);
        but_phi22_min.setBounds(10,Ly/2+46,80,27);
        but_go.setBounds(10,Ly/2+90,80,27);

        pan.add(but_go); pan.add(but_new);
		pan.add(but_phi22_plus); pan.add(but_phi22_min);
        add(pan); add(can); add(header);

        but_new.addActionListener(this);
        but_go.addActionListener(this);
        but_phi22_plus.addActionListener(this);
        but_phi22_min.addActionListener(this);

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,15));
        g = can.getGraphics();
        g.setFont(new Font("Verdana",Font.BOLD,10));

		startwert = Math.PI/180*170.; //phi22
    }//init()

    public void startwerte()  //method for setting initial values
    {
        phi11 = Math.PI/180*130.; omega11 = 0.0;
		phi12 = Math.PI/180*170.; omega12 = 0.0;
        phi21 = Math.PI/180*130.; omega21 = 0.0;
		phi22 = startwert; omega22 = 0.0;
    	step = 0;
    	stop = false;
    }//startwerte()

    public void run()
    {
        try{Thread.sleep(700);} catch(InterruptedException e){}
		ghead.drawString("Change Phi_22 by a minimal value ...",120,25);
        startwerte();

        while(Thread.currentThread() == animator)
        {
            while(stop){try{Thread.sleep(40);} catch(InterruptedException e){}}

	       	/* pendulum 1 */
	       	runge_step_phi1(phi11,omega11,phi12,omega12); //Runge-Kutta
        	runge_step_phi2(phi11,omega11,phi12,omega12); //Runge-Kutta
            phi11 = phi11 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //Runge-Kutta
            omega11 = omega11 + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //Runge-Kutta
            phi12 = phi12 + (k2[0]+2*k2[1]+2*k2[2]+k2[3])/6; //Runge-Kutta
            omega12 = omega12 + (l2[0]+2*l2[1]+2*l2[2]+l2[3])/6; //Runge-Kutta

	       	/* pendulum 2 */
	       	runge_step_phi1(phi21,omega21,phi22,omega22); //Runge-Kutta
        	runge_step_phi2(phi21,omega21,phi22,omega22); //Runge-Kutta
            phi21 = phi21 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //Runge-Kutta
            omega21 = omega21 + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //Runge-Kutta
            phi22 = phi22 + (k2[0]+2*k2[1]+2*k2[2]+k2[3])/6; //Runge-Kutta
            omega22 = omega22 + (l2[0]+2*l2[1]+2*l2[2]+l2[3])/6; //Runge-Kutta

        	if(step % 15 == 0) //paint not so often
        	{
	            try{Thread.sleep(8);} catch(InterruptedException e){}
                pixels(); //calculate pixelcoords
                repaint(); //paint new frame
        	}
			step++;
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
            offImage = createImage(2*Lx,Ly+50);
            offGraphics = offImage.getGraphics();
        }
        offGraphics.setFont(new Font("Verdana",Font.BOLD,10));
        offGraphics.setColor(can.getBackground());
        offGraphics.fillRect(0, 0, 2*Lx, Ly+50);
        paintFrame(offGraphics);
        gr.drawImage(offImage, 0, 0, null);
    } //update(gr)

    public void paintFrame(Graphics gr)
    {
		print(gr); //print values
        gr.drawLine(0,Ly/2,2*Lx,Ly/2);
        gr.drawLine(Lx/2,0,Lx/2,Ly);
        gr.drawLine(Lx+Lx/2,0,Lx+Lx/2,Ly);
        gr.setColor(Color.red);
        gr.drawLine(Lx/2,Ly/2,px11,py11);
        gr.drawLine(px11,py11,px12,py12);
        gr.drawLine(Lx+Lx/2,Ly/2,px21,py21);
        gr.drawLine(px21,py21,px22,py22);
        gr.setColor(Color.black);
        gr.fillOval(px11-6,py11-6,13,13);
        gr.fillOval(px12-6,py12-6,13,13);
        gr.fillOval(px21-6,py21-6,13,13);
        gr.fillOval(px22-6,py22-6,13,13);
    } //paintFrame(gr)

    public void print(Graphics gr)
    {
		gr.setColor(Color.blue);
		gr.drawString("Phi_12:",Lx/2-20,Ly+20);
		gr.drawString("" +Math.round(100*phi12*180/Math.PI)/100. +"°",Lx/2-20,Ly+40);
		gr.drawString("Phi_22:",Lx+Lx/2-20,Ly+20);
		gr.drawString("" +Math.round(100*phi22*180/Math.PI)/100. +"°",Lx+Lx/2-20,Ly+40);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoords
    {
        px11 = (int)Math.round((double)(Lx/2) + 62*Math.sin(phi11));
        py11 = (int)Math.round((double)(Ly/2) + 62*Math.cos(phi11));
        px12 = (int)Math.round((double)px11 + 62*Math.sin(phi12));
        py12 = (int)Math.round((double)py11 + 62*Math.cos(phi12));
        px21 = (int)Math.round((double)(Lx+Lx/2) + 62*Math.sin(phi21));
        py21 = (int)Math.round((double)(Ly/2) + 62*Math.cos(phi21));
        px22 = (int)Math.round((double)px21 + 62*Math.sin(phi22));
        py22 = (int)Math.round((double)py21 + 62*Math.cos(phi22));
    }//pixels()

    /* force on Phi_1 */
    public double forcephi1(double phi1, double omega1, double phi2, double omega2)
    {
        return -( grav*(   2*Math.sin(phi1)
        		  	     + Math.sin(phi1)
        		  	     + Math.sin(phi1-2*phi2)
        		 	   )
        		  + omega1*omega1*Math.sin(2*phi1-2*phi2)
        		  + 2*omega2*omega2*Math.sin(phi1-phi2)
			    ) / ( 3. - Math.cos(2*phi1-2*phi2) );
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(double phi1, double omega1, double phi2, double omega2)
    {
        return (   2*Math.sin(phi1-phi2)
        			  *(   2*grav*Math.cos(phi1)
        			     + 2*omega1*omega1
			    		 + omega2*omega2*Math.cos(phi1-phi2)
			    	   )
        	   ) / ( 3. - Math.cos(2*phi1-2*phi2) );
    }//forcephi2()

    public double energy(double phi1, double omega1, double phi2, double omega2)
    {
    	return   omega1*omega1 + 0.5*omega2*omega2
    		   + omega1*omega2*Math.cos(phi1-phi2)
    		   - grav*(2*Math.cos(phi1) + Math.cos(phi2));
    }//energy()

    public void runge_step_phi1(double phi1, double omega1, double phi2, double omega2)
    {
        k1[0] = dt*omega1;
        l1[0] = dt*forcephi1(phi1, omega1, phi2, omega2);
        k1[1] = dt*(omega1+l1[0]/2);
        l1[1] = dt*forcephi1(phi1+k1[0]/2, omega1+l1[0]/2, phi2, omega2);
        k1[2] = dt*(omega1+l1[1]/2);
        l1[2] = dt*forcephi1(phi1+k1[1]/2, omega1+l1[1]/2, phi2, omega2);
        k1[3] = dt*(omega1+l1[2]);
        l1[3] = dt*forcephi1(phi1+k1[2], omega1+l1[2], phi2, omega2);
    }//runge_step_phi1()

    public void runge_step_phi2(double phi1, double omega1, double phi2, double omega2)
    {
        k2[0] = dt*omega2;
        l2[0] = dt*forcephi2(phi1, omega1, phi2, omega2);
        k2[1] = dt*(omega2+l2[0]/2);
        l2[1] = dt*forcephi2(phi1, omega1, phi2+k2[0]/2, omega2+l2[0]/2);
        k2[2] = dt*(omega2+l2[1]/2);
        l2[2] = dt*forcephi2(phi1, omega1, phi2+k2[1]/2, omega2+l2[1]/2);
        k2[3] = dt*(omega2+l2[2]);
        l2[3] = dt*forcephi2(phi1, omega1, phi2+k2[2], omega2+l2[2]);
    }//runge_step_phi2()

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getActionCommand()==but_new.getActionCommand())
        {
        	stop();
			startwerte(); //set initial values
            pixels(); //calculate pixelcoords
			update(g); //paint initial state
			start();
        }
        if(evt.getActionCommand()==but_go.getActionCommand())
		{
			stop = !stop;
		}
        if(evt.getActionCommand()==but_phi22_plus.getActionCommand())
        {
			stop();
        	startwert = startwert + Math.PI/180*0.01;
            startwerte();
            pixels(); //calculate pixelcoords
			update(g); //paint initial state
			start();
        }
        if(evt.getActionCommand()==but_phi22_min.getActionCommand())
        {
			stop();
        	startwert = startwert - Math.PI/180*0.01;
            startwerte();
            pixels(); //calculate pixelcoords
			update(g); //paint initial state
			start();
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
    	offImage = null;
		offGraphics = null;
    }//stop()
}

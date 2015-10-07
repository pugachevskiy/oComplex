package com.openComplex.app.DynamicalSystems.Pendulums.PendulumWithFreeMounting;

/**
 * Created by strange on 07/10/15.
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class FreePend extends Applet implements Runnable, ActionListener
{
    private static final int Lx = 500, Ly = 250; //graphic window
    private int ax, px, py; //pixelcoordinates
    private double a, ap, phi, omega; //coordinates and velocities
    private double mratio; //mass-ratio
    private int reib; //friction
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0004; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[4]; //save initial values
    private int startwert_reib; //save initial friction
    private int step;
    Canvas can, header;
    Button but_new;
    Button but_phi_plus, but_phi_min;
    Button but_omega_plus, but_omega_min;
    Button but_reib_plus, but_reib_min;
    Button but_m_plus, but_m_min;
    Button but_a_plus, but_a_min;
    Checkbox cb_stop;
    Thread animator;
    Graphics g, offGraphics, ghead;
    Image offImage;

    public void init()
    {
        setLayout(null);
        header = new Canvas();
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
        but_phi_plus = new Button("Phi +");
        but_phi_min = new Button("Phi -");
        but_omega_plus = new Button("Omega +");
        but_omega_min = new Button("Omega -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        but_m_plus = new Button("m1/m2 +");
        but_m_min = new Button("m1/m2 -");
        but_a_plus = new Button("a +");
        but_a_min = new Button("a -");
        cb_stop = new Checkbox("Stop/Go", false);

        but_new.setBounds(10,10,80,23);
        but_phi_plus.setBounds(10,40,80,23);
        but_phi_min.setBounds(10,65,80,23);
        but_omega_plus.setBounds(10,95,80,23);
        but_omega_min.setBounds(10,120,80,23);
        but_reib_plus.setBounds(10,150,80,23);
        but_reib_min.setBounds(10,175,80,23);
        but_m_plus.setBounds(10,205,80,23);
        but_m_min.setBounds(10,230,80,23);
        but_a_plus.setBounds(10,260,80,23);
        but_a_min.setBounds(10,285,80,23);
        cb_stop.setBounds(10,310,70,25);
        cb_stop.setBackground(new Color(0,200,100));

        pan.add(cb_stop); pan.add(but_new);
        pan.add(but_phi_plus); pan.add(but_phi_min);
        pan.add(but_omega_plus); pan.add(but_omega_min);
        pan.add(but_reib_plus); pan.add(but_reib_min);
        pan.add(but_m_plus); pan.add(but_m_min);
        pan.add(but_a_plus); pan.add(but_a_min);
        add(pan); add(can); add(header);

        but_new.addActionListener(this);
        but_phi_plus.addActionListener(this);
        but_phi_min.addActionListener(this);
        but_omega_plus.addActionListener(this);
        but_omega_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
        but_m_plus.addActionListener(this);
        but_m_min.addActionListener(this);
        but_a_plus.addActionListener(this);
        but_a_min.addActionListener(this);

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,15));
        g = can.getGraphics();
        g.setFont(new Font("Verdana",Font.BOLD,10));

        startwert[0] = Math.PI/180.*175.; //phi
        startwert[1] = 0.0; //omega
        startwert[2] = 1.2; //a
        startwert[3] = 10.0; //mratio
        startwert_reib = 1; //reib
    }//init()

    public void startwerte()  //method for setting initial values
    {
        phi = startwert[0];
        omega = startwert[1];
        a = startwert[2];
        mratio = startwert[3];
        reib = startwert_reib;
        ap = 0.0;
        step = 0;
    }//startwerte()

    public void run()
    {
        try{Thread.sleep(500);} catch(InterruptedException e){}
        ghead.drawString("Pendulum with free mounting",140,25);
        startwerte();

        while(Thread.currentThread() == animator)
        {
            if(cb_stop.getState()) //Stop/Go
            {try{Thread.sleep(10);}catch(InterruptedException e){}continue;}

            runge_step_a(); //Runge-Kutta
            runge_step_phi(); //Runge-Kutta
            a = a + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //Runge-Kutta
            ap = ap + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //Runge-Kutta
            phi = phi + (k2[0]+2*k2[1]+2*k2[2]+k2[3])/6; //Runge-Kutta
            omega = omega + (l2[0]+2*l2[1]+2*l2[2]+l2[3])/6; //Runge-Kutta

            if(step % 16 == 0) //paint not so often
            {
                try{Thread.sleep(8);} catch(InterruptedException e){}
                pixels(); //calculate pixelcoordinates
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
            offImage = createImage(Lx,Ly+50);
            offGraphics = offImage.getGraphics();
        }
        offGraphics.setFont(new Font("Verdana",Font.BOLD,10));
        offGraphics.setColor(can.getBackground());
        offGraphics.fillRect(0, 0, Lx, Ly+50);
        paintFrame(offGraphics);
        gr.drawImage(offImage, 0, 0, null);
    } //update(gr)

    public void paintFrame(Graphics gr)
    {
        print(gr); //print values
        gr.drawLine(0,Ly/2,Lx,Ly/2);
        gr.drawLine(Lx/2,0,Lx/2,Ly);
        gr.setColor(Color.black);
        gr.drawString("Step " + step, 20, 10);
        gr.setColor(Color.red);
        gr.drawLine(ax,Ly/2,px,py);
        gr.setColor(Color.black);
        gr.fillRect(ax-4,Ly/2-2,9,5);
        gr.fillOval((int)(px-5*Math.pow(mratio,1/3.)),(int)(py-5*Math.pow(mratio,1/3.)),
                (int)(11*Math.pow(mratio,1/3.)),(int)(11*Math.pow(mratio,1/3.)));
    } //paintFrame(gr)

    public void print(Graphics gr)  //method for printing values
    {
        gr.setColor(Color.blue);
        gr.drawString("Energy:",20,Ly+20);
        gr.drawString("" +(double)Math.round(10*energy())/10,20,Ly+40);
        gr.drawString("Friction:",100,Ly+20);
        gr.drawString("" +reib,100,Ly+40);
        gr.drawString("m1/m2:",190,Ly+20);
        gr.drawString("" +mratio,190,Ly+40);
        gr.drawString("Phi:",290,Ly+20);
        gr.drawString("" +(double)Math.round(10*phi*180/Math.PI)/10 +"�",290,Ly+40);
        gr.drawString("Omega:",370,Ly+20);
        gr.drawString("" +(double)Math.round(10*omega)/10,370,Ly+40);
        gr.drawString("a:",450,Ly+20);
        gr.drawString("" +(double)Math.round(10*a)/10,450,Ly+40);
    }//print()

    public void pixels()  //method for calculating pixelcoords
    {
        ax = (int)Math.round((double)(Lx/2) + 100*a);
        px = (int)Math.round((double)(Lx/2) + 100*a + 100*Math.sin(phi));
        py = (int)Math.round((double)(Ly/2) + 100*Math.cos(phi));
    }//pixels()

    /* force on a */
    public double forcea(double phi, double omega)
    {
        return ((1./(1.+1./mratio))*omega*omega*Math.sin(phi)+10.*Math.sin(phi)*Math.cos(phi))/
                (1.-(1./(1.+1./mratio))*Math.cos(phi)*Math.cos(phi))-0.1*reib*ap;
    }//forcea()

    /* force on phi */
    public double forcephi(double phi, double omega)
    {
        return -((1./(1.+1./mratio))*omega*omega*Math.sin(phi)*Math.cos(phi)+10.*Math.sin(phi))/
                (1.-(1./(1.+1./mratio))*Math.cos(phi)*Math.cos(phi))-0.1*reib*omega;
    }//forcephi()

    public double energy()
    {
        return 0.5*ap*ap + 0.5*mratio*(ap*ap+omega*omega+2.*ap*omega*Math.cos(phi))
                - mratio*10.*Math.cos(phi);
    }//energy()

    public void runge_step_a()
    {
        k1[0] = dt*ap;
        l1[0] = dt*forcea(phi,omega);
        k1[1] = dt*(ap+l1[0]/2);
        l1[1] = dt*forcea(phi+k1[0]/2,omega+l1[0]/2);
        k1[2] = dt*(ap+l1[1]/2);
        l1[2] = dt*forcea(phi+k1[1]/2,omega+l1[1]/2);
        k1[3] = dt*(ap+l1[2]);
        l1[3] = dt*forcea(phi+k1[2],omega+l1[2]);
    }//runge_step_a()

    public void runge_step_phi()
    {
        k2[0] = dt*omega;
        l2[0] = dt*forcephi(phi,omega);
        k2[1] = dt*(omega+l2[0]/2);
        l2[1] = dt*forcephi(phi+k2[0]/2,omega+l2[0]/2);
        k2[2] = dt*(omega+l2[1]/2);
        l2[2] = dt*forcephi(phi+k2[1]/2,omega+l2[1]/2);
        k2[3] = dt*(omega+l2[2]);
        l2[3] = dt*forcephi(phi+k2[2],omega+l2[2]);
    }//runge_step_phi()

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
        if(evt.getActionCommand()==but_phi_plus.getActionCommand())
        {
            stop();
            startwert[0] = startwert[0] + Math.PI/180*5;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_phi_min.getActionCommand())
        {
            stop();
            startwert[0] = startwert[0] - Math.PI/180*5;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_omega_plus.getActionCommand())
        {
            stop();
            startwert[1]++;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_omega_min.getActionCommand())
        {
            stop();
            startwert[1]--;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_reib_plus.getActionCommand())
        {
            startwert_reib++; reib = startwert_reib;
            if(cb_stop.getState()) { update(g); }
        }
        if(evt.getActionCommand()==but_reib_min.getActionCommand())
        {
            if(reib > 0)
            { startwert_reib--; reib = startwert_reib;
                if(cb_stop.getState()) { update(g); }
            }
        }
        if(evt.getActionCommand()==but_m_plus.getActionCommand())
        {
            if(mratio < 100)
            {
                startwert[3]++;
                mratio = startwert[3];
                if(cb_stop.getState()) { update(g); }
            }
        }
        if(evt.getActionCommand()==but_m_min.getActionCommand())
        {
            if(mratio > 1)
            {
                startwert[3]--;
                mratio = startwert[3];
                if(cb_stop.getState()) { update(g); }
            }
        }
        if(evt.getActionCommand()==but_a_plus.getActionCommand())
        {
            stop();
            startwert[2] += .1;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_a_min.getActionCommand())
        {
            stop();
            startwert[2] -= .1;
            startwerte(); //set initial values
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
       // offImage = null;
      //  offGraphics = null;
    }//stop()
}

package com.openComplex.app.DynamicalSystems.Pendulums.TriplePendulum;

/**
 * Created by strange on 07/10/15.
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class TripPend extends Applet implements Runnable, ActionListener
{
    private static final int Lx = 490, Ly = 330; //graphic window
    private int px1, px2, px3, py1, py2, py3; //pixelcoordinates
    private double phi1, omega1; //coordinates and velocities
    private double phi2, omega2; //coordinates and velocities
    private double phi3, omega3; //coordinates and velocities
    private int reib; //Friction
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double k3[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double l3[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[3]; //save initial values
    private int startwert_reib; //save initial friction
    private int step;
    Canvas can, header;
    Button but_new;
    Button but_phi1_plus, but_phi1_min;
    Button but_phi2_plus, but_phi2_min;
    Button but_phi3_plus, but_phi3_min;
    Button but_reib_plus, but_reib_min;
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
        but_phi1_plus = new Button("Phi1 +");
        but_phi1_min = new Button("Phi1 -");
        but_phi2_plus = new Button("Phi2 +");
        but_phi2_min = new Button("Phi2 -");
        but_phi3_plus = new Button("Phi3 +");
        but_phi3_min = new Button("Phi3 -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        cb_stop = new Checkbox("Stop/Go", false);

        but_new.setBounds(10,20,80,27);
        but_phi1_plus.setBounds(10,70,80,27);
        but_phi1_min.setBounds(10,105,80,27);
        but_phi2_plus.setBounds(10,145,80,27);
        but_phi2_min.setBounds(10,180,80,27);
        but_phi3_plus.setBounds(10,220,80,27);
        but_phi3_min.setBounds(10,255,80,27);
        but_reib_plus.setBounds(10,300,80,27);
        but_reib_min.setBounds(10,335,80,27);
        cb_stop.setBounds(10,380,70,30);
        cb_stop.setBackground(new Color(0,200,100));

        pan.add(but_new); pan.add(cb_stop);
        pan.add(but_phi1_plus); pan.add(but_phi1_min);
        pan.add(but_phi2_plus); pan.add(but_phi2_min);
        pan.add(but_phi3_plus); pan.add(but_phi3_min);
        pan.add(but_reib_plus); pan.add(but_reib_min);
        add(pan); add(can); add(header);

        but_new.addActionListener(this);
        but_phi1_plus.addActionListener(this);
        but_phi1_min.addActionListener(this);
        but_phi2_plus.addActionListener(this);
        but_phi2_min.addActionListener(this);
        but_phi3_plus.addActionListener(this);
        but_phi3_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,15));
        g = can.getGraphics();
        g.setFont(new Font("Verdana",Font.BOLD,10));

        startwert[0] = Math.PI/180*130.; //phi1
        startwert[1] = Math.PI/180*130.; //phi2
        startwert[2] = Math.PI/180*130.; //phi3
        startwert_reib = 0; //reib
    }//init()

    public void startwerte()
    {
        phi1 = startwert[0];
        phi2 = startwert[1];
        phi3 = startwert[2];
        reib = startwert_reib;
        omega1 = 0.0;
        omega2 = 0.0;
        omega3 = 0.0;
        step = 0;
    }//startwerte()

    public void run()
    {
        try{Thread.sleep(500);} catch(InterruptedException e){}
        ghead.drawString("Triple-Pendulum",180,25);
        startwerte();

        while(Thread.currentThread() == animator)
        {
            if(cb_stop.getState()) //Stop/Go
            {try{Thread.sleep(10);}catch(InterruptedException e){}continue;}



            if(step % 20 == 0) //paint not so often
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
            gr = can.getGraphics(); //initialize graphic window again
            gr.drawImage(offImage, 0, 0, null);
        }
    } //paint(gr)

    public void update(Graphics gr)  //double buffer
    {
        gr = can.getGraphics(); //initialize graphic window again
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
        gr.setColor(Color.black);
        gr.drawLine(0,Ly/2,Lx,Ly/2);
        gr.drawLine(Lx/2,0,Lx/2,Ly);
        gr.drawString("Step " + step, 20, 10);
        gr.setColor(Color.red);
        gr.drawLine(Lx/2,Ly/2,px1,py1);
        gr.drawLine(px1,py1,px2,py2);
        gr.drawLine(px2,py2,px3,py3);
        gr.setColor(Color.black);
        gr.fillOval(px1-5,py1-5,10,10);
        gr.fillOval(px2-5,py2-5,10,10);
        gr.fillOval(px3-5,py3-5,10,10);
    } //paintFrame(gr)

    public void print(Graphics gr)
    {
        gr.setColor(Color.blue);
        gr.drawString("Energy:",30,Ly+20);
        gr.drawString("" +(double)(Math.round(10*energy()))/10,30,Ly+40);
        gr.drawString("Friction:",120,Ly+20);
        gr.drawString("" +reib,120,Ly+40);
        gr.drawString("Phi1:",230,Ly+20);
        gr.drawString("" +Math.round(10*phi1*180/Math.PI)/10.0 +"�",230,Ly+40);
        gr.drawString("Phi2:",320,Ly+20);
        gr.drawString("" +Math.round(10*phi2*180/Math.PI)/10.0 +"�",320,Ly+40);
        gr.drawString("Phi3:",410,Ly+20);
        gr.drawString("" +Math.round(10*phi3*180/Math.PI)/10.0 +"�",410,Ly+40);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoords
    {
        px1 = (int)Math.round((double)(Lx/2) + 55*Math.sin(phi1));
        py1 = (int)Math.round((double)(Ly/2) + 55*Math.cos(phi1));
        px2 = (int)Math.round((double)(px1) + 55*Math.sin(phi2));
        py2 = (int)Math.round((double)(py1) + 55*Math.cos(phi2));
        px3 = (int)Math.round((double)(px2) + 55*Math.sin(phi3));
        py3 = (int)Math.round((double)(py2) + 55*Math.cos(phi3));
    }//pixels()

    /* force on Phi_1 */
    public double forcephi1(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3   )
    {
        return (   10*grav*Math.sin(phi1)
                + 4*grav*Math.sin(phi1-2*phi2)
                - grav*Math.sin(phi1+2*phi2-2*phi3)
                - grav*Math.sin(phi1-2*phi2+2*phi3)
                + 4*omega1*omega1*Math.sin(2*phi1-2*phi2)
                + 8*omega2*omega2*Math.sin(phi1-phi2)
                + 2*omega3*omega3*Math.sin(phi1-phi3)
                + 2*omega3*omega3*Math.sin(phi1-2*phi2+phi3)
        ) /
                (-10. + 4*Math.cos(2*phi1-2*phi2) + 2*Math.cos(2*phi2-2*phi3))
                -0.1*reib*omega1;
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3   )
    {
        return ( - 7*grav*Math.sin(2*phi1-phi2)
                + 7*grav*Math.sin(phi2)
                + grav*Math.sin(phi2-2*phi3)
                + grav*Math.sin(2*phi1+phi2-2*phi3)
                + 2*omega1*omega1*Math.sin(phi1+phi2-2*phi3)
                - 14*omega1*omega1*Math.sin(phi1-phi2)
                + 2*omega2*omega2*Math.sin(2*phi2-2*phi3)
                - 4*omega2*omega2*Math.sin(2*phi1-2*phi2)
                + 6*omega3*omega3*Math.sin(phi2-phi3)
                - 2*omega3*omega3*Math.sin(2*phi1-phi2-phi3)
        ) /
                (-10. + 4*Math.cos(2*phi1-2*phi2) + 2*Math.cos(2*phi2-2*phi3))
                -0.1*reib*omega2;
    }//forcephi2()

    /* force on Phi_3 */
    public double forcephi3(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3   )
    {
        return -2*Math.sin(phi2-phi3)*
                (   grav*Math.cos(2*phi1-phi2)
                        + grav*Math.cos(phi2)
                        + 2*omega1*omega1*Math.cos(phi1-phi2)
                        + 2*omega2*omega2
                        + omega3*omega3*Math.cos(phi2-phi3)
                ) /
                (-5. + 2*Math.cos(2*phi1-2*phi2) + Math.cos(2*phi2-2*phi3))
                -0.1*reib*omega3;
    }//forcephi3()

    public double energy()
    {
        return 0.5*(   3*omega1*omega1 + 2*omega2*omega2 + omega3*omega3
                + 4*omega1*omega2*Math.cos(phi1-phi2)
                + 2*omega1*omega3*Math.cos(phi1-phi3)
                + 2*omega2*omega3*Math.cos(phi2-phi3)
        )
                - grav*(3*Math.cos(phi1) + 2*Math.cos(phi2) + Math.cos(phi3));
    }//energy()

    public void runge_step_phi1()
    {
        k1[0] = dt*omega1;
        l1[0] = dt*forcephi1(phi1,omega1,phi2,omega2,phi3,omega3);
        k1[1] = dt*(omega1+l1[0]/2);
        l1[1] = dt*forcephi1(phi1+k1[0]/2,omega1+l1[0]/2,phi2,omega2,phi3,omega3);
        k1[2] = dt*(omega1+l1[1]/2);
        l1[2] = dt*forcephi1(phi1+k1[1]/2,omega1+l1[1]/2,phi2,omega2,phi3,omega3);
        k1[3] = dt*(omega1+l1[2]);
        l1[3] = dt*forcephi1(phi1+k1[2],omega1+l1[2],phi2,omega2,phi3,omega3);
    }//runge_step_phi1()

    public void runge_step_phi2()
    {
        k2[0] = dt*omega2;
        l2[0] = dt*forcephi2(phi1,omega1,phi2,omega2,phi3,omega3);
        k2[1] = dt*(omega2+l2[0]/2);
        l2[1] = dt*forcephi2(phi1,omega1,phi2+k2[0]/2,omega2+l2[0]/2,phi3,omega3);
        k2[2] = dt*(omega2+l2[1]/2);
        l2[2] = dt*forcephi2(phi1,omega1,phi2+k2[1]/2,omega2+l2[1]/2,phi3,omega3);
        k2[3] = dt*(omega2+l2[2]);
        l2[3] = dt*forcephi2(phi1,omega1,phi2+k2[2],omega2+l2[2],phi3,omega3);
    }//runge_step_phi2()

    public void runge_step_phi3()
    {
        k3[0] = dt*omega3;
        l3[0] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3,omega3);
        k3[1] = dt*(omega3+l3[0]/2);
        l3[1] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3+k3[0]/2,omega3+l3[0]/2);
        k3[2] = dt*(omega3+l3[1]/2);
        l3[2] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3+k3[1]/2,omega3+l3[1]/2);
        k3[3] = dt*(omega3+l3[2]);
        l3[3] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3+k3[2],omega3+l3[2]);
    }//runge_step_phi3()

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
        if(evt.getActionCommand()==but_phi1_plus.getActionCommand())
        {
            stop();
            startwert[0] = startwert[0] + Math.PI/180*5;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_phi1_min.getActionCommand())
        {
            stop();
            startwert[0] = startwert[0] - Math.PI/180*5;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_phi2_plus.getActionCommand())
        {
            stop();
            startwert[1] = startwert[1] + Math.PI/180*5;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_phi2_min.getActionCommand())
        {
            stop();
            startwert[1] = startwert[1] - Math.PI/180*5;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_phi3_plus.getActionCommand())
        {
            stop();
            startwert[2] = startwert[2] + Math.PI/180*5;
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_phi3_min.getActionCommand())
        {
            stop();
            startwert[2] = startwert[2] - Math.PI/180*5;
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
    }//actionPerformed(evt)

    public void start()
    {
        animator = new Thread(this);
        animator.start();
    }//start()

    public void stop()
    {
        animator = null;
        //offImage = null;
       // offGraphics = null;
    }//stop()
}

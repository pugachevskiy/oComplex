package com.openComplex.app.DynamicalSystems.Pendulums.DrivenTriplePendulum;

/**
 * Created by strange on 07/10/15.
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class Driv3PendY extends Applet
        implements Runnable, ActionListener, ItemListener
{
    private static final int Lx = 340, Ly = 340; //graphic window
    private int px1, px2, px3, py1, py2, py3, pa; //pixelcoordinates
    private double phi1, omega1; //coordinates and velocities
    private double phi2, omega2; //coordinates and velocities
    private double phi3, omega3; //coordinates and velocities
    private double a, ap, aforce; //coordinates and velocities
    private double freq, amp; //frequency and amplitude
    private int reib; //friction
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0004; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double k3[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double l3[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[2]; //save initial values
    private int step;
    Canvas can;
    Button but_reib_plus, but_reib_min;
    Choice Chooser;
    Checkbox cb_stop;
    Thread animator;
    private Graphics g, offGraphics;
    private Image offImage;

    public void init()
    {
        setLayout(null);
        can = new Canvas();
        can.setBounds(1,1,Lx+100,Ly);
        can.setBackground(Color.white);
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setBounds(Lx+101,1,100,Ly);
        pan.setBackground(new Color(0,200,100));
        pan.setFont(new Font("Verdana",Font.PLAIN,10));

        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        cb_stop = new Checkbox("Stop/Go", false);
        Choice Chooser = new Choice();
        Chooser.add("Values_1");
        Chooser.add("Values_2");
        Chooser.add("Values_3");
        Chooser.add("Values_4");

        Chooser.setBounds(10,30,80,30);
        but_reib_plus.setBounds(10,70,80,27);
        but_reib_min.setBounds(10,100,80,27);
        cb_stop.setBounds(10,140,80,30);
        cb_stop.setBackground(new Color(0,200,100));

        pan.add(Chooser); pan.add(cb_stop);
        pan.add(but_reib_plus); pan.add(but_reib_min);
        add(can); add(pan);

        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
        Chooser.addItemListener(this);

        g = can.getGraphics();
        g.setFont(new Font("Verdana",Font.BOLD,10));

        startwert[0] = 0.05; //amp
        startwert[1] = 250.; //freq
        phi1 = Math.PI/180.*165.;
        phi2 = Math.PI/180.*165.;
        phi3 = Math.PI/180.*165.;
    }//init()

    public void startwerte()  //method for setting initial values
    {
        amp = startwert[0];
        freq = startwert[1];
        omega1 = 0.0;
        omega2 = 0.0;
        omega3 = 0.0;
        step = 0;
    }//startwerte()

    public void treibwerte()  //driving values
    {
        a = amp*Math.cos(freq*step*dt);
        ap = -amp*freq*Math.sin(freq*step*dt);
        aforce = -freq*freq*a;
    }//treibwerte()

    public void run()
    {
        try{Thread.sleep(100);} catch(InterruptedException e){}
        startwerte();
        treibwerte();

        while(Thread.currentThread() == animator)
        {
            if(cb_stop.getState()) //stop/go
            {try{Thread.sleep(10);}catch(InterruptedException e){}continue;}

            treibwerte(); //new a, ap, aforce
            runge_step_phi1(); //Runge-Kutta
            runge_step_phi2(); //Runge-Kutta
            runge_step_phi3(); //Runge-Kutta
            phi1 = phi1 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //Runge-Kutta
            omega1 = omega1 + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //Runge-Kutta
            phi2 = phi2 + (k2[0]+2*k2[1]+2*k2[2]+k2[3])/6; //Runge-Kutta
            omega2 = omega2 + (l2[0]+2*l2[1]+2*l2[2]+l2[3])/6; //Runge-Kutta
            phi3 = phi3 + (k3[0]+2*k3[1]+2*k3[2]+k3[3])/6; //Runge-Kutta
            omega3 = omega3 + (l3[0]+2*l3[1]+2*l3[2]+l3[3])/6; //Runge-Kutta

            if(step % 14 == 0) //paint frame not so often
            {
                try{Thread.sleep(5);} catch(InterruptedException e){}
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
            gr = can.getGraphics(); //initialize graphic windows again
            gr.drawImage(offImage, 0, 0, null);
        }
    } //paint(gr)

    public void update(Graphics gr)  //double buffer
    {
        gr = can.getGraphics(); //initialize graphic windows again
        if(offGraphics == null)
        {
            offImage = createImage(Lx+100,Ly);
            offGraphics = offImage.getGraphics();
        }
        offGraphics.setFont(new Font("Verdana",Font.BOLD,10));
        offGraphics.setColor(can.getBackground());
        offGraphics.fillRect(0, 0, Lx+100, Ly);
        paintFrame(offGraphics);
        gr.drawImage(offImage, 0, 0, null);
    } //update(gr)

    public void paintFrame(Graphics gr)
    {
        print(gr); //print values
        gr.drawLine(0,Ly/2,Lx,Ly/2);
        gr.drawLine(Lx/2,0,Lx/2,Ly);
        gr.setColor(Color.black);
        gr.drawString("Step " + step, 20, 30);
        gr.setColor(Color.red);
        gr.drawLine(Lx/2,pa,px1,py1);
        gr.drawLine(px1,py1,px2,py2);
        gr.drawLine(px2,py2,px3,py3);
        gr.setColor(Color.black);
        gr.fillOval(px1-5,py1-5,10,10);
        gr.fillOval(px2-5,py2-5,10,10);
        gr.fillOval(px3-5,py3-5,10,10);
        gr.fillRect(Lx/2-1,pa-4,3,8);
    } //paintFrame(gr)

    public void print(Graphics gr)
    {
        gr.setColor(Color.blue);
        gr.drawString("Energy:",Lx+20,40);
        gr.drawString("" +(double)Math.round(10*energy())/10,Lx+20,55);
        gr.drawString("Friction:",Lx+20,Ly/2+55);
        gr.drawString("" +reib,Lx+20,Ly/2+70);
        gr.drawString("Amplitude:",Lx+20,Ly/2+95);
        gr.drawString("" +(double)Math.round(100*amp)/100,Lx+20,Ly/2+110);
        gr.drawString("Frequency:",Lx+20,Ly/2+135);
        gr.drawString("" +(double)Math.round(100*freq)/100,Lx+20,Ly/2+150);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoords
    {
        pa = (int)Math.round((double)(Ly/2) + 50*a);
        px1 = (int)Math.round((double)(Lx/2) + 50*Math.sin(phi1));
        py1 = (int)Math.round((double)pa + 50*Math.cos(phi1));
        px2 = (int)Math.round((double)px1 + 50*Math.sin(phi2));
        py2 = (int)Math.round((double)py1 + 50*Math.cos(phi2));
        px3 = (int)Math.round((double)px2 + 50*Math.sin(phi3));
        py3 = (int)Math.round((double)py2 + 50*Math.cos(phi3));
    }//pixels()

    /* force on Phi_1 */
    public double forcephi1(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3   )
    {
        return (-10*(aforce - grav)*Math.sin(phi1)
                - 4*(aforce - grav)*Math.sin(phi1 - 2*phi2)
                + 8*omega2*omega2*Math.sin(phi1 - phi2)
                + 4*omega1*omega1*Math.sin(2*(phi1 - phi2))
                + aforce*Math.sin(phi1 + 2*phi2 - 2*phi3)
                - grav*Math.sin(phi1 + 2*phi2 - 2*phi3)
                + 2*omega3*omega3*Math.sin(phi1 - phi3)
                + 2*omega3*omega3*Math.sin(phi1 - 2*phi2 + phi3)
                + aforce*Math.sin(phi1 - 2*phi2 + 2*phi3)
                - grav*Math.sin(phi1 - 2*phi2 + 2*phi3))/(2*(-5
                + 2*Math.cos(2*(phi1 - phi2)) + Math.cos(2*(phi2 - phi3))))
                - 0.1*reib*omega1;
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3   )
    {
        return (-14*omega1*omega1*Math.sin(phi1 - phi2)
                - 4*omega2*omega2*Math.sin(2*(phi1 - phi2))
                + 7*aforce*Math.sin(2*phi1 - phi2)
                - 7*grav*Math.sin(2*phi1 - phi2) - 7*aforce*Math.sin(phi2)
                + 7*grav*Math.sin(phi2) - aforce*Math.sin(phi2 - 2*phi3)
                + grav*Math.sin(phi2 - 2*phi3)
                + 2*omega1*omega1*Math.sin(phi1 + phi2 - 2*phi3)
                - aforce*Math.sin(2*phi1 + phi2 - 2*phi3)
                + grav*Math.sin(2*phi1 + phi2 - 2*phi3)
                - 2*omega3*omega3*Math.sin(2*phi1 - phi2 - phi3)
                + 6*omega3*omega3*Math.sin(phi2 - phi3)
                + 2*omega2*omega2*Math.sin(2*(phi2 - phi3)))/(2*(-5
                + 2*Math.cos(2*(phi1 - phi2)) + Math.cos(2*(phi2 - phi3))))
                - 0.1*reib*omega2;
    }//forcephi2()

    /* force on Phi_3 */
    public double forcephi3(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3   )
    {
        return (-2*(2*omega2*omega2 + 2*omega1*omega1*Math.cos(phi1 - phi2)
                + (-aforce + grav)*Math.cos(2*phi1 - phi2)
                - aforce*Math.cos(phi2) + grav*Math.cos(phi2)
                + omega3*omega3*Math.cos(phi2 - phi3))*Math.sin(phi2 - phi3))/(
                - 5 + 2*Math.cos(2*(phi1 - phi2)) + Math.cos(2*(phi2 - phi3)))
                - 0.1*reib*omega3;
    }//forcephi3()

    public double energy()
    {
        return (3*ap*ap - 6*a*grav + 3*omega1*omega1 + 2*omega2*omega2
                + omega3*omega3 - 6*grav*Math.cos(phi1)
                + 4*omega1*omega2*Math.cos(phi1 - phi2)
                - 4*grav*Math.cos(phi2) + 2*omega1*omega3*Math.cos(phi1 - phi3)
                + 2*omega2*omega3*Math.cos(phi2 - phi3) - 2*grav*Math.cos(phi3)
                - 6*ap*omega1*Math.sin(phi1) - 4*ap*omega2*Math.sin(phi2)
                - 2*ap*omega3*Math.sin(phi3))/2;
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
        if(evt.getActionCommand()==but_reib_plus.getActionCommand())
        {
            reib++; if(cb_stop.getState()){ update(g); }
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
            startwert[0] = 0.05; //amp
            startwert[1] = 250.;  //freq
            phi1 = Math.PI/180.*165.;
            phi2 = Math.PI/180.*165.;
            phi3 = Math.PI/180.*165.;
            startwerte();
            treibwerte();
            pixels();
            update(g);
            start();
        }
        if(evt.getItem() == "Values_2")
        {
            stop();
            startwert[0] = 0.05; //amp
            startwert[1] = 500.;  //freq
            phi1 = Math.PI/180.*145.;
            phi2 = Math.PI/180.*145.;
            phi3 = Math.PI/180.*145.;
            startwerte();
            treibwerte();
            pixels();
            update(g);
            start();
        }
        if(evt.getItem() == "Values_3")
        {
            stop();
            startwert[0] = 0.05; //amp
            startwert[1] = 300.;  //freq
            phi1 = Math.PI/180.*165.;
            phi2 = Math.PI/180.*185.;
            phi3 = Math.PI/180.*195.;
            startwerte();
            treibwerte();
            pixels();
            update(g);
            start();
        }
        if(evt.getItem() == "Values_4")
        {
            stop();
            startwert[0] = 0.5; //amp
            startwert[1] = 1.;  //freq
            phi1 = Math.PI/180.*175.;
            phi2 = Math.PI/180.*175.;
            phi3 = Math.PI/180.*175.;
            startwerte();
            treibwerte();
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
     //   offImage = null;
     //   offGraphics = null;
    }//stop()
}

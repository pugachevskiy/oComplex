package com.openComplex.app.DynamicalSystems.Pendulums.QuadBarPendulum;

/**
 * Created by strange on 07/10/15.
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class QuadBarPend extends Applet implements Runnable, ActionListener
{
    private static final int Lx = 500, Ly = 330; //graphic window
    private int xPoints[] = new int[5]; //pixelcoordinates
    private int yPoints[] = new int[5]; //pixelcoordinates
    private double phi1, omega1; //coordinates and velocities
    private double phi2, omega2; //coordinates and velocities
    private double phi3, omega3; //coordinates and velocities
    private double phi4, omega4; //coordinates and velocities
    private int reib; //friction
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double k3[] = new double[4]; //Runge-Kutta
    private double k4[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double l3[] = new double[4]; //Runge-Kutta
    private double l4[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[4]; //save initial values
    private int startwert_reib; //save initial friction
    private int step;
    Canvas can;
    Button but_new;
    Button but_phi1_plus, but_phi1_min;
    Button but_phi2_plus, but_phi2_min;
    Button but_phi3_plus, but_phi3_min;
    Button but_phi4_plus, but_phi4_min;
    Button but_reib_plus, but_reib_min;
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
        but_phi3_plus = new Button("Phi3 +");
        but_phi3_min = new Button("Phi3 -");
        but_phi4_plus = new Button("Phi4 +");
        but_phi4_min = new Button("Phi4 -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        cb_stop = new Checkbox("Stop/Go", false);

        but_new.setBounds(10,15,80,25);
        but_phi1_plus.setBounds(10,55,80,25);
        but_phi1_min.setBounds(10,85,80,25);
        but_phi2_plus.setBounds(10,120,80,25);
        but_phi2_min.setBounds(10,150,80,25);
        but_phi3_plus.setBounds(10,185,80,25);
        but_phi3_min.setBounds(10,215,80,25);
        but_phi4_plus.setBounds(10,250,80,25);
        but_phi4_min.setBounds(10,280,80,25);
        but_reib_plus.setBounds(10,315,80,25);
        but_reib_min.setBounds(10,345,80,25);
        cb_stop.setBounds(10,380,70,27);
        cb_stop.setBackground(new Color(0,200,100));

        pan.add(but_new); pan.add(cb_stop);
        pan.add(but_phi1_plus); pan.add(but_phi1_min);
        pan.add(but_phi2_plus); pan.add(but_phi2_min);
        pan.add(but_phi3_plus); pan.add(but_phi3_min);
        pan.add(but_phi4_plus); pan.add(but_phi4_min);
        pan.add(but_reib_plus); pan.add(but_reib_min);
        add(header); add(pan); add(can);

        but_new.addActionListener(this);
        but_phi1_plus.addActionListener(this);
        but_phi1_min.addActionListener(this);
        but_phi2_plus.addActionListener(this);
        but_phi2_min.addActionListener(this);
        but_phi3_plus.addActionListener(this);
        but_phi3_min.addActionListener(this);
        but_phi4_plus.addActionListener(this);
        but_phi4_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,15));
        g = can.getGraphics();

        startwert[0] = Math.PI/180*100.; //phi1
        startwert[1] = Math.PI/180*110.; //phi2
        startwert[2] = Math.PI/180*120.; //phi3
        startwert[3] = Math.PI/180*130.; //phi4
        startwert_reib = 0; //reib

        xPoints[0] = Lx/2; yPoints[0] = Ly/2; //mounting point
    }//init()

    public void startwerte()
    {
        phi1 = startwert[0]; omega1 = 0.0;
        phi2 = startwert[1]; omega2 = 0.0;
        phi3 = startwert[2]; omega3 = 0.0;
        phi4 = startwert[3]; omega4 = 0.0;
        reib = startwert_reib;
        step = 0;
    }//startwerte()

    public void run()
    {
        try{Thread.sleep(500);} catch(InterruptedException e){}
        ghead.drawString("Quad-Bar-Pendulum",170,25);
        offImage = createImage(Lx,Ly+50);
        offGraphics = offImage.getGraphics();
        startwerte();

        while(Thread.currentThread() == animator)
        {
            if(cb_stop.getState()) //Stop/Go
            {try{Thread.sleep(10);}catch(InterruptedException e){}continue;}

            runge_step_phi1(); //Runge-Kutta
            runge_step_phi2(); //Runge-Kutta
            runge_step_phi3(); //Runge-Kutta
            runge_step_phi4(); //Runge-Kutta
            phi1 = phi1 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //Runge-Kutta
            omega1 = omega1 + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //Runge-Kutta
            phi2 = phi2 + (k2[0]+2*k2[1]+2*k2[2]+k2[3])/6; //Runge-Kutta
            omega2 = omega2 + (l2[0]+2*l2[1]+2*l2[2]+l2[3])/6; //Runge-Kutta
            phi3 = phi3 + (k3[0]+2*k3[1]+2*k3[2]+k3[3])/6; //Runge-Kutta
            omega3 = omega3 + (l3[0]+2*l3[1]+2*l3[2]+l3[3])/6; //Runge-Kutta
            phi4 = phi4 + (k4[0]+2*k4[1]+2*k4[2]+k4[3])/6; //Runge-Kutta
            omega4 = omega4 + (l4[0]+2*l4[1]+2*l4[2]+l4[3])/6; //Runge-Kutta

            if(step % 40 == 0) //paint not so often
            {
                try{Thread.sleep(8);} catch(InterruptedException e){}
                pixels(); //calculate pixelcoordinates
                paintFrame(offGraphics); //paint new frame into Image
                g.drawImage(offImage, 0, 0, null); //display Image on screen
            }
            step++;
        }//while currentThread()==animator
    }//run()

    public void paintFrame(Graphics gr)
    {
        gr.setFont(new Font("Verdana",Font.BOLD,10));
        gr.setColor(can.getBackground());
        gr.fillRect(0, 0, Lx, Ly+50);
        print(gr); //print values
        gr.drawLine(0,Ly/2,Lx,Ly/2);
        gr.drawLine(Lx/2,0,Lx/2,Ly);
        gr.setColor(Color.black);
        gr.drawString("Step " + step, 20, 10);
        gr.drawPolyline(xPoints, yPoints, 5);
    } //paintFrame(gr)

    public void print(Graphics gr) //method for printing values
    {
        gr.setColor(Color.blue);
        gr.drawString("Energy:",25,Ly+20);
        gr.drawString("" +(double)Math.round(10*energy())/10,25,Ly+40);
        gr.drawString("Friction:",100,Ly+20);
        gr.drawString("" +reib,100,Ly+40);
        gr.drawString("Phi1:",195,Ly+20);
        gr.drawString("" +(double)Math.round(10*phi1*180/Math.PI)/10 +"�",195,Ly+40);
        gr.drawString("Phi2:",270,Ly+20);
        gr.drawString("" +(double)Math.round(10*phi2*180/Math.PI)/10 +"�",270,Ly+40);
        gr.drawString("Phi3:",345,Ly+20);
        gr.drawString("" +(double)Math.round(10*phi3*180/Math.PI)/10 +"�",345,Ly+40);
        gr.drawString("Phi4:",420,Ly+20);
        gr.drawString("" +(double)Math.round(10*phi4*180/Math.PI)/10 +"�",420,Ly+40);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoords
    {
        xPoints[1] = (int)Math.round((double)xPoints[0] + 45*Math.sin(phi1));
        yPoints[1] = (int)Math.round((double)yPoints[0] + 45*Math.cos(phi1));
        xPoints[2] = (int)Math.round((double)xPoints[1] + 45*Math.sin(phi2));
        yPoints[2] = (int)Math.round((double)yPoints[1] + 45*Math.cos(phi2));
        xPoints[3] = (int)Math.round((double)xPoints[2] + 45*Math.sin(phi3));
        yPoints[3] = (int)Math.round((double)yPoints[2] + 45*Math.cos(phi3));
        xPoints[4] = (int)Math.round((double)xPoints[3] + 45*Math.sin(phi4));
        yPoints[4] = (int)Math.round((double)yPoints[3] + 45*Math.cos(phi4));
    }//pixels()

    /* force on Phi_1 */
    public double forcephi1(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3,
                                double phi4, double omega4   )
    {
        return (3*(493*grav*Math.sin(phi1) - 2*omega2*omega2*(
                -187. + 45*Math.cos(2*(phi3 - phi4)))*Math.sin(phi1 - phi2)
                +3*omega2*omega2*(-9*Math.sin(phi1 + phi2 - 2*phi3)
                +Math.sin(phi1 + phi2 - 2*phi4))
                +3*omega1*omega1*((73. - 18*Math.cos(2*(phi3 - phi4)))*Math.sin(2*(phi1 - phi2))
                -9*Math.sin(2*(phi1 - phi3)) + Math.sin(2*(phi1 - phi4)))
                +omega4*omega4*(Math.sin(phi1 - phi4)
                +27*Math.sin(phi1 - 2*phi2 + 2*phi3 - phi4)
                +6*Math.sin(phi1 - 2*phi2 + phi4) + 18*Math.sin(phi1 - 2*phi3 + phi4))
                +3*omega3*omega3*(21*Math.sin(phi1 - phi3) + 36*Math.sin(phi1 - 2*phi2 + phi3)
                -2*Math.sin(phi1 + phi3 - 2*phi4) - 3*Math.sin(phi1 - 2*phi2 - phi3 + 2*phi4))
                +3*grav*(73*Math.sin(phi1 - 2*phi2) - 9*Math.sin(phi1 - 2*phi3)
                -27*Math.sin(phi1 + 2*phi2 - 2*phi3) - 27*Math.sin(phi1 - 2*phi2 + 2*phi3)
                -9*Math.sin(phi1 - 2*(phi2 + phi3 - phi4)) + Math.sin(phi1 - 2*phi4)
                +3*(Math.sin(phi1 + 2*phi2 - 2*phi4) - 7*Math.sin(phi1 + 2*phi3 - 2*phi4)
                +Math.sin(phi1 - 2*phi2 + 2*phi4) - 7*Math.sin(phi1 - 2*phi3 + 2*phi4)
                -3*Math.sin(phi1 - 2*(phi2 - phi3 + phi4)))))) /
                (-1310. + 657*Math.cos(2*(phi1 - phi2)) - 81*Math.cos(2*(phi1 - phi3))
                        +405*Math.cos(2*(phi2 - phi3)) + 9*Math.cos(2*(phi1 - phi4))
                        -45*Math.cos(2*(phi2 - phi4)) + 333*Math.cos(2*(phi3 - phi4))
                        -81*Math.cos(2*(phi1 - phi2 + phi3 - phi4))
                        -81*Math.cos(2*(phi1 - phi2 - phi3 + phi4))) - 0.1*reib*omega1;
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3,
                                double phi4, double omega4   )
    {
        return (-3*(758*omega1*omega1*Math.sin(phi1 - phi2)
                -18*Math.cos(2*(phi3 - phi4))*(11*omega1*omega1*Math.sin(phi1 - phi2)
                +3*omega2*omega2*Math.sin(2*(phi1 - phi2))
                +6*grav*Math.sin(2*phi1 - phi2) - 5*grav*Math.sin(phi2))
                +15*omega1*omega1*(-9*Math.sin(phi1 + phi2 - 2*phi3)
                +Math.sin(phi1 + phi2 - 2*phi4)) + grav*(411*Math.sin(2*phi1 - phi2)
                -347*Math.sin(phi2) - 54*Math.sin(phi2 - 2*phi3)
                -81*Math.sin(2*phi1 + phi2 - 2*phi3) + 6*Math.sin(phi2 - 2*phi4)
                +9*Math.sin(2*phi1 + phi2 - 2*phi4))
                +3*omega3*omega3*(36*Math.sin(2*phi1 - phi2 - phi3)
                -3*(37*Math.sin(phi2 - phi3) + Math.sin(2*phi1 - phi2 + phi3 - 2*phi4))
                +8*Math.sin(phi2 + phi3 - 2*phi4))
                +3*omega2*omega2*(73*Math.sin(2*(phi1 - phi2))
                +5*(-9*Math.sin(2*(phi2 - phi3)) + Math.sin(2*(phi2 - phi4))))
                +omega4*omega4*(6*Math.sin(2*phi1 - phi2 - phi4)
                -31*Math.sin(phi2 - phi4) + 27*Math.sin(2*phi1 - phi2 - 2*phi3 + phi4)
                -72*Math.sin(phi2 - 2*phi3 + phi4)))) /
                (-1310. + 657*Math.cos(2*(phi1 - phi2))
                        -81*Math.cos(2*(phi1 - phi3)) + 405*Math.cos(2*(phi2 - phi3))
                        +9*Math.cos(2*(phi1 - phi4)) - 45*Math.cos(2*(phi2 - phi4))
                        +333*Math.cos(2*(phi3 - phi4))
                        -81*Math.cos(2*(phi1 - phi2 + phi3 - phi4))
                        -81*Math.cos(2*(phi1 - phi2 - phi3 + phi4))) - 0.1*reib*omega2;
    }//forcephi2()

    /* force on Phi_3 */
    public double forcephi3(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3,
                                double phi4, double omega4   )
    {
        return (3*(3*omega2*omega2*(18*Math.sin(2*phi1 - phi2 - phi3)
                -3*(49*Math.sin(phi2 - phi3) + Math.sin(2*phi1 - phi2 + phi3 - 2*phi4))
                +22*Math.sin(phi2 + phi3 - 2*phi4))
                +omega4*omega4*(9*Math.sin(2*phi1 - phi3 - phi4)
                -45*Math.sin(2*phi2 - phi3 - phi4) + 14*(
                + 17. - 9*Math.cos(2*(phi1 - phi2)))*Math.sin(phi3 - phi4))
                +3*omega3*omega3*(9*Math.sin(2*(phi1 - phi3))
                -45*Math.sin(2*(phi2 - phi3)) + (
                + 37. -18*Math.cos(2*(phi1 - phi2)))*Math.sin(2*(phi3 - phi4)))
                +3*omega1*omega1*(-39*Math.sin(phi1 - phi3)
                +90*Math.sin(phi1 - 2*phi2 + phi3) + 4*Math.sin(phi1 + phi3 - 2*phi4)
                -15*Math.sin(phi1 - 2*phi2 - phi3 + 2*phi4))
                +3*grav*(-27*Math.sin(2*phi1 - phi3) - 36*Math.sin(2*phi2 - phi3)
                +12*Math.sin(phi3) + 54*Math.sin(2*phi1 - 2*phi2 + phi3)
                +Math.sin(phi3 - 2*phi4) + 3*Math.sin(2*phi1 + phi3 - 2*phi4)
                +6*Math.sin(2*phi2 + phi3 - 2*phi4)
                -9*Math.sin(2*phi1 - 2*phi2 - phi3 + 2*phi4)))) /
                (-1310. + 657*Math.cos(2*(phi1 - phi2))
                        -81*Math.cos(2*(phi1 - phi3)) + 405*Math.cos(2*(phi2 - phi3))
                        +9*Math.cos(2*(phi1 - phi4)) - 45*Math.cos(2*(phi2 - phi4))
                        +333*Math.cos(2*(phi3 - phi4))
                        -81*Math.cos(2*(phi1 - phi2 + phi3 - phi4))
                        -81*Math.cos(2*(phi1 - phi2 - phi3 + phi4))) - 0.1*reib*omega3;
    }//forcephi3()

    /* force on Phi_4 */
    public double forcephi4(	double phi1, double omega1,
                                double phi2, double omega2,
                                double phi3, double omega3,
                                double phi4, double omega4   )
    {
        return (-3*(omega3*omega3*(9*Math.sin(2*phi1 - phi3 - phi4)
                -45*Math.sin(2*phi2 - phi3 - phi4) + 2*(
                +251. - 117*Math.cos(2*(phi1 - phi2)))*Math.sin(phi3 - phi4))
                +3*omega4*omega4*(Math.sin(2*(phi1 - phi4)) - 5*Math.sin(2*(phi2 - phi4))
                +(37. - 18*Math.cos(2*(phi1 - phi2)))*Math.sin(2*(phi3 - phi4)))
                +omega1*omega1*(Math.sin(phi1 - phi4)
                +135*Math.sin(phi1 - 2*phi2 + 2*phi3 - phi4)
                -60*Math.sin(phi1 - 2*phi2 + phi4)
                -36*Math.sin(phi1 - 2*phi3 + phi4))
                +omega2*omega2*(-12*Math.sin(2*phi1 - phi2 - phi4)
                +73*Math.sin(phi2 - phi4) + 27*Math.sin(2*phi1 - phi2 - 2*phi3 + phi4)
                -198*Math.sin(phi2 - 2*phi3 + phi4)) + grav*(3*Math.sin(2*phi1 - phi4)
                +24*Math.sin(2*phi2 - phi4) + 9*Math.sin(2*phi3 - phi4)
                +81*Math.sin(2*(phi1 - phi2 + phi3) - phi4) + 2*Math.sin(phi4)
                -9*(4*Math.sin(2*phi1 - 2*phi2 + phi4) + 3*Math.sin(2*phi1 - 2*phi3 + phi4)
                +6*Math.sin(2*phi2 - 2*phi3 + phi4))))) /
                (-1310. + 657*Math.cos(2*(phi1 - phi2))
                        -81*Math.cos(2*(phi1 - phi3)) + 405*Math.cos(2*(phi2 - phi3))
                        +9*Math.cos(2*(phi1 - phi4)) - 45*Math.cos(2*(phi2 - phi4))
                        +333*Math.cos(2*(phi3 - phi4)) - 81*Math.cos(2*(phi1 - phi2 + phi3 - phi4))
                        -81*Math.cos(2*(phi1 - phi2 - phi3 + phi4))) - 0.1*reib*omega4;
    }//forcephi4()

    public double energy()
    {
        return (10*omega1*omega1 + 7*omega2*omega2 + 4*omega3*omega3 + omega4*omega4
                +3*(5*omega1*omega2*Math.cos(phi1 - phi2)
                +3*omega1*omega3*Math.cos(phi1 - phi3)
                +3*omega2*omega3*Math.cos(phi2 - phi3)
                +omega1*omega4*Math.cos(phi1 - phi4) + omega2*omega4*Math.cos(phi2 - phi4)
                +omega3*omega4*Math.cos(phi3 - phi4)) - 3*grav*(7*Math.cos(phi1)
                +5*Math.cos(phi2) + 3*Math.cos(phi3) + Math.cos(phi4)))/6.;
    }//energy()

    public void runge_step_phi1()
    {
        k1[0] = dt*omega1;
        l1[0] = dt*forcephi1(phi1,omega1,phi2,omega2,phi3,omega3,phi4,omega4);
        k1[1] = dt*(omega1+l1[0]/2);
        l1[1] = dt*forcephi1(phi1+k1[0]/2,omega1+l1[0]/2,phi2,omega2,phi3,omega3,phi4,omega4);
        k1[2] = dt*(omega1+l1[1]/2);
        l1[2] = dt*forcephi1(phi1+k1[1]/2,omega1+l1[1]/2,phi2,omega2,phi3,omega3,phi4,omega4);
        k1[3] = dt*(omega1+l1[2]);
        l1[3] = dt*forcephi1(phi1+k1[2],omega1+l1[2],phi2,omega2,phi3,omega3,phi4,omega4);
    }//runge_step_phi1()

    public void runge_step_phi2()
    {
        k2[0] = dt*omega2;
        l2[0] = dt*forcephi2(phi1,omega1,phi2,omega2,phi3,omega3,phi4,omega4);
        k2[1] = dt*(omega2+l2[0]/2);
        l2[1] = dt*forcephi2(phi1,omega1,phi2+k2[0]/2,omega2+l2[0]/2,phi3,omega3,phi4,omega4);
        k2[2] = dt*(omega2+l2[1]/2);
        l2[2] = dt*forcephi2(phi1,omega1,phi2+k2[1]/2,omega2+l2[1]/2,phi3,omega3,phi4,omega4);
        k2[3] = dt*(omega2+l2[2]);
        l2[3] = dt*forcephi2(phi1,omega1,phi2+k2[2],omega2+l2[2],phi3,omega3,phi4,omega4);
    }//runge_step_phi2()

    public void runge_step_phi3()
    {
        k3[0] = dt*omega3;
        l3[0] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3,omega3,phi4,omega4);
        k3[1] = dt*(omega3+l3[0]/2);
        l3[1] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3+k3[0]/2,omega3+l3[0]/2,phi4,omega4);
        k3[2] = dt*(omega3+l3[1]/2);
        l3[2] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3+k3[1]/2,omega3+l3[1]/2,phi4,omega4);
        k3[3] = dt*(omega3+l3[2]);
        l3[3] = dt*forcephi3(phi1,omega1,phi2,omega2,phi3+k3[2],omega3+l3[2],phi4,omega4);
    }//runge_step_phi3()

    public void runge_step_phi4()
    {
        k4[0] = dt*omega4;
        l4[0] = dt*forcephi4(phi1,omega1,phi2,omega2,phi3,omega3,phi4,omega4);
        k4[1] = dt*(omega4+l4[0]/2);
        l4[1] = dt*forcephi4(phi1,omega1,phi2,omega2,phi3,omega3,phi4+k4[0]/2,omega4+l4[0]/2);
        k4[2] = dt*(omega4+l4[1]/2);
        l4[2] = dt*forcephi4(phi1,omega1,phi2,omega2,phi3,omega3,phi4+k4[1]/2,omega4+l4[1]/2);
        k4[3] = dt*(omega4+l4[2]);
        l4[3] = dt*forcephi4(phi1,omega1,phi2,omega2,phi3,omega3,phi4+k4[2],omega4+l4[2]);
    }//runge_step_phi4()

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
        if(evt.getActionCommand()==but_phi3_plus.getActionCommand())
        {
            stop();
            startwert[2] = startwert[2] + Math.PI/180*5;
            startwerte();
            pixels();
            paintFrame(g);
            start();
        }
        if(evt.getActionCommand()==but_phi3_min.getActionCommand())
        {
            stop();
            startwert[2] = startwert[2] - Math.PI/180*5;
            startwerte();
            pixels();
            paintFrame(g);
            start();
        }
        if(evt.getActionCommand()==but_phi4_plus.getActionCommand())
        {
            stop();
            startwert[3] = startwert[3] + Math.PI/180*5;
            startwerte();
            pixels();
            paintFrame(g);
            start();
        }
        if(evt.getActionCommand()==but_phi4_min.getActionCommand())
        {
            stop();
            startwert[3] = startwert[3] - Math.PI/180*5;
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
    }//actionPerformed(evt)

    public void start()
    {
        animator = new Thread(this);
        animator.start();
    }//start()

    public void stop()
    {
        animator = null;
        //offGraphics = null;
       // offImage = null;
    }//stop()
}

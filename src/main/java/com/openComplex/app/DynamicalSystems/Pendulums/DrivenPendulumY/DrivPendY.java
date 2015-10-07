package com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulumY;

/**
 * Created by strange on 07/10/15.
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class DrivPendY extends Applet
        implements Runnable, ActionListener, ItemListener
{
    private static final int Lx = 320, Ly = 320; //graphic window
    private int px, py, pa; //pixelcoordinates
    private double phi, omega; //coordinates and velocities
    private double a, ap, aforce; //coordinates and velocities
    private double freq, amp; //frequency und amplitude
    private int reib; //friction
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k[] = new double[4]; //Runge-Kutta
    private double l[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[3]; //save initial values
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

        Chooser.setBounds(10,40,80,30);
        but_reib_plus.setBounds(10,80,80,27);
        but_reib_min.setBounds(10,110,80,27);
        cb_stop.setBounds(10,150,80,30);
        cb_stop.setBackground(new Color(0,200,100));

        pan.add(Chooser); pan.add(cb_stop);
        pan.add(but_reib_plus); pan.add(but_reib_min);
        add(pan); add(can);

        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
        Chooser.addItemListener(this);

        g = can.getGraphics();
        g.setFont(new Font("Verdana",Font.BOLD,10));

        startwert[0] = 0.5; //amp
        startwert[1] = 1.; //freq
        startwert[2] = Math.PI/180.*178.; //phi
    }//init()

    public void startwerte()  //method for setting initial values
    {
        amp = startwert[0];
        freq = startwert[1];
        phi = startwert[2];
        omega = 0.0;
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
            runge_step_phi(); //Runge-Kutta
            phi = phi + (k[0]+2*k[1]+2*k[2]+k[3])/6; //Runge-Kutta
            omega = omega + (l[0]+2*l[1]+2*l[2]+l[3])/6; //Runge-Kutta

            if(step % 10 == 0) //paint not so often
            {
                try{Thread.sleep(5);} catch(InterruptedException e){}
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
        gr.drawLine(Lx/2,pa,px,py);
        gr.setColor(Color.black);
        gr.fillOval(px-7,py-7,15,15);
        gr.fillRect(Lx/2-1,pa-4,3,9);
    } //paintFrame(gr)

    public void print(Graphics gr)
    {
        gr.setColor(Color.blue);
        gr.drawString("Energy:",Lx+20,50);
        gr.drawString("" +(double)Math.round(10*energy())/10,Lx+20,65);
        gr.drawString("Friction:",Lx+20,Ly/2+35);
        gr.drawString("" +reib,Lx+20,Ly/2+50);
        gr.drawString("Amplitude:",Lx+20,Ly/2+80);
        gr.drawString("" +(double)Math.round(100*amp)/100,Lx+20,Ly/2+95);
        gr.drawString("Frequency:",Lx+20,Ly/2+125);
        gr.drawString("" +(double)Math.round(100*freq)/100,Lx+20,Ly/2+140);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoords
    {
        pa = (int)Math.round((double)(Ly/2) + 90*a);
        px = (int)Math.round((double)(Lx/2) + 90*Math.sin(phi));
        py = (int)Math.round((double)pa + 90*Math.cos(phi));
    }//pixels()

    /* force on Phi */
    public double forcephi(double phi, double omega)
    {
        return (-grav +aforce)*Math.sin(phi) -0.1*reib*omega;
    }//forcephi()

    public double energy()
    {
        return 	0.5*omega*omega +0.5*ap*ap -omega*ap*Math.sin(phi)
                - grav*Math.cos(phi) - grav*a;
    }//energy()

    public void runge_step_phi()
    {
        k[0] = dt*omega;
        l[0] = dt*forcephi(phi, omega);
        k[1] = dt*(omega+l[0]/2);
        l[1] = dt*forcephi(phi+k[0]/2, omega+l[0]/2);
        k[2] = dt*(omega+l[1]/2);
        l[2] = dt*forcephi(phi+k[1]/2, omega+l[1]/2);
        k[3] = dt*(omega+l[2]);
        l[3] = dt*forcephi(phi+k[2], omega+l[2]);
    }//runge_step_phi()

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getActionCommand()==but_reib_plus.getActionCommand())
        {
            reib++; if(cb_stop.getState()) { update(g); }
        }
        if(evt.getActionCommand()==but_reib_min.getActionCommand())
        {
            if(reib > 0)
            { reib--; if(cb_stop.getState()){ update(g); } }
        }
    }//actionPerformed(evt)

    public void itemStateChanged(ItemEvent evt) //Choice Objekt
    {
        if(evt.getItem() == "Values_1")
        {
            stop();
            startwert[0] = 0.5; //amp
            startwert[1] = 1.;  //freq
            startwert[2] = Math.PI/180.*178.; //phi
            startwerte();
            treibwerte();
            pixels();
            update(g);
            start();
        }
        if(evt.getItem() == "Values_2")
        {
            stop();
            startwert[0] = 0.5; //amp
            startwert[1] = 15.;  //freq
            startwert[2] = Math.PI/180.*178.; //phi
            startwerte();
            treibwerte();
            pixels();
            update(g);
            start();
        }
        if(evt.getItem() == "Values_3")
        {
            stop();
            startwert[0] = 0.1; //amp
            startwert[1] = 80.;  //freq
            startwert[2] = Math.PI/180.*120.; //phi
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
      //  offImage = null;
      //  offGraphics = null;
    }//stop()
}

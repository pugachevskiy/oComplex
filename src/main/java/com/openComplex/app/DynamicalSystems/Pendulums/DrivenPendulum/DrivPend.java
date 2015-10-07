package com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulum;

/**
 * Created by strange on 07/10/15.
 */
import java.awt.*;
import java.applet.*;
import java.awt.event.*;

public class DrivPend extends Applet implements Runnable, ActionListener
{
    private static final int Lx = 450, Ly = 220; //graphic window
    private int px, py, pa; //pixelcoordinates
    private double phi, omega; //coordinates and velocities
    private double a, ap, aforce; //coordinates and velocities
    private double freq, amp; //frequency and amplitude
    private int reib; //friction
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0007; //Runge-Kutta-timestep
    private double k[] = new double[4]; //Runge-Kutta
    private double l[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[2]; //save initial values
    private int startwert_reib; //save initial friction
    private int step;
    Canvas can, header;
    Button but_new;
    Button but_amp_plus, but_amp_min;
    Button but_freq_plus, but_freq_min;
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
        but_amp_plus = new Button("Amp +");
        but_amp_min = new Button("Amp -");
        but_freq_plus = new Button("Freq +");
        but_freq_min = new Button("Freq -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        cb_stop = new Checkbox("Stop/Go", false);

        but_new.setBounds(10,25,80,27);
        but_amp_plus.setBounds(10,70,80,27);
        but_amp_min.setBounds(10,100,80,27);
        but_freq_plus.setBounds(10,135,80,27);
        but_freq_min.setBounds(10,165,80,27);
        but_reib_plus.setBounds(10,200,80,27);
        but_reib_min.setBounds(10,230,80,27);
        cb_stop.setBounds(10,270,80,30);
        cb_stop.setBackground(new Color(0,200,100));

        pan.add(but_new); pan.add(cb_stop);
        pan.add(but_amp_plus); pan.add(but_amp_min);
        pan.add(but_freq_plus); pan.add(but_freq_min);
        pan.add(but_reib_plus); pan.add(but_reib_min);
        add(pan); add(can); add(header);

        but_new.addActionListener(this);
        but_amp_plus.addActionListener(this);
        but_amp_min.addActionListener(this);
        but_freq_plus.addActionListener(this);
        but_freq_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana",Font.BOLD,15));
        g = can.getGraphics();
        g.setFont(new Font("Verdana",Font.BOLD,10));

        startwert[0] = 0.5; //amp
        startwert[1] = 3.0; //freq
        startwert_reib = 0; //reib
    }//init()

    public void startwerte()  //method for setting initial values
    {
        phi = 0.0;
        omega = 0.0;
        reib = startwert_reib;
        step = 0;
        amp = startwert[0];
        freq = startwert[1];
    }//startwerte()

    public void treibwerte()  //driving values
    {
        a = amp*Math.cos(freq*step*dt);
        ap = -amp*freq*Math.sin(freq*step*dt);
        aforce = -freq*freq*a;
    }//treibwerte()

    public void run()
    {
        try{Thread.sleep(500);} catch(InterruptedException e){}
        ghead.drawString("Driven Pendulum",Lx/2-60,28);
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
        gr.drawLine(pa,Ly/2,px,py);
        gr.setColor(Color.black);
        gr.fillOval(px-7,py-7,15,15);
        gr.fillRect(pa-4,Ly/2-1,9,3);
    } //paintFrame(gr)

    public void print(Graphics gr)
    {
        gr.setColor(Color.blue);
        gr.drawString("Energy:",25,Ly+20);
        gr.drawString("" +(double)Math.round(10*energy())/10,25,Ly+40);
        gr.drawString("Friction:",105,Ly+20);
        gr.drawString("" +reib,105,Ly+40);
        gr.drawString("a:",195,Ly+20);
        gr.drawString("" +(double)Math.round(100*a)/100,195,Ly+40);
        gr.drawString("Amplitude:",265,Ly+20);
        gr.drawString("" +(double)Math.round(100*amp)/100,265,Ly+40);
        gr.drawString("Frequency:",355,Ly+20);
        gr.drawString("" +(double)Math.round(100*freq)/100,355,Ly+40);
    }//print(gr)

    public void pixels()  //method for calculating pixelcoords
    {
        pa = (int)Math.round((double)(Lx/2) + 100*a);
        px = (int)Math.round((double)(pa) + 100*Math.sin(phi));
        py = (int)Math.round((double)(Ly/2) + 100*Math.cos(phi));
    }//pixels()

    /* force on Phi */
    public double forcephi(double phi, double omega)
    {
        return - grav*Math.sin(phi) - aforce*Math.cos(phi) - 0.1*reib*omega;
    }//forcephi()

    public double energy()
    {
        return 0.5*omega*omega + 0.5*ap*ap + omega*ap*Math.cos(phi)
                - grav*Math.cos(phi);
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
        if(evt.getActionCommand()==but_new.getActionCommand())
        {
            stop();
            startwerte(); //set initial values
            treibwerte();
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if(evt.getActionCommand()==but_amp_plus.getActionCommand())
        {
            if(Math.round(startwert[0]*startwert[1]) < 3)
            {
                stop();
                startwert[0] += 0.1 ;
                startwerte();
                treibwerte();
                pixels(); //calculate pixelcoords
                update(g); //paint initial state
                start();
            }
        }
        if(evt.getActionCommand()==but_amp_min.getActionCommand())
        {
            if(startwert[0] > 0.02)
            {
                stop();
                startwert[0] -= 0.1;
                startwerte();
                treibwerte();
                pixels(); //calculate pixelcoords
                update(g); //paint initial state
                start();
            }
        }
        if(evt.getActionCommand()==but_freq_plus.getActionCommand())
        {
            if(Math.round(startwert[0]*startwert[1]) < 3)
            {
                stop();
                startwert[1] += 1.;
                startwerte();
                treibwerte();
                pixels(); //calculate pixelcoords
                update(g); //paint initial state
                start();
            }
        }
        if(evt.getActionCommand()==but_freq_min.getActionCommand())
        {
            if(startwert[1] > 0)
            {
                stop();
                startwert[1] -= 1.;
                startwerte();
                treibwerte();
                pixels(); //calculate pixelcoords
                update(g); //paint initial state
                start();
            }
        }
        if(evt.getActionCommand()==but_reib_plus.getActionCommand())
        {
            startwert_reib++;
            reib = startwert_reib;
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
        //offGraphics = null;
    }//stop()
}

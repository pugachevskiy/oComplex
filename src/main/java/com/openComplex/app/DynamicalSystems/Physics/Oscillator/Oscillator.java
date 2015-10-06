package com.openComplex.app.DynamicalSystems.Physics.Oscillator;

/**
 * Created by strange on 06/10/15.
 */
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class Oscillator extends Applet implements Runnable, ActionListener
{
    private static final int W = 400, H = 220; //graphic window
    private int px, py = H/2; //pixelcoordinates
    private double x; //position
    private double m = 1., D = 0.25; //mass, spring strength
    private double omega = Math.sqrt(2*D/m); //frequency
    private double amp = -0.8; //amplitude
    private int reib = 0; //friction
    private static final double dt = 0.05; //timestep
    private int step = 0;
    private int xPoints1[] = new int[10]; //pixelcoordinates for spring1
    private int yPoints1[] = new int[10]; //pixelcoordinates for spring1
    private int xPoints2[] = new int[10]; //pixelcoordinates for spring2
    private int yPoints2[] = new int[10]; //pixelcoordinates for spring2
    private boolean stop = false; //go/pause
    Button but_go, but_D_plus, but_D_min;
    Button but_reib_plus, but_reib_min;
    Thread animator;
    private Graphics g, dbg;
    private Image dbi;

    public void init()
    {
        setLayout(null);
        Canvas can = new Canvas();
        can.setBackground(Color.white);
        can.setBounds(1,1,W,H);
        can.addMouseListener(new ML());
        can.addMouseMotionListener(new ML());
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setBounds(W+1,1,100,H);
        pan.setBackground(new Color(0,200,100));
        pan.setFont(new Font("Verdana",Font.PLAIN,10));

        but_D_plus = new Button("Spring +");
        but_D_min = new Button("Spring -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        but_go = new Button("Go/Pause");
        but_D_plus.setBounds(10,25,80,25);
        but_D_min.setBounds(10,55,80,25);
        but_reib_plus.setBounds(10,95,80,25);
        but_reib_min.setBounds(10,125,80,25);
        but_go.setBounds(10,170,80,25);
        but_D_plus.addActionListener(this);
        but_D_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
        but_go.addActionListener(this);

        pan.add(but_go); pan.add(but_D_plus); pan.add(but_D_min);
        pan.add(but_reib_plus); pan.add(but_reib_min);
        add(pan); add(can);

        g = can.getGraphics();
        dbi = createImage(W,H);
        dbg = dbi.getGraphics();
    }

    public void run()
    {
        try{Thread.sleep(200);} catch(InterruptedException exc){}
        while(Thread.currentThread() == animator)
        {
            while(stop){try{Thread.sleep(20);}catch(InterruptedException exc){}}

            try{Thread.sleep(2);} catch(InterruptedException exc){}

            x = amp*Math.exp(-0.1*reib*step*dt)*Math.cos(omega*step*dt); //new x

            pixels(); //calculate pixelcoordinates
            paint(); //paint new frame
            step++;
        }//while
    }//run

    public void startwerte()  //method for setting initial values
    {
        animator = null;
        stop = false;
        step = 0;
        omega = Math.sqrt(2*D/m);
    }//startwerte()

    public void pixels()  //method for calculating pixelcoords
    {
        int length, currentLength1, currentLength2; //spring lengths
        double d1, d2; //pixelcoordinates for springs

        length = W/2-10;
        px = (int)Math.round((double)W/2 + (length-40)*x); //mass position
        currentLength1 = px-20;
        currentLength2 = W-px-20;
        d1 = currentLength1/16.;
        d2 = currentLength2/16.;

        xPoints1[0] = 11;		       yPoints1[0] = H/2;
        xPoints1[1] = (int)(11+d1);    yPoints1[1] = H/2+7;
        xPoints1[2] = (int)(11+3*d1);  yPoints1[2] = H/2-7;
        xPoints1[3] = (int)(11+5*d1);  yPoints1[3] = H/2+7;
        xPoints1[4] = (int)(11+7*d1);  yPoints1[4] = H/2-7;
        xPoints1[5] = (int)(11+9*d1);  yPoints1[5] = H/2+7;
        xPoints1[6] = (int)(11+11*d1); yPoints1[6] = H/2-7;
        xPoints1[7] = (int)(11+13*d1); yPoints1[7] = H/2+7;
        xPoints1[8] = (int)(11+15*d1); yPoints1[8] = H/2-7;
        xPoints1[9] = px-10;		   yPoints1[9] = H/2;

        xPoints2[0] = W-12;		         yPoints2[0] = H/2;
        xPoints2[1] = (int)(W-11-d2);    yPoints2[1] = H/2+7;
        xPoints2[2] = (int)(W-11-3*d2);  yPoints2[2] = H/2-7;
        xPoints2[3] = (int)(W-11-5*d2);  yPoints2[3] = H/2+7;
        xPoints2[4] = (int)(W-11-7*d2);  yPoints2[4] = H/2-7;
        xPoints2[5] = (int)(W-11-9*d2);  yPoints2[5] = H/2+7;
        xPoints2[6] = (int)(W-11-11*d2); yPoints2[6] = H/2-7;
        xPoints2[7] = (int)(W-11-13*d2); yPoints2[7] = H/2+7;
        xPoints2[8] = (int)(W-11-15*d2); yPoints2[8] = H/2-7;
        xPoints2[9] = px+10;		     yPoints2[9] = H/2;
    }//pixels()

    public void paint()
    {
        dbg.setColor(Color.white);
        dbg.fillRect(0,0,W,H);
        dbg.setColor(Color.black);
        dbg.drawLine(10,H/2-30,10,H/2+30);
        dbg.drawLine(W-11,H/2-30,W-11,H/2+30);
        dbg.drawPolyline(xPoints1,yPoints1,10);
        dbg.drawPolyline(xPoints2,yPoints2,10);
        dbg.fillOval(px-10,py-10,21,21);
        print();
        g.drawImage(dbi,0,0,this);
    }//paint()

    public void print()
    {
        dbg.setColor(Color.blue);
        dbg.setFont(new Font("Verdana",Font.BOLD,14));
        dbg.drawString("Drag and drop mass ...",W/2-90,28);
        dbg.setFont(new Font("Verdana",Font.BOLD,10));
        dbg.drawString("Spring force:  " + D,80,H-40);
        dbg.drawString("Friction:  " + reib,250,H-40);
    }//print()

    public void actionPerformed(ActionEvent evt)
    {
        if(evt.getActionCommand() == but_go.getActionCommand())
        { stop = !stop; }

        if(evt.getActionCommand() == but_D_plus.getActionCommand())
        {
            D = D*2; startwerte(); paint(); start();
        }
        if(evt.getActionCommand() == but_D_min.getActionCommand())
        {
            D = D/2; startwerte(); paint(); start();
        }
        if(evt.getActionCommand() == but_reib_plus.getActionCommand())
        {
            reib++; startwerte(); paint(); start();
        }
        if(evt.getActionCommand() == but_reib_min.getActionCommand())
        {
            if(reib > 0)
            { reib--; startwerte(); paint(); start(); }
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

    class ML extends MouseAdapter implements MouseMotionListener  //overwrite mouse
    {
        public void mousePressed (MouseEvent evt)
        {
            animator = null;
            stop = false;
            step = 0;
            //new amplitude
            if(evt.getX() < 40) {amp = (double)(40-W/2)/(double)(W/2-50);}
            else if(evt.getX() > W-40) {amp = (double)(W-40-W/2)/(double)(W/2-50);}
            else {amp = (double)(evt.getX()-W/2)/(double)(W/2-50);}
            x = amp; //to calculate pixelcoordinates
            pixels(); //pixelcoordinates
            paint();
        }//mousePressed

        public void mouseDragged (MouseEvent evt)
        {
            if(evt.getX() < 40) {amp = (double)(40-W/2)/(double)(W/2-50);}
            else if(evt.getX() > W-40) {amp = (double)(W-40-W/2)/(double)(W/2-50);}
            else {amp = (double)(evt.getX()-W/2)/(double)(W/2-50);}
            x = amp; //to calculate pixelcoordinates
            pixels(); //pixelcoordinates
            paint();
        }//mouseDragged

        public void mouseReleased (MouseEvent evt)
        {
            start();
        }//mouseReleased

        public void mouseMoved (MouseEvent evt)
        {  }//mouseMoved (simply implemented for Interface MouseMotionListener)

    }//ML
}
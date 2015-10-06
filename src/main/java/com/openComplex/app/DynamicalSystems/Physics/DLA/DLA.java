package com.openComplex.app.DynamicalSystems.Physics.DLA;

/**
 * Created by strange on 06/10/15.
 */
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

public class DLA extends Applet implements Runnable, ActionListener
{
    private static final int D = 256; //grid dimension, graphic window dimension
    private boolean grid[][] = new boolean[D+1][D+1]; //particle position on grid
    private int CellNum, CellSize; //resize the grid for painting
    private int step = 0;
    private int maxstep = 20000; //run-time
    Canvas can = new Canvas();
    Button but_reset;
    TextField tf_step;
    Label lab_maxstep;
    Checkbox cb_stop;
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
        pan.setBounds(D+1,1,90,D);
        pan.setBackground(new Color(0,200,100));
        pan.setFont(new Font("Verdana",Font.PLAIN,9));

        lab_maxstep = new Label("Run-time");
        lab_maxstep.setBounds(10,D/2-60,70,20);

        tf_step = new TextField("" + maxstep);
        tf_step.setBounds(10,D/2-40,70,30);
        tf_step.setFont(new Font("Verdana",Font.PLAIN,9));
        tf_step.addActionListener(this);

        but_reset = new Button("Reset");
        but_reset.setBounds(10,D/2-5,70,25);
        but_reset.addActionListener(this);

        cb_stop = new Checkbox("Stop/Go", false);
        cb_stop.setBounds(10,D/2+30,80,30);
        cb_stop.setBackground(new Color(0,200,100));

        pan.add(lab_maxstep); pan.add(tf_step); pan.add(but_reset);
        pan.add(cb_stop); add(pan); add(can);

        g = can.getGraphics();
        offImage = createImage(D,D); //double-buffer
        offGraphics = offImage.getGraphics(); //double-buffer
    }

    public void run()
    {
        try{Thread.sleep(100);} catch(InterruptedException exc){}
        double z; //random number [0,1]
        reset();
        while(Thread.currentThread() == animator)
        {
            try{Thread.sleep(0);} catch(InterruptedException e){}
            if(cb_stop.getState()) //stop/go
            {try{Thread.sleep(10);}catch(InterruptedException e){}continue;}
            if(step >= maxstep) {stop();}
            if(step % 25 == 0) //paint not so often
            {
                paintFrame(offGraphics); //paint new frame into image
                g.drawImage(offImage, 0, 0, null); //display image on screen
            }
            z = Math.random()*2*Math.PI; //choose random angle
            //perform 2-dim random walk with random start position on a circle
            RandomWalk((int)Math.round(D/2+(CellNum/2-2)*Math.cos(z)),
                    (int)Math.round(D/2+(CellNum/2-2)*Math.sin(z)));

            if(CellNum < D)
            //for rescaling the graphical grid
            {   for(int i = 0; i < D; i++)
            {   for(int j = 0; j < D; j++)
            {
                if(grid[i][j] == true)
                {
                    if(Math.round(Math.sqrt((i-D/2)*(i-D/2)+(j-D/2)*(j-D/2)))
                            == CellNum/2-2)
                    { CellNum = CellNum*2; CellSize = D/CellNum; } //rescale
                }
            }// for j
            }//for i
            }//if
            step++;
        }//while
    }//run

    public void reset()  //method for resetting the grid
    {
        step = 0;
        CellNum = 32; //start with a graphical field of 32x32 boxes
        CellSize = D/CellNum;
        for(int i = 0; i <= D; i++)
        {	for(int j = 0; j <= D; j++)
        {	grid[i][j] = false; } } //delete all cells
        grid[D/2][D/2] = true; //set mid-cell to true
    }//reset()

    public int evaluateM()  //method for evaluating cluster mass
    {
        int M = 0;

        for(int i = 0; i <= D; i++)
        {	for(int j = 0; j <= D; j++)
        { if(grid[i][j] == true) M++;
        }
        }
        return M;
    }//evaluateM()

    public int evaluateR()  //method for evaluating cluster radius
    {
        int R = 0;
        int R_old = 0;

        for(int i = 0; i <= D; i++)
        {	for(int j = 0; j <= D; j++)
        { if(grid[i][j] == true)
        { R = (int)Math.round(Math.sqrt((i-D/2)*(i-D/2)+(j-D/2)*(j-D/2)));
            if(R > R_old) {R_old = R;}
        }
        }
        }
        return R_old;
    }//evaluateR()

    public double evaluateD()  //method for evaluating fractal dimension
    {
        double D = 0;
        D = Math.round(1000.*Math.log(evaluateM())/Math.log(evaluateR()))/1000.;
        return D;
    }//evaluateD()

    /* method for doing a 2-dim random walk with starting coords i, j
       and aggregation */
    public void RandomWalk(int i, int j)
    {
        for(int n = 0; n < 32*CellNum; n++) //duration of the random walk
        {
            if(i < 1 || i > D-1 || j < 1 || j > D-1) { break; }
            else
            {
                if(grid[i][j-1] == true) {grid[i][j] = true; break;} //aggregation
                if(grid[i+1][j] == true) {grid[i][j] = true; break;} //aggregation
                if(grid[i][j+1] == true) {grid[i][j] = true; break;} //aggregation
                if(grid[i-1][j] == true) {grid[i][j] = true; break;} //aggregation

                //random walk
                if(Math.random() <= 0.5)
                {   if(Math.random() <= 0.5)
                { i++; } else i--;
                }
                else
                {   if(Math.random() <= 0.5)
                { j++; } else j--;
                }
            }
        }
    }//RandomWalk(i,j)

    public void paintFrame(Graphics g)  //method for painting the cluster
    {
        g.setFont(new Font("Verdana",Font.BOLD,10));
        g.setColor(can.getBackground());
        g.fillRect(0,0,D,D);
        g.setColor(Color.black);
        for(int i = 0; i < D; i++)
        {	for(int j = 0; j < D; j++)
        {
            if(grid[i][j] == true)
                g.fillOval((i-D/2+CellNum/2)*CellSize,
                        (j-D/2+CellNum/2)*CellSize,
                        CellSize, CellSize); } } //paint a black particle
        g.setColor(Color.blue); //printing out the values
        g.drawString("Time", D/2-115, 12);
        g.drawString("" + step, D/2-115, 24);
        g.drawString("Radius", D/2-35, 12);
        g.drawString("R = " + evaluateR(), D/2-35, 24);
        g.drawString("Mass", D/2+45, 12);
        g.drawString("M = " + evaluateM(), D/2+45, 24);
        g.drawString("fractal dimension", D/2-115, D-20);
        g.drawString("D = " + evaluateD(), D/2-115, D-8);
    }//paintFrame()

    public void actionPerformed(ActionEvent evt)
    {
        maxstep = (int)Double.valueOf(tf_step.getText()).doubleValue();
        if(evt.getActionCommand() == but_reset.getActionCommand())
        {
            stop(); reset();
            try{Thread.sleep(50);} catch(InterruptedException exc){}
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

package com.openComplex.app.DynamicalSystems.LogicalPictures;

/**
 * Created by strange on 25/08/15.
 */

import java.awt.*;
import java.applet.*;

public class Feigenbaum extends Applet implements Runnable{
    Thread moveThread;
    private static final int MOVEPAUSE = 50;
    public Image offScreenImage;
    public double rmin = 0.0, rmax = 4.0, xmin = 0.0, xmax = 1.0;
    public double rminh = 0.0, rmaxh = 4.0, xminh = 0.0, xmaxh = 1.0;
    public double r[], x[];
    public String thePressedKey = " ";
    public int nIter = 0;
    public int xdown, ydown, xup, yup;
    public int iters=0;
    public boolean myGo = false;
    //public boolean eightColors = false;
    public Color colorS[] = {Color.green, Color.red, Color.blue, Color.cyan, Color.pink,
            Color.white, Color.yellow, Color.magenta};
    int iColor = 0;

    public void start()  {
        if(myGo) {
            if(moveThread == null){
                moveThread = new Thread(this);
                moveThread.start();
            }
        }
    }

    public void stop()  {
        if(moveThread != null){
            moveThread.stop();
            try {
                moveThread.join();
            }
            catch (InterruptedException e) { }
        }
        moveThread=null;
    }

    public void run() {
        while (true){
            try {
                Thread.sleep(MOVEPAUSE);
            }catch(InterruptedException e) {
            }
            repaint();
        }
    }

    public void init() {
        super.init();
        offScreenImage = createImage(400,300);

        //{{INIT_CONTROLS
        setLayout(null);
        resize(500,350);
        setBackground(new Color(16762880));
        button1 = new java.awt.Button("Start");
        button1.reshape(402,6,95,33);
        button1.setFont(new Font("Helvetica", Font.PLAIN, 18));
        add(button1);
        button2 = new java.awt.Button("Clear");
        button2.reshape(402,41,95,33);
        button2.setFont(new Font("Helvetica", Font.PLAIN, 18));
        add(button2);
        button3 = new java.awt.Button("More");
        button3.reshape(402,76,95,33);
        button3.setFont(new Font("Helvetica", Font.PLAIN, 18));
        add(button3);
        r_range = new java.awt.Label(" < r < ");
        r_range.reshape(113,301,38,22);
        r_range.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(r_range);
        x_range = new java.awt.Label("< x <");
        x_range.reshape(113,325,38,22);
        x_range.setFont(new Font("Helvetica", Font.PLAIN, 12));
        add(x_range);
        checkbox1 = new java.awt.Checkbox("8 color");
        checkbox1.reshape(421,300,73,37);
        add(checkbox1);
        textFieldrmin = new java.awt.TextField();
        textFieldrmin.setText("0");
        textFieldrmin.reshape(1,301,110,21);
        textFieldrmin.setFont(new Font("Helvetica", Font.PLAIN, 12));
        textFieldrmin.setBackground(new Color(16777215));
        add(textFieldrmin);
        textFieldxmin = new java.awt.TextField();
        textFieldxmin.setText("0");
        textFieldxmin.reshape(1,325,110,21);
        textFieldxmin.setFont(new Font("Helvetica", Font.PLAIN, 12));
        textFieldxmin.setBackground(new Color(16777215));
        add(textFieldxmin);
        textFieldrmax = new java.awt.TextField();
        textFieldrmax.setText("4");
        textFieldrmax.reshape(152,301,110,21);
        textFieldrmax.setFont(new Font("Helvetica", Font.PLAIN, 12));
        textFieldrmax.setBackground(new Color(16777215));
        add(textFieldrmax);
        textFieldxmax = new java.awt.TextField();
        textFieldxmax.setText("1");
        textFieldxmax.reshape(152,325,110,21);
        textFieldxmax.setFont(new Font("Helvetica", Font.PLAIN, 12));
        textFieldxmax.setBackground(new Color(16777215));
        add(textFieldxmax);
        //}}

        r = new double[401];
        x = new double[401];
        repaint();
    }

    public void initCoord(){
        getValues();
        for (int i=1; i < 401; i++) {
            r[i] = rmin + i*(rmax-rmin)/400.0;
            x[i] = 0.5;
            for (int iter=1; iter<101; iter++){
                x[i] = r[i] * x[i] * (1.0 - x[i]);
            }
        }
        nIter = 0;
    }

    public void getValues(){
        String xmins = textFieldxmin.getText().trim();
        String xmaxs = textFieldxmax.getText().trim();
        String rmins = textFieldrmin.getText().trim();
        String rmaxs = textFieldrmax.getText().trim();
        try {
            Double myDouble1 = new Double(xmins);
            xmin = myDouble1.doubleValue();
        } catch (Exception e) {
        }
        try {
            Double myDouble2 = new Double(xmaxs);
            xmax = myDouble2.doubleValue();
        } catch (Exception e) {
        }
        try {
            Double myDouble3 = new Double(rmins);
            rmin = myDouble3.doubleValue();
        } catch (Exception e) {
        }
        try {
            Double myDouble4 = new Double(rmaxs);
            rmax = myDouble4.doubleValue();
        } catch (Exception e) {
        }
    }

    public void update(Graphics g){

        paint();
        g.drawImage(offScreenImage, 0, 0, this);
    }

    public void paint(){
        Graphics offg = offScreenImage.getGraphics();
        if (thePressedKey.equalsIgnoreCase("n")){
            rmin = 0.0;
            rmax = 4.0;
            xmin = 0.0;
            xmax = 1.0;
            thePressedKey = "c";
            initCoord();
        }
        if (thePressedKey.equalsIgnoreCase("c")){
            thePressedKey = "p";
            offg.setColor(Color.black);				// drawing field color
            offg.fillRect(0,0,400,300);
            nIter = 0;
        }
        if (myGo) {
            // color for iteration points
            iters++;
            offg.setColor(colorS[iColor]);
            if (checkbox1.getState()) iColor++;
            if (iColor == 8) iColor=0;

            if (iters < 101){
                for (int i=1; i < 401; i++) {
                    x[i] = r[i] * x[i] * (1.0 - x[i]);
                    int xpixel = (int)(300.0*(1.0 - (x[i] - xmin) / (xmax - xmin))-1);
                    if(xpixel<301) offg.drawLine(i,xpixel,i,xpixel);
                }
                offg.setColor(Color.black);
                offg.fillRect(0,0,40,20);
                offg.setColor(Color.red);
                offg.setFont(new Font("Helvetica", Font.PLAIN, 12));
                offg.drawString(""+(nIter+iters),2,18);
            } else {
                thePressedKey = " ";
                nIter = nIter + iters-1;
                iters = 0;
                myGo = false;
                this.stop();
            }
        }
    }

    public String outString(String inString, int l){    // returns the first l chars of string
        int ls = Math.min(inString.length(),l);
        return inString.substring(0,ls);
    }

    public boolean handleEvent(Event event) {
        if (event.target == this && event.id == Event.KEY_PRESS) {
            Feigenbaum_KeyPress(event);
            return true;
        }
        if (event.target == this && event.id == Event.MOUSE_DOWN) {
            Feigenbaum_MouseDown(event);
            return true;
        }
        if (event.target == this && event.id == Event.MOUSE_UP) {
            Feigenbaum_MouseUp(event);
            return true;
        }
        if (event.target == button1 && event.id == Event.ACTION_EVENT) {   // Start button
            thePressedKey = "n";
            myGo = true;
            this.start();
            repaint();
            return true;
        }
        if (event.target == button2 && event.id == Event.ACTION_EVENT) {   // Clear button
            thePressedKey = "c";
            myGo = true;
            this.start();
            repaint();
            return true;
        }
        if (event.target == button3 && event.id == Event.ACTION_EVENT) {   // More button
            thePressedKey = "p";
            myGo = true;
            this.start();
            repaint();
            return true;
        }
        if (event.target == checkbox1 && event.id == Event.ACTION_EVENT) {
            checkbox1_Action(event);
            return true;
        }
        if (event.target == this && event.id == Event.MOUSE_DRAG) {
            Feigenbaum_MouseDrag(event);
            return true;
        }
        return false;
        //return super.handleEvent(event);
    }

    //{{DECLARE_CONTROLS
    java.awt.Button button1;
    java.awt.Button button2;
    java.awt.Button button3;
    java.awt.Label r_range;
    java.awt.Label x_range;
    java.awt.Checkbox checkbox1;
    java.awt.TextField textFieldrmin;
    java.awt.TextField textFieldxmin;
    java.awt.TextField textFieldrmax;
    java.awt.TextField textFieldxmax;
    //}}
    void Feigenbaum_KeyPress(Event event) {
        thePressedKey = ""+(char)event.key;
        myGo = true;
        this.start();
        repaint();
    }

    void Feigenbaum_MouseDown(Event event) {
        xminh = xmin;
        xmaxh = xmax;
        rminh = rmin;
        rmaxh = rmax;
        xdown = event.x;
        ydown = event.y;
    }

    void Feigenbaum_MouseUp(Event event) {
        xup = event.x;
        yup = event.y;
        if (xup == xdown) return;
        if (yup == ydown) return;
        makenewr();
    }

    public void makenewr(){
        if (xup < 400 && yup < 300){
            generateCoordinate();
            initCoord();
            thePressedKey = "c";
            myGo = true;
            this.start();
            repaint();
        }
    }

    void checkbox1_Action(Event event) {
        //
    }

    public void generateCoordinate(){
        int xlower = xdown;
        if (xup < xlower) xlower = xup;
        int xhigher = xup;
        if (xdown > xhigher) xhigher = xdown;
        int ylower = ydown;
        if (yup < ylower) ylower = yup;
        int yhigher = yup;
        if (ydown > yhigher) yhigher = ydown;
        rmin = rminh + (rmaxh-rminh)*xlower / 400.;
        rmax = rminh + (rmaxh-rminh)*xhigher / 400.;
        xmax = xmaxh - (xmaxh-xminh)*ylower / 300.;
        xmin = xmaxh - (xmaxh-xminh)*yhigher / 300.;
        textFieldxmin.setText(outString(""+xmin,8));
        textFieldxmax.setText(outString(""+xmax,8));
        textFieldrmin.setText(outString(""+rmin,8));
        textFieldrmax.setText(outString(""+rmax,8));
    }

    void Feigenbaum_MouseDrag(Event event) {
        xup = event.x;
        yup = event.y;
        if (xup == xdown) return;
        if (yup == ydown) return;
        generateCoordinate();
        repaint();
    }

}
package com.openComplex.app.DynamicalSystems.Oscillators.HarmonicOscillator;

import javax.swing.*;
import java.awt.*;

/**
 *  on 06/10/15.
 */
public class OscillatorModel extends JPanel {
    private int xPoints1[] = new int[10]; //pixelcoordinates for spring1
    private int yPoints1[] = new int[10]; //pixelcoordinates for spring1
    private int xPoints2[] = new int[10]; //pixelcoordinates for spring2
    private int yPoints2[] = new int[10]; //pixelcoordinates for spring2
    private int px, py = H / 2; //pixelcoordinates
    private double x; //position
    private double m = 1., D = 0.25; //mass, spring strength
    private double omega = Math.sqrt(2 * D / m); //frequency
    private double amp = -0.8; //amplitude
    private int reib = 0; //friction
    private static final double dt = 0.05; //timestep
    public static final int W = 400, H = 220;

    public void pixels() {  //method for calculating pixelcoords

        int length, currentLength1, currentLength2; //spring lengths
        double d1, d2; //pixelcoordinates for springs

        length = W / 2 - 10;
        px = (int) Math.round((double) W / 2 + (length - 40) * x); //mass position
        currentLength1 = px - 20;
        currentLength2 = W - px - 20;
        d1 = currentLength1 / 16.;
        d2 = currentLength2 / 16.;

        xPoints1[0] = 11;
        yPoints1[0] = H / 2;
        xPoints1[1] = (int) (11 + d1);
        yPoints1[1] = H / 2 + 7;
        xPoints1[2] = (int) (11 + 3 * d1);
        yPoints1[2] = H / 2 - 7;
        xPoints1[3] = (int) (11 + 5 * d1);
        yPoints1[3] = H / 2 + 7;
        xPoints1[4] = (int) (11 + 7 * d1);
        yPoints1[4] = H / 2 - 7;
        xPoints1[5] = (int) (11 + 9 * d1);
        yPoints1[5] = H / 2 + 7;
        xPoints1[6] = (int) (11 + 11 * d1);
        yPoints1[6] = H / 2 - 7;
        xPoints1[7] = (int) (11 + 13 * d1);
        yPoints1[7] = H / 2 + 7;
        xPoints1[8] = (int) (11 + 15 * d1);
        yPoints1[8] = H / 2 - 7;
        xPoints1[9] = px - 10;
        yPoints1[9] = H / 2;

        xPoints2[0] = W - 12;
        yPoints2[0] = H / 2;
        xPoints2[1] = (int) (W - 11 - d2);
        yPoints2[1] = H / 2 + 7;
        xPoints2[2] = (int) (W - 11 - 3 * d2);
        yPoints2[2] = H / 2 - 7;
        xPoints2[3] = (int) (W - 11 - 5 * d2);
        yPoints2[3] = H / 2 + 7;
        xPoints2[4] = (int) (W - 11 - 7 * d2);
        yPoints2[4] = H / 2 - 7;
        xPoints2[5] = (int) (W - 11 - 9 * d2);
        yPoints2[5] = H / 2 + 7;
        xPoints2[6] = (int) (W - 11 - 11 * d2);
        yPoints2[6] = H / 2 - 7;
        xPoints2[7] = (int) (W - 11 - 13 * d2);
        yPoints2[7] = H / 2 + 7;
        xPoints2[8] = (int) (W - 11 - 15 * d2);
        yPoints2[8] = H / 2 - 7;
        xPoints2[9] = px + 10;
        yPoints2[9] = H / 2;
    }//pixels()

    public void update(int step) {
        x = amp * Math.exp(-0.1 * reib * step * dt) * Math.cos(omega * step * dt); //new x

        pixels(); //calculate pixelcoordinates
        repaint(); //paint new frame
    }

    public void startwerte() { //method for setting initial values
        omega = Math.sqrt(2 * D / m);
    }//startwerte()

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0, 0, W, H);
        g.setColor(Color.black);
        g.drawLine(10, H / 2 - 30, 10, H / 2 + 30);
        g.drawLine(W - 11, H / 2 - 30, W - 11, H / 2 + 30);
        g.drawPolyline(xPoints1, yPoints1, 10);
        g.drawPolyline(xPoints2, yPoints2, 10);
        g.fillOval(px - 10, py - 10, 21, 21);
        g.setColor(Color.blue);
        g.setFont(new Font("Verdana", Font.BOLD, 14));
        g.drawString("Drag and drop mass ...", W / 2 - 90, 28);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.drawString("Spring force:  " + D, 80, H - 40);
        g.drawString("Friction:  " + reib, 250, H - 40);

    }//paint()

    public void plusSpring(){
        D = D * 2;
    }

    public void minusSpring(){
        D = D / 2;
    }
    public void plusFriction(){
       reib++;
    }
    public void minusFriction(){
        if (reib > 0) {
            reib--;
        }
    }
}

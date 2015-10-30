package com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillator;

import javax.swing.*;
import java.awt.*;

/**
 *  on 07/10/15.
 */
public class AnharmonicOscillatorModel extends JPanel {
    private static final int W = 400, H = 200; //graphic window
    private int px, py; //pixelcoordinates
    private double x, xp, y, yp; //coordinates and velocities
    private double m = 1., D = 1.; //mass, spring
    private static final double l = 0.2; //spring balance length
    private int reib; //friction
    private static final double dt = 0.002; //timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private int xPoints1_int[] = new int[11]; //pixelcoordinates for spring1
    private int yPoints1_int[] = new int[11]; //pixelcoordinates for spring1
    private int xPoints2_int[] = new int[11]; //pixelcoordinates for spring2
    private int yPoints2_int[] = new int[11]; //pixelcoordinates for spring2
    private double startwert[] = new double[5]; //save initial values
    private int startwert_reib; //save initial friction

    public AnharmonicOscillatorModel() {
        startwert[0] = 0.4; //x
        startwert[1] = 0.0; //xp
        startwert[2] = 0.7; //y
        startwert[3] = 0.0; //yp
        startwert[4] = D; //D
        startwert_reib = 0; //reib
    }


    public void startwerte()  //method for setting initial values
    {
        x = startwert[0];
        xp = startwert[1];
        y = startwert[2];
        yp = startwert[3];
        D = startwert[4];
        reib = startwert_reib;

    }//startwerte()

    public void pixels()  //method for calculating pixelcoords
    {
        px = (int) Math.round(W * x);
        py = (int) Math.round(H * y);

        double currentLength1, currentLength2; //spring lengths
        double d1, d2; //pixel points for springs
        double alpha1, alpha2; //angle to rotate
        double xPoints1[] = new double[11]; //x spring1
        double yPoints1[] = new double[11]; //y spring1
        double xPoints2[] = new double[11]; //x spring2
        double yPoints2[] = new double[11]; //y spring2

        currentLength1 = Math.sqrt(x * x + (y - 0.5) * (y - 0.5)) - 0.05;
        currentLength2 = Math.sqrt((1. - x) * (1. - x) + (y - 0.5) * (y - 0.5)) - 0.05;
        d1 = currentLength1 / 16;
        d2 = currentLength2 / 16;
        alpha1 = Math.atan((y - 0.5) / x);
        alpha2 = Math.atan((y - 0.5) / (1. - x));

        if (px < 0) {
            alpha1 = alpha1 + Math.PI;
        }
        if (px == 0) {
            if (py > H / 2) alpha1 = Math.PI / 2.;
            else alpha1 = -Math.PI / 2.;
        }
        if (px > W) {
            alpha2 = alpha2 + Math.PI;
        }
        if (px == W) {
            if (py > H / 2) alpha2 = Math.PI / 2.;
            else alpha2 = -Math.PI / 2.;
        }

        xPoints1[0] = 0.;
        yPoints1[0] = 0.;
        xPoints1[1] = d1;
        yPoints1[1] = 0.02;
        xPoints1[2] = 3 * d1;
        yPoints1[2] = -0.02;
        xPoints1[3] = 5 * d1;
        yPoints1[3] = 0.02;
        xPoints1[4] = 7 * d1;
        yPoints1[4] = -0.02;
        xPoints1[5] = 9 * d1;
        yPoints1[5] = 0.02;
        xPoints1[6] = 11 * d1;
        yPoints1[6] = -0.02;
        xPoints1[7] = 13 * d1;
        yPoints1[7] = 0.02;
        xPoints1[8] = 15 * d1;
        yPoints1[8] = -0.02;
        xPoints1[9] = 16 * d1;
        yPoints1[9] = 0.;

        xPoints2[0] = 0.;
        yPoints2[0] = 0.;
        xPoints2[1] = -d2;
        yPoints2[1] = -0.02;
        xPoints2[2] = -3 * d2;
        yPoints2[2] = 0.02;
        xPoints2[3] = -5 * d2;
        yPoints2[3] = -0.02;
        xPoints2[4] = -7 * d2;
        yPoints2[4] = 0.02;
        xPoints2[5] = -9 * d2;
        yPoints2[5] = -0.02;
        xPoints2[6] = -11 * d2;
        yPoints2[6] = 0.02;
        xPoints2[7] = -13 * d2;
        yPoints2[7] = -0.02;
        xPoints2[8] = -15 * d2;
        yPoints2[8] = 0.02;
        xPoints2[9] = -16 * d2;
        yPoints2[9] = 0.;

        double xnew, ynew;
        for (int i = 0; i < 10; i++) //rotate coordinates for springs
        {
            xnew = xPoints1[i] * Math.cos(alpha1) - yPoints1[i] * Math.sin(alpha1);
            ynew = xPoints1[i] * Math.sin(alpha1) + yPoints1[i] * Math.cos(alpha1);
            xPoints1[i] = xnew;
            yPoints1[i] = ynew;
            xPoints1_int[i] = (int) Math.round(W * xPoints1[i]);
            yPoints1_int[i] = (int) Math.round(H * yPoints1[i]) + H / 2;

            xnew = xPoints2[i] * Math.cos(alpha2) + yPoints2[i] * Math.sin(alpha2);
            ynew = -xPoints2[i] * Math.sin(alpha2) + yPoints2[i] * Math.cos(alpha2);
            xPoints2[i] = xnew;
            yPoints2[i] = ynew;
            xPoints2_int[i] = (int) Math.round(W * xPoints2[i]) + W;
            yPoints2_int[i] = (int) Math.round(H * yPoints2[i]) + H / 2;
        }
        xPoints1_int[10] = px - 12;
        yPoints1_int[10] = py;
        xPoints2_int[10] = px + 12;
        yPoints2_int[10] = py;
    }//pixels()

    /* force in x-direction */
    public double forcex(double x) {
        return (D * (1. - (2 * l) / Math.sqrt(5. - 8 * x + 4 * x * x - 4 * y + 4 * y * y)
                + x * (-2. + 2 * l * (1. / Math.sqrt(4 * x * x + (1. - 2 * y) * (1. - 2 * y))
                + 1. / Math.sqrt(5. - 8 * x + 4 * x * x - 4 * y + 4 * y * y))))) / m
                - 0.1 * reib * xp;
    }//forcex()

    /* force in y-direction */
    public double forcey(double y) {
        return (D * (1. - l / Math.sqrt(4 * x * x + (1. - 2 * y) * (1. - 2 * y))
                - l / Math.sqrt(5. - 8 * x + 4 * x * x - 4 * y + 4 * y * y)
                + y * (-2. + 2 * l * (1. / Math.sqrt(4 * x * x + (1. - 2 * y) * (1. - 2 * y))
                + 1. / Math.sqrt(5. - 8 * x + 4 * x * x - 4 * y + 4 * y * y))))) / m
                - 0.1 * reib * yp;
    }//forcey()

    public double energy() {
        return (3 * D) / 4 + D * l * l - D * x + D * x * x + (m * xp * xp) / 2
                - D * l * Math.sqrt((-1. + x) * (-1. + x) + (-0.5 + y) * (-0.5 + y))
                - D * l * Math.sqrt(x * x + (-0.5 + y) * (-0.5 + y))
                - D * y + D * y * y + (m * yp * yp) / 2;
    }//energy()

    public void runge_step_x() {
        k1[0] = dt * xp;
        l1[0] = dt * forcex(x);
        k1[1] = dt * (xp + l1[0] / 2);
        l1[1] = dt * forcex(x + k1[0] / 2);
        k1[2] = dt * (xp + l1[1] / 2);
        l1[2] = dt * forcex(x + k1[1] / 2);
        k1[3] = dt * (xp + l1[2]);
        l1[3] = dt * forcex(x + k1[2]);
    }//runge_step_x()

    public void runge_step_y() {
        k2[0] = dt * yp;
        l2[0] = dt * forcey(y);
        k2[1] = dt * (yp + l2[0] / 2);
        l2[1] = dt * forcey(y + k2[0] / 2);
        k2[2] = dt * (yp + l2[1] / 2);
        l2[2] = dt * forcey(y + k2[1] / 2);
        k2[3] = dt * (yp + l2[2]);
        l2[3] = dt * forcey(y + k2[2]);
    }//runge_step_y()


    public void update(){
        runge_step_x(); //Runge-Kutta
        runge_step_y(); //Runge-Kutta
        x = x + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        xp = xp + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        y = y + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        yp = yp + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
        pixels(); //calculate pixelcoordinates
        repaint(); //paint new frame
    }

    @Override
    protected void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        gr.setFont(new Font("Verdana", Font.BOLD, 10));
        gr.setColor(Color.white);
        gr.fillRect(0, 0, W, H + 50);
        gr.setColor(Color.blue);
        gr.drawString("Energy:", W / 2 - 150, H + 20);
        gr.drawString("" + (double) (Math.round(100 * energy())) / 100, W / 2 - 150, H + 40);
        gr.drawString("Spring force:", W / 2 - 40, H + 20);
        gr.drawString("" + D, W / 2 - 40, H + 40);
        gr.drawString("Friction:", W / 2 + 90, H + 20);
        gr.drawString("" + reib, W / 2 + 90, H + 40);
        gr.drawLine(0, H / 2, W, H / 2);
        gr.drawLine(W / 2, 0, W / 2, H);
        gr.setColor(Color.black);
        gr.drawPolyline(xPoints1_int, yPoints1_int, 11);
        gr.drawPolyline(xPoints2_int, yPoints2_int, 11);
        gr.fillOval(px - 12, py - 12, 25, 25);
    } //paintFrame(gr)

    public void setDPlus(){
        startwert[4] = 2 * startwert[4];
        D = startwert[4];
        repaint();
    }
    public void setDMinus() {
        startwert[4] = startwert[4] / 2;
        D = startwert[4];
        repaint();
    }

    public void plusFriction(){
        startwert_reib++;
        reib = startwert_reib;
    }
    public void minusFriction(){
        if (reib > 0) {
            startwert_reib--;
            reib = startwert_reib;
        }
    }


}

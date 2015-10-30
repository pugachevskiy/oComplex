package com.openComplex.app.DynamicalSystems.Oscillators.OscillatorsWithCoupling;

import javax.swing.*;
import java.awt.*;

/**
 *  on 07/10/15.
 */
public class OscillatorsWithCouplingModel extends JPanel {
    private static final int W = 400, H = 200;
    private int px1, px2; //pixelcoordinates
    private double x1, x1p, x2, x2p; //coordinates and velocities
    private double m = 1.; //mass
    private double D1 = 1., D2 = 1. / 16; //spring strengths
    private static final double l = 1. / 3; //spring lengths of balance
    private int reib; //friction
    private static final double dt = 0.005; //timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double k3[] = new double[4]; //Runge-Kutta
    private double k4[] = new double[4]; //Runge-Kutta
    private double l3[] = new double[4]; //Runge-Kutta
    private double l4[] = new double[4]; //Runge-Kutta
    private int xPoints1_int[] = new int[12]; //pixelcoordinates spring1
    private int yPoints1_int[] = new int[12]; //pixelcoordinates spring1
    private int xPoints2_int[] = new int[12]; //pixelcoordinates spring2
    private int yPoints2_int[] = new int[12]; //pixelcoordinates spring2
    private int xPoints3_int[] = new int[12]; //pixelcoordinates spring middle
    private int yPoints3_int[] = new int[12]; //pixelcoordinates spring middle
    private double startwert[] = new double[3]; //save initial values
    private int startwert_reib; //save initial friction value
    private boolean Choice[] = new boolean[3]; //check for selected item

    public OscillatorsWithCouplingModel() {
        Choice[0] = true; //Free
        startwert[0] = 0.15; //x1
        startwert[1] = 2. / 3; //x2
        startwert[2] = D2; //D2
        startwert_reib = 0; //reib
    }

    public void startwerte()  //method for setting initial values
    {
        x1 = startwert[0];
        x2 = startwert[1];
        x1p = 0.;
        x2p = 0.;
        D2 = startwert[2];
        reib = startwert_reib;

    }//startwerte()

    public void pixels()  //method for calculating pixelcoordinates
    {
        px1 = (int) Math.round(W * x1); //mass left
        px2 = (int) Math.round(W * x2); //mass right

        double currentLength1, currentLength2, currentLength3; //spring lengths
        double d1, d2, d3; //for pixels of springs
        double xPoints1[] = new double[12]; //x pix spring1
        double yPoints1[] = new double[12]; //y pix spring1
        double xPoints2[] = new double[12]; //x pix spring2
        double yPoints2[] = new double[12]; //y pix spring2
        double xPoints3[] = new double[12]; //x pix spring_middle
        double yPoints3[] = new double[12]; //y pix spring_middle

        currentLength1 = x1;
        currentLength2 = 1. - x2;
        currentLength3 = x2 - x1;
        d1 = currentLength1 / 18.; //devide in 18 pieces
        d2 = currentLength2 / 18.; //devide
        d3 = currentLength3 / 18.; //devide

        xPoints1[0] = 0.;
        yPoints1[0] = 0.; //spring pix
        xPoints1[1] = d1;
        yPoints1[1] = 0.;
        xPoints2[0] = 0.;
        yPoints2[0] = 0.;
        xPoints2[1] = -d2;
        yPoints2[1] = 0.;
        xPoints3[0] = 0.;
        yPoints3[0] = 0.;
        xPoints3[1] = d3;
        yPoints3[1] = 0.;
        for (int i = 2; i < 10; i++) //spring pix
        {
            xPoints1[i] = (2 * i - 2) * d1;
            xPoints2[i] = -(2 * i - 2) * d2;
            xPoints3[i] = (2 * i - 2) * d3;
            if (i % 2 == 0) {
                yPoints1[i] = 0.02;
                yPoints2[i] = -0.02;
                yPoints3[i] = 0.02;
            } else {
                yPoints1[i] = -0.02;
                yPoints2[i] = 0.02;
                yPoints3[i] = -0.02;
            }
        }
        xPoints1[10] = 17 * d1;
        yPoints1[10] = 0.; //spring pix
        xPoints1[11] = 18 * d1;
        yPoints1[11] = 0.;
        xPoints2[10] = -17 * d2;
        yPoints2[10] = 0.;
        xPoints2[11] = -18 * d2;
        yPoints2[11] = 0.;
        xPoints3[10] = 17 * d3;
        yPoints3[10] = 0.;
        xPoints3[11] = 18 * d3;
        yPoints3[11] = 0.;

        for (int i = 0; i < 12; i++) //set int coordinates
        {
            xPoints1_int[i] = (int) Math.round(W * xPoints1[i]);
            yPoints1_int[i] = (int) Math.round(H * yPoints1[i]) + H / 2;
            xPoints2_int[i] = (int) Math.round(W * xPoints2[i]) + W;
            yPoints2_int[i] = (int) Math.round(H * yPoints2[i]) + H / 2;
            xPoints3_int[i] = (int) Math.round(W * xPoints3[i]) + px1;
            yPoints3_int[i] = (int) Math.round(H * yPoints3[i]) + H / 2;
        }
    }//pixels()

    /* force on x1 */
    public double forcex1(double x1) {
        return -((D1 * (x1 * x1 - l * Math.sqrt(x1 * x1)) * (x1 - x2)
                + D2 * x1 * (x1 * x1 - l * Math.sqrt((x1 - x2) * (x1 - x2))
                - 2 * x1 * x2 + x2 * x2)) / (m * x1 * (x1 - x2)))
                - reib * 0.1 * x1p; //friction
    }//forcex1()

    /* force on x2 */
    public double forcex2(double x2) {
        return (-(D1 * (x1 - x2) * (1 - l * Math.sqrt((-1 + x2) * (-1 + x2))
                - 2 * x2 + x2 * x2)) + D2 * (-1 + x2) * (x1 * x1
                - l * Math.sqrt((x1 - x2) * (x1 - x2)) - 2 * x1 * x2
                + x2 * x2)) / (m * (x1 - x2) * (-1 + x2))
                - reib * 0.1 * x2p; //friction
    }//forcex2()

    public double energy() {
        return D1 / 2 + D1 * l * l + (D2 * l * l) / 2 + (D1 * x1 * x1) / 2 + (D2 * x1 * x1) / 2
                - D1 * l * Math.sqrt(x1 * x1) + (m * x1p * x1p) / 2
                - D1 * l * Math.sqrt((1 - x2) * (1 - x2)) - D1 * x2 - D2 * x1 * x2
                + (D1 * x2 * x2) / 2 + (D2 * x2 * x2) / 2
                - D2 * l * Math.sqrt((-x1 + x2) * (-x1 + x2)) + (m * x2p * x2p) / 2;
    }//energy()

    public void runge_step_x1() {
        k1[0] = dt * x1p;
        l1[0] = dt * forcex1(x1);
        k1[1] = dt * (x1p + l1[0] / 2);
        l1[1] = dt * forcex1(x1 + k1[0] / 2);
        k1[2] = dt * (x1p + l1[1] / 2);
        l1[2] = dt * forcex1(x1 + k1[1] / 2);
        k1[3] = dt * (x1p + l1[2]);
        l1[3] = dt * forcex1(x1 + k1[2]);
    }//runge_step_x1()

    public void runge_step_x2() {
        k3[0] = dt * x2p;
        l3[0] = dt * forcex2(x2);
        k3[1] = dt * (x2p + l3[0] / 2);
        l3[1] = dt * forcex2(x2 + k3[0] / 2);
        k3[2] = dt * (x2p + l3[1] / 2);
        l3[2] = dt * forcex2(x2 + k3[1] / 2);
        k3[3] = dt * (x2p + l3[2]);
        l3[3] = dt * forcex2(x2 + k3[2]);
    }//runge_step_x2()


    public void update() {
        runge_step_x1(); //Runge-Kutta-calculation
        runge_step_x2(); //Runge-Kutta-calculation
        x1 = x1 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //new x1
        x1p = x1p + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //new x1p
        x2 = x2 + (k3[0] + 2 * k3[1] + 2 * k3[2] + k3[3]) / 6; //new x2
        x2p = x2p + (l3[0] + 2 * l3[1] + 2 * l3[2] + l3[3]) / 6; //new x2p
        pixels(); //calculate pixelcoordinates
        repaint(); //paint new frame
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, W, H + 50);

        g.setColor(Color.blue);
        g.drawString("Energy:", W / 2 - 140, H + 20);
        g.drawString("" + (double) (Math.round(100 * energy())) / 100, W / 2 - 140, H + 40);
        g.drawString("Coupling:", W / 2 - 30, H + 20);
        g.drawString("" + D2, W / 2 - 30, H + 40);
        g.drawString("Friction:", W / 2 + 80, H + 20);
        g.drawString("" + reib, W / 2 + 80, H + 40);

        g.drawLine(0, H / 2, W, H / 2);
        g.drawLine(W / 2, 0, W / 2, H);
        g.setColor(Color.black);
        g.drawPolyline(xPoints1_int, yPoints1_int, 12); //spring_left
        g.drawPolyline(xPoints2_int, yPoints2_int, 12); //spring_right
        g.setColor(new Color(100, 50, 10));
        g.drawPolyline(xPoints3_int, yPoints3_int, 12); //spring_middle
        g.setColor(Color.black);
        g.fillOval(px1 - 9, H / 2 - 9, 19, 19);
        g.fillOval(px2 - 9, H / 2 - 9, 19, 19);
    } //paintFrame(g)


    public void setDPlus() {
        if (startwert[2] < 4) {
            startwert[2] = 2 * startwert[2];
            D2 = startwert[2];
        }
        repaint();
    }

    public void setDMinus() {
        startwert[2] = startwert[2] / 2;
        D2 = startwert[2];
        repaint();
    }

    public void plusFriction() {
        startwert_reib++;
        reib = startwert_reib;
    }

    public void minusFriction() {
        if (reib > 0) {
            startwert_reib--;
            reib = startwert_reib;
        }
    }

    public void setChoiceFree() {
        Choice[0] = true; //Free
        Choice[1] = false; //Together
        Choice[2] = false; //Against
        startwert[0] = 0.15; //x1
        startwert[1] = 2. / 3; //x2
        startwerte();
        pixels();
        repaint();
    }

    public void setChoiceTogether() {
        Choice[0] = false;
        Choice[1] = true;
        Choice[2] = false;
        startwert[0] = 1. / 3 - 0.2; //x1
        startwert[1] = 2. / 3 - 0.2; //x2
        startwerte();
        pixels();
        repaint();
    }

    public void setChoiceAganist() {
        Choice[0] = false;
        Choice[1] = false;
        Choice[2] = true;
        startwert[0] = 1. / 3 - 0.1; //x1
        startwert[1] = 2. / 3 + 0.1; //x2
        startwerte();
        pixels();
        repaint();
    }
}

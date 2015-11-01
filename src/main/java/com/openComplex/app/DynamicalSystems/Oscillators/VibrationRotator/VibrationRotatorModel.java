package com.openComplex.app.DynamicalSystems.Oscillators.VibrationRotator;

import javax.swing.*;
import java.awt.*;

/**
 *  on 07/10/15.
 */
public class VibrationRotatorModel extends JPanel {
    private static int Lx = 300, Ly = 300; //graphic-window
    private int px, py; //pixelcoordinates
    private double phi, omega; //coordinates and velocities
    private double r, rp; //coordinates and velocities
    private double D = 100., m = 1., r0 = 1.; //spring_strenght,mass,balance_distance
    private int reib; //friction
    private double phi0 = 0.; //initial value for phi
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k[] = new double[4]; //Runge-Kutta
    private double l[] = new double[4]; //Runge-Kutta
    private int xPoints_int[] = new int[11]; //pixelcoordinates for spring
    private int yPoints_int[] = new int[11]; //pixelcoordinates for spring
    private double startwert[] = new double[2]; //save initial values


    public VibrationRotatorModel(int value){
        startwert[0] = r0; //r
        startwert[1] = (60. - (double) value) / 10.; //omega
    }

    public void startwerte() {
        r = startwert[0];
        omega = startwert[1];
        phi = 0.;
        phi0 = 0.;
        rp = 0.;

    }//startwerte()


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.white);
        g.fillRect(0, 0, Lx + 100, Ly);
        g.setColor(Color.blue);
        g.drawString("Friction:", Lx + 20, Ly / 2 + 25);
        g.drawString("" + reib, Lx + 20, Ly / 2 + 40);
        g.drawString("Omega:", Lx + 20, Ly / 2 + 70);
        g.drawString("" + (double) Math.round(100 * omega) / 100, Lx + 20, Ly / 2 + 85);
        g.drawString("Distance:", Lx + 20, Ly / 2 + 115);
        g.drawString("" + (double) Math.round(100 * r) / 100, Lx + 20, Ly / 2 + 130);
        g.setColor(Color.black);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
       // g.drawString("Step " + step, 20, 30);
        g.setColor(Color.red);
        g.drawPolyline(xPoints_int, yPoints_int, 11);
        g.setColor(Color.black);
        g.fillOval(px - 7, py - 7, 15, 15);
    } //update(g)

    public void pixels() //calculate pixelcoordinates
    {
        px = (int) Math.round((double) (Lx / 2) + 80 * r * Math.cos(phi));
        py = (int) Math.round((double) (Ly / 2) - 80 * r * Math.sin(phi));

        double d; //points for spring
        double xPoints[] = new double[11]; //x spring
        double yPoints[] = new double[11]; //y spring

        d = r / 16;

        xPoints[0] = 0.;
        yPoints[0] = 0.;
        xPoints[1] = d;
        yPoints[1] = 0.03;
        xPoints[2] = 3 * d;
        yPoints[2] = -0.03;
        xPoints[3] = 5 * d;
        yPoints[3] = 0.03;
        xPoints[4] = 7 * d;
        yPoints[4] = -0.03;
        xPoints[5] = 9 * d;
        yPoints[5] = 0.03;
        xPoints[6] = 11 * d;
        yPoints[6] = -0.03;
        xPoints[7] = 13 * d;
        yPoints[7] = 0.03;
        xPoints[8] = 15 * d;
        yPoints[8] = 0.;
        xPoints[9] = 16 * d;
        yPoints[9] = 0.;

        double xnew, ynew;
        for (int i = 0; i < 10; i++)  //rotating coordinates
        {
            xnew = xPoints[i] * Math.cos(phi) + yPoints[i] * Math.sin(phi);
            ynew = -xPoints[i] * Math.sin(phi) + yPoints[i] * Math.cos(phi);
            xPoints[i] = xnew;
            yPoints[i] = ynew;
            xPoints_int[i] = (int) Math.round(80 * xPoints[i]) + Lx / 2;
            yPoints_int[i] = (int) Math.round(80 * yPoints[i]) + Ly / 2;
        }
        xPoints_int[10] = px;
        yPoints_int[10] = py;
    }//pixels()

    /* force in radial direction */
    public double force_r(double r, double rp) {
        return D / m * (r0 - r) + omega * omega * r - 0.1 * reib * rp;
    }//force_r()

    public void runge_step_r() {
        k[0] = dt * rp;
        l[0] = dt * force_r(r, rp);
        k[1] = dt * (rp + l[0] / 2);
        l[1] = dt * force_r(r + k[0] / 2, rp + l[0] / 2);
        k[2] = dt * (rp + l[1] / 2);
        l[2] = dt * force_r(r + k[1] / 2, rp + l[1] / 2);
        k[3] = dt * (rp + l[2]);
        l[3] = dt * force_r(r + k[2], rp + l[2]);
    }//runge_step_r()

    public void update(int step){
        phi = phi0 + omega * step * dt; //increase phi with an offset of phi0
        runge_step_r(); //Runge-Kutta-calculation
        r = r + (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6; //new r
        rp = rp + (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6; //new rp
        pixels();
        repaint();

    }

    public void setSpeedSlider(int value){
        phi0 = phi; //save the current value for phi
        startwert[1] = (60. - (double) value) / 10.; //new omega
        omega = startwert[1];
        pixels();
        repaint();
    }

    public void state1(){
        startwert[0] = 1.; //r
        startwerte();
        pixels();
        repaint();
    }
    public void state2(){
        startwert[0] = 0.7; //r
        startwerte();
        pixels();
        repaint();
    }
    public void state3(){
        startwert[0] = 0.5; //r
        startwerte();
        pixels();
        repaint();
    }

    public void plusFriction() {
        reib++;
        repaint();
    }

    public void minusFriction() {
        if (reib > 0) {
            reib--;
        }
        repaint();
    }

    public void updateSize(int width, int height) {
        this.Lx = width;
        this.Ly = height;
    }
}

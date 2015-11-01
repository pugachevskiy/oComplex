package com.openComplex.app.DynamicalSystems.Pendulums.PendulumWithFreeMounting;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;

/**
 *  on 12/10/15.
 */
public class PendulumWithFreeMountingModel extends PendulumsModel {
    private int ax, px, py; //pixelcoordinates
    private int step;

    public PendulumWithFreeMountingModel() {
        startwert[0] = Math.PI / 180. * 175.; //phi
        startwert[1] = 0.0; //omega
        startwert[2] = 1.2; //a
        startwert[3] = 10.0; //mratio
        startwert_reib = 1; //reib
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public void startwerte()  //method for setting initial values
    {
        phi = startwert[0];
        omega = startwert[1];
        a = startwert[2];
        mratio = startwert[3];
        reib = startwert_reib;
        ap = 0.0;
    }//startwerte()

    @Override
    public void pixels()  //method for calculating pixelcoords
    {
        ax = (int) Math.round((double) (Lx / 2) + 100 * a);
        px = (int) Math.round((double) (Lx / 2) + 100 * a + 100 * Math.sin(phi));
        py = (int) Math.round((double) (Ly / 2) + 100 * Math.cos(phi));
    }//pixels()

    public double forcea(double phi, double omega) {
        return ((1. / (1. + 1. / mratio)) * omega * omega * Math.sin(phi) + 10. * Math.sin(phi) * Math.cos(phi)) /
                (1. - (1. / (1. + 1. / mratio)) * Math.cos(phi) * Math.cos(phi)) - 0.1 * reib * ap;
    }//forcea()

    @Override
    /* force on phi */
    public double forcephi(double phi, double omega) {
        return -((1. / (1. + 1. / mratio)) * omega * omega * Math.sin(phi) * Math.cos(phi) + 10. * Math.sin(phi)) /
                (1. - (1. / (1. + 1. / mratio)) * Math.cos(phi) * Math.cos(phi)) - 0.1 * reib * omega;
    }//forcephi()

    @Override
    public double energy() {
        return 0.5 * ap * ap + 0.5 * mratio * (ap * ap + omega * omega + 2. * ap * omega * Math.cos(phi))
                - mratio * 10. * Math.cos(phi);
    }//energy()

    public void runge_step_a() {
        k1[0] = dt * ap;
        l1[0] = dt * forcea(phi, omega);
        k1[1] = dt * (ap + l1[0] / 2);
        l1[1] = dt * forcea(phi + k1[0] / 2, omega + l1[0] / 2);
        k1[2] = dt * (ap + l1[1] / 2);
        l1[2] = dt * forcea(phi + k1[1] / 2, omega + l1[1] / 2);
        k1[3] = dt * (ap + l1[2]);
        l1[3] = dt * forcea(phi + k1[2], omega + l1[2]);
    }//runge_step_a()

    @Override
    public void runge_step_phi() {
        k2[0] = dt * omega;
        l2[0] = dt * forcephi(phi, omega);
        k2[1] = dt * (omega + l2[0] / 2);
        l2[1] = dt * forcephi(phi + k2[0] / 2, omega + l2[0] / 2);
        k2[2] = dt * (omega + l2[1] / 2);
        l2[2] = dt * forcephi(phi + k2[1] / 2, omega + l2[1] / 2);
        k2[3] = dt * (omega + l2[2]);
        l2[3] = dt * forcephi(phi + k2[2], omega + l2[2]);
    }//runge_step_phi()

    @Override
    public void update() {
        runge_step_a(); //Runge-Kutta
        runge_step_phi(); //Runge-Kutta
        a = a + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        ap = ap + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi = phi + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega = omega + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
        pixels();
        repaint();
    }


    public void phiPlus() {
        startwert[0] = startwert[0] + Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void phiMinus() {
        startwert[0] = startwert[0] - Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void aPlus() {
        startwert[2] += .1;
        startwerte(); //set initial values
        pixels(); //calculate pixelcoords
        repaint();
    }

    public void aMinus() {
        startwert[2] -= .1;
        startwerte(); //set initial values
        pixels(); //calculate pixelcoords
        repaint();
    }

    public void mPlus() {
        if (mratio < 100) {
            startwert[3]++;
            mratio = startwert[3];
        }
        startwerte();
        pixels();
        repaint();
    }

    public void mMinus() {
        if (mratio > 1) {
            startwert[3]--;
            mratio = startwert[3];
        }
        startwerte();
        pixels();
        repaint();
    }

    public void omegaPlus() {
        startwert[1]++;
        startwerte(); //set initial values
        pixels(); //calculate pixelcoords
        repaint();
    }

    public void omegaMinus() {
        startwert[1]--;
        startwerte(); //set initial values
        pixels(); //calculate pixelcoords
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Lx, Ly + 50);
        g.setColor(Color.blue);
        g.drawString("Energy:", 20, Ly + 20);
        g.drawString("" + (double) Math.round(10 * energy()) / 10, 20, Ly + 40);
        g.drawString("Friction:", 100, Ly + 20);
        g.drawString("" + reib, 100, Ly + 40);
        g.drawString("m1/m2:", 190, Ly + 20);
        g.drawString("" + mratio, 190, Ly + 40);
        g.drawString("Phi:", 290, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi * 180 / Math.PI) / 10 + "o", 290, Ly + 40);
        g.drawString("Omega:", 370, Ly + 20);
        g.drawString("" + (double) Math.round(10 * omega) / 10, 370, Ly + 40);
        g.drawString("a:", 450, Ly + 20);
        g.drawString("" + (double) Math.round(10 * a) / 10, 450, Ly + 40);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        g.setColor(Color.black);
        g.drawString("Step " + step, 20, 10);
        g.setColor(Color.red);
        g.drawLine(ax, Ly / 2, px, py);
        g.setColor(Color.black);
        g.fillRect(ax - 4, Ly / 2 - 2, 9, 5);
        g.fillOval((int) (px - 5 * Math.pow(mratio, 1 / 3.)), (int) (py - 5 * Math.pow(mratio, 1 / 3.)),
                (int) (11 * Math.pow(mratio, 1 / 3.)), (int) (11 * Math.pow(mratio, 1 / 3.)));

    }
}

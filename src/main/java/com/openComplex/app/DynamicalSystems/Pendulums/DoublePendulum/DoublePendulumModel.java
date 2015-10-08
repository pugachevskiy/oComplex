package com.openComplex.app.DynamicalSystems.Pendulums.DoublePendulum;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 08/10/15.
 */
public class DoublePendulumModel extends JPanel {

    private static final int Lx = 500, Ly = 330; //gic window
    private int px1, px2, py1, py2; //pixelcoordinates
    private double phi1, omega1; //coordinates and velocities
    private double phi2, omega2; //coordinates and velocities
    private double m1, m2; //masses
    private int reib; //friction
    private static final double grav = 9.81; //Gravity
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double startwert[] = new double[4]; //save initial values
    private int startwert_reib; //save initial friction

    public DoublePendulumModel() {
        startwert[0] = Math.PI / 180 * 170.; //phi1
        startwert[1] = Math.PI / 180 * 130.; //phi2
        startwert[2] = 1.0; //m1
        startwert[3] = 1.0; //m2
        startwert_reib = 0; //reib
    }


    public void startwerte() //initial values
    {
        phi1 = startwert[0];
        phi2 = startwert[1];
        m1 = startwert[2];
        m2 = startwert[3];
        reib = startwert_reib;
        omega1 = 0.0;
        omega2 = 0.0;
    }//startwerte()

    public void pixels() //method for calculating pixelcoordinates
    {
        px1 = (int) Math.round((double) (Lx / 2) + 80 * Math.sin(phi1));
        py1 = (int) Math.round((double) (Ly / 2) + 80 * Math.cos(phi1));
        px2 = (int) Math.round((double) px1 + 80 * Math.sin(phi2));
        py2 = (int) Math.round((double) py1 + 80 * Math.cos(phi2));
    }//pixels()

    /* force on Phi_1 */
    public double forcephi1(double phi1, double phi2, double omega1, double omega2) {
        return -(grav * (2 * m1 * Math.sin(phi1)
                + m2 * Math.sin(phi1)
                + m2 * Math.sin(phi1 - 2 * phi2)
        )
                + m2 * omega1 * omega1 * Math.sin(2 * phi1 - 2 * phi2)
                + 2 * m2 * omega2 * omega2 * Math.sin(phi1 - phi2)
        ) / (2 * m1 + m2 - m2 * Math.cos(2 * phi1 - 2 * phi2))
                - 0.1 * reib * omega1;
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(double phi1, double phi2, double omega1, double omega2) {
        return (2 * Math.sin(phi1 - phi2)
                * (grav * (m1 + m2) * Math.cos(phi1)
                + (m1 + m2) * omega1 * omega1
                + m2 * omega2 * omega2 * Math.cos(phi1 - phi2)
        )
        ) / (2 * m1 + m2 - m2 * Math.cos(2 * phi1 - 2 * phi2))
                - 0.1 * reib * omega2;
    }//forcephi2()

    public double energy() {
        return 0.5 * m1 * omega1 * omega1 + 0.5 * m2 * omega2 * omega2 + 0.5 * m2 * omega1 * omega1
                + m2 * omega1 * omega2 * Math.cos(phi1 - phi2)
                - grav * (m1 * Math.cos(phi1) + m2 * Math.cos(phi1) + m2 * Math.cos(phi2));
    }//energy()

    public void runge_step_phi1() {
        k1[0] = dt * omega1;
        l1[0] = dt * forcephi1(phi1, phi2, omega1, omega2);
        k1[1] = dt * (omega1 + l1[0] / 2);
        l1[1] = dt * forcephi1(phi1 + k1[0] / 2, phi2, omega1 + l1[0] / 2, omega2);
        k1[2] = dt * (omega1 + l1[1] / 2);
        l1[2] = dt * forcephi1(phi1 + k1[1] / 2, phi2, omega1 + l1[1] / 2, omega2);
        k1[3] = dt * (omega1 + l1[2]);
        l1[3] = dt * forcephi1(phi1 + k1[2], phi2, omega1 + l1[2], omega2);
    }//runge_step_phi1()

    public void runge_step_phi2() {
        k2[0] = dt * omega2;
        l2[0] = dt * forcephi2(phi1, phi2, omega1, omega2);
        k2[1] = dt * (omega2 + l2[0] / 2);
        l2[1] = dt * forcephi2(phi1, phi2 + k2[0] / 2, omega1, omega2 + l2[0] / 2);
        k2[2] = dt * (omega2 + l2[1] / 2);
        l2[2] = dt * forcephi2(phi1, phi2 + k2[1] / 2, omega1, omega2 + l2[1] / 2);
        k2[3] = dt * (omega2 + l2[2]);
        l2[3] = dt * forcephi2(phi1, phi2 + k2[2], omega1, omega2 + l2[2]);
    }//runge_step_phi2()

    public void update() {
        runge_step_phi1(); //Runge-Kutta
        runge_step_phi2(); //Runge-Kutta
        phi1 = phi1 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        omega1 = omega1 + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi2 = phi2 + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega2 = omega2 + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
        pixels();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Lx, Ly + 50);
        g.setColor(Color.blue);
        g.drawString("Energy:", 30, Ly + 20);
        g.drawString("" + (double) Math.round(10 * energy()) / 10, 30, Ly + 40);
        g.drawString("Friction:", 120, Ly + 20);
        g.drawString("" + reib, 120, Ly + 40);
        g.drawString("m1:", 210, Ly + 20);
        g.drawString("" + (double) Math.round(100 * m1) / 100, 210, Ly + 40);
        g.drawString("m2:", 270, Ly + 20);
        g.drawString("" + (double) Math.round(100 * m2) / 100, 270, Ly + 40);
        g.drawString("Phi1:", 350, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi1 * 180 / Math.PI) / 10 + "o", 350, Ly + 40);
        g.drawString("Phi2:", 420, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi2 * 180 / Math.PI) / 10 + "o", 420, Ly + 40);
        g.setColor(Color.black);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        //g.drawString("Step " + step, 20, 10);
        g.setColor(Color.red);
        g.drawLine(Lx / 2, Ly / 2, px1, py1);
        g.drawLine(px1, py1, px2, py2);
        g.setColor(Color.black);
        g.fillOval((int) (px1 - 7 * Math.pow(m1, 1. / 3.)), (int) (py1 - 7 * Math.pow(m1, 1. / 3.)),
                (int) (15 * Math.pow(m1, 1. / 3.)), (int) (15 * Math.pow(m1, 1. / 3.)));
        g.fillOval((int) (px2 - 7 * Math.pow(m2, 1. / 3.)), (int) (py2 - 7 * Math.pow(m2, 1. / 3.)),
                (int) (15 * Math.pow(m2, 1. / 3.)), (int) (15 * Math.pow(m2, 1. / 3.)));
    } //paint()

    public void phi1Plus() {
        startwert[0] = startwert[0] + Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void phi1Minus() {
        startwert[0] = startwert[0] - Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void phi2Plus() {
        startwert[1] = startwert[1] + Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void phi2Minus() {
        startwert[1] = startwert[1] - Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void frictionPlus() {
        startwert_reib++;
        reib = startwert_reib;
        repaint();
    }

    public void frictionMinus() {
        if (reib > 0) {
            startwert_reib--;
            reib = startwert_reib;
        }
        repaint();
    }

    public void m1Plus() {
        startwert[2] = startwert[2] / 0.5;
        m1 = startwert[2];
        repaint();
    }

    public void m1Minus() {
        if (m1 / m2 > 0.1) {
            startwert[2] = startwert[2] * 0.5;
            m1 = startwert[2];
        }
        repaint();
    }

    public void m2Plus() {
        if (m1 / m2 > 0.1) {
            startwert[3] = startwert[3] / 0.5;
            m2 = startwert[3];
        }
        repaint();
    }

    public void m2Minus() {
        startwert[3] = startwert[3] * 0.5;
        m2 = startwert[3];
        repaint();
    }


}

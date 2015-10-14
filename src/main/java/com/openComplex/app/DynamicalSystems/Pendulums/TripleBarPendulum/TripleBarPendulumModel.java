package com.openComplex.app.DynamicalSystems.Pendulums.TripleBarPendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;

/**
 * Created by strange on 13/10/15.
 */
public class TripleBarPendulumModel extends PendulumsModel {
    private int xPoints[] = new int[4]; //pixelcoords
    private int yPoints[] = new int[4]; //pixelcoords
    private int step;


    public TripleBarPendulumModel() {
        startwert[0] = Math.PI / 180 * 130.; //phi1
        startwert[1] = Math.PI / 180 * 130.; //phi2
        startwert[2] = Math.PI / 180 * 130.; //phi3
        startwert_reib = 0; //reib

        xPoints[0] = Lx / 2;
        yPoints[0] = Ly / 2; //mounting point
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public void pixels()  //method for calculating pixelcoords
    {
        xPoints[1] = (int) Math.round((double) xPoints[0] + 60 * Math.sin(phi1));
        yPoints[1] = (int) Math.round((double) yPoints[0] + 60 * Math.cos(phi1));
        xPoints[2] = (int) Math.round((double) xPoints[1] + 60 * Math.sin(phi2));
        yPoints[2] = (int) Math.round((double) yPoints[1] + 60 * Math.cos(phi2));
        xPoints[3] = (int) Math.round((double) xPoints[2] + 60 * Math.sin(phi3));
        yPoints[3] = (int) Math.round((double) yPoints[2] + 60 * Math.cos(phi3));
    }//pixels()

    public void startwerte() //method for setting initial values
    {
        phi1 = startwert[0];
        omega1 = 0.0;
        phi2 = startwert[1];
        omega2 = 0.0;
        phi3 = startwert[2];
        omega3 = 0.0;
        reib = startwert_reib;
    }//startwerte()

    @Override
    public void update() {
        runge_step_phi1(); //Runge-Kutta
        runge_step_phi2(); //Runge-Kutta
        runge_step_phi3(); //Runge-Kutta
        phi1 = phi1 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        omega1 = omega1 + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi2 = phi2 + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega2 = omega2 + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
        phi3 = phi3 + (k3[0] + 2 * k3[1] + 2 * k3[2] + k3[3]) / 6; //Runge-Kutta
        omega3 = omega3 + (l3[0] + 2 * l3[1] + 2 * l3[2] + l3[3]) / 6; //Runge-Kutta
        pixels();
        repaint();
    }


    /* force on Phi_1 */
    public double forcephi1(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3) {
        return (3 * (64 * grav * Math.sin(phi1)
                - 3 * grav * (6 * Math.cos(2 * (phi2 - phi3)) * Math.sin(phi1)
                - 9 * Math.sin(phi1 - 2 * phi2) + Math.sin(phi1 - 2 * phi3))
                - 3 * omega2 * omega2 * (-15 * Math.sin(phi1 - phi2)
                + Math.sin(phi1 + phi2 - 2 * phi3))
                - 3 * omega1 * omega1 * (-9 * Math.sin(2 * (phi1 - phi2))
                + Math.sin(2 * (phi1 - phi3)))
                + omega3 * omega3 * (5 * Math.sin(phi1 - phi3)
                + 9 * Math.sin(phi1 - 2 * phi2 + phi3)))) /
                (-169. + 81 * Math.cos(2 * (phi1 - phi2))
                        - 9 * Math.cos(2 * (phi1 - phi3))
                        + 45 * Math.cos(2 * (phi2 - phi3))) - 0.1 * reib * omega1;

    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3) {
        return (3 * (-54 * grav * Math.sin(2 * phi1 - phi2)
                + 45 * grav * Math.sin(phi2) + 6 * grav * Math.sin(phi2 - 2 * phi3)
                + 3 * omega1 * omega1 * (-33 * Math.sin(phi1 - phi2)
                + 5 * Math.sin(phi1 + phi2 - 2 * phi3))
                + 9 * grav * Math.sin(2 * phi1 + phi2 - 2 * phi3)
                + omega3 * omega3 * (-9 * Math.sin(2 * phi1 - phi2 - phi3)
                + 29 * Math.sin(phi2 - phi3))
                + 3 * omega2 * omega2 * (-9 * Math.sin(2 * (phi1 - phi2))
                + 5 * Math.sin(2 * (phi2 - phi3))))) /
                (-169. + 81 * Math.cos(2 * (phi1 - phi2))
                        - 9 * Math.cos(2 * (phi1 - phi3))
                        + 45 * Math.cos(2 * (phi2 - phi3))) - 0.1 * reib * omega2;

    }//forcephi2()

    /* force on Phi_3 */
    public double forcephi3(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3) {
        return (3 * (omega2 * omega2 * (9 * Math.sin(2 * phi1 - phi2 - phi3)
                - 71 * Math.sin(phi2 - phi3))
                + 3 * omega3 * omega3 * (Math.sin(2 * (phi1 - phi3))
                - 5 * Math.sin(2 * (phi2 - phi3)))
                + omega1 * omega1 * (-17 * Math.sin(phi1 - phi3)
                + 45 * Math.sin(phi1 - 2 * phi2 + phi3))
                + grav * (-12 * Math.sin(2 * phi1 - phi3)
                - 18 * Math.sin(2 * phi2 - phi3) + 5 * Math.sin(phi3)
                + 27 * Math.sin(2 * phi1 - 2 * phi2 + phi3)))) /
                (-169. + 81 * Math.cos(2 * (phi1 - phi2))
                        - 9 * Math.cos(2 * (phi1 - phi3))
                        + 45 * Math.cos(2 * (phi2 - phi3))) - 0.1 * reib * omega3;

    }//forcephi3()

    public double energy() {
        return (1. / 6.) * (-3 * grav * (5 * Math.cos(phi1)
                + 3 * Math.cos(phi2)
                + Math.cos(phi3)
        )
                + 7 * omega1 * omega1 + 4 * omega2 * omega2 + omega3 * omega3
                + 9 * omega1 * omega2 * Math.cos(phi1 - phi2)
                + 3 * (omega1 * Math.cos(phi1 - phi3)
                + omega2 * Math.cos(phi2 - phi3)
        ) * omega3
        );
    }//energy()

    public void runge_step_phi1() {
        k1[0] = dt * omega1;
        l1[0] = dt * forcephi1(phi1, omega1, phi2, omega2, phi3, omega3);
        k1[1] = dt * (omega1 + l1[0] / 2);
        l1[1] = dt * forcephi1(phi1 + k1[0] / 2, omega1 + l1[0] / 2, phi2, omega2, phi3, omega3);
        k1[2] = dt * (omega1 + l1[1] / 2);
        l1[2] = dt * forcephi1(phi1 + k1[1] / 2, omega1 + l1[1] / 2, phi2, omega2, phi3, omega3);
        k1[3] = dt * (omega1 + l1[2]);
        l1[3] = dt * forcephi1(phi1 + k1[2], omega1 + l1[2], phi2, omega2, phi3, omega3);
    }//runge_step_phi1()

    public void runge_step_phi2() {
        k2[0] = dt * omega2;
        l2[0] = dt * forcephi2(phi1, omega1, phi2, omega2, phi3, omega3);
        k2[1] = dt * (omega2 + l2[0] / 2);
        l2[1] = dt * forcephi2(phi1, omega1, phi2 + k2[0] / 2, omega2 + l2[0] / 2, phi3, omega3);
        k2[2] = dt * (omega2 + l2[1] / 2);
        l2[2] = dt * forcephi2(phi1, omega1, phi2 + k2[1] / 2, omega2 + l2[1] / 2, phi3, omega3);
        k2[3] = dt * (omega2 + l2[2]);
        l2[3] = dt * forcephi2(phi1, omega1, phi2 + k2[2], omega2 + l2[2], phi3, omega3);
    }//runge_step_phi2()

    public void runge_step_phi3() {
        k3[0] = dt * omega3;
        l3[0] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3, omega3);
        k3[1] = dt * (omega3 + l3[0] / 2);
        l3[1] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[0] / 2, omega3 + l3[0] / 2);
        k3[2] = dt * (omega3 + l3[1] / 2);
        l3[2] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[1] / 2, omega3 + l3[1] / 2);
        k3[3] = dt * (omega3 + l3[2]);
        l3[3] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[2], omega3 + l3[2]);
    }//runge_step_phi3()


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

    public void phi3Plus() {
        startwert[2] = startwert[2] + Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void phi3Minus() {
        startwert[2] = startwert[2] - Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.white);
        g.fillRect(0, 0, Lx, Ly + 50);
        g.setColor(Color.blue);
        g.drawString("Energy:", 30, Ly + 20);
        g.drawString("" + (double) Math.round(10 * energy()) / 10, 30, Ly + 40);
        g.drawString("Friction:", 120, Ly + 20);
        g.drawString("" + reib, 120, Ly + 40);
        g.drawString("Phi1:", 230, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi1 * 180 / Math.PI) / 10 + "o", 230, Ly + 40);
        g.drawString("Phi2:", 320, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi2 * 180 / Math.PI) / 10 + "o", 320, Ly + 40);
        g.drawString("Phi3:", 410, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi3 * 180 / Math.PI) / 10 + "o", 410, Ly + 40);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        g.setColor(Color.black);
        g.drawString("Step " + step, 20, 10);
        g.drawPolyline(xPoints, yPoints, 4);
    }
}

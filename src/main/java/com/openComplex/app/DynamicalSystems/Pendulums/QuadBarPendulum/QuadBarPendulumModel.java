package com.openComplex.app.DynamicalSystems.Pendulums.QuadBarPendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;

/**
 *  on 13/10/15.
 */
public class QuadBarPendulumModel extends PendulumsModel {

    private int step;
    private int xPoints[] = new int[5]; //pixelcoordinates
    private int yPoints[] = new int[5]; //pixelcoordinates

    public QuadBarPendulumModel() {
        startwert[0] = Math.PI / 180 * 100.; //phi1
        startwert[1] = Math.PI / 180 * 110.; //phi2
        startwert[2] = Math.PI / 180 * 120.; //phi3
        startwert[3] = Math.PI / 180 * 130.; //phi4
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
        xPoints[1] = (int) Math.round((double) xPoints[0] + 45 * Math.sin(phi1));
        yPoints[1] = (int) Math.round((double) yPoints[0] + 45 * Math.cos(phi1));
        xPoints[2] = (int) Math.round((double) xPoints[1] + 45 * Math.sin(phi2));
        yPoints[2] = (int) Math.round((double) yPoints[1] + 45 * Math.cos(phi2));
        xPoints[3] = (int) Math.round((double) xPoints[2] + 45 * Math.sin(phi3));
        yPoints[3] = (int) Math.round((double) yPoints[2] + 45 * Math.cos(phi3));
        xPoints[4] = (int) Math.round((double) xPoints[3] + 45 * Math.sin(phi4));
        yPoints[4] = (int) Math.round((double) yPoints[3] + 45 * Math.cos(phi4));
    }//pixels()

    @Override
    public void startwerte() {
        phi1 = startwert[0];
        omega1 = 0.0;
        phi2 = startwert[1];
        omega2 = 0.0;
        phi3 = startwert[2];
        omega3 = 0.0;
        phi4 = startwert[3];
        omega4 = 0.0;
        reib = startwert_reib;
    }//startwerte()


    @Override
    public void update() {
        runge_step_phi1(); //Runge-Kutta
        runge_step_phi2(); //Runge-Kutta
        runge_step_phi3(); //Runge-Kutta
        runge_step_phi4(); //Runge-Kutta
        phi1 = phi1 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        omega1 = omega1 + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi2 = phi2 + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega2 = omega2 + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
        phi3 = phi3 + (k3[0] + 2 * k3[1] + 2 * k3[2] + k3[3]) / 6; //Runge-Kutta
        omega3 = omega3 + (l3[0] + 2 * l3[1] + 2 * l3[2] + l3[3]) / 6; //Runge-Kutta
        phi4 = phi4 + (k4[0] + 2 * k4[1] + 2 * k4[2] + k4[3]) / 6; //Runge-Kutta
        omega4 = omega4 + (l4[0] + 2 * l4[1] + 2 * l4[2] + l4[3]) / 6; //Runge-Kutta
        pixels();
        repaint();
    }

    public double forcephi1(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3,
                            double phi4, double omega4) {
        return (3 * (493 * grav * Math.sin(phi1) - 2 * omega2 * omega2 * (
                -187. + 45 * Math.cos(2 * (phi3 - phi4))) * Math.sin(phi1 - phi2)
                + 3 * omega2 * omega2 * (-9 * Math.sin(phi1 + phi2 - 2 * phi3)
                + Math.sin(phi1 + phi2 - 2 * phi4))
                + 3 * omega1 * omega1 * ((73. - 18 * Math.cos(2 * (phi3 - phi4))) * Math.sin(2 * (phi1 - phi2))
                - 9 * Math.sin(2 * (phi1 - phi3)) + Math.sin(2 * (phi1 - phi4)))
                + omega4 * omega4 * (Math.sin(phi1 - phi4)
                + 27 * Math.sin(phi1 - 2 * phi2 + 2 * phi3 - phi4)
                + 6 * Math.sin(phi1 - 2 * phi2 + phi4) + 18 * Math.sin(phi1 - 2 * phi3 + phi4))
                + 3 * omega3 * omega3 * (21 * Math.sin(phi1 - phi3) + 36 * Math.sin(phi1 - 2 * phi2 + phi3)
                - 2 * Math.sin(phi1 + phi3 - 2 * phi4) - 3 * Math.sin(phi1 - 2 * phi2 - phi3 + 2 * phi4))
                + 3 * grav * (73 * Math.sin(phi1 - 2 * phi2) - 9 * Math.sin(phi1 - 2 * phi3)
                - 27 * Math.sin(phi1 + 2 * phi2 - 2 * phi3) - 27 * Math.sin(phi1 - 2 * phi2 + 2 * phi3)
                - 9 * Math.sin(phi1 - 2 * (phi2 + phi3 - phi4)) + Math.sin(phi1 - 2 * phi4)
                + 3 * (Math.sin(phi1 + 2 * phi2 - 2 * phi4) - 7 * Math.sin(phi1 + 2 * phi3 - 2 * phi4)
                + Math.sin(phi1 - 2 * phi2 + 2 * phi4) - 7 * Math.sin(phi1 - 2 * phi3 + 2 * phi4)
                - 3 * Math.sin(phi1 - 2 * (phi2 - phi3 + phi4)))))) /
                (-1310. + 657 * Math.cos(2 * (phi1 - phi2)) - 81 * Math.cos(2 * (phi1 - phi3))
                        + 405 * Math.cos(2 * (phi2 - phi3)) + 9 * Math.cos(2 * (phi1 - phi4))
                        - 45 * Math.cos(2 * (phi2 - phi4)) + 333 * Math.cos(2 * (phi3 - phi4))
                        - 81 * Math.cos(2 * (phi1 - phi2 + phi3 - phi4))
                        - 81 * Math.cos(2 * (phi1 - phi2 - phi3 + phi4))) - 0.1 * reib * omega1;
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3,
                            double phi4, double omega4) {
        return (-3 * (758 * omega1 * omega1 * Math.sin(phi1 - phi2)
                - 18 * Math.cos(2 * (phi3 - phi4)) * (11 * omega1 * omega1 * Math.sin(phi1 - phi2)
                + 3 * omega2 * omega2 * Math.sin(2 * (phi1 - phi2))
                + 6 * grav * Math.sin(2 * phi1 - phi2) - 5 * grav * Math.sin(phi2))
                + 15 * omega1 * omega1 * (-9 * Math.sin(phi1 + phi2 - 2 * phi3)
                + Math.sin(phi1 + phi2 - 2 * phi4)) + grav * (411 * Math.sin(2 * phi1 - phi2)
                - 347 * Math.sin(phi2) - 54 * Math.sin(phi2 - 2 * phi3)
                - 81 * Math.sin(2 * phi1 + phi2 - 2 * phi3) + 6 * Math.sin(phi2 - 2 * phi4)
                + 9 * Math.sin(2 * phi1 + phi2 - 2 * phi4))
                + 3 * omega3 * omega3 * (36 * Math.sin(2 * phi1 - phi2 - phi3)
                - 3 * (37 * Math.sin(phi2 - phi3) + Math.sin(2 * phi1 - phi2 + phi3 - 2 * phi4))
                + 8 * Math.sin(phi2 + phi3 - 2 * phi4))
                + 3 * omega2 * omega2 * (73 * Math.sin(2 * (phi1 - phi2))
                + 5 * (-9 * Math.sin(2 * (phi2 - phi3)) + Math.sin(2 * (phi2 - phi4))))
                + omega4 * omega4 * (6 * Math.sin(2 * phi1 - phi2 - phi4)
                - 31 * Math.sin(phi2 - phi4) + 27 * Math.sin(2 * phi1 - phi2 - 2 * phi3 + phi4)
                - 72 * Math.sin(phi2 - 2 * phi3 + phi4)))) /
                (-1310. + 657 * Math.cos(2 * (phi1 - phi2))
                        - 81 * Math.cos(2 * (phi1 - phi3)) + 405 * Math.cos(2 * (phi2 - phi3))
                        + 9 * Math.cos(2 * (phi1 - phi4)) - 45 * Math.cos(2 * (phi2 - phi4))
                        + 333 * Math.cos(2 * (phi3 - phi4))
                        - 81 * Math.cos(2 * (phi1 - phi2 + phi3 - phi4))
                        - 81 * Math.cos(2 * (phi1 - phi2 - phi3 + phi4))) - 0.1 * reib * omega2;
    }//forcephi2()

    /* force on Phi_3 */
    public double forcephi3(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3,
                            double phi4, double omega4) {
        return (3 * (3 * omega2 * omega2 * (18 * Math.sin(2 * phi1 - phi2 - phi3)
                - 3 * (49 * Math.sin(phi2 - phi3) + Math.sin(2 * phi1 - phi2 + phi3 - 2 * phi4))
                + 22 * Math.sin(phi2 + phi3 - 2 * phi4))
                + omega4 * omega4 * (9 * Math.sin(2 * phi1 - phi3 - phi4)
                - 45 * Math.sin(2 * phi2 - phi3 - phi4) + 14 * (
                +17. - 9 * Math.cos(2 * (phi1 - phi2))) * Math.sin(phi3 - phi4))
                + 3 * omega3 * omega3 * (9 * Math.sin(2 * (phi1 - phi3))
                - 45 * Math.sin(2 * (phi2 - phi3)) + (
                +37. - 18 * Math.cos(2 * (phi1 - phi2))) * Math.sin(2 * (phi3 - phi4)))
                + 3 * omega1 * omega1 * (-39 * Math.sin(phi1 - phi3)
                + 90 * Math.sin(phi1 - 2 * phi2 + phi3) + 4 * Math.sin(phi1 + phi3 - 2 * phi4)
                - 15 * Math.sin(phi1 - 2 * phi2 - phi3 + 2 * phi4))
                + 3 * grav * (-27 * Math.sin(2 * phi1 - phi3) - 36 * Math.sin(2 * phi2 - phi3)
                + 12 * Math.sin(phi3) + 54 * Math.sin(2 * phi1 - 2 * phi2 + phi3)
                + Math.sin(phi3 - 2 * phi4) + 3 * Math.sin(2 * phi1 + phi3 - 2 * phi4)
                + 6 * Math.sin(2 * phi2 + phi3 - 2 * phi4)
                - 9 * Math.sin(2 * phi1 - 2 * phi2 - phi3 + 2 * phi4)))) /
                (-1310. + 657 * Math.cos(2 * (phi1 - phi2))
                        - 81 * Math.cos(2 * (phi1 - phi3)) + 405 * Math.cos(2 * (phi2 - phi3))
                        + 9 * Math.cos(2 * (phi1 - phi4)) - 45 * Math.cos(2 * (phi2 - phi4))
                        + 333 * Math.cos(2 * (phi3 - phi4))
                        - 81 * Math.cos(2 * (phi1 - phi2 + phi3 - phi4))
                        - 81 * Math.cos(2 * (phi1 - phi2 - phi3 + phi4))) - 0.1 * reib * omega3;
    }//forcephi3()

    /* force on Phi_4 */
    public double forcephi4(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3,
                            double phi4, double omega4) {
        return (-3 * (omega3 * omega3 * (9 * Math.sin(2 * phi1 - phi3 - phi4)
                - 45 * Math.sin(2 * phi2 - phi3 - phi4) + 2 * (
                +251. - 117 * Math.cos(2 * (phi1 - phi2))) * Math.sin(phi3 - phi4))
                + 3 * omega4 * omega4 * (Math.sin(2 * (phi1 - phi4)) - 5 * Math.sin(2 * (phi2 - phi4))
                + (37. - 18 * Math.cos(2 * (phi1 - phi2))) * Math.sin(2 * (phi3 - phi4)))
                + omega1 * omega1 * (Math.sin(phi1 - phi4)
                + 135 * Math.sin(phi1 - 2 * phi2 + 2 * phi3 - phi4)
                - 60 * Math.sin(phi1 - 2 * phi2 + phi4)
                - 36 * Math.sin(phi1 - 2 * phi3 + phi4))
                + omega2 * omega2 * (-12 * Math.sin(2 * phi1 - phi2 - phi4)
                + 73 * Math.sin(phi2 - phi4) + 27 * Math.sin(2 * phi1 - phi2 - 2 * phi3 + phi4)
                - 198 * Math.sin(phi2 - 2 * phi3 + phi4)) + grav * (3 * Math.sin(2 * phi1 - phi4)
                + 24 * Math.sin(2 * phi2 - phi4) + 9 * Math.sin(2 * phi3 - phi4)
                + 81 * Math.sin(2 * (phi1 - phi2 + phi3) - phi4) + 2 * Math.sin(phi4)
                - 9 * (4 * Math.sin(2 * phi1 - 2 * phi2 + phi4) + 3 * Math.sin(2 * phi1 - 2 * phi3 + phi4)
                + 6 * Math.sin(2 * phi2 - 2 * phi3 + phi4))))) /
                (-1310. + 657 * Math.cos(2 * (phi1 - phi2))
                        - 81 * Math.cos(2 * (phi1 - phi3)) + 405 * Math.cos(2 * (phi2 - phi3))
                        + 9 * Math.cos(2 * (phi1 - phi4)) - 45 * Math.cos(2 * (phi2 - phi4))
                        + 333 * Math.cos(2 * (phi3 - phi4)) - 81 * Math.cos(2 * (phi1 - phi2 + phi3 - phi4))
                        - 81 * Math.cos(2 * (phi1 - phi2 - phi3 + phi4))) - 0.1 * reib * omega4;
    }//forcephi4()

    public double energy() {
        return (10 * omega1 * omega1 + 7 * omega2 * omega2 + 4 * omega3 * omega3 + omega4 * omega4
                + 3 * (5 * omega1 * omega2 * Math.cos(phi1 - phi2)
                + 3 * omega1 * omega3 * Math.cos(phi1 - phi3)
                + 3 * omega2 * omega3 * Math.cos(phi2 - phi3)
                + omega1 * omega4 * Math.cos(phi1 - phi4) + omega2 * omega4 * Math.cos(phi2 - phi4)
                + omega3 * omega4 * Math.cos(phi3 - phi4)) - 3 * grav * (7 * Math.cos(phi1)
                + 5 * Math.cos(phi2) + 3 * Math.cos(phi3) + Math.cos(phi4))) / 6.;
    }//energy()

    public void runge_step_phi1() {
        k1[0] = dt * omega1;
        l1[0] = dt * forcephi1(phi1, omega1, phi2, omega2, phi3, omega3, phi4, omega4);
        k1[1] = dt * (omega1 + l1[0] / 2);
        l1[1] = dt * forcephi1(phi1 + k1[0] / 2, omega1 + l1[0] / 2, phi2, omega2, phi3, omega3, phi4, omega4);
        k1[2] = dt * (omega1 + l1[1] / 2);
        l1[2] = dt * forcephi1(phi1 + k1[1] / 2, omega1 + l1[1] / 2, phi2, omega2, phi3, omega3, phi4, omega4);
        k1[3] = dt * (omega1 + l1[2]);
        l1[3] = dt * forcephi1(phi1 + k1[2], omega1 + l1[2], phi2, omega2, phi3, omega3, phi4, omega4);
    }//runge_step_phi1()

    public void runge_step_phi2() {
        k2[0] = dt * omega2;
        l2[0] = dt * forcephi2(phi1, omega1, phi2, omega2, phi3, omega3, phi4, omega4);
        k2[1] = dt * (omega2 + l2[0] / 2);
        l2[1] = dt * forcephi2(phi1, omega1, phi2 + k2[0] / 2, omega2 + l2[0] / 2, phi3, omega3, phi4, omega4);
        k2[2] = dt * (omega2 + l2[1] / 2);
        l2[2] = dt * forcephi2(phi1, omega1, phi2 + k2[1] / 2, omega2 + l2[1] / 2, phi3, omega3, phi4, omega4);
        k2[3] = dt * (omega2 + l2[2]);
        l2[3] = dt * forcephi2(phi1, omega1, phi2 + k2[2], omega2 + l2[2], phi3, omega3, phi4, omega4);
    }//runge_step_phi2()

    public void runge_step_phi3() {
        k3[0] = dt * omega3;
        l3[0] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3, omega3, phi4, omega4);
        k3[1] = dt * (omega3 + l3[0] / 2);
        l3[1] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[0] / 2, omega3 + l3[0] / 2, phi4, omega4);
        k3[2] = dt * (omega3 + l3[1] / 2);
        l3[2] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[1] / 2, omega3 + l3[1] / 2, phi4, omega4);
        k3[3] = dt * (omega3 + l3[2]);
        l3[3] = dt * forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[2], omega3 + l3[2], phi4, omega4);
    }//runge_step_phi3()

    public void runge_step_phi4() {
        k4[0] = dt * omega4;
        l4[0] = dt * forcephi4(phi1, omega1, phi2, omega2, phi3, omega3, phi4, omega4);
        k4[1] = dt * (omega4 + l4[0] / 2);
        l4[1] = dt * forcephi4(phi1, omega1, phi2, omega2, phi3, omega3, phi4 + k4[0] / 2, omega4 + l4[0] / 2);
        k4[2] = dt * (omega4 + l4[1] / 2);
        l4[2] = dt * forcephi4(phi1, omega1, phi2, omega2, phi3, omega3, phi4 + k4[1] / 2, omega4 + l4[1] / 2);
        k4[3] = dt * (omega4 + l4[2]);
        l4[3] = dt * forcephi4(phi1, omega1, phi2, omega2, phi3, omega3, phi4 + k4[2], omega4 + l4[2]);
    }//runge_step_phi4()


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

    public void phi4Plus() {
        startwert[3] = startwert[3] + Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    public void phi4Minus() {
        startwert[3] = startwert[3] - Math.PI / 180 * 5;
        startwerte();
        pixels();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Lx, Ly + 50);
        g.setColor(Color.blue);
        g.drawString("Energy:", 25, Ly + 20);
        g.drawString("" + (double) Math.round(10 * energy()) / 10, 25, Ly + 40);
        g.drawString("Friction:", 100, Ly + 20);
        g.drawString("" + reib, 100, Ly + 40);
        g.drawString("Phi1:", 195, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi1 * 180 / Math.PI) / 10 + "o", 195, Ly + 40);
        g.drawString("Phi2:", 270, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi2 * 180 / Math.PI) / 10 + "o", 270, Ly + 40);
        g.drawString("Phi3:", 345, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi3 * 180 / Math.PI) / 10 + "o", 345, Ly + 40);
        g.drawString("Phi4:", 420, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi4 * 180 / Math.PI) / 10 + "o", 420, Ly + 40);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        g.setColor(Color.black);
        g.drawString("Step " + step, 20, 10);
        g.drawPolyline(xPoints, yPoints, 5);
    }
}

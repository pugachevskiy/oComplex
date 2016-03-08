package com.openComplex.app.DynamicalSystems.Pendulums;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 *  on 09/10/15.
 */
public abstract class PendulumsModel extends JPanel {
    public static final int Lx = 500, Ly = 330; //graphic window
    public int px1, px2, px3, py1, py2, py3; //pixelcoordinates
    public double phi1, omega1; //coordinates and velocities
    public double phi2, omega2; //coordinates and velocities
    public double phi3, omega3; //coordinates and velocities
    public double phi4, omega4; //coordinates and velocities
    public double m1, m2; //masses
    public int reib; //friction
    public static final double grav = 9.81; //Gravity
    public static final double dt = 0.0005; //Runge-Kutta-timestep
    public double l[] = new double[4]; //Runge-Kutta
    public double l1[] = new double[4]; //Runge-Kutta
    public double l2[] = new double[4]; //Runge-Kutta
    public double l3[] = new double[4]; //Runge-Kutta
    public double l4[] = new double[4]; //Runge-Kutta
    public double k[] = new double[4]; //Runge-Kutta
    public double k1[] = new double[4]; //Runge-Kutta
    public double k2[] = new double[4]; //Runge-Kutta
    public double k3[] = new double[4]; //Runge-Kutta
    public double k4[] = new double[4]; //Runge-Kutta
    public double startwert[] = new double[4]; //save initial values
    public int startwert_reib; //save initial friction
    public double phi, omega; //coordinates and velocities
    public double a, ap, aforce; //coordinates and velocities
    public double freq, amp; //frequency and amplitude
    public double mratio; //mass-ratio

    private int trajectories;
    private int limit;
    private int queueIndex;

    private java.util.List<Integer> attractorList1X;
    private java.util.List<Integer> attractorList1Y;
    private java.util.List<Integer> attractorList2X;
    private java.util.List<Integer> attractorList2Y;
    private java.util.List<Integer> attractorList3X;
    private java.util.List<Integer> attractorList3Y;
    private java.util.List<Integer> attractorList4X;
    private java.util.List<Integer> attractorList4Y;

    private static int attractorSize = 2;

    private Color[] colors;

    private List[] attractors = {attractorList1X, attractorList1Y, attractorList2X, attractorList2Y, attractorList3X,
                                    attractorList3Y, attractorList4X, attractorList4Y};

    public void setTrajectory(int numberOfTrajectories, int limit) {
        queueIndex = 0;
        this.limit = limit;
        trajectories = numberOfTrajectories;
        for(int i = 0; i < 2*trajectories; i++) {
            attractors[i] = new LinkedList<Integer>();
        }
    }

    public void resetAttractor() {
        for(int i = 0; i < 2*trajectories; i++) {
            attractors[i].removeAll(attractors[i]);
        }
        queueIndex = 0;
    }

    public void attractorAdd(int[] xPoints, int[] yPoints) {
        for(int i = 0; i < trajectories; i++) {
            attractors[i*2].add(xPoints[i+1]);
            attractors[i*2+1].add(yPoints[i + 1]);
        }
        if(queueIndex <= limit) {
            queueIndex += 1;
        }
    }
    public void attractorPaint(Graphics g) {
        int x, y;
        for(int i = 0; i < limit; i++) {
            if(queueIndex > i) {
                for(int j = 0; j < trajectories; j++) {
                    g.setColor(colors[j]);
                    x = (int) attractors[j*2].get(i);
                    y = (int) attractors[j*2+1].get(i);
                    g.fillOval(x, y, attractorSize, attractorSize);
                }
            }
        }
    }

    public void attractorRemove() {
        if(queueIndex >= limit) {
            for(int i = 0; i < 2*trajectories; i++) {
                attractors[i].remove(0);
            }
        }
    }
    public int getLimit() {
        return limit;
    }

    public void setTrajectoryColors(Color[] colors) {
        this.colors = colors;
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

    public void startwerteDriven()  //method for setting initial values
    {
        phi = 0.0;
        omega = 0.0;
        reib = startwert_reib;
        amp = startwert[0];
        freq = startwert[1];

    }//startwerte()

    public void startwerteDrivenY()  //method for setting initial values
    {
        amp = startwert[0];
        freq = startwert[1];
        phi = startwert[2];
        omega = 0.0;
    }//startwerte()

    public void startwerteDrivenTripleY()  //method for setting initial values
    {
        amp = startwert[0];
        freq = startwert[1];
        omega1 = 0.0;
        omega2 = 0.0;
        omega3 = 0.0;
    }//startwerte()

    public void pixels() //method for calculating pixelcoordinates
    {
        px1 = (int) Math.round((double) (Lx / 2) + 80 * Math.sin(phi1));
        py1 = (int) Math.round((double) (Ly / 2) + 80 * Math.cos(phi1));
        px2 = (int) Math.round((double) px1 + 80 * Math.sin(phi2));
        py2 = (int) Math.round((double) py1 + 80 * Math.cos(phi2));
    }//pixels()

    public double forcephi(double phi, double omega) {
        return -grav * Math.sin(phi) - aforce * Math.cos(phi) - 0.1 * reib * omega;
    }//forcephi()

    public double forcephiY(double phi, double omega) {
        return (-grav + aforce) * Math.sin(phi) - 0.1 * reib * omega;
    }

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

    public double energyDriven() {
        return 0.5 * omega * omega + 0.5 * ap * ap + omega * ap * Math.cos(phi)
                - grav * Math.cos(phi);
    }//energy()

    public double energyDrivenY() {
        return 0.5 * omega * omega + 0.5 * ap * ap - omega * ap * Math.sin(phi)
                - grav * Math.cos(phi) - grav * a;
    }

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



    public void runge_step_phi() {
        k[0] = dt * omega;
        l[0] = dt * forcephi(phi, omega);
        k[1] = dt * (omega + l[0] / 2);
        l[1] = dt * forcephi(phi + k[0] / 2, omega + l[0] / 2);
        k[2] = dt * (omega + l[1] / 2);
        l[2] = dt * forcephi(phi + k[1] / 2, omega + l[1] / 2);
        k[3] = dt * (omega + l[2]);
        l[3] = dt * forcephi(phi + k[2], omega + l[2]);
    }//runge_step_phi()

    public void runge_step_phiY() {
        k[0] = dt * omega;
        l[0] = dt * forcephiY(phi, omega);
        k[1] = dt * (omega + l[0] / 2);
        l[1] = dt * forcephiY(phi + k[0] / 2, omega + l[0] / 2);
        k[2] = dt * (omega + l[1] / 2);
        l[2] = dt * forcephiY(phi + k[1] / 2, omega + l[1] / 2);
        k[3] = dt * (omega + l[2]);
        l[3] = dt * forcephiY(phi + k[2], omega + l[2]);
    }

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

    public void treibwerte(int step)  //driving values
    {
        a = amp * Math.cos(freq * step * dt);
        ap = -amp * freq * Math.sin(freq * step * dt);
        aforce = -freq * freq * a;
    }//treibwerte()

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

    public abstract void update();

}

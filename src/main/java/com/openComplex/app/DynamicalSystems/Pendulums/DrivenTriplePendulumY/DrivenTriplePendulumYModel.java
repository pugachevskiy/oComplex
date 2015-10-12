package com.openComplex.app.DynamicalSystems.Pendulums.DrivenTriplePendulumY;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;

/**
 * Created by strange on 11/10/15.
 */
public class DrivenTriplePendulumYModel extends PendulumsModel {

    private int step;
    private int px1, px2, px3, py1, py2, py3, pa; //pixelcoordinates

    public DrivenTriplePendulumYModel() {
        startwert[0] = 0.05; //amp
        startwert[1] = 250.; //freq
        phi1 = Math.PI / 180. * 165.;
        phi2 = Math.PI / 180. * 165.;
        phi3 = Math.PI / 180. * 165.;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public void pixels()  //method for calculating pixelcoords
    {
        pa = (int) Math.round((double) (Ly / 2) + 50 * a);
        px1 = (int) Math.round((double) (Lx / 2) + 50 * Math.sin(phi1));
        py1 = (int) Math.round((double) pa + 50 * Math.cos(phi1));
        px2 = (int) Math.round((double) px1 + 50 * Math.sin(phi2));
        py2 = (int) Math.round((double) py1 + 50 * Math.cos(phi2));
        px3 = (int) Math.round((double) px2 + 50 * Math.sin(phi3));
        py3 = (int) Math.round((double) py2 + 50 * Math.cos(phi3));
    }//pixels()

    @Override
    public void update() {
        treibwerte(step); //new a, ap, aforce
        this.runge_step_phi1(); //Runge-Kutta
        this.runge_step_phi2(); //Runge-Kutta
        this.runge_step_phi3(); //Runge-Kutta
        phi1 = phi1 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        omega1 = omega1 + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi2 = phi2 + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega2 = omega2 + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
        phi3 = phi3 + (k3[0] + 2 * k3[1] + 2 * k3[2] + k3[3]) / 6; //Runge-Kutta
        omega3 = omega3 + (l3[0] + 2 * l3[1] + 2 * l3[2] + l3[3]) / 6; //Runge-Kutta
        this.pixels();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Lx + 100, Ly);
        g.setColor(Color.blue);
        g.drawString("Energy:", Lx + 20, 40);
        g.drawString("" + (double) Math.round(10 * this.energy()) / 10, Lx + 20, 55);
        g.drawString("Friction:", Lx + 20, Ly / 2 + 55);
        g.drawString("" + reib, Lx + 20, Ly / 2 + 70);
        g.drawString("Amplitude:", Lx + 20, Ly / 2 + 95);
        g.drawString("" + (double) Math.round(100 * amp) / 100, Lx + 20, Ly / 2 + 110);
        g.drawString("Frequency:", Lx + 20, Ly / 2 + 135);
        g.drawString("" + (double) Math.round(100 * freq) / 100, Lx + 20, Ly / 2 + 150);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        g.setColor(Color.black);
        g.drawString("Step " + step, 20, 30);
        g.setColor(Color.red);
        g.drawLine(Lx / 2, pa, px1, py1);
        g.drawLine(px1, py1, px2, py2);
        g.drawLine(px2, py2, px3, py3);
        g.setColor(Color.black);
        g.fillOval(px1 - 5, py1 - 5, 10, 10);
        g.fillOval(px2 - 5, py2 - 5, 10, 10);
        g.fillOval(px3 - 5, py3 - 5, 10, 10);
        g.fillRect(Lx / 2 - 1, pa - 4, 3, 8);
    } //paintFrame(gr)


    /* force on Phi_1 */
    public double forcephi1(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3) {
        return (-10 * (aforce - grav) * Math.sin(phi1)
                - 4 * (aforce - grav) * Math.sin(phi1 - 2 * phi2)
                + 8 * omega2 * omega2 * Math.sin(phi1 - phi2)
                + 4 * omega1 * omega1 * Math.sin(2 * (phi1 - phi2))
                + aforce * Math.sin(phi1 + 2 * phi2 - 2 * phi3)
                - grav * Math.sin(phi1 + 2 * phi2 - 2 * phi3)
                + 2 * omega3 * omega3 * Math.sin(phi1 - phi3)
                + 2 * omega3 * omega3 * Math.sin(phi1 - 2 * phi2 + phi3)
                + aforce * Math.sin(phi1 - 2 * phi2 + 2 * phi3)
                - grav * Math.sin(phi1 - 2 * phi2 + 2 * phi3)) / (2 * (-5
                + 2 * Math.cos(2 * (phi1 - phi2)) + Math.cos(2 * (phi2 - phi3))))
                - 0.1 * reib * omega1;
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3) {
        return (-14 * omega1 * omega1 * Math.sin(phi1 - phi2)
                - 4 * omega2 * omega2 * Math.sin(2 * (phi1 - phi2))
                + 7 * aforce * Math.sin(2 * phi1 - phi2)
                - 7 * grav * Math.sin(2 * phi1 - phi2) - 7 * aforce * Math.sin(phi2)
                + 7 * grav * Math.sin(phi2) - aforce * Math.sin(phi2 - 2 * phi3)
                + grav * Math.sin(phi2 - 2 * phi3)
                + 2 * omega1 * omega1 * Math.sin(phi1 + phi2 - 2 * phi3)
                - aforce * Math.sin(2 * phi1 + phi2 - 2 * phi3)
                + grav * Math.sin(2 * phi1 + phi2 - 2 * phi3)
                - 2 * omega3 * omega3 * Math.sin(2 * phi1 - phi2 - phi3)
                + 6 * omega3 * omega3 * Math.sin(phi2 - phi3)
                + 2 * omega2 * omega2 * Math.sin(2 * (phi2 - phi3))) / (2 * (-5
                + 2 * Math.cos(2 * (phi1 - phi2)) + Math.cos(2 * (phi2 - phi3))))
                - 0.1 * reib * omega2;
    }//forcephi2()

    /* force on Phi_3 */
    public double forcephi3(double phi1, double omega1,
                            double phi2, double omega2,
                            double phi3, double omega3) {
        return (-2 * (2 * omega2 * omega2 + 2 * omega1 * omega1 * Math.cos(phi1 - phi2)
                + (-aforce + grav) * Math.cos(2 * phi1 - phi2)
                - aforce * Math.cos(phi2) + grav * Math.cos(phi2)
                + omega3 * omega3 * Math.cos(phi2 - phi3)) * Math.sin(phi2 - phi3)) / (
                -5 + 2 * Math.cos(2 * (phi1 - phi2)) + Math.cos(2 * (phi2 - phi3)))
                - 0.1 * reib * omega3;
    }//forcephi3()

    public double energy() {
        return (3 * ap * ap - 6 * a * grav + 3 * omega1 * omega1 + 2 * omega2 * omega2
                + omega3 * omega3 - 6 * grav * Math.cos(phi1)
                + 4 * omega1 * omega2 * Math.cos(phi1 - phi2)
                - 4 * grav * Math.cos(phi2) + 2 * omega1 * omega3 * Math.cos(phi1 - phi3)
                + 2 * omega2 * omega3 * Math.cos(phi2 - phi3) - 2 * grav * Math.cos(phi3)
                - 6 * ap * omega1 * Math.sin(phi1) - 4 * ap * omega2 * Math.sin(phi2)
                - 2 * ap * omega3 * Math.sin(phi3)) / 2;
    }//energy()

    public void runge_step_phi3() {
        k3[0] = dt * omega3;
        l3[0] = dt * this.forcephi3(phi1, omega1, phi2, omega2, phi3, omega3);
        k3[1] = dt * (omega3 + l3[0] / 2);
        l3[1] = dt * this.forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[0] / 2, omega3 + l3[0] / 2);
        k3[2] = dt * (omega3 + l3[1] / 2);
        l3[2] = dt * this.forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[1] / 2, omega3 + l3[1] / 2);
        k3[3] = dt * (omega3 + l3[2]);
        l3[3] = dt * this.forcephi3(phi1, omega1, phi2, omega2, phi3 + k3[2], omega3 + l3[2]);
    }//runge_step_phi3()

    public void runge_step_phi1() {
        k1[0] = dt * omega1;
        l1[0] = dt * this.forcephi1(phi1, omega1, phi2, omega2, phi3, omega3);
        k1[1] = dt * (omega1 + l1[0] / 2);
        l1[1] = dt * this.forcephi1(phi1 + k1[0] / 2, omega1 + l1[0] / 2, phi2, omega2, phi3, omega3);
        k1[2] = dt * (omega1 + l1[1] / 2);
        l1[2] = dt * this.forcephi1(phi1 + k1[1] / 2, omega1 + l1[1] / 2, phi2, omega2, phi3, omega3);
        k1[3] = dt * (omega1 + l1[2]);
        l1[3] = dt * this.forcephi1(phi1 + k1[2], omega1 + l1[2], phi2, omega2, phi3, omega3);
    }//runge_step_phi1()

    public void runge_step_phi2() {
        k2[0] = dt * omega2;
        l2[0] = dt * this.forcephi2(phi1, omega1, phi2, omega2, phi3, omega3);
        k2[1] = dt * (omega2 + l2[0] / 2);
        l2[1] = dt * this.forcephi2(phi1, omega1, phi2 + k2[0] / 2, omega2 + l2[0] / 2, phi3, omega3);
        k2[2] = dt * (omega2 + l2[1] / 2);
        l2[2] = dt * this.forcephi2(phi1, omega1, phi2 + k2[1] / 2, omega2 + l2[1] / 2, phi3, omega3);
        k2[3] = dt * (omega2 + l2[2]);
        l2[3] = dt * this.forcephi2(phi1, omega1, phi2 + k2[2], omega2 + l2[2], phi3, omega3);
    }//runge_step_phi2()

    public void value1() {
        startwert[0] = 0.05; //amp
        startwert[1] = 250.;  //freq
        phi1 = Math.PI / 180. * 165.;
        phi2 = Math.PI / 180. * 165.;
        phi3 = Math.PI / 180. * 165.;
        startwerteDrivenTripleY();
        treibwerte(step);
        pixels();
        repaint();
    }

    public void value2() {
        startwert[0] = 0.05; //amp
        startwert[1] = 500.;  //freq
        phi1 = Math.PI / 180. * 145.;
        phi2 = Math.PI / 180. * 145.;
        phi3 = Math.PI / 180. * 145.;
        startwerteDrivenTripleY();
        treibwerte(step);
        pixels();
        repaint();
    }


    public void value3() {
        startwert[0] = 0.05; //amp
        startwert[1] = 300.;  //freq
        phi1 = Math.PI / 180. * 165.;
        phi2 = Math.PI / 180. * 185.;
        phi3 = Math.PI / 180. * 195.;
        startwerteDrivenTripleY();
        treibwerte(step);
        pixels();
        repaint();
    }

    public void value4() {
        startwert[0] = 0.5; //amp
        startwert[1] = 1.;  //freq
        phi1 = Math.PI / 180. * 175.;
        phi2 = Math.PI / 180. * 175.;
        phi3 = Math.PI / 180. * 175.;
        startwerteDrivenTripleY();
        treibwerte(step);
        pixels();
        repaint();
    }

}

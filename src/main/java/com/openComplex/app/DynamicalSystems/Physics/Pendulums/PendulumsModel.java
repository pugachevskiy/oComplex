package com.openComplex.app.DynamicalSystems.Physics.Pendulums;

/**
 * Created by strange on 05/10/15.
 */
public class PendulumsModel {
    private static final double grav = 9.81; //gravity
    private static final double dt = 0.0005; //Runge-Kutta-timestep
    private double k1[] = new double[4]; //Runge-Kutta
    private double k2[] = new double[4]; //Runge-Kutta
    private double l1[] = new double[4]; //Runge-Kutta
    private double l2[] = new double[4]; //Runge-Kutta
    private double phi11, omega11; //coordinates and velocities
    private double phi12, omega12; //coordinates and velocities
    private double phi21, omega21; //coordinates and velocities
    private double phi22, omega22; //coordinates and velocities
    private int px11, px12, py11, py12; //pixelcoordinates
    private int px21, px22, py21, py22; //pixelcoordinates
    private double startwert; //save initial values
    private boolean stop = false; //go/pause


    public void startwerte()  //method for setting initial values
    {
        startwert = Math.PI / 180 * 170.; //phi22
        phi11 = Math.PI / 180 * 130.;
        omega11 = 0.0;
        phi12 = Math.PI / 180 * 170.;
        omega12 = 0.0;
        phi21 = Math.PI / 180 * 130.;
        omega21 = 0.0;
        phi22 = startwert;
        omega22 = 0.0;
        stop = false;


    }//startwerte()

    public boolean getStatus() {
        return this.stop;
    }

    public void setStatus(boolean status) {
        this.stop = status;
    }

    public void setStartwert(double wert) {
        this.startwert = wert;
    }

    public double getStartwert() {
        return this.startwert;
    }

    public void pixels()  //method for calculating pixelcoords
    {
        px11 = (int) Math.round((double) (PendulumsView.Lx / 2) + 62 * Math.sin(phi11));
        py11 = (int) Math.round((double) (PendulumsView.Ly / 2) + 62 * Math.cos(phi11));
        px12 = (int) Math.round((double) px11 + 62 * Math.sin(phi12));
        py12 = (int) Math.round((double) py11 + 62 * Math.cos(phi12));
        px21 = (int) Math.round((double) (PendulumsView.Lx + PendulumsView.Lx / 2) + 62 * Math.sin(phi21));
        py21 = (int) Math.round((double) (PendulumsView.Ly / 2) + 62 * Math.cos(phi21));
        px22 = (int) Math.round((double) px21 + 62 * Math.sin(phi22));
        py22 = (int) Math.round((double) py21 + 62 * Math.cos(phi22));
    }//pixels()

    /* force on Phi_1 */
    public double forcephi1(double phi1, double omega1, double phi2, double omega2) {
        return -(grav * (2 * Math.sin(phi1)
                + Math.sin(phi1)
                + Math.sin(phi1 - 2 * phi2)
        )
                + omega1 * omega1 * Math.sin(2 * phi1 - 2 * phi2)
                + 2 * omega2 * omega2 * Math.sin(phi1 - phi2)
        ) / (3. - Math.cos(2 * phi1 - 2 * phi2));
    }//forcephi1()

    /* force on Phi_2 */
    public double forcephi2(double phi1, double omega1, double phi2, double omega2) {
        return (2 * Math.sin(phi1 - phi2)
                * (2 * grav * Math.cos(phi1)
                + 2 * omega1 * omega1
                + omega2 * omega2 * Math.cos(phi1 - phi2)
        )
        ) / (3. - Math.cos(2 * phi1 - 2 * phi2));
    }//forcephi2()

    public double energy(double phi1, double omega1, double phi2, double omega2) {
        return omega1 * omega1 + 0.5 * omega2 * omega2
                + omega1 * omega2 * Math.cos(phi1 - phi2)
                - grav * (2 * Math.cos(phi1) + Math.cos(phi2));
    }//energy()

    public void runge_step_phi1(double phi1, double omega1, double phi2, double omega2) {
        k1[0] = dt * omega1;
        l1[0] = dt * forcephi1(phi1, omega1, phi2, omega2);
        k1[1] = dt * (omega1 + l1[0] / 2);
        l1[1] = dt * forcephi1(phi1 + k1[0] / 2, omega1 + l1[0] / 2, phi2, omega2);
        k1[2] = dt * (omega1 + l1[1] / 2);
        l1[2] = dt * forcephi1(phi1 + k1[1] / 2, omega1 + l1[1] / 2, phi2, omega2);
        k1[3] = dt * (omega1 + l1[2]);
        l1[3] = dt * forcephi1(phi1 + k1[2], omega1 + l1[2], phi2, omega2);
    }//runge_step_phi1()

    public void runge_step_phi2(double phi1, double omega1, double phi2, double omega2) {
        k2[0] = dt * omega2;
        l2[0] = dt * forcephi2(phi1, omega1, phi2, omega2);
        k2[1] = dt * (omega2 + l2[0] / 2);
        l2[1] = dt * forcephi2(phi1, omega1, phi2 + k2[0] / 2, omega2 + l2[0] / 2);
        k2[2] = dt * (omega2 + l2[1] / 2);
        l2[2] = dt * forcephi2(phi1, omega1, phi2 + k2[1] / 2, omega2 + l2[1] / 2);
        k2[3] = dt * (omega2 + l2[2]);
        l2[3] = dt * forcephi2(phi1, omega1, phi2 + k2[2], omega2 + l2[2]);
    }//runge_step_phi2()

    public void solve1() {
        runge_step_phi1(phi11, omega11, phi12, omega12); //Runge-Kutta
        runge_step_phi2(phi11, omega11, phi12, omega12); //Runge-Kutta
        phi11 = phi11 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        omega11 = omega11 + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi12 = phi12 + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega12 = omega12 + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
    }

    public void solve2() {
        runge_step_phi1(phi21, omega21, phi22, omega22); //Runge-Kutta
        runge_step_phi2(phi21, omega21, phi22, omega22); //Runge-Kutta
        phi21 = phi21 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        omega21 = omega21 + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi22 = phi22 + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega22 = omega22 + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
    }


}

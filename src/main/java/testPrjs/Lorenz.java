package testPrjs;

/******************************************************************************
 *  Compilation:  javac Lorenz.java 
 *  Execution:    java Lorenz
 *  Dependencies: StdDraw.java
 *
 *  Plot phase space (x vs. z) of Lorenz attractor with one set of
 *  initial conditions and another set of slightly perturbed intial
 *  conditions. Uses Euler method.
 *
 ******************************************************************************/

import java.awt.Color;

public class Lorenz { 
    private double x, y, z;
    private Color color;

    public Lorenz(double x, double y, double z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color = color;
    }

    public void update(double dt) {
        double xnew = x + dx(x, y, z) * dt;
        double ynew = y + dy(x, y, z) * dt;
        double znew = z + dz(x, y, z) * dt;
        x = xnew;
        y = ynew;
        z = znew;
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.point(x, z);
    }


    public static double dx(double x, double y, double z) {
        return -10*(x - y);
    }

    public static double dy(double x, double y, double z) {
        return -x*z + 28*x - y;
    }

    public static double dz(double x, double y, double z) {
        return x*y - 8*z/3;
    }


    public static void main(String[] args) {
        StdDraw.clear(StdDraw.LIGHT_GRAY);
        StdDraw.setXscale(-25, 25);
        StdDraw.setYscale(  0, 50);
        Lorenz lorenz1 = new Lorenz(0.0, 20.00, 25.0, StdDraw.BLACK);
        Lorenz lorenz2 = new Lorenz(0.0, 20.01, 25.0, StdDraw.BOOK_BLUE);
        Lorenz lorenz3 = new Lorenz(0.0, 20.02, 25.0, StdDraw.WHITE);

        // Use Euler's method to numerically solve ODE
        double dt = 0.001;
        for (int i = 0; i < 500000; i++) {
            lorenz1.update(dt);
            lorenz2.update(dt);
            lorenz3.update(dt);
            lorenz1.draw();
            lorenz2.draw();
            lorenz3.draw();
            if (i % 10 == 0) StdDraw.show(2);
        }
    }

}
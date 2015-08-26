package com.openComplex.app;

/**
 * Created by strange on 26/08/15.
 */
public class LorenzModel {
    private double x, y, z;

    public double a = -10, b = 28, c = - 8.0/3.0;
    public LorenzModel(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public void update(double dt) {
        double xnew = x + dx(x, y, z) * dt;
        double ynew = y + dy(x, y, z) * dt;
        double znew = z + dz(x, y, z) * dt;
        x = xnew;
        y = ynew;
        z = znew;
    }

    private double dx(double x, double y, double z) {
        return a*(x - y);
    }

    private double dy(double x, double y, double z) {
        return -x*z + b*x - y;
    }

    private double dz(double x, double y, double z) {
        return x*y + c*z;
    }
    public double getX1(){
        return this.x;
    }
    public double getZ1(){
        return this.z;
    }

}
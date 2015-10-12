package com.openComplex.app.DynamicalSystems.Fractals.Model;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import com.openComplex.app.DynamicalSystems.Fractals.Fractal;
import com.openComplex.app.DynamicalSystems.Fractals.FractalsCollection;
import java.awt.*;

/**
 * Created by strange on 29/06/15.
 */
public class MinkowskiCurve extends GLJPanel implements Fractal {
    //Strat point
    private static final int DX0 = 50;
    private static final int DY0 = 250;
    //End point
    private static final int DX = 550;
    private static final int DY = 250;

    private int step = 0;


    public MinkowskiCurve(GLCapabilities capabilities,int initStep) {
        super(capabilities);
        step = initStep;
    }

    public static void doDrawing(Graphics g, int xa, int ya, int xi, int yi, int n) {
        int xb, xc, xd, xe, xf, xg, xh;
        int yb, yc, yd, ye, yf, yg, yh;
        if (n == 0) {
            g.drawLine(xa, ya, xi, yi);

        } else {
            xb = (int) (xa + (xi - xa) * (0.25));
            yb = (int) (ya + (yi - ya) * (0.25));
            xe = (int) (xa + (xi - xa) * (0.5));
            ye = (int) (ya + (yi - ya) * (0.5));
            xh = (int) (xa + (xi - xa) * (0.75));
            yh = (int) (ya + (yi - ya) * (0.75));
            int cos90 = 0;
            int sin90 = -1;
            xc = xb + (xe - xb) * cos90 - sin90 * (ye - yb);
            yc = yb + (xe - xb) * sin90 + cos90 * (ye - yb);

            xd = xc + (xe - xb);
            yd = yc + (ye - yb);

            sin90 = 1;
            xf = xe + (xh - xe) * cos90 - sin90 * (yh - ye);
            yf = ye + (xh - xe) * sin90 + cos90 * (yh - ye);
            xg = xf + (xh - xe);
            yg = yf + (yh - ye);

            doDrawing(g, xa, ya, xb, yb, n - 1);
            doDrawing(g, xb, yb, xc, yc, n - 1);
            doDrawing(g, xc, yc, xd, yd, n - 1);
            doDrawing(g, xd, yd, xe, ye, n - 1);
            doDrawing(g, xe, ye, xf, yf, n - 1);
            doDrawing(g, xf, yf, xg, yg, n - 1);
            doDrawing(g, xg, yg, xh, yh, n - 1);
            doDrawing(g, xh, yh, xi, yi, n - 1);

        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        doDrawing(g, DX0, DY0, DX, DY, step);
    }

    @Override
    public String getName() {
        return FractalsCollection.MINKOWSKI_CURVE.get(0);
    }

    @Override
    public String getCopy() {
        return FractalsCollection.MINKOWSKI_CURVE.get(1);
    }

    @Override
    public String getFactor() {
        return FractalsCollection.MINKOWSKI_CURVE.get(2);
    }
    @Override
    public String getDimension() {

        return FractalsCollection.MINKOWSKI_CURVE.get(3);
    }

    @Override
    public String getDicription() {
        return FractalsCollection.MINKOWSKI_CURVE.get(4);
    }

    @Override
    public GLJPanel getPanel() {
        return this;
    }

}

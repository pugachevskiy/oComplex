package com.openComplex.app.DynamicalSystems.Fractals.Model;

import com.openComplex.app.DynamicalSystems.Fractals.Fractal;
import com.openComplex.app.DynamicalSystems.Fractals.FractalsCollection;

import javax.swing.*;
import java.awt.*;

public class KochCurve extends JPanel implements Fractal {
    //Start point
    private int AX = 50;
    private int AY = 240;
    //End point
    private int EX = 450;
    private int EY = 240;

    private int step = 0;

    public KochCurve(int initStep) {
        this.step = initStep;
    }

    public void doDrawing(Graphics g, int ax, int ay, int ex, int ey, int n) {
        double cos = 0.5;
        double sin = -0.866;

        if (n == 0) {
            g.drawLine(ax, ay, ex, ey);
        } else {
            int bx = ax + (ex - ax) / 3;
            int by = ay + (ey - ay) / 3;
            int dx = ax + 2 * (ex - ax) / 3;
            int dy = ay + 2 * (ey - ay) / 3;
            int x = dx - bx;
            int y = dy - by;
            int cx = (int) (bx + x * cos - sin * y);
            int cy = (int) (by + x * sin + cos * y);

            doDrawing(g, ax, ay, bx, by, n - 1);
            doDrawing(g, bx, by, cx, cy, n - 1);
            doDrawing(g, cx, cy, dx, dy, n - 1);
            doDrawing(g, dx, dy, ex, ey, n - 1);
        }
    }

    @Override
    public String getName() {
        return FractalsCollection.KOCH_CURVE.get(0);
    }

    @Override
    public String getCopy() {
        return FractalsCollection.KOCH_CURVE.get(1);
    }

    @Override
    public String getFactor() {
        return FractalsCollection.KOCH_CURVE.get(2);
    }

    @Override
    public String getDimension() {
        return FractalsCollection.KOCH_CURVE.get(3);
    }

    @Override
    public String getDicription() {
        return FractalsCollection.KOCH_CURVE.get(4);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);

        //Change size dynamically
        int height = this.getHeight();
        int maxWidth = this.getWidth();
        while (height < 11 * maxWidth / 16) {
            maxWidth -= 30;
        }

        AY = 2 * height / 3;
        AX = this.getWidth() / 2 - maxWidth / 2;
        EX = this.getWidth() / 2 + maxWidth / 2;
        EY = 2 * height / 3;

        doDrawing(g, AX, AY, EX, EY, step);
    }
}

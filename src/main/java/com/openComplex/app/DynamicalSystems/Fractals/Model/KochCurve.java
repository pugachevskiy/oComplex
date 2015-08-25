package com.openComplex.app.DynamicalSystems.Fractals.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 20/04/15.
 */
public class KochCurve extends JPanel {
    //Start point
    private static final int AX = 50;
    private static final int AY = 150;
    //End point
    private static final int EX = 250;
    private static final int EY = 150;
    public static final double DIMENSION = 1.2619;
    public static final int COPY = 3;
    public static final int FACTOR = 4;

    private int step = 0;

    public KochCurve(int initStep) {
        step = initStep;
    }

    public static void doDrawing(Graphics g, int ax, int ay, int ex, int ey, int n) {
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g, AX, AY, EX, EY, step);
    }
}

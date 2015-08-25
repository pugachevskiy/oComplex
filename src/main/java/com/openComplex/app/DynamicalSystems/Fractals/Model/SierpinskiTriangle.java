package com.openComplex.app.DynamicalSystems.Fractals.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 19/04/15.
 */
public class SierpinskiTriangle extends JPanel {
    //First point of Triange
    private static final int X1 = 100;
    private static final int Y1 = 50;
    //Second point of Triange
    private static final int X2 = 150;
    private static final int Y2 = 138;
    //Third point of Triange
    private static final int X3 = 50;
    private static final int Y3 = 138;
    public static final double DIMENSION = 1.5849;
    public static final int COPY = 3;
    public static final int FACTOR = 2;

    private int step = 0;

    public SierpinskiTriangle(int initStep) {
        step = initStep;
    }

    public static void doDrawing(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int step) {
        if (step > 0) {
            int x1n = (x1 + x2) / 2;
            int y1n = (y1 + y2) / 2;
            int x2n = (x2 + x3) / 2;
            int y2n = (y2 + y3) / 2;
            int x3n = (x3 + x1) / 2;
            int y3n = (y3 + y1) / 2;
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x3, y3, x1, y1);
            doDrawing(g, x1, y1, x1n, y1n, x3n, y3n, step - 1);
            doDrawing(g, x1n, y1n, x2, y2, x2n, y2n, step - 1);
            doDrawing(g, x3n, y3n, x2n, y2n, x3, y3, step - 1);
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        doDrawing(g, X1, Y1, X2, Y2, X3, Y3, step);
    }
}


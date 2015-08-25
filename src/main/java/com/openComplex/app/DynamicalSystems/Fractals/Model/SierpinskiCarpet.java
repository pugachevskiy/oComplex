package com.openComplex.app.DynamicalSystems.Fractals.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 22/03/15.
 */
public class SierpinskiCarpet extends JPanel {
    //Start point
    private static final int X = 50;
    private static final int Y = 50;
    //Length of side of the rectangle
    private static final int DIM = 243;
    public static final double DIMENSION = 1.8928;
    public static final int COPY = 3;
    public static final int FACTOR = 8;

    private int step = 1;

    public SierpinskiCarpet(int initStep) {
        step = initStep;
    }

    public void doDrawing(Graphics g, int x, int y, int dim, int n) {
        g.setColor(Color.BLACK);
        int w = dim / 3;
        int x0, y0;

        g.drawRect(X, Y, DIM, DIM);
        if (n == 0)
            return;

        if (n == 1 ){
            g.drawRect(x + w, y + w, w, w);
          //  g.drawRect(x,y,dim,dim);
        } else {
            g.drawRect(x+w, y+w, w, w);
            for (int i = 0; i < 9; i++) {
                if (i != 4) {
                    x0 = i / 3;
                    y0 = i % 3;
                    doDrawing(g, x + x0 * w, y + y0 * w, w, n - 1);
                }
            }
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        doDrawing(g, X, Y, DIM, step);
    }
}

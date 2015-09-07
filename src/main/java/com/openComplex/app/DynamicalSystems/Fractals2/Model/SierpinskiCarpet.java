package com.openComplex.app.DynamicalSystems.Fractals2.Model;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import com.openComplex.app.DynamicalSystems.Fractals2.Fractal;
import com.openComplex.app.DynamicalSystems.Fractals2.FractalsCollection;
import java.awt.*;

/**
 * Created by strange on 29/06/15.
 */
public class SierpinskiCarpet extends GLJPanel implements Fractal {
    //Start point
    private static final int X = 20;
    private static final int Y = 50;
    //Length of side of the rectangle
    private static final int DIM = 486;


    private int step = 0;

    public SierpinskiCarpet(GLCapabilities capabilities,int initStep) {
        super(capabilities);
        step = initStep;
    }

    public void doDrawing(Graphics g, int x, int y, int dim, int n) {
        int w = dim / 3;
        int x0, y0;
        g.drawRect(X, Y, DIM, DIM);
        if (n == 0)
            return;

        if (n == 1 ){
            g.drawRect(x + w, y + w, w, w);

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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        doDrawing(g, X, Y, DIM, step);
    }

    @Override
    public String getName() {
        return FractalsCollection.SIERPINSKI_CARPET.get(0);
    }

    @Override
    public String getCopy() {
        return FractalsCollection.SIERPINSKI_CARPET.get(1);
    }

    @Override
    public String getFactor() {
        return FractalsCollection.SIERPINSKI_CARPET.get(2);
    }
    @Override
    public String getDimension() {
        return FractalsCollection.SIERPINSKI_CARPET.get(3);
    }

    @Override
    public String getDicription() {
        return FractalsCollection.SIERPINSKI_CARPET.get(4);
    }
    @Override
    public GLJPanel getPanel() {
        return this;
    }

    @Override
    public void updatePanel() {
        this.repaint();
    }
}

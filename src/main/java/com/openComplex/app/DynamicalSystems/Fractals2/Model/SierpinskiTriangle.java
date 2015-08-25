package com.openComplex.app.DynamicalSystems.Fractals2.Model;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import com.openComplex.app.DynamicalSystems.Fractals2.Fractal;
import com.openComplex.app.DynamicalSystems.Fractals2.FractalsCollection;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 29/06/15.
 */
public class SierpinskiTriangle extends GLJPanel implements Fractal {
    //First point of Triange
    private static final int X1 = 250;
    private static final int Y1 = 50;
    //Second point of Triange
    private static final int X2 = 400;
    private static final int Y2 = 450;
    //Third point of Triange
    private static final int X3 = 100;
    private static final int Y3 = 450;


    private int step = 0;

    public SierpinskiTriangle(GLCapabilities capabilities, int initStep) {
        super(capabilities);
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
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        doDrawing(g, X1, Y1, X2, Y2, X3, Y3, step);
    }
    @Override
    public String getName() {
        return FractalsCollection.SIERPINSKI_TRIANGLE.get(0);
    }
    @Override
    public String getCopy() {
        return FractalsCollection.SIERPINSKI_TRIANGLE.get(1);
    }
    @Override
    public String getFactor() {
        return FractalsCollection.SIERPINSKI_TRIANGLE.get(2);
    }
    @Override
    public String getDimension() {
        return FractalsCollection.SIERPINSKI_TRIANGLE.get(3);
    }
    @Override
    public String getDicription() {
        return FractalsCollection.SIERPINSKI_TRIANGLE.get(4);
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

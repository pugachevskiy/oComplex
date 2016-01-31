package com.openComplex.app.DynamicalSystems.Fractals.Model;

import com.openComplex.app.DynamicalSystems.Fractals.Fractal;
import com.openComplex.app.DynamicalSystems.Fractals.FractalsCollection;

import javax.swing.*;
import java.awt.*;

/**
 * on 29/06/15.
 */
public class SierpinskiTriangle extends JPanel implements Fractal {
    //First point of Triange
    private int X1 = 250;
    private int Y1 = 50;
    //Second point of Triange
    private int X2 = 400;
    private int Y2 = 450;
    //Third point of Triange
    private int X3 = 100;
    private int Y3 = 450;


    private int step = 0;

    public SierpinskiTriangle(int initStep) {
        this.step = initStep;
    }

    public void doDrawing(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int step) {
        if (step == 0) {
            g.drawLine(x1, y1, x2, y2);
            g.drawLine(x2, y2, x3, y3);
            g.drawLine(x3, y3, x1, y1);
        } else {
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
        super.paintComponent(g);
        g.setColor(Color.RED);

        //Change size dynamically
        int height = this.getHeight();
        int maxWidth = this.getWidth();
        while (height < maxWidth) {
            maxWidth -= 30;
        }

        X3 = this.getWidth() / 2 - maxWidth / 2;
        Y3 = 9 * height / 10;
        X2 = this.getWidth() / 2 + maxWidth / 2;
        Y2 = 9 * height / 10;
        X1 = X3 + maxWidth / 2;
        Y1 = Y3 - (int) (0.866 * maxWidth);

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
    public JPanel getPanel() {
        return this;
    }

}

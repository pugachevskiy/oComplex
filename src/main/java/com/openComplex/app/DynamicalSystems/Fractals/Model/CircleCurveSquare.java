package com.openComplex.app.DynamicalSystems.Fractals.Model;


import com.openComplex.app.DynamicalSystems.Fractals.Fractal;

import javax.swing.*;
import java.awt.*;


public class CircleCurveSquare extends JPanel implements Fractal {
    //Start point
    private static int AX = 50;
    private static int AY = 240;

    private int step = 0;

    public CircleCurveSquare(int initStep) {
        this.step = initStep;
    }

    public static void doDrawing(Graphics g, int x, int y, int radius, int step) {

        if (step == 0)
            return;
        g.drawOval(x, y, radius, radius);
        if (step > 1) {
            //left
            doDrawing(g, x - radius / 4, y + radius / 4, radius / 2, step - 1);
            //right
            doDrawing(g, x + 3 * radius / 4, y + radius / 4, radius / 2, step - 1);
            //upper
            doDrawing(g, x + radius / 4, y - radius / 4, radius / 2, step - 1);
            //lower
            doDrawing(g, x + radius / 4, y + 3 * radius / 4, radius / 2, step - 1);
        }

    }

    @Override
    public String getName() {
        return "Circle Square";
    }

    @Override
    public String getCopy() {
        return "1";
    }

    @Override
    public String getFactor() {
        return "1";
    }

    @Override
    public String getDimension() {
        return "1";
    }

    @Override
    public String getDicription() {
        return "Rekursive construction of circles arranged as square.\n\n\n\n\n\n\n\n\n\n\n";
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.RED);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AX = this.getWidth() / 2;
        AY = this.getHeight() / 2;


        int maxRadius = this.getHeight() < this.getWidth() ? this.getHeight() / 2 : this.getWidth() / 2;

        //int maxRadius = this.getHeight();
        while (maxRadius > this.getWidth() / 2) {
            maxRadius -= 30;
        }

        doDrawing(g2, AX - maxRadius / 2, AY - maxRadius / 2, maxRadius, step);
    }
}

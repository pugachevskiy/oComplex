package com.openComplex.app.DynamicalSystems.Fractals.Model;

import com.openComplex.app.DynamicalSystems.Fractals.Fractal;


import javax.swing.*;
import java.awt.*;

/**
 * Created on 29.06.2015.
 */
public class CircleCurve extends JPanel implements Fractal {
    //Start point
    private int AX = 50;
    private int AY = 240;

    private int step = 0;

    public CircleCurve(int initStep) {
        this.step = initStep;
    }

    public void doDrawing(Graphics g, int x, int y, int radius, int step) {
        if (step == 0)
            return;
        g.drawOval(x, y, radius, radius);
        if (step > 1) {
            doDrawing(g, x - radius / 4, y + radius / 4, radius / 2, step - 1);
            doDrawing(g, x + 3 * radius / 4, y + radius / 4, radius / 2, step - 1);
        }
    }

    @Override
    public String getName() {
        return "Circle Curve";
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
        return "Rekursive construction of circles.\n\n\n\n\n\n\n\n\n\n\n";
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

        int maxRadius = this.getHeight();
        while (maxRadius > this.getWidth() / 2) {
            maxRadius -= 30;
        }

        doDrawing(g2, AX - maxRadius / 2, AY - maxRadius / 2, maxRadius, step);
    }
}

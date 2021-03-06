package com.openComplex.app.DynamicalSystems.Fractals.Model;

import com.openComplex.app.DynamicalSystems.Fractals.Fractal;

import javax.swing.*;
import java.awt.*;

public class FractalTree extends JPanel implements Fractal {

    //Start point
    private int AX = 50;
    private int AY = 240;

    private final int startStrokeSize = 6;
    private final int startAngle = -90;

    private int step = 0;

    public FractalTree(int initStep) {
        this.step = initStep;
    }

    public void doDrawing(Graphics2D g, int x, int y, int angle, int step) {

        if (step == 0) return;
        int x2 = x + (int) (Math.cos(Math.toRadians(angle)) * step * 15);
        int y2 = y + (int) (Math.sin(Math.toRadians(angle)) * step * 15);
        //g.setStroke(new BasicStroke(  (int) ((7-step)/7) * startStrokeSize ));
        g.drawLine(x, y, x2, y2);
        doDrawing(g, x2, y2, angle - 20, step - 1);
        doDrawing(g, x2, y2, angle + 20, step - 1);
    }

    @Override
    public String getName() {
        return "Tree";
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
        return "Rekursive construction of a tree.\n\n\n\n\n\n\n\n\n\n\n";
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
        AY = 5 * this.getHeight() / 6;
        doDrawing(g2, AX, AY, startAngle, step);
    }
}
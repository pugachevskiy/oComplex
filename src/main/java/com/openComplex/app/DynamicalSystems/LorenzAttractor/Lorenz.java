package com.openComplex.app.DynamicalSystems.LorenzAttractor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 25/08/15.
 */
public class Lorenz implements ActionListener {

    private LorenzView2 gui;
    LorenzModel lorenz1 = new LorenzModel(0.0, 20.00, 25.0);
    double dt = 0.01;
    private int step = 0;
    private int stepMax = 5000;
    private double[] xArray, yArray, zArray;

    public Lorenz() {
        gui = new LorenzView2();
        gui.addListener(this);
        gui.setTextA(String.valueOf(lorenz1.getA()));
        gui.setTextB(String.valueOf(lorenz1.getB()));
        gui.setTextC(String.valueOf(lorenz1.getC()).substring(0, 5));
        xArray = new double[stepMax+1];
        zArray = new double[stepMax+1];
        yArray = new double[stepMax+1];

    }
    public void draw(LorenzModel lorenz, int i) {
        xArray[i] = lorenz.getX1();
        yArray[i] = lorenz.getY1();
        zArray[i] = lorenz.getZ1();
    }

    private void start(){
        while (step < stepMax) {
            step++;
            lorenz1.update(dt);
            draw(lorenz1, step);
        }
        stopDraw();
        gui.doPaint(xArray,yArray,zArray);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Draw":
                setCoeff();
                start();
                gui.setStartButtonText("Draw");
                break;
            case "Clear":
                gui.clear();
                step = 0;
                break;
        }
    }

    private void stopDraw(){
        step = 0;
        lorenz1 = new LorenzModel(0.0, 20.00, 25.0);
    }

    private void setCoeff(){
        lorenz1.setA(gui.getTextA());
        lorenz1.setB(gui.getTextB());
        lorenz1.setC(gui.getTextC());
    }
}
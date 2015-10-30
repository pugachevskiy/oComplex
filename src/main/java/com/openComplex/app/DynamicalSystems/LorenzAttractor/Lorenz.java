package com.openComplex.app.DynamicalSystems.LorenzAttractor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  on 25/08/15.
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
        xArray = new double[stepMax+1];
        zArray = new double[stepMax+1];
        yArray = new double[stepMax+1];
        gui.setTextA(String.valueOf(lorenz1.getA()));
        gui.setTextB(String.valueOf(lorenz1.getB()));
        gui.setTextC(String.valueOf(lorenz1.getC()).substring(0, 5));

    }
    public void draw(LorenzModel lorenz, int i) {
        xArray[i] = lorenz.getX1();
        yArray[i] = lorenz.getY1();
        zArray[i] = lorenz.getZ1();
    }

    private void start(){
        new Thread(){
            public void run() {
                while (step < stepMax) {

                    try {
                        step++;
                        lorenz1.update(dt);
                        draw(lorenz1, step);
                        if (step % 25 == 0)
                        gui.doPaint(xArray, yArray, zArray);
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                stopDraw();
            }
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Draw":
                clear();
                setCoeff();
                gui.deactivateControls();
                start();
                break;
            case "Clear":
                clear();
                break;
        }
    }

    private void stopDraw(){
        lorenz1 = new LorenzModel(0.0, 20.00, 25.0);
        gui.activateControls();
    }

    private void setCoeff(){
        lorenz1.setA(gui.getTextA());
        lorenz1.setB(gui.getTextB());
        lorenz1.setC(gui.getTextC());
    }

    private void clear(){
        gui.clear();
        xArray = new double[stepMax+1];
        zArray = new double[stepMax+1];
        yArray = new double[stepMax+1];
        step = 0;
    }
}
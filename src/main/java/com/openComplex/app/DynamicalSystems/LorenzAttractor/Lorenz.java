package com.openComplex.app.DynamicalSystems.LorenzAttractor;


import java.awt.*;
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
    private boolean isActive;

    public Lorenz() {
        gui = new LorenzView2();
        gui.addListener(this);
        gui.setTextA(String.valueOf(lorenz1.getA()));
        gui.setTextB(String.valueOf(lorenz1.getB()));
        gui.setTextC(String.valueOf(lorenz1.getC()).substring(0, 5));

    }

    public void start() {

        // Use Euler's method to numerically solve ODE
        new Thread() {
            public void run() {
                try {
                    while (isActive && step < 5000) {
                        step++;
                        lorenz1.update(dt);
                        gui.draw(lorenz1, step);
                        sleep(1);
                    }
                    gui.doPaint();
                    stopDraw();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start":
                isActive = true;
                setCoeff();
                start();
                gui.setStartButtonText("Stop");
                break;
            case "Stop":
                stopDraw();
                break;
            case "Clear":
                gui.clear();
                step = 0;
                break;
        }
    }

    private void stopDraw(){
        isActive = false;
        step = 0;
        gui.setStartButtonText("Start");
        lorenz1 = new LorenzModel(0.0, 20.00, 25.0);
    }

    private void setCoeff(){
        lorenz1.setA(gui.getTextA());
        lorenz1.setB(gui.getTextB());
        lorenz1.setC(gui.getTextC());
    }
}
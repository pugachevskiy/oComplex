package com.openComplex.app.DynamicalSystems.LorenzAttractor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * on 25/08/15.
 */
public class Lorenz implements ActionListener {

    private LorenzView2 gui;
    LorenzModel lorenz;
    double dt = 0.001;
    private int step = 0;
    private int stepMax = 100000;
    private double[] xArray, yArray, zArray;
    private boolean flag = false;
    private double x, y;

    public Lorenz() {
        gui = new LorenzView2();
        gui.addListener(this);
        xArray = new double[stepMax + 1];
        zArray = new double[stepMax + 1];
        yArray = new double[stepMax + 1];
        gui.setTextPoints("10000");
        gui.setTextx("0");
        gui.setTexty("20.0");


        x = gui.getTextx();
        y = gui.getTexty();

        lorenz = new LorenzModel(x, y, 25.0);
        gui.setTextA(String.valueOf(lorenz.getA()));
        gui.setTextB(String.valueOf(lorenz.getB()));
        gui.setTextC(String.valueOf(lorenz.getC()).substring(0, 5));

    }

    public void draw(LorenzModel lorenz, int i) {
        xArray[i] = lorenz.getX1();
        yArray[i] = lorenz.getY1();
        zArray[i] = lorenz.getZ1();
    }

    private void start() {
        new Thread() {
            public void run() {
                while (step < stepMax) {
                    if (flag) {
                        step++;
                        lorenz.update(dt);
                        draw(lorenz, step);
                        StdDraw.setPenColor(StdDraw.BLACK);
                        StdDraw.point(xArray[step], zArray[step]);

                        if (step % 10 == 0) {
                            StdDraw.show(2);
                        }
                    }
                }
                gui.activateControls();
            }
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Draw":
                flag = true;
                stepMax = gui.getTextPoints();
                openFrame();
                x = gui.getTextx();
                y = gui.getTexty();
                lorenz = new LorenzModel(x, y, 25.0);
                setCoeff();
                gui.deactivateControls();
                start();
                break;
            case "Clear":
                clear();
                StdDraw.show();
                break;
            case "Stop":
                flag = false;
                gui.activateControls();
                break;
        }
    }

    private void openFrame() {
        if (!StdDraw.isFrameOpen) {
            StdDraw.init();
        }
        clear();
        StdDraw.show();

    }

    private void setCoeff() {
        lorenz.setA(gui.getTextA());
        lorenz.setB(gui.getTextB());
        lorenz.setC(gui.getTextC());


    }

    private void clear() {
        gui.clear();
        xArray = new double[stepMax + 1];
        zArray = new double[stepMax + 1];
        yArray = new double[stepMax + 1];
        step = 0;
    }
}
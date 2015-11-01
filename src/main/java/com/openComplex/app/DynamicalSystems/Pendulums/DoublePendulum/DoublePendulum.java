package com.openComplex.app.DynamicalSystems.Pendulums.DoublePendulum;

/**
 *  on 07/10/15.
 */

import java.awt.event.*;

public class DoublePendulum implements ActionListener {


    private int step;
    private boolean stop = false;


    private DoublePendulumView gui;
    private DoublePendulumModel model;

    public DoublePendulum() {
        gui = new DoublePendulumView(this);
        model = new DoublePendulumModel();
        gui.addPanel(model);
    }

    public void start() {
        new Thread() {
            public void run() {
                model.startwerte();
                step = 0;
                while (stop) {
                    try {
                        sleep(1);
                        model.update();
                        step++;
                    } catch (InterruptedException e) {
                    }
                }

            }
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start":
                stop = !stop;
                start();
                break;
            case "New":
                stop = false;
                model.startwerte(); //set initial values
                step = 0;
                model.pixels(); //calculate pixelcoords
                model.repaint(); //paint initial state
                break;
            case "Phi1 +":
                stop = false;
                model.phi1Plus();
                break;
            case "Phi1 -":
                stop = false;
                model.phi1Minus();
                break;
            case "Phi2 +":
                stop = false;
                model.phi2Plus();
                break;
            case "Phi2 -":
                stop = false;
                model.phi2Minus();
                break;
            case "Friction ++":
                stop = false;
                model.frictionPlus();
                break;
            case "Friction --":
                stop = false;
                model.frictionMinus();
                break;
            case "m1 +":
                stop = false;
                model.m1Plus();
                break;
            case "m1 -":
                stop = !stop;
                model.m1Minus();
                break;
            case "m2 +":
                stop = false;
                model.m2Plus();
                break;
            case "m2 -":
                stop = false;
                model.m2Minus();
                break;
        }
    }
}


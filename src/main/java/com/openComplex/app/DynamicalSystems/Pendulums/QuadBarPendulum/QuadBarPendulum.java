package com.openComplex.app.DynamicalSystems.Pendulums.QuadBarPendulum;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 13/10/15.
 */
public class QuadBarPendulum implements ActionListener {

    private QuadBarPendulumView gui;
    private QuadBarPendulumModel model;
    private int step;
    private boolean stop;

    public QuadBarPendulum() {
        gui = new QuadBarPendulumView(this);
        model = new QuadBarPendulumModel();
        gui.addPanel(model);
    }

    public void start() {
        new Thread() {
            public void run() {
                model.startwerteDrivenY();
                step = 0;
                while (stop) {
                    try {
                        sleep(1);
                        model.setStep(step);
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
            case "New":
                stop = false;
                step = 0;
                model.startwerte(); //set initial values
                model.treibwerte(step);
                model.repaint();
                break;
            case "Start":
                stop = !stop;
                start();
                break;
            case "Friction ++":
                model.frictionPlus();
                break;
            case "Friction --":
                model.frictionMinus();
                break;
            case "Phi1 +":
                model.phi1Plus();
                break;
            case "Phi1 -":
                model.phi1Minus();
                break;
            case "Phi2 +":
                model.phi2Plus();
                break;
            case "Phi2 -":
                model.phi2Minus();
                break;
            case "Phi3 +":
                model.phi3Plus();
                break;
            case "Phi3 -":
                model.phi3Minus();
                break;
            case "Phi4 +":
                model.phi4Plus();
                break;
            case "Phi4 -":
                model.phi4Minus();
                break;
        }
    }
}

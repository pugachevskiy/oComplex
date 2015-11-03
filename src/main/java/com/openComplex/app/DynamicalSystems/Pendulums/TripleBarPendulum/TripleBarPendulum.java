package com.openComplex.app.DynamicalSystems.Pendulums.TripleBarPendulum;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  on 13/10/15.
 */
public class TripleBarPendulum implements ActionListener{

    private TripleBarPendulumView gui;
    private TripleBarPendulumModel model;
    private int step;
    private boolean stop;

    public TripleBarPendulum() {
        gui = new TripleBarPendulumView(this);
        model = new TripleBarPendulumModel();
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
                model.resetAttractor();
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
        }
    }
}

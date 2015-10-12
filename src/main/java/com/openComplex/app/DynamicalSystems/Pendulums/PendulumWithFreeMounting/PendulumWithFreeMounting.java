package com.openComplex.app.DynamicalSystems.Pendulums.PendulumWithFreeMounting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 12/10/15.
 */
public class PendulumWithFreeMounting implements ActionListener {

    private PendulumWithFreeMountingModel model;
    private PendulumWithFreeMountingView gui;
    private int step;
    private boolean stop;

    public PendulumWithFreeMounting() {
        gui = new PendulumWithFreeMountingView(this);
        model = new PendulumWithFreeMountingModel();
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
            case "Phi +":
                model.phiPlus();
                break;
            case "Phi -":
                model.phiMinus();
                break;
            case "m1/m2 +":
                model.mPlus();
                break;
            case "m1/m2 -":
                model.mMinus();
                break;
            case "a +":
                model.aPlus();
                break;
            case "a -":
                model.aMinus();
                break;
            case "Omega +":
                model.omegaPlus();
                break;
            case "Omega -":
                model.omegaMinus();
                break;

        }
    }
}

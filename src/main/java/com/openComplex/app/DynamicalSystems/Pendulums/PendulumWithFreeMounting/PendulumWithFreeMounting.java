package com.openComplex.app.DynamicalSystems.Pendulums.PendulumWithFreeMounting;

import com.openComplex.app.DynamicalSystems.Pendulums.Pendulums;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  on 12/10/15.
 */
public class PendulumWithFreeMounting extends Pendulums implements ActionListener {

    private PendulumWithFreeMountingModel model;
    private PendulumWithFreeMountingView gui;
    private int step;
    private boolean stop;
    private final int numberOfTrajectories = 1;

    public PendulumWithFreeMounting() {
        gui = new PendulumWithFreeMountingView(this);
        model = new PendulumWithFreeMountingModel();
        model.setTrajectoryColors(getColors());
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
            case "Trajectory":
                gui.buildTrajectorySettings(numberOfTrajectories, model.getLimit(), this);
                break;
            case "Trajectory 1":
                colorChooser(1);
                model.setTrajectoryColors(getColors());
                break;
            case "Limit":
                model.setTrajectory(numberOfTrajectories, gui.getLimit());
                break;

        }
    }
}

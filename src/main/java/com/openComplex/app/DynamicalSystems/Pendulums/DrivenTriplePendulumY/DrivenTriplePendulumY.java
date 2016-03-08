package com.openComplex.app.DynamicalSystems.Pendulums.DrivenTriplePendulumY;

import com.openComplex.app.DynamicalSystems.Pendulums.Pendulums;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *  on 11/10/15.
 */
public class DrivenTriplePendulumY extends Pendulums implements ActionListener, ItemListener {

    private DrivenTriplePendulumYView gui;
    private DrivenTriplePendulumYModel model;
    private int step;
    private boolean stop;
    private final int numberOfTrajectories = 3;

    public DrivenTriplePendulumY() {
        gui = new DrivenTriplePendulumYView(this, this);
        model = new DrivenTriplePendulumYModel();
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
                model.startwerteDrivenTripleY(); //set initial values
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
            case "Trajectory":
                gui.buildTrajectorySettings(numberOfTrajectories, model.getLimit(),this);
                break;
            case "Trajectory 1":
                colorChooser(1);
                model.setTrajectoryColors(getColors());
                break;
            case "Trajectory 2":
                colorChooser(2);
                model.setTrajectoryColors(getColors());
                break;
            case "Trajectory 3":
                colorChooser(3);
                model.setTrajectoryColors(getColors());
                break;
            case "Limit":
                model.setTrajectory(numberOfTrajectories, gui.getLimit());
                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == "Values_1") {
            model.value1();
        }
        if (e.getItem() == "Values_2") {
            model.value2();
        }
        if (e.getItem() == "Values_3") {
            model.value3();
        }
        if (e.getItem() == "Values_4") {
            model.value4();
        }
    }
}

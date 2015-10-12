package com.openComplex.app.DynamicalSystems.Pendulums.DrivenTriplePendulumY;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by strange on 11/10/15.
 */
public class DrivenTriplePendulumY implements ActionListener, ItemListener {

    private DrivenTriplePendulumYView gui;
    private DrivenTriplePendulumYModel model;
    private int step;
    private boolean stop;

    public DrivenTriplePendulumY() {
        gui = new DrivenTriplePendulumYView(this, this);
        model = new DrivenTriplePendulumYModel();
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

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                stop = false;
                step = 0;
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
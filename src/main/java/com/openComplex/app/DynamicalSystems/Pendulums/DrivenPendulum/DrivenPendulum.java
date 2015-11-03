package com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulum;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 *  on 09/10/15.
 */
public class DrivenPendulum implements ActionListener{
    private int step;
    private boolean stop = false;

    private DrivenPendulumView gui;
    private DrivenPendulumModel model;

    public DrivenPendulum() {
        gui = new DrivenPendulumView(this);
        model = new DrivenPendulumModel();
        gui.addPanel(model);
    }

    public void start() {
        new Thread() {
            public void run() {
                model.startwerteDriven();
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
        switch (command){
            case "New":
                stop = false;
                step = 0;
                model.resetAttractor();
                model.startwerteDriven(); //set initial values
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
            case "Amp +":
                model.ampPlus();
                break;
            case "Amp -":
                model.ampMinus();
                break;
            case "Freq +":
                model.freqPlus();
                break;
            case "Freq -":
                model.freqMinus();
                break;
        }
    }

}

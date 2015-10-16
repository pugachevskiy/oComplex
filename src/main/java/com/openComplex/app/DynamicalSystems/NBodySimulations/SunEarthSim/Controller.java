package com.openComplex.app.DynamicalSystems.NBodySimulations.SunEarthSim;

import com.openComplex.app.DynamicalSystems.NBodySimulations.GasIn2DimBox.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by strange on 16/10/15.
 */
public class Controller implements ActionListener, ItemListener {
    private View gui;
    private Model model;
    private int step;
    private boolean stop;

    public Controller() {
        gui = new View(this, this);
        model = new Model();
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
        // maxstep = (int) Double.valueOf(getText()).doubleValue();
        String command = e.getActionCommand();
        switch (command) {

            case "New":
                model.startwerte();
                step = 0;
                stop = true;
                start();
                break;
            case "Start":
                stop = !stop;
                start();
                break;
            case "vy_Earth +":
                model.vyEarthPlus();
                break;
            case "vy_Earth -":
                model.vyEarthMinus();
                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == "Values_1") {
            model.values1();
        }
        if (e.getItem() == "Values_2") {
            model.values2();
        }
        if (e.getItem() == "Values_3") {
            model.values3();
        }
        if (e.getItem() == "Values_4") {
            model.values4();
        }
    } //itemStateChanged(evt)

}

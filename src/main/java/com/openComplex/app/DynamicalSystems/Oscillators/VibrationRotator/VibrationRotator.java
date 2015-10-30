package com.openComplex.app.DynamicalSystems.Oscillators.VibrationRotator;


import java.awt.event.*;

/**
 *  on 07/10/15.
 */
public class VibrationRotator implements ActionListener, ItemListener, AdjustmentListener {

    private int step;
    private boolean stop = false;
    private VibrationRotatorView gui;
    private VibrationRotatorModel model;

    public VibrationRotator(){
        gui = new VibrationRotatorView();
        model = new VibrationRotatorModel(gui.getSpeed());
        gui.addListener(this, this, this);
        gui.addPanel(model);
    }



    public void start() {
        new Thread() {
            public void run() {
                model.startwerte();
                step = 0;
               while (stop) {
                    try {
                        sleep(10);
                        model.update(step);
                        step++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }//while
            }
        }.start();
    }//run


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Friction ++":
                model.plusFriction();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                break;
            case "Friction --":
                model.minusFriction();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                break;
            case "Go":
                stop = !stop;
                start();
                break;
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == "Values_1") {
            model.state1();
        }
        if (e.getItem() == "Values_2") {
            model.state2();
        }
        if (e.getItem() == "Values_3") {
            model.state3();
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        model.setSpeedSlider(e.getValue());
    }
}


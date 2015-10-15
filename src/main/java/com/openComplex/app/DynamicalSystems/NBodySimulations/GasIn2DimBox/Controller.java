package com.openComplex.app.DynamicalSystems.NBodySimulations.GasIn2DimBox;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

/**
 * Created by strange on 15/10/15.
 */
public class Controller implements ActionListener, AdjustmentListener {
    private int step = 0;
    private int maxstep = 1000000;//run-time
    private boolean stop;
    private View gui;
    private Model model;
    private int speed = 50;


    public Controller() {
        gui = new View(this, this);
        model = new Model();
        gui.addPanel(model);
    }


    public void start() {
        new Thread() {
            public void run() {
                model.reset();
                step = 0;
                while (stop) {
                    try {
                        sleep(100/speed);
                        model.update();
                        model.repaint(); //paint new frame
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
        switch (command){
            case "Reset":
                model.reset();
                step = 0;
                break;
            case "New":
                model.reset();
                step = 0;
                stop = true;
                start();
                break;
            case "Start":
                stop = !stop;
                start();
                break;
        }
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        JScrollBar scrollBar = (JScrollBar) e.getSource();
        switch (scrollBar.getName()){
            case "Speed":
                speed = Math.abs(e.getValue() - 1001);
                model.setSpeed(Math.abs(e.getValue() - 1001));
                model.reset();
                model.repaint();
                break;
            case "Number":
                model.setN(Math.abs(e.getValue()-101));
                model.reset();
                model.repaint();

        }

    }
}

package com.openComplex.app.DynamicalSystems.Oscillators.VibrationRotator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by strange on 07/10/15.
 */
public class VibrationRotator implements ActionListener, ItemListener {

    private int step;
    private VibrationRotatorView gui;
    private VibrationRotatorModel model;

    public VibrationRotator(){
        gui = new VibrationRotatorView();
        model = new VibrationRotatorModel();
      //  gui.addListener(this, this);
     //   gui.addPanel(model);
    }



    public void start() {
        new Thread() {
            public void run() {
                model.startwerte();
                step = 0;
           /*     while (stop) {
                    try {
                        sleep(10);
                        model.update(step);
                        step++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }//while*/
            }
        }.start();
    }//run



   /* public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand() == but_reib_plus.getActionCommand()) {
            reib++;
            if (cb_stop.getState()) {
                update(g);
            }
        }
        if (evt.getActionCommand() == but_reib_min.getActionCommand()) {
            if (reib > 0) {
                reib--;
                if (cb_stop.getState()) {
                    update(g);
                }
            }
        }
    }//actionPerformed(evt)

    public void itemStateChanged(ItemEvent evt) //Choice object
    {
        if (evt.getItem() == "Values_1") {
            stop();
            startwert[0] = 1.; //r
            startwerte();
            pixels();
            update(g);
            start();
        }
        if (evt.getItem() == "Values_2") {
            stop();
            startwert[0] = 0.7; //r
            startwerte();
            pixels();
            update(g);
            start();
        }
        if (evt.getItem() == "Values_3") {
            stop();
            startwert[0] = 0.5; //r
            startwerte();
            pixels();
            update(g);
            start();
        }
    } //itemStateChanged(evt)*/

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}


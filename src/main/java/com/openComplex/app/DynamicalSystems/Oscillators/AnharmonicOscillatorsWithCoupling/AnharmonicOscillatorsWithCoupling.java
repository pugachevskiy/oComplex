package com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillatorsWithCoupling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 *  on 07/10/15.
 */
public class AnharmonicOscillatorsWithCoupling implements ActionListener, ItemListener {

    private int step;
    private boolean stop = false;
    private AnharmonicOscillatorsWithCouplingModel model;
    private AnharmonicOscillatorsWithCouplingView gui;

    public AnharmonicOscillatorsWithCoupling() {
        gui = new AnharmonicOscillatorsWithCouplingView();
        model = new AnharmonicOscillatorsWithCouplingModel();
        gui.addListener(this, this);
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
                        model.update(gui.getCheckBoxGravity());
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
            case "Go":
                stop = !stop;
                start();
                break;
            case "New":
                model.startwerte();
                break;
            case "Spring +":
                model.setDPlus();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                break;
            case "Spring -":
                model.setDMinus();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                break;
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
        }

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem() == "Free") {
            model.setChoiceFree();
        }
        if (e.getItem() == "Together") {
            model.setChoiceTogether();
        }
        if (e.getItem() == "Against") {
            model.setChoiceAganist();
        }
    }
}

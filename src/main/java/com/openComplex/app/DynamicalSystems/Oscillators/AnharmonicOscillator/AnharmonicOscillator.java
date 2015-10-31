package com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 *  on 07/10/15.
 */
public class AnharmonicOscillator implements ActionListener {


    private boolean stop = false; //go/pause
    private int step;
    private AnharmonicOscillatorView gui;
    private AnharmonicOscillatorModel model;

    public AnharmonicOscillator() {
        gui = new AnharmonicOscillatorView();
        model = new AnharmonicOscillatorModel();
        gui.addListener(this);

        gui.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                model.updateSize(gui.getWidth(), gui.calculatePanelHeight()-100);
            }

            @Override
            public void componentMoved(ComponentEvent e) { }
            public void componentShown(ComponentEvent e) { }
            public void componentHidden(ComponentEvent e) {}
        });
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
                            model.update();
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


}

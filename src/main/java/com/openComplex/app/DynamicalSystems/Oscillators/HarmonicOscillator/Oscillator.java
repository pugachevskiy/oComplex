package com.openComplex.app.DynamicalSystems.Oscillators.HarmonicOscillator;

/**
 *  on 06/10/15.
 */

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;


public class Oscillator implements ActionListener {

    private OscillatorView gui;
    private OscillatorModel model;
    private int step = 0;
    private boolean stop = false;


    public Oscillator() {
        gui = new OscillatorView();
        model = new OscillatorModel();
        gui.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                model.updateSize(gui.getWidth()-20, gui.calculatePanelHeight()-20);
            }
            public void componentMoved(ComponentEvent e) { }
            public void componentShown(ComponentEvent e) { }
            public void componentHidden(ComponentEvent e) {}
        });
        gui.addJSlider(createFrictionSlider(), createSpringSlider());
        gui.addListener(this);
        gui.addPanel(model);
    }


    public void start() {
        new Thread() {
            public void run() {
                while (stop) {
                    try {
                        sleep(20);
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
            case "Go":
                stop = !stop;
                start();
                break;
        }
    }

    private JSlider createFrictionSlider() {
        JSlider frictionSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 20, 1);
        frictionSlider.setPaintLabels(true);
        frictionSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                model.updateFriction(value);
                gui.updateFrictionLabel();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                start();
            }
        });
        return frictionSlider;
    }

    private JSlider createSpringSlider (){
        JSlider springSlider = new JSlider(SwingConstants.HORIZONTAL, 0, 16, 1);
        springSlider.setPaintLabels(true);
        springSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                model.updateSpring(value/4);
                gui.updateSpringLabel();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                start();
            }
        });
        return springSlider;
    }
}



    /*class ML extends MouseAdapter implements MouseMotionListener  //overwrite mouse
    {
        public void mousePressed(MouseEvent evt) {
            stop = false;
            step = 0;
            //new amplitude
            if (evt.getX() < 40) {
                amp = (double) (40 - W / 2) / (double) (W / 2 - 50);
            } else if (evt.getX() > W - 40) {
                amp = (double) (W - 40 - W / 2) / (double) (W / 2 - 50);
            } else {
                amp = (double) (evt.getX() - W / 2) / (double) (W / 2 - 50);
            }
            x = amp; //to calculate pixelcoordinates
            pixels(); //pixelcoordinates
            paint();
        }//mousePressed

        public void mouseDragged(MouseEvent evt) {
            if (evt.getX() < 40) {
                amp = (double) (40 - W / 2) / (double) (W / 2 - 50);
            } else if (evt.getX() > W - 40) {
                amp = (double) (W - 40 - W / 2) / (double) (W / 2 - 50);
            } else {
                amp = (double) (evt.getX() - W / 2) / (double) (W / 2 - 50);
            }
            x = amp; //to calculate pixelcoordinates
            pixels(); //pixelcoordinates
            paint();
        }//mouseDragged

        public void mouseReleased(MouseEvent evt) {
            start();
        }//mouseReleased

        public void mouseMoved(MouseEvent evt) {
        }//mouseMoved (simply implemented for Interface MouseMotionListener)

    }//ML
*/
package com.openComplex.app.DynamicalSystems.Oscillators.HarmonicOscillator;

/**
 *  on 06/10/15.
 */

import java.awt.event.*;


public class Oscillator implements ActionListener {

    private OscillatorView gui;
    private OscillatorModel model;
    private int step = 0;
    private boolean stop = false; //go/pause


    public Oscillator() {
        gui = new OscillatorView();
        model = new OscillatorModel();
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
            case "Spring +":
                model.plusSpring();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                start();

                break;
            case "Spring -":
                model.minusSpring();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                start();
                break;
            case "Friction ++":
                model.plusFriction();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                start();
                break;
            case "Friction --":
                model.minusFriction();
                stop = false;
                step = 0;
                model.startwerte();
                model.repaint();
                start();
                break;
            case "Go":
                stop = !stop;
                start();
                break;
        }
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
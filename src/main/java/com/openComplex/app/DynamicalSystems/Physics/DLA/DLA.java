package com.openComplex.app.DynamicalSystems.Physics.DLA;

/**
 * Created by strange on 06/10/15.
 */

import java.awt.event.*;


public class DLA implements ActionListener {

    private int step = 0;
    private int maxstep = 20000; //run-time
    private DLAView gui;
    private DLAModel model;
    private boolean flag = false;

    public DLA() {
        gui = new DLAView();
        model = new DLAModel();
        gui.addListener(this);
        gui.addPanel(model);
    }


    public void start() {
        new Thread() {
            public void run() {
                model.reset();

                    try {
                        while (step < maxstep) {
                            if (flag) { //stop/go
                                sleep(10);
                                model.update();
                                step++;
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }//run
        }.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
       // maxstep = gui.getTfStep();
        if (e.getActionCommand().equals("Reset")) {
            model.reset();
            flag = false;
            model.repaint();
        }

        if (e.getActionCommand().equals("Start")){
            flag = !flag;
            start();
        }

    }
}

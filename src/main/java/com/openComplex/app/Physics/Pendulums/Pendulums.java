package com.openComplex.app.Physics.Pendulums;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 05/10/15.
 */


public class Pendulums implements ActionListener {


    private PendulumsView gui;
    private PendulumsModel model;
    private int step = 0;

    public Pendulums() {
        gui = new PendulumsView();
        model = new PendulumsModel();
        gui.addListener(this);
    }


    public void start() {
        new Thread() {
            public void run() {
                try {
                    sleep(700);
                } catch (InterruptedException e) {
                }

                // ghead.drawString("Change Phi_22 by a minimal value ...", 120, 25);

                model.startwerte();
                step = 0;
                while (model.getStatus()) {
                    try {
                        Thread.sleep(40);
                    } catch (InterruptedException e) {
                    }
                }

	       	/* pendulum 1 */
                model.solve1();

	       	/* pendulum 2 */
                model.solve2();

                if (step % 15 == 0) //paint not so often
                {
                    try {
                        Thread.sleep(8);
                    } catch (InterruptedException e) {
                    }
                    model.pixels(); //calculate pixelcoords
                    gui.repaint(); //paint new frame
                }
                step++;
            }
        }.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Go":
                model.setStatus(!model.getStatus());
                break;
            case "New":
                //  stop();
                model.startwerte(); //set initial values
                step = 0;
                model.pixels(); //calculate pixelcoords
                //  update(g); //paint initial state
                start();
                break;
            case "Plus":
                //    stop();
                model.setStartwert(model.getStartwert() + Math.PI / 180 * 0.01);
                model.startwerte();
                step = 0;
                model.pixels(); //calculate pixelcoords
                //    update(g); //paint initial state
                start();
                break;
            case "Minus":
                //    stop();
                model.setStartwert(model.getStartwert() - Math.PI / 180 * 0.01);
                model.startwerte();
                step = 0;
                model.pixels(); //calculate pixelcoords
                //    update(g); //paint initial state
                start();
                break;


        }
    }
}

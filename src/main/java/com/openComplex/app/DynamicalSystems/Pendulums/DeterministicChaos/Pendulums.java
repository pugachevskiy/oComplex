package com.openComplex.app.DynamicalSystems.Pendulums.DeterministicChaos;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Pendulums implements ActionListener {

    private PendulumsView gui;
    private PendulumsModel model;
    private int step = 0;

    public Pendulums() {
        gui = new PendulumsView();
        model = new PendulumsModel();
        gui.init();
        gui.addListener(this);
        gui.addPanel(model);
    }


    public void start() {
        new Thread() {
            public void run() {
                model.startwerte();
                step = 0;
                while (model.getStatus()) {
                    try {
                        sleep(1);
                        // pendulum 1
                        model.solve1();
                        // pendulum 2
                        model.solve2();
                        model.pixels(); //calculate pixelcoords
                        model.repaint(); //paint new frame
                        step++;
                        if (step % 15 == 0) ;
                    } catch (InterruptedException e) {
                    }
                }

            }
        }.start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Go":
                model.setStatus(!model.getStatus());
                start();
                break;
            case "New":
                model.setStatus(false);
                model.startwerte(); //set initial values
                step = 0;
                model.pixels(); //calculate pixelcoords
                model.repaint(); //paint initial state
                break;
            case "Plus":
                model.setStatus(false);
                model.setStartwert(model.getStartwert() + Math.PI / 180 * 1.00);
                model.startwerte();
                step = 0;
                model.pixels(); //calculate pixelcoords
                model.repaint();
                break;
            case "Minus":
                model.setStatus(false);
                model.setStartwert(model.getStartwert() - Math.PI / 180 * 1.00);
                model.startwerte();
                step = 0;
                model.pixels(); //calculate pixelcoords
                model.repaint();
                break;
        }
    }
}

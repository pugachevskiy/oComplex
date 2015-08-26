package com.openComplex.app;


import java.awt.*;

/**
 * Created by strange on 25/08/15.
 */
public class Lorenz {

    private LorenzView gui;
    LorenzModel lorenz1 = new LorenzModel(0.0, 20.00, 25.0);
    double dt = 0.001;
  //  LorenzModel lorenz2 = new LorenzModel(20.0, 130.01, 40.0);
    public Lorenz(){
        start();
    }

    public void start() {
        gui = new LorenzView();
        gui.init();
        // Use Euler's method to numerically solve ODE
        new Thread() {
            public void run() {

                try {
                    for (int i = 0; i < 50000; i++) {

                        lorenz1.update(dt);
                        gui.draw(lorenz1, Color.BLUE, i,150);

                        sleep(2);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
package com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillatorsWithCoupling;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * Created by strange on 07/10/15.
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


    public void actionPerformed(ActionEvent evt) //buttons
    {
        if (evt.getActionCommand() == but_new.getActionCommand()) {
            stop();
            startwerte(); //set initial values
            pixels(); //calculate pixelcoords
            update(g); //paint initial state
            start();
        }
        if (evt.getActionCommand() == but_D_plus.getActionCommand()) {
            if (startwert[8] < 3) {
                startwert[8] = 2 * startwert[8];
                D = startwert[8];
                if (cb_stop.getState()) {
                    update(g);
                }
            }
        }
        if (evt.getActionCommand() == but_D_min.getActionCommand()) {
            startwert[8] = startwert[8] / 2;
            D = startwert[8];
            if (cb_stop.getState()) {
                update(g);
            }
        }
        if (evt.getActionCommand() == but_reib_plus.getActionCommand()) {
            startwert_reib++;
            reib = startwert_reib;
            if (cb_stop.getState()) {
                update(g);
            }
        }
        if (evt.getActionCommand() == but_reib_min.getActionCommand()) {
            if (reib > 0) {
                startwert_reib--;
                reib = startwert_reib;
                if (cb_stop.getState()) {
                    update(g);
                }
            }
        }
    }//actionPerformed(evt)

    public void itemStateChanged(ItemEvent evt) //Choice object
    {
        if (evt.getItem() == "Free") {
            animator = null;
            Choice[0] = true; //Free
            Choice[1] = false; //Together
            Choice[2] = false; //Against
            startwert[0] = 0.2; //x1
            startwert[2] = 0.2; //y1
            startwert[4] = 0.7; //x2
            startwert[6] = 0.0; //y2
            startwerte();
            pixels();
            update(g);
            start();
        }
        if (evt.getItem() == "Together") {
            animator = null;
            Choice[0] = false;
            Choice[1] = true;
            Choice[2] = false;
            startwert[0] = 1. / 3 - 0.2; //x1
            startwert[2] = 0.0; //y1
            startwert[4] = 2. / 3 - 0.2; //x2
            startwert[6] = 0.0; //y2
            startwerte();
            pixels();
            update(g);
            start();
        }
        if (evt.getItem() == "Against") {
            animator = null;
            Choice[0] = false;
            Choice[1] = false;
            Choice[2] = true;
            startwert[0] = 1. / 3 - 0.1; //x1
            startwert[2] = 0.0; //y1
            startwert[4] = 2. / 3 + 0.1; //x2
            startwert[6] = 0.0; //y2
            startwerte();
            pixels();
            update(g);
            start();
        }
    } //itemStateChanged(evt)

}

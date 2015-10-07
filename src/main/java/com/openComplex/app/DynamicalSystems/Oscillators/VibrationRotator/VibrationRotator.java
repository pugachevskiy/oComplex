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
    Button but_reib_plus, but_reib_min;
    Choice Chooser;
    Checkbox cb_stop;
    Scrollbar speed;
    Label descrip;


    public void init() {
        setLayout(null);
        can = new Canvas();
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        cb_stop = new Checkbox("Stop/Go", false);
        Choice Chooser = new Choice();
        Chooser.add("Values_1");
        Chooser.add("Values_2");
        Chooser.add("Values_3");

        can.setBounds(1, 1, Lx + 100, Ly);
        can.setBackground(Color.white);
        pan.setBounds(Lx + 101, 1, 100, Ly);
        pan.setBackground(new Color(0, 200, 100));

        Chooser.setBounds(10, 20, 80, 30);
        but_reib_plus.setBounds(10, 60, 80, 30);
        but_reib_min.setBounds(10, 100, 80, 30);
        cb_stop.setBounds(10, 145, 80, 30);
        cb_stop.setBackground(new Color(0, 200, 100));

        speed = new Scrollbar(Scrollbar.VERTICAL, 40, 20, 0, 80);
        speed.setSize(15, 100);
        speed.setLocation(10, 190);

        speed.addAdjustmentListener(new AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                phi0 = phi; //save the current value for phi
                startwert[1] = (60. - (double) e.getValue()) / 10.; //new omega
                omega = startwert[1];
                step = 0;
                pixels();
                update(g);
            }
        });

        descrip = new Label("Omega");
        descrip.setSize(50, 30);
        descrip.setLocation(35, 268);

        pan.add(speed);
        pan.add(descrip);
        pan.add(Chooser);
        pan.add(but_reib_plus);
        pan.add(but_reib_min);
        pan.add(cb_stop);
        add(can);
        add(pan);

        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
        Chooser.addItemListener(this);

        g = can.getGraphics();
        g.setFont(new Font("Verdana", Font.BOLD, 10));

        startwert[0] = r0; //r
        startwert[1] = (60. - (double) speed.getValue()) / 10.; //omega
    }//init()

    public void startwerte() {
        r = startwert[0];
        omega = startwert[1];
        phi = 0.;
        phi0 = 0.;
        rp = 0.;
        step = 0;
    }//startwerte()

    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
        }
        startwerte();

        while (Thread.currentThread() == animator) {
            if (cb_stop.getState()) //Stop/Go
            {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
                continue;
            }

            phi = phi0 + omega * step * dt; //increase phi with an offset of phi0
            runge_step_r(); //Runge-Kutta-calculation
            r = r + (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6; //new r
            rp = rp + (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6; //new rp

            if (step % 20 == 0) //paint frame not so often
            {
                try {
                    Thread.sleep(12);
                } catch (InterruptedException e) {
                }
                pixels(); //claculate pixelcoordinates
                repaint(); //paint new frame
            }
            step++; //increase step
        }//while currentThread()==animator
    }//run()



    public void actionPerformed(ActionEvent evt) {
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
    } //itemStateChanged(evt)

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}


package com.openComplex.app.DynamicalSystems.Oscillators.VibrationRotator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * Created by strange on 07/10/15.
 */
public class VibrationRotatorView {
    Button but_reib_plus, but_reib_min;
    Choice Chooser;
    Checkbox cb_stop;
    Scrollbar speed;
    Label descrip;

  /*  public VibrationRotatorView(){
        init();
    }


    public void init() {

        JPanel pan = new JPanel();
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        cb_stop = new Checkbox("Stop/Go", false);
        Chooser = new Choice();
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


    }//init()

    public void addListener(ActionListener listener, ItemListener itemListener) {
        buttonNew.addActionListener(listener);
        buttonDplus.addActionListener(listener);
        buttonDmin.addActionListener(listener);
        buttonReibMin.addActionListener(listener);
        buttonReibPlus.addActionListener(listener);
        buttonGo.addActionListener(listener);
        Chooser.addItemListener(itemListener);
    }

    public void addPanel(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }*/
}

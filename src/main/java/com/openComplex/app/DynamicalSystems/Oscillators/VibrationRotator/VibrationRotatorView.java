package com.openComplex.app.DynamicalSystems.Oscillators.VibrationRotator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemListener;

/**
 *  on 07/10/15.
 */public class VibrationRotatorView {
    private static final int Lx = 300, Ly = 300; //graphic-window
    private JButton buttonReibPlus, buttonReibMin, buttonGo;
    private Choice Chooser;
    private JScrollBar speed;
    private JLabel descrip;
    private JFrame frame;

    public VibrationRotatorView(){
        init();
    }


    public void init() {
        frame = new JFrame("2 Oscillators With Coupling");
        frame.setSize(800, 500);
        frame.setLayout(new BorderLayout());

        JPanel pan = new JPanel();
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));
        buttonReibPlus = new JButton("Friction ++");
        buttonReibMin = new JButton("Friction --");
        buttonGo = new JButton("Stop/Go");

        buttonGo.setActionCommand("Go");
        buttonReibMin.setActionCommand("Friction --");
        buttonReibPlus.setActionCommand("Friction ++");
        Chooser = new Choice();
        Chooser.add("Values_1");
        Chooser.add("Values_2");
        Chooser.add("Values_3");


        pan.setBounds(Lx + 101, 1, 100, Ly);
        pan.setBackground(new Color(0, 200, 100));

        Chooser.setBounds(10, 20, 80, 30);
        buttonReibPlus.setBounds(10, 60, 80, 30);
        buttonReibMin.setBounds(10, 100, 80, 30);
        buttonGo.setBounds(10, 145, 80, 30);
        buttonGo.setBackground(new Color(0, 200, 100));

        speed = new JScrollBar(Scrollbar.VERTICAL, 40, 20, 0, 80);
        speed.setSize(15, 100);
        speed.setLocation(10, 190);



        descrip = new JLabel("Omega");
        descrip.setSize(50, 30);
        descrip.setLocation(35, 268);

        pan.add(speed);
        pan.add(descrip);
        pan.add(Chooser);
        pan.add(buttonReibPlus);
        pan.add(buttonReibMin);
        pan.add(buttonGo);
        frame.add(pan, BorderLayout.NORTH);
        frame.setVisible(true);



    }//init()

    public void addListener(ActionListener listener, ItemListener itemListener, AdjustmentListener adjustmentListener) {

        buttonReibMin.addActionListener(listener);
        buttonReibPlus.addActionListener(listener);
        buttonGo.addActionListener(listener);
        Chooser.addItemListener(itemListener);
        speed.addAdjustmentListener(adjustmentListener);
    }

    public void addPanel(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }

    public int getSpeed(){
        return speed.getValue();
    }
}

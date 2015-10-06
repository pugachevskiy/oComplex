package com.openComplex.app.DynamicalSystems.Physics.Oscillator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by strange on 06/10/15.
 */
public class OscillatorView {
    private JButton buttonGo, buttonDplus, buttonDmin;
    private JButton buttonReibPlus, buttonReibMin;
    private JFrame frame;
    public static final int W = 400, H = 220; //graphic window

    public OscillatorView(){
        init();
    }

    public void init() {

        frame = new JFrame("Oscillator");
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 400);

        JPanel pan = new JPanel();

        pan.setBounds(W + 1, 1, 100, H);
        pan.setBackground(new Color(0, 200, 100));
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));

        buttonDplus = new JButton("Spring +");
        buttonDmin = new JButton("Spring -");
        buttonReibPlus = new JButton("Friction ++");
        buttonReibMin = new JButton("Friction --");
        buttonGo = new JButton("Go/Pause");

        buttonGo.setActionCommand("Go");
        buttonReibMin.setActionCommand("Friction --");
        buttonReibPlus.setActionCommand("Friction ++");
        buttonDmin.setActionCommand("Spring -");
        buttonDplus.setActionCommand("Spring +");

        buttonDplus.setBounds(10, 25, 80, 25);
        buttonDmin.setBounds(10, 55, 80, 25);
        buttonReibPlus.setBounds(10, 95, 80, 25);
        buttonReibMin.setBounds(10, 125, 80, 25);
        buttonGo.setBounds(10, 170, 80, 25);


        pan.add(buttonGo);
        pan.add(buttonDplus);
        pan.add(buttonDmin);
        pan.add(buttonReibPlus);
        pan.add(buttonReibMin);
        frame.add(pan, BorderLayout.NORTH);

        frame.setVisible(true);
    }

    public void addPanel(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }

    public void addListener(ActionListener listener) {
        buttonDplus.addActionListener(listener);
        buttonDmin.addActionListener(listener);
        buttonReibPlus.addActionListener(listener);
        buttonReibMin.addActionListener(listener);
        buttonGo.addActionListener(listener);
    }
}

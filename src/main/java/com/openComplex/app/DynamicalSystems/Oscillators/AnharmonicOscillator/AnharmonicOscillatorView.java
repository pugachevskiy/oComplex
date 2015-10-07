package com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by strange on 07/10/15.
 */
public class AnharmonicOscillatorView {
    private static final int W = 400, H = 200; //graphic window
    private JButton buttonNew, buttonGo;
    private JButton buttonDplus, buttonDmin;
    private JButton buttonReibPlus, buttonReibMin;
    private JFrame frame;

    public AnharmonicOscillatorView() {
        init();
    }

    public void init() {

        frame = new JFrame("Anharmonic Oscillator");
        frame.setSize(700,400);
        frame.setLayout(new BorderLayout());
        JPanel pan = new JPanel();

        pan.setBounds(W + 1, 1, 100, H + 90);
        pan.setBackground(new Color(0, 200, 100));
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));

        buttonNew = new JButton("New");
        buttonGo = new JButton("Go/Pause");
        buttonDplus = new JButton("Spring +");
        buttonDmin = new JButton("Spring -");
        buttonReibPlus = new JButton("Friction ++");
        buttonReibMin = new JButton("Friction --");

        buttonNew.setActionCommand("New");
        buttonGo.setActionCommand("Go");
        buttonDplus.setActionCommand("Spring +");
        buttonDmin.setActionCommand("Spring -");
        buttonReibMin.setActionCommand("Friction --");
        buttonReibPlus.setActionCommand("Friction ++");


        buttonNew.setBounds(10, H / 2 - 78, 80, 27);
        buttonDplus.setBounds(10, H / 2 - 28, 80, 27);
        buttonDmin.setBounds(10, H / 2 + 7, 80, 27);
        buttonReibPlus.setBounds(10, H / 2 + 47, 80, 27);
        buttonReibMin.setBounds(10, H / 2 + 82, 80, 27);
        buttonGo.setBounds(10, H / 2 + 132, 80, 27);

        pan.add(buttonGo);
        pan.add(buttonNew);
        pan.add(buttonDplus);
        pan.add(buttonDmin);
        pan.add(buttonReibPlus);
        pan.add(buttonReibMin);
        frame.add(pan, BorderLayout.NORTH);
        frame.setVisible(true);


    }//init()

    public void addListener(ActionListener listener){
        buttonNew.addActionListener(listener);
        buttonGo.addActionListener(listener);
        buttonDplus.addActionListener(listener);
        buttonDmin.addActionListener(listener);
        buttonReibMin.addActionListener(listener);
        buttonReibPlus.addActionListener(listener);
    }

    public void addPanel(JPanel panel){
        frame.add(panel, BorderLayout.CENTER);
    }
}

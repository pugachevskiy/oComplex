package com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillator;

import com.openComplex.app.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *  on 07/10/15.
 */
public class AnharmonicOscillatorView extends JFrame{
    private static final int W = 400, H = 200; //graphic window
    private JButton buttonNew, buttonGo;
    private JButton buttonDplus, buttonDmin;
    private JButton buttonReibPlus, buttonReibMin;
    private JPanel setupPanel;

    public AnharmonicOscillatorView() {
        init();
    }

    public void init() {

        this.setTitle("Anharmonic Oscillator");
        this.setSize(700, 400);
        this.setLayout(new BorderLayout());
        setupPanel = new JPanel();

        setupPanel.setBounds(W + 1, 1, 100, H + 90);
        setupPanel.setBackground(new Color(0, 200, 100));
        setupPanel.setFont(new Font("Verdana", Font.PLAIN, 10));

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

        setupPanel.add(buttonGo);
        setupPanel.add(buttonNew);
        setupPanel.add(buttonDplus);
        setupPanel.add(buttonDmin);
        setupPanel.add(buttonReibPlus);
        setupPanel.add(buttonReibMin);
        this.add(setupPanel, BorderLayout.NORTH);
        App.setFrameCentral(this);
        this.setVisible(true);


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
        this.add(panel, BorderLayout.CENTER);
    }

    public int calculatePanelHeight() {
        return this.getHeight()-setupPanel.getHeight();
    }
}

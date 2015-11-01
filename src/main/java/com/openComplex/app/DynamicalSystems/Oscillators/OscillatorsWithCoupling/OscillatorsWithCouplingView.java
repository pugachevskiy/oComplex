package com.openComplex.app.DynamicalSystems.Oscillators.OscillatorsWithCoupling;

import com.openComplex.app.App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 *  on 07/10/15.
 */
public class OscillatorsWithCouplingView extends JFrame{
    private static final int W = 400, H = 200; //graphic window

    private JButton buttonNew, buttonGo;
    private JButton buttonDplus, buttonDmin;
    private JButton buttonReibPlus, buttonReibMin;
    private JPanel setupPanel;

    private Choice Chooser;

    public OscillatorsWithCouplingView() {
        init();
    }

    public void init() {
        this.setTitle("2 Oscillators With Coupling");
        this.setSize(800, 500);
        this.setLayout(new BorderLayout());
        setupPanel = new JPanel();
        setupPanel.setBounds(W + 1, 1, 100, H + 90);
        setupPanel.setBackground(new Color(0, 200, 100));
        setupPanel.setFont(new Font("Verdana", Font.PLAIN, 10));

        buttonNew = new JButton("New");
        buttonDplus = new JButton("Spring +");
        buttonDmin = new JButton("Spring -");
        buttonReibPlus = new JButton("Friction ++");
        buttonReibMin = new JButton("Friction --");
        buttonGo = new JButton("Go");

        buttonNew.setActionCommand("New");
        buttonDplus.setActionCommand("Spring +");
        buttonDmin.setActionCommand("Spring -");
        buttonReibMin.setActionCommand("Friction --");
        buttonReibPlus.setActionCommand("Friction ++");
        buttonGo.setActionCommand("Go");

        Chooser = new Choice();
        Chooser.add("Free");
        Chooser.add("Together");
        Chooser.add("Against");

        buttonNew.setBounds(10, 15, 80, 27);
        buttonDplus.setBounds(10, 55, 80, 27);
        buttonDmin.setBounds(10, 90, 80, 27);
        buttonReibPlus.setBounds(10, 130, 80, 27);
        buttonReibMin.setBounds(10, 165, 80, 27);
        Chooser.setBounds(10, 215, 80, 27);
        buttonGo.setBounds(10, 250, 70, 30);
        buttonGo.setBackground(new Color(0, 200, 100));

        setupPanel.add(buttonGo);
        setupPanel.add(Chooser);
        setupPanel.add(buttonNew);
        setupPanel.add(buttonDplus);
        setupPanel.add(buttonDmin);
        setupPanel.add(buttonReibPlus);
        setupPanel.add(buttonReibMin);
        this.add(setupPanel, BorderLayout.NORTH);
        App.setFrameCentral(this);
        this.setVisible(true);

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
        this.add(panel, BorderLayout.CENTER);
    }

    public int calculatePanelHeight() {
        return this.getHeight()-setupPanel.getHeight();
    }

}

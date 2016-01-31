package com.openComplex.app.DynamicalSystems.NBodySimulations.SunEarthSim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created on 07.10.2015.
 */
public class View extends JFrame {
    private static final int Lx = 500, Ly = 530; //graphic window
    public JFrame frame;
    public JPanel pan;
    private List<JButton> buttons = new LinkedList<>();
    private List<String> buttonsName = Arrays.asList("New", "Start", "vy_Earth +", "vy_Earth -");
    private Choice Chooser;
    private ItemListener itemListener;

    public View(ActionListener listener, ItemListener itemListener) {
        this.itemListener = itemListener;
        init("Sun-Earth", listener, buttonsName);
    }

    public void init(String name, ActionListener listener, List<String> list) {
        frame = new JFrame(name);
        frame.setSize(1100, 450);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        pan = new JPanel();
        pan.setBounds(Lx + 1, 1, 100, Ly + 90);
        pan.setBackground(new Color(0, 200, 100));
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));

        addButtons(listener, list);

        Chooser = new Choice();
        Chooser.add("Values_1");
        Chooser.add("Values_2");
        Chooser.add("Values_3");
        Chooser.add("Values_4");
        Chooser.addItemListener(itemListener);
        pan.add(Chooser);

        frame.add(pan, BorderLayout.NORTH);
        frame.setVisible(true);

    }//init()

    private void addButtons(ActionListener listener, List<String> buttonsName) {
        for (int i = 0; i < buttonsName.size(); i++) {
            this.buttons.add(new JButton(buttonsName.get(i)));
            this.buttons.get(i).setActionCommand(buttonsName.get(i));
            this.buttons.get(i).addActionListener(listener);
            pan.add(this.buttons.get(i));
        }
    }

    public void addPanel(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }
}

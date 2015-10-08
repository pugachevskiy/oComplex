package com.openComplex.app.DynamicalSystems.Pendulums.DoublePendulum;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by strange on 08/10/15.
 */
public class DoublePendulumView {
    private static final int Lx = 500, Ly = 330; //graphic window
    private JPanel pan;

    private List<String> buttonsName = Arrays.asList("New", "Start", "Phi1 +", "Phi1 -", "Phi2 +", "Phi2 -", "Friction ++",
            "Friction --", "m1 +", "m1 -", "m2 +", "m2 -");
    private List<JButton> buttons = new LinkedList<>();
    private ActionListener listener;
    private JFrame frame;

    public DoublePendulumView(ActionListener listener) {
        this.listener = listener;
        init();
    }

    public void init() {

        frame = new JFrame("Double Pendulum");
        frame.setSize(1100, 450);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        pan = new JPanel();
        pan.setBounds(Lx + 1, 1, 100, Ly + 90);
        pan.setBackground(new Color(0, 200, 100));
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));

        addButtons();
        frame.add(pan, BorderLayout.NORTH);
        frame.setVisible(true);

    }//init()

    private void addButtons() {
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

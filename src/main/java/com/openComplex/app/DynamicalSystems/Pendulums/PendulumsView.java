package com.openComplex.app.DynamicalSystems.Pendulums;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 *  on 09/10/15.
 */
public abstract class PendulumsView {
    private static final int Lx = 500, Ly = 530; //graphic window
    public JFrame frame;
    public JPanel pan;
    private List<JButton> buttons = new LinkedList<>();

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

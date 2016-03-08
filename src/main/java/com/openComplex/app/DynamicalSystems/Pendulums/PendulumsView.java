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

    JFormattedTextField limitField;

    String[] trajectories = {"Trajectory 1", "Trajectory 2", "Trajectory 3", "Trajectory 4"};

    public void init(String name, ActionListener listener, List<String> list) {

        frame = new JFrame(name);
        frame.setSize(1300, 450);
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

    public void buildTrajectorySettings(int numberOfTrajectories, int limit, ActionListener listener) {
        JLabel[] TrajectoriesLabels = new JLabel[numberOfTrajectories];
        JButton[] TrajectoriesColorButton = new JButton[numberOfTrajectories];
        JFrame ts = new JFrame("Trajectory Settings");

        ts.setLayout(new GridLayout(numberOfTrajectories + 1, 2));
        ts.setSize(100 + 50 * numberOfTrajectories, 150);
        ts.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        for(int i = 0; i <= numberOfTrajectories; i++) {
            if(i == 0) {
                JLabel limitLabel = new JLabel("Limit");
                limitField = new JFormattedTextField(Integer.toString(limit));
                limitField.addActionListener(listener);
                limitField.setActionCommand("Limit");
                ts.add(limitLabel);
                ts.add(limitField);

            } else {
                TrajectoriesLabels[i-1] = new JLabel(trajectories[i-1]);
                TrajectoriesColorButton[i-i] = new JButton("Color");
                TrajectoriesColorButton[i-i].addActionListener(listener);
                TrajectoriesColorButton[i-i].setActionCommand(trajectories[i - 1]);
                ts.add(TrajectoriesLabels[i - 1]);
                ts.add(TrajectoriesColorButton[i-i]);
            }
        }
        ts.setResizable(false);
        ts.setVisible(true);
    }

    public int getLimit() {
        int i = 0;
        try {
            i = Integer.parseInt(limitField.getText());
            if(i < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            i = 1000;

        }
        limitField.setText(Integer.toString(i));
        return i;
    }

    public void addPanel(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }
}

package com.openComplex.app.CellularAutomat.GameOfLife2.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by strange on 10/09/15.
 */
public class View {
    private JFrame mainFrame;
    private JButton stopButton, startButton, nextButton, endButton;
    private JComboBox<String> anfangsBedingungBox, cellFormBox, cellGroeßeBox, cellFarbeBox, geschwindigkeitBox;
    public static final String[] ANFANGSBEDINGUNGFILL = {"Pigeon", "Figure 1", "Gliter", "blank"}, CELLFORMFILL = {"Square"},//, "Hexagon" },
            CELLGROESSEFILL = {"Small", "Medium", "Large"}, CELLFARBEFILL = {"Black", "Blue", "Green", "Yellow"},
            GESCHWINDIGKEITFILL = {"Slow", "Normal", "Fast"};
    private JLabel counter;

    public void init() {
        mainFrame = new JFrame("Conways Game of Life");

        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(600, 600));

        //mainFrame.setJMenuBar(addMenu());

        JPanel menuPanel;
        mainFrame.add(menuPanel = new JPanel(), BorderLayout.WEST);
        addComponentsToPane(menuPanel);
        mainFrame.requestFocus();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    private void addComponentsToPane(Container pane) {
        pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        addControl(pane, c);
    }

    private void addControl(Container pane, GridBagConstraints c) {
        JLabel anfangsLabel = new JLabel("Initial model");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        pane.add(anfangsLabel, c);
        anfangsBedingungBox = new JComboBox<>(ANFANGSBEDINGUNGFILL);
        anfangsBedingungBox.setEnabled(true);
        anfangsBedingungBox.setActionCommand("Init state");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        pane.add(anfangsBedingungBox, c);


        JLabel cellFormLabel = new JLabel("Cell Form");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 1;
        pane.add(cellFormLabel, c);
        cellFormBox = new JComboBox<>(CELLFORMFILL);
        cellFormBox.setEnabled(true);
        cellFormBox.setActionCommand("Cell form");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 1;
        pane.add(cellFormBox, c);


        JLabel cellGroeßeLabel = new JLabel("Size of cells");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 1;
        pane.add(cellGroeßeLabel, c);
        cellGroeßeBox = new JComboBox<>(CELLGROESSEFILL);
        cellGroeßeBox.setEnabled(true);
        cellGroeßeBox.setActionCommand("Cell size");
        cellGroeßeBox.setSelectedIndex(1);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 1;
        pane.add(cellGroeßeBox, c);


        JLabel cellFarbeLabel = new JLabel("Color of cells");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 1;
        pane.add(cellFarbeLabel, c);


        cellFarbeBox = new JComboBox<>(CELLFARBEFILL);
        cellFarbeBox.setActionCommand("Cell color");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 8;
        c.gridwidth = 1;
        pane.add(cellFarbeBox, c);


        JLabel geschwindigkeitLabel = new JLabel("Speed");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 1;
        pane.add(geschwindigkeitLabel, c);


        geschwindigkeitBox = new JComboBox<>(GESCHWINDIGKEITFILL);
        geschwindigkeitBox.setSelectedIndex(1);
        geschwindigkeitBox.setActionCommand("Speed");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 10;
        c.gridwidth = 1;
        pane.add(geschwindigkeitBox, c);


        startButton = new JButton("Start");
        startButton.setEnabled(true);
        startButton.setActionCommand("Start");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 11;
        c.gridwidth = 1;
        pane.add(startButton, c);


        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.setActionCommand("Stop");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 12;
        c.gridwidth = 1;
        pane.add(stopButton, c);


        nextButton = new JButton("Next");
        nextButton.setEnabled(true);
        nextButton.setActionCommand("Next");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 13;
        c.gridwidth = 1;
        pane.add(nextButton, c);


        endButton = new JButton("Exit");
        endButton.setActionCommand("Exit");
        endButton.setEnabled(true);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 14;
        c.gridwidth = 1;
        pane.add(endButton, c);

        JLabel label = new JLabel("Step");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 15;
        c.gridwidth = 1;
        pane.add(label, c);
        counter = new JLabel("0");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 16;
        c.gridwidth = 1;
        pane.add(counter, c);


    }

    // On/Off buttons on Press start/stop button
    public void deactivateButtons() {
        stopButton.setEnabled(true);
        startButton.setEnabled(false);
        nextButton.setEnabled(false);
        anfangsBedingungBox.setEnabled(false);
        cellFormBox.setEnabled(false);
        cellGroeßeBox.setEnabled(false);
    }

    public void activateButtons() {
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        nextButton.setEnabled(true);
        anfangsBedingungBox.setEnabled(true);
        cellFormBox.setEnabled(true);
        cellGroeßeBox.setEnabled(true);
    }

    // add Field Jpanel to JFrame
    public void addField(JPanel panel) {
        mainFrame.add(panel, BorderLayout.CENTER);
    }

    public void deleteField(JPanel panel) {
        mainFrame.remove(panel);
    }

    public void addListener(ActionListener listener) {
        startButton.addActionListener(listener);
        stopButton.addActionListener(listener);
        endButton.addActionListener(listener);
        nextButton.addActionListener(listener);
        cellFarbeBox.addActionListener(listener);
        cellFormBox.addActionListener(listener);
        cellGroeßeBox.addActionListener(listener);
        anfangsBedingungBox.addActionListener(listener);
        geschwindigkeitBox.addActionListener(listener);
    }

    public void frameClose() {
        mainFrame.dispose();
    }

    public void setCounter(String text) {
        this.counter.setText(text);
    }

    public int getFigureBox() {
        return anfangsBedingungBox.getSelectedIndex();
    }

    public int getColorBox() {
        return cellFarbeBox.getSelectedIndex();
    }

    public int getSpeedBox() {
        return geschwindigkeitBox.getSelectedIndex();
    }

    public int getSizeBox() {
        return cellGroeßeBox.getSelectedIndex();
    }
}


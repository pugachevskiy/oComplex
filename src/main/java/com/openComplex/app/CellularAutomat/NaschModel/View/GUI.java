package com.openComplex.app.CellularAutomat.NaschModel.View;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Hashtable;

public class GUI {
    private JFrame frame;
    private JButton stateButton;
    private JSlider percentSlider;
    private JComboBox <String> systemSpeedBox, randomBox, maxSpeedBox, optionBox,
                                accDistanceBox, decDistanceBox, decBox, accBox;


    private String[] systemSpeed = {"slow", "medium", "fast"}, speed = {"1", "2", "3", "4","5", "6", "7", "8", "9", "10"},
            random = {"0.0", "0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9", "1.0"},
            option = {"default rules","define own rules"},
            distance = {"distance to next cell", "distance to next cell + 1", "distance to next cell + 2", "distance to next cell + 3"},
            accDec = {"1", "2", "3"};

    public GUI() {
        frame = new JFrame("Nash-Model");
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setLayout(new BorderLayout());
        frame.add(controlPanel(), BorderLayout.NORTH);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel controlPanel () {
        JPanel control = new JPanel();
        control.setLayout(new GridLayout(5, 4));

        randomBox = new JComboBox<>(random);
        maxSpeedBox = new JComboBox<>(speed);
        systemSpeedBox = new JComboBox<>(systemSpeed);
        optionBox = new JComboBox<>(option);

        accDistanceBox = new JComboBox<>(distance);
        decDistanceBox = new JComboBox<>(distance);
        accBox = new JComboBox<>(accDec);
        decBox = new JComboBox<>(accDec);

        randomBox.setActionCommand("random");
        maxSpeedBox.setActionCommand("maxSpeed");
        systemSpeedBox.setActionCommand("systemSpeed");
        optionBox.setActionCommand("options");

        accDistanceBox.setActionCommand("accDistance");
        decDistanceBox.setActionCommand("decDistance");
        accBox.setActionCommand("acc");
        decBox.setActionCommand("dec");

        randomBox.setSelectedIndex(1);
        maxSpeedBox.setSelectedIndex(4);
        systemSpeedBox.setSelectedIndex(1);
        optionBox.setSelectedIndex(0);

        stateButton = new JButton("Start");
        stateButton.setActionCommand("start");

        createPercentSlider();

        control.add(new JLabel("Starting condition:"));
        control.add(percentSlider);
        control.add(new JLabel("System speed:"));
        control.add(systemSpeedBox);

        control.add(new JLabel("Maximum speed:"));
        control.add(maxSpeedBox);
        control.add(new JLabel("Randomisation:"));
        control.add(randomBox);

        control.add(new JLabel("Options: "));
        control.add(optionBox);
        control.add(new JLabel(" "));
        control.add(stateButton);




        control.add(new JLabel("Acceleration:"));
        control.add(accBox);
        control.add(new JLabel("Acceleration distance:"));
        control.add(accDistanceBox);

        control.add(new JLabel("Deceleration: "));
        control.add(decBox);
        control.add(new JLabel("Deceleration distance:"));
        control.add(decDistanceBox);

        enableRuleField(false);

        return control;
    }

    private JPanel systemOptionPanel() {
        JPanel systemPanel = new JPanel();

        return systemPanel;
    }

    public void addActionListener(ActionListener al) {
        stateButton.addActionListener(al);
        randomBox.addActionListener(al);
        maxSpeedBox.addActionListener(al);
        systemSpeedBox.addActionListener(al);
        optionBox.addActionListener(al);
        accBox.addActionListener(al);
        decBox.addActionListener(al);
        accDistanceBox.addActionListener(al);
        decDistanceBox.addActionListener(al);
    }

    public void addChangeListener(ChangeListener cl) {
        percentSlider.addChangeListener(cl);
    }

    public void addField(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }

    public int getMaxSpeedBox() {
        return maxSpeedBox.getSelectedIndex();
    }
    public int getSystemSpeedBox() {
        return systemSpeedBox.getSelectedIndex();
    }

    public int getRandomBox() {
        return randomBox.getSelectedIndex();
    }

    public int getOptionsBox() {
        return optionBox.getSelectedIndex();
    }

    public int getAccBox() {
        return accBox.getSelectedIndex();
    }

    public int getAccDistanceBox() {
        return accDistanceBox.getSelectedIndex();
    }

    public int getDecBox() {
        return decBox.getSelectedIndex();
    }

    public int getDecDistanceBox(){
        return decDistanceBox.getSelectedIndex();
    }

    public void changeStateButtonTo(String state) {
        if(state.equals("start")) {
            stateButton.setText("Start");
            stateButton.setActionCommand("start");
        } else if(state.equals("stop")) {
            stateButton.setText("Stop");
            stateButton.setActionCommand("stop");
        }
    }

    public void enableControlField (boolean state) {
        percentSlider.setEnabled(state);
        maxSpeedBox.setEnabled(state);
        randomBox.setEnabled(state);
        systemSpeedBox.setEnabled(state);
        optionBox.setEnabled(state);
    }

    public void enableRuleField(boolean state) {
        accBox.setEnabled(state);
        accDistanceBox.setEnabled(state);
        decBox.setEnabled(state);
        decDistanceBox.setEnabled(state);
    }

    private void createPercentSlider() {
        percentSlider = new JSlider(0, 100, 20);
        percentSlider.setPaintTicks(true);
        percentSlider.setMajorTickSpacing(50);
        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(20, new JLabel("20"));
        labelTable.put(40, new JLabel("40"));
        labelTable.put(60, new JLabel("60"));
        labelTable.put(80, new JLabel("80"));
        labelTable.put(100, new JLabel("100"));
        percentSlider.setLabelTable(labelTable);
        percentSlider.setPaintLabels(true);
        percentSlider.setValue(0);
    }

}

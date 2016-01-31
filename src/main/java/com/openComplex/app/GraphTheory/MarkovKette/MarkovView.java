package com.openComplex.app.GraphTheory.MarkovKette;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * on 27/08/15.
 */
public class MarkovView {
    private JFrame frame;
    private JPanel panel, tablePanel, downPanel;
    private int length, start;
    private JButton solveButton, newTableButton, setDefaultButton;
    private JLabel step;
    private JTextArea stateTextField;
    private List<JTextField> transition = new ArrayList<>();
    private JTextField lengthTextField, startPositionTextField;

    public MarkovView(int size, int start) {
        this.length = size;
        this.start = start;
        init();
        addTopPanel();
        addBottomPanel();
        frame.pack();
    }

    public void init() {
        frame = new JFrame("Markov Kette");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(800, 800);
        tablePanel = new JPanel();
        panel = new JPanel();
        downPanel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(downPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addTopPanel() {
        newTableButton = new JButton("New");
        newTableButton.setActionCommand("New");
        setDefaultButton = new JButton("Default");
        setDefaultButton.setActionCommand("Default");
        solveButton = new JButton("Solve");
        solveButton.setActionCommand("Solve");
        panel.add(newTableButton);
        panel.add(setDefaultButton);
        panel.add(solveButton);
        JLabel lengthLabel = new JLabel("Length");
        panel.add(lengthLabel);
        lengthTextField = new JTextField(String.valueOf(length));
        lengthTextField.setPreferredSize(new Dimension(40, 30));
        lengthTextField.setActionCommand("length");
        panel.add(lengthTextField);
        JLabel startPositionLabel = new JLabel("Start position");
        panel.add(startPositionLabel);
        startPositionTextField = new JTextField(String.valueOf(start));
        startPositionTextField.setPreferredSize(new Dimension(40, 30));
        panel.add(startPositionTextField);
    }

    public void addTable(int length, double[][] transitionArray) {
        tablePanel.setLayout(new GridLayout(length, length));
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                transition.add(new JTextField(String.valueOf(transitionArray[i][j])));
            }
        }
        for (JTextField aTransition : transition) {
            tablePanel.add(aTransition);
        }
        tablePanel.updateUI();
        frame.pack();
    }

    private void addBottomPanel() {
        JLabel stepLabel = new JLabel("The number of steps = ");
        step = new JLabel("");
        downPanel.setLayout(new BorderLayout());
        downPanel.add(stepLabel, BorderLayout.CENTER);
        downPanel.add(step, BorderLayout.EAST);
        stateTextField = new JTextArea("");
        stateTextField.setLineWrap(true);
        stateTextField.setMinimumSize(new Dimension(400, 400));
        downPanel.add(stateTextField, BorderLayout.SOUTH);
    }

    public void clearPanel() {
        tablePanel.removeAll();
        transition.removeAll(transition);
        stateTextField.setText("");
    }

    public void setStep(String text) {
        step.setText(text);
    }

    public void setStateTextField(String step) {
        this.stateTextField.setText(step);
        downPanel.revalidate();
        frame.pack();
    }

    public int getLength() {
        return Integer.valueOf(lengthTextField.getText());
    }

    public int getStart() {
        return Integer.valueOf(startPositionTextField.getText());
    }

    public double[][] getTable(int length) {
        double[][] matrix = new double[length][length];
        int counter = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {

                matrix[i][j] = Double.valueOf(transition.get(counter).getText());
                counter++;
            }
        }
        return matrix;
    }

    public void setLength(int length) {
        this.length = length;
        lengthTextField.setText(String.valueOf(length));
        lengthTextField.updateUI();
    }

    public void setStart(int start) {
        this.start = start;
        startPositionTextField.setText(String.valueOf(start));
        startPositionTextField.updateUI();
    }

    public void addListener(ActionListener listener) {
        solveButton.addActionListener(listener);
        newTableButton.addActionListener(listener);
        setDefaultButton.addActionListener(listener);
        lengthTextField.addActionListener(listener);
    }
}
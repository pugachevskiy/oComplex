package com.openComplex.app.GraphTheory.MarkovKette;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by strange on 27/08/15.
 */
public class MarkovView {
    private JFrame frame;
    private JPanel panel,tablePanel, downPanel ;
    private int length,start;
    private JButton solve,newTable, setDefault;
    private JLabel stepLabel,step;
    private JTextArea stateTextField;
    private List<JTextField> transition = new ArrayList<>();
    private JTextField lengthTextField, startPositionTextField;

    public MarkovView(int size, double[][] transitionArray, int start){
        this.length = size;
        this.start = start;
        init();
        addTopPanel();
        addTable(size, transitionArray);
        addBottomPanel();
        frame.pack();
    }

    public void init(){
        frame = new JFrame("Markov Kette");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(800,800);
        tablePanel = new JPanel();
        panel = new JPanel();
        downPanel = new JPanel();
        frame.add(panel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(downPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void addTopPanel(){
        newTable = new JButton("New");
        newTable.setActionCommand("New");
        setDefault = new JButton("Default");
        setDefault.setActionCommand("Default");
        solve = new JButton("Solve");
        solve.setActionCommand("Solve");
        panel.add(newTable);
        panel.add(setDefault);
        panel.add(solve);
        JLabel lengthLabel = new JLabel("Length");
        panel.add(lengthLabel);
        lengthTextField = new JTextField(String.valueOf(length));
        lengthTextField.setActionCommand("length");
        panel.add(lengthTextField);
        JLabel startPositionLabel = new JLabel("Start position");
        panel.add(startPositionLabel);
        startPositionTextField = new JTextField(String.valueOf(length - start));
        panel.add(startPositionTextField);
    }

    public void addTable(int length, double[][] transitionArray){
        tablePanel.setLayout(new GridLayout(length,length));
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                transition.add(new JTextField(String.valueOf(transitionArray[i][j])));
            }
        }

        for (int i = 0; i < transition.size(); i++){
                tablePanel.add(transition.get(i));
            }
        tablePanel.updateUI();
        frame.pack();
    }


    private void addBottomPanel(){
        stepLabel = new JLabel("The number of steps = ");
        step = new JLabel("");
        downPanel.setLayout(new BorderLayout());
        downPanel.add(stepLabel, BorderLayout.CENTER);
        downPanel.add(step, BorderLayout.EAST);
        stateTextField = new JTextArea("");
        stateTextField.setLineWrap(true);
        stateTextField.setMinimumSize(new Dimension(400,400));
        downPanel.add(stateTextField,BorderLayout.SOUTH);
    }

    public JButton getSolve(){
        return solve;
    }

    public JButton getNewTable() {return newTable;}

    public JButton getSetDefault(){return setDefault;}

    public void clearPanel(){
        tablePanel.removeAll();
        transition.removeAll(transition);
        stateTextField.setText("");
    }

    public void setStep(String text){
        step.setText(text);
    }

    public JTextField getLengthTextField(){
        return lengthTextField;
    }

    public void setStateTextField(String step){
        this.stateTextField.setText(step);
        downPanel.revalidate();
        frame.pack();
    }

    public int getLength(){
        return Integer.valueOf(lengthTextField.getText());
    }

    public int getStart(){
        return Integer.valueOf(startPositionTextField.getText());
    }

    public double[][] getTable(int length){
        double[][] matrix = new double[length][length];
        int counter = 0;
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++) {

                matrix[i][j] = Double.valueOf(transition.get(counter).getText());
                counter++;
            }
        }
        return matrix;
    }

    public void setLength(int length){
        this.length = length;
        lengthTextField.setText(String.valueOf(length));
        lengthTextField.updateUI();
    }

    public void setStart(int start){
        this.start = start;
        startPositionTextField.setText(String.valueOf(start));
        startPositionTextField.updateUI();
    }
}

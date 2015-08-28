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
    private JButton solve;
    private JLabel stepLabel,step;
    private JTextArea stateTextField;
    private List<JTextField> transition = new ArrayList<>();
    private JTextField lengthTextField, startPositionTextField;
    double[][] transitionArray;
    public MarkovView(int size, double[][] transitionArray, int start){
        this.length = size;
        this.transitionArray = transitionArray.clone();
        this.start = start;
    }

    public void init(){
        frame = new JFrame("Markov Kette");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(800,800);
        panel = new JPanel();
        solve = new JButton("Solve");
        panel.add(solve);
        tablePanel = new JPanel();
        addLength();
        addTable(length);
        stepLabel = new JLabel("The number of steps = ");
        step = new JLabel("");

        downPanel = new JPanel();
        downPanel.add(stepLabel);
        downPanel.add(step);
        stateTextField = new JTextArea("");
        //stateTextField.setPreferredSize(new Dimension(300, 30));
        stateTextField.setLineWrap(true);
        downPanel.add(stateTextField);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.add(downPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
    private void addLength(){
        JLabel lengthLabel = new JLabel("Length");
        panel.add(lengthLabel);
        lengthTextField = new JTextField(String.valueOf(length));
        panel.add(lengthTextField);
        JLabel startPositionLabel = new JLabel("Start position");
        panel.add(startPositionLabel);
        startPositionTextField = new JTextField(String.valueOf(length - start));
        panel.add(startPositionTextField);
    }

    public void addTable(int length){
        tablePanel.setLayout(new GridLayout(length,length));
        for (int i = 0; i < length; i++){
            for (int j = 0; j < length; j++){
                transition.add(new JTextField(String.valueOf(transitionArray[i][j])));

            }
        }
        for (int i = 0; i < transition.size(); i++){
                tablePanel.add(transition.get(i));
            }

    }
    public JButton getSolve(){
        return solve;
    }
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
    public JTextField getStartPositionTextField(){
        return startPositionTextField;
    }
    public void setStateTextField(String step){
        System.out.println(stateTextField.getText());
        this.stateTextField.setText(stateTextField.getText() + "->" + step);
        downPanel.revalidate();
        frame.pack();
    }
}

package com.openComplex.app.CellularAutomat.WolframsUniverse;


import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Hashtable;

/**
 * Created by Matthias on 16.06.2015.
 */
public class GUIWolfram {

    private JFormattedTextField ruleField;
    private JSlider generationSlider;
    private JButton ruleButton;
    private JPanel field;
    private JFrame frame;

    public GUIWolfram() {
        frame = new JFrame("Wolfram Universe");
        frame.setMinimumSize(new Dimension(800, 800));
        field = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.add(controlPanel(), BorderLayout.NORTH);
        createField(33,66,25);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setVisible(true);
    }
    private JPanel controlPanel () {
        JPanel control = new JPanel();
        control.setLayout(new FlowLayout());

        JLabel ruleLabel = new JLabel("Rule: ");

        ruleLabel.setFont(ruleLabel.getFont().deriveFont(15.0f));
        control.add(ruleLabel);

        createRuleField();
        control.add(ruleField);

        createRuleButton();
        control.add(ruleButton);

        control.add(new JLabel("   "));

        JLabel generationLabel = new JLabel("Generation: ");
        generationLabel.setFont(generationLabel.getFont().deriveFont(15.0f));
        control.add(generationLabel);

        createSlider();
        control.add(generationSlider);
        return control;
    }
    private void createRuleField(){
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        ruleField = new JFormattedTextField(formatter);
        ruleField.setFont(ruleField.getFont().deriveFont(15.0f));
        ruleField.setText("110");
    }
    private void createRuleButton(){
        ruleButton = new JButton("Enter");
        ruleButton.setFont(ruleButton.getFont().deriveFont(15.0f));
        ruleButton.setActionCommand("enter");
    }
    public void createField(int rows, int cols, int size){
        field.setLayout(new GridLayout(rows, cols));
        field.setPreferredSize(new Dimension(rows*size, cols*size));
        frame.add(field, BorderLayout.CENTER);

    }

    private void createSlider(){
        generationSlider = new JSlider(1, 100, 50);
        generationSlider.setPaintTicks(true);
        generationSlider.setMajorTickSpacing(10);
        Hashtable labelTable = new Hashtable();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(100, new JLabel("100"));
        generationSlider.setLabelTable(labelTable);
        generationSlider.setPaintLabels(true);
        generationSlider.setValue(0);

    }
    public JSlider getGenerationSlider(){return generationSlider;}
    public String getRuleFieldText(){
        return ruleField.getText();
    }
    public JButton getRuleButton(){
        return ruleButton;
    }
    public JPanel getField(){return field;}
}

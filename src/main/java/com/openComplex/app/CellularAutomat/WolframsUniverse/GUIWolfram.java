package com.openComplex.app.CellularAutomat.WolframsUniverse;


import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
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
        frame.setMinimumSize(new Dimension(1000, 1000));
        field = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.add(controlPanel(), BorderLayout.NORTH);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        createField(1, 1, 2);
        frame.setVisible(true);
    }

    private JPanel controlPanel() {
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

    private void createRuleField() {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        ruleField = new JFormattedTextField(formatter);
        ruleField.setFont(ruleField.getFont().deriveFont(15.0f));
        ruleField.setText("110");
    }

    private void createRuleButton() {
        ruleButton = new JButton("Enter");
        ruleButton.setFont(ruleButton.getFont().deriveFont(15.0f));
        ruleButton.setActionCommand("enter");
    }

    public void createField(int rows, int cols, int size) {
        field.setLayout(new GridLayout(rows, cols));
        field.setBounds(0, 0, size, size);
        field.setMinimumSize(new Dimension(rows * size, cols * size));
        field.repaint();
        frame.add(field, BorderLayout.CENTER);
        frame.revalidate();

    }

    public void addField(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }

    private void createSlider() {
        generationSlider = new JSlider(1, 200, 50);
        generationSlider.setPaintTicks(true);
        generationSlider.setMajorTickSpacing(50);
        Hashtable labelTable = new Hashtable();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(50, new JLabel("50"));
        labelTable.put(100, new JLabel("100"));
        labelTable.put(150, new JLabel("150"));
        labelTable.put(200, new JLabel("200"));
        generationSlider.setLabelTable(labelTable);
        generationSlider.setPaintLabels(true);
        generationSlider.setValue(0);

    }

    public String getRuleFieldText() {
        return ruleField.getText();
    }

    public void addActionListener(ActionListener listener) {
        ruleButton.addActionListener(listener);
    }

    public void addChangeListener(ChangeListener listener) {
        generationSlider.addChangeListener(listener);
    }
}

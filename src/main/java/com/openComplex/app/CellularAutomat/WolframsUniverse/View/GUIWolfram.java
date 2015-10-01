package com.openComplex.app.CellularAutomat.WolframsUniverse.View;


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
    private JButton ruleButton, clearButton,startButton, stopButton;
    private JPanel field;
    private JFrame frame;
    private JMenuItem saveItem, exitItem, ruleItem, loadItem;
    public GUIWolfram() {
        frame = new JFrame("Wolfram Universe");
        frame.setMinimumSize(new Dimension(1000, 1000));
        field = new JPanel();
        frame.setLayout(new BorderLayout());
        frame.add(controlPanel(), BorderLayout.NORTH);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setJMenuBar(addMenu());
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
        control.add(clearButton);
        control.add(startButton);

        control.add(new JLabel("   "));

        JLabel generationLabel = new JLabel("Generation: ");
        generationLabel.setFont(generationLabel.getFont().deriveFont(15.0f));
        control.add(generationLabel);

        createSlider();
        control.add(generationSlider);
        return control;
    }

    private JMenuBar addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu firstMenu = new JMenu("Data");
        saveItem = new JMenuItem("Save");
        saveItem.setActionCommand("Save");
        loadItem = new JMenuItem("Load");
        loadItem.setActionCommand("Load");
        exitItem = new JMenuItem("Exit");
        exitItem.setActionCommand("Exit");
        firstMenu.add(saveItem);
        firstMenu.add(loadItem);
        firstMenu.addSeparator();
        firstMenu.add(exitItem);

        JMenu secondMenu = new JMenu("Help");
        ruleItem = new JMenuItem("Rule");
        ruleItem.setActionCommand("Rule");
        secondMenu.add(ruleItem);

        menuBar.add(firstMenu);
        menuBar.add(secondMenu);
        return menuBar;
    }

    private void createRuleField() {
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);
        ruleField = new JFormattedTextField(formatter);
        ruleField.setFont(ruleField.getFont().deriveFont(16.0f));
        ruleField.setText("110");
        ruleField.setPreferredSize(new Dimension(100, 30));
    }

    private void createRuleButton() {
        ruleButton = new JButton("Enter");
        ruleButton.setFont(ruleButton.getFont().deriveFont(16.0f));
        ruleButton.setActionCommand("Enter");
        clearButton = new JButton("Clear");
        clearButton.setFont(clearButton.getFont().deriveFont(16.0f));
        clearButton.setActionCommand("Clear");
        startButton= new JButton("Start");
        startButton.setFont(startButton.getFont().deriveFont(16.0f));
        startButton.setActionCommand("Start");
        startButton.setPreferredSize(new Dimension(150, 30));

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
        generationSlider = new JSlider(1, 500, 100);
        generationSlider.setPaintTicks(true);
        generationSlider.setMajorTickSpacing(50);
        Hashtable labelTable = new Hashtable();
        labelTable.put(1, new JLabel("1"));
        labelTable.put(100, new JLabel("100"));
        labelTable.put(200, new JLabel("200"));
        labelTable.put(300, new JLabel("300"));
        labelTable.put(400, new JLabel("400"));
        labelTable.put(500, new JLabel("500"));
        generationSlider.setLabelTable(labelTable);
        generationSlider.setPaintLabels(true);
        generationSlider.setValue(0);

    }

    public String getRuleFieldText() {
        return ruleField.getText();
    }

    public void addActionListener(ActionListener listener) {
        ruleButton.addActionListener(listener);
        clearButton.addActionListener(listener);
        startButton.addActionListener(listener);
        saveItem.addActionListener(listener);
        exitItem.addActionListener(listener);
        ruleItem.addActionListener(listener);
        loadItem.addActionListener(listener);
    }

    public void addChangeListener(ChangeListener listener) {
        generationSlider.addChangeListener(listener);
    }

    public void setGenerationSlider(int value){
        generationSlider.setValue(value);
    }

    public void setStartButtonText(String text){
        startButton.setText(text);
        startButton.setActionCommand(text);
    }
}

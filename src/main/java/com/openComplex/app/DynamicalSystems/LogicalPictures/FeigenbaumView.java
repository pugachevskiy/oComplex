package com.openComplex.app.DynamicalSystems.LogicalPictures;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 01/09/15.
 */
public class FeigenbaumView {
    private JFrame frame;
    private JPanel panelMenu;
    private JButton buttonStart, buttonClear;
    private JLabel labelRRange, labelXRange;
    private JTextField textFieldrmin = new JTextField(), textFieldxmin = new JTextField(), textFieldrmax = new JTextField(), textFieldxmax = new JTextField();
    private List<JTextField> textFieldList = Arrays.asList(textFieldxmin,textFieldxmax,textFieldrmin,textFieldrmax);


    public FeigenbaumView() {
        frame = new JFrame("Feigenbaum");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(600, 450);
        panelMenu = new JPanel();
        frame.add(panelMenu, BorderLayout.NORTH);
        init();
        frame.setVisible(true);

    }

    public void init() {
        buttonStart = new JButton("Start");
        buttonStart.setActionCommand("Start");

        buttonClear = new JButton("Clear");
        buttonClear.setActionCommand("Clear");

        labelRRange = new JLabel(" < r < ");
        labelXRange = new JLabel("< x <");

        textFieldrmin.setText("0");
        textFieldxmin.setText("0");
        textFieldrmax.setText("4");
        textFieldxmax.setText("1");

        panelMenu.add(buttonStart);
        panelMenu.add(buttonClear);
        panelMenu.add(textFieldrmin);
        panelMenu.add(labelRRange);
        panelMenu.add(textFieldrmax);
        panelMenu.add(textFieldxmin);
        panelMenu.add(labelXRange);
        panelMenu.add(textFieldxmax);
        panelMenu.revalidate();
    }

    public JButton getButtonStart() {
        return buttonStart;
    }

    public JButton getButtonClear() {
        return buttonClear;
    }

    public List<JTextField> getTextFieldList() {
        return textFieldList;
    }

    public void addPanel(JPanel panel){
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
    }

}
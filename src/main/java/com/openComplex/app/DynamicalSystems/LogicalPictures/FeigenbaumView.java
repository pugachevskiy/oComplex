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
    private JPanel panelMenu, panelField;
    private JButton buttonStart, buttonClear, buttonMore;
    private JLabel labelRRange, labelXRange;
    private JCheckBox checkboxColor;
    private JTextField textFieldrmin = new JTextField(), textFieldxmin = new JTextField(), textFieldrmax = new JTextField(), textFieldxmax = new JTextField();
    private List<JTextField> textFieldList = Arrays.asList(textFieldxmin,textFieldxmax,textFieldrmin,textFieldrmax);


    public FeigenbaumView() {
        frame = new JFrame("Feigenbaum");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(600, 450);
        panelMenu = new JPanel();
        //panelField = new JPanel();
        //panelField.setSize(500,300);
        //panelField.setBounds(0,0,500,300);

        frame.add(panelMenu, BorderLayout.NORTH);
//        frame.add(panelField, BorderLayout.CENTER);
        init();
        frame.setVisible(true);

    }

    public void init() {
        buttonStart = new JButton("Start");
        buttonStart.setActionCommand("Start");

        buttonClear = new JButton("Clear");
        buttonClear.setActionCommand("Clear");

        buttonMore = new JButton("More");
        buttonMore.setActionCommand("More");

        labelRRange = new JLabel(" < r < ");
        labelXRange = new JLabel("< x <");

        checkboxColor = new JCheckBox("8 color");


        textFieldrmin.setText("0");


        textFieldxmin.setText("0");


        textFieldrmax.setText("4");


        textFieldxmax.setText("1");


        System.out.print(textFieldList.get(0).getText());
       /* textFieldList.add(textFieldxmin);
        textFieldList.add(textFieldxmax);
        textFieldList.add(textFieldrmin);
        textFieldList.add(textFieldrmax);*/

        panelMenu.add(buttonStart);
        panelMenu.add(buttonClear);
        panelMenu.add(buttonMore);
        panelMenu.add(textFieldrmin);
        panelMenu.add(labelRRange);
        panelMenu.add(textFieldrmax);

        panelMenu.add(checkboxColor);

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

    public JButton getButtonMore() {
        return buttonMore;
    }

    public List<JTextField> getTextFieldList() {
        return textFieldList;
    }



    public JPanel getPanelField(){
        return panelField;
    }
    public JCheckBox getCheckboxColor(){
        return checkboxColor;
    }
    public void addPanel(JPanel panel){
        frame.add(panel, BorderLayout.CENTER);
        frame.revalidate();
    }

}
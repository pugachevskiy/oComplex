package com.openComplex.app.DynamicalSystems.IteratedMaps;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class IMView {

    private JFrame frame;
    private JLabel[] fix;
    private JLabel[] fixType;
    private JLabel[] fixInfo;
    private JButton okButton;
    public JFormattedTextField[] coeff;

    public void view() {
        frame = new JFrame("Iterated Maps");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 200, 800, 400);
        frame.setResizable(false);

        frame.setLayout(new BorderLayout());
        frame.add(northPanel(), BorderLayout.NORTH);
        frame.add(centerPanel(), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void setActionListener(ActionListener control) {
        okButton.addActionListener(control);
    }

    private JPanel northPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        okButton = new JButton("OK");
        Font font = okButton.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), 15);

        coeff = new JFormattedTextField[5];

        for (int i = 0; i < coeff.length; i++) {
            coeff[i] = new JFormattedTextField(new DecimalFormat("#.##"));
            coeff[i].setFont(newFont);
            coeff[i].setPreferredSize(new Dimension(60, 25));
        }

        coeff[0].setText("5.00");
        coeff[1].setText("4.00");
        coeff[2].setText("-4.00");
        coeff[3].setText("4.00");
        coeff[4].setText("4.00");

        okButton.setFont(newFont);
        okButton.setBackground(new Color(192, 192, 192));
        okButton.setActionCommand("ok");


        newFont = new Font(font.getName(), font.getStyle(), 20);

        JLabel equation = new JLabel("Equation: ");
        JLabel coeff4Label = new JLabel("\u00b7x\u2074 + ");
        JLabel coeff3Label = new JLabel("\u00b7x\u00b3 + ");
        JLabel coeff2Label = new JLabel("\u00b7x\u00b2 + ");
        JLabel coeff1Label = new JLabel("\u00b7x + ");

        equation.setFont(newFont);
        coeff1Label.setFont(newFont);
        coeff2Label.setFont(newFont);
        coeff3Label.setFont(newFont);
        coeff4Label.setFont(newFont);

        panel.add(equation);
        panel.add(coeff[4]);
        panel.add(coeff4Label);
        panel.add(coeff[3]);
        panel.add(coeff3Label);
        panel.add(coeff[2]);
        panel.add(coeff2Label);
        panel.add(coeff[1]);
        panel.add(coeff1Label);
        panel.add(coeff[0]);

        for (int i = 0; i < 3; i++) {
            panel.add(new JLabel(" "));
        }

        panel.add(okButton);

        return panel;
    }

    private JPanel centerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        fix = new JLabel[4];
        fixType = new JLabel[4];
        fixInfo = new JLabel[4];

        JLabel valueLabel = new JLabel("Value");
        JLabel typeLabel = new JLabel("Type");
        JLabel infoLabel = new JLabel("Information");


        Font font = valueLabel.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), 15);

        valueLabel.setFont(newFont);
        infoLabel.setFont(newFont);
        typeLabel.setFont(newFont);

        valueLabel.setForeground(Color.red);
        infoLabel.setForeground(Color.red);
        typeLabel.setForeground(Color.red);

        panel.add(new JLabel());
        panel.add(valueLabel);
        panel.add(typeLabel);
        panel.add(infoLabel);

        addFixAndInfo(panel, newFont);

        return panel;
    }

    private void addFixAndInfo(JPanel panel, Font newFont) {
        for (int i = 0; i < 4; i++) {
            JLabel fixLabel = new JLabel("Fixpunkt " + (i + 1) + ":");
            fixLabel.setFont(newFont);
            fixLabel.setForeground(Color.red);

            fix[i] = new JLabel("---");
            fixType[i] = new JLabel("---");
            fixInfo[i] = new JLabel("---");

            fix[i].setFont(newFont);
            fixType[i].setFont(newFont);
            fixInfo[i].setFont(newFont);

            panel.add(fixLabel);
            panel.add(fix[i]);
            panel.add(fixType[i]);
            panel.add(fixInfo[i]);

        }
    }

    public void setFixpoint(double[] fixPoint, String[] type, String[] info) {
        for (int i = 0; i < fix.length; i++) {
            if (fixPoint[i] != Double.MAX_VALUE) {
                fix[i].setText(Double.toString(fixPoint[i]));
            } else {
                fix[i].setText("---");
            }
            fixType[i].setText(type[i]);
            fixInfo[i].setText(info[i]);
        }
    }
}
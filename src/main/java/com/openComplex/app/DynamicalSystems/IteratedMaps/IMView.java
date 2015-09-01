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
    public JFormattedTextField a, b, c, d, e;


    public void view() {
        frame = new JFrame("Iterated Maps");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(100, 200, 1100, 500);
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

        a = new JFormattedTextField(new DecimalFormat("#.##"));
        b = new JFormattedTextField(new DecimalFormat("#.##"));
        c = new JFormattedTextField(new DecimalFormat("#.##"));
        d = new JFormattedTextField(new DecimalFormat("#.##"));
        e = new JFormattedTextField(new DecimalFormat("#.##"));

        Font font = a.getFont();
        Font newFont = new Font(font.getName(), font.getStyle(), 25);
        a.setFont(newFont);
        b.setFont(newFont);
        c.setFont(newFont);
        d.setFont(newFont);
        e.setFont(newFont);

        a.setText("5.00");
        b.setText("4.00");
        c.setText("-4.00");
        d.setText("4.00");
        e.setText("4.00");

        a.setPreferredSize(new Dimension(80, 40));
        b.setPreferredSize(new Dimension(80, 40));
        c.setPreferredSize(new Dimension(80, 40));
        d.setPreferredSize(new Dimension(80, 40));
        e.setPreferredSize(new Dimension(80, 40));

        okButton = new JButton("OK");
        okButton.setFont(newFont);
        okButton.setBackground(new Color(192, 192, 192));
        okButton.setActionCommand("ok");


        newFont = new Font(font.getName(), font.getStyle(), 30);

        JLabel equation = new JLabel("Equation: ");
        JLabel eLabel = new JLabel("\u00b7x\u2074 + ");
        JLabel dLabel = new JLabel("\u00b7x\u00b3 + ");
        JLabel cLabel = new JLabel("\u00b7x\u00b2 + ");
        JLabel bLabel = new JLabel("\u00b7x + ");

        equation.setFont(newFont);
        eLabel.setFont(newFont);
        dLabel.setFont(newFont);
        cLabel.setFont(newFont);
        bLabel.setFont(newFont);

        panel.add(equation);
        panel.add(e);
        panel.add(eLabel);
        panel.add(d);
        panel.add(dLabel);
        panel.add(c);
        panel.add(cLabel);
        panel.add(b);
        panel.add(bLabel);
        panel.add(a);


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
        Font newFont = new Font(font.getName(), font.getStyle(), 20);

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
        for(int i = 0; i < 4; i++) {
            JLabel fixLabel = new JLabel("Fixpunkt " + (i+1) + ":");
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
        for(int i = 0; i < fix.length; i++) {
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
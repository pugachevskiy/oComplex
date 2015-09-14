package com.openComplex.app.CellularAutomat.WolframsUniverse;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by Matthias on 16.06.2015.
 */
public class MainWolfram implements ActionListener, ChangeListener {
    private Field field;
    private GUIWolfram guiWolfram;
    private Rule rule;
    private int gen = 1;

    public MainWolfram() {
        guiWolfram = new GUIWolfram();
        field = new Field(8, Color.BLACK);
        rule = new Rule();
        guiWolfram.addField(field);
        guiWolfram.addActionListener(this);
        guiWolfram.addChangeListener(this);
        rule.setRule(Integer.parseInt(guiWolfram.getRuleFieldText()));
        field.createCA(rule.getRule());
        field.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            field.setLast(1);
            int ruleInt = Integer.parseInt(guiWolfram.getRuleFieldText());
            if (ruleInt >= 0 && ruleInt <= 255) {
                rule.setRule(ruleInt);
                field.createCA(rule.getRule());
            }
           field.repaint();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        field.setLast(gen);
        field.resetField();
        JSlider source = (JSlider) e.getSource();
        gen = source.getValue();
        field.setGen(gen);
        field.createCA(rule.getRule());
    }
}





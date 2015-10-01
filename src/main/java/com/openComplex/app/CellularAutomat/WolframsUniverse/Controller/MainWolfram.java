package com.openComplex.app.CellularAutomat.WolframsUniverse.Controller;

import com.openComplex.app.CellularAutomat.WolframsUniverse.Model.Field;
import com.openComplex.app.CellularAutomat.WolframsUniverse.Model.Rule;
import com.openComplex.app.CellularAutomat.WolframsUniverse.View.GUIWolfram;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


/**
 * Created by Matthias on 16.06.2015.
 */
public class MainWolfram implements ActionListener, ChangeListener {
    private Field field;
    private GUIWolfram guiWolfram;
    private Rule rule;
    private int gen = 1;
    private boolean flag = false;
    private int speed = 150;

    public MainWolfram() {
        guiWolfram = new GUIWolfram();
        field = new Field(8, Color.BLACK);
        rule = new Rule();
        guiWolfram.addField(field);
        guiWolfram.addActionListener(this);
        guiWolfram.addChangeListener(this);
        rule.setRule(Integer.parseInt(guiWolfram.getRuleFieldText()));
        field.createCA(rule.getRule());
    }

    private void start() {
        new Thread() {
            public void run() {
                try {
                    while (flag) {
                        guiWolfram.setGenerationSlider(gen++);
                        sleep(speed);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Enter":
                field.setLast(1);
                int ruleInt = Integer.parseInt(guiWolfram.getRuleFieldText());
                if (ruleInt >= 0 && ruleInt <= 255) {
                    rule.setRule(ruleInt);
                    field.createCA(rule.getRule());
                }
                break;

            case "Clear":
                field.setLast(0);
                field.resetField();
                guiWolfram.setGenerationSlider(0);
                if (flag) {
                    flag = !flag;
                    guiWolfram.setStartButtonText("Start");
                }
                break;
            case "Start":
                flag = true;
                start();
                guiWolfram.setStartButtonText("Stop");
                break;
            case "Stop":
                flag = false;
                guiWolfram.setStartButtonText("Start");
                break;
            case "Save":
                try {
                    field.saveField();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            case "Rule":
              //  gui.getRule();
                break;
            case "Load":
                int gen = 0;
                try {
                    gen = field.loadField();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                field.setLast(0);
                guiWolfram.setGenerationSlider(gen);
                if (flag) {
                    flag = !flag;
                    guiWolfram.setStartButtonText("Start");
                }
                break;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        field.setLast(1);
        field.resetField();
        JSlider source = (JSlider) e.getSource();
        gen = source.getValue();
        field.setGen(gen);
        field.createCA(rule.getRule());
        field.setGridSize();
    }
}





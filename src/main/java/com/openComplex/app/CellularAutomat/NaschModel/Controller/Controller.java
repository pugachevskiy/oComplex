package com.openComplex.app.CellularAutomat.NaschModel.Controller;

import com.openComplex.app.CellularAutomat.NaschModel.Model.Field;
import com.openComplex.app.CellularAutomat.NaschModel.View.GUI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Controller implements ActionListener, ChangeListener {
    private Field field;
    private GUI gui;
    private int systemSpeed = 500;
    private boolean start = false;
    private int acc = 1, dec = 1, accDistance, decDistance;


    public Controller() {
        gui = new GUI();
        field = new Field(20, Color.BLACK);
        gui.addField(field);
        gui.addActionListener(this);
        gui.addChangeListener(this);
    }

    private void start() {
        new Thread() {
            public void run() {
                try {
                    while (start) {
                        field.applyRules();
                        sleep(systemSpeed);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();
        if (temp.equals("start")) {
            start = true;
            start();
            gui.changeStateButtonTo("stop");
            gui.enableControlField(false);
            gui.enableRuleField(false);
        } else if (temp.equals("stop")) {
            start = false;
            gui.changeStateButtonTo("start");
            gui.enableControlField(true);
            if (gui.getOptionsBox() == 1) {
                gui.enableRuleField(true);
            }
        }
        if (temp.equals("systemSpeed")) {
            getSystemSpeed();
        }
        if (temp.equals("maxSpeed")) {
            getMaxSpeed();
        }
        if (temp.equals("random")) {
            getRandom();
        }
        if (temp.equals("options")) {
            getOptions();
        }
        if (temp.equals("acc")) {
            getAcc();
        }
        if (temp.equals("dec")) {
            getDec();
        }
        if (temp.equals("accDistance")) {
            getAccDistance();
        }
        if (temp.equals("decDistance")) {
            getDecDistance();
        }
    }

    private void getOptions() {
        int index = gui.getOptionsBox();
        switch (index) {
            case 0:
                field.setDec(1);
                field.setAcc(1);
                field.setAccDistance(0);
                field.setDecDistance(0);
                gui.enableRuleField(false);
                break;
            case 1:
                getAcc();
                getDec();
                getDecDistance();
                getAccDistance();
                gui.enableRuleField(true);
                break;
        }
    }

    private void getSystemSpeed() {
        int index = gui.getSystemSpeedBox();
        switch (index) {
            case 0:
                systemSpeed = 1000;
                break;
            case 1:
                systemSpeed = 500;
                break;
            case 2:
                systemSpeed = 250;
                break;
        }
    }

    private void getMaxSpeed() {
        int index = gui.getMaxSpeedBox();
        switch (index) {
            case 0:
                field.setMaxSpeed(1);
                break;
            case 1:
                field.setMaxSpeed(2);
                break;
            case 2:
                field.setMaxSpeed(3);
                break;
            case 3:
                field.setMaxSpeed(4);
                break;
            case 4:
                field.setMaxSpeed(5);
                break;
            case 5:
                field.setMaxSpeed(6);
                break;
            case 6:
                field.setMaxSpeed(7);
                break;
            case 7:
                field.setMaxSpeed(8);
                break;
            case 8:
                field.setMaxSpeed(9);
                break;
            case 9:
                field.setMaxSpeed(10);
                break;
        }
    }

    private void getRandom() {
        int index = gui.getRandomBox();
        switch (index) {
            case 0:
                field.setRandom(0.0);
                break;
            case 1:
                field.setRandom(0.1);
                break;
            case 2:
                field.setRandom(0.2);
                break;
            case 3:
                field.setRandom(0.3);
                break;
            case 4:
                field.setRandom(0.4);
                break;
            case 5:
                field.setRandom(0.5);
                break;
            case 6:
                field.setRandom(0.6);
                break;
            case 7:
                field.setRandom(0.7);
                break;
            case 8:
                field.setRandom(0.8);
                break;
            case 9:
                field.setRandom(0.9);
                break;
            case 10:
                field.setRandom(1.0);
                break;
        }
    }

    private void getAcc() {
        int index = gui.getAccBox();
        switch (index) {
            case 0:
                field.setAcc(1);
                break;
            case 1:
                field.setAcc(2);
                break;
            case 2:
                field.setAcc(3);
                break;
        }
    }

    private void getDec() {
        int index = gui.getDecBox();
        switch (index) {
            case 0:
                field.setDec(1);
                break;
            case 1:
                field.setDec(2);
                break;
            case 2:
                field.setDec(3);
                break;
        }
    }

    private void getDecDistance() {
        int index = gui.getDecDistanceBox();
        switch (index) {
            case 0:
                field.setDecDistance(0);
                break;
            case 1:
                field.setDecDistance(1);
                break;
            case 2:
                field.setDecDistance(2);
                break;
            case 3:
                field.setDecDistance(3);
                break;
        }
    }

    private void getAccDistance() {
        int index = gui.getAccDistanceBox();
        switch (index) {
            case 0:
                field.setAccDistance(0);
                break;
            case 1:
                field.setAccDistance(1);
                break;
            case 2:
                field.setAccDistance(2);
                break;
            case 3:
                field.setAccDistance(3);
                break;
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider) e.getSource();
        int percent = source.getValue();
        field.resetField();
        field.startCondition(percent);
    }
}

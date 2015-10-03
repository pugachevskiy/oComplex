package com.openComplex.app.DynamicalSystems.LogicalPictures;

/**
 * Created by strange on 25/08/15.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Feigenbaum implements ActionListener {

    private FeigenbaumView gui;
    private FeigenbaumModel model;

    public Feigenbaum() {
        gui = new FeigenbaumView();
        model = new FeigenbaumModel();
        addListener();
        gui.addPanel(model);

    }
    private void addListener(){
        gui.getButtonStart().addActionListener(this);
        gui.getButtonClear().addActionListener(this);
    }

    private void start() {
        new Thread() {
            public void run() {
                while (model.myGo) {
                    try {
                        model.repaint();
                        sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    model.repaint();
                }
            }
        }.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "Start":
                model.myGo = true;
                model.initCoord(gui.getTextFieldList());
                start();
                break;
            case "Clear":
                model.myGo = false;
                gui.getTextFieldList().get(0).setText("0");
                gui.getTextFieldList().get(1).setText("1");
                gui.getTextFieldList().get(2).setText("0");
                gui.getTextFieldList().get(3).setText("4");
                break;
            default:
                break;

        }
    }
}
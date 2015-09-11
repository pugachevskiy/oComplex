package com.openComplex.app.CellularAutomat.GameOfLife2.Controller;

import com.openComplex.app.CellularAutomat.GameOfLife2.Model.Field;
import com.openComplex.app.CellularAutomat.GameOfLife2.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 10/09/15.
 */
public class Controller implements ActionListener{
    private Field field;
    private View gui;
    private boolean lifegoeson = false;
    private int gamespeed = 100;
    private int counter = 0;

    public Controller(){
        field = new Field();
        gui = new View();
        gui.init();
        gui.addField(field);
        gui.addListener(this);
    }
    private void start() {
        new Thread() {
            public void run() {
                try {
                    while (lifegoeson) {
                        if(field.nextStep()) {
                            updateCounter();
                        } else {
                            lifegoeson = false;
                        }
                        sleep(gamespeed);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void updateCounter(){
        counter++;
        gui.setCounter(String.valueOf(counter));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Start":
                lifegoeson = true;
                gui.activateButtons();
                start();
                break;
            case "Stop":
                lifegoeson = false;
                gui.deactivateButtons();
                break;
            case "Next":
                if(field.nextStep()) {
                    updateCounter();
                } else {
                    lifegoeson = false;
                }
                break;
            case "Exit":
                gui.frameClose();
                break;
        }
    }


}

package com.openComplex.app.CellularAutomat.GameOfLife2.Controller;

import com.openComplex.app.CellularAutomat.GameOfLife.View.ViewController;
import com.openComplex.app.CellularAutomat.GameOfLife2.Model.Field;
import com.openComplex.app.CellularAutomat.GameOfLife2.View.View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 10/09/15.
 */
public class Controller implements ActionListener {
    private Field field;
    private View gui;
    private boolean lifegoeson = false;
    private int gamespeed = 100;
    private int counter = 0;
    private int size;
    private int lenght;

    public final static int LARGESIZE = 20;
    public final static int MEDIUMSIZE = 15;
    public final static int SMALLSIZE = 10;
    private Color cellColor = Color.BLACK;


    public Controller() {

        field = new Field(200, MEDIUMSIZE, cellColor);
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
                        if (field.nextStep()) {
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

    private void updateCounter() {
        counter++;
        gui.setCounter(String.valueOf(counter));
    }

    private void createFeld() {

        gui.deleteField(field);
        field.remove(field);
        field = new Field(200, size, cellColor);
        gui.addField(field);
        field.revalidate();
        field.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            //Buttons
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
                if (field.nextStep()) {
                    updateCounter();
                } else {
                    lifegoeson = false;
                }
                break;
            case "Exit":
                gui.frameClose();
                break;
            //JCombobox
            case "Cell size":
                getSize();
                break;
            case "Init state":
                field.setFigure(gui.getFigureBox());
                break;
            case "Speed":
                getSpeed();
                break;
            case "Cell color":
                getColor();
                break;

        }
    }

    private void getColor() {
        int index = gui.getColorBox();
        switch (index) {
            case 0:
                cellColor = Color.BLACK;
                break;
            case 1:
                cellColor = Color.BLUE;
                break;
            case 2:
                cellColor = Color.GREEN;
                break;
            case 3:
                cellColor = Color.YELLOW;
                break;
        }
        field.setColor(cellColor);
    }

    private void getSpeed() {
        int index = gui.getSpeedBox();
        switch (index) {
            case 0:  //4 Hz
                gamespeed = 250;
                break;
            case 1:    //1 Hz
                gamespeed = 1000;
                break;
            case 2: //8 Hz
                gamespeed = 25;
                break;
        }
    }

    private void getSize() {
        int index = gui.getSizeBox();
        switch (index) {
            case 0:
                this.size = SMALLSIZE;
                break;
            case 1:
                this.size = MEDIUMSIZE;
                break;
            case 2:
                this.size = LARGESIZE;
                break;
        }
        createFeld();
        field.setFigure(gui.getFigureBox());
    }

}

package com.openComplex.app.CellularAutomat.GameOfLife.Controller;

import com.openComplex.app.CellularAutomat.GameOfLife.Model.Cell;
import com.openComplex.app.CellularAutomat.GameOfLife.View.ViewController;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Created by strange1 on 17/11/14.
 */
public class Life implements MouseListener{


    public static ViewController  gui;
    private Cell field[][];
    int gamespeed = 250;
    private int lifegoeson = 0;
    private int length;
    private Cell[][] cells_after;

    public Life() {
        gui = new ViewController();
        addListener();
        createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
        setTaube();
    }
    private void addListener() {
        gui.getSpeichernItem().addActionListener(menuController);
        gui.getExitItem().addActionListener(menuController);
        gui.getSpielRegelnItem().addActionListener(menuController);
        gui.getAnfangsBedingungBox().addActionListener(cListener);
        gui.getCellFormBox().addActionListener(cListener);
        gui.getCellGroeßeBox().addActionListener(cListener);
        gui.getCellFarbeBox().addActionListener(cListener);
        gui.getGeschwindigkeitBox().addActionListener(cListener);
        gui.getStartButton().addActionListener(bListener);
        gui.getStopButton().addActionListener(bListener);
        gui.getNextButton().addActionListener(bListener);
        gui.getEndButton().addActionListener(bListener);

    }
    private void start() {
        new Thread() {
            public void run() {
                try {
                    while (lifegoeson == 1) {
                        updateGame();
                        sleep(gamespeed);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private int getLength(int index) {
        int len;
        switch (index) {
            case 0:
                if (gui.getFieldSize().getHeight() >= gui.getFieldSize().getWidth()) {
                    len = ((int) gui.getFieldSize().getWidth()) / Cell.KLEIN;
                } else {
                    len = ((int) gui.getFieldSize().getHeight()) / Cell.KLEIN;
                }
                this.length = len;
                return len;

            case 1:
                if (gui.getFieldSize().getHeight() >= gui.getFieldSize().getWidth()) {
                    len = ((int) gui.getFieldSize().getWidth()) / Cell.MITTE;
                } else {
                    len = ((int) gui.getFieldSize().getHeight()) / Cell.MITTE;
                }
                this.length = len;
                return len;

            case 2:
                if (gui.getFieldSize().getHeight() >= gui.getFieldSize().getWidth()) {
                    len = ((int) gui.getFieldSize().getWidth()) / Cell.GROSS;
                } else {
                    len = ((int) gui.getFieldSize().getHeight()) / Cell.GROSS;
                }
                this.length = len;
                return len;

            default:
                return 10;

        }
    }


    private void updateGame() {
        getNewStats();
        playGod();
        colorChange();
    }


    public void getNewStats() {
        cells_after = new Cell[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                cells_after[i][j] = new Cell(length);
            }
        }
        for (int i = 1; i < length-1; i++) {
            for (int j = 1; j < length-1; j++) {
                int k = 8;
                if (field[i][j - 1] == null || !field[i][j - 1].getCell())
                    k--;
                if (field[i][j + 1] == null || !field[i][j + 1].getCell())
                    k--;
                if (field[i - 1][j] == null || !field[i - 1][j].getCell())
                    k--;
                if (field[i + 1][j] == null || !field[i + 1][j].getCell())
                    k--;
                if (field[i - 1][j - 1] == null || !field[i - 1][j - 1].getCell())
                    k--;
                if (field[i + 1][j - 1] == null || !field[i + 1][j - 1].getCell())
                    k--;
                if (field[i - 1][j + 1] == null || !field[i - 1][j + 1].getCell())
                    k--;
                if (field[i + 1][j + 1] == null || !field[i + 1][j + 1].getCell())
                    k--;
                switch (k) {
                    case 1:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 4:
                        cells_after[i][j].setCell(false);
                        break;
                    case 3:
                        cells_after[i][j].setCell(true);
                        break;
                    case 2:
                        if (field[i][j].getCell())
                            cells_after[i][j].setCell(true);
                        break;
                }
            }
        }
    }

    public void playGod() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (cells_after[i][j].getCell()) {
                    field[i][j].setCell(true);
                } else {
                    field[i][j].setCell(false);
                }

            }
        }
    }


    public void colorChange() {
       for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (field[i][j].getCell()) {
                    field[i][j].setColor(gui.getCellFarbeBox().getSelectedIndex());
                } else {
                    field[i][j].setColor(Color.WHITE);
                }
            }
        }

        gui.getFieldPanel().repaint();
    }

    public void createFeld(int size) {
        gui.getFieldPanel().removeAll();
        gui.getFieldPanel().setLayout(new GridLayout(size, size));
        field = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = new Cell(size);
                field[i][j].addMouseListener(this);
                gui.getFieldPanel().add(field[i][j]);
            }
        }
        colorChange();
        gui.getFieldPanel().revalidate();

    }

    public void setTaube() {
        field[field.length / 2 - 3][field.length / 2 - 2].get_to_life();
        field[field.length / 2 - 2][field.length / 2 - 2].get_to_life();
        field[field.length / 2 - 3][field.length / 2 - 1].get_to_life();
        field[field.length / 2 - 2][field.length / 2 - 1].get_to_life();
        field[field.length / 2 - 1][field.length / 2 - 1].get_to_life();
        field[field.length / 2][field.length / 2 - 1].get_to_life();
        field[field.length / 2 + 1][field.length / 2 - 1].get_to_life();
        field[field.length / 2 + 2][field.length / 2 - 2].get_to_life();
        field[field.length / 2 + 2][field.length / 2 - 3].get_to_life();
        field[field.length / 2 + 1][field.length / 2 - 3].get_to_life();
        field[field.length / 2][field.length / 2 - 3].get_to_life();
        field[field.length / 2 - 3][field.length / 2 + 2].get_to_life();
        field[field.length / 2 - 2][field.length / 2 + 2].get_to_life();
        field[field.length / 2 - 3][field.length / 2 + 1].get_to_life();
        field[field.length / 2 - 2][field.length / 2 + 1].get_to_life();
        field[field.length / 2 - 1][field.length / 2 + 1].get_to_life();
        field[field.length / 2][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 1][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 2][field.length / 2 + 2].get_to_life();
        field[field.length / 2 + 2][field.length / 2 + 3].get_to_life();
        field[field.length / 2 + 1][field.length / 2 + 3].get_to_life();
        field[field.length / 2][field.length / 2 + 3].get_to_life();
        colorChange();
        gui.getFieldPanel().revalidate();
    }
    public void setFigure1() {
        //up
        field[field.length / 2 - 1][field.length / 2 - 1].get_to_life();
        field[field.length / 2 - 2][field.length / 2 - 1].get_to_life();
        field[field.length / 2 - 3][field.length / 2 - 1].get_to_life();
        field[field.length / 2 - 3][field.length / 2].get_to_life();
        field[field.length / 2 - 3][field.length / 2 + 1].get_to_life();
        field[field.length / 2 - 2][field.length / 2 + 1].get_to_life();
        field[field.length / 2 - 1][field.length / 2 + 1].get_to_life();
        //down
        field[field.length / 2 + 1][field.length / 2 - 1].get_to_life();
        field[field.length / 2 + 2][field.length / 2 - 1].get_to_life();
        field[field.length / 2 + 3][field.length / 2 - 1].get_to_life();
        field[field.length / 2 + 3][field.length / 2].get_to_life();
        field[field.length / 2 + 3][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 2][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 1][field.length / 2 + 1].get_to_life();
        colorChange();
        gui.getFieldPanel().revalidate();
    }
    public void setGliter() {
        field[field.length / 2 - 1][field.length / 2 ].get_to_life();
        field[field.length / 2][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 1][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 1][field.length / 2 ].get_to_life();
        field[field.length / 2 + 1][field.length / 2 - 1].get_to_life();
        colorChange();
        gui.getFieldPanel().revalidate();
    }



    private ActionListener cListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            String combovalue = (String) combo.getSelectedItem();
            switch (combovalue) {
                case "Klein":
                    createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
                    break;
                case "Mittel":
                    createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
                    break;
                case "Groß":
                    createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
                    break;
                case "Figure 1":
                    createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
                    setFigure1();
                    break;
                case "Taube":
                    createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
                    setTaube();
                    break;
                case "Gliter":
                    createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
                    setGliter();
                    break;
                case "blank":
                    createFeld(getLength(gui.getCellGroeßeBox().getSelectedIndex()));
                    break;
                case "Normal":  //4 Hz
                    gamespeed = 250;
                    break;
                case "Langsam":    //1 Hz
                    gamespeed = 1000;
                    break;
                case "Schnell": //8 Hz
                    gamespeed = 25;
                    break;
                case "Schwarz":
                    colorChange();
                    break;
                case "Blau":
                    colorChange();
                    break;
                case "Grün":
                    colorChange();
                    break;
                case "Gelb":
                    colorChange();
                    break;
                default:
                    break;
            }
        }
    };
    private MenuController menuController = new MenuController();
    private ActionListener bListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton but = (JButton) e.getSource();
            switch (but.getText()) {
                case "Start":
                    lifegoeson = 1;
                    gui.activateButtons();
                    start();
                    break;
                case "Stop":
                    lifegoeson = 0;
                    gui.deactivateButtons();
                    break;
                case "Next":
                    updateGame();
                    break;
                case "Beenden":
                    gui.frameClose();
                    break;
            }
        }
    };

    @Override
    public void mouseClicked(MouseEvent e) {
           Cell comp = (Cell) e.getComponent();
           comp.setCell(!comp.getCell());
           colorChange();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

}

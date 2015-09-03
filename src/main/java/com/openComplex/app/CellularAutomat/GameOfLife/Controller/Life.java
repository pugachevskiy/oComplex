package com.openComplex.app.CellularAutomat.GameOfLife.Controller;

import com.openComplex.app.CellularAutomat.GameOfLife.Model.Cell;
import com.openComplex.app.CellularAutomat.GameOfLife.View.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * Created by strange1 on 17/11/14.
 */
public class Life implements MouseListener {


    public static ViewController gui;
    private Cell field[][];
    int gamespeed = 250;
    private int lifegoeson = 0;
    private int size;
    private int length;
    private int counter = 0;
    private Cell[][] cells_after;


    public Life() {
        gui = new ViewController();
        addListener();
        getLength(gui.getCellGroeßeBox().getSelectedIndex());
        createFeld();
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
        gui.getFieldPanel().addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                gui.getFieldPanel().revalidate();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });

    }

    private void start() {
        new Thread() {
            public void run() {
                try {
                    while (lifegoeson == 1) {
                        updateGame();
                        updateCounter();
                        sleep(gamespeed);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private int getLength(int index) {
        switch (index) {
            case 0:
                this.size = Cell.KLEIN;
                this.length = ((int) gui.getFieldSize().getWidth()) / Cell.KLEIN;
                return Cell.KLEIN;
            case 1:
                this.size = Cell.MITTE;
                this.length = ((int) gui.getFieldSize().getWidth()) / Cell.MITTE;
                return Cell.MITTE;
            case 2:
                this.size = Cell.GROSS;
                this.length = ((int) gui.getFieldSize().getWidth()) / Cell.GROSS;
                return Cell.GROSS;
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
                cells_after[i][j] = new Cell(size);
            }
        }
        for (int i = 1; i < length - 1; i++) {
            for (int j = 1; j < length - 1; j++) {
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
        int counter = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (cells_after[i][j].getCell()) {
                    field[i][j].setCell(true);
                    counter++;
                } else {
                    field[i][j].setCell(false);
                }
            }
        }
        if (counter == 0){
            lifegoeson = 0;
            gui.deactivateButtons();
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
    private void updateCounter(){
        counter++;
        gui.setCounter(String.valueOf(counter));
    }

    public void createFeld() {
        counter = 0;
        gui.setCounter(String.valueOf(counter));
        gui.getFieldPanel().removeAll();
        getLength(gui.getCellGroeßeBox().getSelectedIndex());
        gui.getFieldPanel().setLayout(new GridLayout(length, length));

        field = new Cell[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                field[i][j] = new Cell(this.size);
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
        field[field.length / 2 - 1][field.length / 2].get_to_life();
        field[field.length / 2][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 1][field.length / 2 + 1].get_to_life();
        field[field.length / 2 + 1][field.length / 2].get_to_life();
        field[field.length / 2 + 1][field.length / 2 - 1].get_to_life();
        colorChange();
        gui.getFieldPanel().revalidate();
    }

    private void setFigure(int index) {
        createFeld();
        switch (index) {
            case 0:
                setTaube();
                break;
            case 1:
                setFigure1();
                break;
            case 2:
                setGliter();
                break;
            case 3:
                createFeld();
                colorChange();
                break;
            default:
                break;
        }

    }


    private ActionListener cListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox combo = (JComboBox) e.getSource();
            String combovalue = (String) combo.getSelectedItem();
            switch (combovalue) {
                case ViewController.SMALL:
                    createFeld();
                    setFigure(gui.getAnfangsBedingungBox().getSelectedIndex());
                    break;
                case ViewController.MEDIUM:
                    createFeld();
                    setFigure(gui.getAnfangsBedingungBox().getSelectedIndex());
                    break;
                case ViewController.LARGE:
                    createFeld();
                    setFigure(gui.getAnfangsBedingungBox().getSelectedIndex());
                    break;
                case "Figure 1":
                    getLength(gui.getCellGroeßeBox().getSelectedIndex());
                    setFigure(gui.getAnfangsBedingungBox().getSelectedIndex());
                    break;
                case "Taube":
                    getLength(gui.getCellGroeßeBox().getSelectedIndex());
                    setFigure(gui.getAnfangsBedingungBox().getSelectedIndex());
                    break;
                case "Gliter":
                    getLength(gui.getCellGroeßeBox().getSelectedIndex());
                    setFigure(gui.getAnfangsBedingungBox().getSelectedIndex());
                    break;
                case "blank":
                    getLength(gui.getCellGroeßeBox().getSelectedIndex());
                    setFigure(gui.getAnfangsBedingungBox().getSelectedIndex());
                    break;
                case ViewController.NORMAL:  //4 Hz
                    gamespeed = 250;
                    break;
                case ViewController.SLOW:    //1 Hz
                    gamespeed = 1000;
                    break;
                case ViewController.FAST: //8 Hz
                    gamespeed = 25;
                    break;
                case ViewController.BLACK:
                    colorChange();
                    break;
                case ViewController.BLUE:
                    colorChange();
                    break;
                case ViewController.GREEN:
                    colorChange();
                    break;
                case ViewController.YELLOW:
                    colorChange();
                    break;
                default:
                    break;
            }
            gui.getFieldPanel().revalidate();
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
                    updateCounter();
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

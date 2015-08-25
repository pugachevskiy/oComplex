package com.openComplex.app.CellularAutomat.WolframsUniverse;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Matthias on 16.06.2015.
 */
public class MainWolfram implements ActionListener, ChangeListener, MouseListener {
    public CoreWolfram[][] cells;
    public boolean[] rule = new boolean[8];
    private GUIWolfram guiWolfram;
    private int maxGeneration = 33;
    private int lastCol = 66;
    private int gen = 1;
    private int flag = 0;

    public MainWolfram() {
        guiWolfram = new GUIWolfram();

        cells = new CoreWolfram[maxGeneration][lastCol];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new CoreWolfram(25);

            }
        }

        init(25, 1);
        addListener();
        setRule(Integer.parseInt(guiWolfram.getRuleFieldText()));
        guiWolfram.getField().revalidate();
    }

    private void addListener() {
        guiWolfram.getRuleButton().addActionListener(this);
        guiWolfram.getGenerationSlider().addChangeListener(this);
    }

    public void init(int size, int flag) {
        if (this.flag != flag) {
            int l = 0;
            guiWolfram.getField().removeAll();
            guiWolfram.createField(maxGeneration, lastCol, size);

            cells = new CoreWolfram[maxGeneration][lastCol];
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                  guiWolfram.getField().add(cells[i][j] = new CoreWolfram(size));
                  cells[i][j].addMouseListener(this);
                }
            }
            cells[0][lastCol / 2].setCell(true);
            guiWolfram.getField().revalidate();
            this.flag = flag;
        }
    }

    private void resetField() {
        for (int i = 1; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j].setCell(false);
            }
        }
    }

    public void setRule(int ruleInt) {
        String stringBool = Integer.toBinaryString(ruleInt);
        char[] charBool = {0, 0, 0, 0, 0, 0, 0, 0};

        char[] stringToChar = stringBool.toCharArray();

        int index = 8 - stringToChar.length;
        for (char aStringToChar : stringToChar) {
            charBool[index] = aStringToChar;
            index = index + 1;
        }

        resetRule();

        for (int i = 0; i < charBool.length; i++) {
            rule[i] = charBool[i] == '1';
            System.out.print(rule[i] + " ");
            System.out.println();
        }
        createCA();
    }

    public void createCA() {
        boolean a, b, c;
        for (int generation = 1; generation < gen; generation++) {
            for (int i = 0; i < cells[generation].length; i++) {
                b = cells[generation - 1][i].getCell();
                int neighbourhood = 1;
                if (i == 0) {
                    a = cells[generation - 1][lastCol - 1].getCell();
                    c = cells[generation - 1][i + neighbourhood].getCell();
                } else if (i == lastCol - 1) {
                    a = cells[generation - 1][i - neighbourhood].getCell();
                    c = cells[generation - 1][0].getCell();
                } else {
                    a = cells[generation - 1][i - neighbourhood].getCell();
                    c = cells[generation - 1][i + neighbourhood].getCell();
                }
                cells[generation][i].setCell(applyRule(a, b, c));
            }
        }
    }

    private void resetRule() {
        for (int i = 0; i < rule.length; i++) {
            rule[i] = false;
        }
    }

    public boolean applyRule(boolean a, boolean b, boolean c) {
        if (a && b && c) {
            return rule[0];
        }
        if (a && b && c == false) {
            return rule[1];
        }
        if (a && b == false && c) {
            return rule[2];
        }
        if (a && b == false && c == false) {
            return rule[3];
        }
        if (a == false && b && c) {
            return rule[4];
        }
        if (a == false && b && c == false) {
            return rule[5];
        }
        if (a == false && b == false && c) {
            return rule[6];
        }
        if (a == false && b == false && c == false) {
            return rule[7];
        }
        return false;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("enter")) {
            int rule = Integer.parseInt(guiWolfram.getRuleFieldText());
            if (rule >= 0 && rule <= 255) {
                setRule(rule);
            }
            guiWolfram.getField().repaint();

        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        resetField();
        JSlider source = (JSlider) e.getSource();
        gen = source.getValue();
        if (gen >= 0 && gen <= 33) {
            maxGeneration = 33;
            lastCol = 66;
            init(25, 1);
            flag = 1;
        } else if (gen > 33 && gen <= 66) {
            maxGeneration = 66;
            lastCol = 132;
            init(20, 2);
            flag = 2;
        } else if (gen > 66 && gen <= 100) {
            maxGeneration = 100;
            lastCol = 200;
            init(15, 3);
            flag = 3;
        }
        int rule = Integer.parseInt(guiWolfram.getRuleFieldText());
        if (rule >= 0 && rule <= 255) {
            setRule(rule);
        }
        guiWolfram.getField().repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        CoreWolfram comp = (CoreWolfram) e.getComponent();
        comp.changeCell(comp);
        createCA();

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}





package com.openComplex.app.CellularAutomat.WolframsUniverse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


/**
 * Created by strange on 10/09/15.
 */
public class Field extends JPanel implements MouseListener {
    private int size;
    private Cell[][] field;
    private Color cellColor;
    private int lastCol = 200;
    private int gen = 1;
    private int last = 1;
    private boolean[] rule;

    public Field(int size, Color color) {
        this.cellColor = color;
        this.size = size;
        init();
        this.addMouseListener(this);
        this.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                Field.this.repaint();

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

    public void init() {
        field = new Cell[300][300];
        for (int i = 0; i < 300; i++) {
            for (int j = 0; j < 300; j++) {
                field[i][j] = new Cell(false, Color.BLACK);
            }
        }
    }

    public void setLast(int last){
        this.last = last;
    }

    public void setGen(int gen){
        this.gen = gen;
    }

    public void resetField() {
        for (int i = last; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j].setStatus(false);
            }
        }
    }

    public void createCA(boolean[] rule) {
        this.rule = rule;
        boolean a, b, c;
        for (int generation = last; generation < gen; generation++) {
            for (int i = 0; i < lastCol; i++) {
                b = field[generation - 1][i].getStatus();
                int neighbourhood = 1;
                if (i == 0) {
                    a = field[generation - 1][lastCol - 1].getStatus();
                    c = field[generation - 1][i + neighbourhood].getStatus();
                } else if (i == lastCol - 1) {
                    a = field[generation - 1][i - neighbourhood].getStatus();
                    c = field[generation - 1][0].getStatus();
                } else {
                    a = field[generation - 1][i - neighbourhood].getStatus();
                    c = field[generation - 1][i + neighbourhood].getStatus();
                }
                field[generation][i].setStatus(applyRule(a, b, c, rule));
            }
        }
        repaint();
    }

    public boolean applyRule(boolean a, boolean b, boolean c, boolean[] rule) {
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
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // paint Box
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        int i;
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.black);
        // draw the rows and cols
        int rowHt = height / (size);
        int rowWid = width / (size);
        if (rowHt >= rowWid) {
            for (i = 0; i < rowHt + 1; i++) {
                g.drawLine(0, i * size, width, i * size);
                g.drawLine(i * size, 0, i * size, height);
            }
        } else {
            for (i = 0; i < rowWid + 1; i++) {
                g.drawLine(0, i * size, width, i * size);
                g.drawLine(i * size, 0, i * size, height);
            }
        }
        // draw field
        g.setColor(cellColor);
        for (i = 0; i < rowHt + 1; i++) {
            for (int j = 0; j < rowWid + 1; j++) {
                if (field[i][j].getStatus()) {
                    g.fillRect(j * size, i * size, size, size);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getY()  < size) {
            field[e.getY() / size][e.getX() / size].setStatus(!field[e.getY() / size][e.getX() / size].getStatus());
            last = 1;
            createCA(rule);
        }
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

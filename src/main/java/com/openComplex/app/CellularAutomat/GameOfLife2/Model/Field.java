package com.openComplex.app.CellularAutomat.GameOfLife2.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;

/**
 * Created by strange on 10/09/15.
 */
public class Field extends JPanel implements MouseListener {
    private int size = 30;
    private Cell[][] field = new Cell[200][200];

    public Field() {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                field[i][j] = new Cell(i, j, false, Color.WHITE);
            }
        }
        this.addMouseListener(this);
        setTaube();
    }
//
    public void nextStep() {
        Cell[][] temp = field;
        for (int i = 1; i < 200; i++) {
            for (int j = 1; j < 200; j++) {
                if (temp[i][j].getStatus()) {
                    setNeigbors(i, j);
                }
            }
        }
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                if (temp[i][j].getNeighbors() == 2 && temp[i][j].getStatus()) {
                    field[i][j].setStatus(true);
                } else {
                    if (temp[i][j].getNeighbors() == 3) {
                        field[i][j].setStatus(true);
                    } else {
                        field[i][j].setStatus(false);
                    }

                }
            }
        }
        for (int i = 1; i < 200; i++) {
            for (int j = 1; j < 200; j++) {
                field[i][j].setNeighbors(0);
            }
        }
        repaint();
    }

    private void setNeigbors(int row, int col) {
        field[row - 1][col - 1].addNeighbors();
        field[row][col - 1].addNeighbors();
        field[row + 1][col - 1].addNeighbors();
        field[row - 1][col + 1].addNeighbors();
        field[row][col + 1].addNeighbors();
        field[row + 1][col + 1].addNeighbors();
        field[row - 1][col].addNeighbors();
        field[row + 1][col].addNeighbors();


    }

    private void setTaube() {
        int width = 20;
        field[width / 2 - 3][width / 2 - 2].setStatus(true);
        field[width / 2 - 2][width / 2 - 2].setStatus(true);
        field[width / 2 - 3][width / 2 - 1].setStatus(true);
        field[width / 2 - 2][width / 2 - 1].setStatus(true);
        field[width / 2 - 1][width / 2 - 1].setStatus(true);
        field[width / 2][width / 2 - 1].setStatus(true);
        field[width / 2 + 1][width / 2 - 1].setStatus(true);
        field[width / 2 + 2][width / 2 - 2].setStatus(true);
        field[width / 2 + 2][width / 2 - 3].setStatus(true);
        field[width / 2 + 1][width / 2 - 3].setStatus(true);
        field[width / 2][width / 2 - 3].setStatus(true);

        field[width / 2 - 3][width / 2 + 2].setStatus(true);
        field[width / 2 - 2][width / 2 + 2].setStatus(true);
        field[width / 2 - 3][width / 2 + 1].setStatus(true);
        field[width / 2 - 2][width / 2 + 1].setStatus(true);
        field[width / 2 - 1][width / 2 + 1].setStatus(true);
        field[width / 2][width / 2 + 1].setStatus(true);
        field[width / 2 + 1][width / 2 + 1].setStatus(true);
        field[width / 2 + 2][width / 2 + 2].setStatus(true);
        field[width / 2 + 2][width / 2 + 3].setStatus(true);
        field[width / 2 + 1][width / 2 + 3].setStatus(true);
        field[width / 2][width / 2 + 3].setStatus(true);
        this.revalidate();
    }

    public void setFigure1() {
        int width = 20;
        //up
        field[width / 2 - 1][width / 2 - 1].setStatus(true);
        field[width / 2 - 2][width / 2 - 1].setStatus(true);
        field[width / 2 - 3][width / 2 - 1].setStatus(true);
        field[width / 2 - 3][width / 2].setStatus(true);
        field[width / 2 - 3][width / 2 + 1].setStatus(true);
        field[width / 2 - 2][width / 2 + 1].setStatus(true);
        field[width / 2 - 1][width / 2 + 1].setStatus(true);
        //down
        field[width / 2 + 1][width / 2 - 1].setStatus(true);
        field[width / 2 + 2][width / 2 - 1].setStatus(true);
        field[width / 2 + 3][width / 2 - 1].setStatus(true);
        field[width / 2 + 3][width / 2].setStatus(true);
        field[width / 2 + 3][width / 2 + 1].setStatus(true);
        field[width / 2 + 2][width / 2 + 1].setStatus(true);
        field[width / 2 + 1][width / 2 + 1].setStatus(true);

        this.revalidate();
    }

    public void setGliter() {
        int width = 20;
        field[width / 2 - 1][width / 2].setStatus(true);
        field[width / 2][width / 2 + 1].setStatus(true);
        field[width / 2 + 1][width / 2 + 1].setStatus(true);
        field[width / 2 + 1][width / 2].setStatus(true);
        field[width / 2 + 1][width / 2 - 1].setStatus(true);

        this.revalidate();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        int i;
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.black);
        // draw the rows
        int rowHt = height / (size);
        for (i = 0; i < rowHt + 1; i++)
            g.drawLine(0, i * size, width, i * size);

        // draw the columns
        int rowWid = width / (size);
        for (i = 0; i < rowWid + 1; i++)
            g.drawLine(i * size, 0, i * size, height);


        for (i = 0; i < rowHt + 1; i++) {
            for (int j = 0; j < rowWid + 1; j++) {
                if (field[i][j].getStatus()) {
                    g.setColor(field[i][j].getColor());
                    g.fillRect(j * size, i * size, size, size);
                }
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        field[e.getY() / size][e.getX() / size].setStatus(!field[e.getY() / size][e.getX() / size].getStatus());
        System.out.print(e.getX() / size);
        repaint();
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

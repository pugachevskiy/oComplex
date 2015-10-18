package com.openComplex.app.CellularAutomat.GameOfLife.Model;

import com.openComplex.app.mainWindow.Controller.CSVFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class Field extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private int size;
    private Cell[][] field;
    private int length;
    private Color cellColor;
    private int newX = 0;
    private int newY = 0;

    public Field(int length, int size, Color color) {
        this.cellColor = color;
        this.size = size;
        this.length = length;
        init();
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
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
        field = new Cell[200][200];
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                field[i][j] = new Cell(i, j, false, Color.BLACK);
            }
        }
    }

    public void setColor(Color color) {
        this.cellColor = color;
        this.repaint();

    }

    public boolean nextStep() {
        Cell[][] temp = field;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (temp[i][j].getStatus()) {
                    setNeigbors(i, j);
                }
            }
        }
        boolean isAlive = false;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (temp[i][j].getNeighbors() == 2 && temp[i][j].getStatus()) {
                    field[i][j].setStatus(true);
                    isAlive = true;
                } else {
                    if (temp[i][j].getNeighbors() == 3) {
                        field[i][j].setStatus(true);
                        isAlive = true;
                    } else {
                        field[i][j].setStatus(false);
                    }

                }
            }
        }
        resetNeigbours();
        repaint();
        return isAlive;
    }

    private void resetNeigbours() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                field[i][j].setNeighbors(0);
            }
        }
    }

    private void setNeigbors(int row, int col) {
        if (row != 0) {
            field[row - 1][col].addNeighbors();
        }
        if (col != 0) {
            field[row][col - 1].addNeighbors();
        }
        if (col != 0 && row != 0) {
            field[row - 1][col - 1].addNeighbors();
        }
        if (row != 0 && col != length) {
            field[row - 1][col + 1].addNeighbors();
        }
        if (row != length && col != length) {
            field[row + 1][col + 1].addNeighbors();
        }
        if (row != length) {
            field[row + 1][col].addNeighbors();
        }
        if (col != length) {
            field[row][col + 1].addNeighbors();
        }
        if (row != length && col != 0) {
            field[row + 1][col - 1].addNeighbors();
        }
    }

    public void setFigure(int index) {
        setBlank();
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
                setBlank();
                break;
            default:
                break;
        }
        repaint();
    }

    private void setTaube() {
        field[length / 2 - 3][length / 2 - 2].setStatus(true);
        field[length / 2 - 2][length / 2 - 2].setStatus(true);
        field[length / 2 - 3][length / 2 - 1].setStatus(true);
        field[length / 2 - 2][length / 2 - 1].setStatus(true);
        field[length / 2 - 1][length / 2 - 1].setStatus(true);
        field[length / 2][length / 2 - 1].setStatus(true);
        field[length / 2 + 1][length / 2 - 1].setStatus(true);
        field[length / 2 + 2][length / 2 - 2].setStatus(true);
        field[length / 2 + 2][length / 2 - 3].setStatus(true);
        field[length / 2 + 1][length / 2 - 3].setStatus(true);
        field[length / 2][length / 2 - 3].setStatus(true);

        field[length / 2 - 3][length / 2 + 2].setStatus(true);
        field[length / 2 - 2][length / 2 + 2].setStatus(true);
        field[length / 2 - 3][length / 2 + 1].setStatus(true);
        field[length / 2 - 2][length / 2 + 1].setStatus(true);
        field[length / 2 - 1][length / 2 + 1].setStatus(true);
        field[length / 2][length / 2 + 1].setStatus(true);
        field[length / 2 + 1][length / 2 + 1].setStatus(true);
        field[length / 2 + 2][length / 2 + 2].setStatus(true);
        field[length / 2 + 2][length / 2 + 3].setStatus(true);
        field[length / 2 + 1][length / 2 + 3].setStatus(true);
        field[length / 2][length / 2 + 3].setStatus(true);
    }

    private void setFigure1() {
        //up
        field[length / 2 - 1][length / 2 - 1].setStatus(true);
        field[length / 2 - 2][length / 2 - 1].setStatus(true);
        field[length / 2 - 3][length / 2 - 1].setStatus(true);
        field[length / 2 - 3][length / 2].setStatus(true);
        field[length / 2 - 3][length / 2 + 1].setStatus(true);
        field[length / 2 - 2][length / 2 + 1].setStatus(true);
        field[length / 2 - 1][length / 2 + 1].setStatus(true);
        //down
        field[length / 2 + 1][length / 2 - 1].setStatus(true);
        field[length / 2 + 2][length / 2 - 1].setStatus(true);
        field[length / 2 + 3][length / 2 - 1].setStatus(true);
        field[length / 2 + 3][length / 2].setStatus(true);
        field[length / 2 + 3][length / 2 + 1].setStatus(true);
        field[length / 2 + 2][length / 2 + 1].setStatus(true);
        field[length / 2 + 1][length / 2 + 1].setStatus(true);
    }

    private void setGliter() {
        field[length / 2 - 1][length / 2].setStatus(true);
        field[length / 2][length / 2 + 1].setStatus(true);
        field[length / 2 + 1][length / 2 + 1].setStatus(true);
        field[length / 2 + 1][length / 2].setStatus(true);
        field[length / 2 + 1][length / 2 - 1].setStatus(true);
    }

    private void setBlank() {
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                field[i][j].setStatus(false);
                field[i][j].setNeighbors(0);
            }
        }
    }

    public void saveField() throws IOException {
        CSVFile.saveField(field, Field.this);
    }

    public void loadField() throws IOException {
        setBlank();
        CSVFile.loadField(field, Field.this);
        repaint();
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
            length = rowHt;
            for (i = 0; i < rowHt + 1; i++) {
                g.drawLine(0, i * size, width, i * size);
                g.drawLine(i * size, 0, i * size, height);
            }
        } else {
            length = rowWid;
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


    }

    @Override
    public void mousePressed(MouseEvent e) {
        field[e.getY() / size][e.getX() / size].setStatus(!field[e.getY() / size][e.getX() / size].getStatus());
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!(newX == e.getX() / size) || !(newY == e.getY() / size)) {
            field[e.getY() / size][e.getX() / size].setStatus(!field[e.getY() / size][e.getX() / size].getStatus());
            newX = e.getX() / size;
            newY = e.getY() / size;
            repaint();
        }
    }


    @Override
    public void mouseMoved(MouseEvent e) {

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

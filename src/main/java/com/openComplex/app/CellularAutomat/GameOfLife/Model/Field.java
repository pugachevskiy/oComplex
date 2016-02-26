package com.openComplex.app.CellularAutomat.GameOfLife.Model;

import com.openComplex.app.mainWindow.Controller.CSVFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Field extends JPanel implements MouseListener, MouseMotionListener {
    private static final long serialVersionUID = 1L;
    private int size;
    private Cell[][] field;
    private int lengthAbs;
    private int breadthAbs;
    private Color cellColor;
    private int newX = -1;
    private int newY = -1;
    private Point newPoint = new Point(-1, -1);
    private int form = 0;
    private int t, r, h;
    private int storageLength = 50;
    private int storageIndex = 0;

    private LinkedList<Cell[][]> storage = new LinkedList<>();

    public Field(int lengthAbs, int breadthAbs, int size, int form, Color color) {
        this.form = form;
        this.cellColor = color;
        this.lengthAbs = lengthAbs;
        this.breadthAbs = breadthAbs;
        this.size = size;
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

    private void init() {
        field = new Cell[200][200];
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                field[i][j] = new Cell(i, j, false, Color.BLACK);
            }
        }
        storage.removeAll(storage);
        storageIndex = 0;
    }


    public void setColor(Color color) {
        this.cellColor = color;
        this.repaint();
    }

    public boolean previousStep() {

        if (storageIndex > 0) {
            System.out.println("Pre: " + storageIndex);
            field = storage.get(storageIndex-1);
            for(int i = 0; i < 50; i++) {
                for(int j = 0; j < 50; j++) {
                    if (storage.get(storageIndex-1)[i][j].getStatus()) {
                        System.out.print(1);
                    } else {
                        System.out.print(0);
                    }
                }
                System.out.println();
            }
            storageIndex--;
            repaint();
            return true;
        } else {
            return false;
        }
    }

    public boolean nextStep() {

        storage.add(storageIndex, field);
        System.out.println("Next: " + storageIndex);
        for(int i = 0; i < 50; i++) {
            for(int j = 0; j < 50; j++) {
                if (storage.get(storageIndex)[i][j].getStatus()) {
                    System.out.print(1);
                } else {
                    System.out.print(0);
                }
            }
            System.out.println();
        }
        if(storageIndex <= storageLength) {
            storageIndex++;
        } else {
            storage.remove(0);
        }


        for (int i = 0; i < lengthAbs; i++) {
            for (int j = 0; j < breadthAbs; j++) {
                if(form == 0) {
                    if (field[i][j].getStatus()) {
                        setNeighborsSquare(i, j);
                    }
                } else if (form == 1) {
                    if (field[i][j].getStatus()) {
                        setNeighborsHexagon(i, j);
                    }
                }
            }
        }
        Cell[][] temp = field;

        boolean isAlive = false;
        for (int i = 0; i < lengthAbs; i++) {
            for (int j = 0; j < breadthAbs; j++) {
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



        resetNeighbours();
        repaint();
        return isAlive;
    }

    private void resetNeighbours() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j].setNeighbors(0);
            }
        }
    }

    private void setNeighborsHexagon(int row, int col) {
        if(col%2 == 0) {
            if(row != 0 && col != 0) {
                field[row-1][col-1].addNeighbors();
            }
            if(row != 0) {
                field[row-1][col].addNeighbors();
            }
            if(row != 0 && col != breadthAbs) {
                field[row-1][col+1].addNeighbors();
            }
            if(col != breadthAbs) {
                field[row][col+1].addNeighbors();
            }
            if(row != lengthAbs) {
                field[row+1][col].addNeighbors();
            }
            if(col != 0) {
                field[row][col-1].addNeighbors();
            }
        } else {
            if(col != 0) {
                field[row][col-1].addNeighbors();
            }
            if(row != 0) {
                field[row-1][col].addNeighbors();
            }
            if(col != breadthAbs) {
                field[row][col+1].addNeighbors();
            }
            if(row != lengthAbs && col != breadthAbs) {
                field[row+1][col+1].addNeighbors();
            }
            if(row != lengthAbs) {
                field[row+1][col].addNeighbors();
            }
            if(row != lengthAbs && col != 0) {
                field[row+1][col-1].addNeighbors();
            }
        }
    }

    private void setNeighborsSquare(int row, int col) {
        if (row != 0) {
            field[row - 1][col].addNeighbors();
        }
        if (col != 0) {
            field[row][col - 1].addNeighbors();
        }
        if (col != 0 && row != 0) {
            field[row - 1][col - 1].addNeighbors();
        }
        if (row != 0 && col != lengthAbs) {
            field[row - 1][col + 1].addNeighbors();
        }
        if (row != lengthAbs && col != lengthAbs) {
            field[row + 1][col + 1].addNeighbors();
        }
        if (row != lengthAbs) {
            field[row + 1][col].addNeighbors();
        }
        if (col != lengthAbs) {
            field[row][col + 1].addNeighbors();
        }
        if (row != lengthAbs && col != 0) {
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
        field[lengthAbs / 2 - 3][breadthAbs / 2 - 2].setStatus(true);
        field[lengthAbs / 2 - 2][breadthAbs / 2 - 2].setStatus(true);
        field[lengthAbs / 2 - 3][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 - 2][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 - 1][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 + 2][breadthAbs / 2 - 2].setStatus(true);
        field[lengthAbs / 2 + 2][breadthAbs / 2 - 3].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2 - 3].setStatus(true);
        field[lengthAbs / 2][breadthAbs / 2 - 3].setStatus(true);

        field[lengthAbs / 2 - 3][breadthAbs / 2 + 2].setStatus(true);
        field[lengthAbs / 2 - 2][breadthAbs / 2 + 2].setStatus(true);
        field[lengthAbs / 2 - 3][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 - 2][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 - 1][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 + 2][breadthAbs / 2 + 2].setStatus(true);
        field[lengthAbs / 2 + 2][breadthAbs / 2 + 3].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2 + 3].setStatus(true);
        field[lengthAbs / 2][breadthAbs / 2 + 3].setStatus(true);
    }

    private void setFigure1() {
        //up
        field[lengthAbs / 2 - 1][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 - 2][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 - 3][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 - 3][breadthAbs / 2].setStatus(true);
        field[lengthAbs / 2 - 3][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 - 2][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 - 1][breadthAbs / 2 + 1].setStatus(true);
        //down
        field[lengthAbs / 2 + 1][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 + 2][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 + 3][breadthAbs / 2 - 1].setStatus(true);
        field[lengthAbs / 2 + 3][breadthAbs / 2].setStatus(true);
        field[lengthAbs / 2 + 3][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 + 2][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2 + 1].setStatus(true);
    }

    private void setGliter() {
        field[lengthAbs / 2 - 1][breadthAbs / 2].setStatus(true);
        field[lengthAbs / 2][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2 + 1].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2].setStatus(true);
        field[lengthAbs / 2 + 1][breadthAbs / 2 - 1].setStatus(true);
    }

    private void setBlank() {
        for (int i = 0; i < lengthAbs; i++) {
            for (int j = 0; j < breadthAbs; j++) {
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

    public void setForm(int form) {
        this.form = form;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(form == 0) {
            paintSquareGrid(g);
        } else if(form == 1) {
            paintHexagonGrid(g);
        }

    }

    private void paintSquareGrid(Graphics g) {
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
            breadthAbs = lengthAbs = rowHt;
            for (i = 0; i < rowHt + 1; i++) {
                g.drawLine(0, i * size, width, i * size);
                g.drawLine(i * size, 0, i * size, height);
            }
        } else {
            breadthAbs = lengthAbs = rowWid;
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

    private void paintHexagonGrid(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        t =  (size / 2);			//t = s sin(30)
        r =  (int) (size * 0.8660254037844);	//r = s cos(30)
        h=2*r;

        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.black);
        // draw the rows and cols
        int rowHt = height / (h);
        int rowWid = width / (size + t);
        lengthAbs = rowHt;
        breadthAbs = rowWid;
        for(int i = 0; i < rowHt; i++) {
            for(int j = 0; j < rowWid; j++) {
                Polygon polygon;
                if(j%2 == 0) {
                    polygon = hex(j*(size + t), i*h, t, r);
                } else {
                    polygon = hex(j*(size + t), i*h+r, t, r);
                }
                g2.setColor(Color.BLACK);
                g2.drawPolygon(polygon);
                if (field[i][j].getStatus()) {
                    g2.setColor(cellColor);
                    g2.fillPolygon(polygon);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillPolygon(polygon);
                }
            }
        }
    }


    private Polygon hex(int x0, int y0, int t, int r) {
        int[] cx, cy;
        cx = new int[] {x0+t,x0+size+t,x0+size+t+t,x0+size+t,x0+t,x0};
        cy = new int[] {y0,y0,y0+r,y0+r+r,y0+r+r,y0+r};
        return new Polygon(cx, cy, 6);
    }

    private Point mouseHexagon(int xPos, int yPos) {
        Point point = new Point();
        int x = (xPos / (size+t));
        int y = (yPos - (x%2)*r)/h;

        int dx = xPos - x*(size+t);
        int dy = yPos - y*h;

        if (x%2==0) {
            if (dy > r) {
                if (dx * r /t < dy - r) {
                    x--;
                }
            }
            if (dy < r) {
                if ((t - dx)*r/t > dy ) {
                    x--;
                    y--;
                }
            }
        } else {
            if (dy > h) {
                if (dx * r/t < dy - h) {
                    x--;
                    y++;
                }
            }
            if (dy < h) {
                if ((t - dx)*r/t > dy - r) {
                    x--;
                }
            }
        }
        point.x = x;
        point.y = y;
        return point;
    }


    @Override
    public void mouseClicked(MouseEvent e) {


    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(form == 0) {
            field[e.getY() / size][e.getX() / size].setStatus(!field[e.getY() / size][e.getX() / size].getStatus());
            repaint();
        } else if (form == 1) {
            Point point = mouseHexagon(e.getX(), e.getY());
            field[point.y][point.x].setStatus(!field[point.y][point.x].getStatus());
            repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(form == 0) {
            if (!(newX == e.getX() / size) || !(newY == e.getY() / size)) {
                field[e.getY() / size][e.getX() / size].setStatus(!field[e.getY() / size][e.getX() / size].getStatus());
                newX = e.getX() / size;
                newY = e.getY() / size;
                repaint();
            }
        } else if(form == 1) {
            Point point = mouseHexagon(e.getX(), e.getY());
            if (!(newPoint.x == point.x) || !(newPoint.y == point.y)) {
                newPoint = point;
                field[newPoint.y][newPoint.x].setStatus(!field[newPoint.y][newPoint.x].getStatus());
                repaint();
            }
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
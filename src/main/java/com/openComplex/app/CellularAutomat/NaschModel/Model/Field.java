package com.openComplex.app.CellularAutomat.NaschModel.Model;

//import com.openComplex.app.mainWindow.Controller.CSVFile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class Field extends JPanel implements MouseListener {
    private int size;
    public Cell[][] field, tempField;
    private Color cellColor;
    private int rowHt, rowWid;
    private boolean initField = false;

    private int maxSpeed = 5, acc = 1, dec = 1, decDistance = 0, accDistance = 0;
    private double maxRandom = 0.1;


    public Field(int size, Color color) {
        this.cellColor = color;
        this.size = size;
        this.addMouseListener(this);
    }

    private void init() {
        field = new Cell[rowHt][rowWid];
        tempField = new Cell[rowHt][rowWid];
        for (int i = 0; i < rowHt; i++) {
            for (int j = 0; j < rowWid; j++) {
                field[i][j] = new Cell(false, Color.BLACK);
                tempField[i][j] = new Cell(false, Color.BLACK);
            }
        }
    }

    public void startCondition(int percent) {
        double random;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                random = Math.random();
                if (random < (double) percent / 100) {
                    field[i][j].setStatus(true);
                    field[i][j].setSpeed((int) random * 5);
                }
            }
        }
        repaint();
    }

    public void resetField() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j].setStatus(false);
            }
        }
    }

    public void applyRules() {

        int distance;
        int speed;
        double random;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (field[i][j].getStatus()) {
                    random = Math.random();

                    distance = countDistance(i, j);
                    speed = field[i][j].getSpeed();
                    if (speed + acc < distance + accDistance) {
                        if (speed + acc <= maxSpeed) {
                            speed = speed + acc;
                        } else {
                            speed = maxSpeed;
                        }
                    }

                    /*
                    if(speed < maxSpeed && speed + 1 < distance) {
                        speed++;
                    }
                    */

                    if (speed > 0 && distance + decDistance < speed) {
                        if (distance - acc >= 0) {
                            speed = distance - acc;
                        } else
                            speed = 0;
                    }

                    /*
                    if(speed > 0 && distance < speed) {
                        if(distance > 0) {
                            speed = distance - 1;
                        } else {
                            speed = 0;
                        }
                    }
                    */
                    if (speed > 0 && random < maxRandom) {
                        speed--;
                    }

                    if (j + speed < rowWid) {
                        tempField[i][j + speed].setStatus(true);
                        tempField[i][j + speed].setSpeed(speed);

                    } else {
                        tempField[i][j + speed - rowWid].setStatus(true);
                        tempField[i][j + speed - rowWid].setSpeed(speed);
                    }
                }
            }
        }
        for (int i = 0; i < tempField.length; i++) {
            for (int j = 0; j < tempField[0].length; j++) {
                field[i][j].setStatus(tempField[i][j].getStatus());
                field[i][j].setSpeed(tempField[i][j].getSpeed());
                tempField[i][j].setStatus(false);
            }
        }

        repaint();
    }

    private int countDistance(int i, int j) {

        int distance = 0;
        for (int k = j + 1; k <= j + 2 * maxSpeed; k++) {
            if (k < rowWid && !field[i][k].getStatus()) {
                distance = distance + 1;
            } else if (k >= rowWid && !field[i][k - rowWid].getStatus()) {
                distance = distance + 1;
            } else {
                return distance;
            }
        }
        return distance;
    }

    /*public void saveField() throws IOException {
        CSVFile.saveField(field, Field.this);
    }

    public int loadField() throws IOException {
        resetField();
        int temp = CSVFile.loadField(field, Field.this);
        repaint();
        return temp;
    }
    */

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        paintGrid(g);

        if (!initField) {
            init();
            initField = true;
        }

        if (initField) {
            fillGrid(g);
        }
    }

    private void paintGrid(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        int i;
        int width = getWidth();
        int height = getHeight();

        rowHt = height / (size);
        rowWid = width / (size);

        g.setColor(Color.black);

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
    }

    private void fillGrid(Graphics g) {
        g.setColor(cellColor);
        int i;
        for (i = 0; i < rowHt; i++) {
            for (int j = 0; j < rowWid; j++) {
                if (field[i][j].getStatus()) {
                    drawArrow(j * size, i * size + size / 2, j * size + size, i * size + size / 2, g);
                    //g.fillRect(j * size, i * size, size, size);
                }
            }
        }
    }

    public void drawArrow(int startX, int startY, int stopX, int stopY, Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(startX, startY, stopX, stopY);

        double l = 5.0;

        double a = Math.PI / 4 - Math.atan2((stopY - startY), (stopX - startX));
        double c = Math.cos(a) * l;
        double s = Math.sin(a) * l;
        g2d.drawLine(stopX, stopY, (int) (stopX - s), (int) (stopY - c));
        g2d.drawLine(stopX, stopY, (int) (stopX - c), (int) (stopY + s));
    }

    public void setRandom(double random) {
        maxRandom = random;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setAcc(int acc) {
        this.acc = acc;
    }

    public void setDec(int dec) {
        this.dec = dec;
    }

    public void setAccDistance(int accDistance) {
        this.accDistance = accDistance;
    }

    public void setDecDistance(int decDistance) {
        this.decDistance = decDistance;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getY() / size < rowHt) {
            field[e.getY() / size][(e.getX() / size)].setStatus(!field[e.getY() / size][(e.getX() / size)].getStatus());
            repaint();
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

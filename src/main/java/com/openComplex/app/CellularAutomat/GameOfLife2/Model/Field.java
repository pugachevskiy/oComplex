package com.openComplex.app.CellularAutomat.GameOfLife2.Model;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 10/09/15.
 */
public class Field extends JPanel {
    private int size = 30;
    private Cell[][] field = new Cell[200][200];

    public Field() {
        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                field[i][j] = new Cell(i, j, false, Color.WHITE);
            }
        }
        setTaube();
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

}

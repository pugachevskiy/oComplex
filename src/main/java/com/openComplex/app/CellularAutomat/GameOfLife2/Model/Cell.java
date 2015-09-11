package com.openComplex.app.CellularAutomat.GameOfLife2.Model;

import java.awt.*;


/**
 * Created by strange on 10/09/15.
 */
public class Cell{
    private int row;
    private int col;
    private boolean status;
    private Color color;
    private int neighbors;

    public Cell(int row, int col, boolean status, Color color) {
        this.row = row;
        this.col = col;
        this.status = status;
        this.color = color;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(int neighbors) {
        this.neighbors = neighbors;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        this.setColor(Color.BLACK);
    }

    public void addNeighbors(){
        this.neighbors++;
    }
}

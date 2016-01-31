package com.openComplex.app.CellularAutomat.NaschModel.Model;

import java.awt.*;


public class Cell {
    private boolean status;
    private int speed;
    private Color color;

    public Cell(boolean status, Color color) {
        this.status = status;
        this.color = color;
        speed = 0;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (status) {
            this.color = color;
        } else {
            this.color = Color.WHITE;
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
        if (!status) {
            speed = 0;
        }
    }
}

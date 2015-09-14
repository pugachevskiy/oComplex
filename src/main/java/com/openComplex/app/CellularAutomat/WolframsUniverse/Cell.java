package com.openComplex.app.CellularAutomat.WolframsUniverse;

import java.awt.*;


/**
 * Created by strange on 10/09/15.
 */
public class Cell {
    private boolean status;
    private Color color;

    public Cell(boolean status, Color color) {
        this.status = status;
        this.color = color;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}

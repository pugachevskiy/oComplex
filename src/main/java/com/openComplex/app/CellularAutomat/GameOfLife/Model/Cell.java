package com.openComplex.app.CellularAutomat.GameOfLife.Model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class Cell extends JComponent {
    private boolean cell;
    private int size;
    private Color color = Color.DARK_GRAY;

    public final static int GROSS = 30;
    public final static int MITTE = 20;
    public final static int KLEIN = 10;

    public Cell(int size) {
        this.size = size;
        this.cell = false;
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
    }

    public void get_to_life(){
         cell = !cell;
     }

    public void setColor(Color color){
        this.color = color;
    }
    public void setColor(int index) {
        switch (index) {
            case 0:
                this.color = Color.BLACK;
                break;
            case 1:
                this.color = Color.BLUE;
                break;
            case 2:
                this.color = Color.GREEN;
                break;
            case 3:
                this.color = Color.YELLOW;
                break;
            default:
                this.color = Color.BLACK;
                break;
        }

    }

    public boolean getCell(){
        return cell;
    }
    public void setCell(boolean cell){
        this.cell = cell;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }



}


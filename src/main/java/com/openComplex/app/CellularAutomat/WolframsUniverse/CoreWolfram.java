package com.openComplex.app.CellularAutomat.WolframsUniverse;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Matthias on 22.06.2015.
 */
public class CoreWolfram extends JComponent{

    private boolean cell;
    private int size;
    private Color color = Color.GRAY;

    public CoreWolfram(){
        this.cell = false;

    }
    public boolean getCell(){
        return cell;
    }
    public void setCell(boolean cell){
        this.cell = cell;
        if (cell){
            color = Color.BLACK;
        } else {
            color = Color.GRAY;
        }
    }
    public void changeCell(CoreWolfram cell){
        cell.setCell(!cell.getCell());
        setColor(cell);
        this.repaint();

    }
    private void setColor(CoreWolfram cell){
        if(cell.getCell()){
            color = Color.BLACK;
        } else {
            color = Color.GRAY;
        }
    }
    public void addCell(JPanel panel){
        panel.add(this);
    }
    public void setSize(int size){
        this.size = size;

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, size, size);
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(size, size);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(size, size);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(size, size);
    }
}

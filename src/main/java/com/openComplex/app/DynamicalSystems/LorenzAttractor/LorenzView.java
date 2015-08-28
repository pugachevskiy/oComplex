package com.openComplex.app.DynamicalSystems.LorenzAttractor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 26/08/15.
 */
public class LorenzView extends JPanel{
    private JFrame frame;
    private Color color;
    private int translate;
    private int[] xArray, zArray;

    public  void init(){
        frame = new JFrame("Lorenz Attractor");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        frame.setSize(new Dimension(700, 400));
        frame.add(this);
        frame.setVisible(true);
        xArray = new int[50001];
        zArray = new int[50001];
    }

    public void draw(LorenzModel lorenz, Color color, int i, int translate){

        xArray[i] = (int) (lorenz.getX1()*4);
        zArray[i] = (int) (lorenz.getZ1()*4);
        this.color = color;
        this.translate = translate;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(20, 220, 20, 350);
        g.drawLine(20, 350, 360, 350);

        g.drawString("Y", 25, 230);
        g.drawString("X", 350, 346);
        g.translate(translate,translate);
        int nPoint = 50000;
        g.setColor(color);
        g.drawPolyline(xArray, zArray, nPoint);


    }
}



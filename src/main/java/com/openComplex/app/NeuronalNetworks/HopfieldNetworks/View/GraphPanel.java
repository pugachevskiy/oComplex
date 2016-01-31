package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.View;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Controller.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * on 16.06.2015.
 */
public class GraphPanel extends JPanel {

    //public Color lightColor = Color.WHITE, darkColor = new JPanel().getBackground(), fontColor = Color.GREEN;
    public static int steps = 0;
    public int selectedNode = -1;
    private final int size = 80;
    private int clickedX, clickedY;
    public int radius;
    public Dimension centerCircle;
    private String[][] valueArray = new String[15][15];


    public void paintComponent(Graphics g) {
        updateValueArray(Main.gui.tablePanel.getTextfieldValues());

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //Setup various options
        g2.setStroke(new BasicStroke(4));
        g.setColor(Color.black);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));

        centerCircle = new Dimension(this.getWidth() / 2 - 50, this.getHeight() / 2 - 50);
        radius = Math.min(this.getWidth(), this.getHeight()) / 2 - 70;
        int x = 0, y = 0, x2, y2;

        //Draw connections first
        for (int i = 1; i <= steps; i++) {
            x = calculateXCoord(i);
            y = calculateYCoord(i);


            for (int j = 1; j <= i; j++) {
                g.setColor(Color.BLACK);
                if (i == selectedNode || j == selectedNode) {
                    g.setColor(Color.ORANGE);
                }
                x2 = calculateXCoord(j);
                y2 = calculateYCoord(j);
                //g.drawLine(x+40, y+40, x2+40, y2+40);
                //drawShape()
                ((Graphics2D) g).draw(new Line2D.Double(x + 40, y + 40, x2 + 40, y2 + 40));
            }
            g.setColor(Color.BLACK);
        }

        //Draws neurons and caption
        for (int i = 1; i <= steps; i++) {
            g.setColor(i == selectedNode ? Color.ORANGE : Color.BLACK);
            x = calculateXCoord(i);
            y = calculateYCoord(i);
            g.fillOval(x, y, size, size);
            g.setColor(Color.CYAN);
            g.drawString("N" + i, x + 30, y + 45);
        }

        g.setColor(Color.RED);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 25));
        x = (int) (centerCircle.width + Math.cos((((2 * Math.PI) / Main.size) * selectedNode)) * radius);
        y = (int) (centerCircle.height + Math.sin((((2 * Math.PI) / Main.size) * selectedNode)) * radius);
        //Draw weights, if specific neuron is selected
        if (!(selectedNode == -1)) {
            for (int i = 1; i <= Main.size; i++) {
                if (i == selectedNode)
                    continue;
                x2 = calculateXCoord(i);
                y2 = calculateYCoord(i);
                g.drawString(valueArray[selectedNode - 1][i - 1], (x + 40 + x2 + 40) / 2, (y + 40 + y2 + 40) / 2);
            }

        }

        //g.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
        // RenderingHints.VALUE_ANTIALIAS_ON );

    }

    private int calculateXCoord(int node) {
        return (int) (centerCircle.width + Math.cos((((2 * Math.PI) / Main.size) * node)) * radius);
    }

    private int calculateYCoord(int node) {
        return (int) (centerCircle.height + Math.sin((((2 * Math.PI) / Main.size) * node)) * radius);
    }

    /**
     * Calculates, wether given point is inside one of the node-circles
     *
     * @param x
     * @param y
     */
    public void updateClicks(int x, int y) {
        clickedX = x;
        clickedY = y;
        boolean isInside = false;
        int centerX, centerY;
        for (int i = 1; i <= Main.size; i++) {
            centerX = calculateXCoord(i);
            centerY = calculateYCoord(i);

            if (size > Math.sqrt(quadrat(clickedX - centerX) + quadrat(clickedY - centerY))) {
                selectedNode = i;
                repaint();
                isInside = true;
            }
        }
        if (!isInside) {
            selectedNode = -1;
            repaint();
        }
    }

    private static double quadrat(double x) {
        return x * x;
    }

    /**
     * Updates to valueArray with the values form the given matrix
     */
    public void updateValueArray(String[][] matrix) {
        for (int i = 0; i < Main.size; i++) {
            for (int j = 0; j < Main.size; j++) {
                valueArray[i][j] = matrix[i][j];
            }
        }
    }
}

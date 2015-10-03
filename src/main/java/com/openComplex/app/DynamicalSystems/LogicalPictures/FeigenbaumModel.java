package com.openComplex.app.DynamicalSystems.LogicalPictures;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created by strange on 01/09/15.
 */
public class FeigenbaumModel extends JPanel {
    public double r[] = new double[401], x[] = new double[401];
    public double rmin = 0.0, rmax = 4.0, xmin = 0.0, xmax = 1.0;
    public int nIter = 0;
    public int iters = 0;
    public boolean myGo = false;

    public void initCoord(List<JTextField> textFieldList) {
        getValues(textFieldList);
        for (int i = 1; i < 401; i++) {
            r[i] = rmin + i * (rmax - rmin) / 400.0;
            x[i] = 0.5;
            for (int iter = 1; iter < 101; iter++) {
                x[i] = r[i] * x[i] * (1.0 - x[i]);
            }
        }
        nIter = 0;
    }

    public void getValues(List<JTextField> textFieldList) {
        xmin = Double.valueOf(textFieldList.get(0).getText());
        xmax = Double.valueOf(textFieldList.get(1).getText());
        rmin = Double.valueOf(textFieldList.get(2).getText());
        rmax = Double.valueOf(textFieldList.get(3).getText());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 300);
        if (myGo) {
            iters++;
            g.setColor(Color.RED);
            if (iters < 101) {
                for (int i = 1; i < 401; i++) {
                    x[i] = r[i] * x[i] * (1.0 - x[i]);
                    int xpixel = (int) (300.0 * (1.0 - (x[i] - xmin) / (xmax - xmin)) - 1);
                    if (xpixel < 301) g.drawLine(i, xpixel, i, xpixel);
                }
                g.setColor(Color.black);
                g.fillRect(0, 0, 40, 20);
                g.setColor(Color.red);
                g.setFont(new Font("Helvetica", Font.PLAIN, 12));
                g.drawString("" + (nIter + iters), 2, 18);
            } else {
                nIter = nIter + iters - 1;
                iters = 0;
                myGo = false;
            }
        }
    }
}

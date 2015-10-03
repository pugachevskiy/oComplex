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
    public double rminh = 0.0, rmaxh = 4.0, xminh = 0.0, xmaxh = 1.0;
    public int nIter = 0;
    public int xdown, ydown, xup, yup;
    public int iters = 0;
    public boolean myGo = false;

    //public boolean eightColors = false;
    public Color colorS[] = {Color.green, Color.red, Color.blue, Color.cyan, Color.pink,
            Color.white, Color.yellow, Color.magenta};
    int iColor = 0;

    public FeigenbaumModel() {

    }

    public void generateCoordinate(List<JTextField> textFieldList) {
        int xlower = xdown;
        if (xup < xlower) xlower = xup;
        int xhigher = xup;
        if (xdown > xhigher) xhigher = xdown;
        int ylower = ydown;
        if (yup < ylower) ylower = yup;
        int yhigher = yup;
        if (ydown > yhigher) yhigher = ydown;
        rmin = rminh + (rmaxh - rminh) * xlower / 400.;
        rmax = rminh + (rmaxh - rminh) * xhigher / 400.;
        xmax = xmaxh - (xmaxh - xminh) * ylower / 300.;
        xmin = xmaxh - (xmaxh - xminh) * yhigher / 300.;
        textFieldList.get(0).setText(outString("" + xmin, 8));
        textFieldList.get(1).setText(outString("" + xmax, 8));
        textFieldList.get(2).setText(outString("" + rmin, 8));
        textFieldList.get(3).setText(outString("" + rmax, 8));
    }

    public String outString(String inString, int l) {    // returns the first l chars of string
        int ls = Math.min(inString.length(), l);
        return inString.substring(0, ls);
    }


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

    public void update() {

        this.repaint();
        //  g.drawImage(offScreenImage, 0, 0, this);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("12");
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, 400, 300);
        if (myGo) {
            System.out.println("1");
            // color for iteration points
            iters++;
            g.setColor(Color.BLUE);
           // if (checkBox.isSelected()) iColor++;
            if (iColor == 8) iColor = 0;

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
                //    thePressedKey = " ";
                nIter = nIter + iters - 1;
                iters = 0;
                myGo = false;
                //stop();
            }
        }
       // this.repaint();
    }
   /* public void paint(JPanel panel, JCheckBox checkBox) {

        Graphics offg = panel.getGraphics();
       /* if (thePressedKey.equalsIgnoreCase("n")) {
            rmin = 0.0;
            rmax = 4.0;
            xmin = 0.0;
            xmax = 1.0;
            thePressedKey = "c";
            initCoord();
        }
        if (thePressedKey.equalsIgnoreCase("c")) {
            thePressedKey = "p";
            offg.setColor(Color.black);                // drawing field color
            offg.fillRect(0, 0, 400, 300);
            nIter = 0;
        }
        if (myGo) {
            // color for iteration points
            iters++;
            offg.setColor(colorS[iColor]);
            if (checkBox.isSelected()) iColor++;
            if (iColor == 8) iColor = 0;

            if (iters < 101) {
                for (int i = 1; i < 401; i++) {
                    x[i] = r[i] * x[i] * (1.0 - x[i]);
                    int xpixel = (int) (300.0 * (1.0 - (x[i] - xmin) / (xmax - xmin)) - 1);
                    if (xpixel < 301) offg.drawLine(i, xpixel, i, xpixel);
                }
                offg.setColor(Color.black);
                offg.fillRect(0, 0, 40, 20);
                offg.setColor(Color.red);
                offg.setFont(new Font("Helvetica", Font.PLAIN, 12));
                offg.drawString("" + (nIter + iters), 2, 18);
            } else {
            //    thePressedKey = " ";
                nIter = nIter + iters - 1;
                iters = 0;
                myGo = false;
                //stop();
            }
        }
        panel.repaint();
    }*/
}

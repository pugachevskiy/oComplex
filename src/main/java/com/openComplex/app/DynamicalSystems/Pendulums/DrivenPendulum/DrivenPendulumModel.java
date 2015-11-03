package com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;
import java.awt.List;
import java.util.*;

/**
 *  on 09/10/15.
 */
public class DrivenPendulumModel extends PendulumsModel {

    private int px, py, pa; //pixelcoordinates
    private int step = 0;

    private java.util.List<Integer> attractorList1X = new LinkedList<Integer>();
    private java.util.List<Integer> attractorList1Y = new LinkedList<Integer>();
    private int queueIndex = 0;
    private static int attractorLength = 1000;
    private static int attractorSize = 2;
    private static Color attractor1Color = new Color(200, 200, 200);


    public DrivenPendulumModel() {
        startwert[0] = 0.5; //amp
        startwert[1] = 3.0; //freq
        startwert_reib = 0; //reib
    }

    @Override
    public void pixels()  //method for calculating pixelcoords
    {
        pa = (int) Math.round((double) (Lx / 2) + 100 * a);
        px = (int) Math.round((double) (pa) + 100 * Math.sin(phi));
        py = (int) Math.round((double) (Ly / 2) + 100 * Math.cos(phi));
    }

    @Override
    public void update() {
        treibwerte(step); //new a, ap, aforce
        runge_step_phi(); //Runge-Kutta
        phi = phi + (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6; //Runge-Kutta
        omega = omega + (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6; //Runge-Kutta
        this.pixels();
        repaint();
    }

    public void setStep(int step) {
        this.step = step;
    }

    public void ampPlus() {
        startwert[0] += 0.1;
        startwerteDriven();
        treibwerte(step);
        this.pixels();
        repaint();
    }

    public void ampMinus() {
        if (startwert[0] > 0.02) {
            startwert[0] -= 0.1;
            startwerteDriven();
            treibwerte(step);
        }
        this.pixels();
        repaint();
    }

    public void freqPlus() {
        if (Math.round(startwert[0] * startwert[1]) < 3) {
            startwert[1] += 1.;
            startwerteDriven();
            treibwerte(step);
            this.pixels(); //calculate pixelcoords
        }
    }

    public void freqMinus() {
        if (startwert[1] > 0) {
            startwert[1] -= 1.;
            startwerteDriven();
            treibwerte(step);
            this.pixels(); //calculate pixelcoords
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Lx, Ly + 50);
        g.setColor(Color.blue);
        g.drawString("Energy:", 25, Ly + 20);
        g.drawString("" + (double) Math.round(10 * energyDriven()) / 10, 25, Ly + 40);
        g.drawString("Friction:", 105, Ly + 20);
        g.drawString("" + reib, 105, Ly + 40);
        g.drawString("a:", 195, Ly + 20);
        g.drawString("" + (double) Math.round(100 * a) / 100, 195, Ly + 40);
        g.drawString("Amplitude:", 265, Ly + 20);
        g.drawString("" + (double) Math.round(100 * amp) / 100, 265, Ly + 40);
        g.drawString("Frequency:", 355, Ly + 20);
        g.drawString("" + (double) Math.round(100 * freq) / 100, 355, Ly + 40);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        g.setColor(Color.black);
        g.drawString("Step " + step, 20, 10);
        g.setColor(Color.red);
        g.drawLine(pa, Ly / 2, px, py);

        int attractor1X = px;
        int attractor1Y = py;
        attractorList1X.add(attractor1X);
        attractorList1Y.add(attractor1Y);
        if(queueIndex <= attractorLength) {
            queueIndex += 1;
        }
        for(int i = 0; i < attractorLength; i++) {
            if(queueIndex > i) {
                g.setColor(attractor1Color);
                g.fillOval(attractorList1X.get(i), attractorList1Y.get(i), attractorSize, attractorSize);
            }
        }
        if(queueIndex >= attractorLength) {
            attractorList1X.remove(0);
            attractorList1Y.remove(0);
        }

        g.setColor(Color.black);
        g.fillOval(px - 7, py - 7, 15, 15);
        g.fillRect(pa - 4, Ly / 2 - 1, 9, 3);
    }

    public void resetAttractor() {
        attractorList1X.removeAll(attractorList1X);
        attractorList1Y.removeAll(attractorList1Y);
        queueIndex = 0;
    }
}

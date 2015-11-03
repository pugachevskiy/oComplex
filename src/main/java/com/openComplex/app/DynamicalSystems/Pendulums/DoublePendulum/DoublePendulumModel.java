package com.openComplex.app.DynamicalSystems.Pendulums.DoublePendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 *  on 08/10/15.
 */
public class DoublePendulumModel extends PendulumsModel {
    private List<Integer> attractorList1X = new LinkedList<Integer>();
    private List<Integer> attractorList1Y = new LinkedList<Integer>();
    private List<Integer> attractorList2X = new LinkedList<Integer>();
    private List<Integer> attractorList2Y = new LinkedList<Integer>();
    private int queueIndex = 0;
    private static int attractorLength = 1000;
    private static int attractorSize = 2;
    private static Color attractor1Color = new Color(200, 200, 200);
    private static Color attractor2Color = new Color(150, 150, 150);

    public DoublePendulumModel() {
        startwert[0] = Math.PI / 180 * 170.; //phi1
        startwert[1] = Math.PI / 180 * 130.; //phi2
        startwert[2] = 1.0; //m1
        startwert[3] = 1.0; //m2
        startwert_reib = 0; //reib
    }

    @Override
    public void update() {
        runge_step_phi1(); //Runge-Kutta
        runge_step_phi2(); //Runge-Kutta
        phi1 = phi1 + (k1[0] + 2 * k1[1] + 2 * k1[2] + k1[3]) / 6; //Runge-Kutta
        omega1 = omega1 + (l1[0] + 2 * l1[1] + 2 * l1[2] + l1[3]) / 6; //Runge-Kutta
        phi2 = phi2 + (k2[0] + 2 * k2[1] + 2 * k2[2] + k2[3]) / 6; //Runge-Kutta
        omega2 = omega2 + (l2[0] + 2 * l2[1] + 2 * l2[2] + l2[3]) / 6; //Runge-Kutta
        pixels();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.white);
        g.fillRect(0, 0, Lx, Ly + 50);
        g.setColor(Color.black);
        g.drawString("Energy:", 30, Ly + 20);
        g.drawString("" + (double) Math.round(10 * energy()) / 10, 30, Ly + 40);
        g.drawString("Friction:", 120, Ly + 20);
        g.drawString("" + reib, 120, Ly + 40);
        g.drawString("m1:", 210, Ly + 20);
        g.drawString("" + (double) Math.round(100 * m1) / 100, 210, Ly + 40);
        g.drawString("m2:", 270, Ly + 20);
        g.drawString("" + (double) Math.round(100 * m2) / 100, 270, Ly + 40);
        g.drawString("Phi1:", 350, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi1 * 180 / Math.PI) / 10 + "o", 350, Ly + 40);
        g.drawString("Phi2:", 420, Ly + 20);
        g.drawString("" + (double) Math.round(10 * phi2 * 180 / Math.PI) / 10 + "o", 420, Ly + 40);
        g.setColor(Color.black);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        //g.drawString("Step " + step, 20, 10);
        g.setColor(Color.red);
        g.drawLine(Lx / 2, Ly / 2, px1, py1);
        g.drawLine(px1, py1, px2, py2);

        int attractor1X = (int) (px1);
        int attractor1Y = (int) (py1);
        int attractor2X = (int) (px2);
        int attractor2Y = (int) (py2);
        attractorList1X.add(attractor1X);
        attractorList1Y.add(attractor1Y);
        attractorList2X.add(attractor2X);
        attractorList2Y.add(attractor2Y);
        if(queueIndex <= attractorLength) {
            queueIndex += 1;
        }
        for(int i = 0; i < attractorLength; i++) {
            if(queueIndex > i) {
                g.setColor(attractor1Color);
                g.fillOval(attractorList1X.get(i), attractorList1Y.get(i), attractorSize, attractorSize);
                g.setColor(attractor2Color);
                g.fillOval(attractorList2X.get(i), attractorList2Y.get(i), attractorSize, attractorSize);
            }
        }
        if(queueIndex >= attractorLength) {
            attractorList1X.remove(0);
            attractorList1Y.remove(0);
            attractorList2X.remove(0);
            attractorList2Y.remove(0);
        }

        g.setColor(Color.black);
        g.fillOval((int) (px1 - 7 * Math.pow(m1, 1. / 3.)), (int) (py1 - 7 * Math.pow(m1, 1. / 3.)),
                +(int) (15 * Math.pow(m1, 1. / 3.)), (int) (15 * Math.pow(m1, 1. / 3.)));
        g.fillOval((int) (px2 - 7 * Math.pow(m2, 1. / 3.)), (int) (py2 - 7 * Math.pow(m2, 1. / 3.)),
                +(int) (15 * Math.pow(m2, 1. / 3.)), (int) (15 * Math.pow(m2, 1. / 3.)));
    } //paint()

    public void resetAttractor() {
        attractorList1X.removeAll(attractorList1X);
        attractorList1Y.removeAll(attractorList1Y);
        attractorList2X.removeAll(attractorList2X);
        attractorList2Y.removeAll(attractorList2Y);
        queueIndex = 0;
    }
}

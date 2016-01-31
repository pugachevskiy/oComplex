package com.openComplex.app.DynamicalSystems.NBodySimulations.GasIn2DimBox;

import javax.swing.*;
import java.awt.*;

/**
 * on 15/10/15.
 */
public class Model extends JPanel {
    private static final int D = 256;
    private int N = 30;                      //initial number of particles
    private static final double dt = 0.0013; //timestep
    private double x[] = new double[100];    //particle positions
    private double y[] = new double[100];    //particle positions
    private double vx[] = new double[100];   //particle velocities
    private double vy[] = new double[100];   //particle velocities
    private double r = 0.0;                  //distance between particles
    private static final double r0 = 0.001;  //Yukawa potential parameter
    private double pressure = 0.0;           //pressure
    private int v_field[] = new int[50];     //needed for Maxwell-distrib.
    private int step = 0;
    private int maxstep = 1000000;           //run-time
    private int speed = 50;                  //speed for painting

    public void update() {
        double force[] = new double[2];
        double forcesum_x, forcesum_y;
        double pulse = 0.0;
        for (int i = 0; i < N; i++) //Euler-step for every particle
        {
            x[i] = x[i] + vx[i] * dt;
            y[i] = y[i] + vy[i] * dt;
            forcesum_x = 0.0;
            forcesum_y = 0.0;
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    r = Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]));
                    if (r < 0.1) {
                        force = Force(x[i], y[i], x[j], y[j]);
                        forcesum_x = forcesum_x + force[0];
                        forcesum_y = forcesum_y + force[1];
                    }
                }
            }
            vx[i] = vx[i] + forcesum_x * dt;
            vy[i] = vy[i] + forcesum_y * dt;
        }
        for (int i = 0; i < N; i++) //boundary conditions
        {
            if (x[i] < 0.01) {
                vx[i] = -vx[i];
                pulse = pulse + vx[i];
            }
            if (x[i] > 0.99) {
                vx[i] = -vx[i];
                pulse = pulse - vx[i];
            }
            if (y[i] < 0.01) {
                vy[i] = -vy[i];
                pulse = pulse + vy[i];
            }
            if (y[i] > 0.99) {
                vy[i] = -vy[i];
                pulse = pulse - vy[i];
            }
        }
        if (step % 5000 == 0) //evaluate pressure
        {
            pressure = pulse / (5000 * dt);
            pulse = 0.0;
        }
        PDF_velocity();
        repaint();
    }

    public void reset() { //method for setting initial values
        step = 0;
        for (int i = 0; i < 50; i++) {
            v_field[i] = 0;
        } //clear field
        boolean reset_not_finished = true;
        double r; //particle distance
        int counter;
        while (reset_not_finished) {
            counter = 0;
            for (int i = 0; i < N; i++) //set initial particle pos. and vel.
            {
                x[i] = 0.96 * Math.random() + 0.02;
                y[i] = 0.96 * Math.random() + 0.02;
                vx[i] = 0.1 * (Math.random() - 0.5);
                vy[i] = 0.1 * (Math.random() - 0.5);
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (i != j) {
                        r = Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]));
                        if (r < 0.01) continue;
                        else counter++; //minimal distance
                    }
                }
            }
            if (counter == N * N - N) reset_not_finished = false; //all distances are ok
        }
    }//reset()

    public double[] Force(double x1, double y1, double x2, double y2) {
        double force[] = new double[2];
        double dummy = -1.0 / (r * r) * Math.exp(-r / r0) * (1.0 / r + 1.0 / r0);
        force[0] = dummy * (x2 - x1);
        force[1] = dummy * (y2 - y1);
        return force;
    }

    public float Energy() {
        float U = 0; //potential energy
        float T = 0; //kinetic energy
        float r;     //particle distance
        for (int i = 0; i < N; i++) {
            T = T + (float) (0.5 * 1.0 * (vx[i] * vx[i] + vy[i] * vy[i]));
            for (int j = 0; j < N; j++) {
                if (i != j) {
                    r = (float) Math.sqrt((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j]) * (y[i] - y[j]));
                    U = U + (float) (1.0 * 1.0 / r * Math.exp(-r / r0)); //Yukawa potential sum
                }
            }
        }
        return T + U;
    }

    public void PDF_velocity() {
        double v;
        for (int i = 0; i < N; i++) {
            v = Math.sqrt(vx[i] * vx[i] + vy[i] * vy[i]);
            for (int j = 0; j < 50; j++) {
                if ((v >= j * 0.0019) && (v < (j + 1) * 0.0019)) v_field[j]++;
            }
        }
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }


    public void setN(int N) {
        this.N = N;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.white);
        g.fillRect(0, 0, D, D);
        g.setColor(Color.black);
        g.drawRect(0, 0, D - 1, D - 1);
        for (int i = 0; i < N; i++) {
            g.fillOval((int) (D * x[i] - 4), (int) (D * y[i] - 4), 7, 7); //draw particle
        }
        for (int i = 0; i < 50; i++) {
            g.fillRect(10 + i * 3, (int) (D - v_field[i] / 8000.), //draw Maxwell-
                    2, (int) (v_field[i] / 8000.));  //Boltzmann Distribution
        }
        g.setColor(Color.blue); //printing out values
        g.drawString("Time", D / 2 - 120, 12);
        g.drawString("" + step, D / 2 - 120, 24);
        g.drawString("Energy", D / 2 - 35, 12);
        g.drawString("" + Math.round(Energy() * 1e4) / 1e4, D / 2 - 35, 24);
        g.drawString("Pressure", D / 2 + 50, 12);
        g.drawString("" + Math.round(pressure * 1e4) / 1e4, D / 2 + 50, 24);
        g.drawString("Speed", D / 2 + 80, D - 20);
        g.drawString("" + speed, D / 2 + 80, D - 8);
        g.drawString("N", D / 2 + 50, D - 20);
        g.drawString("" + N, D / 2 + 50, D - 8);
    }
}

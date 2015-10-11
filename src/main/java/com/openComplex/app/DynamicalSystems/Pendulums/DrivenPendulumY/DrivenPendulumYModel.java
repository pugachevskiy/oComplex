package com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulumY;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;

/**
 * Created by strange on 11/10/15.
 */
public class DrivenPendulumYModel extends PendulumsModel {
    private int px, py, pa; //pixelcoordinates
    private int step;

    public DrivenPendulumYModel() {
        startwert[0] = 0.5; //amp
        startwert[1] = 1.; //freq
        startwert[2] = Math.PI / 180. * 178.; //phi
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public void update() {
        treibwerte(step); //new a, ap, aforce
        runge_step_phiY(); //Runge-Kutta
        phi = phi + (k[0] + 2 * k[1] + 2 * k[2] + k[3]) / 6; //Runge-Kutta
        omega = omega + (l[0] + 2 * l[1] + 2 * l[2] + l[3]) / 6; //Runge-Kutta
        this.pixels();
        repaint();

    }

    @Override
    public void pixels()  //method for calculating pixelcoords
    {
        pa = (int) Math.round((double) (Ly / 2) + 90 * a);
        px = (int) Math.round((double) (Lx / 2) + 90 * Math.sin(phi));
        py = (int) Math.round((double) pa + 90 * Math.cos(phi));
    }//pixels()

    public void value1() {
        startwert[0] = 0.5; //amp
        startwert[1] = 1.;  //freq
        startwert[2] = Math.PI / 180. * 178.; //phi
        startwerteDrivenY();
        treibwerte(step);
        this.pixels();
        repaint();
    }

    public void value2() {
        startwert[0] = 0.5; //amp
        startwert[1] = 15.;  //freq
        startwert[2] = Math.PI / 180. * 178.; //phi
        startwerteDrivenY();
        treibwerte(step);
        this.pixels();
        repaint();
    }

    public void value3() {
        startwert[0] = 0.1; //amp
        startwert[1] = 80.;  //freq
        startwert[2] = Math.PI / 180. * 120.; //phi
        startwerteDrivenY();
        treibwerte(step);
        this.pixels();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.white);
        g.fillRect(0, 0, Lx + 100, Ly);
        g.setColor(Color.blue);
        g.drawString("Energy:", Lx + 20, 50);
        g.drawString("" + (double) Math.round(10 * energyDrivenY()) / 10, Lx + 20, 65);
        g.drawString("Friction:", Lx + 20, Ly / 2 + 35);
        g.drawString("" + reib, Lx + 20, Ly / 2 + 50);
        g.drawString("Amplitude:", Lx + 20, Ly / 2 + 80);
        g.drawString("" + (double) Math.round(100 * amp) / 100, Lx + 20, Ly / 2 + 95);
        g.drawString("Frequency:", Lx + 20, Ly / 2 + 125);
        g.drawString("" + (double) Math.round(100 * freq) / 100, Lx + 20, Ly / 2 + 140);
        g.drawLine(0, Ly / 2, Lx, Ly / 2);
        g.drawLine(Lx / 2, 0, Lx / 2, Ly);
        g.setColor(Color.black);
        g.drawString("Step " + step, 20, 30);
        g.setColor(Color.red);
        g.drawLine(Lx / 2, pa, px, py);
        g.setColor(Color.black);
        g.fillOval(px - 7, py - 7, 15, 15);
        g.fillRect(Lx / 2 - 1, pa - 4, 3, 9);
    } //paintFrame(gr)

}

package com.openComplex.app.DynamicalSystems.Pendulums.DoublePendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsModel;

import java.awt.*;

/**
 * Created by strange on 08/10/15.
 */
public class DoublePendulumModel extends PendulumsModel {

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
}

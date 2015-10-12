package com.openComplex.app.DynamicalSystems.Fractals;

import com.jogamp.opengl.awt.GLJPanel;

/**
 * Created by laptop on 29.06.2015.
 */
public interface Fractal{

    String getDicription();
    String getDimension();
    String getFactor();
    String getCopy();
    String getName();
    GLJPanel getPanel();
}

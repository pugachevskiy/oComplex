package com.openComplex.app.DynamicalSystems.Pendulums;

import javax.swing.*;
import java.awt.*;

/**
 * Created by MatthiasFuchs on 08.03.16.
 */
public abstract class Pendulums {

    private Color attractor1Color = new Color(200, 200, 200);
    private Color attractor2Color = new Color(150, 150, 150);
    private Color attractor3Color = new Color(100, 100, 100);
    private Color attractor4Color = new Color(50, 50, 50);

    private Color[] colors = {attractor1Color, attractor2Color, attractor3Color, attractor4Color};

    public void colorChooser(int trajectory) {
        colors[trajectory]= JColorChooser.showDialog(null,
                "Farbauswahl", null);
    }

    public Color[] getColors() {
        return colors;
    }

}

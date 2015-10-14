package com.openComplex.app.DynamicalSystems.Pendulums.TriplePendulum;


import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsView;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 13/10/15.
 */
public class TriplePendulumView extends PendulumsView {

    private List<String> buttonsName = Arrays.asList("New", "Start", "Friction ++", "Friction --", "Phi1 +", "Phi1 -",
            "Phi2 +", "Phi2 -", "Phi3 +", "Phi3 -");
    private static String NAME = "Triple Pendulum";

    public TriplePendulumView(ActionListener listener) {
        init(NAME, listener, buttonsName);
    }
}

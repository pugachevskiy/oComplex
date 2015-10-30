package com.openComplex.app.DynamicalSystems.Pendulums.QuadBarPendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsView;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 *  on 13/10/15.
 */
public class QuadBarPendulumView extends PendulumsView {
    private List<String> buttonsName = Arrays.asList("New", "Start", "Friction ++", "Friction --", "Phi1 +", "Phi1 -",
            "Phi2 +", "Phi2 -", "Phi3 +", "Phi3 -", "Phi4 +", "Phi4 -");
    private static String NAME = "Quad Bar Pendulum";

    public QuadBarPendulumView(ActionListener listener) {
        init(NAME, listener, buttonsName);
    }
}

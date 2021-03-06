package com.openComplex.app.DynamicalSystems.Pendulums.TripleBarPendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsView;
import testPrjs.Trajectory;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 *  on 13/10/15.
 */
public class TripleBarPendulumView extends PendulumsView {
    private List<String> buttonsName = Arrays.asList("New", "Start", "Friction ++", "Friction --", "Phi1 +", "Phi1 -",
            "Phi2 +", "Phi2 -", "Phi3 +", "Phi3 -", "Trajectory");
    private static String NAME = "Triple Bar Pendulum";

    public TripleBarPendulumView(ActionListener listener) {
        init(NAME, listener, buttonsName);
    }
}

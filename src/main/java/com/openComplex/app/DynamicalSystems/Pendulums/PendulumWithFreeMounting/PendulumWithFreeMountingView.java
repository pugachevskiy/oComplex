package com.openComplex.app.DynamicalSystems.Pendulums.PendulumWithFreeMounting;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsView;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 *  on 12/10/15.
 */
public class PendulumWithFreeMountingView extends PendulumsView {
    private List<String> buttonsName = Arrays.asList("New", "Start", "Friction ++", "Friction --", "Phi +", "Phi -",
            "Omega +", "Omega -", "m1/m2 +", "m1/m2 -", "a +", "a -", "Trajectory");
    private static String NAME = "Pendulum with free mounting";

    public PendulumWithFreeMountingView(ActionListener listener) {
        init(NAME, listener, buttonsName);
    }
}

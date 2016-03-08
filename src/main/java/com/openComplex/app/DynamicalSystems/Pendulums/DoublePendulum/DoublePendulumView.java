package com.openComplex.app.DynamicalSystems.Pendulums.DoublePendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsView;

import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 *  on 08/10/15.
 */
public class DoublePendulumView extends PendulumsView {
    private List<String> buttonsName = Arrays.asList("New", "Start", "Phi1 +", "Phi1 -", "Phi2 +", "Phi2 -", "Friction ++",
//	private List<String> buttonsName = Arrays.asList("New", "Start", "\u03D5 1 +", "\u03D5 1 -", "\u03D5 2 +", "\u03D5 2 -", "Friction ++",
            "Friction --", "m1 +", "m1 -", "m2 +", "m2 -", "Trajectory");


    private static String NAME = "openCoSy - Double Pendulum";


    public DoublePendulumView(ActionListener listener) {

        init(NAME, listener, buttonsName);
    }

}

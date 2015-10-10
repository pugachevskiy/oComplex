package com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulum;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsView;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 09/10/15.
 */
public class DrivenPendulumView extends PendulumsView {
    private List<String> buttonsName = Arrays.asList("New", "Start", "Amp +", "Amp -", "Freq +", "Freq -", "Friction ++",
            "Friction --");


    private static String NAME = "Driven Pendulum";


    public DrivenPendulumView(ActionListener listener) {
        init(NAME, listener, buttonsName);
    }
}

package com.openComplex.app.DynamicalSystems.Pendulums.DrivenTriplePendulumY;

import com.openComplex.app.DynamicalSystems.Pendulums.PendulumsView;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.List;

/**
 *  on 11/10/15.
 */
public class DrivenTriplePendulumYView extends PendulumsView {
    private List<String> buttonsName = Arrays.asList("New", "Start", "Friction ++", "Friction --");
    private static String NAME = "Driven Triple Pendulum";

    public DrivenTriplePendulumYView(ActionListener listener, ItemListener itemListener) {
        init(NAME, listener, buttonsName);
        Choice Chooser = new Choice();
        Chooser.add("Values_1");
        Chooser.add("Values_2");
        Chooser.add("Values_3");
        Chooser.add("Values_4");
        super.pan.add(Chooser);
        Chooser.addItemListener(itemListener);
    }
}

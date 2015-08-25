package com.openComplex.app.mainWindow.View;

import com.openComplex.app.mainWindow.Listener.MFrameListener;
import com.openComplex.app.mainWindow.Model.ModelMain;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 29/05/15.
 */
public class DynamicPanelView extends JPanel {
    private ActionListener listener = new MFrameListener();
    private static final String FEIGENBAUM = "Logical pictures with Feigenbaum-diagram", ITERATED = "Iterated pictures",
        LANDAU =  "Landau symbols", FRACTALS = "Fractals";
    private  List<String> BUTTONSDYNAMICS = Arrays.asList(FEIGENBAUM, ITERATED, LANDAU,FRACTALS);

    public DynamicPanelView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Dynamical systems"));
        JButton backButton = new JButton("back");
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);

        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2, 2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        ModelMain model = new ModelMain();
        model.createButtons(topicPanel, BUTTONSDYNAMICS);

        this.add(backButton);
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(2, 1));
        lowerPanel.add(new JLabel("<html><body>A dynamical system is a concept in mathematics <br> where a fixed rule describes how a point in a geometrical space depends on time.</body></html>"));
        lowerPanel.add(topicPanel);
        this.add(lowerPanel);

    }


}

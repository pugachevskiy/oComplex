package com.openComplex.app.mainWindow.View;

import com.openComplex.app.mainWindow.Listener.MFrameListener;
import com.openComplex.app.mainWindow.Model.ModelMain;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by strange on 29/05/15.
 */
public class CellularPanelView extends JPanel {
    private ActionListener listener = new MFrameListener();
    private static final String GOL = "Conway's Game of Life", WOLFRAM = "Wolfram's universe",
            HEGELMANN = "Model of Hegelmann";
    private static final List<String> BUTTONSCELLULAR = Arrays.asList(GOL, WOLFRAM, HEGELMANN);


    public CellularPanelView() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Cellular automata"));
        JButton backButton = new JButton("back");
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);


        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2, 2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        ModelMain model = new ModelMain();
        model.createButtons(topicPanel, BUTTONSCELLULAR);

        this.add(backButton);
        this.add(new JLabel("Short description about topic."));
        this.add(topicPanel);
    }

}


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
public class GraphPanelView extends JPanel {
    private ActionListener listener = new MFrameListener();
    private static final String GRAPHS ="Graphs", SCHULLERNUMBER = "Schuller-number", TREES = "Trees",
            LITTLEWORLD = "Little World networks";
    private List<String> BUTTONGRAPH = Arrays.asList(GRAPHS, SCHULLERNUMBER, TREES, LITTLEWORLD);
    public  GraphPanelView() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Graph Theory"));
        JButton backButton = new JButton("back");
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);


        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2,2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        ModelMain model = new ModelMain();
        model.createButtons(topicPanel, BUTTONGRAPH);

        this.add(backButton);
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(2, 1));
        lowerPanel.add(new JLabel("<html><body>In mathematics and computer science, graph theory is the study of graphs, <br>which are mathematical structures used to model pairwise relations between objects. </body></html>"));
        lowerPanel.add(topicPanel);
        this.add(lowerPanel);
    }

}
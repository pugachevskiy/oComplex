package com.openComplex.app.mainWindow.View;

import com.openComplex.app.mainWindow.Listener.MFrameListener;
import com.openComplex.app.mainWindow.Model.ModelMain;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 29/05/15.
 */
public class DynamicPanelView extends JPanel {
    private ActionListener listener = new MFrameListener();
    private static final String FEIGENBAUM = "Logical pictures with Feigenbaum-diagram", ITERATED = "Lorenz Attractor",
        LANDAU =  "Landau symbols", FRACTALS = "Fractals";
    private  List<String> BUTTONSDYNAMICS = Arrays.asList(FEIGENBAUM, ITERATED, LANDAU,FRACTALS);

    public DynamicPanelView() {
        JPanel upperPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        upperPanel.setLayout(gbl);
        int i = 0;
        JButton backButton = new JButton(), hopfieldButton = new JButton("Hopfield networks");
        try {
            backButton.setIcon(new ImageIcon(ImageIO.read(new File(MainView.BACKBUTTONPICTUREPATH))));

        } catch(IOException e) {
            backButton.setText("BACK");
        }
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);
        addComponent(upperPanel, gbl, new JLabel(), i++, 0, 1, 1);
        addComponent(upperPanel, gbl, backButton, i++, 0, 1, 1);
        while(i<3) {
            addComponent(upperPanel, gbl, new JLabel("                              "), i++, 0, 1, 1);
        }
        JLabel networksLabel = new JLabel("Dynamical systems");
        networksLabel.setFont(MainView.HEADINGFONT);
        addComponent(upperPanel, gbl, networksLabel, i++, 0, 5, 1);
        while(i<6) {
            addComponent(upperPanel, gbl, new JLabel("                                      "), i++, 0, 1, 1);
        }

        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2, 2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        ModelMain model = new ModelMain();
        model.createButtons(topicPanel, BUTTONSDYNAMICS);

        this.add(upperPanel);
        JPanel lowerPanel = new JPanel();
        lowerPanel.setLayout(new GridLayout(2, 1));
        lowerPanel.add(new JLabel("<html><body>A dynamical system is a concept in mathematics <br> where a fixed rule describes how a point in a geometrical space depends on time.</body></html>"));
        lowerPanel.add(topicPanel);
        this.add(lowerPanel);

    }

    static void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
                             int height) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x; gbc.gridy = y;
        gbc.gridwidth = width; gbc.gridheight = height;
        gbc.weightx = 1; gbc.weighty = 1;
        gbl.setConstraints(c, gbc);
        cont.add(c);

    }


}

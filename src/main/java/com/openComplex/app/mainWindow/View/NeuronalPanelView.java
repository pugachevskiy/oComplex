package com.openComplex.app.mainWindow.View;

import com.openComplex.app.mainWindow.Listener.MFrameListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by strange on 29/05/15.
 */
public class NeuronalPanelView extends JPanel{
    private ActionListener listener = new MFrameListener();
    public  NeuronalPanelView() {

        //neuronalMenu.setLayout(new GridLayout(2, 1));
        JPanel upperPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        upperPanel.setLayout(gbl);
        int i = 0;
        JButton backButton = new JButton("back"), hopfieldButton = new JButton("Hopfield networks");
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);
        addComponent(upperPanel, gbl, backButton, i++, 0, 1, 1);
        while(i<3) {
            addComponent(upperPanel, gbl, new JLabel("                  "), i++, 0, 1, 1);
        }
        addComponent(upperPanel, gbl, new JLabel("Neuronal Networks"), i++, 0, 5, 1);
        while(i<6) {
            addComponent(upperPanel, gbl, new JLabel("                                     "), i++, 0, 1, 1);
        }

        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2,2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        JButton feedforwardButton = new JButton(), recurrentButton = new JButton();

        List<JButton> buttonList = Arrays.asList(feedforwardButton, recurrentButton);
        List<String> buttonStrings = Arrays.asList("Feedforward networks", "Recurrent networks");

        i = 0;
        for(JButton button : buttonList) {
            button.addActionListener(listener);
            button.setText(buttonStrings.get(i));
            button.setActionCommand(buttonStrings.get(i++));
            topicPanel.add(button);
        }


        this.add(upperPanel);
        this.add(new JLabel(""
                + "<html><body>"
                + "In machine learning and cognitive science, <br>"
                + "artificial neural networks (ANNs) are a family of statistical learning models<br>"
                + "inspired by biological neural networks (the central nervous systems of animals,<br> "
                + "in particular the brain) and are used to estimate or approximate functions <br>"
                + "that can depend on a large number of inputs and are generally unknown."
                + "</body></html>"));
        this.add(topicPanel);


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
    public void destroy(){
        this.removeAll();
    }
}

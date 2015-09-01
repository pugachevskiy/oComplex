package com.openComplex.app.mainWindow.View;

import com.openComplex.app.mainWindow.Listener.MFrameListener;
import com.openComplex.app.mainWindow.Model.ModelMain;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
            addComponent(upperPanel, gbl, new JLabel("                         "), i++, 0, 1, 1);
        }
        JLabel networksLabel = new JLabel("Cellular automata");
        networksLabel.setFont(MainView.HEADINGFONT);
        addComponent(upperPanel, gbl, networksLabel, i++, 0, 5, 1);
        while(i<6) {
            addComponent(upperPanel, gbl, new JLabel("                                        "), i++, 0, 1, 1);
        }


        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2, 2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        ModelMain model = new ModelMain();
        model.createButtons(topicPanel, BUTTONSCELLULAR);

        JTextPane informationText = new JTextPane();
        informationText.setEditable(false);
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(set, "Mono");
        StyleConstants.setFontSize(set, 12);
        //StyleConstants.setItalic(set, true);
        StyleConstants.setBold(set, true);
        informationText.setParagraphAttributes(set, true);
        informationText.setBackground(new JLabel().getBackground());
        informationText.setBounds(100, 100, 300, 300);
        informationText.setText("Cellular automata consist of a grid of cells.\n" +
                "For each cell, a set of cells called neighborhood is defined\n" +
                "relative to this cell. Those cells influence each other trough\n" +
                "this defined neighborhood.\n");

        this.add(upperPanel);
        this.add(informationText);
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

}


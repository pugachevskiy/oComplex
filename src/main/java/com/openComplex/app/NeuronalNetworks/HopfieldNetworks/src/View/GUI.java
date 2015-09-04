package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.View;


import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller.*;

import javax.swing.*;
import java.awt.*;

/**
 * GUI-Klasse, die den Konstruktor für das Hauptframe enthält
 */

public class GUI extends JFrame {

    public JFrame frame;
    public GraphPanel graphPanel;
    public TablePanel tablePanel;

    //Fonts
    public static final Font fontVectors = new Font(Font.MONOSPACED, Font.PLAIN, 20);
    public static final Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);
    public static final Font defaultFont = new Font(Font.MONOSPACED, Font.BOLD, 16);

    /**
     * Konstruktor mit Einstellungen für das Frame
     */
    public GUI() {
        frame = new JFrame("Hopfield net");
        graphPanel = new GraphPanel();
        graphPanel.addMouseListener(new GraphListener());
        tablePanel = new TablePanel();
        frame.setDefaultLookAndFeelDecorated(false);

        frame.setLayout(new BorderLayout());
        frame.add(graphPanel, BorderLayout.CENTER);
        frame.add(tablePanel, BorderLayout.WEST);
        frame.setJMenuBar(new Menu());
        frame.setBounds(350, 300, 790, 370);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Image leftIcon = Toolkit.getDefaultToolkit().getImage("assets/clock.png");
        frame.setIconImage(leftIcon);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        Dimension screensize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(0, 0, (int) screensize.getWidth(), (int) screensize.getHeight());
        frame.setMinimumSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        frame.setMaximumSize(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getSize());
        frame.setVisible(true);
    }

    /**
     * Creates window with various information about hopfield nets
     */
    public void createHelpFrame() {
        String rules = "Hopfield nets are a sort of artificial neural networks. Such nets\n" +
                "hold only one layer, consisting of a set of nodes. This program\n" +
                "creates a graphical representation of the hopfield net based on the weight matrix.\n\n" +
                "Rules for the weight matrix: \n" +
                "-No neuron is allowed to have a link to itself(->diagonal axis = 0)\n" +
                "-The connections are undirected(->weight matrix has to be symmetrical)\n" +
                "-Alle neurons have to be connected to each other(->weights never 0)";


        JTextArea area = new JTextArea(rules);
        area.setFont(defaultFont);
        area.setBorder(BorderFactory.createEmptyBorder());
        area.setBackground(new JPanel().getBackground());
        area.setEditable(false);
        JOptionPane.showMessageDialog(frame, area, "Help", JOptionPane.PLAIN_MESSAGE);
    }


    public boolean getSelectedState() {
        return true;
    }

}

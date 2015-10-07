package com.openComplex.app.DynamicalSystems.NBodySimulations.GasIn2DimBox;

import javax.swing.*;

/**
 * Created by Tobias on 07.10.2015.
 */
public class View extends JFrame {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private GasApplet applet;


    public View() {
        mainFrame = new JFrame("SunEarthSimulation");
        mainPanel = new JPanel();
        applet = new GasApplet();

        mainPanel.add(applet);
        mainFrame.add(mainPanel);
        applet.init();

        mainFrame.setBounds(350, 300, 500, 300);
        mainFrame.setResizable(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setVisible(true);
    }






}

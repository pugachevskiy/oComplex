package com.openComplex.app.DynamicalSystems.LorenzAttractor;

/**
 * Created by strange on 04/10/15.
 */

import org.math.plot.Plot3DPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by strange on 26/08/15.
 */
public class LorenzView2 {
    private JFrame frame;
    private JPanel menuPanel;
    private double[] xArray, yArray, zArray;
    private JTextField coeffA, coeffB, coeffC;
    private JButton startButton, clearButton;
    private Plot3DPanel plot;

    public LorenzView2() {
        xArray = new double[50001];
        zArray = new double[50001];
        yArray = new double[50001];

        init();
    }

    public void init() {
        frame = new JFrame("Lorenz");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(700, 700));
        plot = new Plot3DPanel();
        frame.add(plot, BorderLayout.CENTER);
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        addMenuPanel();
        frame.add(menuPanel, BorderLayout.WEST);
        frame.setVisible(true);

    }

    private void addMenuPanel() {
        menuPanel.add(new JLabel("Сoefficients"));
        menuPanel.add(new JLabel("A"));
        coeffA = new JTextField("");
        coeffA.setPreferredSize(new Dimension(100, 40));
        menuPanel.add(coeffA);
        menuPanel.add(new JLabel("B"));
        coeffB = new JTextField("");
        coeffB.setPreferredSize(new Dimension(100, 40));
        menuPanel.add(coeffB);
        menuPanel.add(new JLabel("C"));
        coeffC = new JTextField("");
        coeffC.setPreferredSize(new Dimension(100, 40));
        menuPanel.add(coeffC);
        startButton = new JButton("Start");
        startButton.setActionCommand("Start");
        menuPanel.add(startButton);
        clearButton = new JButton("Clear");
        clearButton.setActionCommand("Clear");
        menuPanel.add(clearButton);
    }

    public void setTextA(String a) {
        coeffA.setText(a);
    }

    public void setTextB(String b) {
        coeffB.setText(b);
    }

    public void setTextC(String c) {
        coeffC.setText(c);
    }

    public double getTextA() {
        return Double.valueOf(coeffA.getText());
    }

    public double getTextB() {
        return Double.valueOf(coeffB.getText());
    }

    public double getTextC() {
        return Double.valueOf(coeffC.getText());
    }

    public void addListener(ActionListener listener) {
        startButton.addActionListener(listener);
        clearButton.addActionListener(listener);
    }

    public void draw(LorenzModel lorenz, int i) {
        xArray[i] = lorenz.getX1();
        yArray[i] = lorenz.getY1();
        zArray[i] = lorenz.getZ1();
    }

    public void clear() {
        plot.removeAll();
        plot = new Plot3DPanel();
        frame.add(plot);
        plot.revalidate();
    }

    public void doPaint() {
        plot.addLinePlot("Lorenz", xArray, yArray, zArray);
    }


    public void setStartButtonText(String text) {
        startButton.setText(text);
        startButton.setActionCommand(text);
    }
}


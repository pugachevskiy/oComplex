package com.openComplex.app.DynamicalSystems.LorenzAttractor;

/**
 * on 04/10/15.
 */

import org.math.plot.Plot3DPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class LorenzView2 {
    private JFrame frame;
    private JSlider coeffA, coeffB, coeffC;
    private JButton startButton, clearButton;
    private Plot3DPanel plot;

    int temp = 0;

    public LorenzView2() {
        init();
    }

    public void init() {
        frame = new JFrame("openCoSy - Lorenz Attraktor");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(700, 700));

        plot = new Plot3DPanel();
        frame.add(plot, BorderLayout.CENTER);

        frame.add(createSliderPanel(), BorderLayout.NORTH);
        frame.add(createStartStopPanel(), BorderLayout.SOUTH);
        frame.setVisible(true);

    }


    private JPanel createStartStopPanel() {
        JPanel startStopPanel = new JPanel();
        startStopPanel.setLayout(new GridLayout(1, 2));
        startStopPanel.setBorder(new EmptyBorder(5, 70, 5, 70));

        startButton = new JButton("Draw");
        startButton.setActionCommand("Draw");
        startStopPanel.add(startButton);
        clearButton = new JButton("Clear");
        clearButton.setActionCommand("Clear");
        startStopPanel.add(clearButton);

        return startStopPanel;
    }

    private JPanel createSliderPanel() {
        JPanel sliderPanel = new JPanel();

        sliderPanel.setLayout(new GridLayout(1, 3));
        sliderPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        sliderPanel.add(createSingleSlider(coeffA = new JSlider(-40, 40), "A"));
        sliderPanel.add(createSingleSlider(coeffB = new JSlider(-40, 40), "B"));
        sliderPanel.add(createSingleSlider(coeffC = new JSlider(-40, 40), "C"));

        return sliderPanel;
    }

    private JPanel createSingleSlider(JSlider slider, String info) {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(2, 1));
        sliderPanel.setBorder(new EmptyBorder(0, 3, 0, 3));
        JLabel sliderLabel = new JLabel(info);
        sliderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sliderPanel.add(sliderLabel);

        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        sliderPanel.add(slider);

        slider.setValue(-20);

        return sliderPanel;
    }

    public void setTextA(String a) {
        coeffA.setValue((int) Double.parseDouble(a));
    }

    public void setTextB(String b) {
        coeffB.setValue((int) Double.parseDouble(b));
    }

    public void setTextC(String c) {
        coeffC.setValue((int) Double.parseDouble(c));
    }

    public double getTextA() {
        return (double) coeffA.getValue();
    }

    public double getTextB() {
        return (double) coeffB.getValue();
    }

    public double getTextC() {
        return (double) coeffC.getValue();
    }

    public void addListener(ActionListener listener) {
        startButton.addActionListener(listener);
        clearButton.addActionListener(listener);
    }


    public void clear() {
        plot.removeAll();
        plot = new Plot3DPanel();
        frame.add(plot, BorderLayout.CENTER);
        temp = 0;
        plot.revalidate();
    }

    public void doPaint(double[] xArray, double[] yArray, double[] zArray) {
        if (temp > 0)
        plot.removePlot(0);
        plot.addLinePlot("Lorenz", xArray, yArray, zArray);
        temp++;
        plot.revalidate();
    }

    public void activateControls(){
        startButton.setEnabled(true);
        clearButton.setEnabled(true);
        coeffA.setEnabled(true);
        coeffB.setEnabled(true);
        coeffC.setEnabled(true);
    }

    public void deactivateControls(){
        startButton.setEnabled(false);
        clearButton.setEnabled(false);
        coeffA.setEnabled(false);
        coeffB.setEnabled(false);
        coeffC.setEnabled(false);
    }
}


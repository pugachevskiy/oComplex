package com.openComplex.app.DynamicalSystems.LorenzAttractor;

/**
 * on 04/10/15.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LorenzView2 {
    private JSlider coeffA, coeffB, coeffC;
    private JSlider x, y , points;
    private JButton startButton, clearButton;

    public LorenzView2() {
        init();
    }

    public void init() {
        JFrame frame = new JFrame("openCoSy - Lorenz Attraktor");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(700, 700));
        frame.add(createSliderPanel(), BorderLayout.NORTH);
        frame.add(createStartStopPanel(), BorderLayout.SOUTH);
        frame.pack();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
               StdDraw.closeFrame();
            }
        });

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

        sliderPanel.setLayout(new GridLayout(2, 3));
        sliderPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        sliderPanel.add(createSingleSlider(coeffA = new JSlider(-40, 40), "A"));
        sliderPanel.add(createSingleSlider(coeffB = new JSlider(-40, 40), "B"));
        sliderPanel.add(createSingleSlider(coeffC = new JSlider(-40, 40), "C"));

        sliderPanel.add(createSingleSlider(x = new JSlider(-25, 25), "x"));
        sliderPanel.add(createSingleSlider(y = new JSlider(0, 50), "y"));
        sliderPanel.add(createSingleSlider(points = new JSlider(1000, 101000), "Points"));
        points.setPaintLabels(false);
        points.setMajorTickSpacing(50000);
        points.setMinorTickSpacing(25000);
        points.setPaintTicks(true);
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

    public void setTextx(String a) {
        x.setValue((int) Double.parseDouble(a));
    }

    public void setTexty(String b) {
        y.setValue((int) Double.parseDouble(b));
    }

    public void setTextPoints(String c) {
        points.setValue( Integer.parseInt(c));
    }

    public double getTextx() {
        return (double) x.getValue();
    }

    public double getTexty() {
        return (double) y.getValue();
    }

    public int getTextPoints() {
        return points.getValue();
    }

    public void addListener(ActionListener listener) {
        startButton.addActionListener(listener);
        clearButton.addActionListener(listener);
    }


    public void clear() {
        StdDraw.clear(StdDraw.LIGHT_GRAY);
        StdDraw.setXscale(-25, 25);
        StdDraw.setYscale(0, 50);
    }

    public void activateControls() {
        startButton.setText("Draw");
        startButton.setActionCommand("Draw");
        clearButton.setEnabled(true);
        coeffA.setEnabled(true);
        coeffB.setEnabled(true);
        coeffC.setEnabled(true);
        x.setEnabled(true);
        y.setEnabled(true);
        points.setEnabled(true);
    }

    public void deactivateControls() {
        startButton.setText("Stop");
        startButton.setActionCommand("Stop");
        clearButton.setEnabled(false);
        coeffA.setEnabled(false);
        coeffB.setEnabled(false);
        coeffC.setEnabled(false);
        x.setEnabled(false);
        y.setEnabled(false);
        points.setEnabled(false);
    }
}


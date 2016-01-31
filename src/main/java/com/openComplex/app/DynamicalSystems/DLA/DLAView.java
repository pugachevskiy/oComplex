package com.openComplex.app.DynamicalSystems.DLA;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


/**
 * on 06/10/15.
 */
public class DLAView {
    private JFrame frame;
    private JButton buttonReset, startButton;
    private JTextField tfStep;
    private JLabel labMaxStep;
    private JPanel setupPanel;
    private static final int D = 256;
    private int maxstep = 20000; //run-time

    public DLAView() {
        init();
    }

    public void init() {

        frame = new JFrame("DLA");
        frame.setLayout(new BorderLayout());
        frame.setSize(700, 500);
        setupPanel = new JPanel();
        setupPanel.setLayout(new GridLayout(1, 6));
        setupPanel.setBounds(D + 1, 1, 90, D);
        setupPanel.setFont(new Font("Verdana", Font.PLAIN, 9));

        labMaxStep = new JLabel("Run-time");
        labMaxStep.setBounds(10, D / 2 - 60, 70, 20);

        tfStep = new JTextField("" + maxstep);
        tfStep.setBounds(10, D / 2 - 40, 70, 30);
        tfStep.setFont(new Font("Verdana", Font.PLAIN, 9));

        buttonReset = new JButton("Reset");
        buttonReset.setBounds(10, D / 2 - 5, 70, 25);
        buttonReset.setActionCommand("Reset");

        startButton = new JButton("Start/Stop");
        startButton.setActionCommand("Start");
        startButton.setBounds(10, D / 2 + 30, 80, 30);

        frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                int maxHeight = frame.getHeight() - setupPanel.getHeight();
                int maxWidth = frame.getWidth();
                JPanel tempPanel = (JPanel) frame.getContentPane().getComponent(1);
                //(DLAModel)tempPanel.updateSize(maxHeight<maxWidth ? maxHeight : maxWidth);
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }

            public void componentHidden(ComponentEvent e) {
            }
        });

        setupPanel.add(labMaxStep);
        setupPanel.add(tfStep);
        setupPanel.add(buttonReset);
        setupPanel.add(startButton);
        frame.add(setupPanel, BorderLayout.NORTH);
        frame.setVisible(true);

    }

    public void addListener(ActionListener listener) {
        buttonReset.addActionListener(listener);
        tfStep.addActionListener(listener);
        startButton.addActionListener(listener);
    }

    public int getTfStep() {
        return (int) Double.valueOf(tfStep.getText()).doubleValue();
    }

    public void addPanel(DLAModel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }
}

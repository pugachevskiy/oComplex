package com.openComplex.app.DynamicalSystems.NBodySimulations.GasIn2DimBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentListener;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tobias on 07.10.2015.
 */
public class View {
    private static final int Lx = 500, Ly = 530; //graphic window
    public JFrame frame;
    public JPanel pan;
    private JTextField textFieldStep;
    private JScrollBar numberofParticles, speedbar;
    private JLabel descriptionNumberofParticles, descriptionSpeedbar, maxStepLabel;
    private List<JButton> buttons = new LinkedList<>();
    private List<String> buttonsName = Arrays.asList("New", "Start", "Reset");
    private AdjustmentListener adjustment;

    public View(ActionListener listener, AdjustmentListener adjustmentListener) {
        this.adjustment = adjustmentListener;
        init("Gas in 2 Dimensional Box", listener, buttonsName);
    }

    public void init(String name, ActionListener listener, List<String> list) {
        frame = new JFrame(name);
        frame.setSize(1100, 450);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        pan = new JPanel();
        pan.setBounds(Lx + 1, 1, 100, Ly + 90);
        pan.setBackground(new Color(0, 200, 100));
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));

        addButtons(listener, list);
        // textFieldStep = new JTextField();
        descriptionNumberofParticles = new JLabel("N");
        descriptionSpeedbar = new JLabel("Speed");
        //maxStepLabel = new JLabel("Run-time");
        numberofParticles = new JScrollBar(Scrollbar.VERTICAL, 71, 30, 1, 130);
        numberofParticles.setName("Number");
        speedbar = new JScrollBar(Scrollbar.VERTICAL, 951, 300, 1, 1300);
        speedbar.setName("Speed");
        speedbar.addAdjustmentListener(adjustment);
        numberofParticles.addAdjustmentListener(adjustment);
//        pan.add(maxStepLabel);
        // pan.add(textFieldStep);
        pan.add(descriptionNumberofParticles);
        pan.add(numberofParticles);
        pan.add(descriptionSpeedbar);
        pan.add(speedbar);

        frame.add(pan, BorderLayout.NORTH);
        frame.setVisible(true);

    }//init()

    private void addButtons(ActionListener listener, List<String> buttonsName) {
        for (int i = 0; i < buttonsName.size(); i++) {
            this.buttons.add(new JButton(buttonsName.get(i)));
            this.buttons.get(i).setActionCommand(buttonsName.get(i));
            this.buttons.get(i).addActionListener(listener);
            pan.add(this.buttons.get(i));
        }
    }

    public void addPanel(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }
}

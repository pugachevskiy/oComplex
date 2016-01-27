package com.openComplex.app.DynamicalSystems.Oscillators.HarmonicOscillator;

import com.openComplex.app.App;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 *  on 06/10/15.
 */
public class OscillatorView extends JFrame{
    private JButton buttonGo;
    public static final int W = 400, H = 220; //graphic window
    private JPanel setupPanel;

    private static final String lableSpringString = "Spring: ", lableFrictionString = "Friction: ";
    private JLabel springLabel, frictionLabel;
    private JSlider springSlider, frictionSlider;

    public OscillatorView(){
        init();
    }

    public void init() {

        this.setTitle("Oscillators");
        this.setLayout(new BorderLayout());
        this.setSize(700, 400);

        buttonGo = new JButton("Go/Pause");
        buttonGo.setActionCommand("Go");

        App.setFrameCentral(this);
        this.setVisible(true);
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 6));
        optionsPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        optionsPanel.add(createSliderPanel(springLabel = new JLabel(lableSpringString), springSlider));
        optionsPanel.add(createSliderPanel(frictionLabel = new JLabel(lableFrictionString), frictionSlider));
        optionsPanel.add(buttonGo);
        this.updateFrictionLabel();
        this.updateSpringLabel();

        return optionsPanel;
    }

    private JPanel createSliderPanel(JLabel label, JSlider slider) {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(2, 1));
        sliderPanel.setBorder(new EmptyBorder(0, 3, 0, 3));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        sliderPanel.add(label);
        sliderPanel.add(slider);

        return sliderPanel;
    }

   /* private JPanel createButtonsPanel(JLabel label, JButton button) {
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(2, 1));
        sliderPanel.setBorder(new EmptyBorder(0, 3, 0, 3));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        sliderPanel.add(label);
        sliderPanel.add(button);

        return sliderPanel;
    }*/


    public void addPanel(JPanel panel) {
        this.add(panel, BorderLayout.CENTER);
    }

    public void addListener(ActionListener listener) {
        buttonGo.addActionListener(listener);
    }

    public int calculatePanelHeight() {
        return this.getHeight()-setupPanel.getHeight();
    }

    public void addJSlider(JSlider sliderFriction, JSlider sliderSpring) {
        this.springSlider = sliderSpring;
        this.frictionSlider = sliderFriction;
        this.add(setupPanel = createOptionsPanel(), BorderLayout.NORTH);
    }

    public void updateFrictionLabel() {
        frictionLabel.setText("Friction: " + frictionSlider.getValue());
    }

    public void updateSpringLabel() {
        springLabel.setText("Spring: " + springSlider.getValue()/4.0);
    }
}

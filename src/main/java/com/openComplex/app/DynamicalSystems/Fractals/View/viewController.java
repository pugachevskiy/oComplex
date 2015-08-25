package com.openComplex.app.DynamicalSystems.Fractals.View;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 22/03/15.
 */

public class viewController {
    private JFrame mainField;
    private JPanel menuPanel, fractalPanel;
    private JSlider stepSlider;
    private JComboBox nameOfFractalComboBox;

    private static final int MAXSTEP = 7;
    private static final int MINSTEP = 0;
    private static final int STARTSTEP = 2;

    private static final String SERPINSKICARPET = "Sierpinski Carpet";
    private static final String MINKOVSKICURVE = "Minkovski Curve";
    private static final String SERPINSKITRIANGE = "Sierpinski Triangle";
    private static final String KOCHCURVE = "Koch Curve";

    private JLabel iterLabel, dimensionLabel,factorLabel,copyLabel;


    private static final List<String> LISTOFFRACTALS = Arrays.asList(SERPINSKICARPET, MINKOVSKICURVE, SERPINSKITRIANGE, KOCHCURVE);

    public void createGui() {
        mainField = new JFrame("Fractals");
        mainField.setSize(700, 500);
        mainField.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainField.setLayout(new BorderLayout());
        menuPanel = new JPanel();
        fractalPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(200,500));
        menuPanel.setLayout(new FlowLayout() );
        fractalPanel.setLayout(new BorderLayout());
        mainField.add(menuPanel, BorderLayout.WEST);
        mainField.add(fractalPanel, BorderLayout.CENTER);

        createCombobox();
        createSlider();
        createDimension();
        mainField.setVisible(true);
      //  mainField.pack();
    }

    private void createSlider() {
        stepSlider = new JSlider(MINSTEP, MAXSTEP, STARTSTEP);
        JLabel stepLabel = new JLabel("Step");
        iterLabel = new JLabel(String.valueOf(STARTSTEP));
        menuPanel.add(stepLabel);
        menuPanel.add(stepSlider);
        menuPanel.add(iterLabel);
    }

    private void createCombobox() {
        JLabel name = new JLabel("Choose fractal");
        nameOfFractalComboBox = new JComboBox();
        for (int i = 0; i < LISTOFFRACTALS.size(); i++) {
            nameOfFractalComboBox.addItem(LISTOFFRACTALS.get(i));
        }
        menuPanel.add(name);
        menuPanel.add(nameOfFractalComboBox);
    }

    private void createDimension(){
        menuPanel.add(new JLabel("Dimension"));
        dimensionLabel = new JLabel();
        menuPanel.add(dimensionLabel);
        menuPanel.add(new JLabel("Factor"));
        factorLabel = new JLabel();
        menuPanel.add(factorLabel);
        menuPanel.add(new JLabel("Copy"));
        copyLabel = new JLabel();
        menuPanel.add(copyLabel);


    }

    public JComboBox getComboBox(){
        return this.nameOfFractalComboBox;
    }
    public JSlider getSlider(){
        return this.stepSlider;
    }
    public JPanel getFractalPanel(){
        return this.fractalPanel;
    }
    public JLabel getIterLabel(){
        return this.iterLabel;
    }
    public JLabel getDimensionLabel(){
        return this.dimensionLabel;
    }
    public JLabel getFactorLabel(){
        return this.factorLabel;
    }
    public JLabel getCopyLabel(){
        return this.copyLabel;
    }
}

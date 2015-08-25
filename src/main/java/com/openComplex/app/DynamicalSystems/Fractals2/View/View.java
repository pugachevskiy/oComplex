package com.openComplex.app.DynamicalSystems.Fractals2.View;

import com.jogamp.opengl.awt.GLJPanel;
import com.openComplex.app.DynamicalSystems.Fractals2.FractalsCollection;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Created by laptop on 29.06.2015.
 */
public class View {
    private JFrame mainField;
    private GLJPanel fractalPanel;
    private JPanel menuPanel;
    private JSlider stepSlider;
    private JComboBox nameOfFractalComboBox = new JComboBox();
    private JLabel name = new JLabel("Choose fractal");

    private JButton searchButton, resetButton;
    private JTextField searchFactorTextField, searchCopyTextField;

    private static final int MAXSTEP = 7;
    private static final int MINSTEP = 0;
    private static final int STARTSTEP = 2;

    private JLabel iterLabel, dimensionLabel = new JLabel(),factorLabel = new JLabel(),copyLabel = new JLabel(), discriptionLabel = new JLabel();
    private List<JLabel> labels= Arrays.asList(copyLabel, factorLabel,dimensionLabel,discriptionLabel);

    public void createGui() {
        mainField = new JFrame("Fractals");
        mainField.setSize(800, 600);
        mainField.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainField.setLayout(new BorderLayout());
        menuPanel = new JPanel();
        fractalPanel = new GLJPanel(null);
        menuPanel.setPreferredSize(new Dimension(200,500));
        menuPanel.setLayout(new FlowLayout() );
        fractalPanel.setLayout(new BorderLayout());
        mainField.add(menuPanel, BorderLayout.WEST);
        mainField.add(fractalPanel, BorderLayout.CENTER);

       // createCombobox();
        menuPanel.add(name);
        menuPanel.add(nameOfFractalComboBox);
        createSlider();
        createInfo();
        createSearch();
        mainField.setVisible(true);
    }

    private void createSlider() {
        stepSlider = new JSlider(MINSTEP, MAXSTEP, STARTSTEP);
        JLabel stepLabel = new JLabel("Step");
        iterLabel = new JLabel(String.valueOf(STARTSTEP));
        menuPanel.add(stepLabel);
        menuPanel.add(stepSlider);
        menuPanel.add(iterLabel);
    }



    private void createInfo(){
        for (int i = 1; i < FractalsCollection.TITEL.size(); i++) {
            menuPanel.add(new JLabel(FractalsCollection.TITEL.get(i)));
            menuPanel.add(labels.get(i-1));

        }
    }
    private void createSearch(){
        menuPanel.add(searchFactorTextField = new JTextField());
        menuPanel.add(searchCopyTextField = new JTextField());
        searchCopyTextField.setPreferredSize(new Dimension(50, 20));
        searchCopyTextField.setEditable(true);
        searchFactorTextField.setPreferredSize(new Dimension(50, 20));
        searchFactorTextField.setEditable(true);
        searchCopyTextField.setName("Copy");
        searchFactorTextField.setName("Factor");
        JLabel copy = new JLabel("Copy");
        JLabel factor = new JLabel("Factor");
        menuPanel.add(factor);
        menuPanel.add(copy);

        menuPanel.add(searchButton = new JButton("Search"));
        menuPanel.add(resetButton = new JButton("Reset"));


    }

    public JComboBox getComboBox(){
        return this.nameOfFractalComboBox;
    }
    public JSlider getSlider(){
        return this.stepSlider;
    }
    public GLJPanel getFractalPanel(){
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
    public JLabel getDiscriptionLabel(){
        return this.discriptionLabel;
    }
    public JButton getSearchButton(){ return this.searchButton;}
    public JTextField getSearchFactorTextField(){return this.searchFactorTextField;}
    public JTextField getSearchCopyTextField() { return this.searchCopyTextField;}
    public JButton getResetButton(){return this.resetButton;}
    public JFrame getMainField(){return this.mainField;}
}


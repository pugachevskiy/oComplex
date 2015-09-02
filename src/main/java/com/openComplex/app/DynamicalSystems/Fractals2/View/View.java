package com.openComplex.app.DynamicalSystems.Fractals2.View;

import com.jogamp.opengl.awt.GLJPanel;
import com.openComplex.app.DynamicalSystems.Fractals2.FractalsCollection;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
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
    private List<JLabel> labels= Arrays.asList(copyLabel,factorLabel,dimensionLabel,discriptionLabel);

    public void createGui() {
        mainField = new JFrame("Fractals");
        mainField.setSize(800, 600);
        mainField.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainField.setLayout(new BorderLayout());
        menuPanel = new JPanel();
        fractalPanel = new GLJPanel(null);
        menuPanel.setPreferredSize(new Dimension(250, 500));
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        fractalPanel.setLayout(new BorderLayout());

        createSlider();
        createInfo();
        createSearch();

        mainField.add(menuPanel, BorderLayout.WEST);
        mainField.add(fractalPanel, BorderLayout.CENTER);
        mainField.setVisible(true);
    }

    private void createSlider() {

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));

        stepSlider = new JSlider(MINSTEP, MAXSTEP, STARTSTEP);
        JLabel stepLabel = new JLabel("Step");
        iterLabel = new JLabel(String.valueOf(STARTSTEP));

        JPanel slider = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        slider.setLayout(gbl);

        addComponent(slider, gbl, stepSlider, 0, 0, 5, 1);
        addComponent(slider, gbl, iterLabel, 5, 0, 1, 1);

        sliderPanel.add(stepLabel);
        sliderPanel.add(slider);

        menuPanel.add(sliderPanel);

        //createCombobox();
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));

        comboPanel.add(name);
        comboPanel.add(nameOfFractalComboBox);

        menuPanel.add(comboPanel);




    }


    private void createInfo(){

        JPanel searchFactorPanel = new JPanel(new GridLayout(3, 3));

        for (int i = 1; i < FractalsCollection.TITEL.size()-1; i++) {
            searchFactorPanel.add(new JLabel(FractalsCollection.TITEL.get(i)));
            searchFactorPanel.add(new JLabel(" "));
            searchFactorPanel.add(labels.get(i-1));
        }
        menuPanel.add(searchFactorPanel);

        JPanel descriptonPanel = new JPanel();
        descriptonPanel.setLayout(new BoxLayout(descriptonPanel, BoxLayout.Y_AXIS));

        //descriptonPanel.add(new JLabel(FractalsCollection.TITEL.get(FractalsCollection.TITEL.size() - 1)));
        //descriptonPanel.add(labels.get(FractalsCollection.TITEL.size() - 2));

        descriptonPanel.add(new JLabel(" "));

        JTextPane informationText = new JTextPane();
        informationText.setEditable(false);
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(set, "Mono");
        StyleConstants.setFontSize(set, 12);
        //StyleConstants.setItalic(set, true);
        StyleConstants.setBold(set, true);
        informationText.setParagraphAttributes(set, true);
        informationText.setBackground(new JLabel().getBackground());
        informationText.setBounds(100, 100, 300, 300);
        informationText.setText(FractalsCollection.FRACTALS.get(nameOfFractalComboBox.getSelectedIndex()+1).get(4));

        descriptonPanel.add(informationText);

        menuPanel.add(descriptonPanel);
    }

    private void createSearch(){

        JPanel searchPanel = new JPanel(new GridLayout(2, 3));
        searchFactorTextField = new JTextField();
        searchCopyTextField = new JTextField();
        searchCopyTextField.setPreferredSize(new Dimension(50, 20));
        searchCopyTextField.setEditable(true);
        searchFactorTextField.setPreferredSize(new Dimension(50, 20));
        searchFactorTextField.setEditable(true);
        searchCopyTextField.setName(  "Copy");
        searchFactorTextField.setName("Factor");

        JLabel copy = new JLabel("Copy");
        JLabel factor = new JLabel("Factor");


        searchPanel.add(copy);
        searchPanel.add(new JLabel(" "));
        searchPanel.add(searchCopyTextField);
        searchPanel.add(factor);
        searchPanel.add(new JLabel(" "));
        searchPanel.add(searchFactorTextField);

        menuPanel.add(searchPanel);
        menuPanel.add(new JLabel(" "));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(searchButton = new JButton("Search"));
        buttonPanel.add(resetButton = new JButton("Reset"));

        menuPanel.add(buttonPanel);


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


    static void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
                             int height) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x; gbc.gridy = y;
        gbc.gridwidth = width; gbc.gridheight = height;
        gbc.weightx = 1; gbc.weighty = 1;
        gbl.setConstraints(c, gbc);
        cont.add(c);

    }
}


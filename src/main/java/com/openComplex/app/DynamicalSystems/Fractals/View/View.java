package com.openComplex.app.DynamicalSystems.Fractals.View;

import com.jogamp.opengl.awt.GLJPanel;
import com.openComplex.app.DynamicalSystems.Fractals.FractalsCollection;

import javax.swing.*;
import javax.swing.border.TitledBorder;
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
    public DefaultListModel listModel = new DefaultListModel();
    private JList nameOfFractalsComboBox = new JList(listModel);
    private JTextPane description;
    private JLabel name = new JLabel("Choose fractal"), stepLabel = new JLabel("Step");

    private JButton searchButton, resetButton, startButton;
    private JTextField searchFactorTextField, searchCopyTextField;

    private static final int MAXSTEP = 7;
    private static final int MINSTEP = 0;
    private static final int STARTSTEP = 2;

    private JLabel iterLabel, dimensionLabel = new JLabel(), factorLabel = new JLabel(), copyLabel = new JLabel(), discriptionLabel = new JLabel();
    private List<JLabel> labels = Arrays.asList(copyLabel, factorLabel, dimensionLabel, discriptionLabel);

    public void createGui() {
        mainField = new JFrame("Fractals");
        mainField.setSize(800, 600);
        mainField.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainField.setLayout(new BorderLayout());
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
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
        iterLabel = new JLabel(String.valueOf(STARTSTEP));

        JPanel slider = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        slider.setLayout(gbl);
        addComponent(slider, gbl, stepSlider, 0, 0, 5, 1);
        addComponent(slider, gbl, iterLabel, 5, 0, 1, 1);

        startButton = new JButton("Start");
        startButton.setActionCommand("Start");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        //buttonPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        buttonPanel.add(startButton);

        sliderPanel.add(stepLabel);
        sliderPanel.add(slider);
        sliderPanel.add(buttonPanel);

        //createCombobox();
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new BoxLayout(comboPanel, BoxLayout.Y_AXIS));
        JPanel namePanel = new JPanel(new GridLayout(1, 3));
        namePanel.add(new JLabel(" "));
        namePanel.add(name);
        namePanel.add(new JLabel(" "));

        nameOfFractalsComboBox.setSelectedIndex(0);
        comboPanel.add(namePanel);
        comboPanel.add(nameOfFractalsComboBox);


        menuPanel.add(sliderPanel);
        menuPanel.add(comboPanel);
    }


    private void createInfo() {

        JPanel searchFactorPanel = new JPanel(new GridLayout(3, 3));

        for (int i = 1; i < FractalsCollection.TITEL.size() - 1; i++) {
            searchFactorPanel.add(new JLabel(FractalsCollection.TITEL.get(i)));
            searchFactorPanel.add(new JLabel(" "));
            searchFactorPanel.add(labels.get(i - 1));
        }
        menuPanel.add(searchFactorPanel);

        JPanel descriptonPanel = new JPanel();
        descriptonPanel.setLayout(new BoxLayout(descriptonPanel, BoxLayout.Y_AXIS));

        TitledBorder title = new TitledBorder("Description");
        title.setBorder(BorderFactory.createLineBorder(Color.black));
        descriptonPanel.setBorder(title);

        //Create informationWindow
        description = new JTextPane();
        description.setEditable(false);
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(set, "Mono");
        StyleConstants.setFontSize(set, 12);
        StyleConstants.setBold(set, true);
        description.setParagraphAttributes(set, true);
        description.setBackground(new JLabel().getBackground());
        description.setBounds(100, 100, 300, 300);
        description.setText(FractalsCollection.FRACTALS.get(3).get(4));

        descriptonPanel.add(description);

        menuPanel.add(descriptonPanel);
    }

    private void createSearch() {

        JPanel searchPanel = new JPanel(new GridLayout(2, 3));
        searchFactorTextField = new JTextField();
        searchCopyTextField = new JTextField();
        searchCopyTextField.setPreferredSize(new Dimension(50, 20));
        searchCopyTextField.setEditable(true);
        searchFactorTextField.setPreferredSize(new Dimension(50, 20));
        searchFactorTextField.setEditable(true);
        searchCopyTextField.setName("Copy");
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

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(3, 0, 0, 0));
        buttonPanel.add(searchButton = new JButton("Search"));
        buttonPanel.add(resetButton = new JButton("Reset"));

        menuPanel.add(buttonPanel);


    }

    public JList getComboBox() {
        return this.nameOfFractalsComboBox;
    }

    public JSlider getSlider() {
        return this.stepSlider;
    }

    public GLJPanel getFractalPanel() {
        return this.fractalPanel;
    }

    public JLabel getIterLabel() {
        return this.iterLabel;
    }

    public JLabel getDimensionLabel() {
        return this.dimensionLabel;
    }

    public JLabel getFactorLabel() {
        return this.factorLabel;
    }

    public JLabel getCopyLabel() {
        return this.copyLabel;
    }

    public JLabel getDiscriptionLabel() {
        return this.discriptionLabel;
    }

    public JButton getSearchButton() {
        return this.searchButton;
    }

    public JTextField getSearchFactorTextField() {
        return this.searchFactorTextField;
    }

    public JTextField getSearchCopyTextField() {
        return this.searchCopyTextField;
    }

    public JButton getResetButton() {
        return this.resetButton;
    }

    public JButton getStartButton() {
        return this.startButton;
    }

    public void updateDescription(String text) {
        description.setText(text);
    }

    private void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
                              int height) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbl.setConstraints(c, gbc);
        cont.add(c);
    }
}


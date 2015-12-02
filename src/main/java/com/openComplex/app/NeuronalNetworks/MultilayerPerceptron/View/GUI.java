package com.openComplex.app.NeuronalNetworks.MultilayerPerceptron.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Created by MatthiasFuchs on 29.11.15.
 */
public class GUI {
    private JFrame frame;
    private JComboBox<String> hiddenLayerBox, dataSplitBox;
    private JButton selectDataButton, classifyButton, learnButton, confusionButton;
    public JFormattedTextField[] hiddenLayerFields;
    private JFormattedTextField maxIterationField, learningRateField;
    public JLabel statusLabel;
    private String[] hiddenLayerString = {"1", "2", "3", "4","5", "6"},
            dataSplitString = {"0.1", "0.2", "0.3", "0.4", "0.5", "0.6", "0.7", "0.8", "0.9"};

    public GUI(){
        frame = new JFrame("Multilayer-Perceptron");
        frame.setLayout(new BorderLayout());
        frame.add(northPanel(), BorderLayout.NORTH);
        frame.add(westPanel(), BorderLayout.WEST);
        frame.add(southPanel(), BorderLayout.SOUTH);
        frame.add(centerPanel(), BorderLayout.CENTER);
        frame.setSize(new Dimension(1000, 1000));
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel northPanel() {
        JPanel northPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        northPanel.setLayout(gbl);

        addComponent(northPanel, gbl, new JLabel("Select data: "), 0, 0, 1, 1);
        selectDataButton = new JButton("Choose");
        selectDataButton.setActionCommand("selectDataButton");
        addComponent(northPanel, gbl, selectDataButton, 0, 1, 1, 1);

        addComponent(northPanel, gbl, new JLabel(" "), 0, 2, 1, 2);


        addComponent(northPanel, gbl, new JLabel("Data split: "), 1, 0, 1, 1);
        dataSplitBox = new JComboBox<>(dataSplitString);
        dataSplitBox.setActionCommand("dataSplitBox");
        dataSplitBox.setSelectedIndex(5);
        dataSplitBox.setEnabled(false);
        addComponent(northPanel, gbl, dataSplitBox, 1, 1, 1, 1);

        addComponent(northPanel, gbl, new JLabel(" "), 1, 2, 1, 2);

        return northPanel;
    }

    private JPanel westPanel() {
        JPanel westPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        westPanel.setLayout(gbl);

        hiddenLayerFields = new JFormattedTextField[6];
        for(int i = 0; i < hiddenLayerFields.length; i++) {
            hiddenLayerFields[i] = new JFormattedTextField(new DecimalFormat("##"));
            hiddenLayerFields[i].setPreferredSize(new Dimension(40, 20));

            hiddenLayerFields[i].setText("20");
            hiddenLayerFields[i].setEnabled(false);

        }

        addComponent(westPanel, gbl, new JLabel(" "), 0, 0, 1, 2);

        addComponent(westPanel, gbl, new JLabel("Hidden layer: "), 1, 0, 1, 1);
        hiddenLayerBox = new JComboBox<>(hiddenLayerString);
        hiddenLayerBox.setActionCommand("hiddenLayerBox");
        hiddenLayerBox.setEnabled(false);
        addComponent(westPanel, gbl, hiddenLayerBox, 1, 1, 1, 1);

        addComponent(westPanel, gbl, new JLabel(" "), 2, 0, 1, 2);

        addComponent(westPanel, gbl, new JLabel("Number of perceptrons: "), 3, 0, 1, 2);
        for(int i = 0; i < hiddenLayerFields.length; i++) {
            addComponent(westPanel, gbl, new JLabel("Hidden layer " + (i+1) + ":"), i+4, 0, 1, 1);
            addComponent(westPanel, gbl, hiddenLayerFields[i], i+4, 1, 1, 1);

        }

        addComponent(westPanel, gbl, new JLabel(" "), 10, 0, 1, 2);

        addComponent(westPanel, gbl, new JLabel("Learning rate: "), 11, 0, 1, 1);
        learningRateField = new JFormattedTextField();
        learningRateField.setText("0.005");
        learningRateField.setEnabled(false);
        addComponent(westPanel, gbl, learningRateField, 11, 1, 1, 1);

        addComponent(westPanel, gbl, new JLabel("Maximal iteration:  "), 12, 0, 1, 1);
        maxIterationField = new JFormattedTextField(new DecimalFormat("###"));
        maxIterationField.setText("500");
        maxIterationField.setEnabled(false);
        addComponent(westPanel, gbl, maxIterationField, 12, 1, 1, 1);

        addComponent(westPanel, gbl, new JLabel(" "), 13, 0, 1, 2);

        return westPanel;
    }

    private JPanel southPanel() {
        JPanel southPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        southPanel.setLayout(gbl);

        learnButton = new JButton("Learn");
        learnButton.setActionCommand("learnButton");
        learnButton.setPreferredSize(new Dimension(200, 25));
        learnButton.setEnabled(false);
        addComponent(southPanel, gbl, learnButton, 0, 0, 1, 1);

        classifyButton = new JButton("Classify");
        classifyButton.setActionCommand("classifyButton");
        classifyButton.setPreferredSize(new Dimension(200, 25));
        classifyButton.setEnabled(false);
        addComponent(southPanel, gbl, classifyButton, 0, 1, 1, 1);

        confusionButton = new JButton("Confusion Matrix");
        confusionButton.setActionCommand("confusionButton");
        confusionButton.setPreferredSize(new Dimension(200, 25));
        confusionButton.setEnabled(false);
        addComponent(southPanel, gbl, confusionButton, 0, 2, 1, 1);

        statusLabel = new JLabel("   ");
        addComponent(southPanel, gbl, statusLabel, 1, 0, 1, 3);

        return southPanel;
    }

    private JPanel centerPanel() {
        JPanel centerPanel = new JPanel();
        return centerPanel;
    }



    public void addActionListener(ActionListener al) {
        selectDataButton.addActionListener(al);
        hiddenLayerBox.addActionListener(al);
        dataSplitBox.addActionListener(al);
        learnButton.addActionListener(al);
        classifyButton.addActionListener(al);
        confusionButton.addActionListener(al);
    }

    private void addComponent(Container cont, GridBagLayout gbl, Component c, int y, int x, int height,
                             int width) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x; gbc.gridy = y;
        gbc.gridwidth = width; gbc.gridheight = height;
        gbc.weightx = 1; gbc.weighty = 1;
        gbl.setConstraints(c, gbc);
        cont.add(c);
    }

    public int getHiddenLayerBox() {
        return hiddenLayerBox.getSelectedIndex();
    }

    public int getDataSplitBox() {
        return dataSplitBox.getSelectedIndex();
    }

    public int getMaxIteration() {
        int maxIteration;
        maxIteration = Integer.parseInt(maxIterationField.getText());
        return maxIteration;
    }

    public void createConfusionMatrixFrame(int[][] matrix) {
        JFrame frame = new JFrame("Confusion Matirx");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        GridBagLayout gbl = new GridBagLayout();
        frame.setLayout(gbl);

        for(int i = 0; i <= matrix.length; i++) {
            for(int j = 0; j <= matrix[0].length; j++) {
                if(i == 0 && j == 0) {

                }
                else if(i == 0) {
                    JLabel temp = new JLabel("" + (j));
                    temp.setForeground(Color.red);
                    temp.setPreferredSize(new Dimension(50, 25));
                    addComponent(frame, gbl, temp, i, j, 1, 1);
                }
                else if( j== 0) {
                    JLabel temp = new JLabel("" + (i));
                    temp.setForeground(Color.red);
                    temp.setPreferredSize(new Dimension(50, 25));
                    addComponent(frame, gbl, temp, i, j, 1, 1);
                }
                else {
                    JLabel temp = new JLabel("" + matrix[i - 1][j - 1]);
                    temp.setPreferredSize(new Dimension(50, 25));
                    addComponent(frame, gbl, temp , i, j, 1, 1);
                }
            }
        }
        frame.pack();
        frame.setVisible(true);
    }

    public double getLearningRate() {
        double learningRate;
        learningRate = Double.parseDouble(learningRateField.getText());
        return learningRate;
    }

    public void setEnableOptions(boolean state) {
        hiddenLayerBox.setEnabled(state);
        dataSplitBox.setEnabled(state);
        maxIterationField.setEnabled(state);
        learningRateField.setEnabled(state);
    }


    public void setEnableLearnButton (boolean state) {
        learnButton.setEnabled(state);
    }

    public void setEnableClassifyButton (boolean state) {
        classifyButton.setEnabled(state);
    }

    public void setEnableConfusionButton (boolean state) {
        confusionButton.setEnabled(state);
    }
}

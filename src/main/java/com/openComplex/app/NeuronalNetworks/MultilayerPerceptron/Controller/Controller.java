package com.openComplex.app.NeuronalNetworks.MultilayerPerceptron.Controller;

import Model.ConfusionMatrix;
import Model.MLP;
import Model.SelectData;
import View.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MatthiasFuchs on 29.11.15.
 */
public class Controller implements ActionListener{
    private double dataSplit = 0.6, learningRate = 0.005;
    private int numberOfHiddenLayer = 1, maxIteration = 500;
    private int[] hiddenLayers;
    private double[] classified;
    private int[][] matrix;

    private MLP mlp;
    private SelectData selectData;
    private SelectData.Data data;
    private ConfusionMatrix confusionMatrix;
    private GUI gui;

    public Controller() {
        gui = new GUI();
        gui.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if(command.equals("selectDataButton")) {
            System.out.println("selectDataButton");
            selectData = new SelectData();
            selectData.csvChooser();
            if(selectData.path != null) {
                data = selectData.readCSV();
                gui.setEnableOptions(true);
                gui.hiddenLayerFields[0].setEnabled(true);
                gui.setEnableLearnButton(true);
                data.splitData(dataSplit);
                gui.statusLabel.setText("Data split: " + dataSplit + " (Train pattern: " + data.trainPattern.length
                + " Test pattern: " + data.testPattern.length + ")");
            }
        }
        else if (command.equals("hiddenLayerBox")) {
            System.out.println("hiddenLayerBox");
            getHiddenLayer();
            for(int i = 0; i < numberOfHiddenLayer; i++) {
                gui.hiddenLayerFields[i].setEnabled(true);
            }
        }

        else if (command.equals("dataSplitBox")) {
            System.out.println("dataSplitBox");
            getDataSplit();
            data.splitData(dataSplit);
            gui.statusLabel.setText("Data split: " + dataSplit + " (Train pattern: " + data.trainPattern.length
                    + " Test pattern: " + data.testPattern.length + ")");
        }

        else if (command.equals("learnButton")) {
            System.out.println("learnButton");
            if(data != null) {
                if(data.trainLabel != null && data.trainPattern != null) {

                    hiddenLayers = new int[numberOfHiddenLayer];
                    for(int i = 0; i < numberOfHiddenLayer; i++) {
                        hiddenLayers[i] = Integer.parseInt(gui.hiddenLayerFields[i].getText());
                    }
                    learningRate = gui.getLearningRate();
                    maxIteration = gui.getMaxIteration();
                    if(checkValues()) {
                        mlp = new MLP();
                        mlp.train(data.trainPattern, data.trainLabel, hiddenLayers, learningRate, maxIteration);
                        gui.statusLabel.setText("Learn finished");
                    }

                    gui.setEnableClassifyButton(true);
                }
            }
        }
        else if (command.equals("classifyButton")) {
            System.out.println("classifyButton");
            classified = mlp.classify(data.testPattern);
            gui.statusLabel.setText("Classify finished ");
            gui.setEnableConfusionButton(true);
        }
        else if (command.equals("confusionButton")) {
            System.out.println("confusionButton");
            confusionMatrix = new ConfusionMatrix();
            matrix = confusionMatrix.computeConfusionMatrix(classified, data.testLabel);
            gui.createConfusionMatrixFrame(matrix);
        }
    }


    private void getHiddenLayer() {
        int index = gui.getHiddenLayerBox();
        switch (index) {
            case 0:
                numberOfHiddenLayer = 1;
                break;
            case 1:
                numberOfHiddenLayer = 2;
                break;
            case 2:
                numberOfHiddenLayer = 3;
                break;
            case 3:
                numberOfHiddenLayer = 4;
                break;
            case 4:
                numberOfHiddenLayer = 5;
                break;
            case 5:
                numberOfHiddenLayer = 6;
                break;
        }
    }

    private void getDataSplit() {
        int index = gui.getDataSplitBox();
        switch (index) {
            case 0:
                dataSplit = 0.1;
                break;
            case 1:
                dataSplit = 0.2;
                break;
            case 2:
                dataSplit = 0.3;
                break;
            case 3:
                dataSplit = 0.4;
                break;
            case 4:
                dataSplit = 0.5;
                break;
            case 5:
                dataSplit = 0.6;
                break;
            case 6:
                dataSplit = 0.7;
                break;
            case 7:
                dataSplit = 0.8;
                break;
            case 8:
                dataSplit = 0.8;
                break;
        }
    }

    private boolean checkValues() {
        for(int i = 0; i < numberOfHiddenLayer; i++) {
            if(hiddenLayers[i] < 1) {
                System.out.println("Test");
                gui.statusLabel.setText("Please enter a positive integer in number of perceptron");
                return false;
            }
        }
        if(maxIteration < 1) {
            System.out.println("Test");
            gui.statusLabel.setText("Please enter a positive integer in maximal iteration");
            return false;
        }
        else if (!(0 < learningRate && learningRate < 1)) {
            System.out.println("Test");
            gui.statusLabel.setText("Please enter a value bigger than 0 and smaller than 1 in learning rate");
            return false;
        } else {
            return true;
        }
    }
}

package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.View.*;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Main-Klasse, die für erste Initalisierung sorgt
 */
public class Main {

    public static GUI gui;
    public static int size = 5;
    public static int speed = 1;
    public static int steps = 0;

    public static java.util.List<int[]> stepList= new ArrayList<int[]>();
    public static boolean[] similiarVectors;

    public static java.util.ArrayList<String> stepListString = new ArrayList<String>();
    public static java.util.ArrayList<String> nextStepListString = new ArrayList<String>();
    public static java.util.ArrayList<String> differencesString = new ArrayList<String>();


    public Main() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        gui = new GUI();
        Main.gui.tablePanel.updateSize(size);
    }

    public static void paintHopField() {
        new Thread() {
            @Override public void run() {
                GraphPanel.steps=0;
                gui.tablePanel.setEnabled(false);
                gui.graphPanel.selectedNode = -1;
                gui.graphPanel.updateValueArray(gui.tablePanel.getTextfieldValues());
                while(GraphPanel.steps<size) {
                    GraphPanel.steps++;
                    Main.gui.graphPanel.repaint();
                    if(!(speed==0)){
                        try {
                            Thread.sleep(speed*250);
                        } catch (InterruptedException exp) {
                            System.err.println("Error with sleep-function.");
                        }
                    }

                }
                gui.tablePanel.setEnabled(true);
            }
        }.start();
    }


    public static void calculateSteps() {
        steps = 0;
        stepList.clear();
        stepListString.clear();
        nextStepListString.clear();
        differencesString.clear();

        boolean asynchron = gui.getSelectedState();

        int [][]matrix = gui.tablePanel.getIntTextfieldValues();
        int []oldVector = gui.tablePanel.optionPanel.getStartState();
        int []thresholdVector = gui.tablePanel.optionPanel.getThreshold();

        stepList.add(oldVector);

        while(steps++<30) {
            //Calculate Net-input:
            int netinput[] = matrixMalVector(matrix, oldVector);
            netinput = vectorAdd(netinput, thresholdVector);
            //Calculate nextStep
            int[] tempVector = netinput;
            int[] nextState = normalize(tempVector);

            //Look for differences between old state and current netinput
            similiarVectors = new boolean[netinput.length];
            for (int i = 0; i < size; i++) {
                similiarVectors[i] = Math.signum(oldVector[i]) == Math.signum(netinput[i]);
            }

            //If asynchronized, switch one false vector element
            if(!asynchron)
                for(int i=0; i<similiarVectors.length; i++) {
                    if(!similiarVectors[i]) {
                        nextState[i] = nextState[i]==-1 ? 1 : -1;
                        break;
                    }
                }


            //Inserting vectors in history
            stepList.add(new int[size]);
            for(int i=0; i<size; i++) {
                stepList.get(stepList.size()-1)[i] = netinput[i];
            }
            appendArray(stepListString, netinput);
            appendArray(nextStepListString, nextState);
            appendArray(differencesString, similiarVectors);

            oldVector = nextState;
            if(terminationCondition(steps)) {
                System.out.println("Break");
                break;
            }

        }
        System.out.println("\n\n");

        Main.gui.tablePanel.optionPanel.repaint();
    }



    public static int[][] shakeMatrix() {
        int[][] matrix = new int[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = (int)(Math.random()*10-5);
            }

        }
        return matrix;
    }

    public static int[] matrixMalVector(int[][] matrix, int[] vector) {
        int []returnVector = new int[vector.length];
        int temp;
        //Multiplicating old vector and Matrix
        for (int i = 0; i < matrix.length; i++) {
            temp = 0;
            for (int j = 0; j < matrix.length; j++) {
                temp += vector[j] * matrix[i][j];
            }
            returnVector[i] = temp;
        }
        return returnVector;
    }

    public static int[] vectorAdd(int[] vector1, int[] vector2) {

        for (int i = 0; i < vector1.length; i++) {
            vector1[i] += vector2[i];
        }
        return  vector1;
    }

    public static int[] normalize(int[] vector) {

        int[] vectorNormalize = new int[vector.length];
        for(int i=0; i<vectorNormalize.length; i++) {
            vectorNormalize[i] = vector[i];
        }

        for(int i=0; i<vector.length; i++) {
            vectorNormalize[i] = (int)Math.signum(vectorNormalize[i]);
        }

        return vectorNormalize;
    }

    public static void appendArray(java.util.ArrayList<String> updatedList, boolean[] toAppend) {
        String updateString = "";
        for(int i=0; i<toAppend.length; i++) {
            if(!toAppend[i])
                updateString += i + ", ";
        }
        updatedList.add(updateString);
    }

    public static void appendArray(java.util.ArrayList<String> updatedList, int[] toAppend) {
        String updateString = "(";
        for(int i=0; i<toAppend.length; i++) {
            updateString += toAppend[i];
            if(!(i+1==toAppend.length))
                updateString += " ";
        }
        updateString += ")T";
        updatedList.add(updateString);
    }

    private static boolean terminationCondition(int step) {
        boolean terminate = false;

        if(step>1) {
            if(nextStepListString.get(step-1).equals(nextStepListString.get(step-2))) {
                nextStepListString.add("stable state: z" + (step-2) + " = z" + (step-1));
                terminate = true;
            }

        }
        if(step>2) {
            if(nextStepListString.get(step-1).equals(nextStepListString.get(step-3))) {
                nextStepListString.add("dead lock: z" + (step-1) + " = z" + (step-3));
                terminate = true;
            }


        }

        return terminate;
    }

}
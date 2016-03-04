package com.openComplex.app.CellularAutomat.ModelOfHegselmann.Model;

import java.util.LinkedList;

/**
 * Created by MatthiasFuchs on 04.03.16.
 */
public class SavedSteps {
    private final double[][][] steps;


    public SavedSteps(int maxStep) {
        steps = new double[maxStep][][];
    }

    public void addStep(int index, final double[][] step) {
        steps[index] = new double[step.length][step[0].length];
        for(int i = 0; i < step.length; i++) {
            for(int j = 0; j < step[0].length; j++) {
                steps[index][i][j] = step[i][j];
            }
        }

    }

    public double[][] getStep(int index) {
        return steps[index];
    }

    public void printSteps() {
        for(int i = 0; i < steps.length; i++) {
            for(int j = 0; j < steps[0].length; j++) {
                for(int k = 0; k < steps[0][0].length; k++) {
                    System.out.print(steps[i][j][k] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }
}

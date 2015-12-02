package com.openComplex.app.NeuronalNetworks.MultilayerPerceptron.Model;

/**
 * Created by MatthiasFuchs on 12.11.15.
 */
public class ConfusionMatrix {

    /**
     * Methode berechnet die Confusionsmatrix
     * @param output klassifizierter Output
     * @param desiredOutput gewünschter Output
     * @return Confusionsmatrix
     */
    public int[][] computeConfusionMatrix(double[] output, double[] desiredOutput) {
        int[][] matrix;

        int max = 0;
        for (int i = 0; i < output.length; i++) {
            if (max < (int) output[i]) {
                max = (int) output[i];
            }
        }

        for (int i = 0; i < desiredOutput.length; i++) {
            if (max < (int) desiredOutput[i]) {
                max = (int) desiredOutput[i];
            }
        }

        matrix = new int[max][max];
        int tempDesired;
        int tempOutput;

        for (int h = 0; h < desiredOutput.length; h++){
            tempDesired = (int) desiredOutput[h];
            tempOutput = (int) output[h];
            matrix[tempDesired-1][tempOutput-1]++;
        }

        for (int i = 0; i < max; i++) {
            for (int j = 0; j < max; j++) {
                System.out.printf("%6d ", matrix[i][j]);
            }
            System.out.println();
        }
        return matrix;
    }
}

package com.openComplex.app.NeuronalNetworks.MultilayerPerceptron.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by MatthiasFuchs on 04.11.15.
 */

public class MLP {
    private int nInput, nOutput;
    private int[] nHidden;
    private List<double[][]> weights = new LinkedList<double[][]>();

    private double [] input, output;
    private List<double[]> layers = new LinkedList<double[]>();

    private int hiddenLayer = 1;

    private double[][] actualOutput;
    private double[][] desiredOutput;
    private double  entireError = 0.0;

    private List<Double> desiredOutputDistribution = new LinkedList<Double>();

    /**
     * Methode trainiert das Netzwerk
     * @param patterns Train-Patterns
     * @param labels Train-Labels
     * @param nHidden Anzahl der zwischen Schichten
     * @param learningRate Lernrate (zwischen 0.0 und 1.0) legt die Lernintensitaet fest
     * @param maxIteration Ist die Maximale Anzahl von Wiederholungen auf den Train-Daten
     */
    public void train(double[][] patterns, double[] labels, int[] nHidden, double learningRate, long maxIteration) {
        nInput = patterns[0].length;
        nOutput = computeMaxLabel(labels);
        this.nHidden = nHidden;

        hiddenLayer = nHidden.length;

        input = new double[nInput + 1];
        output = new double[nOutput];

        for (int i = 0; i < hiddenLayer; i++) {
            double [] hidden = new double[nHidden[i]+1];
            layers.add(hidden);
        }

        initWeights();


        patterns = normalisation(patterns);
        double[][] extendedLabels = extendedLabels(labels);
        calculateDistribution(extendedLabels);
        double previousEntireError = Double.POSITIVE_INFINITY;
        int index = 0;
        if (patterns.length == extendedLabels.length) {
            do {
                if (index > 0) {
                    previousEntireError = entireError;
                }
                actualOutput = new double[extendedLabels.length][extendedLabels[0].length];
                desiredOutput = extendedLabels;

                for (int i = 0; i < patterns.length; i++) {

                    actualOutput[i] = passNetwork(patterns[i]);
                    backPropagation(extendedLabels[i], learningRate);
                }

                entireError = 0.0;
                calculateEntireError();
                System.out.println("Entire Error: " + entireError);
                index++;
            } while (previousEntireError >= entireError && index < maxIteration) ;
            System.out.println("Iterations: " + index);
        } else{
            System.out.println("Pattern und Labels passen nicht zusammen");
        }
    }


    /**
     * Methode initialisiert die Gewichte für die Verbindungen
     */
    private void initWeights(){
        for (int i = 0; i <= hiddenLayer; i++) {
            double[][] weightMatrix;
            if(i== 0) {
                weightMatrix = new double[nInput+1][nHidden[i]+1];
            } else if (i == hiddenLayer) {
                weightMatrix = new double[nHidden[i-1]+1][nOutput];
            } else {
                weightMatrix = new double[nHidden[i-1]+1][nHidden[i]+1];
            }
            for (int j = 0; j < weightMatrix.length; j++) {
                for (int k = 0; k < weightMatrix[0].length; k++) {
                    weightMatrix[j][k] = Math.random() - 0.5;
                }
            }
            weights.add(weightMatrix);
        }
    }

    /**
     * Methode gibt die Gewichte aus
     */
    public void printWeights() {
        for (int i = 0; i <= hiddenLayer; i++) {
            double [][] weightMatrix = weights.get(i);
            System.out.println("weight Matrix " + i + ":");
            for (int j = 0; j < weightMatrix.length; j++) {
                for (int k = 0; k < weightMatrix[0].length; k++) {
                    System.out.print(weightMatrix[j][k] + " ");
                }
                System.out.println(" ");
            }
            System.out.println(" ");
        }
    }

    /**
     * Methode gibt die Werte der Schichten aus
     */
    public void printLayers() {
        System.out.println("Inputlayer: ");
        for (int i=0; i < input.length; i++) {
            System.out.print(input[i] + " ");
        }
        System.out.println(" ");
        for (int i = 0; i < hiddenLayer; i++) {
            System.out.println("Hiddenlayer " + i + ":");
            double[] temp = layers.get(i);
            for (int j=0; j < temp.length; j++) {
                System.out.print(temp[j] + " ");
            }
            System.out.println(" ");
        }
        System.out.println("Outputlayer: ");
        for (int i = 0; i < output.length; i++) {
            System.out.print(output[i] + " ");
        }
    }

    /**
     * Methode berechnet den Gesamtfehler
     */
    private void calculateEntireError() {
        for(int i = 0; i < actualOutput.length; i++) {
            for(int j = 0; j < actualOutput[0].length; j++) {
                entireError = entireError + 0.5 * Math.pow(desiredOutput[i][j] - actualOutput[i][j], 2);
            }
        }
    }



    /**
     * Methode sucht das maximale Label
     * @param labels Train-Labels
     * @return maximale Label
     */
    protected int computeMaxLabel(double[] labels) {
        int maxLabel = 0;
        int numberOfLabels = labels.length;
        for (int i = 0; i < numberOfLabels; i++) {
            if (maxLabel < (int) labels[i]) {
                maxLabel = (int) (labels[i]);
            }
        }
        return maxLabel;
    }

    /**
     * Methode erweitert die Labels für den Multilayer-Perzeptron-Klassifizierer
     * @param labels Train-Labels
     * @return erweiterte Labels
     */
    private double[][] extendedLabels(double[] labels) {
        int length = computeMaxLabel(labels), value;
        double[][] extendedLabels = new double[labels.length][length];

        for (int i = 0; i < labels.length; i ++) {
            value = (int) labels[i];
            extendedLabels[i][value-1] = 1.0;
        }
        return extendedLabels;
    }

    /**
     * Methode normalisiert die Patterns
     * @param patterns Patterns
     * @return normalisierte Patterns
     */
    private double[][] normalisation(double[][] patterns) {
        double[][] newPatterns = new double[patterns.length][patterns[0].length];
        double max;
        for(int i = 0; i < patterns[0].length; i++) {
            max = Double.NEGATIVE_INFINITY;
            for(int j = 0; j < patterns.length; j++) {
                if(max < patterns[j][i]) {
                    max = patterns[j][i];
                }
            }
            for(int j = 0; j < patterns.length; j++) {
                newPatterns[j][i] = patterns[j][i]/max;
            }
        }
        return newPatterns;
    }

    /**
     * Methode klassifiziert die uebergebenen Patterns
     * @param patterns Patterns die klassifiziert Werden
     * @return double-Array mit den jeweiligen Labels
     */
    public double[] classify(double[][] patterns) {
        patterns = normalisation(patterns);
        double [] labels = new double[patterns.length];


        for(int i = 0; i < patterns.length; i++) {
            double[] output = passNetwork(patterns[i]);
            labels [i] = (double) winner(output);
        }
        return labels;
    }

    /**
     * Methode gibt die Zahl des Ausgangsperzeptrons mit dem hoechsten Wert zurück
     * @param classified Ausgangschicht
     * @return Perzeptron mit dem hoechsten Wert
     */
    private int winner(double[] classified) {
        double max = Double.NEGATIVE_INFINITY;
        int index = 0;
        for(int i = 0; i < classified.length; i++) {
            if (classified[i] > max) {
                max = classified[i];
                index = i+1;
            }
        }
        return index;
    }

    /**
     * Methode durchlaeuft das Netzwerk und berechnet die Werte der Ausgabeschicht
     * @param trainInput Eingabeschicht
     * @return Ausgabeschicht
     */
    private double[] passNetwork(double[] trainInput) {

        input[0] = 1.0;
        for(int i=0; i<nInput; i++) {
            input[i+1] = trainInput[i];
        }

        double[] startLayer = input;
        double[][] weightMatrix = weights.get(0);
        double[] targetLayer = layers.get(0);

        targetLayer[0] = 1.0;
        for (int i=1; i<targetLayer.length; i++) {
            targetLayer[i] = 0.0;
            for(int j=0; j<startLayer.length; j++) {
                targetLayer[i] += weightMatrix[j][i] *startLayer[j];
            }
            targetLayer[i] = activationFunction("logistic", targetLayer[i]);
        }
        layers.set(0, targetLayer);

        for (int h=1; h < hiddenLayer; h++) {
            startLayer = layers.get(h - 1);
            weightMatrix = weights.get(h);
            targetLayer = layers.get(h);

            targetLayer[0] = 1.0;
            for (int i=1; i<targetLayer.length; i++) {
                targetLayer[i] = 0.0;
                for(int j=0; j<startLayer.length; j++) {
                    targetLayer[i] += weightMatrix[j][i] *startLayer[j];
                }
                targetLayer[i] = activationFunction("logistic", targetLayer[i]);
            }
            layers.set(h, targetLayer);
        }

        startLayer = layers.get(hiddenLayer-1);
        weightMatrix = weights.get(hiddenLayer);
        targetLayer = output;
        for (int i=0; i<targetLayer.length; i++) {
            targetLayer[i] = 0.0;
            for(int j=0; j<startLayer.length; j++) {
                targetLayer[i] = targetLayer[i] +  weightMatrix[j][i] *startLayer[j];
            }
            targetLayer[i] = activationFunction("logistic", targetLayer[i]);
        }
        output = targetLayer;
        return output;
    }

    /**
     * Methode für die Aktivierungsfunktion für die einzelnen Perzeptronen
     * @param function ausgewaehlte Funktion
     * @param value zu berechnender Wert
     * @return Ergebnis der Berechnung
     */
    private double activationFunction(String function, double value) {
        double solution = 0.0;
        if(function.equals("logistic")) {
            solution = 1.0/(1.0 + Math.exp(-value));
        } else if (function.equals("step")) {
            if (value < 0) {
                solution = 0.0;
            } else {
                solution = 1.0;
            }
        } else {
            System.out.println("Keine gueltige Eingabe");
        }
        return solution;
    }

    /**
     * Methode berechnet die Verteilung der Labels (zur Gewichtung beim Trainieren)
     * @param desiredOutput Train-Labels
     */
    private void calculateDistribution(double [][] desiredOutput) {
        double temp = 0.0;

        int numberOfOutputs = desiredOutput.length;
        for (int i = 0; i < nOutput; i++) {
            desiredOutputDistribution.add(temp);
        }

        for (int i = 0; i < numberOfOutputs; i++) {
            for (int j = 0; j < nOutput; j++) {
                if (desiredOutput[i][j] == 1.0) {
                    temp = desiredOutputDistribution.get(j);
                    temp = temp + 1.0;
                    desiredOutputDistribution.set(j, temp);
                }
            }
        }
        for (int i = 0; i < nOutput; i++) {
            temp = desiredOutputDistribution.get(i);
            temp = temp/((double)numberOfOutputs);
            desiredOutputDistribution.set(i,temp);
        }
    }


    /**
     * Methode führt die BackPropagation durch und passt die Gewichte an
     * @param desiredOutput gewünschter Ausgangswert
     * @param learningRate Lernrate
     */
    private void backPropagation(double[] desiredOutput, double learningRate) {

        double distributionFactor = 0.0;

        for(int i = 0; i < nOutput; i++) {
            if (desiredOutput[i] == 1.0) {
                distributionFactor =1/(desiredOutputDistribution.get(i)*100.0);
            }
        }

        List<double[]> errors = new LinkedList<double[]>();
        for(int i = 0; i <=hiddenLayer; i++) {
            double [] error;
            if (i < hiddenLayer) {
                error = new double[nHidden[i]+1];
            } else {
                error = new double[nOutput];
            }
            errors.add(error);
        }

        double[] error;
        double[][] weight;
        double[][] nextWeight;
        double[] nextError;
        double[] layer;
        double nCurrentLayer, nNextLayer;
        double errorSum = 0.0;



        error = errors.get(hiddenLayer);
        for (int j = 0; j < nOutput; j++) {
            error[j] = output[j] * (1.0-output[j]) * (desiredOutput[j] - output[j]);
        }
        errors.set(hiddenLayer, error);


        error = errors.get(hiddenLayer-1);
        nextError = errors.get(hiddenLayer);
        nextWeight = weights.get(hiddenLayer);
        nCurrentLayer = nHidden[hiddenLayer-1];
        nNextLayer = nOutput;
        layer = layers.get(hiddenLayer-1);

        for (int i = 0; i< nCurrentLayer; i++) {
            for (int j = 0; j< nNextLayer; j++) {
                errorSum = errorSum + nextWeight[i][j] * nextError[j];
            }
            error[i] = layer[i]*(1.0-layer[i])*errorSum;
            errorSum = 0.0;
        }
        errors.set(hiddenLayer-1, error);


        for (int i = hiddenLayer-1; i > 0; i--) {
            error = errors.get(i-1);
            nextError = errors.get(i);
            nextWeight = weights.get(i);
            nCurrentLayer = nHidden[i-1];
            layer = layers.get(i-1);
            nNextLayer = nHidden[i];
            for (int j = 0; j < nCurrentLayer; j++) {
                for (int k = 0; k < nNextLayer; k++) {
                    errorSum = errorSum + nextWeight[j][k] * nextError[k];
                }
                error[j] = layer[j]*(1.0 - layer[j])*errorSum;
                errorSum = 0.0;
            }
            errors.set(i-1, error);
        }

        weight = weights.get(0);
        layer = input;
        error = errors.get(0);

        for(int i = 0; i <= nInput; i++) {
            for(int j = 1; j <= nHidden[0]; j++) {
                weight[i][j] = weight[i][j] + learningRate*error[j]*layer[i]*distributionFactor;
            }
        }
        weights.set(0, weight);


        for(int i = 0; i < hiddenLayer-1; i++) {
            weight = weights.get(i+1);
            error = errors.get(i+1);
            layer = layers.get(i);
            for(int j = 0; j <=nHidden[i]; j++) {
                for(int k = 1; k <= nHidden[i+1]; k++) {
                    weight[j][k] = weight[j][k] + learningRate*error[k]*layer[j]*distributionFactor;
                }
            }
            weights.set(i+1, weight);
        }

        weight = weights.get(hiddenLayer);
        layer = layers.get(hiddenLayer-1);
        error = errors.get(hiddenLayer);

        for (int i = 0; i <= nHidden[hiddenLayer-1]; i++) {
            for (int j = 0; j < nOutput; j++) {
                weight[i][j] = weight[i][j] + learningRate*error[j]*layer[i]*distributionFactor;
            }
        }
        weights.set(hiddenLayer, weight);
    }
}
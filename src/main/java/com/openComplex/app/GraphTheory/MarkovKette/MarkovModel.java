package com.openComplex.app.GraphTheory.MarkovKette;

/**
 * Created by strange on 27/08/15.
 */

import java.util.ArrayList;

/******************************************************************************
 * Compilation:  javac MarkovChain.java
 * Execution:    java MarkovChain
 *
 * Computes the expected time to go from state N-1 to state 0
 *
 * Data taken from Glass and Hall (1949) who distinguish 7 states
 * in their social mobility study:
 *
 * 1. Professional, high administrative
 * 2. Managerial
 * 3. Inspectional, supervisory, non-manual high grade
 * 4. Non-manual low grade
 * 5. Skilled manual
 * 6. Semi-skilled manual
 * 7. Unskilled manual
 *
 * See also Happy Harry, 2.39.
 ******************************************************************************/

public class MarkovModel {
    //default Matrix
    double[][] transition2 = {{0.386, 0.147, 0.202, 0.062, 0.140, 0.047, 0.016},
            {0.107, 0.267, 0.227, 0.120, 0.207, 0.052, 0.020},
            {0.035, 0.101, 0.188, 0.191, 0.357, 0.067, 0.061},
            {0.021, 0.039, 0.112, 0.212, 0.431, 0.124, 0.061},
            {0.009, 0.024, 0.075, 0.123, 0.473, 0.171, 0.125},
            {0.000, 0.013, 0.041, 0.088, 0.391, 0.312, 0.155},
            {0.000, 0.008, 0.036, 0.083, 0.364, 0.235, 0.274}

    };
    // Matrix
    double[][] transition;
    //initial state
    int M = 1;
    // number of states
    int N = 7;
    // current state
    int state = M;
    // numbers of steps for coming from initial state to 0 state
    int steps = 0;
    // Array of steps
    ArrayList stateList = new ArrayList();
    //Create transition Matrix
    // true = default Matrix
    // false = clear
    public double[][] createArray(boolean setDefault) {
        if (setDefault) {
            transition = transition2;
        } else {
            transition = new double[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    transition[i][j] = 0;
                }
            }
        }
        return transition;
    }

    public boolean isArrayValid() {
        double temp = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                temp += transition[i][j];
            }
            if ((temp - 1) < 0.00001 && temp != 0.0) {
                temp = 0;
            } else {
                System.out.println("False statment for transition matrix");
                return false;
            }
        }
        return true;
    }

    public void setTransition(double[][] matrix) {
        this.transition = matrix;
    }

    public int solveChain() {
        if (isArrayValid()) {
            steps = 0;
            state = M;
            stateList.removeAll(stateList);
            while (state > 0) {
                stateList.add(state);
                steps++;
                double r = Math.random();
                double sum = 0.0;

                // determine next state
                for (int j = 0; j < N; j++) {
                    sum += transition[state][j];
                    if (r <= sum) {
                        state = j;
                        break;
                    }
                }

            }
            return steps;
        } else {
            return -1;
        }
    }
    //Set length
    public void setN(int n) {
        this.N = n;
    }
    //Set initial state
    public void setM(int m) {
        if (m >= N) {
            System.out.println("Too big start value");
            this.M = N - 1;
        } else {
            this.M = m;
        }

    }
    //Get length
    public int getN() {
        return N;
    }

    public int getM() {
        return M;
    }

    public ArrayList getStateList() {
        return stateList;
    }

    public void clearStateList(){
        stateList.removeAll(stateList);
    }
}

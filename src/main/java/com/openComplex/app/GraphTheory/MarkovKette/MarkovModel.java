package com.openComplex.app.GraphTheory.MarkovKette;

/**
 * Created by strange on 27/08/15.
 */

import java.util.ArrayList;

/******************************************************************************
 *  Compilation:  javac MarkovChain.java
 *  Execution:    java MarkovChain
 *
 *  Computes the expected time to go from state N-1 to state 0
 *
 *  Data taken from Glass and Hall (1949) who distinguish 7 states
 *  in their social mobility study:
 *
 *   1. Professional, high administrative
 *   2. Managerial
 *   3. Inspectional, supervisory, non-manual high grade
 *   4. Non-manual low grade
 *   5. Skilled manual
 *   6. Semi-skilled manual
 *   7. Unskilled manual
 *
 *   See also Happy Harry, 2.39.
 *
 ******************************************************************************/

public class MarkovModel {
    double[][] transition = { { 0.386, 0.147, 0.202, 0.062, 0.140, 0.047, 0.016},
                              { 0.107, 0.267, 0.227, 0.120, 0.207, 0.052, 0.020},
                              { 0.035, 0.101, 0.188, 0.191, 0.357, 0.067, 0.061},
                              { 0.021, 0.039, 0.112, 0.212, 0.431, 0.124, 0.061},
                              { 0.009, 0.024, 0.075, 0.123, 0.473, 0.171, 0.125},
                              { 0.000, 0.103, 0.041, 0.088, 0.391, 0.312, 0.155},
                              { 0.000, 0.008, 0.036, 0.083, 0.364, 0.235, 0.274}
    };
    int M = 1;
    int N = 7;                        // number of states
    int state = N - M;                // current state
    int steps = 0;
    ArrayList stateList = new ArrayList();
    public MarkovModel() {

    }
    public int solveChain(){
        steps = 0;
        state = N - M;
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
        System.out.println("The number of steps =  " + steps);
        return steps;
    }
    public void setN(int n){
        this.N = n;
    }
    public void setM(int m){
        this.M = m;
    }
    public ArrayList getStateList(){
        return stateList;
    }
}

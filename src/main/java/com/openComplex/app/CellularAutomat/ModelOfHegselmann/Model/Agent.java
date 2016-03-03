package com.openComplex.app.CellularAutomat.ModelOfHegselmann.Model;

import java.util.ArrayList;

/**
 * Created by MatthiasFuchs on 26.02.16.
 */
public class Agent {
    private int agentNumber;
    private  double epsilon;
    private ArrayList<Agent> neighbors = new ArrayList<>();
    private double[] x;

    public Agent(int agentNumber, double[] initialX, double epsilon) {
        this.agentNumber = agentNumber;
        this.epsilon = epsilon;
        x = initialX;
    }

    public void setX (double[] x) {
        this.x = x;
    }

    public double[] getX () {
        return x;
    }

    public void calculateX() {
        int numberOfNeighbor = neighbors.size();
        for(int i = 0; i < x.length; i++) {
            x[i] = 0.0;
            for(int j = 0; j < numberOfNeighbor; j++) {
                x[i] = x[i] + neighbors.get(j).getX()[i];
            }
        }
        for(int i = 0; i < x.length; i++) {
            x[i] = x[i]/(double) numberOfNeighbor;
        }
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;

    }

    public void calculateNeighborhood(ArrayList<Agent> agents) {
        neighbors.removeAll(neighbors);
        int numberOfAgents = agents.size();
        Agent agent;
        double euclidean;
        for(int i = 0; i < numberOfAgents; i++) {
            if(agentNumber != i) {
                agent = agents.get(i);
                euclidean = calculateEuclidean(x, agent.getX());
                if(euclidean <= epsilon) {
                    neighbors.add(agent);
                }
            }

        }
    }

    private double calculateEuclidean(double[] x, double[] y) {
        double euclidean = 0.0;
        for(int i = 0; i < x.length; i++) {
            euclidean = euclidean + Math.pow((x[i] + y[i]), 2.0);
        }
        euclidean = Math.sqrt(euclidean);
        return euclidean;
    }
}

package com.openComplex.app.CellularAutomat.ModelOfHegselmann.Model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by MatthiasFuchs on 28.02.16.
 */


public class Field extends JPanel {
    private int dimension;
    private int numberOfAgents;
    private ArrayList<Agent> agents = new ArrayList<>();
    private double epsilon;
    private int maxStep;
    private double[][][] savedSteps;
    private int step = 0;
    private int selectedDimension = 1;

    public Field(int numberOfAgents, int dimension, double epsilon, int maxStep) {
        this.numberOfAgents = numberOfAgents;
        this.dimension = dimension;
        this.epsilon = epsilon;
        this.maxStep = maxStep;
        init();
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public void setSelectedDimension(int selectedDimension) {
        this.selectedDimension = selectedDimension;
    }

    public void setNumberOfAgents(int numberOfAgents) {
        this.numberOfAgents = numberOfAgents;
    }

    public void setMaxStep(int maxStep) {
        this.maxStep = maxStep;
    }

    public void init() {
        step = 0;
        savedSteps = new double[maxStep][numberOfAgents][];
        Agent agent;
        double[] initialX;
        for(int i = 0; i < numberOfAgents; i++) {
            initialX = initXRandom();
            agent = new Agent(i, initialX, epsilon);
            agents.add(agent);
        }
        for(int i = 0; i < numberOfAgents; i++) {
            agent = agents.get(i);
            agent.calculateNeighborhood(agents);
            agents.set(i, agent);
        }
    }

    private double[] initX(int agentNumber) {
        double[] x = new double[dimension];
        for(int i = 0; i < dimension; i++) {
            x[i] = (double) agentNumber / (double) numberOfAgents;
        }
        return x;
    }

    private double[] initXRandom() {
        double[] x = new double[dimension];
        for(int i = 0; i < dimension; i++) {
            x[i] = Math.random();
        }
        return x;
    }

    public void nextStep() {
        step++;
        Agent agent;
        for(int i = 0; i < numberOfAgents; i++) {
            agent = agents.get(i);
            agent.calculateX();
            savedSteps[step][i] = agent.getX();
            agents.set(i, agent);
        }
        for(int i = 0; i < numberOfAgents; i++) {
            agent = agents.get(i);
            agent.calculateNeighborhood(agents);
            agents.set(i, agent);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int xOffset = 30;
        int yOffset = 30;
        int width = getWidth();
        int height = getHeight();
        // X und Y axis
        g.drawLine(xOffset, height-yOffset, width-xOffset, height-yOffset);
        g.drawLine(xOffset, height-yOffset, xOffset, yOffset);

        g.drawLine(xOffset, height-yOffset, xOffset, height-yOffset+5);
        g.drawLine(width-xOffset, height-yOffset, width-xOffset, height-yOffset+5);

        g.drawLine(xOffset, height-yOffset, xOffset-5, height-yOffset);
        g.drawLine(xOffset, yOffset, xOffset-5, yOffset);

        for(int i = 0; i < numberOfAgents; i++) {
            for(int j = 0; j < step; j++) {

            }
        }
    }
}

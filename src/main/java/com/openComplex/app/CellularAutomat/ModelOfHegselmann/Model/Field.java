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
    private int selectedDimension = 0;

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
        savedSteps = new double[maxStep][numberOfAgents][dimension];
        Agent agent;
        double[] initialX;
        for(int i = 0; i < numberOfAgents; i++) {
            initialX = initX(i);
            agent = new Agent(i, initialX, epsilon);
            savedSteps[step][i] = agent.getX();
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
            for(int j = 0; j < savedSteps[0][0].length; j++) {
                System.out.print(savedSteps[step][i][j] + " ");
            }
            System.out.println();
            agents.set(i, agent);
        }
        System.out.println();
        for(int i = 0; i < numberOfAgents; i++) {
            agent = agents.get(i);
            agent.calculateNeighborhood(agents);
            agents.set(i, agent);
        }
        repaint();
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

        ArrayList<Color> colors = createColorGradient();
        double value;
        int xAxisLength = width - 2* xOffset;
        int yAxisLength = height - 2* yOffset;
        int xPosStart;
        int yPosStart;
        int xPosStop = 0;
        int yPosStop = 0;

        for(int i = 0; i < numberOfAgents; i++) {
            g.setColor(colors.get(i));
            g.drawRect(10*i, 10*i, 5, 5);
            for(int j = 0; j < step; j++) {
                value = savedSteps[j][i][selectedDimension];
                if(j == 0) {
                    xPosStart = xOffset;
                    yPosStart = (int) (value * yAxisLength + yOffset);
                    xPosStop = xOffset;
                    yPosStop = (int) (value * yAxisLength + yOffset);
                } else {
                    xPosStart = xPosStop;
                    yPosStart = yPosStop;
                    xPosStop = j/step * xAxisLength + xOffset;
                    yPosStop = (int) (value * yAxisLength + yOffset);
                }
                g.drawLine(xPosStart, yPosStart, xPosStop, yPosStop);
            }
        }
    }



    private ArrayList<Color> createColorGradient() {
        int colorIndex = (int) Math.ceil((double)numberOfAgents/(double)6);
        ArrayList<Color> colors = new ArrayList<Color>();
        for (int r=0; r<colorIndex; r++){
            colors.add(new Color(r*255/colorIndex,       255,         0));
        }
        for (int g=colorIndex; g>0; g--) {
            colors.add(new Color(      255, g*255/colorIndex,         0));
        }
        for (int b=0; b<colorIndex; b++) {
            colors.add(new Color(      255,         0, b*255/colorIndex));
        }
        for (int r=colorIndex; r>0; r--) {
            colors.add(new Color(r*255/colorIndex,         0,       255));
        }
        for (int g=0; g<colorIndex; g++) {
            colors.add(new Color(        0, g*255/colorIndex,       255));
        }
        for (int b=colorIndex; b>0; b--) {
            colors.add(new Color(        0,       255, b*255/colorIndex));
        }
        return colors;
    }
}

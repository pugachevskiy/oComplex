package com.openComplex.app.CellularAutomat.ModelOfHegselmann.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by MatthiasFuchs on 28.02.16.
 */


public class Field extends JPanel {
    private int dimension;
    private int numberOfAgents;
    private ArrayList<Agent> agents = new ArrayList<>();
    private double epsilon;
    private int maxStep;
    private int step = 0;
    private int selectedDimension = 0;
    private int selectedAgent = 10;
    private SavedSteps savedSteps;

    public Field(int numberOfAgents, int dimension, double epsilon, int maxStep) {
        this.numberOfAgents = numberOfAgents;
        this.dimension = dimension;
        this.epsilon = epsilon;
        this.maxStep = maxStep;
        savedSteps = new SavedSteps(maxStep);
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
        System.out.println("init");
        step = 0;
        double[][] savedStep = new double[numberOfAgents][];
        Agent agent;
        double[] initialX;
        for(int i = 0; i < numberOfAgents; i++) {
            initialX = initX(i);
            agent = new Agent(i, initialX, epsilon);
            savedStep[i] = agent.getX();
            //System.out.println("step: " + step + " agent: " + i + " value: " + savedSteps[step][i][0]);
            agents.add(agent);
        }
        savedSteps.addStep(step, savedStep);
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
        System.out.println("nextStep");
        step++;
        double[][] savedStep = new double[numberOfAgents][];
        Agent agent;
        for(int i = 0; i < numberOfAgents; i++) {
            agent = agents.get(i);
            savedStep[i] = agent.calculateX();

            agents.set(i, agent);
        }
        savedSteps.addStep(step, savedStep);
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
        Graphics2D g2d = (Graphics2D) g;
        int xOffset = 30;
        int yOffset = 30;
        int width = getWidth();
        int height = getHeight();

        ArrayList<Color> colors = createColorGradient();
        double[][] savedStep;
        double value;
        int xAxisLength = width - 2* xOffset;
        int yAxisLength = height - 2* yOffset;

        int xPosPreSelectedAgent = 0;
        int yPosPreSelectedAgent = 0;
        int[] yPosSelectedAgent = new int[maxStep];
        int[] xPosPre = new int[numberOfAgents];
        int[] yPosPre = new int[numberOfAgents];
        int xPos;
        int yPos;

        for(int i = 0; i < step; i++) {
            savedStep = savedSteps.getStep(i);
            for(int j = 0; j < numberOfAgents; j++) {
                g2d.setColor(colors.get(j));
                value = savedStep[j][selectedDimension];
                //System.out.println("step: " + i + " agent: " + j + " value: " + value);
                if(j == selectedAgent) {
                    yPosSelectedAgent[i] = (yOffset + yAxisLength) - (int) (value * yAxisLength);
                }
                if(i == 0) {
                    xPosPre[j] = xOffset;
                    yPosPre[j] = (yOffset + yAxisLength) - (int) (value * yAxisLength);
                } else {
                    xPos = (int) ((double)i/ (double)maxStep * xAxisLength + xOffset);
                    yPos = (yOffset + yAxisLength) - (int) (value * yAxisLength);
                    g2d.drawLine(xPosPre[j], yPosPre[j], xPos, yPos);
                    xPosPre[j] = xPos;
                    yPosPre[j] = yPos;
                }
            }
        }
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.WHITE);
        for(int i = 0; i < step; i++) {
            if(i == 0) {
                xPosPreSelectedAgent = xOffset;
                yPosPreSelectedAgent = yPosSelectedAgent[i];
            } else {
                xPos = (int) ((double)i/ (double)maxStep * xAxisLength + xOffset);
                yPos = yPosSelectedAgent[i];
                g2d.drawLine(xPosPreSelectedAgent, yPosPreSelectedAgent, xPos, yPos);
                xPosPreSelectedAgent = xPos;
                yPosPreSelectedAgent = yPos;
            }
        }

        // X und Y axis
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.BLACK);
        g2d.drawLine(xOffset, height - yOffset, width - xOffset, height - yOffset);
        g2d.drawLine(xOffset, height - yOffset, xOffset, yOffset);
        g2d.drawLine(xOffset, height-yOffset, xOffset, height-yOffset+3);
        g2d.drawLine(width-xOffset, height-yOffset, width-xOffset, height-yOffset+3);
        g2d.drawLine(xOffset, height-yOffset, xOffset-3, height-yOffset);
        g2d.drawLine(xOffset, yOffset, xOffset - 3, yOffset);

        // Axis caption
        g2d.drawString("0", 15, height - 15);
        g2d.drawString(Integer.toString(maxStep), xOffset + xAxisLength - 8, height -15);
        g2d.drawString("Step", width/2, height - 15);
        AffineTransform origin = g2d.getTransform();
        g2d.rotate(-Math.PI / 2);
        g2d.drawString("Agent", -height / 2, 20);
        g2d.drawString(Integer.toString(numberOfAgents), -yOffset - 10, 20);
        g2d.setTransform(origin);
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

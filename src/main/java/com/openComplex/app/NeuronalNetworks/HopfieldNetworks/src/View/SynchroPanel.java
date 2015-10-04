package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.View;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


/**
 * Draws the table
 */
public class SynchroPanel extends JFrame {

    private Table table;

    private final String STEP = "Step", NET_INPUT = "Net-Input", ACTIVE = "Active state", NEXT_STATE = "Next State";
    private final Font font = GUI.font;
    private final int fontSize = 10;
    private final int border = 60;
    private int steps = Main.stepListString.size();
    private final int columnSize = font.getSize() + 15;
    private boolean syncronized = true;

    public SynchroPanel() {

        syncronized  = Main.gui.getSelectedState();

        if(syncronized)
            this.setTitle("Calculating symetrical.");
        else
            this.setTitle("Calculating asymetrical");
        this.setDefaultLookAndFeelDecorated(false);
        this.setLayout(new BorderLayout());
        this.add(table = new Table(), BorderLayout.CENTER);
        this.setBounds(350, 300, calculateWidth(), calculateHeight());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }


    class Table extends JPanel {

        private JFrame parent;

        private int width, height, xValueStep, xValueInput, xValueActive, xValueNext;
        private int border = 50;

        public void paintComponent(Graphics g) {

            if(syncronized)
                paintSynchronous(g);
            else
                paintAsynchronous(g);

        }


        private int calculateMaxLength(ArrayList<String> list) {
            int maxLength = 0;

            for(int i=0; i<list.size(); i++) {
                if(list.get(i).length()>maxLength)
                    maxLength = list.get(i).length();
            }

            return maxLength;
        }

        public void paintSynchronous(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            //Setup various options
            g2.setStroke(new BasicStroke(2));
            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

            width = this.getWidth();
            height = this.getHeight();

            //Calculate dynamic table points
            xValueStep = 70;
            xValueInput = xValueStep + calculateMaxLength(Main.stepListString)*fontSize + border + 20;
            xValueNext = xValueInput + calculateMaxLength(Main.nextStepListString)*fontSize + border;

            //System.out.println("Step: " + xValueStep);
            // System.out.println("Input: " + xValueInput);
            // System.out.println("Active: " + xValueActive);
            // System.out.println("Next: " + xValueNext);

            //quer
            g2.drawLine(0, columnSize, width, columnSize);
            //hoch
            g2.drawLine(xValueStep,   5, xValueStep,   height);
            g2.drawLine(xValueInput, 5, xValueInput, height);

            //Draw caption
            g2.drawString(STEP, 13, 25);
            g2.drawString(NET_INPUT, (xValueInput+xValueStep)/2-NET_INPUT.length()*5, 25);
            g2.drawString(NEXT_STATE, (xValueNext+xValueInput)/2-NEXT_STATE.length()*5, 25);

            //Draw steps
            for(int i=0; i<steps; i++) {
                g2.drawString(""+i, 30, 50+i*20);
            }

            g2.setStroke(new BasicStroke(1));
            //Draw vectors
            for(int i=0; i<steps; i++) {
                g2.drawString(Main.stepListString.get(i), (xValueInput+xValueStep)/2-Main.stepListString.get(i).length()*5-Main.size, 50+20*i);
                g2.drawString(Main.nextStepListString.get(i), (xValueNext+xValueInput)/2-Main.nextStepListString.get(i).length()*5-Main.size, 50+20*i);
                g2.drawLine(0, 55+20*i, width, 55+20*i);
            }
            for(int i=0; i<Main.nextStepListString.size(); i++) {
                g2.drawString(Main.nextStepListString.get(i), (xValueNext+xValueInput)/2-Main.nextStepListString.get(i).length()*5-Main.size, 50+20*i);
            }
        }

        public void paintAsynchronous(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            //Setup various options
            g2.setStroke(new BasicStroke(2));
            g.setColor(Color.black);
            g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));

            width = this.getWidth();
            height = this.getHeight();

            //Calculate dynamic table points
            xValueStep = 70;
            xValueInput = xValueStep + calculateMaxLength(Main.stepListString)*fontSize + border + 20;

            int activeLength = calculateMaxLength(Main.differencesString);
            activeLength = activeLength>ACTIVE.length() ? activeLength : ACTIVE.length();

            xValueActive = xValueInput + activeLength*fontSize + border;
            xValueNext = xValueActive + calculateMaxLength(Main.nextStepListString)*fontSize + border;

            //System.out.println("Step: " + xValueStep);
            // System.out.println("Input: " + xValueInput);
            // System.out.println("Active: " + xValueActive);
            // System.out.println("Next: " + xValueNext);


            //quer
            g2.drawLine(0, columnSize, width, columnSize);
            //hoch
            g2.drawLine(xValueStep,   5, xValueStep,   height);
            g2.drawLine(xValueInput, 5, xValueInput, height);
            g2.drawLine(xValueActive, 5, xValueActive, height);
            //Draw caption
            g2.drawString(STEP, 13, 25);
            g2.drawString(NET_INPUT, (xValueInput+xValueStep)/2-NET_INPUT.length()*5, 25);
            g2.drawString(ACTIVE, (xValueActive+xValueInput)/2-ACTIVE.length()*5, 25);
            g2.drawString(NEXT_STATE, (xValueNext+xValueActive)/2-NEXT_STATE.length()*5, 25);

            //Draw steps
            for(int i=0; i<steps; i++) {
                g2.drawString(""+i, 30, 50+i*20);
            }

            g2.setStroke(new BasicStroke(1));
            //Draw vectors
            for(int i=0; i<steps; i++) {
                g2.drawString(Main.stepListString.get(i), (xValueInput+xValueStep)/2-Main.stepListString.get(i).length()*5-Main.size, 50+20*i);
                g2.drawString(Main.differencesString.get(i), (xValueActive+xValueInput)/2-Main.differencesString.get(i).length()*5, 50+20*i);
                g2.drawString(Main.nextStepListString.get(i), (xValueNext+xValueActive)/2-Main.nextStepListString.get(i).length()*5-Main.size, 50+20*i);
                g2.drawLine(0, 55+20*i, width, 55+20*i);
            }
            for(int i=0; i<Main.nextStepListString.size(); i++) {
                g2.drawString(Main.nextStepListString.get(i), (xValueNext+xValueActive)/2-Main.nextStepListString.get(i).length()*5-Main.size, 50+20*i);
            }
        }
    }

    private int calculateWidth(){
        int width = 70;
        width += table.calculateMaxLength(Main.stepListString)*fontSize + border;

        if(!syncronized) {
            int activeLength = table.calculateMaxLength(Main.differencesString);
            activeLength = activeLength>ACTIVE.length() ? activeLength : ACTIVE.length();

            width += activeLength*fontSize + border;
        }
        width += table.calculateMaxLength(Main.nextStepListString)*fontSize + border;

        return width;
    }

    private int calculateHeight(){

        return steps*columnSize + 200;
    }

}

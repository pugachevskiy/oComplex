package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.View;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Controller.GridListener;
import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Controller.Main;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;

/**
 * on 14.07.2015.
 */
public class OptionPanel extends JPanel {

    private static final String THRESHOLD = "                        Threshold vektor:                        ";
    private static final String START = "Z0 (start state):";
    private int size;

    public static JFormattedTextField[] thresholdArray = new JFormattedTextField[15];
    public static JFormattedTextField[] startStateArray = new JFormattedTextField[15];

    public OptionPanel() {

        initateTextfields();
        this.setLayout(new GridLayout(4, 0));
        JLabel threshold = new JLabel(THRESHOLD);
        threshold.setHorizontalAlignment(JLabel.CENTER);
        threshold.setVerticalAlignment(JLabel.CENTER);
        this.add(threshold);
        this.add(createthresholdPanel());
        JLabel startState = new JLabel(START);
        startState.setHorizontalAlignment(JLabel.CENTER);
        startState.setVerticalAlignment(JLabel.CENTER);
        this.add(startState);

        this.add(createstartPanel());

    }

    private JPanel createthresholdPanel() {
        JPanel thresholdPanel = new JPanel();
        JLabel openBrace = new JLabel("(");
        JLabel closeBrace = new JLabel(")T");
        openBrace.setFont(GUI.fontVectors);
        closeBrace.setFont(GUI.fontVectors);
        thresholdPanel.add(openBrace);
        for (int i = 0; i < Main.size; i++) {
            thresholdPanel.add(thresholdArray[i]);
        }
        thresholdPanel.add(closeBrace);
        return thresholdPanel;
    }

    private JPanel createstartPanel() {
        JPanel startPanel = new JPanel();
        JLabel openBrace = new JLabel("(");
        JLabel closeBrace = new JLabel(")T");
        openBrace.setFont(GUI.fontVectors);
        closeBrace.setFont(GUI.fontVectors);
        startPanel.add(openBrace);
        for (int i = 0; i < Main.size; i++) {
            startPanel.add(startStateArray[i]);
        }
        startPanel.add(closeBrace);
        return startPanel;
    }

    private void initateTextfields() {
        GridListener gridListener = new GridListener();
        FocusListener focusListener = new FocusListener() {
            public void focusLost(FocusEvent e) {
                //if textfield empty
                if (((JFormattedTextField) e.getSource()).getText().equals("")) {
                    ((JFormattedTextField) e.getSource()).setText("1");
                }
                int textFieldValue = 1;
                try {
                    textFieldValue = Integer.parseInt(((JFormattedTextField) e.getSource()).getText());
                } catch (NumberFormatException nfe) {
                    ((JFormattedTextField) e.getSource()).setText("1");
                }
                //if there are zeros in the textfield
                if (textFieldValue == 0 && ((JFormattedTextField) e.getSource()).isEditable()) {
                    ((JFormattedTextField) e.getSource()).setText("1");
                }
            }

            public void focusGained(FocusEvent e) {
            }
        };

        //Defines number format
        NumberFormat format = NumberFormat.getInstance();
        format.setGroupingUsed(false);
        format.setMaximumIntegerDigits(2);      //Länge max. 2
        format.setMinimumIntegerDigits(1);      //Länge min. 1
        format.setMaximumFractionDigits(0);     //keine Kommazahlen
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setAllowsInvalid(false);

        //Initiates the textfield array with customized texfields (concerning font and content)
        for (int i = 0; i < thresholdArray.length; i++) {

            thresholdArray[i] = new JFormattedTextField(formatter);
            thresholdArray[i].setText("-10");
            thresholdArray[i].setFont(GUI.font);
            thresholdArray[i].addFocusListener(focusListener);
            thresholdArray[i].getDocument().addDocumentListener(gridListener);

        }

        for (int i = 0; i < startStateArray.length; i++) {

            startStateArray[i] = new JFormattedTextField(formatter);
            startStateArray[i].setText("-10");
            startStateArray[i].setFont(GUI.font);
            startStateArray[i].addFocusListener(focusListener);
            startStateArray[i].getDocument().addDocumentListener(gridListener);

        }
    }

    /**
     * Creates random values for the matrix
     */
    public void shakeTextFieldArray() {
        int rand;
        for (int i = 0; i < Main.size; i++) {
            rand = (int) (Math.random() * 40 - 20);
            thresholdArray[i].setText("" + rand);
        }
        for (int i = 0; i < Main.size; i++) {
            rand = (int) (Math.random() * 40 - 20);
            startStateArray[i].setText("" + rand);
        }
    }


    public int[] getThreshold() {
        int[] thresholdVector = new int[Main.size];

        for (int i = 0; i < Main.size; i++) {
            thresholdVector[i] = Integer.parseInt(thresholdArray[i].getText());
        }

        return thresholdVector;
    }

    public int[] getStartState() {
        int[] startStateVector = new int[Main.size];

        for (int i = 0; i < Main.size; i++) {
            startStateVector[i] = Integer.parseInt(startStateArray[i].getText());
        }

        return startStateVector;
    }
}

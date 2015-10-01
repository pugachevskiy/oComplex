package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.View;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller.*;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;

/**
 * Creates the left part of the GUI, containing weight matrix and buttons
 */
public class TablePanel extends JPanel {

    private JPanel upperPanel;
    private JPanel lowerPanel;
    private JButton calculateButton;
    private JRadioButton syncronButton, asyncronButton;
    public OptionPanel optionPanel;
    //private JPanel synchroPanel;
    private ActionListener listener;


    public static JFormattedTextField[][] textfieldArray = new JFormattedTextField [15][15];


    //Buttons und JMenuItems für den späteren Zugriff(enable, disable)

    /**
     * Erstellt den linken Teil der GUI, bestehend aus Gewichtsmatrix und Buttons
     */
    public TablePanel() {

        this.setLayout(new BorderLayout());
        initiatetextfieldArray();
        listener = new ButtonListener();
        lowerPanel = createLowerPanel();

        JPanel midPanel = new JPanel();
        midPanel.setLayout(new BorderLayout());
        midPanel.add(optionPanel = new OptionPanel(), BorderLayout.NORTH);
        calculateButton = new JButton("Calculate");
        calculateButton.setActionCommand("Calculate");
        calculateButton.addActionListener(listener);
        midPanel.add(calculateButton, BorderLayout.SOUTH);
        midPanel.add(createRadioPanel(), BorderLayout.CENTER);
        upperPanel = createMatrixPanel();
        this.add(lowerPanel, BorderLayout.SOUTH);
        this.add(midPanel, BorderLayout.CENTER);
        this.add(upperPanel, BorderLayout.NORTH);

    }


    private JPanel createRadioPanel() {
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BorderLayout());

        ButtonGroup buttonGroup = new ButtonGroup();
        syncronButton = new JRadioButton("Synchronous", true);
        asyncronButton = new JRadioButton("Asynchronous");

        buttonGroup.add(syncronButton);
        buttonGroup.add(asyncronButton);

        radioPanel.add(syncronButton, BorderLayout.WEST);
        radioPanel.add(asyncronButton, BorderLayout.EAST);


        return radioPanel;
    }

    /**
     * Creates the Buttons left-down
     */
    private JPanel createLowerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3, 1, 1));

        String [] menuItemArray = {Menu.DRAW, Menu.NEWRANDOM, Menu.LOAD, Menu.SAVE, Menu.SAVEAS, Menu.EXIT};

        //Passt die Buttons an und fügt sie zum 'panel' hinzu
        for(int i=0; i<6; i++) {
            JButton tempButton = new JButton();
            tempButton.addActionListener(listener);
            tempButton.setText(menuItemArray[i]);
            //button.setToolTipText(tooltips[i]);
            tempButton.setActionCommand(menuItemArray[i]);
            tempButton.setPreferredSize(new Dimension(130, 30));
            panel.add(tempButton);
        }
        return panel;
    }

    /**
     * Creates the weight matrix
     */
    private JPanel createMatrixPanel() {
        JPanel panel = new JPanel();

        panel.setLayout(new GridLayout(16, 16, 1, 1));

        panel.add(new JPanel());
        //First line with N1 to N15
        for(int i=0; i<textfieldArray.length; i++) {
            JTextField temp = new JTextField("   N" + (i+1) + "   ");
            temp.setFont(new Font(temp.getFont().getFontName(), Font.BOLD, 14));
            temp.setBorder(BorderFactory.createEmptyBorder());
            temp.setHorizontalAlignment(JTextField.CENTER);
            temp.setEditable(false);
            panel.add(temp);
        }
        //Remaining parts of the matrix
        for(int i=0; i<textfieldArray.length; i++) {
            JTextField temp = new JTextField("   N" + (i+1) + "   ");
            temp.setFont(new Font(temp.getFont().getFontName(), Font.BOLD, 14));
            temp.setBorder(BorderFactory.createEmptyBorder());
            temp.setHorizontalAlignment(JTextField.CENTER);
            temp.setEditable(false);
            panel.add(temp);
            for(int j=0; j<textfieldArray.length; j++) {
                panel.add(textfieldArray[i][j]);
            }
        }


        return panel;
    }

    /**
     *Iteriert durch das Textfieldarray und passt jedes einzelne Feld an das passande NumberFormat an, bzw. legt
     * Grundformatierung fest
     */
    private void initiatetextfieldArray() {
        //When there are invalid inputs, customize textfields
        FocusListener focusListener = new FocusListener() {
            public void focusLost(FocusEvent e) {
                //if textfield empty
                if( ( (JFormattedTextField)e.getSource() ).getText().equals("")) {
                    ( (JFormattedTextField)e.getSource() ).setText("1");
                }
                int textFieldValue = 1;
                try{
                    textFieldValue = Integer.parseInt( ( (JFormattedTextField) e.getSource() ).getText() );
                } catch (NumberFormatException nfe) {
                    ( (JFormattedTextField)e.getSource() ).setText("1");
                }
                //if there are zeros in the textfield
                if(textFieldValue == 0 && ( (JFormattedTextField)e.getSource() ).isEditable()) {
                    ( (JFormattedTextField)e.getSource() ).setText("1");
                }
            }

            public void focusGained(FocusEvent e) {  }
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
        for(int i=0; i<textfieldArray.length; i++) {
            for(int j=0; j<textfieldArray.length; j++) {
                textfieldArray[i][j] = new JFormattedTextField(formatter);
                textfieldArray[i][j].setText("1");
                textfieldArray[i][j].setFont(GUI.font);
                textfieldArray[i][j].addFocusListener(focusListener);
                if(i==j) {
                    textfieldArray[i][j].setText("0");
                    textfieldArray[i][j].setEditable(false);
                }
                textfieldArray[i][j].getDocument().addDocumentListener(new GridListener());
            }
        }
    }

    /**
     * Depending on which size is selected, the size of the matrix will be updated
     * @param size
     */
    public void updateSize(int size) {
        Main.size = size;
        Main.gui.graphPanel.steps = 0;
        Main.gui.graphPanel.selectedNode = -1;
        for(int i=0; i<textfieldArray.length; i++) {
            for(int j=0; j<textfieldArray.length; j++) {
                if(i==j) {
                    //do nothing
                } else if(i>=Main.size || j>=Main.size) {
                    textfieldArray[i][j].setText("0");
                    textfieldArray[j][i].setText("0");
                    textfieldArray[i][j].setEnabled(false);
                } else {
                    if(textfieldArray[i][j].getText().equals("0")) {
                        textfieldArray[i][j].setText("1");
                        textfieldArray[j][i].setText("1");
                    }
                    textfieldArray[i][j].setEnabled(true);
                }
            }
        }

        new Thread() {
            @Override public void run() {
                Main.gui.graphPanel.repaint();
                //Main.gui.tablePanel.getContentPane().removeAll();
                //Main.gui.tablePanel.getContentPane().add(new JPanel());

                //System.out.println(Main.gui.tablePanel.getComponent(1));
                            Main.gui.tablePanel.revalidate();
                            Main.gui.tablePanel.repaint();
                            Main.gui.tablePanel.removeAll();

                Main.gui.tablePanel.add(lowerPanel = createLowerPanel(), BorderLayout.SOUTH);
                JPanel midPanel = new JPanel();
                midPanel.setLayout(new BorderLayout());
                midPanel.add(optionPanel = new OptionPanel(), BorderLayout.NORTH);
                midPanel.add(calculateButton, BorderLayout.SOUTH);
                midPanel.add(createRadioPanel(), BorderLayout.CENTER);
                Main.gui.tablePanel.add(midPanel, BorderLayout.CENTER);
                Main.gui.tablePanel.add(upperPanel = createMatrixPanel(), BorderLayout.NORTH);

                Main.gui.tablePanel.revalidate();
                Main.gui.tablePanel.repaint();

            }
        }.start();
    }

    /**
     * Creates random values for the matrix
     */
    public void shakeTextFieldArray() {
        int rand;
        for(int i=0;i<Main.size; i++) {
            for(int j=i+1; j<Main.size; j++) {
                while((rand=(int)(Math.random()*40-20))==0);
                textfieldArray[i][j].setText("" + rand);
                textfieldArray[j][i].setText("" + rand);
            }
        }
    }

    /**
     * Passt die Textfelder an neu übergebene Werte(z.B. aus einer csv-Datei) an
     */
    public void updateTextFieldArray(String[][] arr) {
        for(int i=0;i<textfieldArray.length-1; i++) {
            for(int j=i+1; j<textfieldArray.length-1; j++) {
                textfieldArray[i][j].setText("" + arr[i][j]);
                textfieldArray[j][i].setText("" + arr[j][i]);
            }
        }
    }

    /**
     * Gibt ein Array mit den Werten der Textfields zurück
     */
    public String[][] getTextfieldValues() {
        String[][] fieldValues = new String[Main.size][Main.size];
        for(int i=0; i<Main.size; i++) {
            for(int j=0; j<Main.size; j++) {
                fieldValues[i][j] = textfieldArray[i][j].getText();
            }
        }
        return fieldValues;
    }

    /*
    Gibt int Array mit Werten zurück
     */
    public int[][] getIntTextfieldValues() {
        int[][] fieldValues = new int[Main.size][Main.size];
        for(int i=0; i<Main.size; i++) {
            for(int j=0; j<Main.size; j++) {
                fieldValues[i][j] = Integer.parseInt(textfieldArray[i][j].getText());
            }
        }
        return fieldValues;
    }


    public boolean getSynchroState() {
        return syncronButton.isSelected();
    }

}

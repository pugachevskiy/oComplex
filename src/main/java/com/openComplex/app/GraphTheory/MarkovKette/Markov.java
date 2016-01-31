package com.openComplex.app.GraphTheory.MarkovKette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * on 27/08/15.
 */
public class Markov implements ActionListener {
    private MarkovModel markovModel;
    private MarkovView gui;

    public Markov() {
        markovModel = new MarkovModel();
        gui = new MarkovView(markovModel.getN(), markovModel.getM());
        gui.addListener(this);
        setDefaultMatrix();
    }

    private void createNewTable() {
        markovModel.setN(gui.getLength());
        markovModel.setM(gui.getStart());
        gui.clearPanel();
        gui.setStep("");
        gui.addTable(gui.getLength(), markovModel.createArray(false));
    }


    private void setDefaultMatrix() {
        markovModel.setN(7);
        markovModel.setM(1);
        gui.setLength(7);
        gui.setStart(1);
        gui.clearPanel();
        gui.setStep("");
        gui.addTable(gui.getLength(), markovModel.createArray(true));
    }

    private void solveMatrix() {
        markovModel.setN(gui.getLength());
        markovModel.setM(gui.getStart());
        gui.setStep("");
        markovModel.setTransition(gui.getTable(markovModel.getN()));
        gui.setStep(String.valueOf(markovModel.solveChain()));
        String step = String.valueOf(markovModel.getM());
        for (int i = 1; i < markovModel.getStateList().size(); i++) {
            step = step + " -> " + String.valueOf(markovModel.getStateList().get(i));
        }
        gui.setStateTextField(step);
        markovModel.clearStateList();
    }

    private void newLength() {
        gui.clearPanel();
        markovModel.setN(gui.getLength());
        markovModel.setM(gui.getStart());
        gui.setStep("");
        gui.addTable(gui.getLength(), markovModel.createArray(false));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                createNewTable();
                break;
            case "Default":
                setDefaultMatrix();
                break;
            case "Solve":
                solveMatrix();
                break;
            default:
                break;
            case "length":
                newLength();
                break;

        }
    }
}
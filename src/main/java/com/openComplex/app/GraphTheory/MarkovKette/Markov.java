package com.openComplex.app.GraphTheory.MarkovKette;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



/**
 * Created by strange on 27/08/15.
 */
public class Markov implements ActionListener {
    private MarkovModel markovModel;
    private MarkovView gui;

    public Markov() {
        markovModel = new MarkovModel();
        gui = new MarkovView(markovModel.getN(), markovModel.createArray(true), markovModel.getM());
        addListener();
    }

    private void addListener() {
        gui.getSolve().addActionListener(this);
        gui.getNewTable().addActionListener(this);
        gui.getSetDefault().addActionListener(this);
        gui.getLengthTextField().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "New":
                markovModel.setN(gui.getLength());
                markovModel.setM(gui.getStart());
                gui.clearPanel();
                gui.setStep("");
                gui.addTable(gui.getLength(), markovModel.createArray(false));
                break;

            case "Default":
                markovModel.setN(7);
                markovModel.setM(1);
                gui.setLength(7);
                gui.setStart(1);
                gui.clearPanel();
                gui.setStep("");
                gui.addTable(gui.getLength(), markovModel.createArray(true));
                break;
            case "Solve":
                markovModel.setN(gui.getLength());
                markovModel.setM(gui.getStart());
                gui.clearPanel();
                gui.setStep("");
                gui.addTable(gui.getLength(), markovModel.transition);
                markovModel.setTransition(gui.getTable(markovModel.getN()));
                gui.setStep(String.valueOf(markovModel.solveChain()));
                String step = String.valueOf(markovModel.getM());
                for (int i = 1; i < markovModel.getStateList().size(); i++) {
                    step = step + " -> " + String.valueOf(markovModel.getStateList().get(i));
                }
                gui.setStateTextField(step);
                markovModel.clearStateList();
                break;
            default:
                break;
            case "length":
                gui.clearPanel();
                markovModel.setN(gui.getLength());
                markovModel.setM(gui.getStart());
                gui.setStep("");
                gui.addTable(gui.getLength(), markovModel.createArray(false));
                break;

        }
    }
}
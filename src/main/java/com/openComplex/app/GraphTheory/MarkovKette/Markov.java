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
        gui = new MarkovView(markovModel.N, markovModel.transition, markovModel.M);
        gui.init();
        addListener();
    }

    private void addListener() {
        gui.getSolve().addActionListener(this);
        gui.getLengthTextField().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        markovModel.setN(Integer.valueOf(gui.getLengthTextField().getText()));
        markovModel.setM(Integer.valueOf(gui.getStartPositionTextField().getText()));

        gui.clearPanel();
        gui.addTable(Integer.valueOf(gui.getLengthTextField().getText()));
        gui.setStep(String.valueOf(markovModel.solveChain()));
        for (int i = 0; i < markovModel.getStateList().size(); i++) {
            gui.setStateTextField(String.valueOf(markovModel.getStateList().get(i)));
            System.out.println(markovModel.getStateList().get(i));
        }
    }

}
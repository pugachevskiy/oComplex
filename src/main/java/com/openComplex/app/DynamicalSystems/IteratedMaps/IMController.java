package com.openComplex.app.DynamicalSystems.IteratedMaps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IMController implements ActionListener {
    private IMView gui;
    private IMModel solver;

    public IMController(){
        gui = new IMView();
        solver = new IMModel();
        gui.view();
        gui.setActionListener(this);
        start();
    }

    public void start() {

        for (int i = 0; i < solver.coeff.length; i++) {
            if(gui.coeff[i].getText().equals("0")) {
                gui.coeff[i].setText("0.00");
            }
            solver.coeff[i] = Double.parseDouble(gui.coeff[i].getText());
        }
        solver.solve();
        gui.setFixpoint(solver.getFix(), solver.getType(), solver.getInfo());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();
        if(temp.equals("ok")) {
          start();
        }
    }
}

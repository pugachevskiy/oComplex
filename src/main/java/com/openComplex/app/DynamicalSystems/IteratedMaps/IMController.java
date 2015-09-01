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
        solver.coeff[4] = Double.parseDouble(gui.e.getText());
        solver.coeff[3] = Double.parseDouble(gui.d.getText());
        solver.coeff[2] = Double.parseDouble(gui.c.getText());
        solver.coeff[1] = Double.parseDouble(gui.b.getText());
        solver.coeff[0] = Double.parseDouble(gui.a.getText());
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

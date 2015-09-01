package com.openComplex.app.DynamicalSystems.IteratedMaps;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IMController implements ActionListener {
    private static IMView gui;
    private static IMModel solver;

    public static IMController control;

    public static void start() {
        gui = new IMView();
        solver = new IMModel();
        control = new IMController();

        gui.view();
        gui.setActionListener(control);

        IMModel.coeff[4] = Double.parseDouble(gui.e.getText());
        IMModel.coeff[3] = Double.parseDouble(gui.d.getText());
        IMModel.coeff[2] = Double.parseDouble(gui.c.getText());
        IMModel.coeff[1] = Double.parseDouble(gui.b.getText());
        IMModel.coeff[0] = Double.parseDouble(gui.a.getText());

        solver.solve();
        gui.setFixpoint(solver.getFix(), solver.getType(), solver.getInfo());

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();
        if(temp.equals("ok")) {

            IMModel.coeff[4] = Double.parseDouble(gui.e.getText());
            IMModel.coeff[3] = Double.parseDouble(gui.d.getText());
            IMModel.coeff[2] = Double.parseDouble(gui.c.getText());
            IMModel.coeff[1] = Double.parseDouble(gui.b.getText());
            IMModel.coeff[0] = Double.parseDouble(gui.a.getText());

            solver.solve();
            gui.setFixpoint(solver.getFix(), solver.getType(), solver.getInfo());

        }
    }
}

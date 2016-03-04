package com.openComplex.app.CellularAutomat.ModelOfHegselmann.Controller;

import com.openComplex.app.CellularAutomat.ModelOfHegselmann.Model.Field;
import com.openComplex.app.CellularAutomat.ModelOfHegselmann.View.View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by MatthiasFuchs on 28.02.16.
 */
public class Controller implements ActionListener {
    private View gui;
    private Field field;

    public Controller() {
        gui = new View();
        field = new Field(10, 1, 0.5, 10);
        gui.setActionListener(this);
        gui.addField(field);
        for(int i = 0; i < 9; i++) {
            field.nextStep();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

package com.openComplex.app.CellularAutomat.GameOfLife2.Controller;

import com.openComplex.app.CellularAutomat.GameOfLife2.Model.Field;
import com.openComplex.app.CellularAutomat.GameOfLife2.View.View;

/**
 * Created by strange on 10/09/15.
 */
public class Controller {
    private Field field;
    private View gui;

    public Controller(){
        field = new Field();
        gui = new View();
        gui.init();
        gui.addField(field);
    }
}

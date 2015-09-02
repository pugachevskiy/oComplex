package com.openComplex.app.DynamicalSystems.Fractals.Controller;

import com.openComplex.app.DynamicalSystems.Fractals.Model.*;
import com.openComplex.app.DynamicalSystems.Fractals.View.viewController;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 22/03/15.
 */


public class Fractals {
    viewController gui = new viewController();
    fractalsModel model = new fractalsModel();
    JPanel fr = null;

    public Fractals() {
        gui.createGui();
        addListener(gui.getSlider(), gui.getComboBox());
        createFractal();
    }

    public void createFractal() {

        switch (model.getBox(gui.getComboBox())) {
            case 0:
                fr = new SierpinskiCarpet(model.getSliderValue(gui.getSlider()));
                model.setDimension(gui.getDimensionLabel(), SierpinskiCarpet.DIMENSION);
                model.setFactor(gui.getFactorLabel(), SierpinskiCarpet.FACTOR);
                model.setCopy(gui.getCopyLabel(),SierpinskiCarpet.COPY);
                break;
            case 1:
                fr = new Minkovski(model.getSliderValue(gui.getSlider()));
                model.setDimension(gui.getDimensionLabel(), Minkovski.DIMENSION);
                model.setFactor(gui.getFactorLabel(), Minkovski.FACTOR);
                model.setCopy(gui.getCopyLabel(),Minkovski.COPY);
                break;
            case 2:
                fr = new SierpinskiTriangle(model.getSliderValue(gui.getSlider()));
                model.setDimension(gui.getDimensionLabel(), SierpinskiTriangle.DIMENSION);
                model.setFactor(gui.getFactorLabel(), SierpinskiTriangle.FACTOR);
                model.setCopy(gui.getCopyLabel(),SierpinskiTriangle.COPY);
                break;
            case 3:
                fr = new KochCurve(model.getSliderValue(gui.getSlider()));
                model.setDimension(gui.getDimensionLabel(), KochCurve.DIMENSION);
                model.setFactor(gui.getFactorLabel(), KochCurve.FACTOR);
                model.setCopy(gui.getCopyLabel(),KochCurve.COPY);
                break;
            default:
                break;
        }

        model.addFractal(fr, gui.getFractalPanel());
        model.updateFractal(gui.getFractalPanel());

    }

    public void addListener(JSlider slider, JComboBox comboBox) {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setIter(gui.getIterLabel(), gui.getSlider());
                model.deleteFractal(fr, gui.getFractalPanel());
                createFractal();
                model.updateFractal(gui.getFractalPanel());
            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.deleteFractal(fr, gui.getFractalPanel());
                createFractal();
                model.updateFractal(gui.getFractalPanel());
            }
        });
    }
}

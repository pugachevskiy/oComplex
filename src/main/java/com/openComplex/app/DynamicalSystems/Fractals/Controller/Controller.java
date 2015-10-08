package com.openComplex.app.DynamicalSystems.Fractals.Controller;


import com.openComplex.app.DynamicalSystems.Fractals.Fractal;
import com.openComplex.app.DynamicalSystems.Fractals.FractalCreator;
import com.openComplex.app.DynamicalSystems.Fractals.FractalsCollection;
import com.openComplex.app.DynamicalSystems.Fractals.FractalsCreator;
import com.openComplex.app.DynamicalSystems.Fractals.Model.Model;
import com.openComplex.app.DynamicalSystems.Fractals.View.View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by laptop on 29.06.2015.
 */
public class Controller {
    FractalsCreator fractalsCreator = new FractalCreator();
    View gui = new View();
    Model model = new Model();
    Fractal fractal;
    ListSelectionListener listSelectionListener;
    boolean flag = false;
    boolean search = false;

    public Controller() {
        gui.createGui();
        addListener(gui.getSlider(), gui.getComboBox());
        createCombobox();
        addListenerSearch(gui.getSearchButton());
        addListenerReset();
        addStartButtonListener();
    }

    private void createCombobox() {
        for (int i = 0; i < FractalsCollection.FRACTALS.size(); i++) {
            gui.listModel.addElement(FractalsCollection.FRACTALS.get(i).get(0));
        }
        gui.getComboBox().setSelectedIndex(0);

    }

    public void createFractal() {
        fractal = fractalsCreator.getFractal(model.getBox(gui.getComboBox()), model.getSliderValue(gui.getSlider()));
        gui.getFractalPanel().setBackground(Color.GRAY);
        model.setDimension(gui.getDimensionLabel(), fractal.getDimension());
        model.setCopy(gui.getCopyLabel(), fractal.getCopy());
        model.setFactor(gui.getFactorLabel(), fractal.getFactor());
        model.setDicription(gui.getDiscriptionLabel(), fractal.getDicription());
        model.addFractal(fractal, gui.getFractalPanel());
        gui.getFractalPanel().grabFocus();
        gui.updateDescription(FractalsCollection.FRACTALS.get(gui.getComboBox().getSelectedIndex()).get(4));
        model.updateFractal(gui.getFractalPanel());

    }

    private void clearComboBox() {
        gui.getComboBox().removeListSelectionListener(listSelectionListener);
        gui.listModel.clear();
        createCombobox();
        gui.getComboBox().addListSelectionListener(listSelectionListener);
    }

    private void addListener(JSlider slider, JList comboBox) {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setIter(gui.getIterLabel(), gui.getSlider());
                model.deleteFractal(gui.getFractalPanel());
                createFractal();
                model.updateFractal(gui.getFractalPanel());
            }
        });

        comboBox.addListSelectionListener(listSelectionListener = new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!search) {
                    model.deleteFractal(gui.getFractalPanel());
                    createFractal();
                    model.updateFractal(gui.getFractalPanel());
                }
            }
        });
    }

    private void addListenerSearch(JButton button) {
        button.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         String searchCopy = gui.getSearchCopyTextField().getText();
                                         String searchFactor = gui.getSearchFactorTextField().getText();
                                         int counter = 0;
                                         for (int i = 0; i < FractalsCollection.FRACTALS.size(); i++) {
                                             if ((searchCopy.equals("")) && (searchFactor.equals(""))) {
                                                 createCombobox();
                                                 break;
                                             }
                                             if ((!searchCopy.equals("")) && (searchFactor.equals(""))) {
                                                 if (FractalsCollection.FRACTALS.get(i).get(1).equals(searchCopy)) {
                                                     counter++;
                                                     search = true;
                                                     gui.listModel.addElement(FractalsCollection.FRACTALS.get(i).get(0));
                                                     gui.getComboBox().setSelectedIndex(gui.listModel.getSize() - 1);
                                                 }
                                             }
                                             if ((searchCopy.equals("")) && (!searchFactor.equals(""))) {
                                                 if (FractalsCollection.FRACTALS.get(i).get(2).equals(searchFactor)) {
                                                     counter++;
                                                     search = true;
                                                     gui.listModel.addElement(FractalsCollection.FRACTALS.get(i).get(0));
                                                     gui.getComboBox().setSelectedIndex(gui.listModel.getSize() - 1);
                                                 }
                                             }
                                             if ((!searchCopy.equals("")) && (!searchFactor.equals(""))) {
                                                 if (FractalsCollection.FRACTALS.get(i).get(2).equals(searchFactor)
                                                         && FractalsCollection.FRACTALS.get(i).get(1).equals(searchCopy)) {
                                                     counter++;
                                                     search = true;
                                                     gui.listModel.addElement(FractalsCollection.FRACTALS.get(i).get(0));
                                                     gui.getComboBox().setSelectedIndex(gui.listModel.getSize() - 1);
                                                 }
                                             }
                                         }
                                         if (counter != 0) {
                                             int all = gui.listModel.getSize();
                                             for (int i = 0; i < all - counter; i++) {
                                                 gui.listModel.remove(0);
                                             }
                                         } else {
                                             clearComboBox();
                                             JOptionPane.showMessageDialog(null, "Kein Fractal");
                                         }
                                         search = false;
                                     }
                                 }

        );

    }

    private void addListenerReset() {
        gui.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearComboBox();
            }
        });
    }

    private void addStartButtonListener() {
        gui.getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = !flag;
                if (flag){
                    gui.getStartButton().setText("Stop");
                } else {
                    gui.getStartButton().setText("Start");
                }
                new Thread() {
                    public void run() {
                        try {
                            while (flag) {
                                if (gui.getSlider().getValue() < 7) {
                                    gui.getSlider().setValue(gui.getSlider().getValue() + 1);
                                } else {
                                    gui.getSlider().setValue(0);
                                }
                                sleep(500);
                            }
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

}

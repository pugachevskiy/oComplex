package com.openComplex.app.DynamicalSystems.Fractals2.Controller;


import com.openComplex.app.DynamicalSystems.Fractals2.Fractal;
import com.openComplex.app.DynamicalSystems.Fractals2.FractalCreator;
import com.openComplex.app.DynamicalSystems.Fractals2.FractalsCollection;
import com.openComplex.app.DynamicalSystems.Fractals2.FractalsCreator;
import com.openComplex.app.DynamicalSystems.Fractals2.Model.Model;
import com.openComplex.app.DynamicalSystems.Fractals2.View.View;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

    public Controller() {
        gui.createGui();
        createCombobox();
        addListener(gui.getSlider(), gui.getComboBox());
        createFractal();
        addListenerSearch(gui.getSearchCopyTextField(), gui.getSearchFactorTextField(), gui.getSearchButton());
        addListenerReset();
    }

    private void createCombobox() {
        for (int i = 0; i < FractalsCollection.FRACTALS.size(); i++) {
            gui.getComboBox().addItem(FractalsCollection.FRACTALS.get(i).get(0));
        }
    }

    public void createFractal() {
        fractal = fractalsCreator.getFractal(model.getBox(gui.getComboBox()), model.getSliderValue(gui.getSlider()));
        model.setDimension(gui.getDimensionLabel(), fractal.getDimension());
        model.setCopy(gui.getCopyLabel(), fractal.getCopy());
        model.setFactor(gui.getFactorLabel(), fractal.getFactor());
        model.setDicription(gui.getDiscriptionLabel(), fractal.getDicription());
        model.addFractal(fractal, gui.getFractalPanel());
        //gui.getFractalPanel().grabFocus();
     //   model.updateFractal(gui.getFractalPanel());
    }

    public void addListener(JSlider slider, JComboBox comboBox) {
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                model.setIter(gui.getIterLabel(), gui.getSlider());
                model.deleteFractal(fractal, gui.getFractalPanel());
                createFractal();
                model.updateFractal(gui.getFractalPanel());
            }
        });
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.deleteFractal(fractal, gui.getFractalPanel());
                createFractal();
                model.updateFractal(gui.getFractalPanel());
            }
        });
    }

    public void addListenerSearch(final JTextField textFieldCopy, final JTextField textFieldFactor, JButton button) {
        button.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         String searchCopy = textFieldCopy.getText();
                                         String searchFactor = textFieldFactor.getText();

                                         int counter = 0;
                                         for (int i = 0; i < FractalsCollection.FRACTALS.size(); i++) {
                                             if ((searchCopy.equals("")) && (searchFactor.equals(""))) {
                                                 createCombobox();
                                             }
                                             if ((!searchCopy.equals("")) && (searchFactor.equals(""))) {
                                                 if (FractalsCollection.FRACTALS.get(i).get(1).equals(searchCopy)) {
                                                     counter++;
                                                     gui.getComboBox().addItem(FractalsCollection.FRACTALS.get(i).get(0));
                                                     gui.getComboBox().setSelectedIndex(gui.getComboBox().getItemCount() - 1);
                                                 }
                                             }
                                             if ((searchCopy.equals("")) && (!searchFactor.equals(""))) {
                                                 System.out.print(searchCopy);
                                                 if (FractalsCollection.FRACTALS.get(i).get(2).equals(searchFactor)) {
                                                     counter++;
                                                     gui.getComboBox().addItem(FractalsCollection.FRACTALS.get(i).get(0));
                                                     gui.getComboBox().setSelectedIndex(gui.getComboBox().getItemCount() - 1);
                                                 }
                                             }
                                             if ((!searchCopy.equals("")) && (!searchFactor.equals(""))) {
                                                 if (FractalsCollection.FRACTALS.get(i).get(2).equals(searchFactor) && FractalsCollection.FRACTALS.get(i).get(1).equals(searchCopy)) {
                                                     counter++;
                                                     gui.getComboBox().addItem(FractalsCollection.FRACTALS.get(i).get(0));
                                                     gui.getComboBox().setSelectedIndex(gui.getComboBox().getItemCount() - 1);
                                                 }
                                             }
                                         }

                                         if (counter != 0)

                                         {
                                             int all = gui.getComboBox().getItemCount();
                                             for (int i = 0; i < all - counter; i++)
                                                 gui.getComboBox().removeItemAt(0);
                                         } else

                                         {
                                             createCombobox();
                                             int all = gui.getComboBox().getItemCount();
                                             for (int i = 0; i < all - FractalsCollection.FRACTALS.size(); i++)
                                                 gui.getComboBox().removeItemAt(0);

                                             JOptionPane.showMessageDialog(null, "Kein Fractal");
                                         }
                                     }
                                 }

        );

    }

    private void addListenerReset() {
        gui.getResetButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCombobox();
                int all = gui.getComboBox().getItemCount();
                for (int i = 0; i < all - FractalsCollection.FRACTALS.size(); i++)
                    gui.getComboBox().removeItemAt(0);
            }
        });
    }

}

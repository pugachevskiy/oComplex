package com.openComplex.app.DynamicalSystems.Fractals.Model;

import com.openComplex.app.DynamicalSystems.Fractals.Fractal;

import javax.swing.*;

public class Model {
    public int getSliderValue(JSlider slider) {
        return slider.getValue();
    }

    public void setIter(JLabel label, JSlider slider) {
        label.setText(String.valueOf(getSliderValue(slider)));
    }

    public String getBox(JList comboBox) {
        return comboBox.getSelectedValue().toString();
    }

    public void addFractal(Fractal fractal, JPanel rootPanel) {
        rootPanel.add(fractal.getPanel());
    }

    public void updateFractal(JPanel rootPanel) {
        rootPanel.revalidate();
    }

    public void deleteFractal(JPanel rootPanel) {
        rootPanel.removeAll();
        //    rootPanel.revalidate();
    }

    public void setDimension(JLabel label, String dim) {
        label.setText(dim);
    }

    public void setFactor(JLabel label, String factor) {
        label.setText(factor);
    }

    public void setCopy(JLabel label, String copy) {
        label.setText(copy);
    }

    public void setDicription(JLabel label, String dicription) {
        label.setText(dicription);
    }

}

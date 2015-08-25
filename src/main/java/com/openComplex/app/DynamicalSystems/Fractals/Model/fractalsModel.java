package com.openComplex.app.DynamicalSystems.Fractals.Model;

import javax.swing.*;

/**
 * Created by strange on 06/06/15.
 */
public class fractalsModel {
    public int getSliderValue(JSlider slider) {
        return slider.getValue();
    }

    public void setIter(JLabel label, JSlider slider) {
        label.setText(String.valueOf(getSliderValue(slider)));
    }

    public int getBox(JComboBox comboBox) {
        return comboBox.getSelectedIndex();
    }

    public void addFractal(JPanel pane, JPanel rootPanel) {
        rootPanel.add(pane);
    }

    public void updateFractal(JPanel rootPanel) {
        rootPanel.updateUI();
    }

    public void deleteFractal(JPanel pane,JPanel rootPanel) {
        rootPanel.remove(pane);
    }

    public void setDimension(JLabel label, double dim) {
        label.setText(String.valueOf(dim));
    }

    public void setFactor(JLabel label, int factor) {
        label.setText(String.valueOf(factor));
    }

    public void setCopy(JLabel label, int copy) {
        label.setText(String.valueOf(copy));
    }


}

package com.openComplex.app.CellularAutomat.ModelOfHegselmann.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by MatthiasFuchs on 28.02.16.
 */
public class View {
    private JFrame frame;

    public View() {
        init();
    }

    private void init() {
        frame = new JFrame("openCoSy - Model of Hegselmann");

        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 600));

        frame.requestFocus();
        frame.pack();
        frame.setVisible(true);
    }
    public void setActionListener(ActionListener actionListener) {

    }

    public void addField(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }

    public void deleteField(JPanel panel) {
        frame.remove(panel);
    }

}

package com.openComplex.app.DynamicalSystems.LogicalPictures;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 01/09/15.
 */
public class FeigenbaumView {
    private JFrame frame;
    private JPanel panel;
    private JButton button1, button2, button3;
    private JLabel r_range, x_range;
    private JCheckBox checkBox1;
    private JTextField textFieldrmin, textFieldxmin, textFieldrmax, textFieldxmax;


    public FeigenbaumView(){
        init();
    }
    private void init(){
        frame = new JFrame("Feigenbaum");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        panel = new JPanel();

        frame.add(panel,BorderLayout.CENTER);
        frame.setVisible(true);
    }

}

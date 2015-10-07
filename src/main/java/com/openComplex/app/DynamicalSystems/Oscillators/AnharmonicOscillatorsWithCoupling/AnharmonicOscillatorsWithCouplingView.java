package com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillatorsWithCoupling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

/**
 * Created by strange on 07/10/15.
 */
public class AnharmonicOscillatorsWithCouplingView {
    private static final int W = 400, H = 240; //graphic window
    private JButton buttonNew, buttonGo;
    private JButton buttonDplus, buttonDmin;
    private JButton buttonReibPlus, buttonReibMin;
    private JCheckBox checkBoxGravity;

    private Choice Chooser;
    private JFrame frame;

    public void init() {

        setLayout(null);
        header = new Canvas();
        header.setBounds(1, 1, W, 40);
        header.setBackground(Color.white);
        can = new Canvas();
        can.setBounds(1, 41, W, H + 50);
        can.setBackground(Color.white);
        can.addMouseListener(new ML());
        can.addMouseMotionListener(new ML());
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setBounds(W + 1, 1, 100, H + 90);
        pan.setBackground(new Color(0, 200, 100));
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));

        but_new = new Button("New");
        but_D_plus = new Button("Spring +");
        but_D_min = new Button("Spring -");
        but_reib_plus = new Button("Friction ++");
        but_reib_min = new Button("Friction --");
        cb_grav = new Checkbox("Gravity", false);
        cb_stop = new Checkbox("Stop/Go", false);
        Choice Chooser = new Choice();
        Chooser.add("Free");
        Chooser.add("Together");
        Chooser.add("Against");

        but_new.setBounds(10, 20, 80, 27);
        but_D_plus.setBounds(10, 60, 80, 27);
        but_D_min.setBounds(10, 95, 80, 27);
        but_reib_plus.setBounds(10, 135, 80, 27);
        but_reib_min.setBounds(10, 170, 80, 27);
        Chooser.setBounds(10, 220, 80, 30);
        cb_grav.setBounds(10, 255, 70, 30);
        cb_grav.setBackground(new Color(0, 200, 100));
        cb_stop.setBounds(10, 290, 70, 30);
        cb_stop.setBackground(new Color(0, 200, 100));

        pan.add(but_new);
        pan.add(but_D_plus);
        pan.add(but_D_min);
        pan.add(but_reib_plus);
        pan.add(but_reib_min);
        pan.add(cb_grav);
        pan.add(cb_stop);
        pan.add(Chooser);
        add(pan);
        add(can);
        add(header);

        but_new.addActionListener(this);
        but_D_plus.addActionListener(this);
        but_D_min.addActionListener(this);
        but_reib_plus.addActionListener(this);
        but_reib_min.addActionListener(this);
        Chooser.addItemListener(this);

        g = can.getGraphics();
        g.setFont(new Font("Verdana", Font.BOLD, 10));

        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana", Font.BOLD, 14));


    }//init()

    public void addListener(ActionListener listener, ItemListener itemListener) {
        buttonNew.addActionListener(listener);
        buttonDplus.addActionListener(listener);
        buttonDmin.addActionListener(listener);
        buttonReibMin.addActionListener(listener);
        buttonReibPlus.addActionListener(listener);
        buttonGo.addActionListener(listener);
        Chooser.addItemListener(itemListener);
    }

    public void addPanel(JPanel panel) {
        frame.add(panel, BorderLayout.CENTER);
    }

    public boolean getCheckBoxGravity(){
        return checkBoxGravity.isSelected();
    }

}

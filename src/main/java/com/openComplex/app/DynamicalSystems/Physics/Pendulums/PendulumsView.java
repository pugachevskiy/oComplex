package com.openComplex.app.DynamicalSystems.Physics.Pendulums;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by strange on 05/10/15.
 */

public class PendulumsView extends JPanel {
    public static final int Lx = 260, Ly = 260; //graphic window
    private JFrame frame;
    private JButton buttonNew, buttonGo;
    private JButton buttonPhi22Plus, buttonPhi22Min;

    public void init() {
        frame = new JFrame("Pendulum");
        frame.setSize(700,400);
        frame.setLayout(new BorderLayout());
        Panel pan = new Panel();
        pan.setLayout(null);
        pan.setBounds(2 * Lx + 1, 1, 100, Ly + 90);
        pan.setBackground(new Color(0, 200, 100));
        pan.setFont(new Font("Verdana", Font.PLAIN, 10));

        buttonNew = new JButton("New");
        buttonGo = new JButton("Go/Pause");
        buttonPhi22Plus = new JButton("Phi_22 +");
        buttonPhi22Min = new JButton("Phi_22 -");

        buttonNew.setActionCommand("New");
        buttonGo.setActionCommand("Go");
        buttonPhi22Plus.setActionCommand("Plus");
        buttonPhi22Min.setActionCommand("Minus");

        buttonNew.setBounds(10, Ly / 2 - 35, 80, 27);
        buttonPhi22Plus.setBounds(10, Ly / 2 + 10, 80, 27);
        buttonPhi22Min.setBounds(10, Ly / 2 + 46, 80, 27);
        buttonGo.setBounds(10, Ly / 2 + 90, 80, 27);

        pan.add(buttonGo);
        pan.add(buttonNew);
        pan.add(buttonPhi22Plus);
        pan.add(buttonPhi22Min);
        frame.add(pan, BorderLayout.EAST);
        frame.setVisible(true);

    }//init()

    public void addListener(ActionListener listener){
        buttonGo.addActionListener(listener);
        buttonNew.addActionListener(listener);
        buttonPhi22Min.addActionListener(listener);
        buttonPhi22Plus.addActionListener(listener);

    }

    public void addPanel(JPanel panel){
        frame.add(panel, BorderLayout.CENTER);
    }

}

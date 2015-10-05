package com.openComplex.app.Physics.Pendulums;

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
        /*setLayout(null);
        header = new Canvas();
        header.setBounds(1, 1, 2 * Lx, 40);
        header.setBackground(Color.white);
        can = new Canvas();
        can.setBounds(1, 41, 2 * Lx, Ly + 50);
        can.setBackground(Color.white);*/
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
        frame.add(pan);
        /*add(can);
        add(header);



        ghead = header.getGraphics();
        ghead.setColor(Color.blue);
        ghead.setFont(new Font("Verdana", Font.BOLD, 15));
        g = can.getGraphics();
        g.setFont(new Font("Verdana", Font.BOLD, 10));
*/

    }//init()

    public void addListener(ActionListener listener){
        buttonGo.addActionListener(listener);
        buttonNew.addActionListener(listener);
        buttonPhi22Min.addActionListener(listener);
        buttonPhi22Plus.addActionListener(listener);

    }


  /*  public void paint(Graphics gr)  //double buffer
    {
        if (offImage != null) {
            gr = can.getGraphics(); //initialize graphic window
            gr.drawImage(offImage, 0, 0, null);
        }
    } //paint(gr)

    public void update(Graphics gr)  //double buffer
    {
        gr = can.getGraphics();
        if (offGraphics == null) {
            offImage = createImage(2 * Lx, Ly + 50);
            offGraphics = offImage.getGraphics();
        }
        offGraphics.setFont(new Font("Verdana", Font.BOLD, 10));
        offGraphics.setColor(can.getBackground());
        offGraphics.fillRect(0, 0, 2 * Lx, Ly + 50);
        paintFrame(offGraphics);
        gr.drawImage(offImage, 0, 0, null);
    } //update(gr)

    public void paintFrame(Graphics gr) {
        print(gr); //print values
        gr.drawLine(0, Ly / 2, 2 * Lx, Ly / 2);
        gr.drawLine(Lx / 2, 0, Lx / 2, Ly);
        gr.drawLine(Lx + Lx / 2, 0, Lx + Lx / 2, Ly);
        gr.setColor(Color.red);
        gr.drawLine(Lx / 2, Ly / 2, px11, py11);
        gr.drawLine(px11, py11, px12, py12);
        gr.drawLine(Lx + Lx / 2, Ly / 2, px21, py21);
        gr.drawLine(px21, py21, px22, py22);
        gr.setColor(Color.black);
        gr.fillOval(px11 - 6, py11 - 6, 13, 13);
        gr.fillOval(px12 - 6, py12 - 6, 13, 13);
        gr.fillOval(px21 - 6, py21 - 6, 13, 13);
        gr.fillOval(px22 - 6, py22 - 6, 13, 13);
    } //paintFrame(gr)

    public void print(Graphics gr) {
        gr.setColor(Color.blue);
        gr.drawString("Phi_12:", Lx / 2 - 20, Ly + 20);
        gr.drawString("" + Math.round(100 * phi12 * 180 / Math.PI) / 100. + "�", Lx / 2 - 20, Ly + 40);
        gr.drawString("Phi_22:", Lx + Lx / 2 - 20, Ly + 20);
        gr.drawString("" + Math.round(100 * phi22 * 180 / Math.PI) / 100. + "�", Lx + Lx / 2 - 20, Ly + 40);
    }//print(gr)*/
}

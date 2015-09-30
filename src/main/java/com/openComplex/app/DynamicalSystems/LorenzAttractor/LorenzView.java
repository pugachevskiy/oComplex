package com.openComplex.app.DynamicalSystems.LorenzAttractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by strange on 26/08/15.
 */
public class LorenzView extends JPanel{
    private JFrame frame;
    private JPanel menuPanel;
    private Color color;
    private int translate;
    private int[] xArray, zArray;
    private JTextField coeffA, coeffB, coeffC;
    private JButton startButton, clearButton;
    int nPoint = 50000;
    int X,Y = 0;

    public LorenzView(){
        xArray = new int[50001];
        zArray = new int[50001];
        init();
    }

    public  void init(){
        frame = new JFrame("Lorenz Attractor");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(new Dimension(700, 400));
        frame.add(this, BorderLayout.CENTER);
        menuPanel  = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel,BoxLayout.Y_AXIS));
        addMenuPanel();
        frame.add(menuPanel, BorderLayout.WEST);
        frame.setVisible(true);

    }

    private void addMenuPanel(){
        menuPanel.add(new JLabel("Сoefficients"));
        menuPanel.add(new JLabel("A"));
        coeffA = new JTextField("");
        coeffA.setPreferredSize(new Dimension(100,40));
        menuPanel.add(coeffA);
        menuPanel.add(new JLabel("B"));
        coeffB = new JTextField("");
        coeffB.setPreferredSize(new Dimension(100,40));
        menuPanel.add(coeffB);
        menuPanel.add(new JLabel("C"));
        coeffC = new JTextField("");
        coeffC.setPreferredSize(new Dimension(100,40));
        menuPanel.add(coeffC);
        startButton = new JButton("Start");
        startButton.setActionCommand("Start");
        menuPanel.add(startButton);
        clearButton = new JButton("Clear");
        clearButton.setActionCommand("Clear");
        menuPanel.add(clearButton);
    }

    public void setTextA(String a){
        coeffA.setText(a);
    }

    public void setTextB(String b){
        coeffB.setText(b);
    }
    public void setTextC(String c){
        coeffC.setText(c);
    }

    public double getTextA(){
        return Double.valueOf(coeffA.getText());
    }

    public double getTextB(){
        return Double.valueOf(coeffB.getText());
    }

    public double getTextC(){
        return Double.valueOf(coeffC.getText());
    }

    public void addListener(ActionListener listener){
        startButton.addActionListener(listener);
        clearButton.addActionListener(listener);
    }

    public void draw(LorenzModel lorenz, Color color, int i, int translate){
        xArray[i] = (int) (lorenz.getX1()*4);
        zArray[i] = (int) (lorenz.getZ1()*4);
        X = (int) lorenz.getX1()*4;
        Y = (int) lorenz.getX1()*4;
        nPoint = i;
        this.color = color;
        this.translate = translate;
    }

    public void clear(){
        nPoint = 0;
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawLine(20, 50, 20, 350);
        g.drawLine(20, 350, 500, 350);
        g.drawString("Y", 25, 60);
        g.drawString("X", 500, 346);
        g.translate(translate,translate);
        g.setColor(color);
        g.drawPolygon(xArray, zArray, nPoint);
    }

    public void setStartButtonText(String text){
        startButton.setText(text);
        startButton.setActionCommand(text);
    }
}



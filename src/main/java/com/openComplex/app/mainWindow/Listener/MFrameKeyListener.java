package com.openComplex.app.mainWindow.Listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MFrameKeyListener implements KeyListener {


    public void keyPressed(KeyEvent e) {
        System.out.println("Tastenposition: " + e.getKeyLocation());
    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }


}

/**
 * package com.openComplex.app.mainWindow.Listener;
 * <p>
 * import java.awt.BorderLayout;
 * import java.awt.event.KeyEvent;
 * import java.awt.event.KeyListener;
 * <p>
 * import javax.swing.JFrame;
 * import javax.swing.JTextField;
 * <p>
 * public class MFrameKeyListener extends JFrame implements KeyListener {
 * <p>
 * public MFrameKeyListener(){
 * this.setLayout(new BorderLayout());
 * JTextField field = new JTextField();
 * field.addKeyListener(this);
 * this.add(field, BorderLayout.CENTER);
 * this.pack();
 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 * this.setLocationRelativeTo(null);
 * this.setVisible(true);
 * }
 * <p>
 * public void keyTyped(KeyEvent e) {
 * if(e.getKeyCode() == KeyEvent.VK_UNDEFINED){
 * System.out.println("Kein Unicode-Character gedr\u00FCckt!");
 * }
 * }
 * <p>
 * public void keyPressed(KeyEvent e) {
 * System.out.println("Tastenposition: " + e.getKeyLocation());
 * }
 * <p>
 * public void keyReleased(KeyEvent e) {
 * if(e.getKeyCode() == KeyEvent.VK_SPACE){
 * System.out.println("Programmabbruch!");
 * System.exit(0);
 * }
 * System.out.println("Taste: " + e.getKeyChar() + ", Code: " + e.getKeyCode());
 * }
 * <p>
 * public static void main(String[] args) {
 * new MFrameKeyListener();
 * }
 * }
 **/
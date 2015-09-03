package com.openComplex.app.CellularAutomat.GameOfLife.Controller;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Created by strange1 on 08/12/14.
 */
public class MenuController implements ActionListener{
         public void actionPerformed(ActionEvent evt) {
             JMenuItem menu = (JMenuItem) evt.getSource();
             switch (menu.getText()) {
                 case "Speichern":
                     /*try {
                         int w = viewController.f.getWidth(), h = viewController.f.getHeight();
                         BufferedImage image = new BufferedImage(w, h,
                                 BufferedImage.TYPE_INT_RGB);
                         Graphics2D g2 = image.createGraphics();
                         viewController.f.paint(g2);
                         g2.dispose();
                         ImageIO.write(image, "jpeg", new File("example.jpeg"));
                     } catch (IOException e) {
                         System.err.println(e);
                     }*/
                    break;
                 case "Exit":
                     Life.gui.frameClose();
                    break;
                 case "Spielregeln":
                     JFrame regeln = new JFrame("Regeln");

                     regeln.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                     JTextArea text = new JTextArea("Spielregeln\n" +
                             "a) Wenn eine lebende Zelle zwei oder drei lebende Nachbarn hat, dann bleibt sie fu ̈r\n" +
                             "die na ̈chste Generation am Leben.\n" +
                             "b) Wenn eine lebende Zelle weniger als zwei lebende Nachbarn hat, dann stirbt sie\n" +
                             "an Vereinsamung (und ist in der na ̈chsten Generation leer). Nachbarn hat.\n" +
                             "c) Wenn eine lebende Zelle mehr als drei lebende Nachbarn hat, dann stirbt sie wegen U ̈berbevo ̈lkerung.\n" +
                             "d) Wenn eine tote Zelle genau drei Nachbarn hat, dann wird sie neugeboren.");
                     text.setEditable(false);
                     regeln.add(text);

                     regeln.requestFocus();
                     regeln.setResizable(false);
                     regeln.pack();
                     regeln.setVisible(true);
                     break;
                 default:
                     break;
             }
         }

}

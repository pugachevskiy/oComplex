package com.openComplex.app.mainWindow.Controller;

import com.openComplex.app.App;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 29/05/15.
 */
public class MenuController {
    public void showOverview() {
        Image leftIcon = Toolkit.getDefaultToolkit().getImage(App.overviewPath);
        Image scaledImage = leftIcon.getScaledInstance(1385, 814, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);
        JOptionPane.showMessageDialog(App.gui, icon, "Hilfe", JOptionPane.PLAIN_MESSAGE);
    }


    /**
     * Creates pop-up window containing various information about the program.
     */
    public void showQuestions() {
        String rules = "This tool was developed to bring the user closer to the topics of Complex Systems and their applications.\n"
                + "Further information will be added soon.";

        JTextArea area = new JTextArea(rules);
        area.setFont(new Font(new JLabel().getFont().getFontName(), Font.PLAIN, 16));
        area.setBorder(BorderFactory.createEmptyBorder());
        area.setBackground(new JPanel().getBackground());
        area.setEditable(false);
        JOptionPane.showMessageDialog(App.gui, area, "Hilfe", JOptionPane.PLAIN_MESSAGE);
    }


    /**
     * Creates pop-up window containing information about development and fashion.
     */
    public void showAbout() {
        String rules = "This tool was and is developed at the Chair of Complex and Intelligent Systems at the University of Passau.\n"
                + "Matthias Fuchs and Tobias Leis worked and work under the supervision of Shahin Amiriparian on the project.";

        JTextArea area = new JTextArea(rules);
        area.setFont(new Font(new JLabel().getFont().getFontName(), Font.PLAIN, 16));
        area.setBorder(BorderFactory.createEmptyBorder());
        area.setBackground(new JPanel().getBackground());
        area.setEditable(false);
        JOptionPane.showMessageDialog(App.gui, area, "Hilfe", JOptionPane.PLAIN_MESSAGE);
    }
}

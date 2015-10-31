package com.openComplex.app.mainWindow.View.RightPanels;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;


/**
 * Created by strange on 29/05/15.
 */
public class mainPanelView extends JPanel {

    private JLabel iconLabel;
    private Image leftIcon;
    private Image scaledImage;
    private ImageIcon icon;
    private final String information =
            "Environment for working with complex systems.\n" +
            "Chair of Complex and Intelligent Systems\n" +
            "Univ.-Prof. Dr.-Ing. habil. Bj\u00f6rn Schuller\n" +
            "Shahin Amiriparian, M.Sc.\n" +
            "The University of Passau\n" +
            "Passau, Germany\n" +
            "Version 0.9\n" +
            "\u00a9 2015";

    private final String logoPath = "resources/grau.png";
    public mainPanelView() {
        this.setLayout(new BorderLayout());
        this.add(this.createMainPanel(), BorderLayout.CENTER);
    }


    //leftPanel contains important information about the program.
    private JPanel createMainPanel() {
        JPanel leftPanel = new JPanel();
        leftIcon = Toolkit.getDefaultToolkit().getImage(logoPath);
        scaledImage = leftIcon.getScaledInstance(400, 150, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        leftPanel.setLayout(new GridLayout(2, 1));
        iconLabel = new JLabel(icon);
        leftPanel.add(iconLabel);
        leftPanel.add(createFormattedPane(information));

        return leftPanel;
    }


    private JTextPane createFormattedPane(String text) {
        JTextPane description;

        description = new JTextPane();
        description.setEditable(false);
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(set, "Mono");
        StyleConstants.setFontSize(set, 15);
        StyleConstants.setBold(set, true);
        description.setParagraphAttributes(set, true);
        description.setBackground(new JLabel().getBackground());
        description.setBounds(100, 100, 300, 300);
        description.setText(text);

        return description;
    }
}

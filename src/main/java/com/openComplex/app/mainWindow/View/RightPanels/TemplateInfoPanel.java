package com.openComplex.app.mainWindow.View.RightPanels;

import com.openComplex.app.mainWindow.Listener.MFrameListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionListener;

public class TemplateInfoPanel extends JPanel {

    private final Border noFocusBorder = new EmptyBorder(5, 5, 5, 5);
    private ActionListener listener;
    private JButton startButton;
    private final int titleFontSize = 20, descriptionFontSize = 16;

    public TemplateInfoPanel(java.util.List<String> description) {
        listener = new MFrameListener();
        this.setLayout(new BorderLayout());
        this.setBorder(noFocusBorder);

        this.add(createFormattedPane(description.get(0), StyleConstants.ALIGN_CENTER, titleFontSize), BorderLayout.NORTH);

        if (description.size() == 3) {
            JPanel centerPanel = new JPanel();
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS));
            centerPanel.add(createLogoLabel(description.get(1)));
            centerPanel.add(createFormattedPane(description.get(2), StyleConstants.ALIGN_CENTER, descriptionFontSize));
            this.add(centerPanel, BorderLayout.CENTER);
        } else if (description.size() == 2) {
            this.add(createFormattedPane(description.get(1), StyleConstants.ALIGN_CENTER, descriptionFontSize));
            JPanel startPanel = new JPanel();
            startPanel.setLayout(new GridLayout(1, 1));
            startPanel.setBorder(new EmptyBorder(5, 150, 5, 150));
            startButton = new JButton("Start");
            startButton.addActionListener(listener);
            startButton.setActionCommand(description.get(0));
            startPanel.add(startButton);
            this.add(startPanel, BorderLayout.SOUTH);
        }
    }

    /**
     * Returns JLabel containing Image located at path
     */
    private JPanel createLogoLabel(String path) {
        JPanel logoPanel = new JPanel();
        JLabel label;

        Image image = Toolkit.getDefaultToolkit().getImage(path);
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);

        label = new JLabel(icon);
        label.setPreferredSize(new Dimension(200, 200));
        label.setMaximumSize(new Dimension(200, 200));
        logoPanel.add(label);

        return logoPanel;
    }

    /**
     * Creates JTextPane with text in it, alignments: StyleConstants.ALIGN_{LEFT/CENTER/RIGHT}
     */
    private JPanel createFormattedPane(String text, int alignment, int textSize) {
        JPanel formattedPanePanel = new JPanel();
        JTextPane description;

        description = new JTextPane();
        description.setEditable(false);
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, alignment);
        StyleConstants.setFontFamily(set, "Mono");
        StyleConstants.setFontSize(set, textSize);
        StyleConstants.setBold(set, true);
        description.setParagraphAttributes(set, true);
        description.setBackground(new JLabel().getBackground());
        description.setText(text);

        formattedPanePanel.add(description);
        return formattedPanePanel;
    }
}

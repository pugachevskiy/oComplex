package com.openComplex.app.mainWindow.View;

import com.openComplex.app.App;
import com.openComplex.app.mainWindow.Listener.MFrameListener;
import com.openComplex.app.mainWindow.Model.ModelMain;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by strange on 29/05/15.
 */
public class mainPanelView extends JPanel {
    public static final String nnButtonText = "Neuronal networks", gtButtonText = "Graph theory",
            caButtonText = "Cellular automat",dsButtonText = "Dynamical systems",mainButtonText = "back";
    public static final List<String> BUTTONSMAIN = Arrays.asList(nnButtonText, gtButtonText, caButtonText, dsButtonText);
    private ActionListener listener = new MFrameListener();
    private List<JButton> buttonList = new ArrayList<>();
    private JPanel leftPanel, topicPanel;
    private JLabel iconLabel;
    private Image leftIcon;
    private Image scaledImage;
    private ImageIcon icon;

    public mainPanelView() {
        this.setLayout(new BorderLayout());
        this.add(this.createLeftPanel(), BorderLayout.WEST);
        this.add(this.createTopicPanel(), BorderLayout.EAST);
    }

    //Initiate the Buttons


    //leftPanel contains important information about the program.
    private JPanel createLeftPanel() {
        leftIcon = Toolkit.getDefaultToolkit().getImage(App.iconPath);
        scaledImage = leftIcon.getScaledInstance(300, 90, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 1, 1, 0));
        iconLabel = new JLabel(icon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 19, 0, 0));
        leftPanel.add(new JLabel(" "));
        leftPanel.add(iconLabel);
        leftPanel.add(new JLabel(" "));
        leftPanel.add(new JLabel(" "));
        leftPanel.add(new JLabel("Environment for working with complex systems."));
        leftPanel.add(new JLabel("Chair of Complex and Intelligent Systems"));
        leftPanel.add(new JLabel("The University of Passau"));
        leftPanel.add(new JLabel("Passau, Germany"));
        leftPanel.add(new JLabel("Version " + App.versionNumber));
        return leftPanel;
    }

    //The right topicPanel offers the submenus and main topics
    private JPanel createTopicPanel() {
        topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(4, 0));
        TitledBorder title = new TitledBorder("Topics");
        topicPanel.setBorder(title);
        ModelMain model = new ModelMain();
        model.createButtons(topicPanel, BUTTONSMAIN);
        return topicPanel;
    }
}

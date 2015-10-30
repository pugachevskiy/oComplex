package com.openComplex.app.mainWindow.View;

import com.openComplex.app.mainWindow.Listener.MFrameListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 *  on 29/05/15.
 */
public class NeuronalPanelView extends JPanel{
    private ActionListener listener = new MFrameListener();
    public  NeuronalPanelView() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //neuronalMenu.setLayout(new GridLayout(2, 1));
        JPanel upperPanel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        upperPanel.setLayout(gbl);
        int i = 0;
        JButton backButton = new JButton();
        try {
            backButton.setIcon(new ImageIcon(ImageIO.read(new File(MainView.BACKBUTTONPICTUREPATH))));

        } catch(IOException e) {
            backButton.setText("BACK");
        }
        backButton.setActionCommand("back");
        backButton.addActionListener(listener);
        addComponent(upperPanel, gbl, new JLabel(), i++, 0, 1, 1);
        addComponent(upperPanel, gbl, backButton, i++, 0, 1, 1);
        while(i<3) {
            addComponent(upperPanel, gbl, new JLabel("                         "), i++, 0, 1, 1);
        }
        JLabel networksLabel = new JLabel("Neuronal networks");
        networksLabel.setFont(MainView.HEADINGFONT);
        addComponent(upperPanel, gbl, networksLabel, i++, 0, 5, 1);
        while(i<6) {
            addComponent(upperPanel, gbl, new JLabel("                                        "), i++, 0, 1, 1);
        }

        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2,2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        JButton feedforwardButton = new JButton(), recurrentButton = new JButton(), hopfieldButton = new JButton();

        List<JButton> buttonList = Arrays.asList(feedforwardButton, recurrentButton, hopfieldButton);
        List<String> buttonStrings = Arrays.asList("Feedforward networks", "Recurrent networks", "Hopfield networks");

        i = 0;
        for(JButton button : buttonList) {
            button.addActionListener(listener);
            button.setText(buttonStrings.get(i));
            button.setActionCommand(buttonStrings.get(i++));
            topicPanel.add(button);
        }


        this.add(upperPanel);

        JTextPane informationText = new JTextPane();
        informationText.setEditable(false);
        SimpleAttributeSet set = new SimpleAttributeSet();
        StyleConstants.setAlignment(set, StyleConstants.ALIGN_CENTER);
        StyleConstants.setFontFamily(set, "Mono");
        StyleConstants.setFontSize(set, 12);
        //StyleConstants.setItalic(set, true);
        StyleConstants.setBold(set, true);
        informationText.setParagraphAttributes(set, true);
        informationText.setBackground(new JLabel().getBackground());
        informationText.setBounds(100, 100, 300, 300);
        informationText.setText("In machine learning and cognitive science,\n" +
                "artificial neural networks (ANNs) are a family of statistical learning models\n" +
                "inspired by biological neural networks (the central nervous systems of animals,\n" +
                "in particular the brain) and are used to estimate or approximate functions\n" +
                "that can depend on a large number of inputs and are generally unknown.");

        this.add(informationText);
        this.add(topicPanel);

    }
    static void addComponent(Container cont, GridBagLayout gbl, Component c, int x, int y, int width,
                             int height) {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x; gbc.gridy = y;
        gbc.gridwidth = width; gbc.gridheight = height;
        gbc.weightx = 1; gbc.weighty = 1;
        gbl.setConstraints(c, gbc);
        cont.add(c);

    }
    public void destroy(){
        this.removeAll();
    }
}

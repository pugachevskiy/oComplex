package com.openComplex.app.mainWindow.View.DynamicSubPanels;

import com.openComplex.app.mainWindow.Listener.MFrameListener;
import com.openComplex.app.mainWindow.Model.ModelMain;
import com.openComplex.app.mainWindow.View.MainView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tobias on 07.10.2015.
 */
public class OscillatorPanelView extends JPanel{
    private ActionListener listener = new MFrameListener();
    private static final String HARMONIC = "Harmonic oscillator", ANHARMONIC = "Anharmonic oscillator",
            TWOOSCILL = "2 oscillators with coupling", TWOANHARM = "2 anharmonic oscillators with coupling", VIBR_ROT = "Vibration-Rotator";
    private static final List<String> BUTTONSCELLULAR = Arrays.asList(HARMONIC, ANHARMONIC, TWOOSCILL, TWOANHARM, VIBR_ROT);


    public OscillatorPanelView() {

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
        backButton.setActionCommand("back2Dynamical");
        backButton.addActionListener(listener);
        backButton.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent arg0) {
            }
        });
        backButton.setMnemonic(KeyEvent.VK_BACK_SPACE);
        addComponent(upperPanel, gbl, new JLabel(), i++, 0, 1, 1);
        addComponent(upperPanel, gbl, backButton, i++, 0, 1, 1);
        while(i<3) {
            addComponent(upperPanel, gbl, new JLabel("                                  "), i++, 0, 1, 1);
        }
        JLabel networksLabel = new JLabel("Oscillators");
        networksLabel.setFont(MainView.HEADINGFONT);
        addComponent(upperPanel, gbl, networksLabel, i++, 0, 5, 1);
        while(i<6) {
            addComponent(upperPanel, gbl, new JLabel("                                   "), i++, 0, 1, 1);
        }


        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new GridLayout(2, 2));
        TitledBorder title = new TitledBorder("Options");
        topicPanel.setBorder(title);
        ModelMain model = new ModelMain();
        model.createButtons(topicPanel, BUTTONSCELLULAR);

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
        informationText.setText("An oscillator is a mechanical or electronic device that\n" +
                "works on the principles of oscillation: a periodic fluctuation between\n" +
                "two things based on changes in energy. Computers, clocks, watches, radios,\n" +
                "and metal detectors are among the many devices that use oscillators.\n");

        this.add(upperPanel);
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

}

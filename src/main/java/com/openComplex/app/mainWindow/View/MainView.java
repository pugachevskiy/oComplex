package com.openComplex.app.mainWindow.View;

import com.openComplex.app.mainWindow.View.RightPanels.TemplateInfoPanel;
import com.openComplex.app.mainWindow.View.RightPanels.mainPanelView;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.util.*;

import static java.awt.Toolkit.getDefaultToolkit;

/**
 * Created by strange on 29/05/15.
 */
public class MainView extends JFrame {

    private JPanel descriptionField;
    private CardLayout cardLayout;
    private JTextField infoField;

    public static final String OPTIONSMENU = "Options", EDITMENU = "Edit", SEARCHMENU = "Search", ABOUTMENU = "?";
    private static final java.util.List<String> MENU = Arrays.asList(OPTIONSMENU, EDITMENU, SEARCHMENU, ABOUTMENU);
    private static final int OPTIONSKEY = KeyEvent.VK_O, EDITKEY = KeyEvent.VK_E, SEARCHKEY = KeyEvent.VK_S, ABOUTKEY = KeyEvent.VK_H;
    private static final java.util.List<Integer> MENUKEY = Arrays.asList(OPTIONSKEY, EDITKEY, SEARCHKEY, ABOUTKEY);
    public static final String OVERVIEWMENUITEM = "Overview", ABOUTMENUITEM = "About", HELPMENUITEM = "Help";
    private static final java.util.List<String> MENUITEM = Arrays.asList(OVERVIEWMENUITEM, ABOUTMENUITEM, HELPMENUITEM);


    public MainView() {
        initFrame();
        setCentral();
    }

    private void initFrame() {

        this.setTitle("OpenCOSY");
        this.setLayout(new BorderLayout());

        //Right Panel contains all submenus describing the individual tools
        descriptionField = new JPanel();
        cardLayout = new CardLayout();
        descriptionField.setLayout(cardLayout);

        descriptionField.add(new mainPanelView(), TopicCollection.menuItems.get(0));

        for(int i=0; i<TopicCollection.allPanels.size(); i++) {
            descriptionField.add(new TemplateInfoPanel(TopicCollection.allPanels.get(i)), TopicCollection.menuItems.get(i+1));
        }

        this.updateGUI(TopicCollection.menuItems.get(0));

        this.add(new MenuPanel(this), BorderLayout.CENTER);
        this.add(descriptionField, BorderLayout.EAST);


        //infoField to show various information during program
        this.getContentPane().add(infoField = new JTextField(), BorderLayout.SOUTH);
        infoField.setEnabled(false);
        infoField.setBackground(Color.WHITE);
        infoField.setText("Welcome to openCOSY!");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
    }


    /**
     * Sets the frame in central position on screen.
     */
    private void setCentral() {
        Dimension frameSize = this.getSize();
        Dimension screensize = getDefaultToolkit().getScreenSize();
        int xCoordinate = (int) (screensize.getWidth() / 2 - frameSize.getWidth() / 2);
        int yCoordinate = (int) (screensize.getHeight() / 2 - frameSize.getHeight() / 2);
        this.setLocation(xCoordinate, yCoordinate);
    }

    /**
     * Set new JPanel on JFrame
     */
    public void updateGUI(String menuValue) {
        cardLayout.show(descriptionField, menuValue);
    }
}

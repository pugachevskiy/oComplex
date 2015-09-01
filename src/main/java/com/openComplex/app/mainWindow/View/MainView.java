package com.openComplex.app.mainWindow.View;

import com.openComplex.app.App;
import com.openComplex.app.mainWindow.Listener.MenubarListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.util.*;
import java.util.List;

import static java.awt.Toolkit.getDefaultToolkit;

/**
 * Created by strange on 29/05/15.
 */
public class MainView extends JFrame {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JTextField infoField;
    private MenubarListener listener;
    public static final String OPTIONSMENU = "Options", EDITMENU = "Edit", SEARCHMENU = "Search", ABOUTMENU = "?";
    private static final List<String> MENU = Arrays.asList(OPTIONSMENU, EDITMENU, SEARCHMENU, ABOUTMENU);
    private static final int OPTIONSKEY = KeyEvent.VK_O, EDITKEY = KeyEvent.VK_E, SEARCHKEY = KeyEvent.VK_S, ABOUTKEY = KeyEvent.VK_H;
    private static final List<Integer> MENUKEY = Arrays.asList(OPTIONSKEY, EDITKEY, SEARCHKEY, ABOUTKEY);
    public static final String OVERVIEWMENUITEM = "Overview", ABOUTMENUITEM = "About", HELPMENUITEM = "Help";
    private static final List<String> MENUITEM = Arrays.asList(OVERVIEWMENUITEM, ABOUTMENUITEM, HELPMENUITEM);

    public static final String BACKBUTTONPICTUREPATH = "resources/pfeil.png";
    public static final Font HEADINGFONT = new Font(new JLabel().getFont().getFontName(), new JLabel().getFont().getStyle(), 18);


    public MainView() {
        initFrame();
        setCentral();
    }

    private void initFrame() {
        mainFrame = new JFrame("OpenComplex");
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setJMenuBar(createMenuBar());
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        mainPanel.add(new mainPanelView(), "MAIN");
        mainPanel.add(new NeuronalPanelView(), mainPanelView.nnButtonText);
        mainPanel.add(new GraphPanelView(), mainPanelView.gtButtonText);
        mainPanel.add(new CellularPanelView(), mainPanelView.caButtonText);
        mainPanel.add(new DynamicPanelView(), mainPanelView.dsButtonText);
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        //Adds a cardlayout to the frame to optimize switching between menus.
       // mainPanel.setLayout(cardLayout = new CardLayout());
        this.updateGUI(mainPanelView.mainButtonText);

        //infoField to show various information during program
        mainFrame.getContentPane().add(infoField = new JTextField(), BorderLayout.SOUTH);
        infoField.setEnabled(false);
        infoField.setBackground(Color.WHITE);
        infoField.setText("Welcome to OpenComplex!");

        mainFrame.setBounds(350, 300, 500, 300);
        Image leftIcon = getDefaultToolkit().getImage(App.iconPath);
        mainFrame.setIconImage(leftIcon);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

    }


    private JMenu createMenu(String name, int mnemonic) {
        JMenu menu = new JMenu(name);
        menu.setMnemonic(mnemonic);
        return menu;
    }

    private JMenuItem createMenuItem(String name, ActionListener listener) {
        JMenuItem menuItem = new JMenuItem(name);
        menuItem.setActionCommand(name);
        menuItem.addActionListener(listener);
        return menuItem;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menu = new JMenuBar();
        listener = new MenubarListener();

        for (int i = 0; i < MENU.size(); i++) {
            menu.add(createMenu(MENU.get(i), MENUKEY.get(i)));
        }

        menu.getMenu(3).addActionListener(listener);

        for (int i = 0; i < MENUITEM.size(); i++) {
            menu.getMenu(3).add(createMenuItem(MENUITEM.get(i), listener));
            if (i == 1) {
                menu.getMenu(3).addSeparator();
            }
        }

        return menu;
    }


    /**
     * Sets the frame in central position on screen.
     */
    private void setCentral() {
        Dimension frameSize = mainFrame.getSize();
        Dimension screensize = getDefaultToolkit().getScreenSize();
        int xCoordinate = (int) (screensize.getWidth() / 2 - frameSize.getWidth() / 2);
        int yCoordinate = (int) (screensize.getHeight() / 2 - frameSize.getHeight() / 2);
        mainFrame.setLocation(xCoordinate, yCoordinate);
    }

    /**
     * Set new JPanel on JFrame
     */
    public void updateGUI(String menuValue) {

        //mainFrame.remove(mainPanel);
        switch (menuValue) {
            case mainPanelView.mainButtonText:
                cardLayout.show(mainPanel, "MAIN");
                //mainPanel = new mainPanelView();
                break;
            case mainPanelView.nnButtonText:
                cardLayout.show(mainPanel, mainPanelView.nnButtonText);
                //mainPanel = new NeuronalPanelView();
                break;
            case mainPanelView.gtButtonText:
                cardLayout.show(mainPanel, mainPanelView.gtButtonText);
                //mainPanel = new GraphPanelView();
                break;
            case mainPanelView.caButtonText:
                cardLayout.show(mainPanel, mainPanelView.caButtonText);
                //mainPanel = new CellularPanelView();
                break;
            case mainPanelView.dsButtonText:
                cardLayout.show(mainPanel, mainPanelView.dsButtonText);
                //mainPanel = new DynamicPanelView();
                break;
            default:
                break;
        }
        //mainFrame.add(mainPanel);
        //mainFrame.revalidate();

    }
}

package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.View;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Controller.ButtonListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;

/**
 * on 22.06.2015.
 */
public class Menu extends JMenuBar {

    private JMenuItem newItem = new JMenuItem(), drawItem = new JMenuItem(), loadItem = new JMenuItem(),
            saveItem = new JMenuItem(), saveAsItem = new JMenuItem(), exitItem = new JMenuItem();
    private java.util.List<JMenuItem> menuItems = Arrays.asList(newItem, drawItem, loadItem, saveItem, saveAsItem, exitItem);
    public static final String DRAW = "Draw", NEWRANDOM = "Random", LOAD = "Load..", SAVE = "Save",
            SAVEAS = "Save as...  ", EXIT = "Exit", HELP = "Help";
    private static final String[] menuItemArray = {DRAW, NEWRANDOM, LOAD, SAVE, SAVEAS, EXIT};

    private ActionListener listener = new ButtonListener();

    public Menu() {

        //Einzelne Menüs
        JMenu file = new JMenu("File");
        JMenu options = new JMenu("Options");
        JMenu faq = new JMenu("?");

        char[] menuAccArray = {'N', 'E', 'L', 'S', 'A', 'Q'};

        //Fügt Items zu Menü 'Datei' hinzu
        int i = 0;
        for (JMenuItem newMenuItem : menuItems) {
            if (i == 2 || i == 5)
                file.addSeparator();
            newMenuItem.setText(menuItemArray[i]);
            newMenuItem.setActionCommand(menuItemArray[i]);
            newMenuItem.addActionListener(listener);
            newMenuItem.setAccelerator(KeyStroke.getKeyStroke(menuAccArray[i], InputEvent.CTRL_DOWN_MASK));

            file.add(newMenuItem);
            i++;
        }

        file.setMnemonic(KeyEvent.VK_D);

        String[] sizes = {"Size  ", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        options.add(createMiniMenu(sizes, 3));
        options.setMnemonic(KeyEvent.VK_O);

        JMenuItem help = new JMenuItem(HELP);
        help.setActionCommand(HELP);
        help.addActionListener(listener);
        faq.add(help);

        this.add(file);
        this.add(options);
        this.add(faq);
        System.out.println();

    }

    private JMenu createMiniMenu(String[] buttonCaption, int select) {
        JMenu backgroundColorMenu = new JMenu(buttonCaption[0]);

        ButtonGroup group = new ButtonGroup();

        String buttonText;
        for (int i = 0; i < buttonCaption.length - 1; i++) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem();
            item.setText(buttonCaption[i + 1] + "x" + buttonCaption[i + 1]);
            item.addActionListener(listener);
            item.setActionCommand(buttonCaption[i + 1]);
            group.add(item);
            backgroundColorMenu.add(item);
            if (select == i)
                item.setSelected(true);
        }
        return backgroundColorMenu;
    }

}

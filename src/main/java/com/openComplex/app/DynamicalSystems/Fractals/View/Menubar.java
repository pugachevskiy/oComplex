package com.openComplex.app.DynamicalSystems.Fractals.View;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * on 22.06.2015.
 */
public class Menubar extends JMenuBar implements ActionListener {

    private int colorSet = 0;

    private static final String COLORS = "Colors", DEFAULT = "Default", BLUE = "Blue-Blue", GREEN = "Green-Green", MODERN = "Modern", FANCY = "Fancy";
    public static final String[] colors = {COLORS, DEFAULT, BLUE, GREEN, MODERN, FANCY};
    private JMenuItem saveItem = new JMenuItem();
    public static final String SAVE = "Save";

    public Menubar() {

        JMenu options = new JMenu("Options");

        saveItem.setText(SAVE);
        saveItem.setActionCommand(SAVE);
        saveItem.addActionListener(this);

        options.add(saveItem);
        options.setMnemonic(KeyEvent.VK_O);
        options.add(createMiniMenu(colors, 0));

        this.add(options);
    }

    private JMenu createMiniMenu(String[] buttonCaption, int select) {
        JMenu backgroundColorMenu = new JMenu(buttonCaption[0]);

        ButtonGroup group = new ButtonGroup();

        String buttonText;
        for (int i = 0; i < buttonCaption.length - 1; i++) {
            JCheckBoxMenuItem item = new JCheckBoxMenuItem();
            item.setText(buttonCaption[i + 1]);
            item.addActionListener(this);
            item.setActionCommand(buttonCaption[i + 1]);
            group.add(item);
            backgroundColorMenu.add(item);
            if (select == i)
                item.setSelected(true);
        }
        return backgroundColorMenu;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();
        switch (temp) {
            case BLUE:
                colorSet = 1;
                break;
            case GREEN:
                colorSet = 2;
                break;
            case MODERN:
                colorSet = 3;
                break;
            case FANCY:
                colorSet = 4;
                break;
            default:
                colorSet = 0;
                break;
        }
    }

    public int getColorSet() {
        return colorSet;
    }
}
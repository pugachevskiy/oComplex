package com.openComplex.app.mainWindow.Listener;

import com.openComplex.app.mainWindow.Controller.MenuController;
import com.openComplex.app.mainWindow.View.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * on 29/05/15.
 */
public class MenubarListener implements ActionListener {
    MenuController menu = new MenuController();

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        switch (command) {
            case MainView.OVERVIEWMENUITEM:
                menu.showOverview();
                break;
            case MainView.HELPMENUITEM:
                menu.showQuestions();
                break;
            case MainView.ABOUTMENUITEM:
                menu.showAbout();
                break;
            default:
                break;
        }
    }
}
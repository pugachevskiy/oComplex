package com.openComplex.app;

import com.openComplex.app.mainWindow.View.MainView;

import javax.swing.*;
//Test abc

public class App {
    public static MainView gui;
    public static final String versionNumber = "0.01", iconPath = "resources/Icon.png",
            logoPath = "resources/logoUni.png", overviewPath = "resources/Mindmap.png";

    public static void main(String[] argv){
        //Try to use Windows Look&Feel for program.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        gui = new MainView();
    }
}

package com.openComplex.app;

import com.openComplex.app.mainWindow.View.MainView;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.*;

public class App {
    public static MainView gui;
//    public static final String versionNumber = "1.01", iconPath = "resources/Icon_braungelb.png",
//            logoPath = "resources/braungelb.png", overviewPath = "resources/Mindmap.png";
  public static String versionNumber = "1.01", iconPath = "resources/Icon_braungelb.png",
  logoPath = "resources/opencosy-logo.png", overviewPath = "resources/Mindmap.png";
    
    public static void main(String[] argv){
        //Try to use Windows Look&Feel for program.
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        if (System.getProperty("os.name").contains("Mac")) {
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        FileInputStream fis;
        Properties property = new Properties();

        try {
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);

            String name = property.getProperty("application.name");
            versionNumber = property.getProperty("application.version");
            String build = property.getProperty("application.build");

            System.out.println("name: " + name
                    + ", version: " + versionNumber
                    + ", build: " + build);

        } catch (IOException e) {
            System.err.println("ОШИБКА: Файл свойств отсуствует!");
        }

    

        gui = new MainView();
    }
}

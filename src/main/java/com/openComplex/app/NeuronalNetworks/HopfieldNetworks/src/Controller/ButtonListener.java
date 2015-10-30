package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.View.*;

import javax.swing.text.html.Option;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InterruptedIOException;

/**
 *  on 23.06.2015.
 */
public class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String temp = e.getActionCommand();
        if(temp.equals(Menu.HELP)) {                                                             //Hilfe
            //Erstellt Hilfefenster mit Regeln und Erklärungen
            Main.gui.createHelpFrame();

        } else if(temp.equals(Menu.EXIT)) {                                                      //Beenden
            System.exit(0);

        } else if(temp.equals(Menu.NEWRANDOM)){                                                  //Neu
            //Weist den Textfields beliebige Werte zu
            Main.gui.tablePanel.shakeTextFieldArray();
            Main.gui.tablePanel.optionPanel.shakeTextFieldArray();

        } else if(temp.equals(Menu.DRAW)){                                                       //Aufzeichnen
            //Disabled Buttons, updatet Array des NetPainters und startet Thread
            Main.paintHopField();
        } else if(temp.equals(Menu.LOAD)){                                                       //Laden
            //Öffnet Dialog, erstellt Pfad und updatet Textfields mit Datei am Ende des Pfades
            String loadPath = FileLoader.getLoadPath();
            String[][] tempArr = FileLoader.readCSV(loadPath);
            Main.gui.tablePanel.updateTextFieldArray(tempArr);

        } else if(temp.equals(Menu.SAVE)){                                                       //Speichern
            //Speichert .jpg auf dem Desktop mit zufälligem Namen
            FileLoader.save(FileLoader.desktopString + "HopfieldNet" + System.currentTimeMillis());

        } else if(temp.equals(Menu.SAVEAS)){                                                     //Speichern unter
            //Öffnet Speicher-Dialog und speichert in erstelltem Pfad
            String path;
            try {
                path = FileLoader.getSavePath();
            } catch (InterruptedIOException iioe) {
                System.out.println("Eingabe abgebrochen");
                return;
            }
            FileLoader.save(path);
        } else if(temp.equals("Calculate")){
            Main.calculateSteps();
            new SynchroPanel();
        } else {
            int newSize = Integer.parseInt(temp);
            Main.gui.tablePanel.updateSize(newSize);
        }
    }
}

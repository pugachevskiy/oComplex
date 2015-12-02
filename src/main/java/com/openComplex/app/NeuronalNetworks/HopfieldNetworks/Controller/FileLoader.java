package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Controller;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for opening and storing the components
 */
public class FileLoader {


    private static String savePath = System.getProperty("user.home");
    private static String loadPath = System.getProperty("user.home");
    public static final String desktopString = "C:\\Users\\" + System.getProperty("user.name") + "\\Desktop\\";


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////               Methods for loading, saving and path decision              ///////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Methode zum Aufruf eines Frames, in dem man eine zu ladende Datei auswählen kann, gibt Pfad zurück
     */
    public static String getLoadPath() {

        JFileChooser chooser;
        chooser = new JFileChooser(loadPath);

        //Anpassen des später angezeigten Fensters
        chooser.setDialogType(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter markUpFilter = new FileNameExtensionFilter(
                ".csv", "csv");
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.setFileFilter(markUpFilter);
        chooser.setDialogTitle("Datei laden");
        chooser.setVisible(true);

        int result = chooser.showOpenDialog(Main.gui.frame);

        //Falls alle Eingaben stimmen und 'OK' gedrückt wurde, wird der path aktualisiert
        if (result == JFileChooser.APPROVE_OPTION) {
            loadPath = chooser.getSelectedFile().toString();
            chooser.setVisible(false);
        }
        chooser.setVisible(false);
        return loadPath;
    }

    /**
     * Methode zum Aufruf eines Frames, in dem man Ort und Namen der zu speichernden Datei festlegen kann
     */
    public static String getSavePath() throws InterruptedIOException{

        JFileChooser chooser;
        chooser = new JFileChooser(savePath);

        //Anpassen des später angezeigten Fensters
        chooser.setDialogType(JFileChooser.SAVE_DIALOG);
        FileNameExtensionFilter markUpFilter = new FileNameExtensionFilter(
                ".jpg", "jpg");
        chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
        chooser.setFileFilter(markUpFilter);
        chooser.setDialogTitle("Speichern unter...");
        chooser.setVisible(true);

        int result = chooser.showSaveDialog(Main.gui.frame);

        //Falls alle Eingaben stimmen und 'OK' gedrückt wurde, wird der path aktualisiert
        if (result == JFileChooser.APPROVE_OPTION) {
            savePath = chooser.getSelectedFile().toString();
            chooser.setVisible(false);
        } else {
            throw new InterruptedIOException();
        }

        chooser.setVisible(false);
        return savePath;
    }

    /**
     * Speichert das JPanel net(das Netz) im Pfad <path>
     */
    public static void save(String path) {
        //Holt sich die Grid von der GUI
        JPanel tempGrid = Main.gui.graphPanel;

        //Zeichnet in den Puffer
        BufferedImage img = new BufferedImage(tempGrid.getWidth(), tempGrid.getHeight(), BufferedImage.TYPE_INT_RGB);
        tempGrid.print(img.getGraphics());

        //Gibt das Image aus
        try {
            ImageIO.write(img, "jpg", new File(path + ".jpg"));
        }
        catch (AccessDeniedException t) {
            System.err.println("Zugriff verweigert");
        }
        catch (IOException io) {
            System.err.println("Fehler beim Schreiben");
        }

        //Update des savePath auf einen Standardpfad (ohne Dateiendung)
        String arr = savePath.replace("\\", "\\\\");
        String[] ar = arr.split("\\\\");
        savePath = "";
        for(int i=0; i<ar.length-1; i++) {
            savePath = savePath + ar[i] + "\\";
        }
        System.out.println(savePath);

    }

    /**
     * Liest eine CSV ein und gibt ein Array mit allen Werten zurück
     */
    public static String[][] readCSV (String path) {
        String [][] arr = new String [6][6];
        for(int i=0; i<Main.size; i++) {
            for(int j=0; j<Main.size; j++) {
                arr[i][j] = "0";
            }
        }
        List<String> lines = new ArrayList<String>();
        FileReader reader = null;
        BufferedReader buffer = null;

        //Erstellt Filereader
        try {
            reader = new FileReader(path);
        } catch (FileNotFoundException ffe) {
            System.err.println("Datei nicht gefunden");
        }

        //Und schreibt die File in den Buffer
        try {
            buffer = new BufferedReader(reader);
        } catch (NullPointerException ill) {
            System.err.println("Fehler beim Erstellen des Buffers");
        }

        //Holt sich solange neue Zeile, bis nichts mehr da ist
        try {
            String line;
            while ((line = buffer.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Auslesen");
        } catch (NullPointerException npe) {
            System.err.println("Buffer-Fehler");
        }

        //Längen der eingelesenen Datei
        int verticLength = 0, horizLength = Main.size;
        for(String st : lines) {
            String [] tempArr = st.split(";");
            horizLength = horizLength<tempArr.length ? horizLength : tempArr.length;
            verticLength++;
        }
        //Maximalgröße
        int newSize = verticLength<horizLength ? verticLength : horizLength;
        newSize = newSize>Main.size ? Main.size : newSize;

        //Überschreibt die Werte in der Liste in das Array, das zurückgegeben wird
        for(int i=0; i<newSize; i++) {
            String [] tempArr = lines.get(i).split(";");
            for(int j=0;j<newSize; j++) {
                try{
                    arr[i][j] = tempArr[j];
                } catch(IndexOutOfBoundsException iob){
                    arr[i][j] = "0";
                }

            }
        }

        //Updatet die Matrix auf die passende Größe (nicht größer als 6)
        Main.gui.tablePanel.updateSize(newSize);

        return arr;
    }
}

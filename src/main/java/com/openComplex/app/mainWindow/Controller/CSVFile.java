package com.openComplex.app.mainWindow.Controller;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.apache.commons.io.FilenameUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by strange on 02/10/15.
 */
public class CSVFile {
    // Game of life
    public static void saveField(com.openComplex.app.CellularAutomat.GameOfLife.Model.Cell[][] field, JComponent parent) throws IOException {
        CSVWriter writer;
        String[] entries = new String[field.length];
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("CSV document (*.csv)", "csv");
        fileChooser.setFileFilter(filter1);
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + ".csv");
            writer = new CSVWriter(new FileWriter(file), ',');
            for (int i = 0; i < field.length; i++) {
                entries[i] = "";
                for (int j = 0; j < field[0].length; j++) {
                    if (field[i][j].getStatus()) {
                        entries[i] += 1;
                    } else {
                        entries[i] += 0;
                    }

                    if (j != field[0].length - 1) {
                        entries[i] += '#';
                    }
                }
                writer.writeNext(entries[i].split("#"));
            }
            writer.close();
        }
    }

    public static void loadField(com.openComplex.app.CellularAutomat.GameOfLife.Model.Cell[][] field, JComponent parent) throws IOException {
        CSVReader reader;
        String[] nextLine;
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("CSV document (*.csv)", "csv");
        fileChooser.setFileFilter(filter1);
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            reader = new CSVReader(new FileReader(file), ',');
            int counter = 0;
            while ((nextLine = reader.readNext()) != null) {
                for (int i = 0; i < field.length; i++) {
                    if (Integer.valueOf(nextLine[i]) == 1) {
                        field[counter][i].setStatus(true);
                    } else {
                        field[counter][i].setStatus(false);
                    }
                }
                counter++;
            }
        }
    }
    //Wolframm
    public static void saveField(com.openComplex.app.CellularAutomat.WolframsUniverse.Model.Cell[][] field, JComponent parent) throws IOException {
        CSVWriter writer;
        String[] entries = new String[field.length];
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("CSV document (*.csv)", "csv");
        fileChooser.setFileFilter(filter1);
        if (fileChooser.showSaveDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            file = new File(file.getParentFile(), FilenameUtils.getBaseName(file.getName()) + ".csv");
            writer = new CSVWriter(new FileWriter(file), ',');
            for (int i = 0; i < field.length; i++) {
                entries[i] = "";
                for (int j = 0; j < field[0].length; j++) {
                    if (field[i][j].getStatus()) {
                        entries[i] += 1;
                    } else {
                        entries[i] += 0;
                    }

                    if (j != field[0].length - 1) {
                        entries[i] += '#';
                    }
                }
                writer.writeNext(entries[i].split("#"));
            }
            writer.close();
        }
    }

    public static int loadField(com.openComplex.app.CellularAutomat.WolframsUniverse.Model.Cell[][] field, JComponent parent) throws IOException {
        CSVReader reader;
        String[] nextLine;
        int last = 0;
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        FileNameExtensionFilter filter1 = new FileNameExtensionFilter("CSV document (*.csv)", "csv");
        fileChooser.setFileFilter(filter1);
        if (fileChooser.showOpenDialog(parent) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            reader = new CSVReader(new FileReader(file), ',');
            int counter = 0;
            while ((nextLine = reader.readNext()) != null) {
                for (int i = 0; i < field.length; i++) {
                    if (Integer.valueOf(nextLine[i]) == 1) {
                        field[counter][i].setStatus(true);
                        last = counter;
                    } else {
                        field[counter][i].setStatus(false);
                    }
                }
                counter++;
            }
        }
        return last;
    }
}

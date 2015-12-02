package com.openComplex.app.NeuronalNetworks.MultilayerPerceptron.Model;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;

/**
 * Created by MatthiasFuchs on 06.11.15.
 */
public class SelectData {
    public String path;

    public void csvChooser() {
        JFileChooser fc = new JFileChooser();
        fc.removeChoosableFileFilter(fc.getAcceptAllFileFilter());
        fc.setFileFilter( new FileNameExtensionFilter("CSV-Dateien", "csv"));
        int state = fc.showOpenDialog( null );

        if ( state == JFileChooser.APPROVE_OPTION )
        {
            File file = fc.getSelectedFile();
            path = file.getPath();
        }
    }

    public Data readCSV() {
        double[][] pattern = new double[0][0];
        double[] label = new double[0];

        FileReader fr = null;
        try {
            fr = new FileReader(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        String line;
        BufferedReader bf;

        try {
            bf = new BufferedReader(fr);
            int rows = 0;
            while (bf.readLine() != null) {
                rows++;
            }

            fr = new FileReader(path);
            bf = new BufferedReader(fr);
            line = bf.readLine();
            String[] temp = line.split("\t") ;
            int columns = temp.length;


            fr = new FileReader(path);
            bf = new BufferedReader(fr);

            pattern = new double[rows][columns-2];
            label = new double[rows];


            for(int i = 0; i <rows; i++) {
                line = bf.readLine();
                temp = line.split("\t");
                for(int j = 2; j < columns; j++) {
                    pattern[i][j-2] = Double.parseDouble(temp[j]);
                }
                label[i] = Double.parseDouble(temp[1]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Data(label, pattern);
    }


    public final class Data {
        private final double[] label;
        private final double[][] pattern;
        public double [][] testPattern;
        public double [] testLabel;
        public double [] trainLabel;
        public double [][] trainPattern;

        public Data (double[] label, double[][] pattern) {
            this.label = label;
            this.pattern = pattern;
        }

        public void splitData(double split) {
            int boarder = (int) (pattern.length*split);
            trainPattern = new double[boarder][pattern[0].length];
            trainLabel = new double[boarder];
            testPattern = new double[pattern.length - boarder][pattern[0].length];
            testLabel = new double[pattern.length - boarder];
            for (int i = 0; i < pattern.length; i++) {
                if(i < boarder) {
                    trainPattern[i] = pattern[i];
                    trainLabel[i] = label[i];
                } else {
                    testPattern[i-boarder] = pattern[i];
                    testLabel[i-boarder] = label[i];
                }
            }
        }

        public double[] getLabel() {
            return label;
        }

        public double[][] getPattern() {
            return pattern;
        }

    }
}

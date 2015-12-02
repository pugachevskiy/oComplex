package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Model;
/**
 *  on 22.06.2015.
 */
public class Matrix {

    public static int size = 15;


    /**
    public String[][] getTextfieldValues() {
        String[][] fieldValues = new String[textfieldArray.length][textfieldArray.length];
        for(int i=0; i<textfieldArray.length; i++) {
            for(int j=0; j<textfieldArray.length; j++) {
                fieldValues[i][j] = textfieldArray[i][j].getText();
            }
        }
        return fieldValues;
    }


    public void shakeTextFieldArray() {
        int rand;
        for(int i=0;i<Main.size; i++) {
            for(int j=i+1; j<Main.size; j++) {
                while((rand=(int)(Math.random()*40-20))==0);
                textfieldArray[i][j].setText("" + rand);
                textfieldArray[j][i].setText("" + rand);
            }
        }
    }


    public void updateTextFieldArray(String[][] arr) {
        for(int i=0;i<textfieldArray.length-1; i++) {
            for(int j=i+1; j<textfieldArray.length-1; j++) {
                textfieldArray[i][j].setText("" + arr[i][j]);
                textfieldArray[j][i].setText("" + arr[j][i]);
            }
        }
    }

    **/
}

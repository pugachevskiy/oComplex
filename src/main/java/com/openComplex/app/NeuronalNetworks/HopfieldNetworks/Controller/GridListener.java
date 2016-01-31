package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Controller;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Model.Matrix;
import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.View.TablePanel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

/**
 * Listener for the Textfields, used for symmetrical reasons
 */
public class GridListener implements DocumentListener {

    private String oldCoord = "00";

    /**
     * Changes the opposite entry for symmetrical reasons
     */
    public void insertUpdate(DocumentEvent e) {
        Document temp = e.getDocument();
        int tempX = 0, tempY = 0;
        //Vergleicht das Event-Document solange mit den versch. Textfields, bis eine Übereinstimmung gefunden wurde
        for (int i = 0; i < Matrix.size; i++) {
            for (int j = 0; j < Matrix.size; j++) {
                if (TablePanel.textfieldArray[i][j].getDocument().equals(temp)) {
                    tempX = i;
                    tempY = j;
                    break;
                }

            }
        }
        //Koordinate des veränderten Textfields
        String coord = tempX + "" + tempY;

        //Nur wenn die alte Koordinate gleich der neuen (bei einem Klick wird diese Methode öfter ausgelöst),
        //soll das gegenüberliegende Textfield angeglichen werden.
        if (oldCoord.equals(coord) && !TablePanel.textfieldArray[tempY][tempX].getText().equals(TablePanel.textfieldArray[tempX][tempY].getText())) {
            TablePanel.textfieldArray[tempY][tempX].setText(TablePanel.textfieldArray[tempX][tempY].getText());
        }
        oldCoord = coord;
    }

    /**
     * Changes the opposite entry for symmetrical reasons in case of deleting
     */
    public void removeUpdate(DocumentEvent e) {
        insertUpdate(e);
    }

    public void changedUpdate(DocumentEvent e) {
    }
}

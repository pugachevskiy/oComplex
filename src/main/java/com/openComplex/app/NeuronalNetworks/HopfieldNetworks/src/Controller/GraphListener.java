package com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller;

import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.View.*;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Admin on 29.06.2015.
 */
public class GraphListener implements MouseListener {

    public int yClick, xClick;

    public void mouseClicked(MouseEvent arg0) { }
    public void mouseEntered(MouseEvent arg0) { }
    public void mouseExited(MouseEvent arg0)  { }
    public void mousePressed(MouseEvent arg0) { }

    //Calculates pressed node
    public void mouseReleased(MouseEvent arg0) {
        xClick = arg0.getX();
        yClick = arg0.getY();
        ((GraphPanel)arg0.getSource()).updateClicks(xClick, yClick);
    }
}

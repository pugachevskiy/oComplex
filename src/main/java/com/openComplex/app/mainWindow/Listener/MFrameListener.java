package com.openComplex.app.mainWindow.Listener;

import com.openComplex.app.App;
import com.openComplex.app.CellularAutomat.GameOfLife.Controller.Life;
import com.openComplex.app.CellularAutomat.WolframsUniverse.MainWolfram;
import com.openComplex.app.DynamicalSystems.Fractals2.Controller.Controller;
import com.openComplex.app.DynamicalSystems.IteratedMaps.IMController;
import com.openComplex.app.DynamicalSystems.LogicalPictures.Feigenbaum;
import com.openComplex.app.DynamicalSystems.LorenzAttractor.Lorenz;
import com.openComplex.app.GraphTheory.MarkovKette.Markov;
import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by strange on 29/05/15.
 */

public class MFrameListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        switch (command) {
            case "Fractals":
                Controller fractal = new Controller();
                break;
            case "Conway's Game of Life":
               // Life gol = new Life();
                com.openComplex.app.CellularAutomat.GameOfLife2.Controller.Controller gol = new com.openComplex.app.CellularAutomat.GameOfLife2.Controller.Controller();
                break;
            case "Wolfram's universe":
                MainWolfram wolf = new MainWolfram();
                break;
            case "Lorenz Attractor":
                Lorenz lorenz = new Lorenz();
                break;
            case "Logical pictures with Feigenbaum-diagram":
                Feigenbaum fb = new Feigenbaum();
                break;
            case "Markov chain":
                Markov markov = new Markov();
                break;
            case "Iterated maps":
                IMController controller = new IMController();
                break;
            case "Hopfield networks":
                Main main = new Main();
                break;
            default:
                App.gui.updateGUI(command);
                break;
        }


        //System.out.println(Main.gui.buttonStrings.indexOf(command));

        //MainMenu-Buttons <Neuronal Networks, Graph theory, Cellular automata, Dynamic systems> pressed

        //neuronalMenu-Buttons pressed

        //graphMenu-Buttons pressed

        //cellularMenu-Buttons pressed

        //dynamicalMenu-Buttons pressed

        //Back-Button pressed

    }
}

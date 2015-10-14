package com.openComplex.app.mainWindow.Listener;

import com.openComplex.app.App;
import com.openComplex.app.CellularAutomat.WolframsUniverse.Controller.MainWolfram;
import com.openComplex.app.DynamicalSystems.Fractals.Controller.Controller;
import com.openComplex.app.DynamicalSystems.IteratedMaps.IMController;
import com.openComplex.app.DynamicalSystems.LogicalPictures.Feigenbaum;
import com.openComplex.app.DynamicalSystems.LorenzAttractor.Lorenz;
import com.openComplex.app.DynamicalSystems.NBodySimulations.GasIn2DimBox.GasApplet;
import com.openComplex.app.DynamicalSystems.NBodySimulations.SunEarthSim.SunEarthApplet;
import com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillator.AnharmonicOscillator;
import com.openComplex.app.DynamicalSystems.Oscillators.AnharmonicOscillatorsWithCoupling.AnharmonicOscillatorsWithCoupling;
import com.openComplex.app.DynamicalSystems.Oscillators.OscillatorsWithCoupling.OscillatorsWithCoupling;
import com.openComplex.app.DynamicalSystems.Oscillators.VibrationRotator.VibrationRotator;
import com.openComplex.app.DynamicalSystems.Pendulums.DoublePendulum.DoublePendulum;
import com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulum.DrivenPendulum;
import com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulumY.DrivenPendulumY;
import com.openComplex.app.DynamicalSystems.Pendulums.DrivenTriplePendulumY.DrivenTriplePendulumY;
import com.openComplex.app.DynamicalSystems.Pendulums.PendulumWithFreeMounting.PendulumWithFreeMounting;
import com.openComplex.app.DynamicalSystems.Pendulums.QuadBarPendulum.QuadBarPendulum;
import com.openComplex.app.DynamicalSystems.Pendulums.TripleBarPendulum.TripleBarPendulum;
import com.openComplex.app.DynamicalSystems.Pendulums.TriplePendulum.TriplePendulum;
import com.openComplex.app.GraphTheory.MarkovKette.Markov;
import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.src.Controller.Main;
import com.openComplex.app.DynamicalSystems.DLA.DLA;
import com.openComplex.app.DynamicalSystems.Oscillators.HarmonicOscillator.Oscillator;
import com.openComplex.app.DynamicalSystems.Pendulums.DeterministicChaos.Pendulums;

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
                new Controller();
                break;
            case "Conway's Game of Life":
                new com.openComplex.app.CellularAutomat.GameOfLife.Controller.Controller();
                break;
            case "Wolfram's universe":
                new MainWolfram();
                break;
            case "Lorenz Attractor":
                new Lorenz();
                break;
            case "Logical maps with Feigenbaum-diagram":
                new Feigenbaum();
                break;
            case "Markov chain":
                new Markov();
                break;
            case "Iterated maps":
                new IMController();
                break;
            case "Hopfield networks":
                new Main();
                break;
            default:
                App.gui.updateGUI(command);
                break;
            case "2 Double-Pendulums":
                new Pendulums();
                break;
            case "Diffusion Limited Aggregation":
                new DLA();
                break;
            case "Harmonic oscillator":
                new Oscillator();
                break;
            case "Anharmonic oscillator":
                new AnharmonicOscillator();
                break;
            case "2 oscillators with coupling":
                new OscillatorsWithCoupling();
                break;
            case "2 anharmonic oscillators with coupling":
                new AnharmonicOscillatorsWithCoupling();
                break;
            case "Nasch Model":
                new com.openComplex.app.CellularAutomat.NaschModel.Controller.Controller();
                break;
            case "Vibration-Rotator":
                new VibrationRotator();
                break;
            case "Double Pendulum":
                new DoublePendulum();
                break;
            case "Triple Pendulum":
                new TriplePendulum();
                break;
            case "Quad Bar Pendulum":
                new QuadBarPendulum();
                break;
            case "Pendulum with free mounting":
                new PendulumWithFreeMounting();
                break;
            case "Driven pendulum":
                new DrivenPendulum();
                break;
            case "Driven pendulum Y":
                new DrivenPendulumY();
                break;
            case "Driven triple pendulum":
                new DrivenTriplePendulumY();
                break;
            case "Gas in a 2-dimensional box":
                JFrame fr8 = new JFrame("Gas in a 2-dimensional box");
                fr8.setSize(800,500);
                GasApplet pend8 = new GasApplet();
                fr8.add(pend8);
                fr8.setVisible(true);
                fr8.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend8.init();
                break;
            case "Sun-Earth-Sim":
                JFrame fr9 = new JFrame("Sun-Earth-Sim");
                fr9.setSize(800,500);
                SunEarthApplet pend9 = new SunEarthApplet();
                fr9.add(pend9);
                fr9.setVisible(true);
                fr9.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend9.init();
                break;
            case "Triple Bar Pendulum":
                new TripleBarPendulum();
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

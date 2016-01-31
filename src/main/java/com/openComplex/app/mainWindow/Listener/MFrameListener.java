package com.openComplex.app.mainWindow.Listener;

import com.openComplex.app.App;
import com.openComplex.app.CellularAutomat.WolframsUniverse.Controller.MainWolfram;
import com.openComplex.app.DynamicalSystems.Fractals.Controller.Controller;
import com.openComplex.app.DynamicalSystems.IteratedMaps.IMController;
import com.openComplex.app.DynamicalSystems.LorenzAttractor.Lorenz;
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
import com.openComplex.app.NeuronalNetworks.HopfieldNetworks.Controller.Main;
import com.openComplex.app.DynamicalSystems.DLA.DLA;
import com.openComplex.app.DynamicalSystems.Oscillators.HarmonicOscillator.Oscillator;
import com.openComplex.app.DynamicalSystems.Pendulums.DeterministicChaos.Pendulums;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * on 29/05/15.
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
                //new Feigenbaum();
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
            case "NaSch Model":
                new com.openComplex.app.CellularAutomat.NaschModel.Controller.Controller();
                break;
            case "Vibration-Rotator":
                new VibrationRotator();
                break;
            case "Double-Pendulum":
                new DoublePendulum();
                break;
            case "Triple-Pendulum":
                new TriplePendulum();
                break;
            case "Quad-Bar-Pendulum":
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
                new com.openComplex.app.DynamicalSystems.NBodySimulations.GasIn2DimBox.Controller();
                break;
            case "Sun-Earth-Simulation":
                new com.openComplex.app.DynamicalSystems.NBodySimulations.SunEarthSim.Controller();
                break;
            case "Triple-Bar-Pendulum":
                new TripleBarPendulum();
                break;
            case "Multilayer perceptron":
                new com.openComplex.app.NeuronalNetworks.MultilayerPerceptron.Controller.Controller();
                break;
        }
    }
}

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
import com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulum.DrivPend;
import com.openComplex.app.DynamicalSystems.Pendulums.DrivenPendulumY.DrivPendY;
import com.openComplex.app.DynamicalSystems.Pendulums.DrivenTriplePendulum.Driv3PendY;
import com.openComplex.app.DynamicalSystems.Pendulums.PendulumWithFreeMounting.FreePend;
import com.openComplex.app.DynamicalSystems.Pendulums.QuadBarPendulum.QuadBarPend;
import com.openComplex.app.DynamicalSystems.Pendulums.TripleBarDoublePendulum.TripBarPend;
import com.openComplex.app.DynamicalSystems.Pendulums.TriplePendulum.TripPend;
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
                Controller fractal = new Controller();
                break;
            case "Conway's Game of Life":
                // Life gol = new Life();
                com.openComplex.app.CellularAutomat.GameOfLife.Controller.Controller gol = new com.openComplex.app.CellularAutomat.GameOfLife.Controller.Controller();
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
            case "2 Double-Pendulums":
                Pendulums pendel = new Pendulums();
                break;
            case "Diffusion Limited Aggregation":
                DLA dla = new DLA();
                break;
            case "Harmonic oscillator":
                Oscillator oscillator = new Oscillator();
                break;
            case "Anharmonic oscillator":
                AnharmonicOscillator anharmonicOscillator = new AnharmonicOscillator();
                break;
            case "2 oscillators with coupling":
                OscillatorsWithCoupling oscillatorsWithCoupling = new OscillatorsWithCoupling();
                break;
            case "2 anharmonic oscillators with coupling":
                AnharmonicOscillatorsWithCoupling anharmonicOscillatorsWithCoupling = new AnharmonicOscillatorsWithCoupling();
                break;
            case "Nasch Model":
                com.openComplex.app.CellularAutomat.NaschModel.Controller.Controller control = new com.openComplex.app.CellularAutomat.NaschModel.Controller.Controller();
                break;
            case "Vibration-Rotator":
                VibrationRotator vibrationRotator = new VibrationRotator();
                break;
            case "Double-DoublePendulum":
                DoublePendulum pend = new DoublePendulum();
                break;
            case "Triple-DoublePendulum":
                JFrame fr1 = new JFrame("Triple-DoublePendulum");
                fr1.setSize(800,500);
                TripPend pend1 = new TripPend();
                fr1.add(pend1);
                fr1.setVisible(true);
                fr1.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend1.init();
                break;
            case "Quad-Bar-DoublePendulum":
                JFrame fr3 = new JFrame("Quad-Bar-DoublePendulum");
                fr3.setSize(800,500);
                QuadBarPend pend3 = new QuadBarPend();
                fr3.add(pend3);
                fr3.setVisible(true);
                fr3.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend3.init();
                break;
            case "DoublePendulum with free mounting":
                JFrame fr4 = new JFrame("DoublePendulum with free mounting");
                fr4.setSize(800,500);
                FreePend pend4 = new FreePend();
                fr4.add(pend4);
                fr4.setVisible(true);
                fr4.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend4.init();
                break;
            case "Driven pendulum":
                JFrame fr5 = new JFrame("Driven pendulum");
                fr5.setSize(800,500);
                DrivPend pend5 = new DrivPend();
                fr5.add(pend5);
                fr5.setVisible(true);
                fr5.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend5.init();
                break;
            case "Driven pendulum Y":
                JFrame fr6 = new JFrame("Driven pendulum Y");
                fr6.setSize(800,500);
                DrivPendY pend6 = new DrivPendY();
                fr6.add(pend6);
                fr6.setVisible(true);
                fr6.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend6.init();
                break;
            case "Driven triple pendulum":
                JFrame fr7 = new JFrame("Driven triple pendulum");
                fr7.setSize(800,500);
                Driv3PendY pend7 = new Driv3PendY();
                fr7.add(pend7);
                fr7.setVisible(true);
                fr7.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend7.init();
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
            case "Triple-Bar-DoublePendulum":
                JFrame fr10 = new JFrame("Triple-Bar-DoublePendulum");
                fr10.setSize(800,500);
                TripBarPend pend10 = new TripBarPend();
                fr10.add(pend10);
                fr10.setVisible(true);
                fr10.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                pend10.init();
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

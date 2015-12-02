package com.openComplex.app.mainWindow.View;

import java.util.Arrays;
import java.util.List;

public final class TopicCollection {

    public static final List<String> menuItems = Arrays.asList(
            "Main",

            "Dynamical Systems",
                "\tBifurcation",
                "\tIterated maps",
                "\tLorenz attractor",
                "\tDiffusion Limited Aggregation",
                "\tFractals",
                "\tOscillators",
                    "\t\tHarmonic oscillator",
                    "\t\tAnharmonic oscillator",
                    "\t\t2 oscillators with coupling",
                    "\t\t2 anharmonic oscillators with coupling",
                    "\t\tVibration-Rotator",
                "\tPendulums",
                    "\t\tDouble-Pendulum",
                    "\t\tTriple-Pendulum",
                    "\t\tTriple-Bar-Pendulum",
                    "\t\tQuad-Bar-Pendulum",
                    "\t\tPendulum with free mounting",
                    "\t\tDriven pendulum",
                    "\t\tDriven pendulum Y",
                    "\t\tDriven triple pendulum",
                "\tDeterministic chaos",
                    "\t\tDouble-DoublePendulum",
                "\tN-Body-Simulations",
                    "\t\tGas in a 2-dimensional box",
                    "\t\tSun-Earth-Simulation",/////////////////////

            "Cellular automata",
                "\tConway's Game of Life",
                "\tWolfram's universe",
                "\tModel of Hegselmann",
                "\tNaSch Model",

            "Neuronal networks",/*
                "Feedforward networks",
                "Recurrent networks",*/
                "\tHopfield networks",
                "\tMultilayer perceptron",

            "Graph theory",
                "\tMarkov chain"/*,
                "BjoernSchuller-number",
                "Trees",
                "Little World networks",*/

    );


    ///////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////               Dynamic Panels                //////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static final List<String> dynamicMainPanel = Arrays.asList(
            "Dynamical systems",
            "resources/natureFractal.png",
            "A dynamical system is a concept in mathematics\n" +
            "where a fixed rule describes how a point in a geometrical space depends on time.\n" +
            "The progress depends on the initial state and is defined by a set of real numbers.\n" +
            "Examples include the mathematic models that describe the swinging of a clock, ...");

        public static final List<String> logicalPictures = Arrays.asList(
                "Logical maps with Feigenbaum-diagram",
                //"resources/flocke.png",
                "TODO");
        public static final List<String> iteratedMaps = Arrays.asList(
                "Iterated maps",
                //"resources/flocke.png",
                "In mathematics, the Gauss map (also known as Gaussian map or mouse map), is a nonlinear\n" +
                "iterated map of the reals into a real interval given by the Gaussian function.");
        public static final List<String> lorenzAttractor = Arrays.asList(
                "Lorenz Attractor",
                //"resources/flocke.png",
                "The Lorenz system is a system of ordinary differential equations (the Lorenz equations,\n" +
                "note it is not Lorentz) first studied by Edward Lorenz. It is notable for having chaotic\n" +
                "solutions for certain parameter values and initial conditions. In particular, the Lorenz\n" +
                "attractor is a set of chaotic solutions of the Lorenz system which, when plotted,\n" +
                "resemble a butterfly or figure eight.");
        public static final List<String> diffLimAggregation = Arrays.asList(
                "Diffusion Limited Aggregation",
                //"resources/flocke.png",
                "Diffusion-limited aggregation (DLA) is the process whereby particles undergoing a \n" +
                "random walk due to Brownian motion cluster together to form aggregates of such particles. This theory,\n" +
                "proposed by T.A. Witten Jr. and L.M. Sander in 1981, is applicable to aggregation in any system where\n" +
                "diffusion is the primary means of transport in the system. DLA can be observed in many systems such as\n" +
                "electrodeposition, Hele-Shaw flow, mineral deposits, and dielectric breakdown.");
        public static final List<String> fractals = Arrays.asList(
                "Fractals",
                //"resources/flocke.png",
                "A fractal is a natural phenomenon or a mathematical set that exhibits a repeating pattern \n" +
                "that displays at every scale. It is also known as expanding symmetry or evolving symmetry. If the\n" +
                "replication is exactly the same at every scale, it is called a self-similar pattern. An example of\n" +
                "this is the Menger Sponge. Fractals can also be nearly the same at different levels. This latter\n" +
                "pattern is illustrated in the magnifications of the Mandelbrot set. Fractals also include\n" +
                "the idea of a detailed pattern that repeats itself.");
        public static final List<String> oscillators = Arrays.asList(
                "Oscillators",
               // "resources/flocke.png",
                "An electronic oscillator is an electronic circuit that produces a periodic, oscillating electronic signal, \n" +
                "often a sine wave or a square wave. Oscillators convert direct current (DC) from a\n" +
                " power supply to an alternating current signal. They are widely used in many electronic devices.\n" +
                " Common examples of signals generated by oscillators include signals broadcast by radio and television\n" +
                " transmitters, clock signals that regulate computers and quartz clocks, and the sounds produced by\n" +
                " electronic beepers and video games.");

            public static final List<String> anharmonicOSC = Arrays.asList(
                    "Anharmonic oscillator",
                    // "resources/flocke.png",
                    "An electronic oscillator is an electronic circuit that produces a periodic, oscillating electronic signal, \n" +
                            "often a sine wave or a square wave. Oscillators convert direct current (DC) from a\n" +
                            " power supply to an alternating current signal. They are widely used in many electronic devices.\n" +
                            " Common examples of signals generated by oscillators include signals broadcast by radio and television\n" +
                            " transmitters, clock signals that regulate computers and quartz clocks, and the sounds produced by\n" +
                            " electronic beepers and video games.");
            public static final List<String> anharmonicOSCwC = Arrays.asList(
                    "2 anharmonic oscillators with coupling",
                    // "resources/flocke.png",
                    "An electronic oscillator is an electronic circuit that produces a periodic, oscillating electronic signal, \n" +
                            "often a sine wave or a square wave. Oscillators convert direct current (DC) from a\n" +
                            " power supply to an alternating current signal. They are widely used in many electronic devices.\n" +
                            " Common examples of signals generated by oscillators include signals broadcast by radio and television\n" +
                            " transmitters, clock signals that regulate computers and quartz clocks, and the sounds produced by\n" +
                            " electronic beepers and video games.");
            public static final List<String> harmonicOSC = Arrays.asList(
                    "Harmonic oscillator",
                    // "resources/flocke.png",
                    "An electronic oscillator is an electronic circuit that produces a periodic, oscillating electronic signal, \n" +
                            "often a sine wave or a square wave. Oscillators convert direct current (DC) from a\n" +
                            " power supply to an alternating current signal. They are widely used in many electronic devices.\n" +
                            " Common examples of signals generated by oscillators include signals broadcast by radio and television\n" +
                            " transmitters, clock signals that regulate computers and quartz clocks, and the sounds produced by\n" +
                            " electronic beepers and video games.");
            public static final List<String> couplingOSC = Arrays.asList(
                    "2 oscillators with coupling",
                    // "resources/flocke.png",
                    "An electronic oscillator is an electronic circuit that produces a periodic, oscillating electronic signal, \n" +
                            "often a sine wave or a square wave. Oscillators convert direct current (DC) from a\n" +
                            " power supply to an alternating current signal. They are widely used in many electronic devices.\n" +
                            " Common examples of signals generated by oscillators include signals broadcast by radio and television\n" +
                            " transmitters, clock signals that regulate computers and quartz clocks, and the sounds produced by\n" +
                            " electronic beepers and video games.");
            public static final List<String> vibrRot = Arrays.asList(
                    "Vibration-Rotator",
                    // "resources/flocke.png",
                    "An electronic oscillator is an electronic circuit that produces a periodic, oscillating electronic signal, \n" +
                            "often a sine wave or a square wave. Oscillators convert direct current (DC) from a\n" +
                            " power supply to an alternating current signal. They are widely used in many electronic devices.\n" +
                            " Common examples of signals generated by oscillators include signals broadcast by radio and television\n" +
                            " transmitters, clock signals that regulate computers and quartz clocks, and the sounds produced by\n" +
                            " electronic beepers and video games.");

        public static final List<String> pendulums = Arrays.asList(
                "Pendulums",
                //"resources/flocke.png",
                "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                " the width of the pendulum's swing.");
            public static final List<String> doublePEN = Arrays.asList(
                    "Double-Pendulum",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");
            public static final List<String> triplePEN = Arrays.asList(
                    "Triple-Pendulum",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");
            public static final List<String> tripleBarPEN = Arrays.asList(
                    "Triple-Bar-Pendulum",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");
            public static final List<String> quatBarPEN = Arrays.asList(
                    "Quad-Bar-Pendulum",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");
            public static final List<String> freeMountPEN = Arrays.asList(
                    "Pendulum with free mounting",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");
            public static final List<String> drivenPEN = Arrays.asList(
                    "Driven pendulum",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");
            public static final List<String> drivenYPEN = Arrays.asList(
                    "Driven pendulum Y",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");
            public static final List<String> drivenTriplePEN = Arrays.asList(
                    "Driven triple pendulum",
                    //"resources/flocke.png",
                    "A pendulum is a weight suspended from a pivot so that it can swing freely. When a pendulum is\n" +
                            " displaced sideways from its resting, equilibrium position, it is subject to a restoring force due to\n" +
                            " gravity that will accelerate it back toward the equilibrium position. When released, the restoring force\n" +
                            " combined with the pendulum's mass causes it to oscillate about the equilibrium position, swinging\n" +
                            " back and forth. The time for one complete cycle, a left swing and a right swing, is called the period.\n" +
                            " The period depends on the length of the pendulum, and also to a slight degree on the amplitude,\n" +
                            " the width of the pendulum's swing.");


        public static final List<String> deterministicChaos = Arrays.asList(
                "Deterministic chaos",
                "resources/flocke.png",
                "Chaos theory is the field of study in mathematics that studies the behavior of dynamical systems\n" +
                "that are highly sensitive to initial conditions - a response popularly referred to as\n" +
                "the butterfly effect.");
            public static final List<String> twoDoublePens = Arrays.asList(
                    "2 Double-Pendulums",
                    //"resources/flocke.png",
                    "TODO");
        public static final List<String> nBodySimulations = Arrays.asList(
                "N-Body-Simulations",
                "resources/flocke.png",
                "In physics and astronomy, an N-body simulation is a simulation of a dynamical system of particles,\n" +
                " usually under the influence of physical forces, such as gravity (see n-body problem). N-body simulations\n" +
                " are widely used tools in astrophysics, from investigating the dynamics of few-body systems like the\n" +
                " Earth-Moon-Sun system to understanding the evolution of the large-scale structure of the universe.\n" +
                " In physical cosmology, N-body simulations are used to study processes of non-linear structure formation\n" +
                " such as galaxy filaments and galaxy halos from the influence of dark matter. Direct N-body simulations are\n" +
                " used to study the dynamical evolution of star clusters.");
            public static final List<String> gasInTwoDimBox = Arrays.asList(
                    "Gas in a 2-dimensional box",
                    //"resources/flocke.png",
                    "behaviour of gas in a two dimensional box");
            public static final List<String> sunEarthSim = Arrays.asList(
                    "Sun-Earth-Simulation",
                    //"resources/flocke.png",
                    "Simulations for corresponding sun and earth");


    ///////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////               Cellular Panels              //////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static final List<String> cellularAutomata = Arrays.asList(
            "Cellular automata",
            "resources/flocke.png",
            menuItems.get(2));

        public static final List<String> gol = Arrays.asList(
                "Conway's Game of Life",
                //"resources/flocke.png",
               "The Game of Life, also known simply as Life, is a cellular automaton devised by the British\n" +
               " mathematician John Horton Conway in 1970. The \"game\" is a zero-player game, meaning that its\n" +
               " evolution is determined by its initial state, requiring no further input. One interacts with the\n" +
               " Game of Life by creating an initial configuration and observing how it evolves or, for\n" +
               " advanced players, by creating patterns with particular properties.");
        public static final List<String> wolfram = Arrays.asList(
                "Wolfram's universe",
                //"resources/flocke.png",
                "TODO");
        public static final List<String> hegelmann = Arrays.asList(
                "Model of Hegselmann",
                //"resources/flocke.png",
                "TODO");
        public static final List<String> naSCH = Arrays.asList(
                "NaSch Model",
                //"resources/flocke.png",
               "TODO");

    ///////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////               Neuronal Panels                //////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////


    public static final List<String> neuronalMainPanel = Arrays.asList(
            "Neuronal networks",
            "resources/flocke.png",
            "In machine learning and cognitive science,\n" +
            "artificial neural networks (ANNs) are a family of statistical learning models\n" +
            "inspired by biological neural networks (the central nervous systems of animals,\n" +
            "in particular the brain) and are used to estimate or approximate functions\n" +
            "that can depend on a large number of inputs and are generally unknown.");


        public static final List<String> hopfield = Arrays.asList(
                "Hopfield networks",
                //"resources/flocke.png",
                "Hopfield nets are a sort of artificial neural networks. Such nets\n" +
                "hold only one layer, consisting of a set of nodes. This program\n" +
                "creates a graphical representation of the hopfield net based on the weight matrix.\n\n" +
                "Rules for the weight matrix: \n" +
                "-No neuron is allowed to have a link to itself(->diagonal axis = 0)\n" +
                "-The connections are undirected(->weight matrix has to be symmetrical)\n" +
                "-Alle neurons have to be connected to each other(->weights never 0)");

    public static final List<String> multilayer = Arrays.asList(
            "Multilayer perceptron",
            //"resources/flocke.png",
            "Hopfield nets are a sort of artificial neural networks. Such nets\n" +
                    "hold only one layer, consisting of a set of nodes. This program\n" +
                    "creates a graphical representation of the hopfield net based on the weight matrix.\n\n" +
                    "Rules for the weight matrix: \n" +
                    "-No neuron is allowed to have a link to itself(->diagonal axis = 0)\n" +
                    "-The connections are undirected(->weight matrix has to be symmetrical)\n" +
                    "-Alle neurons have to be connected to each other(->weights never 0)");
    /*
        public static final List<String> feedforward = Arrays.asList(
                "Hopfield networks",
                "resources/flocke.png",
                "In machine learning and cognitive science,\n" +
                        "artificial neural networks (ANNs) are a family of statistical learning models\n" +
                        "inspired by biological neural networks (the central nervous systems of animals,\n" +
                        "in particular the brain) and are used to estimate or approximate functions\n" +
                        "that can depend on a large number of inputs and are generally unknown.");

        public static final List<String> recurrent = Arrays.asList(
                "Hopfield networks",
                "resources/flocke.png",
                "In machine learning and cognitive science,\n" +
                        "artificial neural networks (ANNs) are a family of statistical learning models\n" +
                        "inspired by biological neural networks (the central nervous systems of animals,\n" +
                        "in particular the brain) and are used to estimate or approximate functions\n" +
                        "that can depend on a large number of inputs and are generally unknown.");

    */


    ///////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////               Graph Panels                ///////////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////

    public static final List<String> graphMainPanel = Arrays.asList(
            "Graph theory",
            "resources/flocke.png",
            "In mathematics and computer science, graph theory is\n" +
            "the study of graphs, which are mathematical structures used to model pairwise\n" +
            " relations between objects. Generally, graphs consist of nodes and edges.\n");

        public static final List<String> markov = Arrays.asList(
                "Markov chain",
                //"resources/flocke.png",
                "A Markov chain (discrete-time Markov chain or DTMC), named after Andrey Markov, is a random\n" +
                "process that undergoes transitions from one state to another on a state space. It must possess\n" +
                "a property that is usually characterized as \"memorylessness\": the probability distribution of\n" +
                "the next state depends only on the current state and not on the sequence of events that preceded it.\n" +
                "This specific kind of \"memorylessness\" is called the Markov property. Markov chains have many \n" +
                "applications as statistical models of real-world processes.");

    public static final List<List<String>> allPanels = Arrays.asList(
            dynamicMainPanel,
                logicalPictures,
                iteratedMaps,
                lorenzAttractor,
                diffLimAggregation,
                fractals,
                oscillators,
                    harmonicOSC,
                    anharmonicOSC,
                    couplingOSC,
                    anharmonicOSCwC,
                    vibrRot,
                pendulums,
                    doublePEN,
                    triplePEN,
                    tripleBarPEN,
                    quatBarPEN,
                    freeMountPEN,
                    drivenPEN,
                    drivenYPEN,
                    drivenTriplePEN,
                deterministicChaos,
                    twoDoublePens,
                nBodySimulations,
                    gasInTwoDimBox,
                    sunEarthSim,
                cellularAutomata,
                    gol,
                    wolfram,
                    hegelmann,
                    naSCH,
            neuronalMainPanel,
                hopfield,
                multilayer,
            graphMainPanel,
                markov
    );

    String []t={  "\t\tDouble-Pendulum",
            "\t\tTriple-Pendulum",
            "\t\tTriple-Bar-Pendulum",
            "\t\tQuad-Bar-Pendulum",
            "\t\tPendulum with free mounting",
            "\t\tDriven pendulum",
            "\t\tDriven pendulum Y",
            "\t\tDriven triple pendulum"};




}
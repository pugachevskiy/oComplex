package com.openComplex.app.CellularAutomat.GameOfLife.View;

import com.openComplex.app.CellularAutomat.GameOfLife.Controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class View {

    private Controller controller;
    private JFrame mainFrame;
    private JButton stopButton, startButton, nextButton, endButton;
    private JComboBox<String> anfangsBedingungBox, cellFormBox, cellGroeßeBox, cellFarbeBox, geschwindigkeitBox;
    List<JComboBox<String>> componentList;
    private JLabel speedLabel;

    public static final String[] ANFANGSBEDINGUNGFILL = {"Pigeon", "Figure 1", "Gliter", "blank"}, CELLFORMFILL = {"Square", "Hexagon"},
            CELLGROESSEFILL = {"Small", "Medium", "Large"}, CELLFARBEFILL = {"Black", "Blue", "Green", "Yellow"},
            GESCHWINDIGKEITFILL = {"Slow", "Normal", "Fast"};
    private JLabel counter;

    public static final String[] labelTitleList = {"Initial model", "Cell Form", "Size of cells", "Cell color"};
    private JMenuItem saveItem, exitItem, ruleItem, loadItem;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void init() {
        mainFrame = new JFrame("openCoSy - Conway's Game of Life");

        mainFrame.setLayout(new BorderLayout());
        mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(600, 600));
        initComboBoxes();

        mainFrame.setJMenuBar(addMenu());


        mainFrame.add(new JPanel(), BorderLayout.WEST);
        mainFrame.add(createOptionsPanel(), BorderLayout.NORTH);

        mainFrame.add(createButtonsPanel(), BorderLayout.SOUTH);
        mainFrame.requestFocus();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }


    private JMenuBar addMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu firstMenu = new JMenu("Data");
        saveItem = new JMenuItem("Save");
        saveItem.setActionCommand("Save");
        loadItem = new JMenuItem("Load");
        loadItem.setActionCommand("Load");
        exitItem = new JMenuItem("Exit");
        exitItem.setActionCommand("Exit");
        firstMenu.add(saveItem);
        firstMenu.add(loadItem);
        firstMenu.addSeparator();
        firstMenu.add(exitItem);

        JMenu secondMenu = new JMenu("Help");
        ruleItem = new JMenuItem("Rule");
        ruleItem.setActionCommand("Rule");
        secondMenu.add(ruleItem);

        menuBar.add(firstMenu);
        menuBar.add(secondMenu);
        return menuBar;
    }


    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(1, 6));
        optionsPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        for (int i = 0; i < labelTitleList.length; i++) {
            JLabel tempLabel = new JLabel(labelTitleList[i]);
            tempLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel tempPanel = new JPanel();
            tempPanel.setLayout(new GridLayout(2, 1));
            tempPanel.add(tempLabel);
            tempPanel.add(componentList.get(i));
            tempPanel.setBorder(new EmptyBorder(0, 3, 0, 3));

            optionsPanel.add(tempPanel);
        }

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new GridLayout(2, 1));
        sliderPanel.setBorder(new EmptyBorder(0, 3, 0, 3));
        speedLabel = new JLabel("Speed");
        speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sliderPanel.add(speedLabel);

        JSlider slider = new JSlider(SwingConstants.HORIZONTAL, 1, 1000, 250);
        slider.setPaintLabels(true);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = ((JSlider) e.getSource()).getValue();
                controller.updateSpeed(value);
                updateSpeed(value);
            }
        });
        sliderPanel.add(slider);
        optionsPanel.add(sliderPanel);
        updateSpeed(slider.getValue());


        JPanel counterPanel = new JPanel();
        counterPanel.setLayout(new GridLayout(2, 1));
        JLabel counterLabel = new JLabel("Step");
        counterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        counterPanel.add(counterLabel);
        counter = new JLabel("0");
        counter.setHorizontalAlignment(SwingConstants.CENTER);
        counterPanel.add(counter);
        optionsPanel.add(counterPanel);

        return optionsPanel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        startButton = new JButton("Start");
        startButton.setEnabled(true);
        startButton.setActionCommand("Start");


        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);
        stopButton.setActionCommand("Stop");


        nextButton = new JButton("Next");
        nextButton.setEnabled(true);
        nextButton.setActionCommand("Next");


        endButton = new JButton("Exit");
        endButton.setActionCommand("Exit");
        endButton.setEnabled(true);


        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(endButton);

        return buttonPanel;
    }


    private void initComboBoxes() {
        anfangsBedingungBox = new JComboBox<>(ANFANGSBEDINGUNGFILL);
        anfangsBedingungBox.setEnabled(true);
        anfangsBedingungBox.setActionCommand("Initial model");

        cellFormBox = new JComboBox<>(CELLFORMFILL);
        cellFormBox.setEnabled(true);
        cellFormBox.setActionCommand("Cell form");

        cellGroeßeBox = new JComboBox<>(CELLGROESSEFILL);
        cellGroeßeBox.setEnabled(true);
        cellGroeßeBox.setActionCommand("Size of cells");
        cellGroeßeBox.setSelectedIndex(1);

        cellFarbeBox = new JComboBox<>(CELLFARBEFILL);
        cellFarbeBox.setActionCommand("Cell color");

        geschwindigkeitBox = new JComboBox<>(GESCHWINDIGKEITFILL);
        geschwindigkeitBox.setSelectedIndex(1);
        geschwindigkeitBox.setActionCommand("Speed");

        componentList = Arrays.asList(anfangsBedingungBox, cellFormBox, cellGroeßeBox, cellFarbeBox, geschwindigkeitBox);
    }

    // On/Off buttons on Press start/stop button
    public void deactivateButtons() {
        stopButton.setEnabled(true);
        startButton.setEnabled(false);
        nextButton.setEnabled(false);
        anfangsBedingungBox.setEnabled(false);
        cellFormBox.setEnabled(false);
        cellGroeßeBox.setEnabled(false);
    }

    public void activateButtons() {
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
        nextButton.setEnabled(true);
        anfangsBedingungBox.setEnabled(true);
        cellFormBox.setEnabled(true);
        cellGroeßeBox.setEnabled(true);
    }

    // add Field Jpanel to JFrame
    public void addField(JPanel panel) {
        mainFrame.add(panel, BorderLayout.CENTER);
    }

    public void deleteField(JPanel panel) {
        mainFrame.remove(panel);
    }

    public void addListener(ActionListener listener) {
        startButton.addActionListener(listener);
        stopButton.addActionListener(listener);
        endButton.addActionListener(listener);
        nextButton.addActionListener(listener);
        cellFarbeBox.addActionListener(listener);
        cellFormBox.addActionListener(listener);
        cellGroeßeBox.addActionListener(listener);
        anfangsBedingungBox.addActionListener(listener);
        geschwindigkeitBox.addActionListener(listener);
        exitItem.addActionListener(listener);
        ruleItem.addActionListener(listener);
        saveItem.addActionListener(listener);
        loadItem.addActionListener(listener);
    }

    public void frameClose() {
        mainFrame.dispose();
    }

    public void setCounter(String text) {
        this.counter.setText(text);
    }

    public int getFigureBox() {
        return anfangsBedingungBox.getSelectedIndex();
    }

    public int getColorBox() {
        return cellFarbeBox.getSelectedIndex();
    }

    public int getSpeedBox() {
        return geschwindigkeitBox.getSelectedIndex();
    }

    public int getCellForm() {
        return cellFormBox.getSelectedIndex();
    }

    public int getSizeBox() {
        return cellGroeßeBox.getSelectedIndex();
    }

    public void getRule() {
        JFrame regeln = new JFrame("Regeln");

        regeln.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea text = new JTextArea("Spielregeln\n" +
                "a) Wenn eine lebende Zelle zwei oder drei lebende Nachbarn hat, dann bleibt sie fu ̈r\n" +
                "die na ̈chste Generation am Leben.\n" +
                "b) Wenn eine lebende Zelle weniger als zwei lebende Nachbarn hat, dann stirbt sie\n" +
                "an Vereinsamung (und ist in der na ̈chsten Generation leer). Nachbarn hat.\n" +
                "c) Wenn eine lebende Zelle mehr als drei lebende Nachbarn hat, dann stirbt sie wegen U ̈berbevo ̈lkerung.\n" +
                "d) Wenn eine tote Zelle genau drei Nachbarn hat, dann wird sie neugeboren.");
        text.setEditable(false);
        regeln.add(text);

        regeln.requestFocus();
        regeln.setResizable(false);
        regeln.pack();
        regeln.setVisible(true);
    }

    public void updateSpeed(int value) {
        speedLabel.setText("Speed " + value + "ms");
    }
}
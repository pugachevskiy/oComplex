package com.openComplex.app.CellularAutomat.GameOfLife.View;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange1 on 17/11/14.
 */
public class ViewController {

	//private Color backgroundColor = new Color(255,255,255);
	private JPanel fieldPanel;
	private JFrame mainFrame;
	private JButton stopButton, startButton, nextButton, endButton;
	private JComboBox<String> anfangsBedingungBox, cellFormBox, cellGroeßeBox, cellFarbeBox, geschwindigkeitBox;
	private static final String[] ANFANGSBEDINGUNGFILL = {"Taube", "blank"}, CELLFORMFILL = { "Quadratisch", "Hexagon" },
			CELLGROESSEFILL = { "Klein", "Mittel", "Groß" }, CELLFARBEFILL = { "Schwarz", "Blau", "Grün", "Gelb" },
			GESCHWINDIGKEITFILL = { "Langsam", "Normal", "Schnell" } ;
	private JMenuItem speichernItem, exitItem, spielRegelnItem;


	public ViewController() {
		mainFrame = new JFrame("Conways Game of Life");
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		mainFrame.setJMenuBar(addMenu());

		JPanel menuPanel;
		mainFrame.add(menuPanel = new JPanel(), BorderLayout.WEST);
		mainFrame.add(fieldPanel = new JPanel(), BorderLayout.CENTER);
		fieldPanel.setSize(600,600);
		fieldPanel.setPreferredSize(new Dimension(600,600));
		addComponentsToPane(menuPanel, fieldPanel);

		mainFrame.requestFocus();
		mainFrame.setResizable(false);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	private JMenuBar addMenu() {
		JMenuBar menuBar = new JMenuBar();

			JMenu firstMenu = new JMenu("Datei");
				speichernItem = new JMenuItem("Speichern");
				exitItem = new JMenuItem("Exit");
			firstMenu.add(speichernItem);
			firstMenu.addSeparator();
			firstMenu.add(exitItem);

			JMenu secondMenu = new JMenu("Hilfe");
				spielRegelnItem = new JMenuItem("Spielregeln");
			secondMenu.add(spielRegelnItem);

		menuBar.add(firstMenu);
		menuBar.add(secondMenu);
		return menuBar;
	}
	private void addComponentsToPane(Container pane, Container thirdpane) {
		pane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		thirdpane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		addControl(pane, c);
	}
	private void addControl(Container pane, GridBagConstraints c) {
		JLabel anfangsLabel = new JLabel("Anfangsbedingung");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		pane.add(anfangsLabel, c);
		anfangsBedingungBox = new JComboBox<>(ANFANGSBEDINGUNGFILL);
		anfangsBedingungBox.setEnabled(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		pane.add(anfangsBedingungBox, c);


		JLabel cellFormLabel = new JLabel("Cell Form");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		pane.add(cellFormLabel, c);
		cellFormBox = new JComboBox<>(CELLFORMFILL);
		cellFormBox.setEnabled(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		pane.add(cellFormBox, c);


		JLabel cellGroeßeLabel = new JLabel("Zellgröße");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		pane.add(cellGroeßeLabel, c);
		cellGroeßeBox = new JComboBox<>(CELLGROESSEFILL);
		cellGroeßeBox.setEnabled(true);
		cellGroeßeBox.setSelectedIndex(1);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		pane.add(cellGroeßeBox, c);


		JLabel cellFarbeLabel = new JLabel("Zellfarbe");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		pane.add(cellFarbeLabel, c);


		cellFarbeBox = new JComboBox<>(CELLFARBEFILL);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 8;
		c.gridwidth = 1;
		pane.add(cellFarbeBox, c);


		JLabel geschwindigkeitLabel = new JLabel("Geschwindigkeit");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		pane.add(geschwindigkeitLabel, c);


		geschwindigkeitBox = new JComboBox<>(GESCHWINDIGKEITFILL);
		geschwindigkeitBox.setSelectedIndex(1);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 10;
		c.gridwidth = 1;
		pane.add(geschwindigkeitBox, c);


		startButton = new JButton("Start");
		startButton.setEnabled(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 11;
		c.gridwidth = 1;
		pane.add(startButton, c);


		stopButton = new JButton("Stop");
		stopButton.setEnabled(false);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 12;
		c.gridwidth = 1;
		pane.add(stopButton, c);


		nextButton = new JButton("Next");
		nextButton.setEnabled(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 13;
		c.gridwidth = 1;
		pane.add(nextButton, c);


		endButton = new JButton("Beenden");
		endButton.setEnabled(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 14;
		c.gridwidth = 1;
		pane.add(endButton, c);


	}
	// On/Off buttons on Press start/stop button
	public void activateButtons(){
		stopButton.setEnabled(true);
		startButton.setEnabled(false);
		nextButton.setEnabled(false);
		anfangsBedingungBox.setEnabled(false);
		cellFormBox.setEnabled(false);
		cellGroeßeBox.setEnabled(false);
	}
	public void deactivateButtons(){
		stopButton.setEnabled(false);
		startButton.setEnabled(true);
		nextButton.setEnabled(true);
		anfangsBedingungBox.setEnabled(true);
		cellFormBox.setEnabled(true);
		cellGroeßeBox.setEnabled(true);
	}
	//Getters und Setters
	public Dimension getFieldSize(){
		return fieldPanel.getSize();
	}
	public JButton getStopButton(){
		return stopButton;
	}
	public JButton getStartButton(){
		return startButton;
	}
	public JButton getEndButton(){
		return endButton;
	}
	public JButton getNextButton(){
		return nextButton;
	}
	public JComboBox<String> getAnfangsBedingungBox(){
		return anfangsBedingungBox;
	}
	public JComboBox<String> getCellFormBox(){
		return cellFormBox;
	}
	public JComboBox<String> getCellGroeßeBox(){
		return cellGroeßeBox;
	}
	public JComboBox<String> getCellFarbeBox(){
		return cellFarbeBox;
	}
	public JComboBox<String> getGeschwindigkeitBox(){
		return geschwindigkeitBox;
	}
	public JMenuItem getSpeichernItem(){
		return speichernItem;
	}
	public JMenuItem getExitItem(){
		return exitItem;
	}
	public JMenuItem getSpielRegelnItem(){
		return spielRegelnItem;
	}
	public JPanel getFieldPanel(){
		return fieldPanel;
	}
	public void frameClose(){
		mainFrame.dispose();
	}
	
}

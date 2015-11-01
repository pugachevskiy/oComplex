package com.openComplex.app.mainWindow.Model;

import com.openComplex.app.mainWindow.Listener.MFrameListener;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  on 16/06/15.
 */
public class ModelMain {
    private List<JButton> buttonList = new ArrayList<>();
    private ActionListener listener = new MFrameListener();
    public void createButtons(JPanel pane, List<String> list) {

        for (int i = 0; i < list.size(); i++) {
            buttonList.add(new JButton(list.get(i)));
            buttonList.get(i).addActionListener(listener);
            buttonList.get(i).setActionCommand(list.get(i));
            pane.add(buttonList.get(i));
        }
    }

}

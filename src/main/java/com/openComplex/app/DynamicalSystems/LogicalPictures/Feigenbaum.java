package com.openComplex.app.DynamicalSystems.LogicalPictures;

/**
 * Created by strange on 25/08/15.
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Feigenbaum implements ActionListener, MouseListener {

    private FeigenbaumView gui;
    private FeigenbaumModel model;

    public String thePressedKey = " ";

    public Feigenbaum() {
        gui = new FeigenbaumView();
        model = new FeigenbaumModel();
        addListener();
        gui.addPanel(model);

    }
    private void addListener(){
        gui.getButtonStart().addActionListener(this);
        gui.getButtonClear().addActionListener(this);
        gui.getButtonMore().addActionListener(this);
    }

    private void start() {
        new Thread() {
            public void run() {
                while (model.myGo) {
                    try {
                        model.update();
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    model.repaint();
                }
            }
        }.start();
    }
     public void makenewr() {
        if (model.xup < 400 && model.yup < 300) {
            model.generateCoordinate(gui.getTextFieldList());
            model.initCoord(gui.getTextFieldList());
            thePressedKey = "c";
            model.myGo = true;
            this.start();
            model.update();
        }
    }





  /*  public boolean handleEvent(Event event) {
        if (event.target == this && event.id == Event.KEY_PRESS) {
            Feigenbaum_KeyPress(event);
            return true;
        }
        if (event.target == this && event.id == Event.MOUSE_DOWN) {
            Feigenbaum_MouseDown(event);
            return true;
        }
        if (event.target == this && event.id == Event.MOUSE_UP) {
            Feigenbaum_MouseUp(event);
            return true;
        }
        if (event.target == gui.getButtonStart() && event.id == Event.ACTION_EVENT) {   // Start button
            thePressedKey = "n";
            myGo = true;
            this.start();
            model.repaint();
            return true;
        }
        if (event.target == gui.getButtonClear() && event.id == Event.ACTION_EVENT) {   // Clear button
            thePressedKey = "c";
            myGo = true;
            this.start();
            model.repaint();
            return true;
        }
        if (event.target == gui.getButtonMore() && event.id == Event.ACTION_EVENT) {   // More button
            thePressedKey = "p";
            myGo = true;
            this.start();
            model.repaint();
            return true;
        }
        if (event.target == checkbox1 && event.id == Event.ACTION_EVENT) {
            checkbox1_Action(event);
            return true;
        }
        if (event.target == this && event.id == Event.MOUSE_DRAG) {
            Feigenbaum_MouseDrag(event);
            return true;
        }
        return false;
        //return super.handleEvent(event);
    }*/


   /* void Feigenbaum_KeyPress(Event event) {
        thePressedKey = ""+(char)event.key;
        myGo = true;
        this.start();
        repaint();
    }*/

  /*  void Feigenbaum_MouseDown(Event event) {
        model.xminh = model.xmin;
        model.xmaxh = model.xmax;
        model.rminh = model.rmin;
        model.rmaxh = model.rmax;
        model.xdown = event.x;
        model.ydown = event.y;
    }*/

  /*  void Feigenbaum_MouseUp(Event event) {
        model.xup = event.x;
        model.yup = event.y;
        if (model.xup == model.xdown) return;
        if (model.yup == model.ydown) return;
        makenewr();
    }*/



    void checkbox1_Action(Event event) {
        //
    }



  /*  void Feigenbaum_MouseDrag(Event event) {
        model.xup = event.x;
        model.yup = event.y;
        if (model.xup == model.xdown) return;
        if (model.yup == model.ydown) return;
        model.generateCoordinate();
        repaint();
    }*/

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command){
            case "Start":
                thePressedKey = "n";
                model.myGo = true;
                model.initCoord(gui.getTextFieldList());
                start();
              //  model.update();
                break;
            case "Clear":
                thePressedKey = "c";
                model.myGo = true;
                start();
               // model.update();
                break;
            case "More":
                thePressedKey = "p";
                model.myGo = true;
                start();
              //  model.update();
                break;
            default:
                break;

        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        model.xup = e.getX();
        model.yup = e.getY();
        if (model.xup == model.xdown) return;
        if (model.yup == model.ydown) return;
        makenewr();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        model.xminh = model.xmin;
        model.xmaxh = model.xmax;
        model.rminh = model.rmin;
        model.rmaxh = model.rmax;
        model.xdown = e.getX();
        model.ydown = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        model.xup = e.getX();
        model.yup = e.getY();
        if (model.xup == model.xdown) return;
        if (model.yup == model.ydown) return;
        model.generateCoordinate(gui.getTextFieldList());
        model.update();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
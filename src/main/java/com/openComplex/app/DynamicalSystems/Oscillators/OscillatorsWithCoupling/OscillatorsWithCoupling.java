package com.openComplex.app.DynamicalSystems.Oscillators.OscillatorsWithCoupling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by strange on 07/10/15.
 */
public class OscillatorsWithCoupling implements ActionListener, ItemListener {


        private int step;
        private OscillatorsWithCouplingModel model;
        private OscillatorsWithCouplingView gui;

        public OscillatorsWithCoupling() {
            gui = new OscillatorsWithCouplingView();
            model = new OscillatorsWithCouplingModel();
            gui.addListener(this, this);
            gui.addPanel(model);
        }






      /*  public void run()
        {
            try{Thread.sleep(100);}catch(InterruptedException e){} //sleep
            ghead.drawString("Drag and drop left mass ...",W/2-100,28);
            startwerte();

            while(Thread.currentThread() == animator)
            {
                if(cb_stop.getState()) //stop/go
                {try{Thread.sleep(10);}catch(InterruptedException e){}continue;}

                runge_step_x1(); //Runge-Kutta-calculation
                runge_step_x2(); //Runge-Kutta-calculation
                x1 = x1 + (k1[0]+2*k1[1]+2*k1[2]+k1[3])/6; //new x1
                x1p = x1p + (l1[0]+2*l1[1]+2*l1[2]+l1[3])/6; //new x1p
                x2 = x2 + (k3[0]+2*k3[1]+2*k3[2]+k3[3])/6; //new x2
                x2p = x2p + (l3[0]+2*l3[1]+2*l3[2]+l3[3])/6; //new x2p

                if(step % 4 == 0) //paint frame not so often
                {
                    try{Thread.sleep(5);}catch(InterruptedException e){} //wait
                    pixels(); //calculate pixelcoordinates
                    repaint(); //paint new frame
                }
                step++; //next step
            }//while currentThread()==animator
        }//run()





        public void actionPerformed (ActionEvent evt) //buttons
        {
            if(evt.getActionCommand() == but_new.getActionCommand())
            {
                stop();
                startwerte(); //set initial values
                pixels(); //calculate pixelcoords
                update(g); //paint initial state
                start();
            }

            if(evt.getActionCommand() == but_D_plus.getActionCommand())
            {
                if(startwert[2] < 4)
                { startwert[2] = 2*startwert[2]; D2 = startwert[2];
                    if(cb_stop.getState()) { update(g); }
                }
            }
            if(evt.getActionCommand() == but_D_min.getActionCommand())
            {
                startwert[2] = startwert[2]/2; D2 = startwert[2];
                if(cb_stop.getState()) { update(g); }
            }
            if(evt.getActionCommand() == but_reib_plus.getActionCommand())
            {
                startwert_reib++;
                reib = startwert_reib;
                if(cb_stop.getState()) { update(g); }
            }
            if(evt.getActionCommand() == but_reib_min.getActionCommand())
            {
                if(reib > 0)
                { startwert_reib--; reib = startwert_reib;
                    if(cb_stop.getState()) { update(g); }
                }
            }
        }//actionPerformed(evt)

        public void itemStateChanged(ItemEvent evt) //Choice object
        {
            if(evt.getItem() == "Free")
            {
                stop();
                Choice[0] = true; //Free
                Choice[1] = false; //Together
                Choice[2] = false; //Against
                startwert[0] = 0.15; //x1
                startwert[1] = 2./3; //x2
                startwerte();
                pixels();
                update(g);
                start();
            }
            if(evt.getItem() == "Together")
            {
                stop();
                Choice[0] = false;
                Choice[1] = true;
                Choice[2] = false;
                startwert[0] = 1./3-0.2; //x1
                startwert[1] = 2./3-0.2; //x2
                startwerte();
                pixels();
                update(g);
                start();
            }
            if(evt.getItem() == "Against")
            {
                stop();
                Choice[0] = false;
                Choice[1] = false;
                Choice[2] = true;
                startwert[0] = 1./3-0.1; //x1
                startwert[1] = 2./3+0.1; //x2
                startwerte();
                pixels();
                update(g);
                start();
            }
        } //itemStateChanged(evt)

*/
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }

}

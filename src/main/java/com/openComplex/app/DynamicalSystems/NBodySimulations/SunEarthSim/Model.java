package com.openComplex.app.DynamicalSystems.NBodySimulations.SunEarthSim;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 16/10/15.
 */
public class Model extends JPanel {
    private static final int Lx = 450, Ly = 340; //graphic window
    private int pxE, pyE, pxS, pyS, spx, spy; //pixelcoordinates
    private double xE, xpE, yE, ypE; //coordinates and velocities
    private double xS, xpS, yS, ypS; //coordinates and velocities
    private double coordnew, vnew; //new coords
    private double helpcoord[] = new double[4]; //help
    private double helpv[] = new double[4]; //help
    private double M = 1.98E30; //sun mass
    private static final double m = 5.977E24; //earth mass
    private static final double G = 6.673E-11; //gravity const.
    private double k1, k2, k3, k4, l1, l2, l3, l4; //Runge-Kutta
    private double dt; //Runge-Kutta-timestep
    private double startwert[] = new double[4]; //save initial values
    private int step;

    public Model(){
        startwert[0] = 6000.0; //dt
        startwert[1] = M; //M
        startwert[2] = Math.sqrt(G*M/1.496E11); //ypE
        startwert[3] = -10.0; //ypS
    }

    public void update(){
        runge_step(M,xE,xS,xpE);
        helpcoord[0] = coordnew;
        helpv[0] = vnew;
        runge_step(M,yE,yS,ypE);
        helpcoord[1] = coordnew;
        helpv[1] = vnew;
        runge_step(m,xS,xE,xpS);
        helpcoord[2] = coordnew;
        helpv[2] = vnew;
        runge_step(m,yS,yE,ypS);
        helpcoord[3] = coordnew;
        helpv[3] = vnew;

        xE = helpcoord[0];
        xpE = helpv[0];
        yE = helpcoord[1];
        ypE = helpv[1];

        xS = helpcoord[2];
        xpS = helpv[2];
        yS = helpcoord[3];
        ypS = helpv[3];

        pixels();
        repaint();
    }

    public void setStep(int step){
        this.step = step;
    }

    public void startwerte()  //method for setting initial values
    {
        dt = startwert[0];
        M = startwert[1];
        xE = 1.496E11;
        yE = 0.0;
        xS = 0.0;
        yS = 0.0;
        xpE = 0.0;
        ypE = startwert[2];
        xpS = 0.0;
        ypS = startwert[3];
        step = 0;
    }//startwerte()

    public void pixels()
    {
        pxE = (int)Math.round((double)(Lx/2) + 1.E-9*xE);
        pyE = (int)Math.round((double)(Ly/2) - 1.E-9*yE);
        pxS = (int)Math.round((double)(Lx/2) + 1.E-9*xS);
        pyS = (int)Math.round((double)(Ly/2) - 1.E-9*yS);
        spx = (int)Math.round((double)(Lx/2) + 1.E-9*Schwerpunkt()[0]);
        spy = (int)Math.round((double)(Ly/2) - 1.E-9*Schwerpunkt()[1]);
    }//pixels()

    public double energy()
    {
        return 0.5*m*vE()*vE() + 0.5*M*vS()*vS() - G*m*M/r();
    }//energy()

    public double r()  //distance between sun and earth
    {
        return Math.sqrt((xE-xS)*(xE-xS)+(yE-yS)*(yE-yS));
    }//r()

    public double[] Schwerpunkt()  //balance point
    {
        double R[] = new double[2];
        R[0] = (M*xS+m*xE)/(M+m);
        R[1] = (M*yS+m*yE)/(M+m);
        return R;
    }//r()

    public double vE()
    {
        return Math.sqrt(xpE*xpE+ypE*ypE);
    }//vE()

    public double vS()
    {
        return Math.sqrt(xpS*xpS+ypS*ypS);
    }//vS()

    public double force(double mass, double coord1, double coord2)
    {
        return -G*mass/Math.pow(r(),3)*(coord1-coord2);
    }//force(mass, coord1, coord2)

    public void runge_step(double mass, double coord1, double coord2, double v)
    {
        k1 = dt*v;
        l1 = dt*force(mass, coord1, coord2);
        k2 = dt*(v+l1/2);
        l2 = dt*force(mass, coord1+k1/2, coord2);
        k3 = dt*(v+l2/2);
        l3 = dt*force(mass, coord1+k2/2, coord2);
        k4 = dt*(v+l3);
        l4 = dt*force(mass, coord1+k3, coord2);
        coordnew = coord1 + (k1+2*k2+2*k3+k4)/6;
        vnew     = v + (l1+2*l2+2*l3+l4)/6;
    }//runge_step(mass, coord1, coord2, v)


    public void vyEarthPlus(){
        startwert[2] = startwert[2]/0.9;
        startwerte(); //set initial values
        pixels(); //calculate pixelcoords
        repaint();
    }

    public void vyEarthMinus(){
        startwert[2] = startwert[2]*0.9;
        startwerte(); //set initial values
        pixels(); //calculate pixelcoords
        repaint();
    }

    public void values1(){
        startwert[0] = 6000.0; //dt
        startwert[1] = 1.98E30; //M
        startwert[2] = Math.sqrt(G*startwert[1]/1.496E11); //ypE
        startwert[3] = - 10.0; //ypS
        startwerte(); //set initial values
        pixels();
        repaint();
    }

    public void values2(){
        startwert[0] = 500000.0; //dt
        startwert[1] = 30*m; //M
        startwert[2] = Math.sqrt(G*startwert[1]/1.496E11); //ypE
        startwert[3] = - 10.0; //ypS
        startwerte(); //set initial values
        pixels();
        repaint();
    }
    public void values3(){
        startwert[0] = 2000000.0; //dt
        startwert[1] = 4*m; //M
        startwert[2] = Math.sqrt(G*startwert[1]/1.496E11); //ypE
        startwert[3] = - 26.0; //ypS
        startwerte(); //set initial values
        pixels();
        repaint();
    }
    public void values4(){
        startwert[0] = 2000000.0; //dt
        startwert[1] = m; //M
        startwert[2] = Math.sqrt(G*startwert[1]/1.496E11); //ypE
        startwert[3] = -55.0; //ypS
        startwerte(); //set initial values
        pixels();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setFont(new Font("Verdana",Font.BOLD,10));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Lx, Ly+50);

       //        gr.drawImage(blackstar, 0, 0, this);

        g.setColor(Color.red);
        g.drawLine(0,Ly/2,Lx,Ly/2);
        g.drawLine(Lx/2,0,Lx/2,Ly);
        g.setColor(Color.BLUE);
        g.drawString("Step " + step, 20, 30);
        g.setColor(Color.BLUE);
        g.drawString("Energy:",20,Ly+20);
        g.drawString("" +Math.round(energy()/1.E28) + "E28",20,Ly+40);
        g.drawString("M / m:",110,Ly+20);
        g.drawString("" +(double)Math.round(M/m),110,Ly+40);
        g.drawString("Distance:",185,Ly+20);
        g.drawString("" +(double)Math.round(1.E-8*r())*1.E8 + " m",185,Ly+40);
        g.drawString("v_Earth:",275,Ly+20);
        g.drawString("" +(double)Math.round(10*vE())/10 + " m/s",275,Ly+40);
        g.drawString("Time:",370,Ly+20);
        g.drawString("" + Math.round(step * dt / 3600. / 24.) + " days", 370, Ly + 40);
        g.setColor(Color.yellow);
        g.fillOval(pxS-11,pyS-11,23,23);
        g.setColor(Color.blue);
        g.fillOval(pxE-7,pyE-7,15,15);
        g.setColor(Color.green);
        g.fillOval(spx-1,spy-1,3,3);
    } //paintFrame(gr)

}

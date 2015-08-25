package com.openComplex.app.CellularAutomat.GameOfLife.Model;
import javax.swing.*;
import java.awt.*;

/**
 * Created by robert on 12/14/14.
 */
public class hexacell extends JComponent{
    public hexacell hexacell_l; //always null
    public hexacell hexacell_r; //always null
    public hexacell hexacell_t;
    public hexacell hexacell_b;
    public hexacell hexacell_lt;
    public hexacell hexacell_lb;
    public hexacell hexacell_rt;
    public hexacell hexacell_rb;

    static public Color color=Color.DARK_GRAY;
    public boolean status;

    public void get_to_life(){
        status = true;
    }

    public void changecolor(Color color){
        hexacell.color =color;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
    }

    //equivalent to setHeigth(10)
    public static int h = 10;
    private static int r = h/2;
    private static int s = (int) (h / 1.73205);
    private static int t = (int) (r / 1.73205);
    public static int w = s + 2 * t;

    public static int width(){
        return w;
    }

    private static int BORDERS=50;	//default number of pixels for the border.

    public static void setBorders(int b){
        BORDERS=b;
    }

    public static Polygon hex (int x0, int y0) {

        int y = y0 + BORDERS;
        int x = x0 + BORDERS; // + (XYVertex ? t : 0); //Fix added for XYVertex = true.
        // NO! Done below in cx= section

        int[] cx,cy;

        //refer to XYVertex as false (left yellow dot)
        //http://www.quarkphysics.ca/scripsi/hexgrid/
        //http://www.quarkphysics.ca/scripsi/wp-content/uploads/2012/12/Image1c.gif
        cx = new int[] {x+t,x+s+t,x+s+t+t,x+s+t,x+t,x};	//this is for the whole hexagon to be below and to the right of this point

        cy = new int[] {y,y,y+r,y+r+r,y+r+r,y+r};
        return new Polygon(cx,cy,6);
 
		/*
		   x=200;
		   poly = new Polygon();
		   poly.addPoint(x,y);
		   poly.addPoint(x+s,y);
		   poly.addPoint(x+s+t,y+r);
		   poly.addPoint(x+s,y+r+r);
		   poly.addPoint(x,y+r+r);
		   poly.addPoint(x-t,y+r);
		 */
    }

    /********************************************************************
     Name: drawHex()
     Parameters: (i,j) : the x,y coordinates of the inital point of the hexagon
     g2: the Graphics2D object to draw on.
     Returns: void
     Calls: hex() 
     Purpose: This function draws a hexagon based on the initial point (x,y).
     The hexagon is drawn in the colour specified in hexgame.COLOURELL.
     *********************************************************************/
    public static void drawHex(int i, int j, Graphics2D g2) {
        int x = i * (s+t);
        int y = j * h + (i%2) * h/2;
        Polygon poly = hex(x,y);
        g2.setColor(color);
        //g2.fillPolygon(hexmech.hex(x,y));
        g2.fillPolygon(poly);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(poly);
    }

    /***************************************************************************
     * Name: fillHex()
     * Parameters: (i,j) : the x,y coordinates of the initial point of the hexagon
     n   : an integer number to indicate a letter to draw in the hex
     g2  : the graphics context to draw on
     * Return: void
     * Called from:
     * Calls: hex()
     *Purpose: This draws a filled in polygon based on the coordinates of the hexagon.
     The colour depends on whether n is negative or positive.
     The colour is set by hexgame.COLOURONE and hexgame.COLOURTWO.
     The value of n is converted to letter and drawn in the hexagon.
     *****************************************************************************/
    public static void fillHex(int i, int j, Color color, Graphics2D g2) {
        char c='o';
        int x = i * (s+t);
        int y = j * h + (i%2) * h/2;
        g2.setColor(color);
        g2.fillPolygon(hex(x,y));
        g2.setColor(color);
        //c = (char)(-n);
        //g2.drawString(""+c, x+r+50, y+r+50+4); //FIXME: handle XYVertex
        //g2.drawString(x+","+y, x+r+50, y+r+50+4);
    }
}

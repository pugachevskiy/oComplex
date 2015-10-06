package com.openComplex.app.DynamicalSystems.Physics.DLA;

import javax.swing.*;
import java.awt.*;

/**
 * Created by strange on 06/10/15.
 */
public class DLAModel extends JPanel {
    private static final int D = 256; //grid dimension, graphic window dimension
    private boolean grid[][] = new boolean[D + 1][D + 1]; //particle position on grid
    private int CellNum, CellSize; //resize the grid for painting

    public void reset()  //method for resetting the grid
    {

        CellNum = 32; //start with a graphical field of 32x32 boxes
        CellSize = D / CellNum;
        for (int i = 0; i <= D; i++) {
            for (int j = 0; j <= D; j++) {
                grid[i][j] = false;
            }
        } //delete all cells
        grid[D / 2][D / 2] = true; //set mid-cell to true
    }//reset()

    public int evaluateM()  //method for evaluating cluster mass
    {
        int M = 0;

        for (int i = 0; i <= D; i++) {
            for (int j = 0; j <= D; j++) {
                if (grid[i][j]) M++;
            }
        }
        return M;
    }//evaluateM()

    public int evaluateR()  //method for evaluating cluster radius
    {
        int R = 0;
        int R_old = 0;

        for (int i = 0; i <= D; i++) {
            for (int j = 0; j <= D; j++) {
                if (grid[i][j]) {
                    R = (int) Math.round(Math.sqrt((i - D / 2) * (i - D / 2) + (j - D / 2) * (j - D / 2)));
                    if (R > R_old) {
                        R_old = R;
                    }
                }
            }
        }
        return R_old;
    }//evaluateR()

    public double evaluateD()  //method for evaluating fractal dimension
    {
        double D = 0;
        D = Math.round(1000. * Math.log(evaluateM()) / Math.log(evaluateR())) / 1000.;
        return D;
    }//evaluateD()

    /* method for doing a 2-dim random walk with starting coords i, j
       and aggregation */
    public void RandomWalk(int i, int j) {
        for (int n = 0; n < 32 * CellNum; n++) //duration of the random walk
        {
            if (i < 1 || i > D - 1 || j < 1 || j > D - 1) {
                break;
            } else {
                if (grid[i][j - 1]) {
                    grid[i][j] = true;
                    break;
                } //aggregation
                if (grid[i + 1][j]) {
                    grid[i][j] = true;
                    break;
                } //aggregation
                if (grid[i][j + 1]) {
                    grid[i][j] = true;
                    break;
                } //aggregation
                if (grid[i - 1][j]) {
                    grid[i][j] = true;
                    break;
                } //aggregation

                //random walk
                if (Math.random() <= 0.5) {
                    if (Math.random() <= 0.5) {
                        i++;
                    } else i--;
                } else {
                    if (Math.random() <= 0.5) {
                        j++;
                    } else j--;
                }
            }
        }
    }//RandomWalk(i,j)

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setFont(new Font("Verdana", Font.BOLD, 10));
        g.setColor(Color.white);
        g.fillRect(0, 0, D, D);
        g.setColor(Color.black);
        for (int i = 0; i < D; i++) {
            for (int j = 0; j < D; j++) {
                if (grid[i][j])
                    g.fillOval((i - D / 2 + CellNum / 2) * CellSize,
                            (j - D / 2 + CellNum / 2) * CellSize,
                            CellSize, CellSize);
            }
        } //paint a black particle
        g.setColor(Color.blue); //printing out the values
        g.drawString("Time", D / 2 - 115, 12);
        // g.drawString("" + step, D/2-115, 24);
        g.drawString("Radius", D / 2 - 35, 12);
        g.drawString("R = " + evaluateR(), D / 2 - 35, 24);
        g.drawString("Mass", D / 2 + 45, 12);
        g.drawString("M = " + evaluateM(), D / 2 + 45, 24);
        g.drawString("fractal dimension", D / 2 - 115, D - 20);
        g.drawString("D = " + evaluateD(), D / 2 - 115, D - 8);
    }//paintFrame()

    public void update() {
        double z = Math.random() * 2 * Math.PI; //choose random angle
        //perform 2-dim random walk with random start position on a circle
        RandomWalk((int) Math.round(D / 2 + (CellNum / 2 - 2) * Math.cos(z)),
                (int) Math.round(D / 2 + (CellNum / 2 - 2) * Math.sin(z)));

        if (CellNum < D)
        //for rescaling the graphical grid
        {
            for (int i = 0; i < D; i++) {
                for (int j = 0; j < D; j++) {
                    if (grid[i][j]) {
                        if (Math.round(Math.sqrt((i - D / 2) * (i - D / 2) + (j - D / 2) * (j - D / 2)))
                                == CellNum / 2 - 2) {
                            CellNum = CellNum * 2;
                            CellSize = D / CellNum;
                        } //rescale
                    }
                }// for j
            }//for i
        }//if
        repaint();
    }
}

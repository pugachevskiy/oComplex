package testPrjs;

import com.openComplex.app.DynamicalSystems.LorenzAttractor.StdDraw;

import java.awt.Font;
// import java.awt.Font;
import java.util.ArrayList;

public class Trajectory {

    // return number of iterations to check if c = a + ib is in Mandelbrot set
    public static boolean mand(Complex z0, int d) {
        StdDraw.setPenColor(StdDraw.RED);
        Complex z = z0;
        StdDraw.filledCircle(z.re(), z.im(), .025);
        StdDraw.setPenColor(StdDraw.BLUE);
        Complex zold = z;
        for (int t = 0; t < d; t++) {
            if (z.abs() >= 2.0) return false;
            zold = z;
            z = z.times(z).plus(z0);
            StdDraw.filledCircle(z.re(), z.im(), .01);
            StdDraw.line(zold.re(), zold.im(), z.re(), z.im());
        }
        return true;
    }

    public static void main(String[] args) {
        ArrayList<Complex> black = new ArrayList<Complex>();
        ArrayList<Complex> white = new ArrayList<Complex>();
        StdDraw.setXscale(-2, 2);
        StdDraw.setYscale(-2, 2);
        StdDraw.setFont(new Font("Arial", Font.PLAIN, 10));
        StdDraw.clear(StdDraw.LIGHT_GRAY);
        StdDraw.circle(0, 0, 2);
 
        while (true) {
            if (StdDraw.mousePressed()) {
                StdDraw.clear(StdDraw.LIGHT_GRAY);

                // draw the points already computed to be in/outside the set
                StdDraw.setPenColor(StdDraw.BLACK);
                for (Complex z : black) {
                    StdDraw.filledCircle(z.re(), z.im(), .01);
                }
                StdDraw.setPenColor(StdDraw.WHITE);
                for (Complex z : white) {
                    StdDraw.filledCircle(z.re(), z.im(), .01);
                }

                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Complex z = new Complex(x, y);
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.circle(0, 0, 2);
                StdDraw.text(0, -2.1, z + "");
                if (mand(z, 100)) black.add(z);
                else              white.add(z);
            }
            StdDraw.show(50);
        }
    }
}
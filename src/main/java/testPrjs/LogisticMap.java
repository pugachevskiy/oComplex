package testPrjs;

import java.awt.Graphics2D;

/******************************************************************************
 *  Compilation:  javac LogisticMap.java
 *  Execution:    java LogisticMap
 *
 *  Plot the bifurcation diagram of the logistic map. The logistic
 *  is: x[n+1] = 4 r x[n](1 - x[n]). For each value of r, discard
 *  the first 1000 iterates x[n], then plot the next 100.
 *
 ******************************************************************************/

public class LogisticMap {
    private static Graphics2D g;


	static double logistic(double y, double r) {
        return 4.0 * r * y * (1.0 - y);
    }


    // plot the logistic map using standard draw
    public static void main(String[] args) {
        int N = 2800;
        StdDraw.setXscale(0.7, 1.0);
        StdDraw.setYscale(0.2, 1.0);

      for (double r = 0.7; r <= 1.0; r += 0.3/N) {

         // choose random initial value
         double y = Math.random();

         // ignore first 1000 iterates
         for (int i = 0; i < 10000; i++)
            y = logistic(y, r);

         // plot next 1000 iterates
         for (int i = 0; i < 1; i++) {
            y = logistic(y, r);
            StdDraw.point(r, y);           
         }
         StdDraw.show(1);
         StdDraw.setPenColor(StdDraw.BLUE);
         StdDraw.setPenRadius(0.002);
         if (StdDraw.mousePressed()){
        	 StdDraw.setPenColor(StdDraw.DARK_GRAY);
         }
      }
   }
   
}
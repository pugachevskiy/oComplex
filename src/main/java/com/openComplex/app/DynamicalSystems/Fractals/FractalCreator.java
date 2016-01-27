package com.openComplex.app.DynamicalSystems.Fractals;

import com.openComplex.app.DynamicalSystems.Fractals.Model.*;


/**
 * Created by laptop on 29.06.2015.
 */
public class FractalCreator implements FractalsCreator{
    private static final String SERPINSKICARPET = "Sierpinski carpet";
    private static final String MINKOVSKICURVE = "Minkowski curve";
    private static final String SERPINSKITRIANGE = "Sierpinski triangle";
    private static final String KOCHCURVE = "Koch Curve";
    private static final String CIRCLECURVE = "Circle Curve";
    private static final String CIRCLESQUARE = "Circle Square";
    private static final String FRACTALSTREE = "Tree";

   // private static final String MENGERSPONGE = "Menger sponge";

    @Override
    public Fractal getFractal(String name, int step){
        switch (name){
            case MINKOVSKICURVE: return new MinkowskiCurve(step);
            case KOCHCURVE:return new KochCurve(step);
            case SERPINSKICARPET:return new SierpinskiCarpet(step);
            case SERPINSKITRIANGE: return new SierpinskiTriangle(step);
            case CIRCLECURVE: return new CircleCurve(step);
            case CIRCLESQUARE: return new CircleCurveSquare(step);
            case FRACTALSTREE: return new FractalTree(step);

           // case MENGERSPONGE: return new MengerSponge(caps, step);
        }
        return null;
    }


}

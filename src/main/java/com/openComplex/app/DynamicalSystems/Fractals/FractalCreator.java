package com.openComplex.app.DynamicalSystems.Fractals;


import com.jogamp.opengl.GLCapabilities;
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
        GLCapabilities caps = new GLCapabilities(null);
        switch (name){
            case MINKOVSKICURVE: return new MinkowskiCurve(caps, step);
            case KOCHCURVE:return new KochCurve(caps, step);
            case SERPINSKICARPET:return new SierpinskiCarpet(caps, step);
            case SERPINSKITRIANGE: return new SierpinskiTriangle(caps, step);
            case CIRCLECURVE: return new CircleCurve(caps, step);
            case CIRCLESQUARE: return new CircleCurveSquare(caps, step);
            case FRACTALSTREE: return new FractalTree(caps, step);

           // case MENGERSPONGE: return new MengerSponge(caps, step);
        }
        return null;
    }


}

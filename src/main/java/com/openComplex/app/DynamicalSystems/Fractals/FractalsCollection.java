package com.openComplex.app.DynamicalSystems.Fractals;

import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 29/06/15.
 */
public final class FractalsCollection {
    public static final List<String> TITEL = Arrays.asList("Name", "Copy", "Factor", "Dimension", "Discription");

    public static final List<String> KOCH_CURVE = Arrays.asList("Koch Curve", "3", "4", "1.2619", "The Koch curve" +
            " is a mathematical curve and one of the earliest fractal curves to have been discribed." +
            " It is based on the Swedish mathematican Helge von Koch and an example for an everywhere continuous," +
            " but never differentiable curve. It is further the most frequently citated example for a fractal." +
            " A well known variation of the Koch curve ist the Koch snowflake, combined out of three Koch curves arranged" +
            " to a rectangle. ");

    public static final List<String> SIERPINSKI_TRIANGLE = Arrays.asList("Sierpinski triangle", "3", "2", "1.5849",
            "The Sierpinski triangle, also called the Sierpinski gasket or the Sierpinski Sieve " +
                    "is a self-similar subset of a (mostly equilateral) triangle. " +
                    "If the triangle is divided in four triangles congruent to each other and " +
                    "their edges are the midpoints of the start triangle, then the three outer triangles are" +
                    "scaled copies of the fractal. The division of the fractal in scaled copies can be continued" +
                    "recursively in the outer partial triangles.");

    public static final List<String> SIERPINSKI_CARPET = Arrays.asList("Sierpinski carpet", "3", "8", "1.8928",
            "The Sierpinski carpet is a plane fractal described by Waclav Sierpinski in 1916." +
                    " A square is divided into nine equal parts. The part in the middle is removed." +
                    " This can be continued recursive with each of the remaining eight squares." +
                    "\n\n\n\n\n\n");
    public static final List<String> MINKOWSKI_CURVE = Arrays.asList("Minkowski curve", "4", "8", "1.5000", "Also called Minkowski sausage\n\n\n\n\n\n\n\n\n\n\n");
    public static final List<String> MENGER_SPONGE = Arrays.asList("Menger sponge", "3", "20", "2.7268", "And its surface has a fractal dimension of log_3(20), which is the same as that by volume.\n\n\n\n\n\n\n\n\n");
    public static final List<String> Circle_Curve = Arrays.asList("Circle Curve", "1", "2", "1.5000", "Rekursive construction of circles.\n\n\n\n\n\n\n\n\n\n\n");
    public static final List<String> CIRCLE_SQUARE = Arrays.asList("Circle Square", "1", "2", "1.5000", "Rekursive construction of circles arranged as square.\n\n\n\n\n\n\n\n\n\n\n");
    public static final List<String> FRACTALS_TREE = Arrays.asList("Tree", "1", "2", "1.5000", "Rekursive construction of a tree.\n\n\n\n\n\n\n\n\n\n\n");
    public static final List<List<String>> FRACTALS = Arrays.asList(KOCH_CURVE, SIERPINSKI_CARPET, SIERPINSKI_TRIANGLE, MINKOWSKI_CURVE, Circle_Curve, CIRCLE_SQUARE, FRACTALS_TREE);//, MENGER_SPONGE);

}

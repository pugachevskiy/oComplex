package com.openComplex.app.DynamicalSystems.Fractals2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by strange on 29/06/15.
 */
public final class FractalsCollection {
    public static final List<String> TITEL = Arrays.asList("Name", "Copy", "Factor", "Dimension", "Discription");
    public static final List<String> KOCH_CURVE = Arrays.asList("Koch Curve", "3", "4", "1.2619", "Die Koch-Kurve oder" +
            " kochsche Kurve ist ein von dem schwedischen Mathematiker Helge von Koch 1904 vorgestelltes Beispiel" +
            " fuer eine ueberall stetige, aber nirgends differenzierbare Kurve. Es handelt sich bei ihr ferner" +
            " um eines der ersten formal beschriebenen fraktalen Objekte. Die Koch-Kurve ist" +
            " eins der am haeufigsten zitierten Beispiele fuer ein Fraktal und wurde bei der Entdeckung" +
            " als Monsterkurve bezeichnet. Die Koch-Kurve ist auch in Form der kochschen Schneeflocke bekannt," +
            " die durch geeignete Kombination dreier Koch-Kurven entsteht.");
    public static final List<String> SIERPINSKI_TRIANGLE = Arrays.asList("Sierpinski triangle", "3", "2", "1.5849",
            "Das Sierpinski-Dreieck ist ein 1915 von Wacław Sierpiński beschriebenes Fraktal – mitunter auch " +
                    "Sierpinski-Fläche oder -Dichtung genannt, welches eine selbstähnliche Teilmenge eines " +
                    "(meist gleichseitig dargestellten) Dreiecks ist. Teilt man das Dreieck in vier zueinander " +
                    "kongruente und zum Ausgangsdreieck ähnliche Dreiecke, deren Eckpunkte die Seitenmittelpunkte " +
                    "des Ausgangsdreiecks sind, dann sind die Teilmengen des Fraktals in den drei äußeren Dreiecken " +
                    "skalierte Kopien des gesamten Fraktals, während das mittlere Teildreieck nicht zum Fraktal gehört. " +
                    "Diese Aufteilung des Fraktals in skalierte Kopien kann in den äußeren Teildreiecken rekursiv " +
                    "fortgesetzt werden. Die fraktale Dimension des Sierpinski-Dreiecks beträgt");
    public static final List<String> SIERPINSKI_CARPET = Arrays.asList("Sierpinski carpet", "3", "8", "1.8928",
            "Der Sierpinski-Teppich ist ein Fraktal, das auf den polnischen Mathematiker Wacław Sierpiński zurückgeht." +
                    " Aus einem Quadrat wird in der Mitte ein Neuntel der Fläche entfernt." +
                    " Aus den um das Loch verbliebenen acht quadratischen Feldern wird wiederum " +
                    "je ein Neuntel der Fläche entfernt, und so weiter.\n\n\n\n\n\n");
    public static final List<String> MINKOWSKI_CURVE = Arrays.asList("Minkowski curve", "4", "8", "1.5000", "Also called Minkowski sausage\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    public static final List<String> MENGER_SPONGE = Arrays.asList("Menger sponge", "3", "20", "2.7268", "And its surface has a fractal dimension of log_3(20), which is the same as that by volume.\n\n\n\n\n\n\n\n\n\n\n\n\n");
    public static final List<List<String>> FRACTALS = Arrays.asList(KOCH_CURVE, SIERPINSKI_CARPET, SIERPINSKI_TRIANGLE, MINKOWSKI_CURVE, MENGER_SPONGE);
    public static final String[] FRACTALS_NAMES = {KOCH_CURVE.get(0), SIERPINSKI_TRIANGLE.get(0), SIERPINSKI_CARPET.get(0), MINKOWSKI_CURVE.get(0),
                                                MENGER_SPONGE.get(0)};


}

package clustree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Metrics {

    public double absoluteError(ArrayList<double[]> kmedoids, ArrayList<double[]> points) {
        double absoluteError = 0.0;

        for (double[] point: points) {
            ArrayList<Double> distances = new ArrayList<>();
            for (double[] medoid: kmedoids) {
                distances.add(distance(point, medoid));
            }
            absoluteError += Collections.min(distances);
        }
        return absoluteError;
    }

    public static double distance(double[] a, double[] b) {
        double total = 0, diff;
        for (int i = 0; i < a.length; i++) {
            diff = b[i] - a[i];
            total += diff * diff;
        }
        return Math.sqrt(total);
    }
}

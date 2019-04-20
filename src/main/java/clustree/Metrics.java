package clustree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Metrics {

    public static double absoluteError(ArrayList<double[]> kmedoids, ArrayList<double[]> points) {
        double maxDist = 0;
        double absoluteError = 0.0;
        int lessThan1 = 0, greaterThan1 = 0;

        for (double[] point: points) {
            ArrayList<Double> distances = new ArrayList<>();
            for (double[] medoid: kmedoids) {
                distances.add(distance(point, medoid));
            }
            double dist = Collections.min(distances);
            if (dist < 1)
                lessThan1 += 1;
            else
                greaterThan1 += 1;
            absoluteError += dist;

            if (dist > maxDist)
                maxDist = dist;
        }
//
//        System.out.println("Less than 0: " + lessThan1);
//        System.out.println("Greater than 0: " + greaterThan1);
//
//        System.out.println("Max distance: " + maxDist);
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

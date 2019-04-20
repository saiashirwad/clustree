package clustree;

import com.yahoo.labs.samoa.instances.Instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Metrics {

    public static double absoluteError(ArrayList<double[]> kmedoids) {
        double maxDist = 0;
        double absoluteError = 0.0;

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue("d:\\covtype.csv");
        stream.prepareForUse();

        while (stream.hasMoreInstances()) {
            double[] point = stream.nextInstance().instance.toDoubleArray();
            ArrayList<Double> distances = new ArrayList<>();
            for (double[] medoid: kmedoids) {
                distances.add(distance(point, medoid));
            }
            double dist = Collections.min(distances);
            absoluteError += dist;
        }

//        for (double[] point: points) {
//            ArrayList<Double> distances = new ArrayList<>();
//            for (double[] medoid: kmedoids) {
//                distances.add(distance(point, medoid));
//            }
//            double dist = Collections.min(distances);
//            absoluteError += dist;
//        }
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

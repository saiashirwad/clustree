package clustree;

import com.yahoo.labs.samoa.instances.Instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Metrics {

    public static double absoluteError(ArrayList<double[]> kmedoids, String dataPath) {
        double maxDist = 0;
        double absoluteError = 0.0;

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue(dataPath);
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

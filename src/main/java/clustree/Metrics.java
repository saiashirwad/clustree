package clustree;

import java.util.ArrayList;
import java.util.HashMap;

public class Metrics {

    public double absoluteError(ArrayList<double[]> kmedoids, ArrayList<double[]> points) {
//        HashMap<Integer, ArrayList<double[]>> map = new HashMap<>();

        double absoluteError = 0.0;

        for (double[] point: points) {
            double minDist = 0.0;
//            int minIndex = 0;
            boolean flag = true;
            for (double[] medoid: kmedoids) {
                double dist = distance(point, medoid);
                if (flag) {
                    minDist = dist;
                    flag = false;
                }
                if (dist < minDist) {
                    minDist = dist;
//                    minIndex = kmedoids.indexOf(medoid);
                }
            }
//            map.putIfAbsent(minIndex, new ArrayList<double[]>());
//            map.get(minIndex).add(point);

            absoluteError += minDist;
            flag = true;

        }

        return absoluteError;
    }

    private double distance(double[] a, double[] b) {
        double total = 0, diff;
        for (int i = 0; i < a.length; i++) {
            diff = b[i] - a[i];
            total += diff * diff;
        }
        return Math.abs(Math.sqrt(total));
    }
}

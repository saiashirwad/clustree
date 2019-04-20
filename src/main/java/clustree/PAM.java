package clustree;

import com.clust4j.algo.KMedoids;
import com.clust4j.algo.KMedoidsParameters;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.jfree.data.io.CSV;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;

public class PAM {
    public static void main(String[] args) {
        int k = 50;
        int nPoints = 100000;

        ArrayList<double[]> points_ = CSVReader.read("d:\\covertype.csv");
        ArrayList<double[]> points = new ArrayList<>(points_.subList(0, nPoints));
        double[][] data = new double[nPoints][points.get(0).length];

        for (int i = 0; i < nPoints; i++) {
            double[] point = points.get(i);
            for (int j = 0; j < point.length; j++) {
                data[i][j] = point[j];
            }
        }

        Array2DRowRealMatrix mat = new Array2DRowRealMatrix(data);
        System.out.println((double) Runtime.getRuntime().freeMemory() / (1000000000.0));
        System.out.println((double) Runtime.getRuntime().maxMemory() / (1000000000.0));
        KMedoids km = new KMedoidsParameters(k).fitNewModel(mat);

        ArrayList<double[]> medoids = km.getCentroids();

        double absoluteError = new Metrics().absoluteError(medoids, points);
        System.out.println(absoluteError);

    }
}

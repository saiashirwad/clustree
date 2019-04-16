package clustree;

import com.clust4j.algo.KMedoids;
import com.clust4j.algo.KMedoidsParameters;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.jfree.data.io.CSV;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;

public class PAM {
    public static void main(String[] args) {
        int k = 10;
        ArrayList<double[]> points = CSVReader.read("d:\\covertype.csv");

        double[][] data = new double[points.size()][];

        for (int i = 0; i < points.size(); i++) {
            data[i] = points.get(i);
        }

        points = null;

        Array2DRowRealMatrix mat = new Array2DRowRealMatrix(data);
        System.out.println((double) Runtime.getRuntime().maxMemory() / (1000000000.0));
        KMedoids km = new KMedoidsParameters(k).fitNewModel(mat);

        ArrayList<double[]> medoids = km.getCentroids();

        double absoluteError = new Metrics().absoluteError(medoids, points);
        System.out.println(absoluteError);

    }
}

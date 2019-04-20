package clustree;

import com.clust4j.algo.KMedoids;
import com.clust4j.algo.KMedoidsParameters;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;

import java.util.ArrayList;
import java.util.Queue;

public class PAMThread implements Runnable {
    private Queue<ClusKernel> points;
    Entry entry;
    int id = 0;

    public PAMThread(Queue<ClusKernel> points, Entry entry) {
        this.entry = entry;
        this.points = points;
    }

    public void run() {
        this.entry.isUsed = true;
        try {
//            int k = (int) (0.4 * (double)this.points.size());

            int k = ThreadConfig.getK();

            double[][] data = new double[points.size()][points.peek().LS.length];

            int i = 0;
            for (ClusKernel ck : points) {
                for (int j = 0; j < ck.LS.length; j++) {
                    data[i][j] = ck.LS[j];
                }
                i++;
            }

            Array2DRowRealMatrix mat = new Array2DRowRealMatrix(data);
            KMedoids km = new KMedoidsParameters(k).fitNewModel(mat);

            ArrayList<double[]> centroids = km.getCentroids();

            this.entry.setKmedoids(centroids);
        }
        catch (Exception e) {
        }

    }
}

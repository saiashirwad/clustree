package clustree;

import com.clust4j.algo.KMedoids;
import com.clust4j.algo.KMedoidsParameters;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.kramerlab.bmad.general.Tuple;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Queue;

public class PAMThread_ implements Runnable {
    private Queue<Tuple<ClusKernel, Instant>> points_;
    Entry entry;
    private Instant instant;
    private ArrayList<double[]> points;
    private double maxAge;
    private boolean initialized = false;

    public PAMThread_(Queue<Tuple<ClusKernel, Instant>> points_, Entry entry, Instant instant) {
        this.entry = entry;
        this.points_ = points_;
        this.instant = instant;
        this.points = new ArrayList<>();
        this.maxAge = ThreadConfig.getMaxAge();
        this.initialized = true;
    }


    public void run() {
        // ugly but works


        try {
            int nDims = this.points_.peek()._1.LS.length;
            double kPercent = ThreadConfig.getKpercent();

            for (Tuple<ClusKernel, Instant> point_: this.points_) {
//                if (Duration.between(this.instant, point_._2) <= maxAge) {
//                    this.points.add(point_._1.LS);
//                }
                if (getDuration(this.instant, point_._2) <= maxAge) {
                    this.points.add(point_._1.LS);
                }
            }

//            int k = (int) (kPercent * (double)this.points.size());

            int k = ThreadConfig.getK();
            double[][] data = new double[this.points.size()][nDims];

            for (int i = 0; i < this.points.size(); i++) {
                for (int j = 0; j < nDims; j++) {
                    data[i][j] = this.points.get(i)[j];
                }
            }

            Array2DRowRealMatrix mat = new Array2DRowRealMatrix(data);
            KMedoids km = new KMedoidsParameters(k).fitNewModel(mat);

            ArrayList<double[]> centroids = km.getCentroids();
            this.entry.setKmedoids(centroids);

        }
        catch (Exception e) {
        }
    }

    public double getDuration(Instant one, Instant two) {
        return (Duration.between(one, two).getSeconds() * Math.pow(10, 9)) + Duration.between(one, two).getNano();
    }
}
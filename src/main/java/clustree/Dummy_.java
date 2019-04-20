package clustree;

import com.clust4j.algo.KMedoids;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.core.InstanceExample;
import com.yahoo.labs.samoa.instances.Instance;
import org.jfree.data.io.CSV;
import org.kramerlab.bmad.general.Tuple;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dummy_ {

    public static Queue<Instance> q = new LinkedList<Instance>();
    public static Clusterer learner = new ClusTree();
    public static int i = 500000;


    public static void main(String[] args) throws IOException {


        ArrayList<double[]> points_ = CSVReader.read("d:\\backup.csv");
        ArrayList<double[]> points = new ArrayList<>(points_.subList(0, i));


        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue("d:\\backup.csv");
        stream.prepareForUse();

        double[] point = points.get(0);

        learner.resetLearning();

        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();

        Instant begin = Instant.now();

        while (stream.hasMoreInstances() && i > 0) {
            Instance inst = stream.nextInstance().instance;
            int n = inst.numAttributes();
            learner.trainOnInstance(inst);
            i--;
        }

        try {
            Thread.sleep(10000);
        }
        catch (Exception e) {}

        ArrayList<Node> leafNodes = learner.collectLeafNodes(learner.getRoot());

        System.out.println("DONE!");

        System.out.println(learner.collectResidualPoints().size());

        int[] ks = new int[]{5,15, 25, 35, 45, 50};

        FileWriter fileWriter = new FileWriter("./results.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int k: ks) {
            KMedoids km = learner.getKMedoids(k);

            ArrayList<double[]> kmedoids = km.getCentroids();
            for (double[] medoid: kmedoids) {
                for (double i: medoid) {
                    System.out.print(i);
                }
                System.out.println("\n\n");
            }
            System.out.println("");

            double absoluteError = Metrics.absoluteError(kmedoids, points);
            double silhouette = km.silhouetteScore();

            System.out.println("k = " + k + "\nabsolute error = " + absoluteError + "\nsilhouette coeff = " + silhouette + "\n");
            printWriter.println("k = " + k + "\nabsolute error = " + absoluteError + "\nsilhouette coeff = " + silhouette + "\n");

        }

        printWriter.close();

    }

}

enum StreamHandler {
    UNIT_HANDLER,
    AGGREGATE_HANDLER
}

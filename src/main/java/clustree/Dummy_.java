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


        ArrayList<double[]> points = CSVReader.read("d:\\covertype.csv");

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue("d:\\covertype.csv");
        stream.prepareForUse();

        learner.resetLearning();

        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();

        Instant begin = Instant.now();

        while (stream.hasMoreInstances() && i > 0) {
            Instance inst = stream.nextInstance().instance;
            learner.trainOnInstance(inst);
            i--;
        }

        try {
            Thread.sleep(1000);
        }
        catch (Exception e) {}

        System.out.println("DONE!");

        int[] ks = new int[]{5,15, 25, 35, 45, 50, 100};

        FileWriter fileWriter = new FileWriter("./results.txt");
        PrintWriter printWriter = new PrintWriter(fileWriter);

        for (int k: ks) {
            KMedoids km = learner.getKMedoids(k);

            ArrayList<double[]> kmedoids = km.getCentroids();
            double absoluteError = Metrics.absoluteError(kmedoids, points);
            double silhouette = km.silhouetteScore();

            System.out.println("k = " + k + "\nabsolute error = " + absoluteError + "\nsilhouette coeff = " + silhouette + "\n");
            printWriter.println("k = " + k + "\nabsolute error = " + absoluteError + "\nsilhouette coeff = " + silhouette + "\n");


            ArrayList<double[]> fakeMedoids = learner.fakeMedoids(k);
            double abs = Metrics.absoluteError(fakeMedoids, points);
            System.out.println("Fake medoids: absolute error = " + abs);
            printWriter.println("Fake medoids: absolute error = " + abs);
        }

        printWriter.close();

    }

}

enum StreamHandler {
    UNIT_HANDLER,
    AGGREGATE_HANDLER
}

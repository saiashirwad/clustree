package clustree;

import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.core.InstanceExample;
import com.yahoo.labs.samoa.instances.Instance;
import org.jfree.data.io.CSV;
import org.kramerlab.bmad.general.Tuple;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Dummy_ {

    public static Queue<Instance> q = new LinkedList<Instance>();
    public static Clusterer learner = new ClusTree();
    public static int i = 100000;


    public static void main(String[] args) {

        ArrayList<double[]> points = CSVReader.read("d:\\backup.csv");

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

        System.out.println("dunzo");

        ArrayList<double[]> kmedoids = learner.getKMedoids(2);

        double absoluteError = new Metrics().absoluteError(kmedoids, points);
        System.out.println(absoluteError);


    }

}

enum StreamHandler {
    UNIT_HANDLER,
    AGGREGATE_HANDLER
}

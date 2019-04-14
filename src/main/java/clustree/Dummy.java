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

public class Dummy {

    public static Queue<Instance> q = new LinkedList<Instance>();
    public static Clusterer learner = new ClusTree();
    public static int i = 200000;

    ArrayList<double[]> points = CSVReader.read("d:\\backup.csv");

    public static void main(String[] args) {

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue("d:\\covertype.csv");
        stream.prepareForUse();

        learner.resetLearning();

        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();

        Instant begin = Instant.now();

        Thread t = new Thread(new InputHandler());
        t.start();

        int speedPlaceholder = 0;
        int THRESHOLD = 30000;

        StreamHandler streamHandler = StreamHandler.UNIT_HANDLER;

        while (stream.hasMoreInstances() && i > 0) {
//            InstanceExample trainInst = stream.nextInstance();
//            Instance inst = trainInst.instance;

            if (speedPlaceholder < THRESHOLD) {
                if (streamHandler != StreamHandler.UNIT_HANDLER) {
                    streamHandler = StreamHandler.UNIT_HANDLER;

                    try {
                        t.join();
                    }
                    catch (Exception e) {}

                    t = new Thread(new InputHandler());
                    t.start();
                }
            }
            else {
                if (streamHandler != StreamHandler.AGGREGATE_HANDLER) {
                    streamHandler = StreamHandler.AGGREGATE_HANDLER;

                    try {
                        t.join();
                    }
                    catch (Exception e) {}

                    t = new Thread(new AggregationHandler());
                    t.start();
                }
            }

            int bufSize = ThreadLocalRandom.current().nextInt(1, 10);
            ArrayList<Instance> instances = new ArrayList<>();
            for (int i = 0; i < bufSize; i++) {
                if (stream.hasMoreInstances()) {
                    instances.add(stream.nextInstance().instance);
                }
            }

            q.addAll(instances);
//            learner.trainOnInstance(inst);
            i -= bufSize;
        }

        try {
            t.join();
        }
        catch (Exception e) {}

        Node root = learner.getRoot();
        System.out.println("dunzo");

        Instant end = Instant.now();
        System.out.println(Duration.between(end, begin));



    }

}

//enum StreamHandler {
//    UNIT_HANDLER,
//    AGGREGATE_HANDLER
//}

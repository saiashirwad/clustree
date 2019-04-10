package clustree;

import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.core.InstanceExample;
import com.yahoo.labs.samoa.instances.Instance;
import org.kramerlab.bmad.general.Tuple;

import java.util.*;

public class Dummy {

    public static Queue<Instance> q = new LinkedList<Instance>();
    public static Clusterer learner = new ClusTree();
    public static int i = 100000;

    public static void main(String[] args) {

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue("d:\\covertype.csv");
        stream.prepareForUse();

        learner.resetLearning();

        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();

//        Thread t = new Thread(new InputHandler());
//        t.start();

        while (stream.hasMoreInstances() && i > 0) {
            InstanceExample trainInst = stream.nextInstance();
            Instance inst = trainInst.instance;
//            q.add(inst);

            learner.trainOnInstance(inst);
            i--;
        }
//        try {
//            t.join();
//        }
//        catch (Exception e) {}

        Node root = learner.getRoot();
        System.out.println("dunzo");
    }

}

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




        while (stream.hasMoreInstances() && i > 0) {
//            if (i % 10000 == 0) {
//                System.out.println(i);
//            }
            InstanceExample trainInst = stream.nextInstance();
            Instance inst = trainInst.instance;
            learner.trainOnInstance(inst);
            i--;
        }

        Node root = learner.getRoot();
        ArrayList<Node> leaves = learner.collectLeafNodes(root);


        List<Tuple<Double, Double>> pointTuples = new ArrayList<Tuple<Double, Double>>();

//        ArrayList<ClusKernel> points = new ArrayList<ClusKernel>();

        Queue<ClusKernel> points = new LinkedList<ClusKernel>();
        LinkedList<Integer> pointArrSizes = new LinkedList<Integer>();
        int nPoints = 0;
        for (Node leaf: leaves) {
            for (Entry e: leaf.getEntries()) {
                e.data.makeOlder(learner.getTimeStamp() - e.getTimestamp(), learner.getNegLambda());
                Tuple<Double, Double> data = new Tuple<Double, Double>(e.data.totalN, e.data.getWeight());
                pointArrSizes.add(e.points.size());
                points.addAll(e.points);

//                try {
//                    for (ClusKernel point: e.points) {
//                        points.add(point);
//                    }
//
//                    pointTuples.add(data);
//                }
//                catch (Exception ex) {}

            }
        }

        System.out.println(Counter.getInstance().val);

    }

    public int[] checkTree() {
        Node root = learner.getRoot();

        int[] maxInLevel = new int[9];
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node temp = queue.remove();
            int level = temp.getLevel();

            if (temp.sinceLastTraversal > maxInLevel[temp.getLevel()]) {
                maxInLevel[temp.getLevel()] = temp.sinceLastTraversal;
            }
            try {
                for (Entry e: temp.getEntries()) {
                    queue.add(e.getChild());
                }
            }
            catch (Exception e) {}
        }
        return maxInLevel;
    }
}

//class InputHandler extends Thread {
//    public void run() {
//        int done = 0;
//        while (!Thread.currentThread().isInterrupted()) {
//            System.out.println(done);
//            try {
//                if (!Dummy.q.isEmpty()) {
//                    Dummy.learner.trainOnInstance(Dummy.q.remove());
//                }
//            }
//            catch (Exception e) {
//                continue;
//            }
//
//        }
//    }
//}
//
//

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
    public static int i = 500000;

    public static void main(String[] args) {

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue("d:\\covertype.csv");
        stream.prepareForUse();

        learner.resetLearning();

        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();



        while (stream.hasMoreInstances() && i > 0) {
            if (i % 50000 == 0) {
                System.out.println("yay");
            }
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
        int nPoints = 0;
        for (Node leaf: leaves) {
            for (Entry e: leaf.getEntries()) {
                e.data.makeOlder(learner.getTimeStamp() - e.getTimestamp(), learner.getNegLambda());
                Tuple<Double, Double> data = new Tuple<Double, Double>(e.data.totalN, e.data.getWeight());

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



        Queue<Node> q = new LinkedList<Node>();
//        q.add(root);
//
//        while (!q.isEmpty()) {
//            Node temp = q.remove();
//
//            try {
//                for (Entry e : temp.getEntries()) {
////                    System.out.println(e.points.size());
//                    q.add(e.getChild());
//                }
//            }
//            catch (Exception e) {
//                continue;
//            }
//        }

        System.out.println(points.size());
        Comparator<Tuple<Double, Double>> comparator = new Comparator<Tuple<Double, Double>>() {
            public int compare(Tuple<Double, Double> o1, Tuple<Double, Double> o2) {
                return o1._2.compareTo(o2._2);
            }
        };

        Collections.sort(pointTuples, comparator);


        for (Tuple<Double, Double> t: pointTuples) {
//            System.out.println(t._2 + " " + t._1);
        }




        Clustering result = learner.getMicroClusteringResult();
//        System.out.println(result);

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

package clustree;

import moa.clusterers.Clusterer;
import moa.core.InstanceExample;
import com.yahoo.labs.samoa.instances.Instance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Dummy {
    public static Queue<Integer> q = new LinkedList<Integer>();

    public static void main(String[] args) {
        Clusterer learner = new ClusTree();

        SimpleCSVStream stream = new SimpleCSVStream();
        stream.csvFileOption.setValue("d:\\covertype.csv");
        stream.prepareForUse();

        System.out.println(stream.hasMoreInstances());

//        Queue<Integer> q = new LinkedList<Integer>();
        learner.setModelContext(stream.getHeader());
        learner.prepareForUse();
        int i = 5;

        Thread t = new Thread(new Informer());
        t.start();
        while (stream.hasMoreInstances() && i > 0) {
            InstanceExample trainInst = stream.nextInstance();
            Instance inst = trainInst.instance;
            learner.trainOnInstance(inst);
            q.add(i);
            i--;

        }
    }
}

class Informer extends Thread {
    public void run() {
        while(Dummy.q.isEmpty()) {
            System.out.println("empty");
        }
        Dummy.q.remove();
    }
}
package clustree;


import sun.security.jca.GetInstance;

import java.util.ArrayList;

public class AggregationHandler implements Runnable {

    public boolean aggregate = false;
    private ArrayList<GetInstance.Instance> aggregates = new ArrayList<>();

    private volatile boolean shutdown = false;

    @Override
    public void run() {
        while (!shutdown) {

        }
    }

    public void shutDown() {
        this.shutdown = true;
    }

    public void addToAggregate() {

    }
}

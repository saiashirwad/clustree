package clustree;

import com.yahoo.labs.samoa.instances.Instance;

public class InputHandler implements Runnable {

    public StreamStatus streamStatus = new StreamStatus();

    private volatile boolean shutdown = false;

    public void run() {
        while (!shutdown) {
            if (Dummy.i % 10000 == 0 && Dummy.i > 0) {
                System.out.println(Dummy.i);
            }
            try {
                Instance inst = Dummy.q.remove();
                Dummy.learner.trainOnInstance(inst);

                QueueState qs = QueueState.getInstance();

                if (Dummy.q.isEmpty()) {
                    qs.isEmpty = true;
                }
                else {
                    qs.isEmpty = false;
                }
            }
            catch (Exception e) {}

            if (Dummy.i == 0 && Dummy.q.isEmpty()) {
                shutdown();
            }
        }
    }

    public void shutdown() {
        this.shutdown = true;
    }
}

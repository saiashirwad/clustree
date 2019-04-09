package clustree;

import java.util.concurrent.ThreadPoolExecutor;

public class Monitor implements Runnable {
    private ThreadPoolExecutor executor;
    private int seconds;
    private boolean run = true;

    public Monitor(ThreadPoolExecutor executor, int delay) {
        this.executor = executor;
        this.seconds = delay;
    }

    public void shutdown() {
        this.run = false;
    }

    public void run() {

    }
}

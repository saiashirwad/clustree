package clustree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PAMService implements Runnable {
//    static final int MAX_T = Runtime.getRuntime().availableProcessors();
    static final int MAX_T = 100;
    private ExecutorService pool = Executors.newFixedThreadPool(MAX_T);
    public boolean shutdown = false;

    private PAMService instance = null;

    Queue<Runnable> tasks = new LinkedList<>();

    private PAMService() {}

    public PAMService getInstance() {
        if (this.instance == null) {
            this.instance = new PAMService();
        }

        return this;
    }


    public void addTask(Runnable task) {
        this.tasks.add(task);
    }

    public void run() {
        while (!shutdown) {
            try {
                if (!tasks.isEmpty()) {
                    pool.execute(tasks.remove());
                }
            }
            catch (Exception e) {}
        }
        pool.shutdown();
    }

    public void shutdown() {
        this.shutdown = true;
    }
}

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

    private static PAMService instance = null;

    Queue<Runnable> tasks = new LinkedList<>();

    private PAMService() {}

    public static void init() {
        PAMService.instance = new PAMService();
    }

    public synchronized static PAMService getInstance() {
        if (PAMService.instance == null) {
            PAMService.instance = new PAMService();
        }

        return PAMService.instance;
    }


    public void addTask(Runnable task) {
        this.tasks.add(task);
    }

    public void run() {
        while (!shutdown) {
            try {
                if (!tasks.isEmpty()) {
                    pool.execute(tasks.remove());
                    System.out.println("Task added");
                }
            }
            catch (Exception e) {}
        }
        pool.shutdown();
    }

    public void shutdown() {
        this.pool.shutdown();
        this.shutdown = true;
    }
}

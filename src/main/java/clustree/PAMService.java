package clustree;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PAMService {

    private static PAMService instance = null;
    ExecutorService executor = Executors.newFixedThreadPool(100);


    private PAMService() {}

    public static synchronized PAMService getInstance() {
        if (instance == null)
            instance = new PAMService();
        return instance;
    }

    public void submitTask(Runnable task) {
        executor.execute(task);
    }

    public void shutdown() {
        executor.shutdown();
        while (!executor.isTerminated()) {

        }
    }

}
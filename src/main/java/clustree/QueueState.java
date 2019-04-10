package clustree;

public class QueueState {
    public boolean isEmpty;
    private static QueueState instance = null;

    private QueueState() {
        isEmpty = true;
    }

    public synchronized static QueueState getInstance() {
        if (instance == null) {
            instance = new QueueState();
        }

        return instance;
    }
}

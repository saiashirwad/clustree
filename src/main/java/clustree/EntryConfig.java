package clustree;

public class EntryConfig {
    private static int queueSize;
    private static int updatePoints;

    public static int getUpdatePoints() {
        return updatePoints;
    }

    public static void setUpdatePoints(int updatePoints) {
        EntryConfig.updatePoints = updatePoints;
    }

    private EntryConfig() {}

    public static void setQueueSize(int queueSize) {
        EntryConfig.queueSize = queueSize;
    }

    public static int getQueueSize() {
        return EntryConfig.queueSize;
    }

}

package clustree;

/**
 * Gets values from ExperimentConfig.
 *
 */
public class ThreadConfig {
    private static int k;

    private ThreadConfig(int k) {
        this.k = k;
    }

    public static void setK(int k) {
        ThreadConfig.k = k;
    }

    public static int getK() {
        return ThreadConfig.k;
    }
}

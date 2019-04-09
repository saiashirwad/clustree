package clustree;

public class Counter {
    public int val;
    private static Counter instance = null;

    private Counter() {
        val = 0;
    }


    public static Counter getInstance() {
        if (instance == null) {
            instance = new Counter();
        }
        return instance;
    }
}

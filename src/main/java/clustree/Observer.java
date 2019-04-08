package clustree;


class Observer extends Thread {
    int root = 0;
//    boolean alive = true;
    public void run() {
        while(Thread.currentThread().isInterrupted()) {
            try {
                if (!Dummy.q.isEmpty()) {
                    int r = Dummy.learner.getRoot().getId();
                    if (r != this.root) {
                        System.out.println(r);
                        this.root = r;
                    }
                }
            }
            catch (Exception e) {
            }
        }
    }

}
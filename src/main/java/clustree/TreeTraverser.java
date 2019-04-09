package clustree;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



/**
 * This is ideally supposed to traverse the tree every 10,000 miilliseconds to find out the node AT EACH LEVEL
 * of the ClusTree that has had the most insertions since the last check
 */
class TreeTraverser extends Thread {
    Queue<Node> queue = new LinkedList<Node>();
    HashMap<Integer, Node> map = new HashMap<Integer, Node>();

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            Node root = Dummy.learner.getRoot();
            queue.add(root);

            while (!queue.isEmpty()) {
                Node temp = queue.remove();
                maybeUpdate(temp);

                try {
                    for (Entry e: temp.getEntries()) {
                        queue.add(e.getChild());
                    }
                }
                catch (Exception e) {}
            }

            try {
                Thread.sleep(10000);
            }
            catch (Exception e) {}
        }
    }

    void maybeUpdate(Node temp) {
        int level = temp.getLevel();

        if (map.containsKey(level)) {
//            if ()
        }
    }
}
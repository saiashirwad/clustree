package clustree;

public class Tree {
    public TreeNode root;

    public Tree(int item) {
        root = new TreeNode(item);
    }
}

class TreeNode {
    int key;
    TreeNode[] children;

    public TreeNode(int item) {
        key = item;
        for(TreeNode tn: children) {
            tn = null;
        }
    }
}

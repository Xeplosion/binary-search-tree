public class BinarySearchTree<T extends Comparable<T>> {
    private class TreeNode {
        T data;
        TreeNode left;
        TreeNode right;
    }

    private TreeNode root;

    public boolean insert(T value) {
        TreeNode newNode = new TreeNode();
        newNode.data = value;
        if (root == null) {
            root = newNode;
        } else {
            TreeNode parent = null;
            TreeNode node = root;
            while(node != null) { // find the node to attach the new node to
                parent = node;
                if (node.data.compareTo(value) < 0) { // go to left
                    node = node.left;
                } else if (node.data.compareTo(value) > 0) { // go to the right
                    node = node.right;
                } else {
                    return false; // Already in tree
                }
            }
            if (parent.data.compareTo(value) < 0) { // attach to the left
                parent.left = newNode;
            } else { // attach to the right
                parent.right = newNode;
            }
        }
        return true; // Value added!
    }
    public boolean remove(T value) {
        TreeNode parent = null;
        TreeNode node = root;
        boolean done = false; // find the node you are deleting
        while(!done && node != null) {
            if(node.data.compareTo(value) < 0) {// go to left
                parent = node;
                node = node.left;
            } else if (node.data.compareTo(value) > 0) { // go to the right
                parent = node;
                node = node.right;
            } else {
                done = true;
            }
        }

        if (node == null) return false; // Value not in tree

        if (node.left == null) { // Delete value and rearrange tree
            if (node == root) {
                root = node.right;
            } else if (node == parent.left) {
                parent.left = node.right;
            } else {
                parent.right = node.right;
            }
        } else {
           TreeNode parentOfRightMost = node;

           TreeNode rightMost = node.left;
           while(rightMost.right != null) {
               parentOfRightMost = rightMost;
               rightMost = rightMost.right;
           }
           node.data = rightMost.data;
           if (parentOfRightMost.left == rightMost) {
               parentOfRightMost.left = rightMost.left;
           } else {
               parentOfRightMost.right = rightMost.left;
           }
        }

        return true; // Value removed from the tree
    }
    public boolean search(T value) {
        TreeNode node = root;
        boolean done = false; // Find what you're looking for? UWU <3
        while(!done && node != null) {
            if(node.data.compareTo(value) < 0) {// go to left
                node = node.left;
            } else if (node.data.compareTo(value) > 0) { // go to the right;
                node = node.right;
            } else {
                done = true;
            }
        }

        return node != null; // Is the value in the tree?
    }
    public void display(String message) {
        System.out.println(message);
        displayInOrder(root);
    }
    private void displayInOrder (TreeNode node) {
        if (node == null) return;
        displayInOrder(node.left);
        System.out.println(node.data);
        displayInOrder(node.right);
    }
    public int numberNodes() {
        return numberNodes(root);
    }
    private int numberNodes(TreeNode node) {
        if (node == null) return 0;
        int leftBranch = numberNodes(node.left); // Count nodes in left and right branches recursively
        int rightBranch = numberNodes(node.right);
        return leftBranch + rightBranch + 1;
    }
    public int numberLeafNodes() {
        return numberLeafNodes(root);
    }
    private int numberLeafNodes(TreeNode node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1; // Node is a leaf
        int leftLeaves = numberLeafNodes(node.left);
        int rightLeaves = numberLeafNodes(node.right);
        return leftLeaves + rightLeaves;
    }
    public int height() {
        return climbTree(root) - 1; // I don't think the tree's height should start at 0 :(
    }
    private int climbTree(TreeNode node) {
        if (node == null) return 0;
        int leftBranch = climbTree(node.left);
        int rightBranch = climbTree(node.right);
        return 1 + Math.max(leftBranch, rightBranch); // Return largest branch height value
    }
}
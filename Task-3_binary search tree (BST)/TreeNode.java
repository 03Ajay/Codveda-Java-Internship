/**
 * TreeNode class represents a node in the Binary Search Tree.
 * Each node contains data and references to left and right child nodes.
 * 
 * @author CodeVita Task 3
 * @version 1.0
 */
public class TreeNode {
    int data;
    TreeNode left;
    TreeNode right;

    /**
     * Constructor to create a new TreeNode with given data.
     * 
     * @param data The integer value to be stored in the node
     */
    public TreeNode(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }

    /**
     * Default constructor for TreeNode.
     */
    public TreeNode() {
        this.data = 0;
        this.left = null;
        this.right = null;
    }

    /**
     * Gets the data stored in this node.
     * 
     * @return The data value of this node
     */
    public int getData() {
        return data;
    }

    /**
     * Sets the data for this node.
     * 
     * @param data The data value to set
     */
    public void setData(int data) {
        this.data = data;
    }

    /**
     * Gets the left child of this node.
     * 
     * @return The left child TreeNode
     */
    public TreeNode getLeft() {
        return left;
    }

    /**
     * Sets the left child of this node.
     * 
     * @param left The TreeNode to set as left child
     */
    public void setLeft(TreeNode left) {
        this.left = left;
    }

    /**
     * Gets the right child of this node.
     * 
     * @return The right child TreeNode
     */
    public TreeNode getRight() {
        return right;
    }

    /**
     * Sets the right child of this node.
     * 
     * @param right The TreeNode to set as right child
     */
    public void setRight(TreeNode right) {
        this.right = right;
    }

    /**
     * Checks if this node is a leaf node (has no children).
     * 
     * @return true if this node has no children, false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }

    /**
     * Returns a string representation of this node.
     * 
     * @return String representation of the node's data
     */
    @Override
    public String toString() {
        return "TreeNode{data=" + data + "}";
    }
}
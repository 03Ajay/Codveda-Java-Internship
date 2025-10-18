import java.util.*;

/**
 * Binary Search Tree (BST) implementation with comprehensive functionality.
 * This class provides insertion, deletion, search, and traversal operations.
 * 
 * Key Properties of BST:
 * - Left subtree contains nodes with values less than the root
 * - Right subtree contains nodes with values greater than the root
 * - Both left and right subtrees are also binary search trees
 * 
 * @author CodeVita Task 3
 * @version 1.0
 */
public class BinarySearchTree {
    private TreeNode root;
    private int size;

    /**
     * Constructor to initialize an empty BST.
     */
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Constructor to initialize BST with a root value.
     * 
     * @param rootValue The value for the root node
     */
    public BinarySearchTree(int rootValue) {
        this.root = new TreeNode(rootValue);
        this.size = 1;
    }

    // ===================== INSERTION METHODS =====================

    /**
     * Public method to insert a value into the BST.
     * 
     * @param value The value to insert
     * @return true if insertion was successful, false if value already exists
     */
    public boolean insert(int value) {
        int initialSize = size;
        root = insertRecursive(root, value);
        return size > initialSize; // Return true if size increased (successful insertion)
    }

    /**
     * Private recursive method to insert a value into the BST.
     * 
     * @param node  The current node being examined
     * @param value The value to insert
     * @return The node after insertion
     */
    private TreeNode insertRecursive(TreeNode node, int value) {
        // Base case: if node is null, create new node
        if (node == null) {
            size++;
            return new TreeNode(value);
        }

        // If value is less than current node, insert in left subtree
        if (value < node.data) {
            node.left = insertRecursive(node.left, value);
        }
        // If value is greater than current node, insert in right subtree
        else if (value > node.data) {
            node.right = insertRecursive(node.right, value);
        }
        // If value equals current node data, do nothing (no duplicates allowed)

        return node;
    }

    // ===================== SEARCH METHODS =====================

    /**
     * Public method to search for a value in the BST.
     * 
     * @param value The value to search for
     * @return true if value exists in the tree, false otherwise
     */
    public boolean search(int value) {
        return searchRecursive(root, value);
    }

    /**
     * Private recursive method to search for a value in the BST.
     * 
     * @param node  The current node being examined
     * @param value The value to search for
     * @return true if value is found, false otherwise
     */
    private boolean searchRecursive(TreeNode node, int value) {
        // Base case: node is null (value not found)
        if (node == null) {
            return false;
        }

        // If value matches current node data
        if (value == node.data) {
            return true;
        }

        // Search in left subtree if value is smaller
        if (value < node.data) {
            return searchRecursive(node.left, value);
        }
        // Search in right subtree if value is larger
        else {
            return searchRecursive(node.right, value);
        }
    }

    /**
     * Find and return the TreeNode containing the specified value.
     * 
     * @param value The value to find
     * @return The TreeNode containing the value, or null if not found
     */
    public TreeNode findNode(int value) {
        return findNodeRecursive(root, value);
    }

    /**
     * Private recursive method to find a node with the specified value.
     * 
     * @param node  The current node being examined
     * @param value The value to find
     * @return The TreeNode containing the value, or null if not found
     */
    private TreeNode findNodeRecursive(TreeNode node, int value) {
        if (node == null || node.data == value) {
            return node;
        }

        if (value < node.data) {
            return findNodeRecursive(node.left, value);
        } else {
            return findNodeRecursive(node.right, value);
        }
    }

    // ===================== DELETION METHODS =====================

    /**
     * Public method to delete a value from the BST.
     * 
     * @param value The value to delete
     * @return true if deletion was successful, false if value doesn't exist
     */
    public boolean delete(int value) {
        int initialSize = size;
        root = deleteRecursive(root, value);
        return size < initialSize; // Return true if size decreased (successful deletion)
    }

    /**
     * Private recursive method to delete a value from the BST.
     * 
     * @param node  The current node being examined
     * @param value The value to delete
     * @return The node after deletion
     */
    private TreeNode deleteRecursive(TreeNode node, int value) {
        // Base case: node is null (value not found)
        if (node == null) {
            return null;
        }

        // If value is smaller than current node, delete from left subtree
        if (value < node.data) {
            node.left = deleteRecursive(node.left, value);
        }
        // If value is greater than current node, delete from right subtree
        else if (value > node.data) {
            node.right = deleteRecursive(node.right, value);
        }
        // If value equals current node data, this is the node to delete
        else {
            size--; // Decrement size as we're deleting a node

            // Case 1: Node has no children (leaf node)
            if (node.left == null && node.right == null) {
                return null;
            }

            // Case 2: Node has only right child
            if (node.left == null) {
                return node.right;
            }

            // Case 3: Node has only left child
            if (node.right == null) {
                return node.left;
            }

            // Case 4: Node has both children
            // Find the inorder successor (smallest value in right subtree)
            TreeNode successor = findMin(node.right);

            // Copy the successor's data to this node
            node.data = successor.data;

            // Delete the successor (which will have at most one child)
            node.right = deleteRecursive(node.right, successor.data);
            size++; // Increment back as we just replaced, not actually deleted
        }

        return node;
    }

    /**
     * Find the node with minimum value in a subtree.
     * 
     * @param node The root of the subtree
     * @return The node with minimum value
     */
    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Find the node with maximum value in a subtree.
     * 
     * @param node The root of the subtree
     * @return The node with maximum value
     */
    private TreeNode findMax(TreeNode node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    // ===================== TRAVERSAL METHODS =====================

    /**
     * Perform in-order traversal of the BST.
     * In-order: Left -> Root -> Right
     * For BST, this gives sorted order of elements.
     * 
     * @return List of values in in-order sequence
     */
    public List<Integer> inOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        inOrderRecursive(root, result);
        return result;
    }

    /**
     * Private recursive method for in-order traversal.
     * 
     * @param node   The current node
     * @param result The list to store traversal result
     */
    private void inOrderRecursive(TreeNode node, List<Integer> result) {
        if (node != null) {
            inOrderRecursive(node.left, result); // Visit left subtree
            result.add(node.data); // Visit root
            inOrderRecursive(node.right, result); // Visit right subtree
        }
    }

    /**
     * Perform pre-order traversal of the BST.
     * Pre-order: Root -> Left -> Right
     * 
     * @return List of values in pre-order sequence
     */
    public List<Integer> preOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        preOrderRecursive(root, result);
        return result;
    }

    /**
     * Private recursive method for pre-order traversal.
     * 
     * @param node   The current node
     * @param result The list to store traversal result
     */
    private void preOrderRecursive(TreeNode node, List<Integer> result) {
        if (node != null) {
            result.add(node.data); // Visit root
            preOrderRecursive(node.left, result); // Visit left subtree
            preOrderRecursive(node.right, result); // Visit right subtree
        }
    }

    /**
     * Perform post-order traversal of the BST.
     * Post-order: Left -> Right -> Root
     * 
     * @return List of values in post-order sequence
     */
    public List<Integer> postOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        postOrderRecursive(root, result);
        return result;
    }

    /**
     * Private recursive method for post-order traversal.
     * 
     * @param node   The current node
     * @param result The list to store traversal result
     */
    private void postOrderRecursive(TreeNode node, List<Integer> result) {
        if (node != null) {
            postOrderRecursive(node.left, result); // Visit left subtree
            postOrderRecursive(node.right, result); // Visit right subtree
            result.add(node.data); // Visit root
        }
    }

    /**
     * Perform level-order (breadth-first) traversal of the BST.
     * 
     * @return List of values in level-order sequence
     */
    public List<Integer> levelOrderTraversal() {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            result.add(current.data);

            if (current.left != null) {
                queue.offer(current.left);
            }
            if (current.right != null) {
                queue.offer(current.right);
            }
        }

        return result;
    }

    // ===================== UTILITY METHODS =====================

    /**
     * Get the root node of the BST.
     * 
     * @return The root TreeNode
     */
    public TreeNode getRoot() {
        return root;
    }

    /**
     * Check if the BST is empty.
     * 
     * @return true if BST is empty, false otherwise
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Get the number of nodes in the BST.
     * 
     * @return The size of the BST
     */
    public int size() {
        return size;
    }

    /**
     * Get the height of the BST.
     * 
     * @return The height of the tree (longest path from root to leaf)
     */
    public int height() {
        return heightRecursive(root);
    }

    /**
     * Private recursive method to calculate height of a subtree.
     * 
     * @param node The root of the subtree
     * @return The height of the subtree
     */
    private int heightRecursive(TreeNode node) {
        if (node == null) {
            return -1; // Height of empty tree is -1
        }

        int leftHeight = heightRecursive(node.left);
        int rightHeight = heightRecursive(node.right);

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Find the minimum value in the BST.
     * 
     * @return The minimum value, or Integer.MAX_VALUE if tree is empty
     */
    public int findMinValue() {
        if (root == null) {
            return Integer.MAX_VALUE;
        }
        TreeNode minNode = findMin(root);
        return minNode.data;
    }

    /**
     * Find the maximum value in the BST.
     * 
     * @return The maximum value, or Integer.MIN_VALUE if tree is empty
     */
    public int findMaxValue() {
        if (root == null) {
            return Integer.MIN_VALUE;
        }
        TreeNode maxNode = findMax(root);
        return maxNode.data;
    }

    /**
     * Validate if the tree maintains BST property.
     * 
     * @return true if tree is a valid BST, false otherwise
     */
    public boolean isValidBST() {
        return isValidBSTRecursive(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Private recursive method to validate BST property.
     * 
     * @param node The current node
     * @param min  The minimum allowed value
     * @param max  The maximum allowed value
     * @return true if subtree is valid BST, false otherwise
     */
    private boolean isValidBSTRecursive(TreeNode node, int min, int max) {
        if (node == null) {
            return true;
        }

        if (node.data <= min || node.data >= max) {
            return false;
        }

        return isValidBSTRecursive(node.left, min, node.data) &&
                isValidBSTRecursive(node.right, node.data, max);
    }

    /**
     * Clear all nodes from the BST.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Print the BST structure in a visual format.
     */
    public void printTree() {
        if (root == null) {
            System.out.println("Tree is empty");
            return;
        }
        printTreeRecursive(root, "", true);
    }

    /**
     * Private recursive method to print tree structure.
     * 
     * @param node   The current node
     * @param indent The indentation string
     * @param isLast Whether this is the last child
     */
    private void printTreeRecursive(TreeNode node, String indent, boolean isLast) {
        if (node != null) {
            System.out.println(indent + (isLast ? "└── " : "├── ") + node.data);

            if (node.left != null || node.right != null) {
                if (node.right != null) {
                    printTreeRecursive(node.right, indent + (isLast ? "    " : "│   "), node.left == null);
                }
                if (node.left != null) {
                    printTreeRecursive(node.left, indent + (isLast ? "    " : "│   "), true);
                }
            }
        }
    }

    /**
     * Convert BST to a sorted array.
     * 
     * @return Array containing all values in sorted order
     */
    public int[] toSortedArray() {
        List<Integer> inOrder = inOrderTraversal();
        return inOrder.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * String representation of the BST showing in-order traversal.
     * 
     * @return String representation of the BST
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            return "BST: []";
        }
        return "BST: " + inOrderTraversal().toString();
    }
}
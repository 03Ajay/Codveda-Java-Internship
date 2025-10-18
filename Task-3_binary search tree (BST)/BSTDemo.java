/**
 * Quick demonstration of Binary Search Tree functionality.
 * This class provides a simple example of how to use the BST implementation.
 * 
 * @author CodeVita Task 3
 * @version 1.0
 */
public class BSTDemo {

    /**
     * Main method demonstrating basic BST usage.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("🌳 Binary Search Tree - Quick Demo");
        System.out.println("=====================================\n");

        // Create a new BST
        BinarySearchTree bst = new BinarySearchTree();

        // Insert some values
        System.out.println("📥 Inserting values: 50, 30, 70, 20, 40, 60, 80");
        int[] values = { 50, 30, 70, 20, 40, 60, 80 };

        for (int value : values) {
            bst.insert(value);
        }

        // Display tree structure
        System.out.println("\n🏗️  Tree Structure:");
        bst.printTree();

        // Show different traversals
        System.out.println("\n🔄 Traversal Results:");
        System.out.println("In-Order (Sorted):     " + bst.inOrderTraversal());
        System.out.println("Pre-Order:             " + bst.preOrderTraversal());
        System.out.println("Post-Order:            " + bst.postOrderTraversal());
        System.out.println("Level-Order:           " + bst.levelOrderTraversal());

        // Demonstrate search operations
        System.out.println("\n🔍 Search Operations:");
        int[] searchValues = { 40, 90, 20, 100 };
        for (int value : searchValues) {
            boolean found = bst.search(value);
            System.out.println("Search " + value + ": " + (found ? "✅ FOUND" : "❌ NOT FOUND"));
        }

        // Show tree statistics
        System.out.println("\n📊 Tree Statistics:");
        System.out.println("Size: " + bst.size());
        System.out.println("Height: " + bst.height());
        System.out.println("Minimum Value: " + bst.findMinValue());
        System.out.println("Maximum Value: " + bst.findMaxValue());
        System.out.println("Is Valid BST: " + bst.isValidBST());

        // Demonstrate deletion
        System.out.println("\n🗑️  Deletion Demo:");
        System.out.println("Deleting 30 (node with two children)...");
        boolean deleted = bst.delete(30);
        System.out.println("Deletion successful: " + deleted);
        System.out.println("New In-Order: " + bst.inOrderTraversal());

        System.out.println("\n🏗️  Updated Tree Structure:");
        bst.printTree();

        // Convert to sorted array
        System.out.println("\n📋 Sorted Array:");
        int[] sortedArray = bst.toSortedArray();
        System.out.print("[");
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.print(sortedArray[i]);
            if (i < sortedArray.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");

        System.out.println("\n✨ Demo completed! Check BSTTest.java for comprehensive testing.");
    }
}
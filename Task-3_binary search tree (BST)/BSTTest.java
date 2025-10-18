/**
 * Test class to demonstrate and validate Binary Search Tree functionality.
 * This class provides comprehensive testing of all BST operations including
 * insertion, deletion, search, and various traversal methods.
 * 
 * @author CodeVita Task 3
 * @version 1.0
 */
public class BSTTest {

    /**
     * Main method to run BST demonstrations and tests.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("     BINARY SEARCH TREE - COMPREHENSIVE TESTING");
        System.out.println("=".repeat(60));

        // Test 1: Basic Operations
        testBasicOperations();

        // Test 2: Insertion and Search
        testInsertionAndSearch();

        // Test 3: Traversal Methods
        testTraversalMethods();

        // Test 4: Deletion Operations
        testDeletionOperations();

        // Test 5: Utility Methods
        testUtilityMethods();

        // Test 6: Edge Cases
        testEdgeCases();

        // Test 7: Complex Scenario
        testComplexScenario();

        System.out.println("\n" + "=".repeat(60));
        System.out.println("     ALL TESTS COMPLETED SUCCESSFULLY!");
        System.out.println("=".repeat(60));
    }

    /**
     * Test basic BST operations and construction.
     */
    private static void testBasicOperations() {
        System.out.println("\n--- TEST 1: BASIC OPERATIONS ---");

        // Create empty BST
        BinarySearchTree bst = new BinarySearchTree();
        System.out.println("Created empty BST");
        System.out.println("Is empty: " + bst.isEmpty());
        System.out.println("Size: " + bst.size());

        // Create BST with root value
        BinarySearchTree bst2 = new BinarySearchTree(50);
        System.out.println("\nCreated BST with root value 50");
        System.out.println("Is empty: " + bst2.isEmpty());
        System.out.println("Size: " + bst2.size());
        System.out.println("BST: " + bst2.toString());
    }

    /**
     * Test insertion and search operations.
     */
    private static void testInsertionAndSearch() {
        System.out.println("\n--- TEST 2: INSERTION AND SEARCH ---");

        BinarySearchTree bst = new BinarySearchTree();

        // Test insertions
        int[] values = { 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45 };
        System.out.println("Inserting values: ");
        for (int value : values) {
            boolean inserted = bst.insert(value);
            System.out.println("Insert " + value + ": " + (inserted ? "SUCCESS" : "FAILED"));
        }

        System.out.println("\nFinal BST size: " + bst.size());
        System.out.println("BST structure:");
        bst.printTree();

        // Test duplicate insertion
        System.out.println("\nTesting duplicate insertion:");
        boolean duplicateResult = bst.insert(50);
        System.out.println("Insert duplicate 50: " + (duplicateResult ? "SUCCESS" : "FAILED (Expected)"));
        System.out.println("Size after duplicate attempt: " + bst.size());

        // Test search operations
        System.out.println("\nTesting search operations:");
        int[] searchValues = { 50, 25, 45, 80, 100, 5 };
        for (int value : searchValues) {
            boolean found = bst.search(value);
            System.out.println("Search " + value + ": " + (found ? "FOUND" : "NOT FOUND"));
        }
    }

    /**
     * Test all traversal methods.
     */
    private static void testTraversalMethods() {
        System.out.println("\n--- TEST 3: TRAVERSAL METHODS ---");

        BinarySearchTree bst = new BinarySearchTree();
        int[] values = { 50, 30, 70, 20, 40, 60, 80, 10, 35, 65, 75 };

        for (int value : values) {
            bst.insert(value);
        }

        System.out.println("BST with values: ");
        bst.printTree();

        System.out.println("\nTraversal Results:");
        System.out.println("In-Order (L-Root-R):   " + bst.inOrderTraversal());
        System.out.println("Pre-Order (Root-L-R):  " + bst.preOrderTraversal());
        System.out.println("Post-Order (L-R-Root): " + bst.postOrderTraversal());
        System.out.println("Level-Order (BFS):     " + bst.levelOrderTraversal());

        System.out.println("\nNote: In-Order traversal gives sorted sequence for BST");
    }

    /**
     * Test deletion operations for all cases.
     */
    private static void testDeletionOperations() {
        System.out.println("\n--- TEST 4: DELETION OPERATIONS ---");

        BinarySearchTree bst = new BinarySearchTree();
        int[] values = { 50, 30, 70, 20, 40, 60, 80, 10, 25, 35, 45, 65, 75, 90 };

        for (int value : values) {
            bst.insert(value);
        }

        System.out.println("Initial BST:");
        bst.printTree();
        System.out.println("Initial In-Order: " + bst.inOrderTraversal());
        System.out.println("Initial Size: " + bst.size());

        // Test Case 1: Delete leaf node
        System.out.println("\n1. Deleting leaf node (10):");
        boolean deleted = bst.delete(10);
        System.out.println("Delete 10: " + (deleted ? "SUCCESS" : "FAILED"));
        System.out.println("After deletion: " + bst.inOrderTraversal());
        System.out.println("Size: " + bst.size());

        // Test Case 2: Delete node with one child
        System.out.println("\n2. Deleting node with one child (25):");
        deleted = bst.delete(25);
        System.out.println("Delete 25: " + (deleted ? "SUCCESS" : "FAILED"));
        System.out.println("After deletion: " + bst.inOrderTraversal());
        System.out.println("Size: " + bst.size());

        // Test Case 3: Delete node with two children
        System.out.println("\n3. Deleting node with two children (30):");
        deleted = bst.delete(30);
        System.out.println("Delete 30: " + (deleted ? "SUCCESS" : "FAILED"));
        System.out.println("After deletion: " + bst.inOrderTraversal());
        System.out.println("Size: " + bst.size());

        // Test Case 4: Delete root node
        System.out.println("\n4. Deleting root node (50):");
        deleted = bst.delete(50);
        System.out.println("Delete 50: " + (deleted ? "SUCCESS" : "FAILED"));
        System.out.println("After deletion: " + bst.inOrderTraversal());
        System.out.println("Size: " + bst.size());

        System.out.println("\nFinal BST structure:");
        bst.printTree();

        // Test deletion of non-existent value
        System.out.println("\n5. Deleting non-existent value (100):");
        deleted = bst.delete(100);
        System.out.println("Delete 100: " + (deleted ? "SUCCESS" : "FAILED (Expected)"));
        System.out.println("Size: " + bst.size());
    }

    /**
     * Test utility methods.
     */
    private static void testUtilityMethods() {
        System.out.println("\n--- TEST 5: UTILITY METHODS ---");

        BinarySearchTree bst = new BinarySearchTree();
        int[] values = { 50, 30, 70, 20, 40, 60, 80, 10, 35, 65, 75 };

        for (int value : values) {
            bst.insert(value);
        }

        System.out.println("BST: " + bst.toString());
        System.out.println("Size: " + bst.size());
        System.out.println("Height: " + bst.height());
        System.out.println("Is empty: " + bst.isEmpty());
        System.out.println("Minimum value: " + bst.findMinValue());
        System.out.println("Maximum value: " + bst.findMaxValue());
        System.out.println("Is valid BST: " + bst.isValidBST());

        System.out.println("Sorted array: ");
        int[] sortedArray = bst.toSortedArray();
        System.out.print("[");
        for (int i = 0; i < sortedArray.length; i++) {
            System.out.print(sortedArray[i]);
            if (i < sortedArray.length - 1)
                System.out.print(", ");
        }
        System.out.println("]");

        // Test findNode method
        System.out.println("\nTesting findNode method:");
        TreeNode node = bst.findNode(40);
        if (node != null) {
            System.out.println("Found node with value 40: " + node.toString());
        } else {
            System.out.println("Node with value 40 not found");
        }

        TreeNode notFound = bst.findNode(100);
        if (notFound != null) {
            System.out.println("Found node with value 100: " + notFound.toString());
        } else {
            System.out.println("Node with value 100 not found (Expected)");
        }
    }

    /**
     * Test edge cases and boundary conditions.
     */
    private static void testEdgeCases() {
        System.out.println("\n--- TEST 6: EDGE CASES ---");

        // Test empty tree operations
        System.out.println("1. Testing operations on empty tree:");
        BinarySearchTree emptyBST = new BinarySearchTree();
        System.out.println("Search in empty tree: " + emptyBST.search(10));
        System.out.println("Delete from empty tree: " + emptyBST.delete(10));
        System.out.println("Height of empty tree: " + emptyBST.height());
        System.out.println("Min value in empty tree: " + emptyBST.findMinValue());
        System.out.println("Max value in empty tree: " + emptyBST.findMaxValue());
        System.out.println("In-order of empty tree: " + emptyBST.inOrderTraversal());
        emptyBST.printTree();

        // Test single node tree
        System.out.println("\n2. Testing single node tree:");
        BinarySearchTree singleNodeBST = new BinarySearchTree(42);
        System.out.println("Single node BST: " + singleNodeBST.toString());
        System.out.println("Height: " + singleNodeBST.height());
        System.out.println("Search 42: " + singleNodeBST.search(42));
        System.out.println("Search 50: " + singleNodeBST.search(50));
        System.out.println("Delete 42: " + singleNodeBST.delete(42));
        System.out.println("After deletion: " + singleNodeBST.toString());
        System.out.println("Is empty: " + singleNodeBST.isEmpty());

        // Test linear tree (worst case)
        System.out.println("\n3. Testing linear tree (worst case):");
        BinarySearchTree linearBST = new BinarySearchTree();
        for (int i = 1; i <= 5; i++) {
            linearBST.insert(i);
        }
        System.out.println("Linear BST (1 to 5): " + linearBST.toString());
        System.out.println("Height: " + linearBST.height());
        linearBST.printTree();
    }

    /**
     * Test complex scenario with multiple operations.
     */
    private static void testComplexScenario() {
        System.out.println("\n--- TEST 7: COMPLEX SCENARIO ---");

        BinarySearchTree bst = new BinarySearchTree();

        System.out.println("Creating a BST with the following operations:");

        // Phase 1: Build initial tree
        System.out.println("\nPhase 1: Building initial tree");
        int[] initialValues = { 50, 25, 75, 15, 35, 65, 85, 10, 20, 30, 40, 60, 70, 80, 90 };
        for (int value : initialValues) {
            bst.insert(value);
            System.out.print(value + " ");
        }
        System.out.println("\nInitial tree structure:");
        bst.printTree();
        System.out.println("In-order: " + bst.inOrderTraversal());

        // Phase 2: Perform searches
        System.out.println("\nPhase 2: Performing searches");
        int[] searchValues = { 25, 75, 5, 45, 95, 50 };
        for (int value : searchValues) {
            boolean found = bst.search(value);
            System.out.println("Search " + value + ": " + (found ? "✓" : "✗"));
        }

        // Phase 3: Delete some nodes
        System.out.println("\nPhase 3: Deleting nodes");
        int[] deleteValues = { 10, 35, 25 }; // leaf, one child, two children
        for (int value : deleteValues) {
            System.out.println("Deleting " + value + "...");
            boolean deleted = bst.delete(value);
            System.out.println("Result: " + (deleted ? "SUCCESS" : "FAILED"));
            System.out.println("New in-order: " + bst.inOrderTraversal());
        }

        // Phase 4: Insert new values
        System.out.println("\nPhase 4: Inserting new values");
        int[] newValues = { 5, 45, 95, 12, 38 };
        for (int value : newValues) {
            boolean inserted = bst.insert(value);
            System.out.println("Insert " + value + ": " + (inserted ? "SUCCESS" : "FAILED"));
        }

        // Final state
        System.out.println("\nFinal BST state:");
        System.out.println("Size: " + bst.size());
        System.out.println("Height: " + bst.height());
        System.out.println("Min: " + bst.findMinValue());
        System.out.println("Max: " + bst.findMaxValue());
        System.out.println("Is valid BST: " + bst.isValidBST());
        System.out.println("In-order: " + bst.inOrderTraversal());
        System.out.println("Pre-order: " + bst.preOrderTraversal());
        System.out.println("Post-order: " + bst.postOrderTraversal());
        System.out.println("Level-order: " + bst.levelOrderTraversal());

        System.out.println("\nFinal tree structure:");
        bst.printTree();

        // Test clear operation
        System.out.println("\nTesting clear operation:");
        bst.clear();
        System.out.println("After clear - Size: " + bst.size() + ", Is empty: " + bst.isEmpty());
    }

    /**
     * Helper method to print a separator line.
     * 
     * @param character The character to use for the line
     * @param length    The length of the line
     */
    // ...existing code...
}
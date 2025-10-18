# Binary Search Tree (BST) Implementation

## Overview

This project provides a comprehensive implementation of a Binary Search Tree (BST) in Java with complete functionality for insertion, deletion, search, and various traversal methods. The implementation follows professional coding standards and includes extensive testing.

## 🌳 Project Structure

```
Task-3_binary search tree (BST)/
├── TreeNode.java           # Node class for BST
├── BinarySearchTree.java   # Main BST implementation
├── BSTTest.java           # Comprehensive test suite
└── README.md              # This documentation
```

## 📋 Features

### Core Operations

- ✅ **Insertion**: Add new nodes while maintaining BST property
- ✅ **Deletion**: Remove nodes (handles all cases: leaf, one child, two children)
- ✅ **Search**: Find nodes efficiently using BST property
- ✅ **Traversal**: Multiple traversal methods (In-order, Pre-order, Post-order, Level-order)

### Advanced Features

- ✅ **Tree Validation**: Verify BST property maintenance
- ✅ **Height Calculation**: Determine tree height
- ✅ **Min/Max Finding**: Locate minimum and maximum values
- ✅ **Tree Visualization**: Print tree structure visually
- ✅ **Array Conversion**: Convert BST to sorted array
- ✅ **Size Tracking**: Maintain accurate node count

## 🏗️ Class Architecture

### TreeNode Class

```java
public class TreeNode {
    int data;              // Node value
    TreeNode left;         // Left child reference
    TreeNode right;        // Right child reference

    // Constructors and utility methods
}
```

### BinarySearchTree Class

The main BST class provides:

- **Constructors**: Empty tree or tree with root value
- **Core Operations**: insert(), delete(), search()
- **Traversal Methods**: inOrderTraversal(), preOrderTraversal(), postOrderTraversal(), levelOrderTraversal()
- **Utility Methods**: height(), size(), isEmpty(), findMinValue(), findMaxValue()
- **Advanced Features**: isValidBST(), printTree(), clear()

## 🚀 Usage Examples

### Basic Operations

```java
// Create a new BST
BinarySearchTree bst = new BinarySearchTree();

// Insert values
bst.insert(50);
bst.insert(30);
bst.insert(70);
bst.insert(20);
bst.insert(40);

// Search for values
boolean found = bst.search(30);  // Returns true
boolean notFound = bst.search(100);  // Returns false

// Delete a value
boolean deleted = bst.delete(30);  // Returns true if successful
```

### Traversal Operations

```java
// Get different traversal orders
List<Integer> inOrder = bst.inOrderTraversal();      // Sorted order
List<Integer> preOrder = bst.preOrderTraversal();    // Root-first
List<Integer> postOrder = bst.postOrderTraversal();  // Root-last
List<Integer> levelOrder = bst.levelOrderTraversal(); // Level by level
```

### Utility Operations

```java
// Get tree information
int size = bst.size();                // Number of nodes
int height = bst.height();            // Tree height
boolean isEmpty = bst.isEmpty();      // Check if empty
int minValue = bst.findMinValue();    // Minimum value
int maxValue = bst.findMaxValue();    // Maximum value

// Validate BST property
boolean isValid = bst.isValidBST();   // Returns true if valid BST

// Visualize tree structure
bst.printTree();                      // Prints tree visually
```

## 📊 BST Properties

### Binary Search Tree Rules

1. **Left Subtree**: All values less than root
2. **Right Subtree**: All values greater than root
3. **Recursively Applied**: Each subtree is also a BST
4. **No Duplicates**: Duplicate values are not allowed

### Time Complexities

| Operation | Average Case | Worst Case | Best Case |
| --------- | ------------ | ---------- | --------- |
| Search    | O(log n)     | O(n)       | O(1)      |
| Insert    | O(log n)     | O(n)       | O(1)      |
| Delete    | O(log n)     | O(n)       | O(1)      |
| Traversal | O(n)         | O(n)       | O(n)      |

### Space Complexity

- **Space**: O(n) for storing n nodes
- **Recursion Stack**: O(h) where h is height (O(log n) average, O(n) worst)

## 🧪 Testing

The `BSTTest.java` class provides comprehensive testing including:

1. **Basic Operations Test**: Construction and basic functionality
2. **Insertion and Search Test**: Multiple insertions and searches
3. **Traversal Methods Test**: All traversal types
4. **Deletion Operations Test**: All deletion cases
5. **Utility Methods Test**: Helper functions
6. **Edge Cases Test**: Empty tree, single node, linear tree
7. **Complex Scenario Test**: Real-world usage simulation

### Running Tests

```bash
# Compile all Java files
javac *.java

# Run the test suite
java BSTTest
```

## 📈 Traversal Methods Explained

### 1. In-Order Traversal (Left → Root → Right)

- **Purpose**: Produces sorted sequence for BST
- **Use Case**: Getting sorted data from BST
- **Example**: `[10, 20, 30, 40, 50, 60, 70]`

### 2. Pre-Order Traversal (Root → Left → Right)

- **Purpose**: Root-first processing
- **Use Case**: Creating a copy of the tree, prefix expression
- **Example**: `[50, 30, 20, 10, 40, 70, 60]`

### 3. Post-Order Traversal (Left → Right → Root)

- **Purpose**: Children-first processing
- **Use Case**: Deleting tree, postfix expression
- **Example**: `[10, 20, 40, 30, 60, 70, 50]`

### 4. Level-Order Traversal (Breadth-First)

- **Purpose**: Level-by-level processing
- **Use Case**: Tree visualization, finding shortest path
- **Example**: `[50, 30, 70, 20, 40, 60, 80]`

## 🔧 Implementation Details

### Deletion Cases Handled

1. **Leaf Node**: Simply remove the node
2. **One Child**: Replace node with its child
3. **Two Children**: Replace with in-order successor

### Key Design Decisions

- **No Duplicates**: Maintains BST property and simplifies logic
- **Size Tracking**: Enables O(1) size queries
- **Recursive Implementation**: Clean and maintainable code
- **Comprehensive Error Handling**: Robust operation handling

## 🎯 Best Practices Implemented

1. **Professional Documentation**: Comprehensive JavaDoc comments
2. **Error Handling**: Proper validation and edge case handling
3. **Code Organization**: Clean separation of concerns
4. **Testing**: Thorough test coverage
5. **Performance**: Efficient algorithms and data structures
6. **Maintainability**: Clean, readable code structure

## 🚦 Example Output

When running `BSTTest.java`, you'll see:

```
============================================================
     BINARY SEARCH TREE - COMPREHENSIVE TESTING
============================================================

--- TEST 1: BASIC OPERATIONS ---
Created empty BST
Is empty: true
Size: 0

Created BST with root value 50
Is empty: false
Size: 1
BST: [50]

--- TEST 2: INSERTION AND SEARCH ---
Inserting values:
Insert 50: SUCCESS
Insert 30: SUCCESS
Insert 70: SUCCESS
...

BST structure:
└── 50
    ├── 70
    │   ├── 80
    │   └── 60
    └── 30
        ├── 40
        │   └── 45
        │   └── 35
        └── 20
            ├── 25
            └── 10
```

## 📝 Notes

- The implementation maintains BST property at all times
- All operations are thoroughly tested for correctness
- The code follows Java naming conventions and best practices
- Memory management is handled efficiently
- The tree visualization helps understand structure

## 🔄 Future Enhancements

Potential improvements that could be added:

- Self-balancing (AVL Tree, Red-Black Tree)
- Iterator implementation
- Serialization support
- Generic type support
- Thread-safety features
- Performance optimizations

---

**Author**: CodeVita Task 3  
**Version**: 1.0  
**Language**: Java  
**Compatibility**: Java 8+

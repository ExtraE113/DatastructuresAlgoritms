package com.ezranewman.bst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Binary Search Tree starter code
 *
 * @author Nathan Lin
 * @author Ezra Newman
 * @version Sept 22, 2020
 */


public class BST {
    private Node root;

    public BST() {
        this.root = null;
    }

    // Searches the bst for the given value
    public boolean search(int val) {
        Node working = root;
        while (working != null) {
            if (working.data == val) {
                return true;
            } else if (working.data > val) {
                working = working.left;
            } else {
                working = working.right;
            }
        }
        return false;
    }

    // Recursively searches the tree for val
    private static boolean searchRec(int val, Node current) {
        if (current.data == val) {
            return true;
        } else if (current.data > val) {
            if (current.left == null) {
                return false;
            }
            return searchRec(val, current.left);
        } else {
            if (current.right == null) {
                return false;
            }
            return searchRec(val, current.right);
        }
    }

    // Inserts val into the BST
    private void insert(int val) {
        Node working = root;
        if (working == null) {
            root = new Node(val);
            return;
        }
        while (true) {
            if (working.data == val) {
                return;
            } else if (working.data > val) {
                if (working.left == null) {
                    working.left = new Node(val);
                    return;
                }
                working = working.left;
            } else {
                if (working.right == null) {
                    working.right = new Node(val);
                    return;
                }
                working = working.right;
            }
        }
    }

    // Deletes val from the BST
    // This is a wrapper function that just calls deleteRec
    public void delete(int val) {
        root = deleteRec(root, val);
    }

    // Deletes val from the BST
    private static Node deleteRec(Node current, int val)
    {
        // Base Case: if tree is empty, return null
        if (current == null) {
            return null;
        }

        // If val < current.data, recur down left subtree
        if (val < current.data) {
            current.left = deleteRec(current.left, val);
            return current;
        }
        // If val > current.data recur down right subtree
        else if (val > current.data) {
            current.right = deleteRec(current.right, val);
            return current;
        }
        // If val is same as current's data, then this is
        // the node to be deleted
        else
        {
            if(current.right == null && current.left == null){
                return  null;
            } else if (current.left == null) {
                return current.right;
            } else if(current.right == null) {
                return current.left;
            } else {
                current.data = minValue(current.right);
                current.right = deleteRec(current.right, current.data);
                return current;
            }
        }

        // We should never reach this line of code,
        // feel free to delete this return statement
        //return current;

    }

    // Returns the smallest value in the subtree
    // where current is the root
    private static int minValue(Node current) {
        if(current.left == null){
            return current.data;
        } else {
            return minValue(current.left);
        }
    }

    // Prints out an inorder traversal of the tree:
    // Left, root, right
    public static void inOrderTraversal(Node node) {
        if(node.left != null){
            inOrderTraversal(node.left);
        }

        System.out.println(node.data);

        if(node.right != null){
            inOrderTraversal(node.right);
        }

    }

    // Prints out an preorder traversal of the tree:
    // Root, left right
    public static void preOrderTraversal(Node node) {
        System.out.println(node.data);
        if(node.left != null){
            preOrderTraversal(node.left);
        }
        if(node.right != null){
            preOrderTraversal(node.right);
        }
    }

    // Prints out an postOrder traversal of the tree:
    // Left, right root
    public static void postOrderTraversal(Node node) {
        if(node.left != null){
            postOrderTraversal(node.left);
        }
        if(node.right != null){
            postOrderTraversal(node.right);
        }
        System.out.println(node.data);
    }


    public static void main(String[] args) {
        // Test 1: Test iterative search
        System.out.println("Test 1: Iterative Search");
        BST bst = new BST();
        Node node10 = new Node(10);
        Node node2 = new Node(2);
        Node node5 = new Node(5);
        Node node7 = new Node(7);
        Node node3 = new Node(3);
        Node node6 = new Node(6);
        Node node11 = new Node(11);
        Node node13 = new Node(13);
        Node node12 = new Node(12);

        bst.root = node7;
        node7.left = node3;
        node3.left = node2;
        node3.right = node6;
        node6.left = node5;
        node7.right = node10;
        node10.right = node12;
        node12.left = node11;
        node12.right = node13;

        for (int i = 0; i < 15; i += 2) {
            System.out.println(bst.search(i));
        }
        // false
        // true
        // false
        // true
        // false
        // true
        // true
        // false

        // Test 2: Test recursive search
        System.out.println("\nTest 2: Recursive Search");
        for (int i = 0; i < 15; i += 2) {
            System.out.println(searchRec(i, bst.root));
        }
        // false
        // true
        // false
        // true
        // false
        // true
        // true
        // false


        // Test 3: Insert some nodes
        System.out.println("\nTest 3:");
        BST integerBST = new BST();
        integerBST.insert(4);
        integerBST.insert(7);
        integerBST.insert(3);
        integerBST.insert(8);
        BTreePrinter.printNode(integerBST.root);
        //    4
        //   / \
        //  /   \
        //  3   7
        //       \
        //       8

        // Test 4: Inorder traversal (and other optional traversals)
        System.out.println("\nTest 4:");
        inOrderTraversal(integerBST.root);
        // 3
        // 4
        // 7
        // 8

        System.out.println();
        preOrderTraversal(integerBST.root);
        // 4
        // 3
        // 7
        // 8

        System.out.println();
        postOrderTraversal(integerBST.root);
        // 3
        // 8
        // 7
        // 4


        // Test 5: Delete a leaf
        System.out.println("\nTest 5:");
        integerBST.insert(9);
        BTreePrinter.printNode(integerBST.root);
        //        4
        //       / \
        //      /   \
        //     /     \
        //    /       \
        //    3       7
        //             \
        //              \
        //              8
        //               \
        //               9

        integerBST.delete(9);
        BTreePrinter.printNode(integerBST.root);
        //    4
        //   / \
        //  /   \
        //  3   7
        //       \
        //       8

        // Test 6: Delete a node with one child
        System.out.println("\nTest 6:");
        integerBST.insert(9);
        BTreePrinter.printNode(integerBST.root);
        //        4
        //       / \
        //      /   \
        //     /     \
        //    /       \
        //    3       7
        //             \
        //              \
        //              8
        //               \
        //               9

        integerBST.delete(8);
        BTreePrinter.printNode(integerBST.root);
        //    4
        //   / \
        //  /   \
        //  3   7
        //       \
        //       9

        // Test 7: Delete a node with two children
        System.out.println("Test 7:");
        integerBST.insert(6);
        BTreePrinter.printNode(integerBST.root);
        //    4
        //   / \
        //  /   \
        //  3   7
        //     / \
        //     6 9

        integerBST.delete(7);
        BTreePrinter.printNode(integerBST.root);
        //
        //    4
        //   / \
        //  /   \
        //  3   9
        //     /
        //     6

        System.out.println();
        integerBST.insert(8);
        integerBST.insert(7);
        integerBST.insert(15);
        integerBST.insert(16);
        integerBST.insert(14);

        BTreePrinter.printNode(integerBST.root);
        integerBST.delete(9);
        BTreePrinter.printNode(integerBST.root);


    }


}

class Node {
    int data;
    Node left;
    Node right;

    public Node(int data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

// Print binary tree in a helpful way
// from: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
class BTreePrinter {

    public static <T extends Comparable<?>> void printNode(Node root) {
        int maxLevel = BTreePrinter.maxLevel(root);

        printNodeInternal(Collections.singletonList(root), 1, maxLevel);
    }

    private static <T extends Comparable<?>> void printNodeInternal(List<Node> nodes, int level, int maxLevel) {
        if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
            return;

        int floor = maxLevel - level;
        int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
        int firstSpaces = (int) Math.pow(2, (floor)) - 1;
        int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

        BTreePrinter.printWhitespaces(firstSpaces);

        List<Node> newNodes = new ArrayList<Node>();
        for (Node node : nodes) {
            if (node != null) {
                System.out.print(node.data);
                newNodes.add(node.left);
                newNodes.add(node.right);
            } else {
                newNodes.add(null);
                newNodes.add(null);
                System.out.print(" ");
            }

            BTreePrinter.printWhitespaces(betweenSpaces);
        }
        System.out.println("");

        for (int i = 1; i <= endgeLines; i++) {
            for (int j = 0; j < nodes.size(); j++) {
                BTreePrinter.printWhitespaces(firstSpaces - i);
                if (nodes.get(j) == null) {
                    BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                    continue;
                }

                if (nodes.get(j).left != null)
                    System.out.print("/");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(i + i - 1);

                if (nodes.get(j).right != null)
                    System.out.print("\\");
                else
                    BTreePrinter.printWhitespaces(1);

                BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
            }

            System.out.println("");
        }

        printNodeInternal(newNodes, level + 1, maxLevel);
    }

    private static void printWhitespaces(int count) {
        for (int i = 0; i < count; i++)
            System.out.print(" ");
    }

    private static <T extends Comparable<?>> int maxLevel(Node node) {
        if (node == null)
            return 0;

        return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
    }

    private static <T> boolean isAllElementsNull(List<T> list) {
        for (Object object : list) {
            if (object != null)
                return false;
        }

        return true;
    }

}


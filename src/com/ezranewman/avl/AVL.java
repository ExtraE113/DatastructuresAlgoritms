package com.ezranewman.avl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AVL {
	Node root;

	public AVL() {
		this.root = null;
	}

	// Writing a height "getter" will help us avoid null pointer exceptions
	static int height(Node current) {
		if (current == null) {
			return 0;
		} else {
			return current.height;
		}
	}

	// Get Balance factor of node current
	int getBalance(Node current) {
		if (current == null)
			return 0;

		return height(current.left) - height(current.right);
	}

	//         A                                      B
	//        / \                                   /   \
	//       B   Ar         LL_Rotate (A)          C      A
	//      / \          - - - - - - - - ->      /  \    /  \
	//     C   Br                               Cl  Cr  Br  Ar
	//    / \
	//  Cl   Cr
	public Node LL_Rotate(Node A) {
		Node B = A.left;
		Node Br = B.right;
		B.right = A;
		A.left = Br;

		A.height = Math.max(height(A.right), height(A.left)) + 1;
		B.height = Math.max(height(B.right), height(B.left)) + 1;

		return B;
	}

	//      A                             C
	//     / \                          /  \
	//    B   Ar     LR_Rotate(A)     B      A
	//   / \       - - - - - - - ->  / \    / \
	// Bl   C                      Bl  Cl Cr  Ar
	//     / \
	//   Cl   Cr
	public Node LR_Rotate(Node A) {
		Node Ar = A.right;
		Node B = A.left;
		Node Bl = B.left;
		Node C = B.right;
		Node Cl = C.left;
		Node Cr = C.right;

		C.left = B;
		C.right = A;
		B.right = Cl;
		A.left = Cr;

		A.height = Math.max(height(A.right), height(A.left)) + 1;
		B.height = Math.max(height(B.right), height(B.left)) + 1;
		C.height = Math.max(height(C.right), height(C.left)) + 1;

		return C;
	}

	//   A                                B
	//  /  \                            /   \
	// Al   B       RR_Rotate(A)       A      C
	//     /  \   - - - - - - - ->    / \    / \
	//    Bl   C                     Al  Bl Cl  Cr
	//        / \
	//      Cl  Cr
	public Node RR_Rotate(Node A) {
		Node B = A.right;
		Node Bl = B.left;
		B.left = A;
		A.right = Bl;

		A.height = Math.max(height(A.right), height(A.left)) + 1;
		B.height = Math.max(height(B.right), height(B.left)) + 1;

		return B;
	}

	//    A                           C
	//   / \                         /  \
	// Al   B       RL_Rotate(A)   A      B
	//     / \    - - - - - - ->  / \    / \
	//    C   Br                Al  Cl  Cr  Br
	//   / \
	// Cl   Cr

	public Node RL_Rotate(Node A) {
		Node Ar = A.right;
		Node B = A.right;
		Node Bl = B.left;
		Node C = B.left;
		Node Cl = C.left;
		Node Cr = C.right;

		C.left = A;
		C.right = B;
		B.left = Cr;
		A.right = Cl;

		A.height = Math.max(height(A.right), height(A.left)) + 1;
		B.height = Math.max(height(B.right), height(B.left)) + 1;
		C.height = Math.max(height(C.right), height(C.left)) + 1;

		return C;
	}

	public void insert(int data) {
		this.root = insertRec(this.root, data);
	}

	private Node insertRec(Node current, int data) {
		// 1) Normal BST insertion
		if (current == null) {
			current = new Node(data);
			return current;
		} else if (data == current.data) {
			return current;
		} else if (data < current.data) {
			current.left = insertRec(current.left, data);
		} else /* if (data > current.data) */ {
			current.right = insertRec(current.right, data);
		}

		// 2) Make sure height is updated, if needed
		current.height = Math.max(height(current.left), height(current.right)) + 1;

		// 3) Check balance factor of current node to see if unbalanced
		int bf = getBalance(current);

		// If unbalanced, perform necessary rotations
		// 4) First check if LL or LR case
		if (bf > 1) {
			// LL: If inserted value is less than current.left.data,
			// then must be left left case
			if (data < current.left.data) {
				return LL_Rotate(current);
			}
			// LR: If inserted value is more than current.left.data,
			// then must be left left case
			else if (data > current.left.data) {
				return LR_Rotate(current);
			}
		}
		// 5) Next check if RR or RL case
		else if (bf < -1) {
			// RR: If inserted value is more than current.right.data,
			// then must be right right case
			if (data > current.right.data) {
				return RR_Rotate(current);
			}
			// RL: If inserted value is less than current.right.data,
			// then must be right left case
			else if (data < current.right.data) {
				return RL_Rotate(current);
			}
		}

		// If balanced, return the original node
		return current;
	}

	public void delete(int data) {
		this.root = deleteRec(this.root, data);
	}

	private Node deleteRec(Node current, int data) {
		// 1) Normal BST Deletion

		// Base case: current is null (the tree did not contain the node
		// we wanted to delete
		if (current == null) {
			return null;
		}

		// Recursive cases: we haven't found the node to delete yet,
		// so we search the appropriate subtree for it
		if (data < current.data) {
			current.left = deleteRec(current.left, data);
		} else if (data > current.data) {
			current.right = deleteRec(current.right, data);
		}

		// Else data == current.data, so we have found the node we want to delete.
		// To delete a node, we just need to return the new node (if any) that we want
		// the parent to have for where its deleted node used to be
		else {
			// Case 1: Node to be deleted is a leaf
			// We just need to return null here
			if (current.left == null && current.right == null) {
				return null;
			}
			// Case 2: Node to be deleted has only one child
			// We just need to return deleted node's child here
			else if (current.left == null) {
				return current.right;
			} else if (current.right == null) {
				return current.left;
			}
			// Case 3: Node to be deleted has two children
			// We need to do two things.
			// a) find the inorder successor and copy its value into the current node
			// b) delete the inorder successor
			else {
				// The inorder successor is the minimum value of the right subtree
				// a) Copy value of inorder successor into the current node
				current.data = minValue(current.right);

				// b) Delete the inorder successor
				current.right = deleteRec(current.right, current.data);
			}
		}

		// 2) Make sure height is updated, if needed
		current.height = Math.max(height(current.left), height(current.right)) + 1;

		// 3) Check balance factor of current node to see if unbalanced
		int bf = getBalance(current);

		// If unbalanced, perform necessary rotations

		//LL or LR
		if(bf == 2){
			if(getBalance(current.left) == 1){
				System.out.println("LL ROTATE" + current.data);
				return LL_Rotate(current);
			} else {
				System.out.println("LR ROTATE" + current.data);
				return LR_Rotate(current);
			}
		} else if(bf == -2){ //
			if(getBalance(current.right) == 1){
				System.out.println("RL ROTATE" + current.data);
				return RL_Rotate(current);
			} else {
				System.out.println("RR ROTATE" + current.data);
				return RR_Rotate(current);
			}
		}


		// If balanced, return the original node
		return current;
	}

	// Normal BST insert methods just for reference, and for use in testing
	public void BSTinsert(int data) {
		this.root = BSTinsertRec(this.root, data);
	}

	private Node BSTinsertRec(Node current, int data) {
		if (current == null) {
			current = new Node(data);
			return current;
		} else if (data == current.data) {
			return current;
		} else if (data < current.data) {
			current.left = BSTinsertRec(current.left, data);
		} else // if (data > current.data)
		{
			current.right = BSTinsertRec(current.right, data);
		}

		return current;
	}

	// Normal BST delete methods for reference
	public void BSTdelete(int data) {
		this.root = BSTdeleteRec(this.root, data);
	}

	private Node BSTdeleteRec(Node current, int data) {
		// Base case: current is null (the tree did not contain the node
		// we wanted to delete
		if (current == null) {
			return current;
		}

		// Recursive cases: we haven't found the node to delete yet,
		// so we search the appropriate subtree for it
		if (data < current.data) {
			current.left = BSTdeleteRec(current.left, data);
		} else if (data > current.data) {
			current.right = BSTdeleteRec(current.right, data);
		}

		// Else data == current.data, so we have found the node we want to delete.
		// To delete a node, we just need to return the new node (if any) that we want
		// the parent to have for where its deleted node used to be
		else {
			// Case 1: Node to be deleted is a leaf
			// We just need to return null here
			if (current.left == null && current.right == null) {
				return null;
			}
			// Case 2: Node to be deleted has only one child
			// We just need to return deleted node's child here
			else if (current.left == null) {
				return current.right;
			} else if (current.right == null) {
				return current.left;
			}
			// Case 3: Node to be deleted has two children
			// We need to do two things.
			// 1) find the inorder successor and copy its value into the current node
			// 2) delete the inorder successor
			else {
				// The inorder successor is the minimum value of the right subtree
				// 1) Copy value of inorder successor into the current node
				current.data = minValue(current.right);

				// 2) Delete the inorder successor
				current.right = BSTdeleteRec(current.right, current.data);
			}
		}

		return current;
	}

	// Helper function used to find inorder successor
	private int minValue(Node current) {
		int minimum = current.data;

		// Keep checking for a left child, if there are no left children
		// we have reached the leftmost (smallest) node
		while (current.left != null) {
			minimum = current.left.data;
			current = current.left;
		}
		return minimum;
	}

	// Object methods that are just used for testing rotations:
	// Test function
	public void LL_rot() {
		this.root = LL_Rotate(this.root);
	}

	// Test function
	public void LR_rot() {
		this.root = LR_Rotate(this.root);
	}

	// Test function
	public void RR_rot() {
		this.root = RR_Rotate(this.root);
	}

	// Test function
	public void RL_rot() {
		this.root = RL_Rotate(this.root);
	}

	public static void main(String[] args) {
		// ******
		// ROTATION TESTS:
		// ******

		// LL rotation
		System.out.println("Test 1: ");
		AVL AVL1 = new AVL();
		AVL1.BSTinsert(8);
		AVL1.BSTinsert(6);
		AVL1.BSTinsert(4);
		AVL1.BSTinsert(9);
		AVL1.BSTinsert(7);
		AVL1.BSTinsert(3);
		AVL1.BSTinsert(5);

		BTreePrinter.printNode(AVL1.root);
		//        8
		//       / \
		//      /   \
		//     /     \
		//    /       \
		//    6       9
		//   / \
		//  /   \
		//  4   7
		// / \
		// 3 5

		// LL_rot() is a wrapper function that just calls LL_rotate with the root of the AVL tree
		AVL1.LL_rot();

		BTreePrinter.printNode(AVL1.root);
		//    6
		//   / \
		//  /   \
		//  4   8
		// / \ / \
		// 3 5 7 9

		// LR rotation
		System.out.println("Test 1.5: ");
		AVL AVL2 = new AVL();
		AVL2.BSTinsert(8);
		AVL2.BSTinsert(4);
		AVL2.BSTinsert(6);
		AVL2.BSTinsert(9);
		AVL2.BSTinsert(3);
		AVL2.BSTinsert(5);
		AVL2.BSTinsert(7);

		BTreePrinter.printNode(AVL2.root);
		//       8
		//      / \
		//     /   \
		//    /     \
		//   /       \
		//   4       9
		//  / \
		// /   \
		// 3   6
		//    / \
		//    5 7

		AVL2.LR_rot();

		BTreePrinter.printNode(AVL2.root);
		//    6
		//   / \
		//  /   \
		//  4   8
		// / \ / \
		// 3 5 7 9

		// RR rotation
		System.out.println("Test 2: ");
		AVL AVL22 = new AVL();
		AVL22.BSTinsert(4);
		AVL22.BSTinsert(1);
		AVL22.BSTinsert(6);
		AVL22.BSTinsert(5);
		AVL22.BSTinsert(8);
		AVL22.BSTinsert(7);
		AVL22.BSTinsert(9);

		BTreePrinter.printNode(AVL22.root);
		//       4
		//      / \
		//     /   \
		//    /     \
		//   /       \
		//   1       6
		//          / \
		//         /   \
		//         5   8
		//            / \
		//            7 9

		AVL22.RR_rot();

		BTreePrinter.printNode(AVL22.root);
		//    6
		//   / \
		//  /   \
		//  4   8
		// / \ / \
		// 1 5 7 9

		// RL rotation
		System.out.println("Test 2.5: ");
		AVL AVL23 = new AVL();
		AVL23.BSTinsert(2);
		AVL23.BSTinsert(6);
		AVL23.BSTinsert(1);
		AVL23.BSTinsert(7);
		AVL23.BSTinsert(4);
		AVL23.BSTinsert(5);
		AVL23.BSTinsert(3);

		BTreePrinter.printNode(AVL23.root);
		//       2
		//      / \
		//     /   \
		//    /     \
		//   /       \
		//   1       6
		//          / \
		//         /   \
		//         4   7
		//        / \
		//        3 5

		AVL23.RL_rot();

		BTreePrinter.printNode(AVL23.root);
		//    4
		//   / \
		//  /   \
		//  2   6
		// / \ / \
		// 1 3 5 7

		// ******
		// INSERTION TESTS:
		// ******
		// Insert, LL-rotation test
		System.out.println("Test 3: ");
		AVL AVL3 = new AVL();
		AVL3.insert(8);
		AVL3.insert(6);
		AVL3.insert(4);
		BTreePrinter.printNode(AVL3.root);
		//  6
		// / \
		// 4 8

		// Insertion, LR-rotation test
		System.out.println("Test 4: ");
		AVL AVL4 = new AVL();
		AVL4.insert(8);
		AVL4.insert(4);
		AVL4.insert(6);
		BTreePrinter.printNode(AVL4.root);
		//  6
		// / \
		// 4 8
		// Insert, RR-rotation test
		System.out.println("Test 3.5: ");
		AVL AVL35 = new AVL();
		AVL35.insert(8);
		AVL35.insert(9);
		AVL35.insert(10);
		BTreePrinter.printNode(AVL35.root);
		//  9
		// / \
		// 8 10

		// Insertion, RL-rotation test
		System.out.println("Test 4.5: ");
		AVL AVL45 = new AVL();
		AVL45.insert(9);
		AVL45.insert(11);
		AVL45.insert(10);
		BTreePrinter.printNode(AVL45.root);
		//  10
		// / \
		// 9 11

		// **************
		// Deletion Tests
		// **************
		// Delete a leaf, should do LL rotate
		System.out.println("Test 5: ");
		AVL AVL5 = new AVL();
		AVL5.insert(8);
		AVL5.insert(6);
		AVL5.insert(9);
		AVL5.insert(5);
		AVL5.insert(7);
		AVL5.insert(10);
		AVL5.insert(4);
		BTreePrinter.printNode(AVL5.root);
		//        8
		//       / \
		//      /   \
		//     /     \
		//    /       \
		//    6       9
		//   / \       \
		//  /   \       \
		//  5   7       10
		// /
		// 4

		AVL5.delete(10);
		BTreePrinter.printNode(AVL5.root);
		//    6
		//   / \
		//  /   \
		//  5   8
		// /   / \
		// 4   7 9

		// Delete a node with one child, should do LL rotate
		System.out.println("Test 6: ");
		AVL AVL6 = new AVL();
		AVL6.insert(8);
		AVL6.insert(6);
		AVL6.insert(9);
		AVL6.insert(5);
		AVL6.insert(7);
		AVL6.insert(10);
		AVL6.insert(4);
		BTreePrinter.printNode(AVL6.root);
		//        8
		//       / \
		//      /   \
		//     /     \
		//    /       \
		//    6       9
		//   / \       \
		//  /   \       \
		//  5   7       10
		// /
		// 4

		AVL6.delete(9);
		BTreePrinter.printNode(AVL6.root);
		//    6
		//   / \
		//  /   \
		//  5   8
		// /   / \
		// 4   7 10

		// Delete a leaf, should do LR rotate
		System.out.println("Test 7: ");
		AVL AVL7 = new AVL();
		AVL7.insert(8);
		AVL7.insert(5);
		AVL7.insert(9);
		AVL7.insert(4);
		AVL7.insert(7);
		AVL7.insert(10);
		AVL7.insert(6);

		BTreePrinter.printNode(AVL7.root);
		//       8
		//      / \
		//     /   \
		//    /     \
		//   /       \
		//   5       9
		//  / \       \
		// /   \       \
		// 4   7       10
		//    /
		//    6

		AVL7.delete(10);
		BTreePrinter.printNode(AVL7.root);
		//    7
		//   / \
		//  /   \
		//  5   8
		// / \   \
		// 4 6   9

		// Delete a leaf, should rotate twice
		System.out.println("Test 8: ");
		AVL AVL8 = new AVL();
		AVL8.insert(5);

		AVL8.insert(2);
		AVL8.insert(10);

		AVL8.insert(1);
		AVL8.insert(4);
		AVL8.insert(7);
		AVL8.insert(11);

		AVL8.insert(3);
		AVL8.insert(6);
		AVL8.insert(9);
		AVL8.insert(12);

		AVL8.insert(8);

		BTreePrinter.printNode(AVL8.root);
		//               5
		//              / \
		//             /   \
		//            /     \
		//           /       \
		//          /         \
		//         /           \
		//        /             \
		//       /               \
		//       2               10
		//      / \             / \
		//     /   \           /   \
		//    /     \         /     \
		//   /       \       /       \
		//   1       4       7       11
		//          /       / \       \
		//         /       /   \       \
		//         3       6   9       12
		//                    /
		//                    8

		AVL8.delete(1);
		BTreePrinter.printNode(AVL8.root);
		//        7
		//       / \
		//      /   \
		//     /     \
		//    /       \
		//    5       10
		//   / \     / \
		//  /   \   /   \
		//  3   6   9   11
		// / \     /     \
		// 2 4     8     12
	}
}

class Node {
	int data;
	int height; // each node will keep track of how tall its subtree is
	Node left;
	Node right;

	public Node(int data) {
		this.data = data;
		this.height = 1; // all nodes will start as leaves, so they will have a default height of 1
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
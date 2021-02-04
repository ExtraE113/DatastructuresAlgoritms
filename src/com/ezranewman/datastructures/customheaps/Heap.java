package com.ezranewman.datastructures.customheaps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Heap {
	int[] arr;
	int maxSize;
	int currentSize;

	public Heap() {
		maxSize = 100;
		currentSize = 0;
		arr = new int[maxSize];
	}

	// Calculate the array index of the parent
	private int parent(int index) {
		return (index - 1) / 2;
	}

	// Calculate the array index of the first child
	private int child1(int index) {
		return (2 * index) + 1;
	}

	// Calculate the array index of the second child
	private int child2(int index) {
		return (2 * index) + 2;
	}

	// Move a node up to its correct location
	public void trickleUp(int index) {
		while(index > 0 && arr[index] < arr[parent(index)]){
			int temp = arr[parent(index)];
			arr[parent(index)] = arr[index];
			arr[index] = temp;
			index = parent(index);
		}
	}

	// Insert a new node
	// Most of the work will be done by the trickleUp() method
	public void insert(int key) {
		if(currentSize < maxSize){
			arr[currentSize] = key;
			trickleUp(currentSize);
			currentSize++;
		}
	}

	// Move a node down to its correct location
	public void trickleDown(int index) {
		return;
	}

	// Return the minimum value in the heap
	// and delete the corresponding node
	public int remove() {
		return -1;
	}

	// Change the priority of the value at the given index
	// to the new priority value
	public void change(int index, int newValue) {
		return;
	}

	// Print function: we'll just make it into a tree to make it easier for us
	public void print() {
		HeapNode[] nodes = new HeapNode[currentSize];
		for (int i = 0; i < currentSize; i++) {
			nodes[i] = new HeapNode(arr[i]);
		}

		for (int i = 0; i < currentSize; i++) {
			int child1Index = child1(i);
			int child2Index = child2(i);

			if (child1Index < currentSize) {
				nodes[i].left = nodes[child1Index];
			}
			if (child2Index < currentSize) {
				nodes[i].right = nodes[child2Index];
			}
		}

		if (currentSize > 0) {
			BTreePrinter.printNode(nodes[0]);
		}
	}

	public static void main(String[] args) {
		Heap test = new Heap();

		// Test 1: Insert nodes requiring no trickling up
		System.out.println("Test 1: ");
		test.insert(7);
		test.insert(8);
		test.insert(9);

		test.print();
		//  7
		// / \
		// 8 9

		// Test 2: Insert nodes that will need to be trickled up
		System.out.println("Test 2: ");
		test.insert(1);
		test.insert(2);
		test.insert(3);

		test.print();
		//    1
		//   / \
		//  /   \
		//  2   3
		// / \ /
		// 8 7 9

		// Test 3: Delete a node
		System.out.println("Test 3: ");
		test.remove();
		test.print();
		//    2
		//   / \
		//  /   \
		//  7   3
		// / \
		// 8 9

		// Test 4: Delete some more nodes
		System.out.println("Test 4: ");
		test.remove();
		test.remove();
		test.print();
		//  7
		// / \
		// 8 9

		// Test 5: Insert some nodes, change values of other nodes that trickle up
		System.out.println("Test 5: ");
		test.insert(3);
		test.insert(4);
		test.insert(5);
		test.insert(6);
		test.print();
		//    3
		//   / \
		//  /   \
		//  4   5
		// / \ / \
		// 8 7 9 6

		test.change(2, 2);
		test.print();
		//    2
		//   / \
		//  /   \
		//  4   3
		// / \ / \
		// 8 7 9 6

		test.change(4, 1);
		test.print();
		//    1
		//   / \
		//  /   \
		//  2   3
		// / \ / \
		// 8 4 9 6

		// Test 6: Change values of nodes to trickle down
		System.out.println("Test 6: ");
		test.print();
		//    1
		//   / \
		//  /   \
		//  2   3
		// / \ / \
		// 8 4 9 6

		test.change(2, 10);
		test.print();
		//    1
		//   / \
		//  /   \
		//  2   6
		// / \ / \
		// 8 4 9 10

		test.change(0, 20);
		test.print();
		//    2
		//   / \
		//  /   \
		//  4   6
		// / \ / \
		// 8 20 9 10
	}
}

// This class is just for the print function
class HeapNode {
	int data;
	HeapNode left;
	HeapNode right;

	public HeapNode(int data) {
		this.data = data;
	}
}

// Print binary tree in a helpful way
// from: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
class BTreePrinter {

	public static <T extends Comparable<?>> void printNode(HeapNode root) {
		int maxLevel = BTreePrinter.maxLevel(root);

		printNodeInternal(Collections.singletonList(root), 1, maxLevel);
	}

	private static <T extends Comparable<?>> void printNodeInternal(List<HeapNode> nodes, int level, int maxLevel) {
		if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
			return;

		int floor = maxLevel - level;
		int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
		int firstSpaces = (int) Math.pow(2, (floor)) - 1;
		int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;

		BTreePrinter.printWhitespaces(firstSpaces);

		List<HeapNode> newNodes = new ArrayList<HeapNode>();
		for (HeapNode node : nodes) {
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

	private static <T extends Comparable<?>> int maxLevel(HeapNode node) {
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

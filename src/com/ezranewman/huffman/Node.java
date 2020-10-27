package com.ezranewman.huffman;

import java.util.PriorityQueue;

public class Node implements Comparable<Node> {
	char ch;
	int frequency;
	Node left;
	Node right;

	public Node(char ch, int frequency) {
		this.ch = ch;
		this.frequency = frequency;
	}

	// Needed to provide a way for the PriorityQueue to rank
	// our nodes
	@Override
	public int compareTo(Node other) {
		return (this.frequency - other.frequency);
	}

	// Given some String, counts the number of times each
	// capital letter and the space character shows up.
	// Returns an int[] containing these counts; index 0
	// of the array corresponds to the number of A's, index 1
	// corresponds to the numbers of B's, etc.
	// Index 26 corresponds to the number of spaces
	public static int[] countChars(String str) {
		int[] out = new int[27];
		for (char c : str.toCharArray()) {
			if (c == ' ') {
				out[26]++;
			} else {
				out[c - 65]++;
			}
		}
		return out;
	}

	// Given an int[] of the counts of each letter/the space
	// character, returns the corresponding Huffman Tree
	public static Node createHuffmanTree(int[] counts) {
		PriorityQueue<Node> pq = new PriorityQueue<>();

		//PART 1
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] != 0) {
				if (i == 26) {
					pq.offer(new Node(' ', counts[i]));

				} else {
					pq.offer(new Node((char) (i + 65), counts[i]));
				}
			}
		}

		//PART 2
		while (pq.size() >= 2) {
			Node node1 = pq.poll();
			Node node2 = pq.poll();

			Node out = new Node('*', node1.frequency + node2.frequency);
			out.left = node1;
			out.right = node2;

			pq.offer(out);
		}
		return pq.poll();
	}

	// This method is given a Huffman Tree as input, and
	// returns a String array representing the binary encoding
	// for each letter in the Huffman Tree.
	// The space character (if present) is located at index 26
	// of the array.
	public static String[] createCode(Node root) {
		String[] code = new String[27];
		createCodeRec(root, "", code);
		return code;
	}

	// Recursive helper method to create the code
	public static void createCodeRec(Node root, String path, String[] table) {
		if (root == null) {
			return;
		}

		if (root.ch != '*') {
			if (root.ch == ' ') {
				table[26] = path;
			} else {
				table[root.ch - 'A'] = path;
			}
		}
		createCodeRec(root.left, path + "0", table);
		createCodeRec(root.right, path + "1", table);
	}


	// Given a message String and a Huffman code, returns the
	// message encoded according to the Huffman code. For
	// convenience, encoded letters are separated by spaces.
	public static String encode(String message, String[] code) {
		var list = message.toCharArray();
		StringBuilder out = new StringBuilder();
		for (char c : list) {
			if (c == ' ') {
				out.append(code[26]);
			} else {
				out.append(code[c - 65]);
			}
			out.append(" ");
		}
		return out.toString();
	}

	// Given a String of 0's and 1's and a Huffman code,
	// decodes the String into the original message String
	// Note that the binary representations of letters is
	// space-separated, which should make this conversion easier
	public static String decode(String bin, String[] code) {
		var l = bin.split(" ");
		StringBuilder out = new StringBuilder();
		for (String s : l) {
			for (int i = 0; i < code.length; i++) {
				if (s.equals(code[i])) {
					if (i == 26) {
						out.append(" ");
					} else {
						char c = (char) (i + 65);
						out.append(c);
					}
				}
			}
		}
		return out.toString();
	}


	public static int calculateSavings(String message){
		int[] counts = countChars(message);
		Node root = createHuffmanTree(counts);
		String[] code = createCode(root);
		String bin = encode(message, code);
		bin = bin.replaceAll(" ", "");
		return message.length()* 8 - bin.length();
	}

	// Method to print out counts for each letter
	public static void printCounts(int[] counts) {
		if (counts != null) {
			for (int i = 0; i < 26; i++) {
				if (counts[i] != 0) {
					System.out.println((char) (i + 'A') + ": " + counts[i]);
				}
			}
			if (counts[26] != 0) {
				System.out.println(" : " + counts[26]);
			}
		}
	}

	// Method to print out the corresponding binary string for
	// each letter, as specified by the code
	public static void printCode(String[] code) {
		if (code != null) {
			for (int i = 0; i < 26; i++) {
				if (code[i] != null) {
					System.out.println((char) (i + 'A') + ": " + code[i]);
				}
			}

			if (code[26] != null) {
				System.out.println(" : " + code[26]);
			}
		}
	}

	// Tree printing code borrowed from: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram
	public String toString() {
		StringBuilder buffer = new StringBuilder(50);
		print(buffer, "", "");
		return buffer.toString();
	}

	private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
		buffer.append(prefix);
		buffer.append(ch).append(": ").append(frequency);
		buffer.append('\n');

		if (left != null && right != null) {
			left.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
			right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
		} else if (left != null) {
			left.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
		} else if (right != null) {
			right.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
		}
	}

	public static void main(String[] args) {
		// Test 1: countChars
		System.out.println("Test 1: countChars");
		String message1 = "BEEKEEPERS KEEP BEES";
		int[] counts1 = countChars(message1);
		printCounts(counts1);
		// B: 2
		// E: 9
		// K: 2
		// P: 2
		// R: 1
		// S: 2
		//  : 2

		System.out.println();
		String message2 = "SHE SELLS SEA SHELLS ON THE SEA SHORE";
		int[] counts2 = countChars(message2);
		printCounts(counts2);
		// A: 2
		// E: 7
		// H: 4
		// L: 4
		// N: 1
		// O: 2
		// R: 1
		// S: 8
		// T: 1
		//  : 7


		// Test 2: createHuffmanTree
		System.out.println("\nTest 2: createHuffmanTree");
		Node root1 = createHuffmanTree(counts1);
		System.out.println(root1);
		// *: 20
		// ├── E: 9
		// └── *: 11
		//     ├── *: 4
		//     │   ├── S: 2
		//     │   └── B: 2
		//     └── *: 7
		//         ├── *: 3
		//         │   ├── R: 1
		//         │   └──  : 2
		//         └── *: 4
		//             ├── P: 2
		//             └── K: 2

		String[] code1 = createCode(root1);
		printCode(code1);
		// B: 101
		// E: 0
		// K: 1111
		// P: 1110
		// R: 1100
		// S: 100
		//  : 1101

		System.out.println();
		Node root2 = createHuffmanTree(counts2);
		System.out.println(root2);
		// *: 37
		// ├── *: 15
		// │   ├── *: 7
		// │   │   ├── *: 3
		// │   │   │   ├── R: 1
		// │   │   │   └── A: 2
		// │   │   └── *: 4
		// │   │       ├── *: 2
		// │   │       │   ├── N: 1
		// │   │       │   └── T: 1
		// │   │       └── O: 2
		// │   └── *: 8
		// │       ├── L: 4
		// │       └── H: 4
		// └── *: 22
		//     ├── S: 8
		//     └── *: 14
		//         ├──  : 7
		//         └── E: 7

		String[] code2 = createCode(root2);
		printCode(code2);
		// A: 0001
		// E: 111
		// H: 011
		// L: 010
		// N: 00100
		// O: 0011
		// R: 0000
		// S: 10
		// T: 00101
		//  : 110

		// Test 3: encode
		System.out.println("\nTest 3: encode");

		String bin1 = encode(message1, code1);
		System.out.println(bin1);
		// 101 0 0 1111 0 0 1110 0 1100 100 1101 1111 0 0 1110 1101 101 0 0 100

		String bin2 = encode(message2, code2);
		System.out.println(bin2);
		// 10 011 111 110 10 111 010 010 10 110 10 111 0001 110 10 011 111 010 010 10 110 0011 00100 110 00101 011 111 110 10 111 0001 110 10 011 0011 0000 111

		// Test 4: decode
		System.out.println("\nTest 4: decode");

		String decodeMessage1 = decode(bin1, code1);
		System.out.println(decodeMessage1);
		// BEEKEEPERS KEEP BEES

		String decodeMessage2 = decode(bin2, code2);
		System.out.println(decodeMessage2);
		// SHE SELLS SEA SHELLS ON THE SEA SHORE

		// Test 5: everything
		System.out.println("\nTest 5: everything");
		String message3 = "TURKEY TROTS TO WATER THE WORLD WONDERS";

		int[] counts3 = countChars(message3);
		Node root3 = createHuffmanTree(counts3);
		String[] code3 = createCode(root3);
		String bin3 = encode(message3, code3);
		System.out.println(decode(bin3, code3));
		// TURKEY TROTS TO WATER THE WORLD WONDERS

		// Test 6: Calculate Savings
		System.out.println("\nTest 6: Calculate Savings");
		int savings1 = calculateSavings(message1);
		System.out.println(calculateSavings(message1));
		// 111
		double percent1 = (double) savings1 / (message1.length() * 8) * 100;
		System.out.println("The file would be " + percent1 + "% smaller using our compression");
		// The file would be 69.375% smaller using our compression

		int savings2 = calculateSavings(message2);
		System.out.println(calculateSavings(message2));
		// 184
		double percent2 = (double) savings2 / (message2.length() * 8) * 100;
		System.out.println("The file would be " + percent2 + "% smaller using our compression");
		// The file would be 62.16216216216216% smaller using our compression

		int savings3 = calculateSavings(message3);
		System.out.println(calculateSavings(message3));
		// 172
		double percent3 = (double) savings3 / (message3.length() * 8) * 100;
		System.out.println("The file would be " + percent3 + "% smaller using our compression");
		// The file would be 55.12820512820513% smaller using our compression
	}
}


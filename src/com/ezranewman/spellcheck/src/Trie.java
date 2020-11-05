package com.ezranewman.spellcheck.src;

public class Trie {
	TrieNode root;

	public Trie() {
		root = new TrieNode();
	}

	// Adds str to the Trie
	public void add(String str) {
		TrieNode working = root;
		for (char c : str.toCharArray()) {
			if(working.children[c-'a'] == null){
				working.children[c-'a'] = new TrieNode();
			}
			working = working.children[c-'a'];
		}
		working.isWord = true;
	}

	// Searches the Trie for str
	public boolean contains(String str) {
		TrieNode working = root;
		for (char c : str.toCharArray()) {
			if(working.children[c-'a'] == null){
				return false;
			}
			working = working.children[c-'a'];
		}
		return working.isWord;
	}

	// Deletes str from the Trie
	public void remove(String str) {
		TrieNode working = root;
		for (char c : str.toCharArray()) {
			if(working.children[c-'a'] == null){
				return;
			}
			working = working.children[c-'a'];
		}
		working.isWord = false;
	}

	// Prints out Trie; just calls recusive function
	public void print() {
		printRec(this.root, "");
	}

	// Recursively prints out the trie
	public void printRec(TrieNode current, String str) {
		if(current.isWord){
			System.out.println(str);
		}
		for (char i = 0; i < current.children.length; i++) {
			if (current.children[i] == null)
				continue;

			printRec(current.children[i], str + Character.toString(i+'a'));
		}
	}

	// Autocompletes based on the words in the Trie;
	// prints out all words that start with str
	public void autoComplete(String str) {
		return;
	}

	public static void main(String[] args) {
		Trie test = new Trie();

		test.add("hi");
		test.add("world");
		test.add("his");
		test.add("history");
		test.add("hiss");

		System.out.println("Test 1:");
		test.print();
		System.out.println();
		// Test 1:
		// hi
		// his
		// hiss
		// history
		// world

		System.out.println("Test 2:");
		test.autoComplete("hi");
		System.out.println();
		// Test 2:
		// hi
		// his
		// hiss
		// history

		System.out.println("Test 3:");
		test.remove("his");
		test.print();
		System.out.println();
		// Test 3:
		// hi
		// hiss
		// history
		// world

		System.out.println("Test 4:");
		test.autoComplete("hi");
		System.out.println();
		// Test 4:
		// hi
		// hiss
		// history

        test.add("hello");
        test.add("world");

        System.out.println(test.contains("hello"));
        System.out.println(test.contains("world"));
        System.out.println(test.contains("worlds"));

        test.add("worlds");

        System.out.println(test.contains("hello"));
        System.out.println(test.contains("world"));
        System.out.println(test.contains("worlds"));
	}
}

class TrieNode {
	TrieNode[] children;
	boolean isWord;

	public TrieNode() {
		children = new TrieNode[26];
		isWord = false;
	}
}
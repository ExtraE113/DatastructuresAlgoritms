package com.ezranewman.datastructures.Markov;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MarkovTextGenerator {
	HashMap<String, LinkedList<String>> table;
	List<String> starts;

	public MarkovTextGenerator() {
		table = new HashMap<>();
		starts = new ArrayList<>();
	}


	// Trains the Markov Text Generator
	// Given the filename for the file that the training will be based on
	public void train(String filename) {
		try {
			File dict = new File("src/com/ezranewman/Markov/" + filename);
			Scanner scanner = new Scanner(dict);
			HashMap<String, LinkedList<String>> trainingTable = table;
			while (scanner.hasNext()) {
				String[] line = scanner.nextLine().split(" ");
				starts.add(line[0]);
				for (int i = 0; i < line.length; i++) {
					LinkedList<String> options;
					if (trainingTable.containsKey(line[i])) {
						options = trainingTable.get(line[i]);
					} else {
						options = new LinkedList<>();
						trainingTable.put(line[i], options);
					}

					try {
						options.add(line[i + 1]);
					} catch (ArrayIndexOutOfBoundsException e) {
						//last element
						options.add(line[0]);

					}
				}
			}


		} catch (FileNotFoundException e) {
			System.err.println("Well, that can't be good ¯\\_(ツ)_/¯"); //It's important that your chain has some personality.
			e.printStackTrace();
		}
	}

	// Generates text based on the training
	// Given the number of words to generate
	public String generateText(int numWords) {
		Random r = new Random();
		// we've kept track of what elements commonly start our linked lists so we can pick one of those
		// using a linked list instead of a string makes it easier to expand to train from multiple files
		StringBuilder out = new StringBuilder(starts.get(r.nextInt(starts.size())));
		String current = out.toString();
		for (int i = 0; i < numWords; i++) {
			List<String> currentRow;
			currentRow = table.get(current);
			current = currentRow.get(r.nextInt(currentRow.size()));
			out.append(" ").append(current);
		}
		return out.toString();
	}

	// Prints out the table, nicely
	@Override
	public String toString() {
		StringBuilder strBuilder = new StringBuilder();

		for (String key : table.keySet()) {
			strBuilder.append(key).append(": ");

			LinkedList<String> list = table.get(key);

			for (String word : list) {
				strBuilder.append(word).append(" ");
			}

			strBuilder.append("\n");
		}

		return strBuilder.toString();
	}

	// Retrains the MTG (this will delete the existing data
	// before training it with new data)
	public void retrain(String filename) throws FileNotFoundException {
		table.clear();
		starts = new ArrayList<>();
		train(filename);
	}

	public static void main(String[] args) throws FileNotFoundException {
		// Test 1: Train Method
		System.out.println("Test 1:");
		MarkovTextGenerator gen = new MarkovTextGenerator();
		gen.train("texts/sample.txt");
		System.out.println(gen);
		// a: bar pie
		// Here: is is
		// bar: chart
		// of: my my
		// is: a a
		// my: favorite favorite
		// pies.: Here
		// bars.: Here
		// chart: of of
		// favorite: pies. bars.
		// pie: chart

		// Test 2: Generate Method (note that your response won't match exactly)
		System.out.println("Test 2:");
		System.out.println(gen.generateText(100));
		// Here is a bar chart of my favorite pies. Here is a pie chart
		// of my favorite pies. Here is a bar chart of my favorite
		// bars. Here is a pie chart of my favorite bars. Here is a
		// bar chart of my favorite pies. Here is a bar chart of my
		// favorite bars. Here is a pie chart of my favorite bars.
		// Here is a pie chart of my favorite pies. Here is a bar
		// chart of my favorite pies. Here is a bar chart of my
		// favorite pies. Here is a bar chart of my favorite bars.

		// Test 3: Test out your code!
		System.out.println("\nTest 3:");
		gen.retrain("texts/DrSeuss.txt");
		System.out.println(gen.generateText(1000));


		// Test 4: news articles
		System.out.println();
		System.out.println("Test 4");
		gen = new MarkovTextGenerator();
		gen.train("texts/abcnews-date-text.csv");

		for (int i = 0; i < 20; i++) {
			System.out.println(gen.generateText(10));
		}

		//test 6 trump
		System.out.println();
		System.out.println("test 5");
		gen = new MarkovTextGenerator();
		gen.retrain("texts/trumptweets.csv");

		for (int i = 0; i < 20; i++) {
			System.out.println(gen.generateText(10));
		}

	}

}
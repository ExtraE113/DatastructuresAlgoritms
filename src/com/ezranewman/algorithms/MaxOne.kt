package com.ezranewman.algorithms

import kotlin.random.Random


class MaxOne() {
	private val POPULATION_SIZE = 1000
	private var population = Array(POPULATION_SIZE) {
		Individual()
	}
	
	fun runGA() {
		// Initialize population
		//populateInitial() //already done when class created
		printPopulation(0)
		
		// We will run generation 1 in more detail, so we can
		// test that each method is working properly
		
		// Use tournament selection to select parents
		val parentsOne = selectParents()
		println("Generation 1 Parents")
		printArray(parentsOne)
		
		// Generate the next generation using crossover
		var nextGenerationOne = crossover(parentsOne)
		println("Generation 1 Children, crossover")
		printArray(nextGenerationOne)
		
		// Edit the next generation using mutation
		nextGenerationOne = mutation(nextGenerationOne)
		println("Generation 1 Children, mutation")
		printArray(nextGenerationOne)
		
		// Replace the current generation with the new generation;
		population = nextGenerationOne
		
		// Uncomment this when you are ready to fully test your code
		val NUM_GENERATIONS = 200000
		for (i in 0..NUM_GENERATIONS) {
			val parents = selectParents()
			var nextGeneration = crossover(parents)
			nextGeneration = mutation(nextGeneration)

			population = nextGeneration
//			printPopulation(i)
		}
		printPopulation(NUM_GENERATIONS)
	}
	
	// Returns a new array of individuals for the chosen
	// parents
	// We will be using tournament selection to select
	// each parent
	// * We choose two random parents, and then add the
	//   fitter parent to the parents array
	fun selectParents(): Array<Individual> {
		return Array(POPULATION_SIZE) {
			val p1 = population[Random.nextInt(POPULATION_SIZE)]
			val p2 = population[Random.nextInt(POPULATION_SIZE)]
			if (p1.calculateFitness() > p2.calculateFitness()) p1 else p2
		}
	}
	
	// Returns an array of offspring created by crossover
	// We will be creating pairs of children using single-point
	// crossover.
	// * Given some random index i, child1 will inherit genes
	//   0, 1, ... i-1 from parent1 and genes i, i+1, ... 7 from parent2
	// * child2 will inherit genes 0, 1, ... i-1 from parent2 and genes
	//   i, i+1, ... 7 from parent1
	// * We use parents 1 and 2 to generate children 1 and 2, and parents
	//   3 and 4 to generate children 3 and 4, and so on...
	fun crossover(parents: Array<Individual>): Array<Individual> {
		var out = Array(POPULATION_SIZE) { Individual() }
		for (i in 0 until POPULATION_SIZE step 2) {
//			//child 1
//			var temp = Individual()
//			temp.genes = IntArray(8) { -1 }
//			val rand = Random.nextInt(0, temp.genes.size)
//			parents[i].genes.copyInto(temp.genes, 0, 0, rand)
//			parents[i + 1].genes.copyInto(temp.genes, rand, rand, temp.genes.size)
//			out[i] = temp
//
//			//child 2
//			temp = Individual()
//			temp.genes = IntArray(8) { -1 }
//			parents[i + 1].genes.copyInto(temp.genes, 0, 0, rand)
//			parents[i].genes.copyInto(temp.genes, rand, rand, temp.genes.size)
//			out[i+1] = temp
			
			val temp1 = Individual()
			temp1.genes = IntArray(temp1.genes.size) { -1 }
			val temp2 = Individual()
			temp2.genes = IntArray(temp1.genes.size) { -1 }
			
			val index = Random.nextInt(temp1.genes.size)
			
			for (j in temp1.genes.indices) {
				if (j < index) {
					temp1.genes[j] = parents[i].genes[j]
					temp2.genes[j] = parents[i + 1].genes[j]
				} else {
					temp1.genes[j] = parents[i + 1].genes[j]
					temp2.genes[j] = parents[i].genes[j]
				}
			}
			out[i] = temp1
			out[i+1] = temp2
		}
		return out
//		return out
	}
	
	// Given an array containing the next generation,
	// iterate through all individuals and all genes,
	// and flip a bit with a 10% chance
	fun mutation(nextGeneration: Array<Individual>): Array<Individual> {
		for (i in nextGeneration.indices) {
			for (j in nextGeneration[i].genes.indices) {
				if (Random.nextInt(10) == 5) {
					if (nextGeneration[i].genes[j] == 0) {
						nextGeneration[i].genes[j] = 1
					} else {
						nextGeneration[i].genes[j] = 0
					}
				}
			}
		}
		return nextGeneration
	}
	
	// Print out the current population/generation
	fun printPopulation(generationNumber: Int) {
		println("Generation #$generationNumber:")
		printArray(population)
	}
	
	// This class defines each individual as having
	// an array for its genes
	// The class also includes a method to calculate
	// an individuals fitness
	class Individual() {
		var genes: IntArray = IntArray(8)
		
		// Calculate fitness by adding up all of the 1's
		fun calculateFitness(): Int {
			var fitness = 0
			for (gene: Int in genes) {
				fitness += gene
			}
			return fitness
		}
		
		// Create an individual with 8
		// random genes
		init {
			for (i in genes.indices) {
				genes[i] = Random.nextInt(2)
			}
		}
	}
	
	// Prints out information about an Individual array
	fun printArray(arr: Array<Individual>) {
		var totalFitness = 0
		var maxFitness = 0
		for (i in arr.indices) {
			val ind = arr[i]
			val fitness = ind.calculateFitness()
			println(
				"Individual " + i + ": " + ind.genes.contentToString() +
						", fitness: " + fitness
			)
			totalFitness += fitness
			maxFitness = Math.max(fitness, maxFitness)
		}
		println("Total Fitness: $totalFitness, Max Fitness: $maxFitness")
		println()
	}
}


fun main(args: Array<String>) {
	val m = MaxOne()
	m.runGA()
}
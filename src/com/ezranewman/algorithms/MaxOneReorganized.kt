package com.ezranewman.algorithms

import java.lang.Integer.max
import java.lang.Integer.min
import java.util.*


class MaxOneReorganized() {
	var population: Population? = null
	fun runGA(): Int {
		// Initialize population
		population = Population(POPULATION_SIZE)
		printPopulation(0)
		var generationNum = 0
		while (population!!.maxFitness < GENE_SIZE) {
			generationNum++
			
			// Carry out selection: We have 3 choices here!
			val parents = selectParentsTournament()
//            val parents = selectParentsRoulette()
//			val parents = selectParentsRank()
			var nextGeneration = uniformCrossover(parents)
			nextGeneration = mutation(nextGeneration)
			
			// Optional: Carry out elitism
//            nextGeneration = elitism(nextGeneration)
			population = nextGeneration
		}
		printPopulation(generationNum)
		return generationNum
	}
	
	// Returns a new array of individuals for the chosen
	// parents
	// We will be using tournament selection to select
	// each parent
	// * We choose two random parents, and then add the
	//   fitter parent to the parents array
	fun selectParentsTournament(): Population {
		val parents = Population(POPULATION_SIZE)
		val rng = Random()
		for (i in 0 until POPULATION_SIZE) {
			val candidate1 = population!!.individuals[rng.nextInt(POPULATION_SIZE)]
			val candidate2 = population!!.individuals[rng.nextInt(POPULATION_SIZE)]
			if (candidate1!!.calculateFitness() > candidate2!!.calculateFitness()) {
				parents.individuals[i] = candidate1
			} else {
				parents.individuals[i] = candidate2
			}
		}
		return parents
	}
	
	// Returns a new array of individuals for the chosen
	// parents
	// We will be using *roulette* selection to select
	// each parent
	fun selectParentsRoulette(): Population {
		val total = population!!.totalFitness
		val rng = Random()
		val parents = Population(POPULATION_SIZE)
		for (i in 0 until POPULATION_SIZE) {
			val x = rng.nextInt(total)
			var partialSum = 0
			for (j in 0 until POPULATION_SIZE) {
				val ind = population!!.individuals[j]
				partialSum += ind!!.calculateFitness()
				if (partialSum > x) {
					parents.individuals[i] = ind
					break
				}
			}
		}
		return parents
	}
	
	// Returns a new array of individuals for the chosen
	// parents
	// We will be using *rank* selection to select
	// each parent
	fun selectParentsRank(): Population {
		val rng = Random()
		
		// Sum up total
		val total = POPULATION_SIZE * (POPULATION_SIZE + 1) / 2
		
		// Create parents array
		val parents = Population(POPULATION_SIZE)
		
		// We'll need a sorted array of individuals for rank selection
		val sorted = population!!.individuals.clone()
		
		// Sort in reverse order
		Arrays.sort(sorted)
		
		// This table will store the rank values assigned to each individual
		val table = IntArray(POPULATION_SIZE)
		for (i in 0 until POPULATION_SIZE) {
			table[sorted[i]!!.index] = i + 1
		}
		for (i in 0 until POPULATION_SIZE) {
			val x = rng.nextInt(total)
			var partialSum = 0
			for (j in 0 until POPULATION_SIZE) {
				partialSum += table[j]
				if (partialSum > x) {
					parents.individuals[i] = sorted[j]
					break
				}
			}
		}
		return parents
	}
	
	fun elitism(nextGeneration: Population): Population {
		nextGeneration.individuals[POPULATION_SIZE - 2] = population!!.fittest
		nextGeneration.individuals[POPULATION_SIZE - 1] = population!!.secondFittest
		return nextGeneration
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
	fun singlePointCrossover(parents: Population): Population {
		val nextGeneration = Population(POPULATION_SIZE)
		val rng = Random()
		for (i in 0 until POPULATION_SIZE / 2) {
			val parent1 = parents.individuals[i * 2]
			val parent2 = parents.individuals[i * 2 + 1]
			val crossoverPoint = rng.nextInt(parent1!!.genes.size)
			val child1: Individual = Individual(i * 2)
			val child2: Individual = Individual(i * 2 + 1)
			for (j in 0 until crossoverPoint) {
				child1.genes[j] = parent1.genes[j]
				child2.genes[j] = parent2!!.genes[j]
			}
			for (j in crossoverPoint until parent1.genes.size) {
				child1.genes[j] = parent2!!.genes[j]
				child2.genes[j] = parent1.genes[j]
			}
			nextGeneration.individuals[i * 2] = child1
			nextGeneration.individuals[i * 2 + 1] = child2
		}
		return nextGeneration
	}
	
	
	fun twoPointCrossover(parents: Population): Population {
		val nextGeneration = Population(POPULATION_SIZE)
		val rng = Random()
		for (i in 0 until POPULATION_SIZE / 2) {
			val parent1 = parents.individuals[i * 2]
			val parent2 = parents.individuals[i * 2 + 1]
			var crossoverPoint = rng.nextInt(parent1!!.genes.size)
			var crossoverPoint2 = rng.nextInt(parent1.genes.size)
			val temp = crossoverPoint
			crossoverPoint = max(crossoverPoint, crossoverPoint2)
			crossoverPoint2 = min(temp, crossoverPoint2)
			val child1: Individual = Individual(i * 2)
			val child2: Individual = Individual(i * 2 + 1)
			for (j in 0 until crossoverPoint2) {
				child1.genes[j] = parent1.genes[j]
				child2.genes[j] = parent2!!.genes[j]
			}
			for (j in 0 until crossoverPoint2) {
				child1.genes[j] = parent2!!.genes[j]
				child2.genes[j] = parent1.genes[j]
			}
			
			for (j in crossoverPoint2 until crossoverPoint) {
				child1.genes[j] = parent1.genes[j]
				child2.genes[j] = parent2!!.genes[j]
			}
			for (j in crossoverPoint2 until crossoverPoint) {
				child1.genes[j] = parent2!!.genes[j]
				child2.genes[j] = parent1.genes[j]
			}
			
			for (j in crossoverPoint until parent1.genes.size) {
				child1.genes[j] = parent1.genes[j]
				child2.genes[j] = parent2!!.genes[j]
			}
			for (j in crossoverPoint2 until parent1.genes.size) {
				child1.genes[j] = parent2!!.genes[j]
				child2.genes[j] = parent1.genes[j]
			}
			nextGeneration.individuals[i * 2] = child1
			nextGeneration.individuals[i * 2 + 1] = child2
		}
		return nextGeneration
	}
	
	fun uniformCrossover (parents: Population): Population {
		val nextGeneration = Population(POPULATION_SIZE)
		val rng = Random()
		for (i in 0 until POPULATION_SIZE / 2) {
			val parent1 = parents.individuals[i * 2]
			val parent2 = parents.individuals[i * 2 + 1]
			val child1: Individual = Individual(i * 2)
			val child2: Individual = Individual(i * 2 + 1)
			
			for (j in parent1!!.genes.indices) {
				if(rng.nextBoolean()) {
					child1.genes[j] = parent1.genes[j]
					child2.genes[j] = parent2!!.genes[j]
				} else {
					child1.genes[j] = parent2!!.genes[j]
					child2.genes[j] = parent1.genes[j]
				}
			}
			nextGeneration.individuals[i * 2] = child1
			nextGeneration.individuals[i * 2 + 1] = child2
		}
		return nextGeneration
	}
	
	
	// Given an array containing the next generation,
	// iterate through all individuals and all genes,
	// and flip a bit with a 10% chance
	fun mutation(nextGeneration: Population): Population {
		val rng = Random()
		for (i in 0 until POPULATION_SIZE) {
			val ind = nextGeneration.individuals[i]
			for (j in ind!!.genes.indices) {
				val flip = rng.nextInt(10)
				if (flip == 0) {
					ind.genes[j] += 1
					ind.genes[j] %= 2
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
	inner class Individual(var index: Int): Comparable<Individual> {
		var genes: IntArray
		
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
			genes = IntArray(GENE_SIZE)
			val rng = Random()
			for (i in genes.indices) {
				genes[i] = rng.nextInt(2)
			}
		}
		
		override fun compareTo(ind1: Individual): Int {
			return -1 * (ind1.calculateFitness() - this.calculateFitness())
		}
	}
	
	inner class Population(var size: Int) {
		var individuals: Array<Individual?>
		val totalFitness: Int
			get() {
				var totalFitness = 0
				for (i in individuals.indices) {
					val ind = individuals[i]
					val fitness = ind!!.calculateFitness()
					totalFitness += fitness
				}
				return totalFitness
			}
		val maxFitness: Int
			get() {
				var maxFitness = 0
				for (i in individuals.indices) {
					val ind = individuals[i]
					val fitness = ind!!.calculateFitness()
					maxFitness = Math.max(fitness, maxFitness)
				}
				return maxFitness
			}
		
		//Get the fittest individual
		val fittest: Individual?
			get() {
				var maxFit = Int.MIN_VALUE
				var maxFitIndex = 0
				for (i in individuals.indices) {
					if (maxFit <= individuals[i]!!.calculateFitness()) {
						maxFit = individuals[i]!!.calculateFitness()
						maxFitIndex = i
					}
				}
				return individuals[maxFitIndex]
			}
		
		//Get the second most fittest individual
		val secondFittest: Individual?
			get() {
				var maxFit = Int.MIN_VALUE
				var maxFitIndex = 0
				for (i in individuals.indices) {
					if (maxFit <= individuals[i]!!.calculateFitness()) {
						maxFit = individuals[i]!!.calculateFitness()
						maxFitIndex = i
					}
				}
				var secondMaxFit = Int.MIN_VALUE
				var secondMaxFitIndex = 0
				for (i in individuals.indices) {
					if (secondMaxFit <= individuals[i]!!.calculateFitness() &&
						i != maxFitIndex
					) {
						secondMaxFit = individuals[i]!!.calculateFitness()
						secondMaxFitIndex = i
					}
				}
				return individuals[secondMaxFitIndex]
			}
		
		init {
			individuals = arrayOfNulls(size)
			
			// The initial population code is here now
			for (i in 0 until size) {
				individuals[i] = Individual(i)
			}
		}
	}
	
	companion object {
		var POPULATION_SIZE = 10
		var GENE_SIZE = 20
		
		// Prints out information about a Population
		fun printArray(population: Population?) {
			val arr = population!!.individuals
			for (i in arr.indices) {
				val ind = arr[i]
				val fitness = ind!!.calculateFitness()
				println(
					"Individual " + i + ": " + Arrays.toString(ind.genes) +
							", fitness: " + fitness
				)
			}
			println(
				("Total Fitness: " + population.totalFitness +
						", Max Fitness: " + population.maxFitness)
			)
			println()
		}
		
		
	}
}
fun main(args: Array<String>) {
	val m = MaxOneReorganized()
	var total = 0
	for (i in 0..1000)
		total += m.runGA()
	println(total/1000.0)
}
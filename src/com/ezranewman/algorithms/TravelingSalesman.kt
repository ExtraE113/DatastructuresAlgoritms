package com.ezranewman.algorithms

import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


class TravelingSalesman() {
	var population: Population? = null
	var graph: CompleteGraph
	fun runGA() {
		// Initialize population
		population = Population(POPULATION_SIZE)
		printPopulation(0)
		val NUM_GENERATIONS = 2000000
		for (i in 1 until NUM_GENERATIONS) {
			val parents = selectParentsTournament()
			var nextGeneration: Population? = orderedCrossover(parents)
			nextGeneration = mutation(nextGeneration)
			population = nextGeneration
			printPopulation(i)
		}
	}
	
	// Returns a new array of individuals for the chosen
	// parents
	fun selectParentsTournament(): Population {
		val parents: Population = Population(POPULATION_SIZE)
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
	
	fun elitism(nextGeneration: Population): Population {
		nextGeneration.individuals[POPULATION_SIZE - 2] = population!!.fittest
		nextGeneration.individuals[POPULATION_SIZE - 1] = population!!.secondFittest
		return nextGeneration
	}
	
	// Included for reference. Don't use this!
	fun twoPointCrossover(parents: Population): Population {
		val nextGeneration: Population = Population(POPULATION_SIZE)
		val rng = Random()
		for (i in 0 until POPULATION_SIZE / 2) {
			val parent1 = parents.individuals[i * 2]
			val parent2 = parents.individuals[i * 2 + 1]
			var crossoverPoint1 = rng.nextInt(GENE_SIZE)
			var crossoverPoint2 = rng.nextInt(GENE_SIZE)
			
			// We'd like crossoverPoint1 to be the smaller index
			if (crossoverPoint1 > crossoverPoint2) {
				val temp = crossoverPoint1
				crossoverPoint1 = crossoverPoint2
				crossoverPoint2 = temp
			}
			val child1: Individual = Individual()
			val child2: Individual = Individual()
			
			// Copy all genes before crossoverPoint1
			for (j in 0 until crossoverPoint1) {
				child1.genes[j] = parent1!!.genes[j]
				child2.genes[j] = parent2!!.genes[j]
			}
			
			// Copy all genes between the crossoverPoints
			for (j in crossoverPoint1 until crossoverPoint2) {
				child1.genes[j] = parent2!!.genes[j]
				child2.genes[j] = parent1!!.genes[j]
			}
			
			// Copy all genes after crossoverPoint2
			for (j in crossoverPoint2 until GENE_SIZE) {
				child1.genes[j] = parent1!!.genes[j]
				child2.genes[j] = parent2!!.genes[j]
			}
			nextGeneration.individuals[i * 2] = child1
			nextGeneration.individuals[i * 2 + 1] = child2
		}
		return nextGeneration
	}
	
	// Returns an array of offspring created by crossover
	fun orderedCrossover(parents: Population?): Population {
		val rng = Random()
		val nextGeneration: Population = Population(POPULATION_SIZE)
		
		fun performCrossover(parent1: Individual, parent2: Individual): Individual {
			var crossoverPoint1 = rng.nextInt(GENE_SIZE)
			var crossoverPoint2 = rng.nextInt(GENE_SIZE)
			
			// We'd like crossoverPoint1 to be the smaller index
			if (crossoverPoint1 > crossoverPoint2) {
				val temp = crossoverPoint1
				crossoverPoint1 = crossoverPoint2
				crossoverPoint2 = temp
			}
			val child1: Individual = Individual()
			val used = HashSet<String>(crossoverPoint2-crossoverPoint1)
			
			// Copy all genes between the crossoverPoints
			for (j in crossoverPoint1 until crossoverPoint2) {
				child1.genes[j] = parent2!!.genes[j]
				used.add(parent2.genes[j]!!)
			}
			var j = 0
			var k = 0
			while (j < child1.genes.size){
				if(j == crossoverPoint1)
					j = crossoverPoint2
				
				if (!used.contains(parent1!!.genes[k])){
					child1.genes[j] = parent1.genes[k]
					j++
				}
				k++
			}
			return child1
		}
		
		
		for (i in 0 until POPULATION_SIZE / 2) {
			val parent1 = parents!!.individuals[i * 2]
			val parent2 = parents.individuals[i * 2 + 1]
			nextGeneration.individuals[i * 2] = performCrossover(parent1!!, parent2!!)
			nextGeneration.individuals[i * 2 + 1] = performCrossover(parent1, parent2)
		}
		return nextGeneration
	}
	
	// Given an array containing the next generation,
	// iterate through all individuals and all genes,
	// and flip a bit with a 10% chance
	fun mutation(nextGeneration: Population?): Population {
		val rng = Random()
		for (i in nextGeneration!!.individuals){
			for ((index, j) in i!!.genes.withIndex()){
				if(rng.nextInt(10) == 5) {
					var swapTo = rng.nextInt(i.genes.size)
					i.genes[index] = i.genes[swapTo]
					i.genes[swapTo] = j
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
	inner class Individual() {
		var genes: Array<String?>
		
		// Calculate fitness by adding up all of the 1's
		fun calculateFitness(): Int {
			var fitness = 0
			for (i in 0 until GENE_SIZE - 1) {
				val w = graph.getEdge(genes[i], genes[i + 1])
				fitness += w!!.weight
			}
			val w = graph.getEdge(genes[0], genes[GENE_SIZE - 1])
			fitness += w!!.weight
			return -1 * fitness
		}
		
		// Create an individual with 8
		// random genes
		init {
			genes = arrayOfNulls(GENE_SIZE)
			val rng = Random()
			for (i in 0 until GENE_SIZE) {
				genes[i] = ('A'.toInt() + i).toChar().toString()+""
			}
			
			// Shuffle by swapping many times
			for (i in 0 until GENE_SIZE) {
				val idx1 = rng.nextInt(GENE_SIZE)
				val idx2 = rng.nextInt(GENE_SIZE)
				val temp = genes[idx1]
				genes[idx1] = genes[idx2]
				genes[idx2] = temp
			}
		}
	}
	
	inner class IndividualComparator() : Comparator<Individual> {
		override fun compare(ind1: Individual, ind2: Individual): Int {
			return -1 * (ind1.calculateFitness() - ind2.calculateFitness())
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
				var maxFitness = Int.MIN_VALUE
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
				individuals[i] = Individual()
			}
		}
	}
	
	companion object {
		var POPULATION_SIZE = 10
		var GENE_SIZE = 6
		@JvmStatic
		fun main(args: Array<String>) {
			val t = TravelingSalesman()
			t.runGA()
		}
		
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
	
	init {
		val weights1 = intArrayOf(5, 3, 7, 2, 3, 1, 12, 2, 8, 10, 8, 9, 3, 6, 1)
		val g1 = CompleteGraph(6, weights1)
		graph = g1
	}
}

class CompleteGraph(numVertices: Int, weights: IntArray) {
	var vertices: HashMap<String, WVertex?>
	fun addVertex(label: String) {
		// Check vertex doesn't already exist before adding it
		if (!vertices.containsKey(label)) {
			val v1 = WVertex(label)
			vertices[label] = v1
		}
	}
	
	fun addEdge(label1: String?, label2: String?, weight: Int) {
		// Check vertices exist before adding an edge between them
		if (vertices.containsKey(label1) && vertices.containsKey(label2)) {
			val v1 = vertices[label1]
			val v2 = vertices[label2]
			v1!!.edges.add(WEdge(v1, v2, weight))
			v2!!.edges.add(WEdge(v2, v1, weight))
		}
	}
	
	fun removeVertex(label: String?) {
		// Check vertex exists before removing it
		if (vertices.containsKey(label)) {
			val v1 = vertices[label]
			
			// Remove all edges to this vertex
			for (edge1: WEdge in v1!!.edges) {
				val v2 = edge1.destination
				
				// Look through v2 edges for edge to this
				for (edge2: WEdge in v2!!.edges) {
					if ((edge2.destination == v1)) {
						v2.edges.remove(edge2)
					}
				}
			}
			v1.edges.clear()
			vertices.remove(label)
		}
	}
	
	fun removeEdge(label1: String?, label2: String?) {
		// Check vertices exist before removing an edge between them
		if (vertices.containsKey(label1) && vertices.containsKey(label2)) {
			val v1 = vertices[label1]
			val v2 = vertices[label2]
			for (edge1: WEdge in v1!!.edges) {
				if ((edge1.destination == v2)) {
					v1.edges.remove(edge1)
				}
			}
			for (edge2: WEdge in v2!!.edges) {
				if ((edge2.destination == v1)) {
					v2.edges.remove(edge2)
				}
			}
		}
	}
	
	fun test() {
		for (label: String? in vertices.keys) {
			println(label)
		}
		println()
		val labelArray = vertices.keys.toTypedArray()
		for (i in labelArray.indices) {
			println(labelArray[i])
		}
	}
	
	fun getEdge(label1: String?, label2: String?): WEdge? {
		val v1 = vertices[label1]
		val v2 = vertices[label2]
		for (edge1: WEdge in v1!!.edges) {
			if ((edge1.destination == v2)) {
				return edge1
			}
		}
		return null
	}
	
	fun printGraph() {
		var longest = 7
		for (str: String in vertices.keys) {
			longest = Math.max(longest, str.length + 1)
		}
		var line = "Vertex "
		for (i in 7 until longest) line += " "
		val leftLength = line.length
		line += "| Adjacent Vertices"
		println(line)
		for (i in 0 until line.length) {
			print("-")
		}
		println()
		for (str1: String in vertices.keys) {
			var str = str1
			val v1 = vertices[str]
			for (i in str.length until leftLength) {
				str += " "
			}
			print("$str| ")
			for (i in 0 until v1!!.edges.size - 1) {
				val edge1 = v1.edges[i]
				print(edge1.destination!!.label + ": " + edge1.weight + ", ")
			}
			if (!v1.edges.isEmpty()) {
				val edge1 = v1.edges[v1.edges.size - 1]
				print(edge1.destination!!.label + ": " + edge1.weight)
			}
			println()
		}
	}
	
	init {
		vertices = HashMap()
		for (i in 0 until numVertices) {
			addVertex(('A'.toInt() + i).toChar().toString() + "")
		}
		var counter = 0
		for (i in 0 until numVertices) {
			for (j in i + 1 until numVertices) {
				addEdge(
					('A'.toInt() + i).toChar().toString() + "", ('A'.toInt() + j).toChar().toString() + "",
					weights[counter++]
				)
			}
		}
	}
}

class WVertex(var label: String) {
	var edges: LinkedList<WEdge>
	
	init {
		edges = LinkedList()
	}
}

class WEdge(var source: WVertex?, var destination: WVertex?, var weight: Int) : Comparable<WEdge?> {
	override fun equals(other: Any?): Boolean {
		if (other is WEdge) {
			val otherEdge = other
			return (source == otherEdge.source) && (destination == otherEdge.destination) ||
					(source == otherEdge.destination) && (destination == otherEdge.source)
		}
		return false
	}
	
	override fun toString(): String {
		return source!!.label + "->" + destination!!.label
	}
	
	override operator fun compareTo(other: WEdge?): Int {
		return weight - other!!.weight
	}
}
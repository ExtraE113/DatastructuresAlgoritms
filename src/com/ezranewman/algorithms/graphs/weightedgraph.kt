package com.ezranewman.algorithms.graphs

import java.util.*
import kotlin.collections.HashMap


class WeightedGraph {
	internal var vertices: HashMap<String, WeightedVertex> = HashMap()
	fun addVertex(label: String) {
		// Check vertex doesn't already exist before adding it
		if (!vertices.containsKey(label)) {
			val v1 = WeightedVertex(label)
			vertices[label] = v1
		}
	}
	
	fun addEdge(label1: String, label2: String, weight: Int) {
		// Check vertices exist before adding an edge between them
		if (vertices.containsKey(label1) && vertices.containsKey(label2)) {
			val v1 = vertices[label1]!!
			val v2 = vertices[label2]!!
			v1.edges.add(WeightedEdge(v1, v2, weight))
			v2.edges.add(WeightedEdge(v2, v1, weight))
		}
	}
	
	fun removeVertex(label: String) {
		// Check vertex exists before removing it
		if (vertices.containsKey(label)) {
			val v1 = vertices[label]
			
			// Remove all edges to this vertex
			for (edge1 in v1!!.edges) {
				val v2 = edge1.destination
				
				// Look through v2 edges for edge to this
				for (edge2 in v2!!.edges) {
					if (edge2.destination == v1) {
						v2.edges.remove(edge2)
					}
				}
			}
			v1.edges.clear()
			vertices.remove(label)
		}
	}
	
	fun removeEdge(label1: String, label2: String) {
		// Check vertices exist before removing an edge between them
		if (vertices.containsKey(label1) && vertices.containsKey(label2)) {
			val v1 = vertices[label1]
			val v2 = vertices[label2]
			for (edge1 in v1!!.edges) {
				if (edge1.destination == v2) {
					v1.edges.remove(edge1)
				}
			}
			for (edge2 in v2!!.edges) {
				if (edge2.destination == v1) {
					v2.edges.remove(edge2)
				}
			}
		}
	}
	
	// This method carries out Dijkstra's algorithm
	// The algorithm is given a empty HashMap for the distances and
	// an empty HashMap for the paths
	fun dijkstra(startingId: String, distances: HashMap<String, Int>, paths: HashMap<String, String>) {
		val explored = HashMap<String, Boolean>()
		for ((key, value) in vertices) {
			distances[key] = Int.MAX_VALUE - 1
		}
		distances[startingId] = 0 //explore me first
		paths[startingId] = vertices[startingId]!!.label
		//https://stackoverflow.com/questions/21777745/finding-the-key-of-hashmap-which-holds-the-lowest-integer-value/21778704
		//there are other data structures that would make this more efficient (by not having to search them all, like a queue)
		//but this is the easiest
		fun getMinKey(map: HashMap<String, Int>): String {
			var minKey: String? = null;
			var minValue = Integer.MAX_VALUE;
			for ((key, value) in map) {
				if (value < minValue && explored[key] != true) {
					minValue = value
					minKey = key
				}
			}
			return minKey!!
		}
		
		
		fun explore(label: String) {
			vertices[label]!!.edges.forEach {
				if (explored[it.destination.label] != true) {
					if (distances[it.source.label]!! + it.weight < distances[it.destination.label]!!) {
						distances[it.destination.label] = distances[it.source.label]!! + it.weight
						paths[it.destination.label] = paths[it.source.label] + it.destination.label
					}
				}
			}
			explored[label] = true
		}
		
		while (explored.size != vertices.size) {
			explore(getMinKey(distances))
		}
		
	}
	
	private val edges: PriorityQueue<WeightedEdge>
		get() {
			val edges: PriorityQueue<WeightedEdge> = PriorityQueue()
			for (label1 in vertices.keys) {
				val v = vertices[label1]
				for (n in v!!.edges) {
					if (!edges.contains(n)) {
						edges.add(n)
					}
				}
			}
			return edges
		}
	
	
	fun prim(source: String): HashSet<WeightedEdge> {
		val starting = vertices[source]!!
		val connected = HashSet<WeightedVertex>()
		val out = HashSet<WeightedEdge>()
		connected.add(starting)
		
		val queue: PriorityQueue<WeightedEdge> = edges
		
		while (!queue.isEmpty()){
			val current = queue.poll()
			if(!connected.contains(current.destination)){
				out.add(current)
				connected.add(current.destination)
				
			}
		}
		
		return out
		
	}
	
	fun printGraph() {
		var longest = 7
		for (str in vertices.keys) {
			longest = Math.max(longest, str.length + 1)
		}
		var line = "Vertex "
		for (i in 7 until longest) line += " "
		val leftLength = line.length
		line += "| Adjacent Vertices"
		println(line)
		for (i in line.indices) {
			print("-")
		}
		println()
		for (str1 in vertices.keys) {
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
}

class WeightedVertex(var label: String) {
	var edges: LinkedList<WeightedEdge> = LinkedList()
}

class WeightedEdge(var source: WeightedVertex, var destination: WeightedVertex, var weight: Int) : Comparable<WeightedEdge> {
	override fun equals(other: Any?): Boolean {
		if (other is WeightedEdge) {
			val otherEdge = other
			return source == otherEdge.source && destination == otherEdge.destination ||
					source == otherEdge.destination && destination == otherEdge.source
		}
		return false
	}
	
	override fun toString(): String {
		return source.label + "->" + destination.label
	}
	
	override operator fun compareTo(other: WeightedEdge): Int {
		return weight - other.weight
	}
} // Optional class that you can use if you want to use a priority queue

// for Dijkstra's Algorithm
internal class VertexDistance(var label: String, var distance: Int) : Comparable<VertexDistance> {
	override fun equals(obj: Any?): Boolean {
		return when {
			obj == null -> {
				false
			}
			obj.javaClass != this.javaClass -> {
				false
			}
			else -> {
				val other = obj as VertexDistance
				label == other.label &&
						distance == other.distance
			}
		}
	}
	
	override operator fun compareTo(other: VertexDistance): Int {
		return distance - other.distance
	}
}

fun main(args: Array<String>) {
	val g1 = WeightedGraph()
	g1.addVertex("A")
	g1.addVertex("B")
	g1.addVertex("C")
	g1.addVertex("D")
	g1.addVertex("E")
	g1.addVertex("F")
	g1.addVertex("G")
	g1.addVertex("H")
	g1.addEdge("A", "B", 3)
	g1.addEdge("A", "C", 4)
	g1.addEdge("A", "D", 7)
	g1.addEdge("B", "C", 1)
	g1.addEdge("B", "F", 5)
	g1.addEdge("C", "D", 2)
	g1.addEdge("C", "F", 6)
	g1.addEdge("D", "E", 3)
	g1.addEdge("D", "G", 6)
	g1.addEdge("E", "F", 1)
	g1.addEdge("E", "G", 3)
	g1.addEdge("E", "H", 4)
	g1.addEdge("F", "H", 8)
	g1.addEdge("G", "H", 2)
	g1.printGraph()
	// Vertex | Adjacent Vertices
	// --------------------------
	// A      | B: 3, C: 4, D: 7
	// B      | A: 3, C: 1, F: 5
	// C      | A: 4, B: 1, D: 2, F: 6
	// D      | A: 7, C: 2, E: 3, G: 6
	// E      | D: 3, F: 1, G: 3, H: 4
	// F      | B: 5, C: 6, E: 1, H: 8
	// G      | D: 6, E: 3, H: 2
	// H      | E: 4, F: 8, G: 2

//        // TEST 1: Pathfinding BFS
//        HashMap<String, String> paths = g1.pathfindingBFS("A");
//        System.out.println(paths);
	
	// TEST 1: DIJKSTRA'S ALGORITHM
	val distances = HashMap<String, Int>()
	val paths = HashMap<String, String>()
	g1.dijkstra("A", distances, paths)
	println(distances)
	// {A=0, B=3, C=4, D=6, E=9, F=8, G=12, H=13}
	
	// TEST 2: DIJKSTRA'S ALGORITHM with added path memory
	println(paths)
	// {A=A, B=A->B, C=A->C, D=A->C->D, E=A->C->D->E, F=A->B->F, G=A->C->D->G, H=A->C->D->E->H}
	
	
	// TEST 4: PRIM'S ALGORITHM
	// TEST 4: PRIM'S ALGORITHM
	val edges = g1.prim("A")
	println(edges)
	// [E->F, A->B, B->C, E->G, D->E, C->D, G->H]
	
}

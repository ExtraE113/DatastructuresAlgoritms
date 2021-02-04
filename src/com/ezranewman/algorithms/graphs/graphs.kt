package com.ezranewman.algorithms.graphs

import java.lang.IndexOutOfBoundsException
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet
import kotlin.math.min

class Graph {
    private var vertices: HashMap<String, Vertex> = HashMap()

    // Adds a vertex with the given label to the graph
    fun addVertex(label: String) {
        vertices[label] = Vertex(label)
    }

    // Adds an edge between the vertices with the given
    // labels to the graph
    fun addEdge(label1: String, label2: String) {
        vertices[label1]!!.neighbors.add(vertices[label2]!!)
        vertices[label2]!!.neighbors.add(vertices[label1]!!)
    }

    // Removes the given vertex and all of its edges from
    // the graph
    fun removeVertex(label: String) {
        vertices[label]!!.neighbors.forEach {
            it.neighbors.remove(vertices[label])
        }
        vertices.remove(label)
    }

    // Removes the edge between the given vertices
    fun removeEdge(label1: String, label2: String) {
        vertices[label1]!!.neighbors.remove(vertices[label2]!!)
        vertices[label2]!!.neighbors.remove(vertices[label1]!!)

    }

    // visits every vertex starting with the vertex with the given `label` and executes the given fn
    fun depthFirstSearch(label: String, function: (Vertex) -> Unit) {
        val queue: Queue<Vertex> = LinkedList<Vertex>()
        val visited = HashSet<Vertex>()

        queue.add(vertices[label]!!)

        while (!queue.isEmpty()) {
            val vert = queue.poll()
            function(vert)
            visited.add(vert)
            vert.neighbors.forEach {
                if (!visited.contains(it))
                    queue.add(it)
            }
        }
    }

    fun testBipartite(label: String): Boolean {
        val visited = HashSet<String>()
        val l = mutableListOf<MutableList<Vertex>>()
        val nodeIsEven = HashMap<String, Boolean>()
        var layerCounter = 0
        l.add(mutableListOf())
        l[0].add(vertices[label]!!)

        while (l.size > layerCounter){
            for (it in l[layerCounter]) {
                for (n in it.neighbors) {
                    if (!visited.contains(n.label)){
                        visited.add(n.label)
                        nodeIsEven[n.label] = layerCounter % 2 == 0
                        try {
                            l[layerCounter + 1].add(n)
                        } catch (e: IndexOutOfBoundsException) {
                            l.add(mutableListOf())
                            l[layerCounter + 1].add(n)
                        }
                    }
                }
            }
            layerCounter++
        }

        edges.forEach {
            if(nodeIsEven[it.first] == nodeIsEven[it.second]){
                return false
            }
        }
        return true
    }

    fun shortestPath(label: String){
        val visited = HashSet<String>()
        val l = mutableListOf<MutableList<Vertex>>()
        val path = HashMap<String, String>()
        var layerCounter = 0
        l.add(mutableListOf())
        l[0].add(vertices[label]!!)
        path[label] = ""

        while (l.size > layerCounter){
            for (it in l[layerCounter]) {
                for (n in it.neighbors) {
                    if (!visited.contains(n.label)){
                        visited.add(n.label)
                        path[n.label] = path[it.label] + it.label
                        try {
                            l[layerCounter + 1].add(n)
                        } catch (e: IndexOutOfBoundsException) {
                            l.add(mutableListOf())
                            l[layerCounter + 1].add(n)
                        }
                    }
                }
            }
            layerCounter++
        }

        println(path)

    }


    private val edges: LinkedList<Pair<String, String>>
        get() {
            val edges: LinkedList<Pair<String, String>> = LinkedList<Pair<String, String>>()
            for (label1 in vertices.keys) {
                val v = vertices[label1]
                for (n in v!!.neighbors) {
                    val label2 = n.label
                    val e = Pair(label1, label2)
                    if (!edges.contains(e)) {
                        edges.add(e)
                    }
                }
            }
            return edges
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
        for (strI in vertices.keys) {
            val v1 = vertices[strI]
            var str = strI
            for (i in str.length until leftLength) {
                str += " "
            }
            print("$str| ")
            for (i in 0 until v1!!.neighbors.size - 1) {
                print(v1.neighbors[i].label + ", ")
            }
            if (!v1.neighbors.isEmpty()) {
                print(v1.neighbors[v1.neighbors.size - 1].label)
            }
            println()
        }
    }
}

class Vertex(var label: String) {
    var neighbors: LinkedList<Vertex> = LinkedList()

    // intellij generated equals function
    // only checks for equality by label
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vertex

        if (label != other.label) return false

        return true
    }

    override fun hashCode(): Int {
        return label.hashCode()
    }

    override fun toString(): String {
        var neighborString = ""
        neighbors.forEach {
            neighborString += "${it.label}, "
        }
        neighborString = neighborString.substring(0, neighborString.length - 2)// remove trailing comma
        return "$label: $neighborString"
    }
}

fun main(args: Array<String>) {
    println("TEST 1")
    val graph = Graph()
    graph.addVertex("London")
    graph.addVertex("New York")
    graph.addVertex("San Francisco")
    graph.addVertex("Chicago")
    graph.printGraph()
    println()
    // Vertex        | Adjacent Vertices
    // ---------------------------------
    // San Francisco |
    // New York      |
    // Chicago       |
    // London        |


    println("TEST 2")
    graph.addEdge("London", "New York")
    graph.addEdge("New York", "Chicago")
    graph.addEdge("San Francisco", "New York")
    graph.addEdge("San Francisco", "Chicago")
    graph.printGraph()
    println()
    // Vertex        | Adjacent Vertices
    // ---------------------------------
    // San Francisco | New York, Chicago
    // New York      | London, Chicago, San Francisco
    // Chicago       | New York, San Francisco
    // London        | New York


    println("TEST 3")
    graph.removeEdge("New York", "Chicago")
    graph.printGraph()
    println()
    // Vertex        | Adjacent Vertices
    // ---------------------------------
    // San Francisco | New York, Chicago
    // New York      | London, San Francisco
    // Chicago       | San Francisco
    // London        | New York


    println("TEST 4")
    graph.depthFirstSearch("San Francisco") { println(it) }
    println()


    println("TEST 5")
    graph.removeVertex("London")
    graph.printGraph()
    println()
    // Vertex        | Adjacent Vertices
    // ---------------------------------
    // San Francisco | New York, Chicago
    // New York      | San Francisco
    // Chicago       | San Francisco


    println("#########")

    // Pentagon Graph -> Not Bipartite
    // Pentagon Graph -> Not Bipartite
    val graph3 = Graph()
    graph3.addVertex("A")
    graph3.addVertex("B")
    graph3.addVertex("C")
    graph3.addVertex("D")
    graph3.addVertex("E")

    graph3.addEdge("A", "B")
    graph3.addEdge("B", "C")
    graph3.addEdge("C", "D")
    graph3.addEdge("D", "E")
    graph3.addEdge("E", "A")

    graph3.printGraph()

    println(graph3.testBipartite("A"))

// Hexagon Graph -> Bipartite

// Hexagon Graph -> Bipartite
    val graph4 = Graph()
    graph4.addVertex("A")
    graph4.addVertex("B")
    graph4.addVertex("C")
    graph4.addVertex("D")
    graph4.addVertex("E")
    graph4.addVertex("F")

    graph4.addEdge("A", "B")
    graph4.addEdge("B", "C")
    graph4.addEdge("C", "D")
    graph4.addEdge("D", "E")
    graph4.addEdge("E", "F")
    graph4.addEdge("F", "A")

    println(graph4.testBipartite("A"))

// Adding this edge to the hex graph should make it not bipartite

// Adding this edge to the hex graph should make it not bipartite
    graph4.addEdge("A", "C")
    println(graph4.testBipartite("A"))

// Concurrent rectangles -> Bipartite

// Concurrent rectangles -> Bipartite
    val graph5 = Graph()
    graph5.addVertex("A")
    graph5.addVertex("B")
    graph5.addVertex("C")
    graph5.addVertex("D")
    graph5.addVertex("E")
    graph5.addVertex("F")
    graph5.addVertex("G")
    graph5.addVertex("H")

    graph5.addEdge("A", "B")
    graph5.addEdge("A", "D")
    graph5.addEdge("A", "E")

    graph5.addEdge("C", "B")
    graph5.addEdge("C", "D")
    graph5.addEdge("C", "G")

    graph5.addEdge("H", "E")
    graph5.addEdge("H", "G")
    graph5.addEdge("H", "D")

    graph5.addEdge("F", "B")
    graph5.addEdge("F", "E")
    graph5.addEdge("F", "G")

    println(graph5.testBipartite("A"))


    println("#####")
    val graph6 = Graph()

    graph6.addVertex("A")
    graph6.addVertex("B")
    graph6.addEdge("A", "B")
    graph6.addVertex("C")
    graph6.addEdge("B", "C")
    graph6.addVertex("D")
    graph6.addEdge("C", "D")
    graph6.shortestPath("A")
    graph6.addEdge("A", "D")
    graph6.shortestPath("A")
}
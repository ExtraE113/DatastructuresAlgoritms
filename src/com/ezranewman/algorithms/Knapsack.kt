package com.ezranewman.algorithms

object Knapsack {
	fun knapsackRecursive(weights: IntArray, values: IntArray, capacity: Int, n: Int = -100): Int {
		//do the recursion
		if (n == -100)
			return knapsackRecursive(weights, values, capacity, weights.size)
		
		if (n == 0 || capacity == 0) //we're out of room
			return 0
		
		if (weights[n - 1] > capacity) {
			return knapsackRecursive(weights, values, capacity, n - 1)
		}
		
		return Math.max(
			values[n - 1] + knapsackRecursive(weights, values, capacity - weights[n - 1], n - 1),
			knapsackRecursive(weights, values, capacity, n - 1)
		)
	}
	
	fun knapsack(weights: IntArray, values: IntArray, capacity: Int): Int {
		val table = Array(weights.size+1){
			IntArray(capacity+1)
		}
		
		//[row][col]
		
		for (i in table.indices){
			for (j in table[i].indices){
				if(i == 0 || j == 0){
					continue
				}
				
				//exclude this
				val case1 = table[i-1][j]
				
				//include this
				val case2 = try{
					values[i-1] + table[i-1][j - weights[i-1]]
				} catch (e: IndexOutOfBoundsException){
					-1
				}
				table[i][j] = Math.max(case1, case2)
			}
		}
		return table[weights.size][capacity]
	}
}

fun main(args: Array<String>) {
	// Basic Tests
	val weights1 = intArrayOf(5, 10, 25)
	val values1 = intArrayOf(70, 90, 140)
	println(Knapsack.knapsack(weights1, values1, 25))
	// 160
	val weights2 = intArrayOf(5, 10, 20)
	val values2 = intArrayOf(150, 60, 140)
	println(Knapsack.knapsack(weights2, values2, 30))
	// 290
	val weights3 = intArrayOf(5, 20, 10)
	val values3 = intArrayOf(50, 140, 60)
	println(Knapsack.knapsack(weights3, values3, 30))
	// 200
	
	// More advanced test
	val weights4 = intArrayOf(85, 26, 48, 21, 22, 95, 43, 45, 55, 52)
	val values4 = intArrayOf(79, 32, 47, 18, 26, 85, 33, 40, 45, 59)
	println(Knapsack.knapsack(weights4, values4, 101))
	// 117
}
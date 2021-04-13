package com.ezranewman.algorithms

import java.lang.IndexOutOfBoundsException

object SubsetSum {
	fun naiveSubsetSum(arr: IntArray, index: Int, sum: Int): Boolean {
		// Base case 1: any subset can add up to 0
		return if (sum == 0) {
			true
		} else if (sum < 0) {
			false
		} else if (index < 0) {
			false
		} else {
			val currentValue = arr[index]
			val case1 = naiveSubsetSum(arr, index - 1, sum - currentValue)
			val case2 = naiveSubsetSum(arr, index - 1, sum)
			case1 || case2
		}
	}
	
	fun subsetSum(arr: IntArray, sum: Int): Boolean {
		// table to store values, feel free to adjust bounds, but these bounds can work
		val table = Array(arr.size) {
			BooleanArray(sum + 1)
		}
		
		for (i in table[0].indices) {
			when (i) {
				0 -> {
					table[0][i] = true
				}
				arr[0] -> {
					table[0][i] = true
				}
				else -> {
					table[0][i] = false
				}
			}
		}
		
		for (i in table.indices) {
			table[i][0] = true
		}
		
		for (i in table.indices) {
			for (j in table[i].indices) {
				try {
					if (table[i - 1][j] == true) {
						table[i][j] = true
					}
				} catch (e: IndexOutOfBoundsException) {
				}
				try {
					if (table[i-1][j - arr[i]]) {
						table[i][j] = true
					}
				} catch (e: IndexOutOfBoundsException) {
				}
			}
		}
		
		
		return table[arr.size - 1][sum]
	}
}

fun main(args: Array<String>) {
	// Test 1: does {1, 2, 3, 7} have a subset sum of 6?
	val arr1 = intArrayOf(1, 2, 3, 7)
	println(SubsetSum.subsetSum(arr1, 6))
	// true
	
	// Test 2: does {1, 2, 3, 7} have a subset sum of 5?
	println(SubsetSum.subsetSum(arr1, 5))
	// true
	
	// Test 3: does {1, 2, 3, 7} have a subset sum of 11?
	println(SubsetSum.subsetSum(arr1, 11))
	// true
	
	// Test 4: does {1, 2, 3, 7} have a subset sum of 14?
	println(SubsetSum.subsetSum(arr1, 14))
	// false
}

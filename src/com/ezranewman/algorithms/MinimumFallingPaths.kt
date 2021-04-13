package com.ezranewman.algorithms

fun minFallingPathSum(matrix: Array<IntArray>): Int {
	val table = Array(matrix.size) {
		IntArray(matrix[0].size)
	}
	for (i in matrix.size-1 downTo 0) {
		println("here")
		for (j in matrix[i].indices) {
			if (i == matrix.size) {
				table[i][j] = matrix[i][j]
			} else {
				var above: Int = Integer.MAX_VALUE
				var diag1: Int = Integer.MAX_VALUE
				var diag2: Int = Integer.MAX_VALUE
				try {
					above = table[i - 1][j]
				} catch (e: IndexOutOfBoundsException) {
				
				}
				try {
					diag1 = table[i - 1][j - 1]
				} catch (e: IndexOutOfBoundsException) {
				
				}
				try {
					diag2 = table[i - 1][j + 1]
				} catch (e: IndexOutOfBoundsException) {
				
				}
				if(Math.min(above, Math.min(diag1, diag2)) == Integer.MAX_VALUE){
				
				}
				table[i][j] = matrix[i][j] + Math.min(above, Math.min(diag1, diag2))
			}
		}
	}
	var min: Int = Integer.MAX_VALUE
	
	for (i in table[0]) {
		min = Math.min(min, i)
	}
	return min
}

fun main() {
	//[[2,1,3],[6,5,4],[7,8,9]]
	println(
		minFallingPathSum(
			arrayOf(
				intArrayOf(2, 1, 3),
				intArrayOf(6, 5, 4),
				intArrayOf(7, 8, 9)
			)
		)
	)
}
package com.ezranewman.algorithms


fun uniquePaths(m: Int, n: Int): Int {
	val table = Array(m) {
		IntArray(n)
	}
	
	for (i in table[0].indices) {
		table[0][i] = 1
	}
	for (i in table.indices) {
		table[i][0] = 1
	}
	
	for (i in table.indices) {
		for (j in table[i].indices) {
			try {
				table[i][j] = table[i - 1][j] + table[i][j - 1]
			} catch (e: IndexOutOfBoundsException) {
			
			}
		}
	}
	return table[m - 1][n - 1]
}

fun main() {
	println(uniquePaths(3, 7))
}
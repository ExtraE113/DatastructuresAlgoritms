package com.ezranewman.algorithms

import java.util.*


object Inversions {
	// Helper method to create subArrays from arrays
	fun subArray(arr: IntArray, begin: Int, end: Int): IntArray {
		val output = IntArray(end - begin)
		for (i in output.indices) {
			output[i] = arr[begin + i]
		}
		return output
	}
	
	// Helper method to print array to aid debugging
	fun printArray(arr: IntArray?) {
		println(Arrays.toString(arr))
	}
	
	// Here is a working version of the merge method
	fun merge(A: IntArray, B: IntArray): IntArray {
		// Indices to keep track of location in input and output arrays
		var aIdx = 0
		var bIdx = 0
		var outputIdx = 0
		val output = IntArray(A.size + B.size)
		
		// While we have two values left to compare...
		// * Copy the smaller value into the output array
		// * Increment the appropriate indexes
		while (aIdx < A.size && bIdx < B.size) {
			if (B[bIdx] < A[aIdx]) {
				output[outputIdx] = B[bIdx]
				bIdx++
			} else {
				output[outputIdx] = A[aIdx]
				aIdx++
			}
			outputIdx++
		}
		
		// Copy rest of array A if array B finished first
		while (aIdx < A.size) {
			output[outputIdx] = A[aIdx]
			aIdx++
			outputIdx++
		}
		
		// Copy rest of array B if array A finished first
		while (bIdx < B.size) {
			output[outputIdx] = B[bIdx]
			bIdx++
			outputIdx++
		}
		return output
	}
	
	// Here is a working version of the merge sort method
	fun mergeSort(arr: IntArray): IntArray {
		// Base case: array of size 1 is sorted
		return if (arr.size == 1) {
			arr
		} else {
			// Split array into two halves
			var A = subArray(arr, 0, arr.size / 2)
			var B = subArray(arr, arr.size / 2, arr.size)
			
			// Call merge sort on each half
			A = mergeSort(A)
			B = mergeSort(B)
			
			// Merge the sorted arrays and return the answer
			merge(A, B)
		}
	}
	
	// The mergeAndCount method merges two sorted arrays and counts
	// the number of inversions
	internal fun mergeAndCount(A: IntArray, B: IntArray): InversionResult {
		val outArr = IntArray(A.size + B.size)
		var count = 0
		var i = 0
		var j = 0
		while (i < A.size && j < B.size) {
			if (B[j] < A[i]) {
				outArr[i + j] = B[j]
				count += A.size - i
				j++
			} else {
				outArr[i + j] = A[i]
				i++
			}
		}
		while (i < A.size) {
			outArr[i + j] = A[i]
			i++
		}
		while (j < B.size) {
			outArr[i + j] = B[j]
			j++
		}
		return InversionResult(outArr, count)
	}
	
	// The sortAndCount method sorts arr and counts the number
	// of inversions in the array
	internal fun sortAndCount(arr: IntArray): InversionResult {
		if (arr.size < 2) {
			return InversionResult(arr, 0)
		} else {
			val A = IntArray(arr.size / 2)
			arr.copyInto(A, 0, 0, arr.size / 2)
			val B = IntArray(arr.size - A.size)
			arr.copyInto(B, 0, A.size, arr.size)
			val Aresult = sortAndCount(A)
			val Bresult = sortAndCount(B)
			val ComboResult = mergeAndCount(Aresult.arr, Bresult.arr)
			return InversionResult(ComboResult.arr, Aresult.count + Bresult.count + ComboResult.count)
		}
	}
	
	
}

internal class InversionResult(var arr: IntArray, var count: Int)


fun main() {
	// Test 1: Merge
	val l = intArrayOf(1, 3, 4, 7)
	val r = intArrayOf(2, 5, 8, 10)
	Inversions.printArray(Inversions.merge(l, r))
	// [1, 2, 3, 4, 5, 7, 8, 10]
	Inversions.printArray(Inversions.merge(r, l))
	// [1, 2, 3, 4, 5, 7, 8, 10]
	
	
	// Test 2: Merge Sort
	val arr = intArrayOf(4, 1, 23, 51, 2, 67, 12, 32, 87, 43, 56, 63, 28, 94, 15, 24)
	Inversions.printArray(Inversions.mergeSort(arr))
	// [1, 2, 4, 12, 15, 23, 24, 28, 32, 43, 51, 56, 63, 67, 87, 94]
	
	// Test 3: Merge and Count
	println("\nTest 3:")
	val arr1 = intArrayOf(1, 3, 6, 8)
	val arr2 = intArrayOf(2, 4, 5, 7)
	val m1 = Inversions.mergeAndCount(arr1, arr2)
	Inversions.printArray(m1.arr)
	// [1, 2, 3, 4, 5, 6, 7, 8]
	println(m1.count)
	// 8
	
	// Test 4: Sort and Count
	println("\nTest 4:")
	val A = Inversions.sortAndCount(intArrayOf(4, 2, 3, 1))
	Inversions.printArray(A.arr)
	// [1, 2, 3, 4]
	println(A.count)
	// 5
	val B = Inversions.sortAndCount(intArrayOf(2, 3, 4, 5, 6, 7, 8, 1))
	Inversions.printArray(B.arr)
	// [1, 2, 3, 4, 5, 6, 7, 8]
	println(B.count)
	// 7
	val C = Inversions.sortAndCount(intArrayOf(2, 3, 7, 4, 1, 5, 8, 6))
	Inversions.printArray(C.arr)
	// [1, 2, 3, 4, 5, 6, 7, 8]
	println(C.count)
	// 8
}
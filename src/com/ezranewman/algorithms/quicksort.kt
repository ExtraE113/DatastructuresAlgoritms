package com.ezranewman.algorithms


object QuickSort {
	// Simple insertion sort to use in the base case of quick sort
	fun insertionSort(arr: IntArray, start: Int, end: Int) {
		for (i in start + 1..end) {
			val key = arr[i]
			var j = i - 1
			
			/* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */while (j >= 0 && arr[j] > key) {
				arr[j + 1] = arr[j]
				j = j - 1
			}
			arr[j + 1] = key
		}
	}
	
	fun mergeSort(a: IntArray) {
		val n = a.size
		recursiveSort(a, 0, n - 1)
	}
	
	// Sorts a[start]...a[end]
	fun recursiveSort(a: IntArray, start: Int, end: Int) {
		if (end - start < 2) {
			// If length == 2 and out of order, swap
			if (end > start && a[start] > a[end]) {
				val temp = a[start]
				a[start] = a[end]
				a[end] = temp
			}
		} else {
			val middle = (start + end) / 2
			recursiveSort(a, start, middle)
			recursiveSort(a, middle + 1, end)
			merge(a, start, middle, end)
		}
	}
	
	// Merge from a[start]...a[middle] and a[middle+1]...a[end]
	fun merge(a: IntArray, start: Int, middle: Int, end: Int) {
		// Let's say: a = [1, 2, 3, 5, 0, 4, 6, 7]
		//            start = 0, middle = 3, end = 7
		
		// The "left" array goes from start->middle (0 -> 3)
		//                [1, 2, 3, 5]
		
		// The "right" array goes from middle+1 -> end
		//                            [0, 4, 6, 7]
		
		// We'll just put them into a temporary array
		val temp = IntArray(end - start + 1)
		
		// The first index of the left array is start
		var leftIdx = start
		
		// The first index of the right array is middle+1
		var rightIdx = middle + 1
		
		// We will keep track of the first empty spot in our temp array
		var tempIdx = 0
		
		// Keep going until you run out of stuff in one of the arrays
		while (leftIdx <= middle && rightIdx <= end) {
			// compare a[leftIdx] with a[rightIdx] to see what is smaller
			if (a[leftIdx] < a[rightIdx]) {
				temp[tempIdx] = a[leftIdx]
				leftIdx++
				tempIdx++
			} else {
				temp[tempIdx] = a[rightIdx]
				rightIdx++
				tempIdx++
			}
		}
		while (rightIdx <= end) {
			temp[tempIdx] = a[rightIdx]
			rightIdx++
			tempIdx++
		}
		while (leftIdx <= middle) {
			temp[tempIdx] = a[leftIdx]
			leftIdx++
			tempIdx++
		}
		for (i in temp.indices) {
			a[i + start] = temp[i]
		}
	}
	
	// You can decide if you want the "start" and "end" variables to be inclusive or not
	// I chose for the "end" variable to be inclusive
	fun qSort(arr: IntArray, start: Int, end: Int) {
		if (start-end <= 3){
			insertionSort(arr, start, end)
		} else {
			// choose ending index as a splitter
			var i = start -1
			for (j in start until end){
				if(arr[j] < arr[end-1]){
					i++
					// fancy kotlin swap
					arr[i] = arr[j].also{arr[j] = arr[i]}
				}
			}
			arr[i + 1] = arr[end-1].also {arr[end-1] = arr[i + 1]}
			qSort(arr, start, i)
			qSort(arr, i, end)
		}
	}
	
	// Wrapper method (update this method if you decided to exclude end)
	fun quickSort(arr: IntArray) {
		qSort(arr, 0, arr.size - 1)
	}
	
	fun printArray(arr: IntArray) {
		for (x in arr) {
			print("$x ")
		}
		println()
	}
}
fun main(args: Array<String>) {
	// QuickSort tests
	val arr1 = intArrayOf(3, 1, 6, 7, 5, 8, 4, 9, 2)
	QuickSort.quickSort(arr1)
	QuickSort.printArray(arr1)
	// 1 2 3 4 5 6 7 8 9
	val arr2 = intArrayOf(12, 37, 74, 356, 23, 41, 134, 71, 234, 73, 21, 34, 43)
	QuickSort.quickSort(arr2)
	QuickSort.printArray(arr2)
	// 12 21 23 34 37 41 43 71 73 74 134 234 356
}

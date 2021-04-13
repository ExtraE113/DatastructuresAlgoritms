package com.ezranewman.algorithms

import kotlin.math.min

class CoinChange {
	var memo: HashMap<Int, Int> = HashMap()
}

fun change(coins: IntArray, amount: Int, memo: CoinChange = CoinChange()): Int {
	if (amount == 0){
		return 0
	} else if (memo.memo.keys.contains(amount)){
		return memo.memo[amount]!!
	} else {
		var answer = Integer.MAX_VALUE
		for (coin in coins){
			if(coin <= amount){
				answer = min(answer, 1+change(coins, amount-coin, memo))
			}
		}
		memo.memo[amount] = answer
		return answer
	}
}


fun main(args: Array<String>) {
	// Test 1: Some basic tests of the change() methd

	println(change(intArrayOf(1, 4, 5), 18))
	// 4
	println(change(intArrayOf(1, 4, 5), 33))
	// 7
	
	println(change(intArrayOf(1, 3, 5), 16))
	// 4
	
	println(change(intArrayOf(1, 3, 5), 33))
	// 7
	
	// Test 2: Test that memoization is working
	// Warning: if you haven't memoized your code yet,
	// this is going to take a long, long time!
	
	println(change(intArrayOf(1, 4, 5), 588))
	// 118
	
	println(change(intArrayOf(1, 4, 5), 1288))
	// 258
	// My computer tried running this without memoization, and
	// it did not complete in 20+ minutes
	// Getting stack overflow error here is possible as well
}
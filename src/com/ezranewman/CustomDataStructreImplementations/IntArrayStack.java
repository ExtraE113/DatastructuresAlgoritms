package com.ezranewman.CustomDataStructreImplementations;

import java.util.Arrays;

public class IntArrayStack implements Stack<Integer>{
    private int[] arr;

    private int lastUsed = -1;

    public IntArrayStack(int size){
        this.arr = new int[size];
    }

    public IntArrayStack() {
        this(2); //default size should be way bigger but this lets me test behavior
    }

    @Override
    public void push(Integer data) {
        if(lastUsed+1 < arr.length){
            lastUsed++;
            arr[lastUsed] = data;
        } else {
            int[] arr2 = new int[arr.length*2];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            arr = arr2;
            push(data);
        }
    }

    @Override
    public Integer pop() {
        lastUsed--;
        return arr[lastUsed+1];
    }

    @Override
    public Integer peek() {
        return arr[lastUsed];
    }

    @Override
    public boolean isEmpty() {
        return lastUsed < 0;
    }

    @Override
    public String toString() {
        return "ezranewmanCustomDataStructreImplementations.IntArrayStack{" +
                "arr=" + Arrays.toString(arr) +
                ", lastUsed=" + lastUsed +
                '}';
    }

    public static void main(String[] args) {
        Stack<Integer> stack = new IntArrayStack();
        stack.push(10);
        stack.push(5);
        stack.push(2);
        stack.push(7);

        System.out.println(stack);

        System.out.println(stack.peek()); // 7

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
        // 7
        // 2
        // 5
        // 10
    }
}

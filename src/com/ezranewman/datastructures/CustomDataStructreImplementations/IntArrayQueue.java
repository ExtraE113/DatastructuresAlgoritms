package com.ezranewman.datastructures.CustomDataStructreImplementations;

public class IntArrayQueue implements Queue<Integer>{

    private int[] arr;
    private int head = -1;

    public IntArrayQueue(int capacity) {
        this.arr = new int[capacity];
    }

    public IntArrayQueue() {
        this(2); //2 for testing, should he higher for actual implementation
    }


    /*
     This could either be O(n) or O(1), depending on your platform
     see this https://stackoverflow.com/questions/7165594/time-complexity-of-system-arraycopy

     if it is O(n) then this could be implemented more efficiently but it still would just be a smaller O(n)
     at least in the worst case. You could have an internal looping array and then you wouldn't have to duplicate
     as often but it would still be O(n) for the worst case.
    */
    @Override
    public void enqueue(Integer data) {
        if(head+1 < arr.length){
            head++;
            arr[head] = data;
        } else {
            int[] arr2 = new int[arr.length*2];
            System.arraycopy(arr, 0, arr2, 0, arr.length);
            arr = arr2;
            enqueue(data);
        }
    }

    @Override
    public Integer dequeue() {
        int out = arr[0];
        System.arraycopy(arr, 1, arr, 0, arr.length-1);
        head--;
        return out;
    }

    @Override
    public Integer peek() {
        return arr[0];
    }

    @Override
    public boolean isEmpty() {
        return head < 0;
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new IntArrayQueue();
        queue.enqueue(10);
        queue.enqueue(5);
        queue.enqueue(2);
        queue.enqueue(7);

        System.out.println(queue.peek()); // 10

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
        // 10
        // 5
        // 2
        // 7
    }
}

package com.ezranewman.CustomDataStructreImplementations;

public class LinearProbingHash {
    private final int TABLE_CAPACITY = 10;
    private int currentSize; // optional variable
    int[] arr;

    public LinearProbingHash() {
        this.arr = new int[TABLE_CAPACITY];
        this.currentSize = 0;
    }

    // A simple hash function
    // It will just calculate the data value % TABLE_CAPACITY
    public int hash(int data) {
        return data % TABLE_CAPACITY;
    }

    // Inserts data into the hash table
    // If table is already full, should just return
    // Here are the main steps for insertion:
    // * Calculate hash value for the data
    // * Check if the appropriate index of the array is empty
    // * If not, keep checking the subsequent entries until you find an empty spot
    public void insert(int data) {
        int hash = hash(data);
        if (currentSize < arr.length) {
            currentSize++;
            while (true) {
                if (arr[hash] == 0 || arr[hash] == -1) {
                    arr[hash] = data;
                    return;
                } else {
                    hash++;
                    hash %= arr.length;
                }
            }
        }
    }

    public boolean search(int data) {
        int hash = hash(data);
        int steps = 0;
        while (true) {
            if (steps > currentSize) {
                return false;
            }

            if (arr[hash] == data) {
                return true;
            }

            if (arr[hash] == 0) {
                return false;
            } else {
                hash++;
                hash %= arr.length;
            }
            steps++;
        }
    }

    // Deletes data from the hash table
    // Change the value for the respective entry to -1
    // Should just return if the data is not found
    public void delete(int data) {
        int hash = hash(data);
        int steps = 0;
        while (true) {
            if (steps > currentSize) {
                return;
            }

            if (arr[hash] == data) {
                currentSize--;
                arr[hash] = -1;
            }

            if (arr[hash] == 0) {
                return;
            } else {
                hash++;
                hash %= arr.length;
            }
            steps++;
        }
    }

    // Prints out the table in a nice-ish format
    public void print() {
        System.out.println(this);
    }

    @Override
    public String toString() {
        StringBuilder x = new StringBuilder(100);
        x.append("INDEX | DATA\n");
        x.append("------------\n");

        for (int i = 0; i < TABLE_CAPACITY; i++) {
            StringBuilder index = new StringBuilder("" + i);
            StringBuilder data = new StringBuilder("" + this.arr[i]);
            int indexLen = index.length();
            int dataLen = data.length();

            // pad index spaces
            for (int j = 0; j < 5 - indexLen; j++) {
                index.insert(0, " ");
            }

            // pad data spaces
            for (int j = 0; j < 5 - dataLen; j++) {
                data.insert(0, " ");
            }

            x.append(index).append(" |").append(data).append("\n");
        }

        return x.toString();
    }

    public static void main(String[] args) {
        // *************
        // Insert tests:
        // *************
        LinearProbingHash table1 = new LinearProbingHash();

        // Insert some values to the table
        System.out.println("Test 1: ");
        table1.insert(5);
        table1.insert(7);
        table1.insert(3);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 |    0
        //     1 |    0
        //     2 |    0
        //     3 |    3
        //     4 |    0
        //     5 |    5
        //     6 |    0
        //     7 |    7
        //     8 |    0
        //     9 |    0

        // Insert some values that need to be properly hashed to
        // fit in the table
        System.out.println("Test 2: ");
        table1.insert(11);
        table1.insert(26);
        table1.insert(34);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 |    0
        //     1 |   11
        //     2 |    0
        //     3 |    3
        //     4 |   34
        //     5 |    5
        //     6 |   26
        //     7 |    7
        //     8 |    0
        //     9 |    0

        // Insert some values whose hashed values are occupied
        // They should do linear probing and look for the next empty slot
        System.out.println("Test 3: ");
        table1.insert(21);
        table1.insert(36);
        table1.insert(49);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 |    0
        //     1 |   11
        //     2 |   21
        //     3 |    3
        //     4 |   34
        //     5 |    5
        //     6 |   26
        //     7 |    7
        //     8 |   36
        //     9 |   49

        // Linear probing should wrap around the table
        System.out.println("Test 4: ");
        table1.insert(31);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 |   31
        //     1 |   11
        //     2 |   21
        //     3 |    3
        //     4 |   34
        //     5 |    5
        //     6 |   26
        //     7 |    7
        //     8 |   36
        //     9 |   49

        // Table is full, so nothing should change
        System.out.println("Test 5: ");
        table1.insert(9);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 |   31
        //     1 |   11
        //     2 |   21
        //     3 |    3
        //     4 |   34
        //     5 |    5
        //     6 |   26
        //     7 |    7
        //     8 |   36
        //     9 |   49

        // *************
        // search tests:
        // *************
        // Search should already work, so if this doesn't work
        // it's probably something wrong with your insert
        System.out.println("Test 6:");
        System.out.println(table1.search(31)); //  true
        System.out.println(table1.search(11)); //  true
        System.out.println(table1.search(21)); //  true
        System.out.println(table1.search(3)); //   true
        System.out.println(table1.search(131)); // false
        System.out.println(table1.search(226)); // false
        System.out.println(table1.search(2)); //   false
        System.out.println(table1.search(4)); //   false
        System.out.println();

        // *************
        // delete tests:
        // *************
        System.out.println("Test 7:");

        // Delete a number in the table that is located at its
        // hash value
        table1.delete(11);

        // Then delete a number that was inserted using
        // linear probing
        table1.delete(36);

        // Then delete a number that is not in the table
        table1.delete(100);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 |   31
        //     1 |   -1
        //     2 |   21
        //     3 |    3
        //     4 |   34
        //     5 |    5
        //     6 |   26
        //     7 |    7
        //     8 |   -1
        //     9 |   49

        // When inserted after deletion, the -1 should be
        // able to be replaced
        System.out.println("Test 8: ");
        table1.insert(11);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 |   31
        //     1 |   11
        //     2 |   21
        //     3 |    3
        //     4 |   34
        //     5 |    5
        //     6 |   26
        //     7 |    7
        //     8 |   -1
        //     9 |   49
    }
}

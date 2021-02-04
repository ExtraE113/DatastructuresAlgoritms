package com.ezranewman.datastructures.chainingHashTable;

public class SeparateChainingHash {
    private int TABLE_CAPACITY = 10;
    private int currentSize;
    IntLinkedList[] arr;

    public SeparateChainingHash() {
        this.arr = new IntLinkedList[TABLE_CAPACITY];

        // initialize all linked lists
        for (int i = 0; i < TABLE_CAPACITY; i++) {
            this.arr[i] = new IntLinkedList();
        }

        this.currentSize = 0;
    }

    // A simple hash function
    // It will just calculate the data value % TABLE_CAPACITY
    public int hash(int data) {
        return data % TABLE_CAPACITY;
    }

    // Searches for the data value in our hash table
    public boolean search(int data) {
        int index = hash(data);
        IntLinkedList list = this.arr[index];
        return list.searchList(data);
    }

    // Inserts the value in our hash table
    // If the loading factor gets too high,
    // should call the resize method
    public void insert(int data) {
        int hash = hash(data);
        System.out.println("current size: "+ currentSize + " capacity: "+ TABLE_CAPACITY);
        if((double)currentSize/TABLE_CAPACITY > 1){
            System.out.println("resizing");
            resize();
            insert(data);
        } else {
            currentSize++;
            arr[hash].insertLast(data);
        }
    }

    // Deletes a value from our array
    public void delete(int data) {
        int hash = hash(data);
        arr[hash].deleteKey(data);
    }

    // Creates a new array double the size of the old one
    // Should update all instance variables appropriately,
    // and re-insert all values into the new array
    public void resize() {
        TABLE_CAPACITY *= 2;
        IntLinkedList[] arr2 = new IntLinkedList[TABLE_CAPACITY];
        for (int i = 0; i < TABLE_CAPACITY; i++) {
            arr2[i] = new IntLinkedList();
        }
        for (IntLinkedList i : arr) {
            IntNode working = i.head;
            while(working != null){
                int hash = hash(working.data);
                arr2[hash].insertLast(working.data);
                working = working.next;
            }
        }
        arr = arr2;
    }

    public void print() {
        System.out.println("INDEX | DATA");
        System.out.println("------------");
        for (int i = 0; i < TABLE_CAPACITY; i++)
        {
            String index = "" + i;
            String data = " " + this.arr[i];
            int indexLen = index.length();

            // pad index spaces
            for (int j = 0; j < 5 - indexLen; j++)
            {
                index = " " + index;
            }

            System.out.println(index + " |" + data);
        }
        System.out.println();
    }

    public static void main(String[] args)
    {
        SeparateChainingHash table1 = new SeparateChainingHash();

        // *************
        // Insert tests:
        // *************
        // Insert some values to the table
        System.out.println("Test 1: ");
        table1.insert(5);
        table1.insert(7);
        table1.insert(3);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 | null
        //     1 | null
        //     2 | null
        //     3 | 3 -> null
        //     4 | null
        //     5 | 5 -> null
        //     6 | null
        //     7 | 7 -> null
        //     8 | null
        //     9 | null

        // Insert some values that need to be properly hashed to
        // fit in the table
        System.out.println("Test 2: ");
        table1.insert(11);
        table1.insert(26);
        table1.insert(34);
        table1.print();
        System.out.println(table1.currentSize);
        // INDEX | DATA
        // ------------
        //     0 | null
        //     1 | 11 -> null
        //     2 | null
        //     3 | 3 -> null
        //     4 | 34 -> null
        //     5 | 5 -> null
        //     6 | 26 -> null
        //     7 | 7 -> null
        //     8 | null
        //     9 | null

        // Insert some values whose hashed values are occupied
        // They should just be appended to the appropriate linked list
        System.out.println("Test 3: ");
        table1.insert(21);
        table1.insert(36);
        table1.insert(49);
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 | null
        //     1 | 11 -> 21 -> null
        //     2 | null
        //     3 | 3 -> null
        //     4 | 34 -> null
        //     5 | 5 -> null
        //     6 | 26 -> 36 -> null
        //     7 | 7 -> null
        //     8 | null
        //     9 | 49 -> null

        // *************
        // Search tests:
        // *************
        // Search should already work, so if this doesn't work
        // it's probably something wrong with your insert
        System.out.println("Test 4:");
        System.out.println(table1.search(31)); //  false
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
        System.out.println("Test 5:");

        // Delete a numbers in the table
        table1.delete(11);
        table1.delete(36);
        table1.delete(3);

        // Then delete a number that is not in the table
        table1.delete(100);

        table1.print();
        // INDEX | DATA
        // ------------
        //     0 | null
        //     1 | 21 -> null
        //     2 | null
        //     3 | null
        //     4 | 34 -> null
        //     5 | 5 -> null
        //     6 | 26 -> null
        //     7 | 7 -> null
        //     8 | null
        //     9 | 49 -> null

        // *************
        // resize tests:
        // *************
        System.out.println("Test 6:");

        // Right now the load factor is 0.6, if we add
        // 5 more elements, then we expect our insert()
        // method to call the resize() method
        table1.insert(42);
        table1.insert(16);
        table1.insert(53);
        table1.insert(44);
        table1.insert(20);

        // Table should be size 20 now
        table1.print();
        // INDEX | DATA
        // ------------
        //     0 | 20 -> null
        //     1 | 21 -> null
        //     2 | 42 -> null
        //     3 | null
        //     4 | 44 -> null
        //     5 | 5 -> null
        //     6 | 26 -> null
        //     7 | 7 -> null
        //     8 | null
        //     9 | 49 -> null
        //    10 | null
        //    11 | null
        //    12 | null
        //    13 | 53 -> null
        //    14 | 34 -> null
        //    15 | null
        //    16 | 16 -> null
        //    17 | null
        //    18 | null
        //    19 | null
    }
}

class IntLinkedList
{
    // instance variables
    IntNode head;

    public IntLinkedList()
    {
        head = null;
    }

    public boolean isEmpty()
    {
        return (head == null);
    }

    // Search the linked list for the specified key
    // return true if key is found, false otherwise
    public boolean searchList(int key)
    {
        IntNode current = head;

        while (current != null)
        {
            if (current.data == key)
            {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    // Insert value to tail of the link list
    public void insertLast(int data)
    {
        //make a new Node
        IntNode newNode = new IntNode(data);

        // Edge case: list is empty
        if (head == null)
        {
            head = newNode;
            return;
        }
        else
        {
            // find the tail
            IntNode current = head;
            while (current.next != null) {
                current = current.next;
            }

            // point tail to new node
            current.next = newNode;
            return;
        }
    }

    // Delete Node with specified key, returns whether
    // deletion was successful or not
    public boolean deleteKey(int key)
    {
        // Linked list is empty
        if (head == null) {
            return false;
        }
        // If deleting head, need to update head
        else if (head.data == key) {
            head = head.next;
            return true;
        }

        // Otherwise, we are not deleting head, we can proceed as usual
        IntNode prev = head;
        IntNode current = head.next;

        while (current != null)
        {
            // If we found the node, delete it
            if (current.data == key) {
                prev.next = current.next;
                return true;
            }
            else {
                prev = current;
                current = current.next;
            }
        }

        // Else we didn't find the node
        return false;
    }

    // toString function that is called when LinkedList is printed
    public String toString()
    {
        String str = "";
        IntNode current = head;
        while (current != null)
        {
            str = str + current.data + " -> ";
            current = current.next;
        }
        str +=  "null";
        return str;

        // a string representation of your linked list
        // 2 -> 6 -> 17 -> 45
    }
}

class IntNode
{
    int data;
    IntNode next;

    public IntNode(int data)
    {
        this.data = data;
        this.next = null;
    }

    public IntNode(int data, IntNode next)
    {
        this.data = data;
        this.next = next;
    }
}





package com.ezranewman.datastructures.spellcheck.src;

public class StringHashTable {
    private int TABLE_SIZE;
    private boolean isSimple;
    private StringLinkedList[] arr;

    public StringHashTable() {
        this(false);
    }

    public StringHashTable(boolean isSimple) {
        if (isSimple) {
            this.TABLE_SIZE = 26;
            this.isSimple = isSimple;
        }
        else {
            this.TABLE_SIZE = 991547;
            this.isSimple = isSimple;
        }



        this.arr = new StringLinkedList[TABLE_SIZE];

        // initialize all linked lists
        for (int i = 0; i < TABLE_SIZE; i++)
        {
            this.arr[i] = new StringLinkedList();
        }
    }

    // Just hash the Strings by the first letter
    // Use this method to test that you are loading your
    // dictionary properly
    private int simpleHash(String data) {
        return data.charAt(0) - 'a';
    }

    // A better hash function for Strings
    private int hash(String data) {
        int hash = 7;
        for (int i = 0; i < data.length(); i++) {
            hash = hash*31 + data.charAt(i);
            hash %= TABLE_SIZE;
        }
        return hash;
    }

    public boolean contains(String data) {
        int index;
        if (this.isSimple) {
            index = simpleHash(data);
        }
        else {
            index = hash(data);
        }

        return this.arr[index].search(data);
    }

    public void add(String data) {
        if (!contains(data)) {
            int index;
            if (this.isSimple) {
                index = simpleHash(data);
            } else {
                index = hash(data);
            }

            this.arr[index].insertFirst(data);
        }
    }

    public void remove(String data) {
        int index;
        if (this.isSimple) {
            index = simpleHash(data);
        }
        else {
            index = hash(data);
        }

        this.arr[index].deleteKey(data);
    }

    public void print() {
        System.out.println("INDEX | DATA");
        System.out.println("------------");
        for (int i = 0; i < TABLE_SIZE; i++)
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
//        com.ezranewman.datastructures.spellcheck.src.StringHashTable table1 = new com.ezranewman.datastructures.spellcheck.src.StringHashTable();
//
//        // *************
//        // Insert tests:
//        // *************
//        // Insert some values to the table
//        System.out.println("Test 1: ");
//        table1.add("5");
//        table1.add("7");
//        table1.add("3");
//        table1.print();
//        // INDEX | DATA
//        // ------------
//        //     0 | null
//        //     1 | null
//        //     2 | null
//        //     3 | 3 -> null
//        //     4 | null
//        //     5 | 5 -> null
//        //     6 | null
//        //     7 | 7 -> null
//        //     8 | null
//        //     9 | null
//
//        // Insert some values that need to be properly hashed to
//        // fit in the table
//        System.out.println("Test 2: ");
//        table1.add("11");
//        table1.add("26");
//        table1.add("34");
//        table1.print();
//        // INDEX | DATA
//        // ------------
//        //     0 | null
//        //     1 | 11 -> null
//        //     2 | null
//        //     3 | 3 -> null
//        //     4 | 34 -> null
//        //     5 | 5 -> null
//        //     6 | 26 -> null
//        //     7 | 7 -> null
//        //     8 | null
//        //     9 | null
//
//        // Insert some values whose hashed values are occupied
//        // They should just be appended to the appropriate linked list
//        System.out.println("Test 3: ");
//        table1.add("21");
//        table1.add("36");
//        table1.add("49");
//        table1.print();
//        // INDEX | DATA
//        // ------------
//        //     0 | null
//        //     1 | 11 -> 21 -> null
//        //     2 | null
//        //     3 | 3 -> null
//        //     4 | 34 -> null
//        //     5 | 5 -> null
//        //     6 | 26 -> 36 -> null
//        //     7 | 7 -> null
//        //     8 | null
//        //     9 | 49 -> null
//
//        // *************
//        // Search tests:
//        // *************
//        // Search should already work, so if this doesn't work
//        // it's probably something wrong with your insert
//        System.out.println("Test 4:");
//        System.out.println(table1.contains("31")); //  false
//        System.out.println(table1.contains("11")); //  true
//        System.out.println(table1.contains("21")); //  true
//        System.out.println(table1.contains("3")); //   true
//        System.out.println(table1.contains("131")); // false
//        System.out.println(table1.contains("226")); // false
//        System.out.println(table1.contains("2")); //   false
//        System.out.println(table1.contains("4")); //   false
//        System.out.println();
//
//        // *************
//        // delete tests:
//        // *************
//        System.out.println("Test 5:");
//
//        // Delete a numbers in the table
//        table1.remove("11");
//        table1.remove("36");
//        table1.remove("3");
//
//        // Then remove a number that is not in the table
//        table1.remove("100");
//
//        table1.print();
//        // INDEX | DATA
//        // ------------
//        //     0 | null
//        //     1 | 21 -> null
//        //     2 | null
//        //     3 | null
//        //     4 | 34 -> null
//        //     5 | 5 -> null
//        //     6 | 26 -> null
//        //     7 | 7 -> null
//        //     8 | null
//        //     9 | 49 -> null
    }
}

class StringLinkedList
{
    // instance variables
    StringNode head;

    public StringLinkedList()
    {
        head = null;
    }

    public boolean isEmpty()
    {
        return (head == null);
    }

    // Search the linked list for the specified key
    // return true if key is found, false otherwise
    public boolean search(String key)
    {
        StringNode current = head;

        while (current != null)
        {
            if (current.data.equals(key))
            {
                return true;
            }
            current = current.next;
        }

        return false;
    }

    public void insertFirst(String data) {
        StringNode newNode = new StringNode(data);
        newNode.next = head;
        head = newNode;
    }

    // Insert value to tail of the link list
    public void insertLast(String data)
    {
        //make a new Node
        StringNode newNode = new StringNode(data);

        // Edge case: list is empty
        if (head == null)
        {
            head = newNode;
            return;
        }
        else
        {
            // find the tail
            StringNode current = head;
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
    public boolean deleteKey(String key)
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
        StringNode prev = head;
        StringNode current = head.next;

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
        StringNode current = head;
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

class StringNode
{
    String data;
    StringNode next;

    public StringNode(String data)
    {
        this.data = data;
        this.next = null;
    }

    public StringNode(String data, StringNode next)
    {
        this.data = data;
        this.next = next;
    }
}
package com.ezranewman.CustomDataStructreImplementations;/* TODO: It would be way cleaner to do this with generics and implement the collections interface,
     but it was 10:30 when I started this and ain't nobody got time for that */


public class IntLinkedList {
    static int count = 0;
    private IntNode head;

    public IntLinkedList() {
        head = null;
    }

    // Returns the total number of nodes in the Linked List
    public int countNodes() {
        // If the head is null, the list is empty
        if (head == null) {
            return 0;
        } else {
            int count = 1;
            IntNode current = head;

            // We loop through the whole linked list until we get to the end
            while (current.getNext() != null) {
                current = current.getNext();
                count += 1;
            }

            return count;
        }
    }

    // Inserts a new node at the front of the ezranewmanCustomDataStructreImplementations.LinkedList (prepend)
    public void insertFirst(int data) {
        IntNode newNode = new IntNode(data);
        newNode.setNext(head);
        head = newNode;
    }

    // Inserts a new node at the back of the ezranewmanCustomDataStructreImplementations.LinkedList (append)
    public void insertLast(IntNode data) {
        if (head == null) {
            head = data;

        } else {
            IntNode current = head;

            // We loop through the whole linked list until we get to the end
            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.insertAfter(data);
        }
    }

    // Inserts a new node at the back of the ezranewmanCustomDataStructreImplementations.LinkedList (append)
    public void insertLast(int data) {
        IntNode newNode = new IntNode(data);
        insertLast(newNode);
    }

    // Returns true if val is present in the list, and false otherwie
    public boolean search(IntNode val) {
        IntNode current = head;
        // We loop through the whole linked list until we get to the end
        while (current != null) {
            if (current.getData() == val.getData()) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // Returns true if val is present in the list, and false otherwie
    public boolean search(int val) {
        IntNode valNode = new IntNode(val);
        return search(valNode);
    }

    // Removes the first node from the ezranewmanCustomDataStructreImplementations.LinkedList, and returns its data value
    public IntNode removeFirstAsNode() {
        IntNode first = head;
        if (head == null) {
            return null;
        }
        head = head.getNext();
        
        return first;
    }

    // Removes the first node from the ezranewmanCustomDataStructreImplementations.LinkedList, and returns its data value
    public int removeFirst() {
        IntNode out = removeFirstAsNode();
        if (out == null) {
            return -1;
        }
        return out.getData();
    }

    // Removes the last node from the ezranewmanCustomDataStructreImplementations.LinkedList, and returns its data value
    public IntNode removeLastAsNode() {
        IntNode current = head;

        if (current == null) {
            return null;
        }

        if (current.getNext() == null) {
            head = null;
            return current;
        }

        // We loop through the whole linked list until we get to the end
        while (current.getNext().getNext() != null) {
            current = current.getNext();
        }
        IntNode out = current;
        current.setNext(null);
        return out;
    }

    public int removeLast() {
        IntNode last = removeLastAsNode();
        if (last == null) {
            return -1;
        }
        return last.getData();
    }


    public void removeAll(IntNode val) {
        IntNode current = head;

        if (current == null) {
            return;
        } else {
            while (current.getData() == val.getData()){
                head = current.getNext();
                current = head;
                if(current == null){
                    return;
                }
            }

            IntNode previous = null;
            // We loop through the whole linked list until we get to the end
            while (current != null) {
                if (current.getData() == val.getData()) {
                    previous.setNext(current.getNext());
                }
                previous = current;
                current = current.getNext();
            }
        }
    }

    public void removeAll(int val) {
        IntNode valNode = new IntNode(val);
        removeAll(valNode);
    }

    public void reverse() {
        IntNode current = head;
        if(current == null){
            return;
        }
        IntNode previous = null;
        IntNode next = current.getNext();
        while (current.getNext() != null){
            current.setNext(previous);
            previous = current;
            current = next;
            next = current.getNext();
        }
        current.setNext(previous);
        head = current;
    }


    public boolean hasCycle(){
        count++;
        IntNode current = head;
        if(current == null){
            return false;
        }
        while (current.getNext() != null){
            if(current.getVisited() == count){
                return true;
            }
            current.setVisited(count);
            current = current.getNext();
        }
        return false;
    }

    // toString function that is called when ezranewmanCustomDataStructreImplementations.LinkedList is printed
    public String toString() {
        String str = "";
        IntNode current = head;
        while (current != null) {
            str = str + current.getData() + " -> ";
            current = current.getNext();
        }
        str += "null";
        return str;
    }

    public static void main(String[] args) {
        // Test 1: insertFirst method
        // We are going to make a list containing 4 -> 2 -> 3 -> null
        System.out.println("Test 1:");
        IntLinkedList list1 = new IntLinkedList();
        list1.insertFirst(3);
        list1.insertFirst(2);
        list1.insertFirst(4);
        System.out.println(list1); // 4 -> 2 -> 3 -> null

        // Test 2: insertLast method
        // We are going to make a list containing 7 -> 2 -> 5 -> 10 -> null
        System.out.println("\nTest 2:");
        IntLinkedList list2 = new IntLinkedList();
        list2.insertLast(7);
        list2.insertLast(2);
        list2.insertLast(5);
        list2.insertLast(10);
        System.out.println(list2); // 7 -> 2 -> 5 -> 10 -> null

        // Test 3: search method
        System.out.println("\nTest 3:");
        System.out.println(list2.search(7)); // true
        System.out.println(list2.search(2)); // true
        System.out.println(list2.search(5)); // true
        System.out.println(list2.search(10)); // true
        System.out.println(list2.search(13)); // false

        // Test 4: removeFirst method
        System.out.println("\nTest 4:");
        list1.removeFirst();
        System.out.println(list1); // 2 -> 3 -> null

        list2.removeFirst();
        System.out.println(list2); // 2 -> 5 -> 10 -> null
        list2.removeFirst();
        System.out.println(list2); // 5 -> 10 -> null

        // Test 5: removeLast method
        System.out.println("\nTest 5:");
        list1.removeLast();
        System.out.println(list1); // 2 -> null
        list1.removeLast();
        System.out.println(list1); // null

        list2.removeFirst();
        System.out.println(list2); // 10 -> null
        list2.removeFirst();
        System.out.println(list2); // null

        // Test 6: removeAll method
        System.out.println();
        System.out.println("Test 6:");
        IntLinkedList list3 = new IntLinkedList();
        list3.insertFirst(3);
        list3.insertFirst(4);
        list3.insertFirst(5);
        list3.insertFirst(6);
        list3.insertFirst(7);
        list3.removeAll(2);
        System.out.println(list3);

        list3 = new IntLinkedList();
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.removeAll(2);
        System.out.println(list3); // null

        list3 = new IntLinkedList();
        list3.insertFirst(3);
        list3.insertFirst(4);
        list3.insertFirst(5);
        list3.insertFirst(2);
        list3.insertFirst(6);
        list3.insertFirst(2);
        list3.insertFirst(7);
        list3.removeAll(2);
        System.out.println(list3); // 7 -> 6 -> 5 -> 4 -> 3 -> null

        // Test 6b: removeAll method
        System.out.println("\nTest 6b:");
        IntLinkedList list4 = new IntLinkedList();
        list4.insertLast(1);
        list4.insertLast(2);
        list4.insertLast(6);
        list4.insertLast(3);
        list4.insertLast(4);
        list4.insertLast(6);
        list4.insertLast(5);
        list4.insertLast(6);
        list4.removeAll(6);
        System.out.println(list4); // 1 -> 2 -> 3 -> 4 -> 5 -> null

        list4 = new IntLinkedList();
        list4.insertLast(6);
        list4.insertLast(5);
        list4.insertLast(3);
        list4.insertLast(6);
        list4.insertLast(1);
        list4.insertLast(2);
        list4.insertLast(6);
        list4.insertLast(0);
        list4.removeAll(6);
        System.out.println(list4); // 5 -> 3 -> 1 -> 2 -> 0 -> null

        list4 = new IntLinkedList();
        list4.insertLast(6);
        list4.insertLast(6);
        list4.insertLast(6);
        list4.insertLast(6);
        list4.insertLast(6);
        list4.removeAll(6);
        System.out.println(list4); // null

        System.out.println();
        System.out.println("Test 7:");
        list3.reverse();
        System.out.println(list3); // 3 -> 4 -> 5 -> 6 -> 7 -> null



// Test 7: reverse
        System.out.println("\nTest 7b:");
        IntLinkedList list5 = new IntLinkedList();
        list5.insertLast(3);
        list5.insertLast(2);
        list5.insertLast(4);
        list5.reverse();
        System.out.println(list5); // 4 -> 2 -> 3 -> null

// Test8: hasCycle
        System.out.println("\nTest 8:");
        IntLinkedList list6 = new IntLinkedList();
        list6.insertLast(3);
        list6.insertLast(2);
        list6.insertLast(4);
        list6.head.getNext().getNext().setNext(list6.head);
        System.out.println(list6.hasCycle()); // true

        list6 = new IntLinkedList();
        list6.insertLast(3);
        list6.insertLast(2);
        list6.insertLast(4);
        list6.insertLast(5);
        list6.head.getNext().getNext().getNext().setNext(list6.head.getNext());
        System.out.println(list6.hasCycle()); // true

        list6 = new IntLinkedList();
        list6.insertLast(3);
        list6.insertLast(2);
        list6.insertLast(4);
        list6.insertLast(5);
        System.out.println(list6.hasCycle()); // false
    }
}



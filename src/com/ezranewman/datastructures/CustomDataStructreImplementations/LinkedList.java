package com.ezranewman.datastructures.CustomDataStructreImplementations;

public class LinkedList<T> implements Stack<T>, Queue<T>{
    static int count = 0;
    private Node<T> head;

    public LinkedList() {
        head = null;
    }


    @Override
    public void push(T data) {
        insertFirst(data);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public T peek() {
        return head.getData();
    }

    @Override
    public boolean isEmpty() {
        return head==null;
    }


    @Override
    public void enqueue(T data) {
        insertLast(data);
    }

    @Override
    public T dequeue() {
        return pop();
    }

    // Returns the total number of nodes in the Linked List
    public int countNodes() {
        // If the head is null, the list is empty
        if (head == null) {
            return 0;
        } else {
            int count = 1;
            Node<T> current = head;

            // We loop through the whole linked list until we get to the end
            while (current.getNext() != null) {
                current = current.getNext();
                count += 1;
            }

            return count;
        }
    }

    // Inserts a new node at the front of the ezranewmanCustomDataStructreImplementations.LinkedList (prepend)
    public void insertFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(head);
        head = newNode;
    }

    // Inserts a new node at the back of the ezranewmanCustomDataStructreImplementations.LinkedList (append)
    public void insertLast(Node<T> data) {
        if (head == null) {
            head = data;

        } else {
            Node<T> current = head;

            // We loop through the whole linked list until we get to the end
            while (current.getNext() != null) {
                current = current.getNext();
            }

            current.insertAfter(data);
        }
    }

    // Inserts a new node at the back of the ezranewmanCustomDataStructreImplementations.LinkedList (append)
    public void insertLast(T data) {
        Node<T> newNode = new Node<>(data);
        insertLast(newNode);
    }

    // Returns true if val is present in the list, and false otherwie
    public boolean search(Node<T> val) {
        Node<T> current = head;
        // We loop through the whole linked list until we get to the end
        while (current != null) {
            if (current.getData().equals(val.getData())) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // Returns true if val is present in the list, and false otherwie
    public boolean search(T val) {
        Node<T> valNode = new Node<>(val);
        return search(valNode);
    }

    // Removes the first node from the ezranewmanCustomDataStructreImplementations.LinkedList, and returns its data value
    public Node <T> removeFirstAsNode() {
        Node<T> first = head;
        if (head == null) {
            return null;
        }
        head = head.getNext();
        return first;
    }

    // Removes the first node from the ezranewmanCustomDataStructreImplementations.LinkedList, and returns its data value
    public T removeFirst() {
        Node<T> out = removeFirstAsNode();
        if (out == null) {
            return null;
        }
        return out.getData();
    }

    // Removes the last node from the ezranewmanCustomDataStructreImplementations.LinkedList, and returns its data value
    public Node<T> removeLastAsNode() {
        Node<T> current = head;

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
        Node<T> out = current.getNext();
        current.setNext(null);
        return out;
    }

    public T removeLast() {
        Node<T> last = removeLastAsNode();
        if (last == null) {
            return null;
        }
        return last.getData();
    }


    public void removeAll(Node<T> val) {
        Node<T> current = head;

        if (current != null) {
            while (current.getData().equals(val.getData())) {
                head = current.getNext();
                current = head;
                if (current == null) {
                    return;
                }
            }

            Node<T> previous = null;
            // We loop through the whole linked list until we get to the end
            while (current != null) {
                if (current.getData() == val.getData()) {
                    assert previous != null;
                    previous.setNext(current.getNext());
                }
                previous = current;
                current = current.getNext();
            }
        }
    }

    public void removeAll(T val) {
        Node<T> valNode = new Node<>(val);
        removeAll(valNode);
    }

    public void reverse() {
        Node<T> current = head;
        if (current == null) {
            return;
        }
        Node<T> previous = null;
        Node<T> next = current.getNext();
        while (current.getNext() != null) {
            current.setNext(previous);
            previous = current;
            current = next;
            next = current.getNext();
        }
        current.setNext(previous);
        head = current;
    }


    public boolean hasCycle() {
        count++;
        Node<T> current = head;
        if (current == null) {
            return false;
        }
        while (current.getNext() != null) {
            if (current.getVisited() == count) {
                return true;
            }
            current.setVisited(count);
            current = current.getNext();
        }
        return false;
    }

    // toString function that is called when ezranewmanCustomDataStructreImplementations.LinkedList is printed
    public String toString() {
        StringBuilder str = new StringBuilder();
        Node<T> current = head;
        while (current != null) {
            str.append(current.getData()).append(" -> ");
            current = current.getNext();
        }
        str.append("null");
        return str.toString();
    }

    public static void main(String[] args) {
        // Test 1: insertFirst method
        // We are going to make a list containing 4 -> 2 -> 3 -> null
        System.out.println("Test 1:");
        LinkedList<Integer> list1 = new LinkedList<>();
        list1.insertFirst(3);
        list1.insertFirst(2);
        list1.insertFirst(4);
        System.out.println(list1); // 4 -> 2 -> 3 -> null

        // Test 2: insertLast method
        // We are going to make a list containing 7 -> 2 -> 5 -> 10 -> null
        System.out.println("\nTest 2:");
        LinkedList<Integer> list2 = new LinkedList<>();
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
        LinkedList<Integer> list3 = new LinkedList<>();
        list3.insertFirst(3);
        list3.insertFirst(4);
        list3.insertFirst(5);
        list3.insertFirst(6);
        list3.insertFirst(7);
        list3.removeAll(2);
        System.out.println(list3);

        list3 = new LinkedList<>();
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.insertFirst(2);
        list3.removeAll(2);
        System.out.println(list3); // null

        list3 = new LinkedList<>();
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
        LinkedList<Integer> list4 = new LinkedList<>();
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

        list4 = new LinkedList<>();
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

        list4 = new LinkedList<>();
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
        LinkedList<Integer> list5 = new LinkedList<>();
        list5.insertLast(3);
        list5.insertLast(2);
        list5.insertLast(4);
        list5.reverse();
        System.out.println(list5); // 4 -> 2 -> 3 -> null

// Test8: hasCycle
        System.out.println("\nTest 8:");
        LinkedList<Integer> list6 = new LinkedList<>();
        list6.insertLast(3);
        list6.insertLast(2);
        list6.insertLast(4);
        list6.head.getNext().getNext().setNext(list6.head);
        System.out.println(list6.hasCycle()); // true

        list6 = new LinkedList<>();
        list6.insertLast(3);
        list6.insertLast(2);
        list6.insertLast(4);
        list6.insertLast(5);
        list6.head.getNext().getNext().getNext().setNext(list6.head.getNext());
        System.out.println(list6.hasCycle()); // true

        list6 = new LinkedList<>();
        list6.insertLast(3);
        list6.insertLast(2);
        list6.insertLast(4);
        list6.insertLast(5);
        System.out.println(list6.hasCycle()); // false
        System.out.println();


        // Generic Classes Test
        System.out.println("Generic classes test");
        LinkedList<String> stringList = new LinkedList<>();
        stringList.insertFirst("Apple");
        stringList.insertFirst("Orange");
        stringList.insertLast("Banana");
        System.out.println(stringList); // "Orange" -> "Apple" -> "Banana" -> null

        System.out.println(stringList.search("Apple")); // true
        System.out.println(stringList.search("Pineapple")); // false

        System.out.println(stringList.removeFirst()); // "Orange"
        System.out.println(stringList.removeLast()); // "Banana"
        System.out.println(stringList); // "Apple" -> null
        System.out.println();

        //doubles
        System.out.println("Doubles test");

        LinkedList<Double> doubleList = new LinkedList<>();
        doubleList.insertFirst(2.);
        doubleList.insertFirst(3.05);
        doubleList.insertFirst(123456789.987654321);
        System.out.println(doubleList.search(2.)); //true
        System.out.println(doubleList.removeFirst()); //123456789.987654321
        System.out.println(doubleList); // 3.05 -> 2.0 -> null


        System.out.println();

        //stack tests
        System.out.println("ezranewmanCustomDataStructreImplementations.Stack tests");
        Stack<Integer> stack = new LinkedList<>();
        stack.push(10);
        stack.push(5);
        stack.push(2);
        stack.push(7);

        System.out.println(stack.peek()); // 7

        while (!stack.isEmpty()) {
            System.out.println(stack.pop());
        }
        // 7
        // 2
        // 5
        // 10


        //queue tests
        System.out.println();
        System.out.println("ezranewmanCustomDataStructreImplementations.Queue tests");
        Queue<Integer> queue = new LinkedList<>();
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



/* TODO: It would be way cleaner to implement this with generics and implement the collections interface,
     but it was 10:30 when I started this and ain't nobody got time for that */


public class IntLinkedList {
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

    // Inserts a new node at the front of the LinkedList (prepend)
    public void insertFirst(int data) {
        IntNode newNode = new IntNode(data);
        newNode.setNext(head);
        head = newNode;
    }

    // Inserts a new node at the back of the LinkedList (append)
    public void insertLast(IntNode data) {
        if(head == null){
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

    // Inserts a new node at the back of the LinkedList (append)
    public void insertLast(int data) {
        IntNode newNode = new IntNode(data);
        insertLast(newNode);
    }

    // Returns true if val is present in the list, and false otherwie
    public boolean search(IntNode val) {
        IntNode current = head;
        // We loop through the whole linked list until we get to the end
        while (current != null) {
            if(current.getData() == val.getData()){
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

    // Removes the first node from the LinkedList, and returns its data value
    public int removeFirst() {
        return -1;
        // YOUR CODE HERE
    }

    // Removes the last node from the LinkedList, and returns its data value
    public int removeLast() {
        return -1;
        // YOUR CODE HERE
    }

    // toString function that is called when LinkedList is printed
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
    }
}



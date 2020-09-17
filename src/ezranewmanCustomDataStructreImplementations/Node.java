package ezranewmanCustomDataStructreImplementations;

public class Node<T> {
    private T data;
    private Node<T> next;
    private int visited;

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public void insertAfter(Node<T> toInsert){
        toInsert.next = next;
        next = toInsert;
    }

    public Node(T data) {
        this.data = data;
    }
}

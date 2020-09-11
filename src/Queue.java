public interface Queue<T> {
    // Enqueues a value to the end of the queue
    public void enqueue(T data);

    // Dequeues a value from the front of the queue
    public T dequeue();

    // Returns the data value at the front of the queue without dequeuing
    public T peek();

    // Checks whether or not the queue is empty
    public boolean isEmpty();
}

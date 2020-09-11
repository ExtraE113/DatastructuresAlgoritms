public interface Stack<T> {
    // Pushes some data value to the top of the Stack
    public void push(T data);

    // Pops some data value off the top of the Stack
    public T pop();

    // Returns the data value at the top of the stack without popping
    public T peek();

    // Checks whether or not the stack is empty
    public boolean isEmpty();
}

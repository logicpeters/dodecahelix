package org.ddx.ds;

/**
 * Stack implementation based on a singly-linked list structure.
 *
 */
public class LinkedStack<T> implements Stack<T> {

    private int size = 0;

    private LinkedNode<T> head;

    @Override
    public void push(T item) {
        // make the new head point to the previous head (or null if this is the first one)
        head = new LinkedNode<T>(item, head);
        size++;
    }

    @Override
    public T pop() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error popping from stack - stack underflow (stack is empty).");
        }
        T poppedValue = head.getValue();

        // replace head with next item in the list
        //  note that this could be setting the head to null!  (stack is now empty)
        //  old head is garbage collected
        head = head.getNext();
        size--;
        return poppedValue;
    }

    @Override
    public T peek() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error peeking into stack - stack underflow (stack is empty).");
        }
        return head.getValue();
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

}

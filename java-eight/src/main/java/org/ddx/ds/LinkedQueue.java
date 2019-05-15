package org.ddx.ds;

/**
 * Queue based on a singly-linked list structure.
 *
 */
public class LinkedQueue<T> implements Queue<T> {

    private int size = 0;

    private LinkedNode<T> head;

    private LinkedNode<T> tail;

    @Override
    public void add(T item) {
        LinkedNode<T> newTail = new LinkedNode<T>(item, null);
        if (tail != null) {
            tail.setNext(newTail);
        }
        tail = newTail;

        // if the queue is empty, this will also become the new head
        if (head == null) {
            head = newTail;
        }
        size++;
    }

    @Override
    public T remove() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error removing from queue - queue is empty.");
        }
        T oldHeadValue = head.getValue();
        head = head.getNext();  // note: this could be null!
        size--;
        return oldHeadValue;
    }

    @Override
    public T peek() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error removing from queue - queue is empty.");
        }
        return (T) head.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }
}

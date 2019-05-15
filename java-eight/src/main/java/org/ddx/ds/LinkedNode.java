package org.ddx.ds;

/**
 * Node class for elements in a singly linked list structure.
 *
 */
public class LinkedNode<T> {

    private T value;

    private LinkedNode<T> next;

    public LinkedNode(T value, LinkedNode next) {
        this.value = value;
        this.next = next;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LinkedNode<T> getNext() {
        return next;
    }

    public void setNext(LinkedNode<T> next) {
        this.next = next;
    }
}

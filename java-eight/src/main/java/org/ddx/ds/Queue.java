package org.ddx.ds;

/**
 * Simple queue interface where items are added to the "tail" of the queue,
 * and removed from the "head" of the queue, in a FIFO (first in, first out) format.
 *
 */
public interface Queue<T> {

    /**
     * Adds an item to the tail (end) of the queue.
     *
     * @param item
     */
    public void add(T item);

    /**
     * Removes the head item in the queue and returns its value.
     *
     * @return value of the item removed.
     * @throws UnderflowError - if the queue is empty
     */
    public T remove() throws UnderflowError;

    /**
     * Returns the value of the head item of the queue *without* removing it.
     *
     * @return value of the head item in the queue
     * @throws UnderflowError
     */
    public T peek() throws UnderflowError;

    /**
     * Get the current count of items in the queue.
     *
     * @return integer value of number of items in the queue.
     */
    public int size();

    /**
     * Return true/false whether the queue has any items.
     *
     * @return true/false whether the queue has any items.
     */
    public boolean isEmpty();

}

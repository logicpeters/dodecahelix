package org.ddx.ds;

/**
 * Stack data structure.
 */
public interface Stack<T> {

    /**
     * Adds an item to the top of the stack.
     *
     * @param item
     */
    public void push(T item);

    /**
     * Removes the top item from the stack (and returns the value)
     * <p>
     * The item beneath the top item becomes the new top.
     *
     * @return the item that was just removed.
     * @throws UnderflowError
     */
    public T pop() throws UnderflowError;

    /**
     * Returns the item at the top of the stack *without* removing it.
     *
     * @return - top item on stack
     * @throws UnderflowError
     */
    public T peek() throws UnderflowError;

    /**
     * Returns true/false whether the stack has any items.
     *
     * @return true/false whether the stack has any items.
     */
    public boolean isEmpty();

    /**
     * Returns a count of how many elements are in the stack.
     *
     * @return integer count of number of items on stack
     */
    public int size();

}

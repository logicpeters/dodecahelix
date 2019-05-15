package org.ddx.ds;

/**
 *   Stack implementation based on a singly Linked List data structure.
 *
 */
public class StringListStack implements Stack<String> {

    private int size = 0;

    private StringNode head;

    @Override
    public void push(String item) {
        // make the new head point to the previous head (or null if this is the first one)
        head = new StringNode(item, head);
        size++;
    }

    @Override
    public String pop() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error popping from stack - stack underflow (stack is empty).");
        }
        String poppedValue = head.getValue();
        // replace head with next item in the list (the old head will be garbage collected)
        //  note that this could be setting the head to null!  (stack is now empty)
        head = head.getNext();
        size--;
        return poppedValue;
    }

    @Override
    public String peek() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error peeking into stack - stack underflow (stack is empty).");
        }
        return head.getValue();
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }

    @Override
    public int size() {
        return size;
    }
}

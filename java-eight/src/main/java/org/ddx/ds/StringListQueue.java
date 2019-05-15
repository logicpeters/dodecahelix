package org.ddx.ds;

/**
 * Queue implementation based on a singly Linked List data structure.
 */
public class StringListQueue implements Queue<String> {

    private int size = 0;

    private StringNode head;

    private StringNode tail;

    @Override
    public void add(String item) {
        StringNode newTail = new StringNode(item, null);
        if (tail!=null) {
            tail.setNext(newTail);
        }
        tail = newTail;

        // if the queue is empty, this will also become the new head
        if (head==null) {
            head = newTail;
        }
        size++;
    }

    @Override
    public String remove() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error removing from queue - queue is empty.");
        }
        String oldHeadValue = head.getValue();
        head = head.getNext();  // note: this could be null!
        size--;
        return oldHeadValue;
    }

    @Override
    public String peek() throws UnderflowError {
        if (isEmpty()) {
            throw new UnderflowError("Error removing from queue - queue is empty.");
        }
        return head.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size==0);
    }
}

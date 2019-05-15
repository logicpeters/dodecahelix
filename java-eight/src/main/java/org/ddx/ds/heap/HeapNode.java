package org.ddx.ds.heap;

/**
 * Represents a node inside of a binary tree.
 * <p>
 * Consists of a value (of some arbitrary type, a left and right child).
 */
public abstract class HeapNode<T> {

    /**
     * Value of the node, for comparison
     */
    private T value;

    public HeapNode(T value) {
        this.value = value;
    }

    /**
     * Resolve whether this node is 'smaller' than another node.
     * <p>
     * Determines precedence rules in Heaps.
     *
     * @param that
     * @return
     */
    public abstract boolean isSmallerThan(HeapNode<T> that);

    public T getValue() {
        return value;
    }

}

package org.ddx.ds.heap;

/**
 * A heap node which only stores an integer value and uses
 * simple comparisons (less than or equal)
 *
 */
public class SimpleIntegerHeapNode extends HeapNode<Integer> {

    public SimpleIntegerHeapNode(int value) {
        super(value);
    }

    @Override
    public boolean isSmallerThan(HeapNode<Integer> that) {
        return this.getValue() < that.getValue();
    }

}

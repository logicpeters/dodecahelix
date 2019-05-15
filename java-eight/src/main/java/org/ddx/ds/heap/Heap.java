package org.ddx.ds.heap;

/**
 * Interface for a Heap structure.
 *
 */
public interface Heap<T> {

    public void add(HeapNode<T> node) throws HeapException;

    public HeapNode<T> remove() throws HeapException;
}

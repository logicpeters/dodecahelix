package org.ddx.ds.sorts;


import org.ddx.ds.Queue;
import org.ddx.ds.UnderflowError;
import org.ddx.ds.heap.HeapException;

/**
 * Interface for the various sorting methods used in this exercise.
 *
 */
public interface Sort {

    public TimedSortResult sort(Queue<Integer> inputList) throws UnderflowError, HeapException;
}

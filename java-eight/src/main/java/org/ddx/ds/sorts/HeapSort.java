package org.ddx.ds.sorts;

import org.ddx.ds.Queue;
import org.ddx.ds.UnderflowError;
import org.ddx.ds.heap.HeapException;
import org.ddx.ds.heap.SimpleIntegerHeapNode;
import org.ddx.ds.heap.SimpleIntegerMinHeap;

/**
 * Runs the Heap Sort algorithm and records the time to sort.
 *
 */
public class HeapSort implements Sort {

    @Override
    public TimedSortResult sort(Queue<Integer> inputList) throws UnderflowError, HeapException {
        int[] sorted = new int[inputList.size()];

        long startTime = System.nanoTime();
        SimpleIntegerMinHeap minHeap = new SimpleIntegerMinHeap(inputList.size());
        while (!inputList.isEmpty()) {
            minHeap.add(new SimpleIntegerHeapNode(inputList.remove()));
        }
        int idx = 0;
        while (minHeap.getCurrentSize() > 0) {
            sorted[idx] = minHeap.remove().getValue();
            idx++;
        }
        long endTime = System.nanoTime();

        return new TimedSortResult(sorted, endTime - startTime);
    }
}

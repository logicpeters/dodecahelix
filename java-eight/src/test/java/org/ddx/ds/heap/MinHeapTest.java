package org.ddx.ds.heap;

import org.junit.Test;

import java.util.Random;


public class MinHeapTest {

    @Test
    public void testMinHeapUsingRandomNumbers() throws HeapException {

        Random rand = new Random();

        int testCapacity = 100;
        SimpleIntegerMinHeap heap = new SimpleIntegerMinHeap(testCapacity + 1);
        for (int i = 0; i < testCapacity; i++) {
            int testFreq = rand.nextInt(1000);
            heap.add(new SimpleIntegerHeapNode(testFreq));
        }

        assert heap.validateHeap();

        int testNumber = 0;
        for (int i = 0; i < testCapacity; i++) {
            int num = heap.remove().getValue();
            assert num >= testNumber;
            testNumber = num;
            assert heap.validateHeap();
        }

        assert heap.getCurrentSize() == 0;
    }
}

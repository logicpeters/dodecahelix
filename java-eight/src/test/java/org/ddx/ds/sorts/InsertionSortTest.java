package org.ddx.ds.sorts;

import org.ddx.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 */
public class InsertionSortTest {

    @Test
    public void testInsertionSort() {
        for (int i=0; i<100; i++) {
            int[] unsorted = TestHelper.generateRandomArray(100,100);
            QuickSort qkSort = new QuickSort(0, PivotSelectionStrategy.END_INDEX);
            int[] sorted = qkSort.insertionSortArray(unsorted, 0, unsorted.length-1);

            int[] compareTo = unsorted.clone();
            Arrays.sort(compareTo);
            for (int j=0; j<compareTo.length; j++) {
                Assert.assertEquals(sorted[j], compareTo[j]);
            }
        }
    }

}

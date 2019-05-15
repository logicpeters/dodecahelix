package org.ddx.ds.sorts;

import org.ddx.TestHelper;
import org.ddx.ds.UnderflowError;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created on 5/2/2019.
 */
public class QuickSortTest {

    @Test
    public void testQuickSortFull() throws UnderflowError {
        for (int i = 0; i < 100; i++) {
            int[] testArr = TestHelper.generateRandomArray(1000, 1000);

            QuickSort qs = new QuickSort(2, PivotSelectionStrategy.END_INDEX);

            //TestHelper.printArrayPortion(testArr, 0, testArr.length - 1, -1);
            int[] output = qs.quicksortArray(testArr);
            //TestHelper.printArrayPortion(output, 0, output.length - 1, -1);

            int[] outCopy = testArr.clone();

            // compare output to java's built in sort output
            Arrays.sort(outCopy);
            for (int j = 0; j < output.length; j++) {
                assert output[j] == outCopy[j];
            }
        }
    }

    @Test
    public void testQuickSortWithInsertionSortAtEnd() throws UnderflowError {
        for (int i = 0; i < 100; i++) {
            int[] testArr = TestHelper.generateRandomArray(1000, 1000);

            QuickSort qs = new QuickSort(50, PivotSelectionStrategy.END_INDEX);

            //TestHelper.printArrayPortion(testArr, 0, testArr.length - 1, -1);
            int[] output = qs.quicksortArray(testArr);
            //TestHelper.printArrayPortion(output, 0, output.length - 1, -1);

            int[] outCopy = testArr.clone();

            // compare output to java's built in sort output
            Arrays.sort(outCopy);
            for (int j = 0; j < output.length; j++) {
                assert output[j] == outCopy[j];
            }
        }
    }
}

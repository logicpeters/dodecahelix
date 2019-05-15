package org.ddx.ds.sorts;

import org.ddx.ds.LinkedStack;
import org.ddx.ds.Queue;
import org.ddx.ds.Stack;
import org.ddx.ds.UnderflowError;

/**
 * Custom Quicksort Algorithm
 * <p>
 * Uses an *iterative* approach, and runs in-place against an array of integers.
 *
 */
public class QuickSort implements Sort {

    private int stoppingSize;
    private PivotSelectionStrategy pivotStrategy;

    public QuickSort(int stoppingPartitionSize, PivotSelectionStrategy pivotStrategy) {
        this.stoppingSize = stoppingPartitionSize;
        this.pivotStrategy = pivotStrategy;
    }

    private static void swapValues(int[] array, int idxOne, int idxTwo) {
        int tmp = array[idxOne];
        array[idxOne] = array[idxTwo];
        array[idxTwo] = tmp;
    }

    @Override
    public TimedSortResult sort(Queue<Integer> inputList) throws UnderflowError {
        int[] unsorted = new int[inputList.size()];

        int idx = 0;
        while (!inputList.isEmpty()) {
            unsorted[idx] = inputList.remove();
            idx++;
        }

        long startTime = System.nanoTime();
        int[] sorted = quicksortArray(unsorted);
        long endTime = System.nanoTime();

        return new TimedSortResult(sorted, endTime - startTime);
    }

    public int[] quicksortArray(int[] input) throws UnderflowError {
        // in-place iteration sorting of the input

        // use a stack to hold the start and end *indices* of the qsort partitions.
        //  this lets us use an iterative solution instead of a recursive one.
        Stack<Integer> partIndices = new LinkedStack<>();
        partIndices.push(0);
        partIndices.push(input.length - 1);

        while (!partIndices.isEmpty()) {
            // grab the indices of the next partition in the stack
            int endIdx = partIndices.pop();
            int startIdx = partIndices.pop();

            if ((startIdx + stoppingSize) >= endIdx) {
                // small partition, use a simple insertion sort on the rest

                if ((endIdx - startIdx) == 1) {
                    // only two values, compare and swap if necessary
                    if (input[startIdx] > input[endIdx]) {
                        swapValues(input, startIdx, endIdx);
                    }
                } else if ((endIdx - startIdx > 1)) {
                    // run an insertion sort to finish up this partition
                    input = insertionSortArray(input, startIdx, endIdx);
                }
            } else {
                // partition
                int pivotIdx = choosePivot(input, startIdx, endIdx);
                int pivotValue = input[pivotIdx];

                // now, lets swap this pivot with the end of the array
                // and we will swap this one at the end
                swapValues(input, pivotIdx, endIdx);

                // everything less than the pivot value goes to the left
                // everything greater than goes to the right of the pivot
                int lowerIdx = startIdx;
                int upperIdx = endIdx - 1;

                // loop until the two pointers meet.
                while (lowerIdx < upperIdx) {

                    // move lowerIdx up until and stop if we find something out of place
                    while (lowerIdx < endIdx && input[lowerIdx] <= pivotValue) {
                        // correct position.  move on to next value.
                        lowerIdx++;
                    }

                    // move upper index down and stop if we find one that is out of place
                    while (upperIdx > startIdx && input[upperIdx] > pivotValue) {
                        upperIdx--;
                    }

                    // check to make sure we aren't overlapping
                    if (lowerIdx < upperIdx) {
                        swapValues(input, lowerIdx, upperIdx);
                    }
                }

                // at this point, we expect the lowerIndex to
                // be where the pivot should be and greater than the pivot value
                if (input[lowerIdx] >= pivotValue) {
                    swapValues(input, lowerIdx, endIdx);
                }

                if (startIdx < lowerIdx) {
                    partIndices.push(startIdx);
                    partIndices.push(lowerIdx - 1);
                }

                if ((lowerIdx + 1) < endIdx) {
                    partIndices.push(lowerIdx + 1);
                    partIndices.push(endIdx);
                }
            }
        }

        return input;
    }

    /**
     * Run an in-place insertion sort on the portion of the array between the two indices.
     *
     * @param array
     * @param startIdx
     * @param endIdx
     * @return the array, sorted between the two indices
     */
    int[] insertionSortArray(int[] array, int startIdx, int endIdx) {
        for (int i = startIdx + 1; i <= endIdx; i++) {
            int j = i;
            while (j > startIdx && array[j] < array[j - 1]) {
                swapValues(array, j, j - 1);
                j--;
            }
        }
        return array;
    }

    private int choosePivot(int[] array, int startIdx, int endIdx) {

        switch (this.pivotStrategy) {
            case START_INDEX:
                return startIdx;
            case END_INDEX:
                return endIdx;
        }

        // use median of three strategy
        int x = array[startIdx];
        int z = array[endIdx];
        int midIdx = (startIdx + endIdx) / 2;
        int y = array[midIdx];

        if (x < y && x < z) {
            // start is lowest, so one of the other two must be mid
            if (y < z) return midIdx;
            return endIdx;
        }
        if (y < x && y < z) {
            // mid is lowest
            if (x < z) return startIdx;
            return endIdx;
        } else {
            // end is lowest
            if (x < y) return startIdx;
            return midIdx;
        }
    }


}

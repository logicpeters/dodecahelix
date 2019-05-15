package org.ddx.ds.sorts;

/**
 * Container for sort results and the time to sort.
 *
 */
public class TimedSortResult {

    private int[] sorted;
    private long timeToSort;  // nanoseconds to sort

    public TimedSortResult(int[] sorted, long timeToSort) {
        this.sorted = sorted;
        this.timeToSort = timeToSort;
    }

    public int[] getSorted() {
        return sorted;
    }

    public long getTimeToSort() {
        return timeToSort;
    }

    public void setTimeToSort(long timeToSort) {
        this.timeToSort = timeToSort;
    }
}

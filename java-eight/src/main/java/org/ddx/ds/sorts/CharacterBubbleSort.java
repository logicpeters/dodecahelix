package org.ddx.ds.sorts;

/**
 *  Bubblesort algorithm to sort the individual characters of a string.
 *
 */
public class CharacterBubbleSort {

    /**
     * Utility method to take a String and sort it by its individual characters.
     * <p>
     * Uses a simple bubble sort.
     *
     * @param toSort - string to sort
     * @return - original string with its individual characters in natural order
     */
    public static String sortCharsInString(String toSort) {
        boolean sorted = false;
        char[] unsorted = toSort.toCharArray();
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < (unsorted.length - 1); i++) {
                if (unsorted[i] > unsorted[i + 1]) {
                    // out of order, swap
                    char tmp = unsorted[i];
                    unsorted[i] = unsorted[i + 1];
                    unsorted[i + 1] = tmp;
                    // there is at least one unsorted element this pass.  Must retry.
                    sorted = false;
                }
            }
        }
        return new String(unsorted);
    }

}

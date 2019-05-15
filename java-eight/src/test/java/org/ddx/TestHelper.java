package org.ddx;

import java.util.Random;

/**
 * Static methods to help out during testing.
 *
 */
public class TestHelper {

    public static int[] generateRandomArray(int numElements, int valueRange) {
        Random rr = new Random();
        int[] testArr = new int[numElements];
        for (int j = 0; j < numElements; j++) {
            testArr[j] = rr.nextInt(valueRange);
        }
        return testArr;
    }

    public static void printArrayPortion(int[] array, int startIdx, int endIdx, int highlightIdx) {
        System.out.print("  ");
        for (int i = startIdx; i <= endIdx; i++) {
            int val = array[i];
            if (i == highlightIdx) {
                System.out.print(" *" + val + "*");
            } else {
                System.out.print(" " + val);
            }
        }
        System.out.println();
    }
}

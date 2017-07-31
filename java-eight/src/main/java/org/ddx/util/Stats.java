package org.ddx.util;

/**
 * Created on 7/30/2017.
 */
public class Stats {

    public static double mean(double[] results) {
        double total = 0;
        for (double result : results) {
            total += result;
        }
        return total / results.length;
    }

    public static double standardDeviation(double[] results) {
        double mean = mean(results);
        double total = 0;
        for (double result : results) {
            total += ((mean-result)*(mean-result));
        }
        return Math.sqrt(total / (results.length-1));
    }

}

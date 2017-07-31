package org.ddx.algorithms.union;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created on 7/30/2017.
 */
public class PercolationTest {

    @Test
    public void test() {
        PercolationStats stats = new PercolationStats(100,100);
        assertEquals("0.59", String.valueOf(stats.mean()).substring(0,4));
        assertEquals("0.0", String.valueOf(stats.standardDeviation()).substring(0,3));
    }

}

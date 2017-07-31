package org.ddx.algorithms.union;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created on 7/27/2017.
 */
public class WgtAvgPathCmpTest {

    @Test
    public void test() {
        int numPoints = 5;
        WgtAvgPathCmpUnionFind wapcAlg = new WgtAvgPathCmpUnionFind(numPoints);
        wapcAlg.union(1,3);
        wapcAlg.union(2,4);
        assertEquals(3, wapcAlg.count());

        wapcAlg.union(0, 4);
        assertEquals(2, wapcAlg.count());

        int largestComponentId = wapcAlg.find(4);
        wapcAlg.union(3, 4);
        assertEquals(largestComponentId, wapcAlg.find(1));
        assertEquals(1, wapcAlg.count());
    }
}

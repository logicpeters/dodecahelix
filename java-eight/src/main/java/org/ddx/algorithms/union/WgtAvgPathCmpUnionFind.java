package org.ddx.algorithms.union;

import java.util.Arrays;

/**
 *  Weighted Path Union Find with Path Compression Algorithm described in the Union-Find class of Coursera's Algorithms, Part I.
 */
public class WgtAvgPathCmpUnionFind implements UnionFind {

    // each element in this array is the component ID (in this case, the root point) for the point specified by the index
    int[] components;

    // for each point, this array maintains the number of points in the component/group for that point
    int[] componentSizes;

    public WgtAvgPathCmpUnionFind(int numberOfPoints) {
        components = new int[numberOfPoints];
        componentSizes = new int[numberOfPoints];

        // initialize the component ID to be the point itself (each point's root is itself)
        // the size of each component will be only 1 element
        for (int i=0; i<numberOfPoints; i++) {
            components[i] = i;
            componentSizes[i] = 1;
        }
    }

    @Override
    public void union(int aPoint, int bPoint) {
        int aComponentId = find(aPoint);
        int bComponentId = find(bPoint);
        if (aComponentId == bComponentId) return;

        // "Weighted Average" technique -- stick the smaller component onto the root of the larger component
        if (componentSizes[aComponentId] < componentSizes[bComponentId]) {
            components[aComponentId] = bComponentId;
            componentSizes[bComponentId] += componentSizes[aComponentId];
            // don't need to worry about updating componentSizes[aComponentId] -- it's of no use any more for this algorithm
        } else {
            components[bComponentId] = aComponentId;
            componentSizes[aComponentId] += componentSizes[bComponentId];
        }
    }

    @Override
    public boolean connected(int aPoint, int bPoint) {
        // returns true if they are in the same group/component
        return (find(aPoint) == find(bPoint));
    }

    /**
     * Returns the component ID for the specified point.
     *
     * In this implementation the "component ID" is the root point in the tree that this point is grouped in.
     *
     * @param pointId
     * @return
     */
    @Override
    public int find(int pointId) {
        int initialPoint = pointId;

        // returns the root point (which is the component ID)
        while (pointId != components[pointId]) {
            pointId = components[pointId];
        }
        int rootComponentId = pointId;

        // second-pass -- the "path compression" technique
        pointId = initialPoint;
        while (pointId != rootComponentId) {
            pointId = components[pointId];
            components[pointId] = rootComponentId;
        }
        return rootComponentId;
    }

    @Override
    public int count() {
        // finds all root points in the set
        int count = 0;
        for (int i=0; i<components.length; i++) {
            if (components[i]==i) { count++; }
        }
        return count;
    }
}

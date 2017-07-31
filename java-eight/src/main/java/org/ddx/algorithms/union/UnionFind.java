package org.ddx.algorithms.union;

/**
 *  Interface for algorithms implementing the Union Find problem.
 *
 *  Given a set of data points, which may or may not be connected
 *
 */
public interface UnionFind {

    /**
     * Connect the two given points in the set.
     *
     * @param aPoint
     * @param bPoint
     */
    public void union(int aPoint, int bPoint);

    /**
     * Whether or not the two points in the set are connected.
     *
     * @param aPoint
     * @param bPoint
     * @return
     */
    public boolean connected(int aPoint, int bPoint);

    /**
     * Returns an identifier for the component to which this point belongs.
     *
     * (When two points or more points are connected, they form a group or "component".
     *  The ID of this component must be unique for all components of the set.)
     *
     *
     * @param aPoint
     * @return
     */
    public int find(int aPoint);

    /**
     * Returns how many unique point compoments exist for the set.
     *
     * @return
     */
    public int count();
}

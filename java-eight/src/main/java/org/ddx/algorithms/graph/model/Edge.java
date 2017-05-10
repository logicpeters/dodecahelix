package org.ddx.algorithms.graph.model;

/**
 * Created on 5/9/2017.
 */
public class Edge {

    private long sourceNodeId;
    private long targetNodeId;
    private int cost = 0;
    private boolean bidirectional = true;

    public Edge(long sourceNode, long targetNode) {
        this.sourceNodeId = sourceNode;
        this.targetNodeId = targetNode;
    }

    public Edge(long sourceNode, long targetNode, int cost, boolean bidirectional) {
        this.sourceNodeId = sourceNode;
        this.targetNodeId = targetNode;
        this.cost = cost;
        this.bidirectional = bidirectional;
    }

    public long getSourceNodeId() {
        return sourceNodeId;
    }

    public long getTargetNodeId() {
        return targetNodeId;
    }

    public int getCost() {
        return cost;
    }

    public boolean isBidirectional() {
        return isBidirectional();
    }

}

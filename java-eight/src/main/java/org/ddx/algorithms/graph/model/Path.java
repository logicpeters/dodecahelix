package org.ddx.algorithms.graph.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a path between nodes in a graph.
 *
 */
public class Path {

    private long pathCost = 0;

    private List<Long> nodeIds = new ArrayList<>();

    public Path(long nodeId) {
        nodeIds.add(nodeId);
    }

    public Path(Path originalPath) {
        nodeIds.addAll(originalPath.getNodeIds());
    }

    public List<Long> getNodeIds() {
        return Collections.unmodifiableList(nodeIds);
    }

    public Stream<Long> pathStream() {
        return nodeIds.stream();
    }

    public void appendEdgeTarget(Edge edge) {
        nodeIds.add(edge.getTargetNodeId());
        pathCost += edge.getCost();
    }

    public void addNode(long nodeId, int cost) {
        nodeIds.add(nodeId);
        pathCost += cost;
    }

    public void addFreeNode(long nodeId) {
        nodeIds.add(nodeId);
    }

    public void addPath(Path path) {
        nodeIds.addAll(path.getNodeIds());
        pathCost += path.getCost();
    }

    public long getCost() {
        return pathCost;
    }

}

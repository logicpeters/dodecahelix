package org.ddx.algorithms.graph;

import java.util.*;

/**
 *  Simple starter class for creating graphs.
 *
 *  Useful for one-class assignments and code competitions.
 *
 */
public class SimpleGraph {

    Map<Long, Node> nodes = new HashMap<>();

    static class Path {
        long pathCost;
        List<Long> nodeIds = new ArrayList<>();
        public Path(long nodeId) { nodeIds.add(nodeId); }
        public Path(Path path) { nodeIds.addAll(path.nodeIds); }
    }

    static class Node {
        long id;
        Set<Edge> edges = new HashSet<Edge>();
        public Node(long id) { this.id = id; }
    }

    static class Edge {
        long sourceNodeId;
        long targetNodeId;
        int cost;
        boolean bidirectional = true;
        public Edge(long sourceNode, long targetNode) { this.sourceNodeId = sourceNode; this.targetNodeId = targetNode; }
    }

    public static void main(String[] args) {
    }
}

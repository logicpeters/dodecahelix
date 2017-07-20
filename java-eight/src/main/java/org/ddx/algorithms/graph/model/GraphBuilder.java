package org.ddx.algorithms.graph.model;

/**
 * Created on 5/10/2017.
 */
public class GraphBuilder {

    private Graph graph;

    public GraphBuilder() {
        graph = new Graph();
    }

    public GraphBuilder addNode(long nodeId) {
        graph.addNode(new Node(nodeId));
        return this;
    }

    public GraphBuilder connect(long sourceNodeId, long targetNodeId) {
        return connect(sourceNodeId, targetNodeId, 0);
    }

    public GraphBuilder connect(long sourceNodeId, long targetNodeId, int cost) {
        return connect(sourceNodeId, targetNodeId, cost, true);
    }

    public GraphBuilder connect(long sourceNodeId, long targetNodeId, int cost, boolean bidirectional) {
        Node sourceNode = graph.getNode(sourceNodeId);
        sourceNode.addEdge(new Edge(sourceNodeId, targetNodeId, cost, bidirectional));
        if (bidirectional) {
            Node targetNode = graph.getNode(targetNodeId);
            targetNode.addEdge(new Edge(targetNodeId, sourceNodeId, cost, true));
        }
        return this;
    }

    public Graph build() {
        return graph;
    }

}

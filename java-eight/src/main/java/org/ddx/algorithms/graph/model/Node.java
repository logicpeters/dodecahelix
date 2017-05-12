package org.ddx.algorithms.graph.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 *  Represents a node in a graph.
 *
 *  Uses a long primitive as its ID, and holds a strong reference to its associated edges.
 */
public class Node {

    private long id;
    private Set<Edge> edges = new HashSet<Edge>();

    public Node(long id) {
        this.id = id;
    }

    public Set<Edge> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public Stream<Edge> edgeStream() {
        return edges.stream();
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Node{" + id + "}";
    }
}

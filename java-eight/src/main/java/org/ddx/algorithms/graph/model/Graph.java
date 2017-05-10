package org.ddx.algorithms.graph.model;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Container for nodes and edges between nodes.
 *
 * This is a simple implementation intended to be used as a tool to learn graph theory.
 */
public class Graph {

    private Map<Long, Node> nodes = new HashMap<>();

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
    }

    public Node getNode(long id) {
        return nodes.get(id);
    }

    public Stream<Node> nodeStream() {
        return nodes.values().stream();
    }

    public Stream<Edge> edgeStream() {
        return nodes.values().stream().flatMap(node -> node.edgeStream());
    }

    public Collection<Node> getNodes() {
        return Collections.unmodifiableCollection(nodes.values());
    }

}

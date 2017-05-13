package org.ddx.algorithms.graph.grid;

import org.ddx.algorithms.graph.model.Edge;

/**
 * Created on 5/12/2017.
 */
public class SimpleGridEdgeCreationStrategy implements GridEdgeCreationStrategy {

    @Override
    public Edge createEdge(long sourceNodeId, long targetNodeId) {
        return new Edge(sourceNodeId, targetNodeId);
    }
}

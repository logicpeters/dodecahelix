package org.ddx.algorithms.graph.grid;

import org.ddx.algorithms.graph.model.Edge;
import org.ddx.algorithms.graph.model.Node;

/**
 * Created on 5/12/2017.
 */
public interface GridEdgeCreationStrategy {

    public Edge createEdge(long sourceNodeId, long targetNodeId);

}

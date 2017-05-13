package org.ddx.algorithms.graph.alg;

import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Path;

/**
 * Created on 5/9/2017.
 */
public interface GraphTraversalStrategy {

    /**
     * Find the path between two nodes.
     *
     * @param graph
     * @param startNode - id of the node to start
     * @param endNode - id of the node to finish
     * @return connected path between the two nodes
     */
    Path traverse(Graph graph, long startNode, long endNode);

}

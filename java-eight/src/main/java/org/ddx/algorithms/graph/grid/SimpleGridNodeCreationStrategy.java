package org.ddx.algorithms.graph.grid;

import org.ddx.algorithms.graph.model.Node;

/**
 * Created on 5/12/2017.
 */
public class SimpleGridNodeCreationStrategy implements GridNodeCreationStrategy {

    @Override
    public Node createNode(int column, int row) {
        long nodeId = GridGraph.getIdByCoordinatePair(column, row);
        return new Node(nodeId);
    }
}

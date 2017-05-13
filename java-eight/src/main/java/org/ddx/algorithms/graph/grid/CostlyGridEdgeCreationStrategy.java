package org.ddx.algorithms.graph.grid;

import org.ddx.algorithms.graph.model.Edge;

/**
 *  Builds edges with a cost based on the provided coordinates
 */
public abstract class CostlyGridEdgeCreationStrategy implements GridEdgeCreationStrategy {

    @Override
    public Edge createEdge(long sourceNodeId, long targetNodeId) {
        int srcColumn = GridGraph.getColumnForId(sourceNodeId);
        int srcRow = GridGraph.getRowForId(sourceNodeId);
        int targetColumn = GridGraph.getColumnForId(targetNodeId);
        int targetRow = GridGraph.getRowForId(targetNodeId);

        int cost = getCostForEdge(srcColumn, srcRow, targetColumn, targetRow);
        return new Edge(sourceNodeId, targetNodeId, cost, true);
    }

    protected abstract int getCostForEdge(int srcColumn, int srcRow, int targetColumn, int targetRow);
}

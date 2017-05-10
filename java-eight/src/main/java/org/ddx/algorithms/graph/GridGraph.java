package org.ddx.algorithms.graph;

import org.ddx.algorithms.graph.model.Edge;
import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Node;

/**
 *  Utility for building a graph of a grid coordinate system.
 *
 */
public class GridGraph {

    public static long getIdByCoordinatePair(int column, int row) {
        return (long)row << 32 | column & 0xFFFFFFFFL;
    }

    public static int getColumnForId(long id) {
        return (int)id;
    }

    public static int getRowForId(long id) {
        return (int)(id >> 32);
    }

    public static Graph buildGridGraph(int rows, int columns) {

        Graph graph = new Graph();

        for (int row=0; row<rows; row++) {
            for (int column = 0; column < columns; column++) {
                long nodeId = getIdByCoordinatePair(column, row);
                Node gridNode = new Node(nodeId);

                // add edge to right
                if (column<(columns-1)) { gridNode.addEdge(new Edge(nodeId, getIdByCoordinatePair(column+1, row))); };

                // add edge to left
                if (column>0) { gridNode.addEdge(new Edge(nodeId, getIdByCoordinatePair(column-1, row))); };

                // add edge on top
                if (row>0) { gridNode.addEdge(new Edge(nodeId, getIdByCoordinatePair(column, row-1))); };

                // add bottom edge
                if (row<(rows-1)) { gridNode.addEdge(new Edge(nodeId, getIdByCoordinatePair(column, row+1))); };

                graph.addNode(gridNode);
            }
        }
        return graph;
    }

}

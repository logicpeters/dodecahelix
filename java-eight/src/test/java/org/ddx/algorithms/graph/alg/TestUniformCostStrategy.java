package org.ddx.algorithms.graph.alg;

import org.ddx.algorithms.graph.GraphLogger;
import org.ddx.algorithms.graph.TestMapGraphBuilder;
import org.ddx.algorithms.graph.grid.CostlyGridEdgeCreationStrategy;
import org.ddx.algorithms.graph.grid.GridGraph;
import org.ddx.algorithms.graph.grid.SimpleGridNodeCreationStrategy;
import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Path;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

/**
 * Created on 5/12/2017.
 */
public class TestUniformCostStrategy {

    @Test
    public void testGridGraph() {
        GraphTraversalStrategy strategy = new UniformCostStrategy();
        Graph testGraph = GridGraph.buildGridGraph(5, 5,
            new SimpleGridNodeCreationStrategy(),
            new CostlyGridEdgeCreationStrategy() {
                @Override
                protected int getCostForEdge(int srcColumn, int srcRow, int targetColumn, int targetRow) {
                    // the default cost is 5.  Can be higher or lower.
                    int cost = 5;

                    //  make it twice as expensive to travel along the axis
                    if ((srcColumn==0 && targetColumn==0) || (srcRow==0 && targetRow==0)) {
                        cost = 10;
                    }

                    // make it cheaper to travel along the far edges
                    if ((srcColumn==4 && targetColumn==4) || (srcRow==4 && targetRow==4)) {
                        cost = 2;
                    }
                    return cost;
                }
            });

        long startNode = GridGraph.getIdByCoordinatePair(0,0);
        long endNode = GridGraph.getIdByCoordinatePair(1, 1);
        Path path = strategy.traverse(testGraph, startNode, endNode);
        // path should be three nodes - the start node, the end node, and the node to the right or below the start
        assertEquals(3, path.pathStream().count());

        // the shortest path is more expensive than a roundabout path since travel along the axis is expensive,
        //   so the answer is not the same as the breadth-first search
        startNode = GridGraph.getIdByCoordinatePair(0,0);
        endNode = GridGraph.getIdByCoordinatePair(0, 4);
        path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(7, path.pathStream().count());

        // same reasaoning as above
        startNode = GridGraph.getIdByCoordinatePair(0,0);
        endNode = GridGraph.getIdByCoordinatePair(4, 0);
        path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(7, path.pathStream().count());

        startNode = GridGraph.getIdByCoordinatePair(0,0);
        endNode = GridGraph.getIdByCoordinatePair(4, 4);
        path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(9, path.pathStream().count());
    }

    @Test
    public void testCityGraph() throws IOException, URISyntaxException {
        GraphTraversalStrategy strategy = new UniformCostStrategy();
        Graph testGraph = TestMapGraphBuilder.buildTestGraph();

        // Test case 1:  Within Texas.  Only 1 hop (two cities)
        long startNode = 0;
        long endNode = 6;
        Path path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(2, path.pathStream().count());

        // Test case 2: Alaska to Texas.  9 hops.  Note:  The same destinatation only takes 8 hopes in BFS.
        startNode = 7;
        endNode = 6;
        path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(9, path.pathStream().count());

        // Test case 3:  Florida to CA.  8 hops. -- Note:  The same destination only takes 7 hops in BFS.
        startNode = 135;
        endNode = 152;
        path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(8, path.pathStream().count());
    }

}

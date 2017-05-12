package org.ddx.algorithms.graph.alg;

import org.ddx.algorithms.graph.GraphLogger;
import org.ddx.algorithms.graph.GridGraph;
import org.ddx.algorithms.graph.TestMapGraphBuilder;
import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Path;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

/**
 * Created on 5/9/2017.
 */
public class TestBreadthFirstStrategy {

    @Test
    public void testGridGraph() {
        TraversalStrategy strategy = new BreadthFirstStrategy();
        Graph testGraph = GridGraph.buildGridGraph(10,10);

        long startNode = GridGraph.getIdByCoordinatePair(0,0);
        long endNode = GridGraph.getIdByCoordinatePair(1, 1);
        Path path = strategy.traverse(testGraph, startNode, endNode);
        // path should be three nodes - the start node, the end node, and the node to the right or below the start
        assertEquals(3, path.pathStream().count());

        startNode = GridGraph.getIdByCoordinatePair(0,0);
        endNode = GridGraph.getIdByCoordinatePair(0, 5);
        path = strategy.traverse(testGraph, startNode, endNode);
        assertEquals(6, path.pathStream().count());

        startNode = GridGraph.getIdByCoordinatePair(0,0);
        endNode = GridGraph.getIdByCoordinatePair(5, 0);
        path = strategy.traverse(testGraph, startNode, endNode);
        assertEquals(6, path.pathStream().count());

        startNode = GridGraph.getIdByCoordinatePair(0,0);
        endNode = GridGraph.getIdByCoordinatePair(5, 5);
        path = strategy.traverse(testGraph, startNode, endNode);
        assertEquals(11, path.pathStream().count());
    }

    @Test
    public void testCityGraph() throws IOException, URISyntaxException {
        TraversalStrategy strategy = new BreadthFirstStrategy();
        Graph testGraph = TestMapGraphBuilder.buildTestGraph();

        // Test case 1:  Within Texas.  Only 1 hop (two cities)
        long startNode = 0;
        long endNode = 6;
        Path path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(2, path.pathStream().count());

        // Test case 2: Alaska to Texas.  9 hops.
        startNode = 7;
        endNode = 6;
        path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(8, path.pathStream().count());

        // Test case 3:  Florida to CA.  8 hops.
        startNode = 135;
        endNode = 152;
        path = strategy.traverse(testGraph, startNode, endNode);
        GraphLogger.logPath(testGraph, path);
        assertEquals(7, path.pathStream().count());
    }

}

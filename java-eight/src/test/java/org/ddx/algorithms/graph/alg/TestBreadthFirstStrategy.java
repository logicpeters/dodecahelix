package org.ddx.algorithms.graph.alg;

import org.ddx.algorithms.graph.GridGraph;
import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Path;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created on 5/9/2017.
 */
public class TestBreadthFirstStrategy {

    @Test
    public void test() {
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

}

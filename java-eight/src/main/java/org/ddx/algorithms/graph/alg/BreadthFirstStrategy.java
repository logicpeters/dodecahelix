package org.ddx.algorithms.graph.alg;

import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Node;
import org.ddx.algorithms.graph.model.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Breadth first strategy, as described by Udacity's "Intro to Artifical Intelligence" class.
 *
 * NOTE: this is just a personal interpretation, not endorsed by the class.
 */
public class BreadthFirstStrategy implements TraversalStrategy {

    public static final Logger LOGGER = LoggerFactory.getLogger(BreadthFirstStrategy.class);

    public Path traverse(Graph graph, long startNodeId, long endNodeId) {
        LOGGER.debug("traversing graph from start node {} to end node {}", startNodeId, endNodeId);

        // explored nodes
        Set<Long> explored = new HashSet<>();

        // frontier nodes, by path
        Map<Long, Path> frontier = new HashMap<>();

        // add the start node to the frontier
        Path rootPath = new Path(startNodeId);
        frontier.put(startNodeId, rootPath);

        while (!frontier.isEmpty()) {
            Path shortestPathInFrontier = frontier.values().stream()
                .min((path1, path2) -> Integer.compare(path1.getNodeIds().size(), path2.getNodeIds().size())).get();

            long shortestPathNodeId = shortestPathInFrontier.getNodeIds()
                .get(shortestPathInFrontier.getNodeIds().size()-1);

            // now, we have the shortest path (or one of the shortest paths) on the frontier
            if (shortestPathNodeId == endNodeId) {
                return shortestPathInFrontier;
            } else {
                explored.add(shortestPathNodeId);
                frontier.remove(shortestPathNodeId);

                // add its child nodes to the frontier
                Node node = graph.getNode(shortestPathNodeId);
                node.getEdges().stream().map(edge -> edge.getTargetNodeId())
                    .filter(nodeId -> !explored.contains(nodeId))
                    .filter(nodeId -> !frontier.containsKey(nodeId))
                    .forEach(nodeId -> {
                        Path newPath = new Path(shortestPathInFrontier);
                        newPath.addFreeNode(nodeId);
                        frontier.put(nodeId, newPath);
                    });
            }
        }

        throw new IllegalArgumentException("there is no path found between the nodes");
    }

}

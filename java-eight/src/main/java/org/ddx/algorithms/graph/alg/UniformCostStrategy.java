package org.ddx.algorithms.graph.alg;

import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Node;
import org.ddx.algorithms.graph.model.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Uniform cost strategy, as described on Udacity's "Intro to Artifical Intelligence" class.
 *
 * NOTE: this is just a personal interpretation of the discussion, not endorsed by the class.
 * This discovers the cheapest route to the destination.
 *
 */
public class UniformCostStrategy implements GraphTraversalStrategy {

    public static final Logger LOGGER = LoggerFactory.getLogger(BreadthFirstStrategy.class);

    @Override
    public Path traverse(Graph graph, long startNodeId, long endNodeId) {
        LOGGER.debug("traversing graph using uniform-cost search from start node {} to end node {}", startNodeId, endNodeId);

        // explored nodes
        Set<Long> explored = new HashSet<>();

        // frontier nodes, by path (key is node ID)
        Map<Long, Path> frontier = new HashMap<>();

        // add the start node to the frontier
        Path rootPath = new Path(startNodeId);
        frontier.put(startNodeId, rootPath);

        while (!frontier.isEmpty()) {
            Path leastCostPathOnFrontier = frontier.values().stream()
                .min((path1, path2) -> Long.compare(path1.getCost(), path2.getCost())).get();

            long tailNodeId = leastCostPathOnFrontier.getTailNodeId();

            if (tailNodeId == endNodeId) {
                return leastCostPathOnFrontier;
            } else {
                explored.add(tailNodeId);
                frontier.remove(tailNodeId);

                // add child nodes to the frontier
                Node node = graph.getNode(tailNodeId);
                node.getEdges().stream()
                    .filter(edge -> !explored.contains(edge.getTargetNodeId())) // only add unexplored nodes
                    //.filter(edge -> (!frontier.containsKey(edge.getTargetNodeId()) || (edge.getTargetNodeId()==endNodeId)))
                    .forEach(edge -> {
                        long nodeId = edge.getTargetNodeId();
                        Path newPath = new Path(leastCostPathOnFrontier);
                        newPath.addNode(nodeId, edge.getCost());

                        // check to see if there is already an existing path to this location.  Replace if cheaper.
                        Path existingPath = frontier.get(nodeId);
                        if ((existingPath==null) || (existingPath.getCost() > newPath.getCost())) {
                            frontier.put(nodeId, newPath);
                        }
                    });
            }
        }

        throw new IllegalArgumentException("there is no path found between the nodes");
    }
}

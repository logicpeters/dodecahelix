package org.ddx.algorithms.graph;

import org.ddx.algorithms.graph.model.Graph;
import org.ddx.algorithms.graph.model.Node;
import org.ddx.algorithms.graph.model.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 *  Utility to print human-friendly debugging logs of graphs, nodes, edges and paths.
 */
public class GraphLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(GraphLogger.class);

    public static final void logPath(Graph graph, Path path) {

        path.pathStream()
            .map(nodeId -> graph.getNode(nodeId).toString())
            .reduce((s1, s2) -> s1 + " -> " + s2);

        StringBuilder pathLog = new StringBuilder("Path: ");

        List<Long> nodePath = path.getNodeIds();
        for (int i=0; i<(nodePath.size()-1); i++) {
            long nodeId = nodePath.get(i);
            Node node = graph.getNode(nodeId);

            pathLog.append(node.toString());
            LOGGER.debug(pathLog.toString());
            pathLog.setLength(0);
            // indent
            pathLog.append(new String(new char[i]).replace('\0', ' '));

            long nextNodeId = nodePath.get(i + 1);
            node.edgeStream()
                .filter(edge -> edge.getTargetNodeId()==nextNodeId)
                .forEach(edge -> {
                    pathLog.append(" -");
                    if (edge.getCost() > 0) {
                        pathLog.append("($");
                        pathLog.append(edge.getCost());
                        pathLog.append(")");
                    }
                    pathLog.append("-> ");
                });
        }

        Node node = graph.getNode(nodePath.get(nodePath.size()-1));
        pathLog.append(node.toString());
        LOGGER.debug(pathLog.toString());
    }

}

package org.ddx.algorithms.graph;

import org.ddx.algorithms.graph.model.DataEdge;
import org.ddx.algorithms.graph.model.DataNode;
import org.ddx.algorithms.graph.model.Graph;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *   Builds a graph based off of a dataset of US/Canada cities and the distances between them.
 *
 */
public class TestMapGraphBuilder {

    // only build a road if the distance is less than this threshold
    public static int THRESHOLD_DISTANCE = 500;

    public static Graph buildTestGraph() throws IOException, URISyntaxException {
        int expectedSize = 312;

        Path cityNamesFile = Paths.get(TestMapGraphBuilder.class.getClassLoader().getResource("datasets/usca312_name.txt").toURI());
        List<String> cities = Files.readAllLines(cityNamesFile).stream()
            .filter(x -> !x.startsWith("#"))
            .collect(Collectors.toList());

        Path cityDistancesFile = Paths.get(TestMapGraphBuilder.class.getClassLoader().getResource("datasets/usca312_dist.txt").toURI());
        List<Integer> allDistances =  Files.readAllLines(cityDistancesFile).stream()
            .filter(x -> !x.startsWith("#"))
            .flatMap(line -> Arrays.stream(line.trim().split(" +")))
            .mapToInt(dist -> Integer.valueOf(dist.trim())).boxed()
            .collect(Collectors.toList());

        Graph graph = new Graph();
        long currentNodeId = 0;
        long currentTargetId = 0;
        DataNode currentNode = null;
        for (int distance : allDistances) {
            if (currentTargetId>=expectedSize) {
                currentNodeId++;
                currentTargetId = 0;
                currentNode = null;
            }

            if (currentNode==null) {
                currentNode = new DataNode(currentNodeId);
                graph.addNode(currentNode);
                String[] cityData = cities.get((int)currentNodeId).split(",");
                currentNode.addMetadata("city", cityData[0].trim());
                currentNode.addMetadata("state", cityData[1].trim());
            }

            // only build if the distance is shorter than threshold
            if (distance>0 && distance<THRESHOLD_DISTANCE) {
                DataEdge edgeNode = new DataEdge(currentNodeId, currentTargetId, distance, true);
                currentNode.addEdge(edgeNode);
            }

            currentTargetId++;
        }

        return graph;
    }

    @Test
    public void testBuildGraph() throws Exception {
        Graph graph = buildTestGraph();
    }

}

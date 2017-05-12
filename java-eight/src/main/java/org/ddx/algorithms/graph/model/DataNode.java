package org.ddx.algorithms.graph.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Edge containing key-value pair properties.
 *
 * Property keys and values are strings
 */
public class DataNode extends Node {

    private Map<String, String> metadata = new HashMap<>();

    public DataNode(long id) {
        super(id);
    }

    public Map<String, String> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }

    @Override
    public String toString() {
        return "Node{" +
        getId() +
        ", meta=[" + metadata.entrySet().stream()
            .map(kvPair -> kvPair.getKey() + "=" + kvPair.getValue())
            .reduce((s1, s2) -> s1 + ", " + s2)
            .get() + "]"
        + "]}";
    }
}

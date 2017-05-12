package org.ddx.algorithms.graph.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Edge containing key-value pair properties.
 *
 * Property keys and values are strings.
 */
public class DataEdge extends Edge implements MetadataHolder<String> {

    private Map<String, String> metadata = new HashMap<>();

    public DataEdge(long sourceNode, long targetNode) {
        super(sourceNode, targetNode);
    }

    public DataEdge(long sourceNode, long targetNode, int cost, boolean bidirectional) {
        super(sourceNode, targetNode, cost, bidirectional);
    }

    public Map<String, String> getMetadata() {
        return Collections.unmodifiableMap(metadata);
    }

    public void addMetadata(String key, String value) {
        metadata.put(key, value);
    }

    @Override
    public String toString() {
        String dirStr = isBidirectional() ? "->" : "<->";
        String costStr = (getCost()>0) ? " ($" + getCost() + ")" : "";
        return "Edge{"
            + getSourceNodeId()
            + dirStr
            + getTargetNodeId()
            + costStr
            + ", meta=[" + metadata.entrySet().stream()
                .map(kvPair -> kvPair.getKey() + "=" + kvPair.getValue())
                .reduce((s1, s2) -> s1 + ", " + s2)
                .get()
            + "]}";
    }

}

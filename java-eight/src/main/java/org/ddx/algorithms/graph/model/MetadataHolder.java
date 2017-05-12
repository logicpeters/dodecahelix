package org.ddx.algorithms.graph.model;

import java.util.Map;

/**
 * Created on 5/10/2017.
 */
public interface MetadataHolder<T> {

    public Map<String, T> getMetadata();

}

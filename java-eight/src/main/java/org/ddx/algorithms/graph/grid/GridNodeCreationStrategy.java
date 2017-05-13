package org.ddx.algorithms.graph.grid;

import org.ddx.algorithms.graph.model.Node;

/**
 * Created on 5/12/2017.
 */
public interface GridNodeCreationStrategy {

    public Node createNode(int column, int row);

}

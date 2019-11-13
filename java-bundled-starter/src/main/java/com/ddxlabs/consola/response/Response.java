package com.ddxlabs.consola.response;

import java.util.Collection;
import java.util.List;

/**
 * Created on 6/2/2019.
 */
public interface Response {

    /**
     * List of available context subjects for commands.
     * @return
     */
    public Collection<String> getSubjects();

    /**
     * List of accessible resources
     * @return
     */
    public Collection<ResponseResource> getResources();

    public List<StyledLine> getOutputDisplay();

}

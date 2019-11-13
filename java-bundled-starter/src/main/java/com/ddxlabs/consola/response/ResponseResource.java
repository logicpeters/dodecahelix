package com.ddxlabs.consola.response;

import java.util.HashSet;
import java.util.Set;

public class ResponseResource {

    private String name;

    /**
     * Optional category field for resource for classification.
     */
    private String category;

    /**
     * Which subjects (name) has access to this resource.
     *
     * Empty set indicates ALL subjects have access.
     */
    private Set<String> subjects;

    /**
     * What are the (visible) command words that this resource supports.
     *
     * Command words are available for auto-completion
     */
    private Set<String> commands;

    public ResponseResource(String name) {
        this.name = name;
        this.subjects = new HashSet<>();
        this.commands = new HashSet<>();
    }

}

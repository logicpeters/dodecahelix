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
     * Which subjects (name) have access to this resource.
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

    public void addSubject(String subject) {
        this.subjects.add(subject);
    }

    public void addCommand(String command) {
        this.commands.add(command);
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getSubjects() {
        return subjects;
    }

    public Set<String> getCommands() {
        return commands;
    }
}

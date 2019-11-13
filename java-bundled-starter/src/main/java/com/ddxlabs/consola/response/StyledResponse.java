package com.ddxlabs.consola.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created on 6/2/2019.
 */
public class StyledResponse implements Response {

    private Set<String> subjects;
    private Set<ResponseResource> resources;
    private List<StyledLine> outputDisplay;

    public StyledResponse() {
        this.outputDisplay = new ArrayList<>();
        this.subjects = new HashSet<>();
        this.resources = new HashSet<>();
    }

    @Override
    public List<StyledLine> getOutputDisplay() {
        return outputDisplay;
    }

    public void addBlankLine() {
        this.outputDisplay.add(new StyledLine(""));
    }

    public void addResponseLine(StyledLine line) {
        outputDisplay.add(line);
    }

    @Override
    public Set<String> getSubjects() {
        return subjects;
    }

    @Override
    public Set<ResponseResource> getResources() {
        return resources;
    }

    public void addResource(ResponseResource resource) {
        this.resources.add(resource);
    }

    public void addSubject(String subject) {
        this.subjects.add(subject);
    }
}

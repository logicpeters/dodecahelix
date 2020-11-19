package com.ddxlabs.consola.response;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StyledResponseBuilder implements ResponseBuilder {

    private StyledLine currentLine;
    private StyledResponse response;

    public StyledResponseBuilder() {
        response = new StyledResponse();
    }

    @Override
    public StyledResponse build() {
        this.finishCurrentLine();
        return response;
    }

    @Override
    public StyledResponseBuilder newline() {
        if (currentLine!=null) {
            finishCurrentLine();
        } else {
            response.addBlankLine();
        }
        return this;
    }

    @Override
    public StyledResponseBuilder line(String message) {
        this.finishCurrentLine();
        response.addResponseLine(new StyledLine(message));
        return this;
    }

    @Override
    public StyledResponseBuilder line(String message, TextStyle style) {
        this.finishCurrentLine();
        response.addResponseLine(new StyledLine(message, style));
        return this;
    }

    @Override
    public StyledResponseBuilder fragment(String fragment, TextStyle style) {
        if (currentLine==null) {
            currentLine = new StyledLine();
        }
        currentLine.addFragment(fragment, style);
        return this;
    }

    @Override
    public StyledResponseBuilder subject(String subject) {
        response.addSubject(subject);
        return this;
    }

    @Override
    public StyledResponseBuilder resource(ResponseResource resource) {
        response.addResource(resource);
        return this;
    }

    private void finishCurrentLine() {
        if (currentLine!=null) {
            response.addResponseLine(currentLine);
        }
        currentLine = null;
    }
}

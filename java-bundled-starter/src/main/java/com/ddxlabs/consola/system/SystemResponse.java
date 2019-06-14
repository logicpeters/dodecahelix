package com.ddxlabs.consola.system;

import com.ddxlabs.consola.Response;
import com.ddxlabs.consola.StyledLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 6/2/2019.
 */
public class SystemResponse implements Response {

    private List<StyledLine> outputDisplay;

    public SystemResponse() {
        this.outputDisplay = new ArrayList<>();
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
}

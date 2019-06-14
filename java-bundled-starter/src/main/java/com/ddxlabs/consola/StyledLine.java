package com.ddxlabs.consola;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created on 6/1/2019.
 */
public class StyledLine {

    private List<StyledFragment> fragments;

    public StyledLine() {
        fragments = new ArrayList<>();
    }

    public StyledLine(String text) {
        this();
        fragments.add(new StyledFragment(text));
    }

    public StyledLine(String text, TextStyle style) {
        this();
        fragments.add(new StyledFragment(text, style));
    }

    public void addFragment(String fragmentText, TextStyle style) {
        fragments.add(new StyledFragment(fragmentText, style));
    }

    public int getLineSize() {
        int size = 0;
        for (StyledFragment fragment : fragments) {
            size += fragment.text.length();
        }
        return size;
    }

    public List<StyledFragment> getFragments() {
        return Collections.unmodifiableList(this.fragments);
    }
}

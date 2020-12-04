package com.ddxlabs.nim.noise.modules;

public enum ComboQualifier {
    ADD (2),
    MULTIPLY (2),
    POWER (2),
    SELECT (3),
    BLEND (3),
    DISPLACE (4),
    MAX (2),
    MIN (2);

    int numSources;

    ComboQualifier(int numSources) {
        this.numSources = numSources;
    }

    public int getNumSources() {
        return numSources;
    }
}

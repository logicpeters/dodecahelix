package com.ddxlabs.nim.noise;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImageTweaks {

    private int chop;

    public Optional<Integer> getChop() {
        return Optional.ofNullable(chop);
    }

    public void setChop(int chop) {
        this.chop = chop;
    }

    public List<String> asCsvList() {
        List<String> lines = new ArrayList<>();
        lines.add(String.format("chop,%d", chop));
        return lines;
    }

    public void addTweak(String tweakParam, String tweakValue) {
        if (tweakParam.equalsIgnoreCase("chop")) {
            chop = Integer.parseInt(tweakValue);
        }
    }

    public void updateTweaks(ImageTweaks tweaks) {
        this.chop = tweaks.chop;
    }
}

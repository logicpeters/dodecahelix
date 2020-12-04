package com.ddxlabs.nim.noise.custom;

import org.spongepowered.noise.exception.NoModuleException;
import org.spongepowered.noise.module.Module;

public class Chop extends Module {

    private int chop;

    public Chop() {
        super(1);
    }

    @Override
    public int getSourceModuleCount() {
        return 1;
    }

    @Override
    public double getValue(double x, double y, double z) {
        if (sourceModule[0] == null) {
            throw new NoModuleException();
        }

        double value = sourceModule[0].getValue(x, y, z);
        if (chop>0) {
            // chop is a way of producing discreet values for noise
            value = Math.ceil(value * chop) / chop;
        }

        return value;
    }

    public int getChop() {
        return chop;
    }

    public void setChop(int chop) {
        this.chop = chop;
    }
}

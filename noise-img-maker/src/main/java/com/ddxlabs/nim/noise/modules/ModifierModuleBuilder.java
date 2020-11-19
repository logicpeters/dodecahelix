package com.ddxlabs.nim.noise.modules;

import com.ddxlabs.nim.noise.ParamsMap;
import org.spongepowered.noise.module.Module;
import org.spongepowered.noise.module.modifier.*;

public class ModifierModuleBuilder {

    public static Module build(String moduleId, String qualifier, ParamsMap params) {
        Module module = new Abs();
        if ("abs".equalsIgnoreCase(qualifier)) {
            module = new Abs();
        }
        if ("clamp".equalsIgnoreCase(qualifier)) {
            module = new Clamp();
            double lwb = params.getDob(moduleId, "lowerbound");
            double ubb = params.getDob(moduleId, "upperbound");
            if (ubb<=lwb) {
                double tmp = lwb;
                lwb = ubb;
                ubb = tmp;
                params.resetValue(moduleId,"lowerbound", String.valueOf(lwb));
                params.resetValue(moduleId,"upperbound", String.valueOf(ubb));
            }
            ((Clamp)module).setUpperBound(ubb);
            ((Clamp)module).setLowerBound(lwb);
        }
        if ("curve".equalsIgnoreCase(qualifier)) {
            module = new Curve();
            int curves = 4 + params.getInt(250, moduleId, "curves");
            for (int i=0; i<curves; i++) {
                double input = params.getDob(moduleId, "in" + i);
                double output = params.getDob(moduleId, "out" + i);
                ((Curve) module).addControlPoint(input, output);
            }
        }
        if ("exponent".equalsIgnoreCase(qualifier)) {
            module = new Exponent();
            ((Exponent)module).setExponent(params.getDob(moduleId, "exponent"));
        }
        if ("range".equalsIgnoreCase(qualifier)) {
            module = new Range();
            // TODO - reset bounds
            // ((Range)module).setBounds(random.nextDouble(), random.nextDouble(), random.nextDouble());
        }
        if ("turbule".equalsIgnoreCase(qualifier)) {
            module = new Turbulence();

            ((Turbulence)module).setFrequency(params.getDob(moduleId, "frequency"));
            ((Turbulence)module).setPower(params.getDob(moduleId, "power"));
            ((Turbulence)module).setRoughness(1 + params.getInt(100, moduleId, "roughness"));  // is divver correct?
        }
        if ("terrace".equalsIgnoreCase(qualifier)) {
            module = new Terrace();
            int controlPoints = params.getInt(250, moduleId, "control-points");
            ((Terrace)module).makeControlPoints(2 + controlPoints);
        }

        return module;
    }
}

package com.ddxlabs.nim.noise.modules;

import com.ddxlabs.nim.noise.ParamsMap;
import org.spongepowered.noise.module.Module;
import org.spongepowered.noise.module.combiner.*;

public class ComboModuleBuilder {

    public static Module build(String moduleId, String qualifier, ParamsMap params) {
        Module comboModule;
        switch (qualifier) {
            case "blend": comboModule = new Blend(); break;
            case "displace": comboModule = new Displace(); break;
            case "max": comboModule = new Max(); break;
            case "min": comboModule = new Min(); break;
            case "multiply": comboModule = new Multiply(); break;
            case "power": comboModule = new Power(); break;
            case "select": {
                comboModule = new Select();
                double lwb = params.getDob(moduleId, "lowerbound");
                double ubb = params.getDob(moduleId, "upperbound");
                if (ubb<=lwb) {
                    double tmp = lwb;
                    lwb = ubb;
                    ubb = tmp;
                    params.resetValue(moduleId,"lowerbound", String.valueOf(lwb));
                    params.resetValue(moduleId,"upperbound", String.valueOf(ubb));
                }
                ((Select)comboModule).setBounds(ubb, lwb);
                ((Select)comboModule).setEdgeFalloff(params.getDob(moduleId,"edge-falloff"));
            }; break;
            default: comboModule = new Add();
        }
        return comboModule;
    }

}

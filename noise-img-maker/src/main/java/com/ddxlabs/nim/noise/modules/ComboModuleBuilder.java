package com.ddxlabs.nim.noise.modules;

import com.ddxlabs.nim.noise.ParamsMap;
import org.spongepowered.noise.module.Module;
import org.spongepowered.noise.module.combiner.*;

public class ComboModuleBuilder {

    public static Module build(String moduleId, String qualifier, ParamsMap params) {
        Module comboModule;
        switch (qualifier) {
            case "blend": comboModule = new Blend(); break;        // requires 3 source modules
            case "displace": comboModule = new Displace(); break;  // requires 4 source modules
            case "max": comboModule = new Max(); break;
            case "min": comboModule = new Min(); break;
            case "multiply": comboModule = new Multiply(); break;
            case "power": comboModule = new Power(); break;
            case "select": {
                comboModule = new Select();
                double lwb = - params.getDob(moduleId, "lowerbound");
                double ubb = params.getDob(moduleId, "upperbound");
                params.resetValue(moduleId, "edge-falloff", "0.0");
                ((Select)comboModule).setBounds(ubb, lwb);
                // ((Select)comboModule).setEdgeFalloff(params.getDob(moduleId,"edge-falloff"));
            }; break;
            default: comboModule = new Add();
        }
        return comboModule;
    }

}

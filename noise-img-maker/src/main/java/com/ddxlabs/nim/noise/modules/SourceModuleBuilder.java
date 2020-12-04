package com.ddxlabs.nim.noise.modules;

import com.ddxlabs.nim.noise.ParamsMap;
import org.spongepowered.noise.NoiseQuality;
import org.spongepowered.noise.module.Module;
import org.spongepowered.noise.module.source.*;

public class SourceModuleBuilder {

    public static Module build(String moduleId, String qualifier, ParamsMap params) {
        Module module = null;
        if ("perlin".equalsIgnoreCase(qualifier)) {
            module = new Perlin();
            ((Perlin)module).setNoiseQuality(NoiseQuality.BEST);  // TODO - parameterize
            ((Perlin)module).setFrequency(params.getDob(moduleId, "frequency", 2));
            ((Perlin)module).setOctaveCount(1 + params.getInt(200, moduleId, "octaves"));
            ((Perlin)module).setLacunarity(params.getDob(moduleId, "lacunarity", 2));
            ((Perlin)module).setPersistence(params.getDob(moduleId, "persistence", 2));
            ((Perlin)module).setSeed(params.getSeed(moduleId));
        }
        if ("voronoi".equalsIgnoreCase(qualifier)) {
            module = new Voronoi();
            boolean enableDistance = params.getBool(moduleId, "enable-distance");
            ((Voronoi)module).setFrequency(params.getDob(moduleId, "frequency"));
            ((Voronoi)module).setEnableDistance(enableDistance);
            ((Voronoi)module).setDisplacement(params.getDob(moduleId, "displacement"));
            ((Voronoi)module).setSeed(params.getSeed(moduleId));
        }
        if ("billow".equalsIgnoreCase(qualifier)) {
            module = new Billow();
            ((Billow)module).setQuality(NoiseQuality.BEST);  // TODO - parameterize
            ((Billow)module).setFrequency(params.getDob(moduleId, "frequency", 2));
            ((Billow)module).setOctaveCount(1 + params.getInt(200, moduleId, "octaves"));
            ((Billow)module).setLacunarity(params.getDob(moduleId, "lacunarity", 2));
            ((Billow)module).setPersistence(params.getDob(moduleId, "persistence", 2));
            ((Billow)module).setSeed(params.getSeed(moduleId));
        }
        if ("ridged".equalsIgnoreCase(qualifier)) {
            module = new RidgedMulti();
            ((RidgedMulti)module).setNoiseQuality(NoiseQuality.BEST);  // TODO - parameterize
            ((RidgedMulti)module).setFrequency(params.getDob(moduleId, "frequency", 2));
            ((RidgedMulti)module).setOctaveCount(1 + params.getInt(200, moduleId, "octaves"));
            ((RidgedMulti)module).setLacunarity(params.getDob(moduleId, "lacunarity", 2));
            ((RidgedMulti)module).setSeed(params.getSeed(moduleId));
        }
        if ("checkerboard".equalsIgnoreCase(qualifier)) {
            module = new Checkerboard();
        }
        if ("const".equalsIgnoreCase(qualifier)) {
            module = new Const();
            ((Const)module).setValue(params.getDob(moduleId, "value"));
        }
        if ("cylinders".equalsIgnoreCase(qualifier)) {
            module = new Cylinders();
            ((Cylinders)module).setFrequency(params.getDob(moduleId, "frequency"));
        }
        if ("spheres".equalsIgnoreCase(qualifier)) {
            module = new Spheres();
            ((Spheres)module).setFrequency(params.getDob(moduleId, "frequency"));
        }
        return module;
    }

}

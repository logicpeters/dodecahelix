package com.ddxlabs.nim.noise;

import org.spongepowered.noise.NoiseQuality;
import org.spongepowered.noise.module.combiner.*;
import org.spongepowered.noise.module.modifier.*;
import org.spongepowered.noise.module.source.*;
import org.spongepowered.noise.module.Module;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferUShort;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Deprecated
public class MassiveGenerator {

    private Random random;

    public NoiseQuality quality = NoiseQuality.BEST;
    public boolean color = false;
    public int height = 1024;
    public int width = 1024;
    public double xPeriod = 128;
    public double yPeriod = 128;

    public static final String KEY_FREQ = "frequency";
    public static final String KEY_OCTAVES = "octaves";
    public static final String KEY_LACUNA = "lacunarity";
    public static final String KEY_PERSIST = "persistence";
    public static final String KEY_RAND_DOUBLE = "randdoub";
    public static final String KEY_EXTRA_DOUBLE = "extradoub";
    public static final String KEY_RAND_INT = "randint";
    public static final String KEY_RAND_BOOL = "randbool";
    public static final String KEY_UPPER_BOUND = "upperbound";
    public static final String KEY_LOWER_BOUND = "lowerbound";

    public MassiveGenerator(int seed) {
        random = new Random(seed);
    }

    public Module buildSourceModule(String moduleType, Map<String, Double> params, int seed) {
        Module module = null;
        if ("perlin".equalsIgnoreCase(moduleType)) {
            module = new Perlin();
            ((Perlin)module).setNoiseQuality(quality);
            ((Perlin)module).setFrequency(params.get(KEY_FREQ));
            ((Perlin)module).setOctaveCount(params.get(KEY_OCTAVES).intValue());
            ((Perlin)module).setLacunarity(params.get(KEY_LACUNA));
            ((Perlin)module).setPersistence(params.get(KEY_PERSIST));
            ((Perlin)module).setSeed(seed);
        }
        if ("voronoi".equalsIgnoreCase(moduleType)) {
            module = new Voronoi();
            boolean enableDistance = params.get(KEY_RAND_BOOL) != 0.0;
            ((Voronoi)module).setFrequency(params.get(KEY_FREQ));
            ((Voronoi)module).setEnableDistance(enableDistance);
            ((Voronoi)module).setDisplacement(params.get(KEY_RAND_DOUBLE));
            ((Voronoi)module).setSeed(seed);
        }
        if ("billow".equalsIgnoreCase(moduleType)) {
            module = new Billow();
            ((Billow)module).setQuality(quality);
            ((Billow)module).setFrequency(params.get(KEY_FREQ));
            ((Billow)module).setOctaveCount(params.get(KEY_OCTAVES).intValue());
            ((Billow)module).setLacunarity(params.get(KEY_LACUNA));
            ((Billow)module).setPersistence(params.get(KEY_PERSIST));
            ((Billow)module).setSeed(seed);
        }
        if ("ridged".equalsIgnoreCase(moduleType)) {
            module = new RidgedMulti();
            ((RidgedMulti)module).setNoiseQuality(quality);
            ((RidgedMulti)module).setFrequency(params.get(KEY_FREQ));
            ((RidgedMulti)module).setOctaveCount(params.get(KEY_OCTAVES).intValue());
            ((RidgedMulti)module).setLacunarity(params.get(KEY_LACUNA));
            ((RidgedMulti)module).setSeed(seed);
        }
        if ("checkerboard".equalsIgnoreCase(moduleType)) {
            module = new Checkerboard();
        }
        if ("const".equalsIgnoreCase(moduleType)) {
            module = new Const();
            ((Const)module).setValue(params.get(KEY_EXTRA_DOUBLE));
        }
        if ("cylinders".equalsIgnoreCase(moduleType)) {
            module = new Cylinders();
            ((Cylinders)module).setFrequency(params.get(KEY_FREQ));
        }
        if ("spheres".equalsIgnoreCase(moduleType)) {
            module = new Spheres();
            ((Spheres)module).setFrequency(params.get(KEY_FREQ));
        }
        return module;
    }

    public Module buildModifierModule(String moduleType, Module baseModule, String[] moduleNames, int seed) {
        Map<String, Double> params = generateRandomParameters();
        Module module = new Abs();
        if ("abs".equalsIgnoreCase(moduleType)) {
            module = new Abs();
        }
        if ("clamp".equalsIgnoreCase(moduleType)) {
            module = new Clamp();
            ((Clamp)module).setUpperBound(params.get(KEY_UPPER_BOUND));
            ((Clamp)module).setLowerBound(params.get(KEY_LOWER_BOUND));
        }
        if ("curve".equalsIgnoreCase(moduleType)) {
            module = new Curve();
            for (int i=0; i<6; i++) {
                ((Curve) module).addControlPoint(random.nextDouble(), random.nextDouble());
            }
        }
        if ("exponent".equalsIgnoreCase(moduleType)) {
            module = new Exponent();
            ((Exponent)module).setExponent(random.nextDouble());
        }
        if ("range".equalsIgnoreCase(moduleType)) {
            module = new Range();
            //((Range)module).setBounds(random.nextDouble(), random.nextDouble(), random.nextDouble());
        }
        if ("turbule".equalsIgnoreCase(moduleType)) {
            module = new Turbulence();
            ((Turbulence)module).setFrequency(params.get(KEY_FREQ));
            ((Turbulence)module).setPower(params.get(KEY_RAND_DOUBLE));
            ((Turbulence)module).setRoughness(params.get(KEY_RAND_INT).intValue());
        }
        if ("terrace".equalsIgnoreCase(moduleType)) {
            module = new Terrace();
            ((Terrace)module).makeControlPoints(2 + params.get(KEY_RAND_INT).intValue());
        }

        module.setSourceModule(0, baseModule);
        return module;
    }

    public void generateImage(Module module, int chop, String filename) {

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY);
        if (color) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
        }

        final short[] data = ((DataBufferUShort) image.getRaster().getDataBuffer()).getData();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double noise = module.getValue(x / xPeriod, y / yPeriod, 0) / 2;
                noise = Math.ceil(noise * chop) / chop;
                data[y * width + x] = (short) (noise * 65_535);
            }
        }

        try {
            ImageIO.write(image, "PNG", new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loopGenerator(String moduleName, int chop, int seed) {
        Map<String, Double> params = new HashMap<>();
        for (double octaves = 2.0; octaves < 10.0; octaves += 2.0) {
            params.put(KEY_OCTAVES, octaves);
            for (double freq = 0.5; freq < 5.0; freq += 0.3) {
                params.put(KEY_FREQ, freq);
                for (double lacuna = 0.5; lacuna < 5.0; lacuna += 0.3) {
                    params.put(KEY_LACUNA, lacuna);
                    for (double persist = 0.5; persist < 5.0; persist += 0.3) {
                        params.put(KEY_PERSIST, persist);
                        String filename = String.format("output/perlin_o%2.1f_f%2.1f_l%2.1f_p%2.1f.png",
                                octaves, freq, lacuna, persist);
                        Module module = buildSourceModule(moduleName, params, seed);
                        System.out.println("Generating perlin image: " + filename);
                        generateImage(module, chop, filename);
                    }
                }
            }
        }
    }

    public void randomGenerator(String[] moduleNames, int seed, int numImages,
                                String combiner, String modifier) {

        String baseModuleName = moduleNames[0];

        int chop;
        for (int i = 0; i < numImages; i++) {
            chop = 5 * random.nextInt(6);
            if (chop == 0) {
                chop = 1;
            }

            Map<String, Double> params = generateRandomParameters();
            String filename = String.format("output/%s/sd%d_ch%d_%s", baseModuleName, seed, chop, generateStringFragByParams(params));
            Module sourceModule = buildSourceModule(baseModuleName, params, seed);

            Module finalModule = sourceModule;
            if (combiner!=null) {
                String combinedModuleName = moduleNames[1];
                Map<String, Double> combinedModuleParams = generateRandomParameters();
                Module combinedModule = buildSourceModule(combinedModuleName, combinedModuleParams, seed);
                filename += String.format("_%s_%s_%s", combiner, combinedModuleName, generateStringFragByParams(combinedModuleParams));
                finalModule = buildComboModule(combiner, sourceModule, combinedModule, moduleNames, seed);
            }
            if (modifier!=null) {
                finalModule = buildModifierModule(modifier, finalModule, moduleNames, seed);
                filename += "_" + modifier;
            }

            filename = filename + ".png";
            System.out.println("Generating image: " + filename);
            generateImage(finalModule, chop, filename);

        }
    }

    private Module buildComboModule(String combiner, Module module, Module combinedModule, String[] moduleNames, int seed) {
        Module comboModule;
        switch (combiner) {
            case "blend": comboModule = new Blend(); break;
            case "displace": comboModule = new Displace(); break;
            case "max": comboModule = new Max(); break;
            case "min": comboModule = new Min(); break;
            case "multiply": comboModule = new Multiply(); break;
            case "power": comboModule = new Power(); break;
            case "select": comboModule = new Select(); break;
            default: comboModule = new Add();
        }
        comboModule.setSourceModule(0, module);
        comboModule.setSourceModule(1, combinedModule);

        if ("displace".equalsIgnoreCase(combiner)
                || "blend".equalsIgnoreCase(combiner)
                || "select".equalsIgnoreCase(combiner)) {
            Map<String, Double> params = generateRandomParameters();
            Module anotherSource = buildSourceModule(moduleNames[2], params, seed);
            comboModule.setSourceModule(2, anotherSource);
        }
        if ("displace".equalsIgnoreCase(combiner)) {
            Map<String, Double> params = generateRandomParameters();
            Module anotherSource = buildSourceModule(moduleNames[3], params, seed);
            comboModule.setSourceModule(3, anotherSource);
        }

        return comboModule;
    }

    private Map<String, Double> generateRandomParameters() {
        Map<String, Double> params = new HashMap<>();
        double octaves = Math.floor(random.nextInt(10)) + 1.0d;
        double freq = random.nextDouble() * (random.nextInt(4) + 1);
        double lacuna = random.nextDouble() * (random.nextInt(4) + 1);
        double persist = random.nextDouble() * (random.nextInt(4) + 1);
        double upper = 0.5 + (random.nextDouble() / 2);
        double lower = 0.5 - (random.nextDouble() / 2);
        double randInt = Math.floor(random.nextInt(10)) + 1.0d;
        double randDouble = random.nextDouble() * (random.nextInt(4) + 1);
        double randBool = random.nextBoolean() ? 1.0 : 0.0;
        params.put(KEY_OCTAVES, octaves);
        params.put(KEY_FREQ, freq);
        params.put(KEY_LACUNA, lacuna);
        params.put(KEY_PERSIST, persist);
        params.put(KEY_RAND_INT, randInt);
        params.put(KEY_RAND_DOUBLE, randDouble);
        params.put(KEY_RAND_BOOL, randBool);
        params.put(KEY_UPPER_BOUND, upper);
        params.put(KEY_LOWER_BOUND, lower);
        return params;
    }

    public static String generateStringFragByParams(Map<String, Double> params) {
        return String.format("oct%d_fr%s_lc%s_pr%s_rd%s_ri%d_rb%s",
                params.get(KEY_OCTAVES).intValue(),
                dts(params.get(KEY_FREQ)),
                dts(params.get(KEY_LACUNA)),
                dts(params.get(KEY_PERSIST)),
                dts(params.get(KEY_RAND_DOUBLE)),
                params.get(KEY_RAND_INT).intValue(),
                bts(params.get(KEY_RAND_BOOL)));
    }

    public static String dts(double dbl) {
        return String.valueOf((int)(dbl*100));
    }

    public static String bts(double dbl) {
        return (dbl==0.0)?"F":"T";
    }

    public static String randomModule() {
        int module = new Random().nextInt(3);
        String moduleName = "perlin";
        switch (module) {
            case 1: moduleName = "ridged"; break;
            case 2: moduleName = "billow"; break;
            case 3: moduleName = "voronoi"; break;
        }
        return moduleName;
    }

    public String randomCombiner() {
        String[] combiners = { "add", "multiply", "power", "displace", "blend", "max", "min", "select"};
        int combinerNum = random.nextInt(20);
        if (combinerNum >= combiners.length) {
            return null;
        }
        return combiners[combinerNum];
    }

    public String randomModifier() {
        String[] modifiers = { "abs", "clamp", "cirve", "exponent", "terrace", "turbule"};
        int modifierNum = random.nextInt(15);
        if (modifierNum >= modifiers.length) {
            return null;
        }
        return modifiers[modifierNum];
    }

    public static void main(String[] args) {
        int seed = (new Random()).nextInt();
        MassiveGenerator generator = new MassiveGenerator(seed);

        for (int i=0; i<10; i++) {
            String[] sourceModules = {randomModule(), randomModule(), randomModule(), randomModule()};  // ridged, perlin, billow, voronoi, spheres, const, checkerboard, cylinders
            String combiner = generator.randomCombiner(); // add, multiply, power, displace, blend, max, min, select
            String modifier = generator.randomModifier(); // abs, clamp, curve, exponent, invert, range, rotate, scaleb, scalep, terrace, trans, turbule
            generator.randomGenerator(sourceModules, seed, 1, combiner, modifier);
        }

        //randomGenerator(moduleName, seed, 10, null, null);
    }

}


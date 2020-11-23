package com.ddxlabs.nim.noise;

import org.spongepowered.noise.module.Module;

import java.io.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

@Deprecated
public class NoiseArtGenerator {

    static Random random = new Random(Calendar.getInstance().getTimeInMillis());

    public static String randomSource() {
        String[] sources = {"perlin", "ridged", "billow", "voronoi", "checkerboard", "const", "cylinders", "spheres"};
        int[] frequencies = {5, 5, 5, 1, 1, 1, 1, 1};
        return getRandomUsingFrequency(sources, frequencies);
    }

    private static String getRandomUsingFrequency(String[] sources, int[] frequencies) {
        int max = IntStream.of(frequencies).sum();
        int roll = random.nextInt(max);
        String source;
        for (int i = 0; i < sources.length; i++) {
            source = sources[i];
            roll = roll - frequencies[i];
            if (roll <= 0) {
                return source;
            }
        }
        return sources[0];
    }

    public String randomCombiner() {
        String[] combiners = { "add", "blend", "select", "displace", "multiply", "power", "max", "min" };
        int[] frequencies =  { 5, 3, 3, 3, 2, 2, 1, 1 };
        return getRandomUsingFrequency(combiners, frequencies);
    }

    public String randomModifier() {
        String[] modifiers = { "abs", "clamp", "curve", "exponent", "terrace", "turbule"};
        int[] frequencies = { 5, 5, 5, 5, 5, 5 };
        return getRandomUsingFrequency(modifiers, frequencies);
    }

    public static void imageFromSampleFile(String localPath, boolean color, int chop, int widthHeight, int period) throws IOException {
        NmBuilder builder = NoiseFileBuilder.buildModuleFromFile(localPath);
        Module fileBuiltModule = builder.build();
        // ImageGenerator.generateImage(fileBuiltModule, true, chop, localPath + ".png", widthHeight, widthHeight, period, period, 10, 1000);
        System.out.println("Image file created.");
    }

    public StructureMap genRandomTree() {
        return genRandomTree(null, null);
    }

    public StructureMap genRandomTree(StructureMap structure, String parentModuleId) {
        // decide whether or not this element is to be a combo or not:
        String moduleId = UUID.randomUUID().toString();
        if (random.nextBoolean() || random.nextBoolean()) {
            String source = randomSource();
            if (structure == null) {
                structure = new StructureMap(moduleId, NmType.SOURCE, source);
            } else {
                // add as a combo child
                structure.addComboChild(parentModuleId, moduleId, NmType.SOURCE, source);
            }

            if (random.nextBoolean()) {
                // create a modifier for this source
                String modifier = randomModifier();
                String modifierId = UUID.randomUUID().toString();
                structure.addModifier(moduleId, modifierId, modifier);
            }
        } else {
            String combo = randomCombiner();
            if (structure == null) {
                structure = new StructureMap(moduleId, NmType.COMBO, combo);
            } else {
                // add as a combo child
                structure.addComboChild(parentModuleId, moduleId, NmType.COMBO, combo);
            }

            // recurse through children
            int numChildren;
            switch (combo) {
                case "displace": numChildren = 4; break;
                case "blend":
                case "select": numChildren = 3; break;
                default: numChildren = 2;
            }
            for (int i=1; i<=numChildren; i++) {
                genRandomTree(structure, moduleId);
            }
        }

        return structure;
    }

    public static void main(String[] args) throws IOException {
        // source - perlin, ridged, billow, voronoi, checkerboard, const, cylinders, spheres
        // combo - blend, add, displace, max, min, power, select
        // modifier - abs, clamp, curve, exponent, terrace, turbule

        String samplePath = "output/samples/sampler.csv";
        NoiseArtGenerator.imageFromSampleFile(samplePath, true, 5, 1024, 128);

        // start with a single perlin
        int startSeed = random.nextInt(Integer.MAX_VALUE);
        int numToGenerate = 100;
        String name = "sample";

        NoiseArtGenerator noiseGenerator = new NoiseArtGenerator();
        for (int seed = startSeed; seed < (startSeed+numToGenerate); seed++) {
            long startTime = Calendar.getInstance().getTimeInMillis();
            String filename = String.format("output/generated/%s_%s", name, seed);
            ParamsMap params = new ParamsMap(seed, 100, 100, 25, 100);

            // StructureMap structure = new StructureMap("root-111", NmType.COMBO, "add");
            // StructureMap structure = new StructureMap("root-111", NmType.SOURCE, "perlin");

            // structure.addComboChild("root-111", "ridged-111", NmType.SOURCE, "ridged");
            // structure.addComboChild("root-111", "const-111", NmType.SOURCE, "cylinders");
            // structure.addComboChild("root-111", "combo-111", NmType.COMBO, "add");
            // structure.addComboChild("combo-111", "perlin-111", NmType.SOURCE, "perlin");
            // structure.addComboChild("combo-111", "billow-111", NmType.SOURCE, "billow");
            // structure.addComboChild("root-111", "voro-111", NmType.SOURCE, "voronoi");
            // structure.addModifier("ridged-111", "terr-123", "terrace");

            StructureMap structure = noiseGenerator.genRandomTree(null, null);
            Module module = (new NmBuilder(structure, params)).build();

            // boolean genImg = ImageGenerator.generateImage(module, true, 5, filename + ".png",
            //        1024, 1024, 128, 128, 10, 1000);
            boolean genImg = true;
            if (genImg) {
                List<String> structureCsv = structure.asCsvList();
                List<String> paramsCsv = params.asCsvList();
                NoiseFileBuilder.writeNoiseTreeToFile(structureCsv, paramsCsv, filename + ".csv");
                long endTime = Calendar.getInstance().getTimeInMillis();
                double secs = (endTime - startTime) / 1000.0;
                System.out.println("generated new image in " + secs + " seconds");
            }
        }
    }

}

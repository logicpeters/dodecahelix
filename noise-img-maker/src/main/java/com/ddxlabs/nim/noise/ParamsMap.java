package com.ddxlabs.nim.noise;

import java.util.*;
import java.util.stream.Collectors;

public class ParamsMap {

    // whether or not to generate random values if they do not exist
    private boolean generator = false;

    private List<Double> dblPool = new ArrayList<>();
    private List<Integer> thouPool = new ArrayList<>();
    private List<Boolean> boolPool = new ArrayList<>();
    private List<Integer> seedPool = new ArrayList<>();

    private int moduleSeed; // special seed for this module

    private int dblPtr = -1;
    private int thouPtr = -1;
    private int boolPtr = -1;
    private int seedPtr = -1;

    private Map<String, String> usages;

    public ParamsMap(Map<String, String> usages) {
        generator = false;
        this.usages = usages;
    }

    public ParamsMap(int seed, int maxDoubles, int maxInts, int maxBools, int maxSeeds) {
        generator = true;
        usages = new HashMap<>();
        Random random = new Random(seed);
        for (int i=0; i<maxDoubles; i++) {
            dblPool.add(random.nextDouble());
        }
        for (int i=0; i<maxInts; i++) {
            thouPool.add(random.nextInt(1000));
        }
        for (int i=0; i<maxBools; i++) {
            boolPool.add(random.nextBoolean());
        }
        for (int i=0; i<maxSeeds; i++) {
            seedPool.add(random.nextInt(Integer.MAX_VALUE));
        }
    }

    private static String tokey(String moduleId, String paramId) {
        return String.format("%s-%s", moduleId, paramId);
    }

    public double getDob(String moduleId, String param) {
        return getDob(moduleId, param, 1);
    }

    public double getDob(String moduleId, String param, double multiplier) {
        String pkey = tokey(moduleId, param);
        if (generator && !usages.containsKey(pkey)) {
            dblPtr++;
            if (dblPtr >= dblPool.size()) {
                dblPtr = 0;
            }
            double value = multiplier * dblPool.get(dblPtr);
            // limit precision of double
            String formatted = String.format("%.4f", value);
            usages.put(pkey, formatted);
        }
        return Double.parseDouble(usages.get(pkey));
    }

    /**
     *
     * @param divver = number to divide value by, i.e; 1 for 1-1000, 10 for 1-100, 100 for 1-10
     * @param moduleId
     * @param param
     * @return
     */
    public int getInt(int divver, String moduleId, String param) {
        String pkey = tokey(moduleId, param);
        if (generator && !usages.containsKey(pkey)) {
            thouPtr++;
            if (thouPtr >= thouPool.size()) {
                thouPtr = 0;
            }
            int value = thouPool.get(thouPtr);
            value = Math.round(value / divver);
            usages.put(pkey, String.valueOf(value));
        }
        return Integer.parseInt(usages.get(pkey));
    }

    public int getSeed(String moduleId) {
        String pkey = tokey(moduleId, "seed");
        if (generator && !usages.containsKey(pkey)) {
            seedPtr++;
            if (seedPtr >= seedPool.size()) {
                seedPtr = 0;
            }
            int value = seedPool.get(seedPtr);
            usages.put(pkey, String.valueOf(value));
        }
        return Integer.parseInt(usages.get(pkey));
    }

    public boolean getBool(String moduleId, String param) {
        String pkey = tokey(moduleId, param);
        if (generator && !usages.containsKey(pkey) ) {
            boolPtr++;
            if (boolPtr >= boolPool.size()) {
                boolPtr = 0;
            }
            boolean value = boolPool.get(boolPtr);
            usages.put(pkey, String.valueOf(value));
        }
        return Boolean.parseBoolean(usages.get(pkey));
    }

    public void printUsages() {
        for (Map.Entry<String, String> usage : usages.entrySet()) {
            System.out.println(usage.getKey() + " = " + usage.getValue());
        }
    }

    public List<String> asCsvList() {
        List<String> lines = new ArrayList<>();
        for (Map.Entry<String, String> usage : usages.entrySet()) {
            lines.add(String.format("%s,%s", usage.getKey(), usage.getValue()));
        }
        return lines;
    }

    public void resetValue(String moduleId, String param, String value) {
        String pkey = tokey(moduleId, param);
        this.usages.put(pkey, value);
    }

    public String getModuleValue(String moduleId, String param) {
        return this.usages.get(tokey(moduleId, param));
    }

    /**
     *  Extracts all parameters for a given module into its own Map
     *  This map's keys will not contain the module ID.
     *
     * @param moduleId
     * @return
     */
    public Map<String, String> getEntriesForModule(String moduleId) {
        return usages.entrySet().stream()
                .filter(entry -> entry.getKey().toUpperCase().startsWith(moduleId.toUpperCase()))
                .collect(Collectors.toMap(entry -> {
                    String originalKey = entry.getKey();
                    return originalKey.substring(moduleId.length()+1);
                }, Map.Entry::getValue));
    }

    /**
     * Uploads the set of parameters for a given module.
     *
     * @param moduleId
     * @param entries
     */
    public void addForModule(String moduleId, Map<String, String> entries) {
        entries.entrySet().forEach(entry -> {
            usages.put(tokey(moduleId, entry.getKey()), entry.getValue());
        });
    }

    /**
     * Manually sets the generator function to true/false.
     *
     * @param generator
     */
    public void lockGenerator(boolean generator) {
        this.generator = generator;
    }
}

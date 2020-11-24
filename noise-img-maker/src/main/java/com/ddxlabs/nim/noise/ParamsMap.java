package com.ddxlabs.nim.noise;

import java.util.*;
import java.util.stream.Collectors;

public class ParamsMap {

    private final List<Double> dblPool = new ArrayList<>();
    private final List<Integer> thouPool = new ArrayList<>();
    private final List<Boolean> boolPool = new ArrayList<>();
    private final List<Integer> seedPool = new ArrayList<>();

    // maintain pointers to the last instance of the
    private int dblPtr = -1;
    private int thouPtr = -1;
    private int boolPtr = -1;
    private int seedPtr = -1;

    // how many numbers to maintain in the pools
    private int maxDoubles = 100;
    private int maxInts = 100;
    private int maxBools = 100;
    private int maxSeeds = 100;

    // as parameters are generated, they are fixed in this map.
    private Map<String, String> usages;

    public ParamsMap(Map<String, String> usages) {
        this.usages = usages;

        int seed = (new Random()).nextInt();
        setOrResetPools(seed);
    }

    /**
     * This initializes a pool of random numbers using a "seed" number from which to build future parameters.
     *
     * @param seed - seed for generating the initial random number pools.
     *
     * @param maxDoubles -- how many random doubles that you want to generate
     * @param maxInts -- how many random 1-1000 integers that you want to generate
     * @param maxBools -- how many random booleans that you want to generate
     * @param maxSeeds -- how many random 'seed' integers that you want to generate
     */
    public ParamsMap(int seed, int maxDoubles, int maxInts, int maxBools, int maxSeeds) {
        usages = new HashMap<>();

        this.maxDoubles = maxDoubles;
        this.maxInts = maxInts;
        this.maxBools = maxBools;
        this.maxSeeds = maxSeeds;

        setOrResetPools(seed);
    }

    private void setOrResetPools(int seed) {
        dblPool.clear();
        thouPool.clear();
        boolPool.clear();
        seedPool.clear();

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

    /**
     *
     * @param moduleId
     * @param param
     * @param multiplier - multiply the result by this amount
     * @return
     */
    public double getDob(String moduleId, String param, double multiplier) {
        String pkey = tokey(moduleId, param);

        // if the key doesn't exist (i.e; you are creating a new module)
        //   create one using a random value from the pool
        if (!usages.containsKey(pkey)) {
            dblPtr++;
            if (dblPtr >= dblPool.size()) {
                dblPtr = 0;
            }
            double value = multiplier * dblPool.get(dblPtr);
            // limit precision of double values
            String formatted = String.format("%.4f", value);
            usages.put(pkey, formatted);
        }

        return Double.parseDouble(usages.get(pkey));
    }

    /**
     *
     * @param divver = number to divide random value for result, i.e; 1 for 1-1000, 10 for 1-100, 100 for 1-10
     * @param moduleId
     * @param param
     * @return randome number from 1-1000 (divided by the 'divver')
     */
    public int getInt(int divver, String moduleId, String param) {
        String pkey = tokey(moduleId, param);

        if (!usages.containsKey(pkey)) {
            thouPtr++;
            if (thouPtr >= thouPool.size()) {
                thouPtr = 0;
            }
            int value = thouPool.get(thouPtr);
            value = value / divver;
            // add 1 to get true 1-1000 value (non-zero)
            value++;
            usages.put(pkey, String.valueOf(value));
        }
        return Integer.parseInt(usages.get(pkey));
    }

    public int getSeed(String moduleId) {
        String pkey = tokey(moduleId, "seed");

        if (!usages.containsKey(pkey)) {
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

        if (!usages.containsKey(pkey) ) {
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
        entries.forEach((key, value) -> usages.put(tokey(moduleId, key), value));
    }

    public void replaceParams(ParamsMap replacementParams) {
        // Can still generate after replacing
        this.usages.clear();
        this.usages.putAll(replacementParams.usages);
    }

    public void removeModuleParams(String moduleId) {
        usages.entrySet().removeIf(entry -> entry.getKey().startsWith(moduleId));
    }
}

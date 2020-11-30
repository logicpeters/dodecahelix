package com.ddxlabs.nim.noise;

import java.io.*;
import java.util.*;

public class NoiseFileBuilder {

    private StructureMap structureMap;
    private ParamsMap paramsMap;
    private ImageTweaks tweaks;

    public NoiseFileBuilder() {
        this.tweaks = new ImageTweaks();
    }

    private void buildFromFileLines(List<String> fileLines) {
        String rootModuleId = "invalid";

        Map<String, String> params = new HashMap<>();
        Map<String, NmType> moduleTypes = new HashMap<>();
        Map<String, String> moduleQualifiers = new HashMap<>();
        Map<String, List<String>> comboChildren = new HashMap<>();
        Map<String, String> modifiers = new HashMap<>();

        Iterator<String> lines = fileLines.iterator();
        String context = "none";
        String line;
        String[] parts;
        while (lines.hasNext()) {
            line = lines.next().trim();
            if (line.startsWith("#")) {
                // switch context
                context = line.substring(1).trim().toLowerCase();
            } else if (!line.isBlank()) {
                parts = line.split(",");
                switch (context) {
                    case "tweaks": this.tweaks.addTweak(parts[0], parts[1]); break;
                    case "params": params.put(parts[0], parts[1]); break;
                    case "root": rootModuleId = line; break;
                    case "types": moduleTypes.put(parts[0], NmType.valueOf(parts[1])); break;
                    case "qualifiers": moduleQualifiers.put(parts[0], parts[1]); break;
                    case "modifiers": modifiers.put(parts[0], parts[1]); break;
                    case "children": {
                        List<String> children = new ArrayList<>();
                        for (int i=1; i<parts.length; i++) {
                            children.add(parts[i]);
                        }
                        comboChildren.put(parts[0], children); break;
                    }
                }
            }
        }

        structureMap = new StructureMap(rootModuleId, moduleTypes, moduleQualifiers, comboChildren, modifiers);
        paramsMap = new ParamsMap(params);
    }

    public NmBuilder buildModuleFromFile(String fileName) throws IOException {

        System.out.println("reading from file " + fileName);
        try (FileReader fileReader = new FileReader(fileName);
             BufferedReader br = new BufferedReader(fileReader);
        ) {
            List<String> stringList = new ArrayList<String>();
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                if (!"".equals(sCurrentLine.trim())) {
                    stringList.add(sCurrentLine);
                }
            }

            this.buildFromFileLines(stringList);
            return new NmBuilder(structureMap, paramsMap, tweaks);
        }
    }

    private static void writeFile(String fileName, List<String> csvLines) throws IOException {
        File fout = new File(fileName);
        FileOutputStream fos = new FileOutputStream(fout);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        for (String line: csvLines) {
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    public static void writeNoiseTreeToFile(List<String> structureCsv,
                                            List<String> paramsCsv,
                                            List<String> tweaksCsv, String filePath) throws IOException {
        structureCsv.add("# PARAMS");
        structureCsv.addAll(paramsCsv);
        structureCsv.add("# TWEAKS");
        structureCsv.addAll(tweaksCsv);
        writeFile(filePath, structureCsv);
    }

}

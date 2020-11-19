package com.ddxlabs.nim.noise;

import org.spongepowered.noise.module.Module;

import java.io.*;
import java.util.*;

public class NoiseFileBuilder {

    private static StructureMap structureFromFileLines(List<String> fileLines) {

        String rootModuleId = "invalid";
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
                    case "params": break;
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

        return new StructureMap(rootModuleId, moduleTypes, moduleQualifiers, comboChildren, modifiers);
    }

    private static ParamsMap paramsFromFileLines(List<String> fileLines) {
        Map<String, String> params = new HashMap<>();

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
                if ("params".equals(context)) {
                    params.put(parts[0], parts[1]);
                }
            }
        }

        return new ParamsMap(params);
    }

    public static Module buildModuleFromFile(String fileName) throws IOException {
        fileName = System.getProperty("user.dir") + File.separator + fileName;
        fileName = fileName.replace("\\", "/");
        fileName = fileName.replace("/", "\\\\");
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

            StructureMap structureMap = structureFromFileLines(stringList);
            ParamsMap paramsMap = paramsFromFileLines(stringList);
            return (new NmBuilder(structureMap, paramsMap)).build();
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

    public static void writeNoiseTreeToFile(StructureMap structure, ParamsMap params, String filePath) throws IOException {
        List<String> lines = structure.asCsvList();
        lines.add("# PARAMS");
        lines.addAll(params.asCsvList());
        writeFile(filePath, lines);
    }

}

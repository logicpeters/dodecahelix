package com.ddxlabs.nim.noise;

import java.util.*;
import java.util.stream.Collectors;

public class StructureMap {

    private String rootModuleId;

    /**
     *  Maps a module ID to its type - combo, source, mod
     */
    private Map<String, NmType> moduleTypes = new HashMap<>();

    /**
     *  Maps a module ID to its detailed qualifier (i.e; Perlin, etc..)
     */
    private Map<String, String> moduleQualifiers = new HashMap<>();

    /**
     *  Maps a combo module ID to its source module ID's
     */
    private Map<String, List<String>> comboChildren = new HashMap<>();

    /**
     *  Maps a module ID to its modifier.
     */
    private Map<String, String> modifiers = new HashMap<>();

    /**
     *  Constructor for loading a previous image
     *
     * @param rootModuleId
     * @param moduleTypes
     * @param moduleQualifiers
     * @param comboChildren
     * @param modifiers
     */
    public StructureMap(String rootModuleId, Map<String, NmType> moduleTypes, Map<String, String> moduleQualifiers,
                        Map<String, List<String>> comboChildren, Map<String, String> modifiers) {
        this.rootModuleId = rootModuleId;
        this.moduleTypes = moduleTypes;
        this.moduleQualifiers = moduleQualifiers;
        this.comboChildren = comboChildren;
        this.modifiers = modifiers;
    }

    public StructureMap(String rootModuleId, NmType rootModuleType, String rootModuleQualifier) {
        // initializes the structure with a root element
        this.rootModuleId = rootModuleId;
        this.moduleTypes.put(rootModuleId, rootModuleType);
        this.moduleQualifiers.put(rootModuleId, rootModuleQualifier);
    }

    public void setRootModule(String moduleId) {
        this.rootModuleId = moduleId;
    }

    public void addNewModule(NmType type, String moduleId, String qualifier) {
        this.moduleTypes.put(moduleId, type);
        this.moduleQualifiers.put(moduleId, qualifier);
    }

    public void addModifier(String baseModuleId, String modifierModuleId, String modifiderQualifier) {
        this.moduleTypes.put(modifierModuleId, NmType.MODIFIER);
        this.moduleQualifiers.put(modifierModuleId, modifiderQualifier);
        this.modifiers.put(baseModuleId, modifierModuleId);
    }

    public void addComboChild(String comboModuleId, String childModuleId, NmType childType, String childQualifier) {
        this.moduleTypes.put(childModuleId, childType);
        this.moduleQualifiers.put(childModuleId, childQualifier);
        List<String> currentChildren = this.comboChildren.get(comboModuleId);
        if (currentChildren==null) {
            currentChildren = new ArrayList<>();
        }
        currentChildren.add(childModuleId);
        this.comboChildren.put(comboModuleId, currentChildren);
    }

    public String getRootModuleId() {
        return rootModuleId;
    }

    public NmType getTypeFor(String moduleId) {
        return this.moduleTypes.get(moduleId);
    }

    public String getQualifier(String moduleId) {
        return this.moduleQualifiers.get(moduleId);
    }

    public String getModifier(String moduleId) {
        return this.modifiers.get(moduleId);
    }

    public List<String> getChildren(String moduleId) {
        List<String> children = comboChildren.get(moduleId);
        if (children==null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public List<String> getUnattachedModules() {
        return this.moduleTypes.keySet().stream()
                .filter(m -> !rootModuleId.equalsIgnoreCase(m))
                .filter(m -> getParent(m).isEmpty())
                .collect(Collectors.toList());
    }

    public Optional<String> getParent(String moduleId) {
        for (Map.Entry<String, List<String>> entry : comboChildren.entrySet()) {
            if (entry.getValue().contains(moduleId)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    public Optional<String> getModified(String modifierModuleId) {
        for (Map.Entry<String, String> entry: modifiers.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(modifierModuleId)) {
                return Optional.of(entry.getKey());
            }
        }
        return Optional.empty();
    }

    public List<String> asCsvList() {
        List<String> csvLines = new ArrayList<>();
        csvLines.add("# ROOT");
        csvLines.add(rootModuleId);
        csvLines.add("# TYPES");
        for (Map.Entry<String, NmType> types : moduleTypes.entrySet()) {
            csvLines.add(String.format("%s,%s", types.getKey(), types.getValue().name()));
        }
        csvLines.add("# QUALIFIERS");
        for (Map.Entry<String, String> qualifier : moduleQualifiers.entrySet()) {
            csvLines.add(String.format("%s,%s", qualifier.getKey(), qualifier.getValue()));
        }
        csvLines.add("# CHILDREN");
        for (Map.Entry<String, List<String>> family : comboChildren.entrySet()) {
            String childrenCsv = family.getValue().stream().collect(Collectors.joining(","));
            csvLines.add(String.format("%s,%s", family.getKey(),childrenCsv));
        }
        csvLines.add("# MODIFIERS");
        for (Map.Entry<String, String> modifier : modifiers.entrySet()) {
            csvLines.add(String.format("%s,%s", modifier.getKey(), modifier.getValue()));
        }
        return csvLines;
    }

    public void printTree() {
        printTreeNode(this.rootModuleId, 0);
    }

    public void printTreeNode(String moduleId, int indent) {
        for (int i=0; i<indent; i++) {
            System.out.print("  ");
        }

        String moduleLabel = String.format("%s (%s)", moduleId, this.getQualifier(moduleId));
        System.out.print(moduleLabel);
        String modifier = this.modifiers.get(moduleId);
        if (modifier!=null) {
            printTreeNode(modifier, indent+1);
        }
        List<String> children = this.comboChildren.get(moduleId);
        if (children!=null) {
            for (String child : children) {
                printTreeNode(child, indent + 1);
            }
        }
    }


}

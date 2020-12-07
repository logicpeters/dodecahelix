package com.ddxlabs.nim.noise;

import com.ddxlabs.nim.noise.modules.ComboModuleBuilder;
import com.ddxlabs.nim.noise.modules.ModifierModuleBuilder;
import com.ddxlabs.nim.noise.modules.SourceModuleBuilder;
import org.spongepowered.noise.module.Module;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class NmBuilder implements ModelComponent {

    private int seed;
    private StructureMap structure;
    private ParamsMap params;

    /**
     *  Constructor - starts with a random seed
     */
    public NmBuilder() {
        this((new Random(Calendar.getInstance().getTimeInMillis())).nextInt(), "perlin");
    }

    /**
     *  Creates an initial structure and parameter map, with a single Perlin root module.
     * @param seed
     */
    public NmBuilder(int seed, String rootSourceQualifier) {
        this.seed = seed;
        params = new ParamsMap(seed, 100, 100, 25, 100);
        String initialId = rootSourceQualifier + "_" + UUID.randomUUID().toString();
        structure = new StructureMap(initialId, NmType.SOURCE, rootSourceQualifier);
    }

    public NmBuilder(StructureMap structure, ParamsMap params) {
        this.structure = structure;
        this.params = params;
    }

    public void replaceStructure(StructureMap replacementStructure, ParamsMap replacementParams) {
        this.params.replaceParams(replacementParams);
        this.structure.fromStructure(replacementStructure);
    }

    public Module build() {
        return this.buildModuleNode(structure.getRootModuleId());
    }

    /**
     * Recursively builds all modules underneath the root.
     *
     * @param moduleId
     * @return
     */
    private Module buildModuleNode(String moduleId) {
        NmType moduleType = structure.getTypeFor(moduleId);
        String qualifier = structure.getQualifier(moduleId);
        Module nodeModule = createModule(moduleId, moduleType, qualifier, params);
        // System.out.printf("building %s module type %s : %s%n", moduleType, qualifier, moduleId);

        if (moduleType==NmType.COMBO) {
            // child modules should be source
            int childCount = 0;
            for (String childModuleId : structure.getChildren(moduleId)) {
                Module builtChild = buildModuleNode(childModuleId);
                nodeModule.setSourceModule(childCount, builtChild);
                childCount++;
            }

            // TODO displacement modules?
        }

        String modifier = structure.getModifier(moduleId);
        if (modifier!=null) {
            // if it has a modifier, replace
            Module modifierModule = buildModuleNode(modifier);
            modifierModule.setSourceModule(0, nodeModule);
            nodeModule = modifierModule;
        }

        return nodeModule;
    }

    private Module createModule(String moduleId, NmType moduleType, String qualifier, ParamsMap params) {
        Module module;
        switch (moduleType) {
            case COMBO: module = ComboModuleBuilder.build(moduleId, qualifier, params); break;
            case MODIFIER: module = ModifierModuleBuilder.build(moduleId, qualifier, params); break;
            default: module = SourceModuleBuilder.build(moduleId, qualifier, params); break;
        }
        return module;
    }

    public StructureMap getStructure() {
        // TODO - make immutable, and only allow edits from this class
        return structure;
    }

    public ParamsMap getParams() {
        // TODO - make immutable, and only allow edits from this class
        return params;
    }

    public int getBaseSeed() {
        return seed;
    }

    public String getParamValue(String moduleId, String paramKey) {
        return params.getModuleValue(moduleId, paramKey);
    }

    public void updateParamValue(String moduleId, String paramKey, String newValue) {
        params.resetValue(moduleId, paramKey, newValue);
    }
}

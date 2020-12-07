package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.NimException;
import com.ddxlabs.nim.noise.*;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.view.Views;
import com.ddxlabs.nim.utils.NumberUtils;
import com.ddxlabs.nim.noise.modules.*;
import com.ddxlabs.nim.view.tabs.ModuleTabs;

import java.util.*;

public class ModuleHandler implements ControllerComponent {

    // models
    private NmBuilder moduleBuilder;
    private UserPreferences prefs;

    // views
    private ModuleTabs moduleTabs;

    // controllers
    private SystemHandler systemHandler;
    private ImageGenerationHandler imageGenerationHandler;

    public ModuleHandler(UserPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void init(Controllers controllers, Views views, Models models) {
        this.moduleBuilder = models.getNmBuilder();
        this.moduleTabs = views.getModuleTabs();
        this.systemHandler = controllers.getSystemHandler();
        this.imageGenerationHandler = controllers.getImageGenerationHandler();
    }

    public String addComboModule(ComboQualifier comboQualifier) {
        // TODO - cant build a combo without sources?
        String newModuleId = comboQualifier.name() + "_" + UUID.randomUUID().toString();
        StructureMap structure = moduleBuilder.getStructure();
        ParamsMap params = moduleBuilder.getParams();
        String qualifierName = comboQualifier.name().toLowerCase();
        structure.addNewModule(NmType.COMBO, newModuleId, qualifierName);
        ComboModuleBuilder.build(newModuleId, qualifierName, params);
        moduleBuilder.getParams().getEntriesForModule(newModuleId);
        moduleTabs.addModule(NmType.COMBO, newModuleId);

        moduleTabs.refreshTabData();
        return newModuleId;
    }

    public String addSourceModule(SourceQualifier sourceQualifier) {
        String newModuleId = sourceQualifier.name() + "_" + UUID.randomUUID().toString();
        StructureMap structure = moduleBuilder.getStructure();
        ParamsMap params = moduleBuilder.getParams();
        String qualifierName = sourceQualifier.name().toLowerCase();
        structure.addNewModule(NmType.SOURCE, newModuleId, qualifierName);
        // generate random params for this new module
        SourceModuleBuilder.build(newModuleId, qualifierName, params);
        moduleBuilder.getParams().getEntriesForModule(newModuleId);
        moduleTabs.addModule(NmType.SOURCE, newModuleId);

        moduleTabs.refreshTabData();
        return newModuleId;
    }

    public String addModifierModule(String moduleId, String modifierQualifier) {
        String modifierModuleId = modifierQualifier + "_" + UUID.randomUUID().toString();
        this.moduleBuilder.getStructure().addModifier(moduleId, modifierModuleId, modifierQualifier);

        ModifierModuleBuilder.build(modifierModuleId, modifierQualifier, moduleBuilder.getParams());
        moduleBuilder.getParams().getEntriesForModule(modifierModuleId);
        moduleTabs.addModule(NmType.MODIFIER, modifierModuleId);

        moduleTabs.refreshTabData();
        this.imageGenerationHandler.generateAndShowImage();
        return modifierModuleId;
    }

    public Map<String, String> getParamsForModule(String moduleId) {
        return Collections.unmodifiableMap(this.moduleBuilder.getParams().getEntriesForModule(moduleId));
    }

    public String getParamValueForModule(String moduleId, String paramName) {
        return this.moduleBuilder.getParams().getModuleValue(moduleId, paramName);
    }

    public void setParamValueForModule(String moduleId, String paramName, String paramValue) {
        // TODO - dont let parameter fall outside constraints
        this.moduleBuilder.getParams().resetValue(moduleId, paramName, paramValue);
    }

    public String getQualifierForModule(String moduleId) {
        return this.moduleBuilder.getStructure().getQualifier(moduleId);
    }

    public void setRootModule(String moduleId) {
        this.moduleBuilder.getStructure().setRootModule(moduleId);
        moduleTabs.refreshTabData();
    }

    public Optional<String> getModifierFor(String moduleId) {
        return Optional.ofNullable(this.moduleBuilder.getStructure().getModifier(moduleId));
    }

    public Optional<String> getModified(String modifierModuleId) {
        return this.moduleBuilder.getStructure().getModified(modifierModuleId);
    }

    /**
     *  Returns the parent of this module if it has one.
     *
     *  If it is root, or does not have a parent return Optional.empty()
     *  If it's a Modifier, returns the Source it is modified to.
     *
     * @param moduleId
     * @return
     */
    public Optional<String> getParent(String moduleId) {
        StructureMap sMap = this.moduleBuilder.getStructure();
        NmType type = sMap.getTypeFor(moduleId);
        if (NmType.MODIFIER == type) {
            return getModified(moduleId);
        } else {
            return sMap.getParent(moduleId);
        }
    }

    /**
     * Tests to see if this module is the root module of the structure.
     *
     * @param moduleId
     * @return
     */
    public boolean isModuleRoot(String moduleId) {
        return this.moduleBuilder.getStructure().getRootModuleId().equalsIgnoreCase(moduleId);
    }

    public List<String> getChildModules(String moduleId) {
        return this.moduleBuilder.getStructure().getChildren(moduleId);
    }

    public List<String> getUnattachedModules() {
        return this.moduleBuilder.getStructure().getUnattachedModules();
    }

    public void showModuleInTabView(String moduleId) {
        this.moduleTabs.showTabForModule(moduleId);
    }

    public NmType getTypeForModule(String moduleId) {
        return this.moduleBuilder.getStructure().getTypeFor(moduleId);
    }

    public void setSourceModulesForCombo(String comboModuleId, List<String> children) {
        StructureMap structure = this.moduleBuilder.getStructure();
        structure.resetComboChildren(comboModuleId, children);
        moduleTabs.refreshTabData();
    }

    public List<String> getStructureAsCsv() {
        return this.moduleBuilder.getStructure().asCsvList();
    }

    public List<String> getParamsAsCsv() {
        return this.moduleBuilder.getParams().asCsvList();
    }

    public void loadNewBuilderStructure(StructureMap structure, ParamsMap params) {
        this.moduleBuilder.replaceStructure(structure, params);
    }

    public Map<String, NmType> getModuleTypes() {
        return this.moduleBuilder.getStructure().getModuleTypes();
    }

    public void incrementParam(String moduleId, String paramKey, boolean decrementInstead, int factor) {
        String paramValue = this.moduleBuilder.getParamValue(moduleId, paramKey);
        String newValue = NumberUtils.increment(paramValue, decrementInstead, factor);
        // TODO - make sure new value does not violate constraints
        this.moduleBuilder.updateParamValue(moduleId, paramKey, newValue);
        moduleTabs.refreshTabData();
        // TODO - preview??
    }

    public void deleteModule(String moduleId) {
        // remove from structure
        try {
            this.moduleBuilder.getStructure().removeModule(moduleId);
        } catch (NimException e) {
            e.printStackTrace();
            this.systemHandler.popupMessage(e.getMessage());
            return;
        }

        // remove params
        this.moduleBuilder.getParams().removeModuleParams(moduleId);

        // UI changes
        this.moduleTabs.removeModuleTab(moduleId);
        this.moduleTabs.refreshTabData();
    }

    public void randomizeParams(String moduleId) {
        Random random = new Random();
        Map<String, String> moduleParams = this.moduleBuilder.getParams().getEntriesForModule(moduleId);
        moduleParams.forEach((key, value) -> {
            System.out.println("randomizing param : " + key);
            if (!key.startsWith("seed")) {
                String newValue = NumberUtils.randomize(value, random);
                System.out.println("resetting to : " + newValue);
                this.moduleBuilder.getParams().resetValue(moduleId, key, newValue);
            }
        });
        this.moduleTabs.refreshTabData();
        this.imageGenerationHandler.generateAndShowImage();
    }

    public String getRootModule() {
        return moduleBuilder.getStructure().getRootModuleId();
    }

}

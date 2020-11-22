package com.ddxlabs.nim.controller;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.Models;
import com.ddxlabs.nim.UserPreferences;
import com.ddxlabs.nim.Views;
import com.ddxlabs.nim.noise.NmBuilder;
import com.ddxlabs.nim.noise.NmType;
import com.ddxlabs.nim.noise.ParamsMap;
import com.ddxlabs.nim.noise.StructureMap;
import com.ddxlabs.nim.noise.modules.*;
import com.ddxlabs.nim.view.tabs.ModuleTabs;

import javax.print.DocFlavor;
import java.util.*;

public class ModuleHandler implements ControllerComponent {

    // models
    private NmBuilder moduleBuilder;
    private UserPreferences prefs;

    // views
    private ModuleTabs moduleTabs;

    public ModuleHandler(UserPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void init(Controllers controllers, Views views, Models models) {
        this.moduleBuilder = models.getNmBuilder();
        this.moduleTabs = views.getModuleTabs();
    }

    public String addComboModule(ComboQualifier comboQualifier) {
        // TODO - cant build a combo without sources?
        String newModuleId = UUID.randomUUID().toString();
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
        String newModuleId = UUID.randomUUID().toString();
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
        String modifierModuleId = UUID.randomUUID().toString();
        this.moduleBuilder.getStructure().addModifier(moduleId, modifierModuleId, modifierQualifier);

        ModifierModuleBuilder.build(modifierModuleId, modifierQualifier, moduleBuilder.getParams());
        moduleBuilder.getParams().getEntriesForModule(modifierModuleId);
        moduleTabs.addModule(NmType.MODIFIER, modifierModuleId);

        moduleTabs.refreshTabData();
        return modifierModuleId;
    }

    public Map<String, String> getParamsForModule(String moduleId) {
        return Collections.unmodifiableMap(this.moduleBuilder.getParams().getEntriesForModule(moduleId));
    }

    public String getParamValueForModule(String moduleId, String paramName) {
        return this.moduleBuilder.getParams().getModuleValue(moduleId, paramName);
    }

    public void setParamValueForModule(String moduleId, String paramName, String paramValue) {
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
}

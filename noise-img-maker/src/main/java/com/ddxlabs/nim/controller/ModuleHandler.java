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

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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

    public void addComboModule(ComboQualifier comboQualifier) {
        // cant build a combo without sources
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
        return newModuleId;
    }

    public String addModifierModule(String moduleId, String modifierQualifier) {
        String modifierModuleId = UUID.randomUUID().toString();
        this.moduleBuilder.getStructure().addModifier(moduleId, modifierModuleId, modifierQualifier);

        ModifierModuleBuilder.build(modifierModuleId, modifierQualifier, moduleBuilder.getParams());
        moduleBuilder.getParams().getEntriesForModule(modifierModuleId);
        moduleTabs.addModule(NmType.MODIFIER, modifierModuleId);
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
    }

    public Optional<String> getModifierModuleId(String moduleId) {
        return Optional.ofNullable(this.moduleBuilder.getStructure().getModifier(moduleId));
    }

}

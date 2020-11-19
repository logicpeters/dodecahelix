package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.noise.ParamsMap;

/**
 *  Configures a source module
 *
 *  TODO:
 *  Add field for "is root? yes/no" w/ button for "Make Root"
 *  Add field for "combo of" w/ button for "Combine With"
 *  Add field for "modified by" w/ button for "Modify With"
 */
public class SourceModuleConfigTab extends ModuleConfigTab {

    public SourceModuleConfigTab(String moduleId, String qualifier, ParamsMap params) {
        super(moduleId, qualifier, params);
    }

}

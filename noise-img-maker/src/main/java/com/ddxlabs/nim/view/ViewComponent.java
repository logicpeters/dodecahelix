package com.ddxlabs.nim.view;

import com.ddxlabs.nim.Controllers;
import com.ddxlabs.nim.UserPreferences;

import javax.swing.*;

/**
 * Created on 5/30/2019.
 */
public interface ViewComponent {

    /**
     * Controllers are added in after constructor, before buildUI.
     *
     * @param controllers
     */
    void init(Controllers controllers);

    /**
     * Creates a new JComponent that represents the component view.
     *
     * @return
     */
    JComponent buildUI();

    /**
     *  Signal to the UI that the preferences have been updated and the look and feel may change.
     *
     * @param
     */
    void applyPreferences();

}

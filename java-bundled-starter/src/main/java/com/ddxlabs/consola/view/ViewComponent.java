package com.ddxlabs.consola.view;

import com.ddxlabs.consola.UserPreferences;

import javax.swing.*;

/**
 * Created on 5/30/2019.
 */
public interface ViewComponent {

    /**
     * Creates a new JComponent that represents the component view.
     *
     * @return
     */
    JComponent buildUI();

    /**
     *  Apply the user preferences to this component.
     *
     * @param
     */
    void applyPreferences(UserPreferences preferences);

}

package com.ddxlabs.nim.view.tabs;

import com.ddxlabs.nim.controller.ModuleHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *  JLabel that you can click on and will open up a
 */
public class ClickableModuleIdLabel extends JLabel implements MouseListener {

    private String textFormat;
    private String moduleId;
    private ModuleHandler moduleHandler;

    public ClickableModuleIdLabel(String textFormat, ModuleHandler moduleHandler) {
        super(String.format(textFormat, "None"));
        this.addMouseListener(this);
        this.moduleHandler = moduleHandler;
        this.textFormat = textFormat;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
        this.setText(String.format(textFormat, moduleId));
        this.setForeground(Color.BLUE);
    }

    public void setNoModule() {
        this.moduleId = null;
        this.setText(String.format(textFormat, "None"));
        this.setForeground(Color.BLACK);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (this.moduleId != null) {
            this.moduleHandler.showModuleInTabView(moduleId);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

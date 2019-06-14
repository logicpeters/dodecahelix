package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.button.ButtonBar;
import org.dmp.gwtpurdy.client.button.GradientButton;
import org.dmp.gwtpurdy.client.color.ColorScheme;
import org.dmp.gwtpurdy.client.form.FormCellGroup;
import org.dmp.gwtpurdy.client.form.FormView;
import org.dmp.gwtpurdy.showcase.demo.DialogDemoPresenter.Display;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

public class DialogDemoView extends FormView implements DemoView, Display {
	
	private GradientButton infoDialogButton;
	private GradientButton errorDialogButton;
	private GradientButton formDialogButton;
	private GradientButton pleaseWaitButton;

    public DialogDemoView() {
        super();
        this.setWidth("100%");
        
        FormCellGroup baseDemoGroup = new FormCellGroup(SafeHtmlUtils.fromString("Dialogs"));
        baseDemoGroup.setGroupLabelVisible(true);
        this.appendCellGroup(baseDemoGroup);
        
        infoDialogButton = new GradientButton("Info Dialog");
        infoDialogButton.setColorScheme(ColorScheme.LIGHT_BLUE);
        this.appendWidget(new ButtonBar(infoDialogButton));
        
        errorDialogButton = new GradientButton("Error Dialog");
        errorDialogButton.setColorScheme(ColorScheme.LIGHT_RED);
        this.appendWidget(new ButtonBar(errorDialogButton));
        
        pleaseWaitButton = new GradientButton("Please Wait Dialog");
        pleaseWaitButton.setColorScheme(ColorScheme.DARK_GREY);
        this.appendWidget(new ButtonBar(pleaseWaitButton));
        
        formDialogButton = new GradientButton("Custom Form Dialog");
        formDialogButton.setColorScheme(ColorScheme.DARK_BLUE);
        this.appendWidget(new ButtonBar(formDialogButton));
    }
    
    @Override
    public Demo getDemoDefinition() {
        return Demo.DIALOGS;
    }

	@Override
	public HasClickHandlers getInfoButton() {
		return this.infoDialogButton;
	}

	@Override
	public HasClickHandlers getPleaseWaitButton() {
		return this.pleaseWaitButton;
	}

	@Override
	public HasClickHandlers getErrorDialogButton() {
		return this.errorDialogButton;
	}

	@Override
	public HasClickHandlers getFormDialogButton() {
		return this.formDialogButton;
	}

}

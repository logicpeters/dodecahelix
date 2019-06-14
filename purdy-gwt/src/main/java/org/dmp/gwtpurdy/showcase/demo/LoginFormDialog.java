package org.dmp.gwtpurdy.showcase.demo;

import org.dmp.gwtpurdy.client.base.component.TitleBar;
import org.dmp.gwtpurdy.client.button.ButtonBar;
import org.dmp.gwtpurdy.client.button.GradientButton;
import org.dmp.gwtpurdy.client.color.ColorResource;
import org.dmp.gwtpurdy.client.dialog.DialogConfig;
import org.dmp.gwtpurdy.client.dialog.ModalDialog;
import org.dmp.gwtpurdy.client.form.FormResource;
import org.dmp.gwtpurdy.client.images.ImgResources;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public class LoginFormDialog extends ModalDialog {
    
    public static final String DIALOG_ID = "loginForm";
    
    private DockLayoutPanel innerPanel;
    private TitleBar titleBar;
    private Button submitButton;

    public LoginFormDialog() {
        super();
        this.setWidth("400px");
        this.setHeight("250px");

        innerPanel = new DockLayoutPanel(Unit.PX);

        titleBar = new TitleBar(ImgResources.INSTANCE.security(), "Login");
        titleBar.removeStyleName(FormResource.INSTANCE.formCss().formFieldGradient());
        titleBar.addStyleName(ColorResource.INSTANCE.colorCss().colorSchemeDarkBlue());
        innerPanel.addNorth(titleBar, 40);

        innerPanel.addStyleName(ColorResource.INSTANCE.colorCss().colorSchemeBase());

        submitButton = new GradientButton("Submit");

        // todo -- delegate this to the presenter
        submitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                LoginFormDialog.this.hideDisplay();
            }
        });

        ButtonBar buttonBar = new ButtonBar(submitButton);
        buttonBar.centerButtons();
        innerPanel.addSouth(buttonBar, 60);

        LoginForm loginForm = new LoginForm();
        innerPanel.add(loginForm);

        this.setInnerWidget(innerPanel);
    }

    public Button getConfirmationButton() {
        return submitButton;
    }

    @Override
    public void configure(DialogConfig dialogConfig) {
        super.configure(dialogConfig);
        titleBar.setTitleLabel(dialogConfig.getDialogTitle());
    }
}

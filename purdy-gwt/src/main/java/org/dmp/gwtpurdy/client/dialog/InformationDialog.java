package org.dmp.gwtpurdy.client.dialog;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.base.component.TitleBar;
import org.dmp.gwtpurdy.client.button.ButtonBar;
import org.dmp.gwtpurdy.client.button.GradientButton;
import org.dmp.gwtpurdy.client.color.ColorResource;
import org.dmp.gwtpurdy.client.images.ImgResources;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class InformationDialog extends ModalDialog {

    private DockLayoutPanel innerPanel;
    private TitleBar titleBar;
    private HTML message = new HTML("");
    private Button confirmationButton;

    public InformationDialog() {
        super();
        this.setWidth("300px");
        this.setHeight("200px");

        innerPanel = new DockLayoutPanel(Unit.PX);

        titleBar = new TitleBar(ImgResources.INSTANCE.information(), "Information");
        
        innerPanel.addNorth(titleBar, 40);
        innerPanel.addStyleName(ColorResource.INSTANCE.colorCss().colorSchemeBase());

        confirmationButton = new GradientButton("OK");

        // todo -- delegate this to the presenter
        confirmationButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                InformationDialog.this.hideDisplay();
            }
        });

        ButtonBar buttonBar = new ButtonBar(confirmationButton);
        buttonBar.centerButtons();
        innerPanel.addSouth(buttonBar, 60);

        FlowPanel messagePanel = new FlowPanel();
        messagePanel.setWidth("100%");
        message.addStyleName(BaseResource.INSTANCE.baseCss().dialogInformationMessage());
        messagePanel.add(message);
        innerPanel.add(messagePanel);

        this.setInnerWidget(innerPanel);
    }

    public Button getConfirmationButton() {
        return confirmationButton;
    }

    @Override
    public void configure(DialogConfig dialogConfig) {
        super.configure(dialogConfig);
        message.setHTML(dialogConfig.getDialogMessage());
        titleBar.setTitleLabel(dialogConfig.getDialogTitle());
    }
}

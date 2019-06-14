package org.dmp.gwtpurdy.client.dialog;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.color.ColorResource;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;

public class PleaseWaitDialog extends ModalDialog {

	private HorizontalPanel innerPanel;
	private HTML pleaseWaitMessage = new HTML("Please Wait..");
	
	public PleaseWaitDialog() {
		super();
		
		innerPanel = new HorizontalPanel();
		innerPanel.setSpacing(10);
		innerPanel.addStyleName(BaseResource.INSTANCE.baseCss().dialogPleaseWait());
		innerPanel.addStyleName(BaseResource.INSTANCE.baseCss().unroundedBorder());
		innerPanel.addStyleName(ColorResource.INSTANCE.colorCss().colorSchemeLightGrey());
		
		Image spinnerImage = new Image(BaseResource.INSTANCE.waitSpinner());
		innerPanel.add(spinnerImage);
		innerPanel.setCellVerticalAlignment(spinnerImage, HasVerticalAlignment.ALIGN_MIDDLE);
		
		innerPanel.add(pleaseWaitMessage);
		innerPanel.setCellVerticalAlignment(pleaseWaitMessage, HasVerticalAlignment.ALIGN_MIDDLE);
		
        this.setInnerWidget(innerPanel);
	}
	
    @Override
    public void configure(DialogConfig dialogConfig) {
         pleaseWaitMessage.setHTML(dialogConfig.getDialogMessage());
    }
	
}

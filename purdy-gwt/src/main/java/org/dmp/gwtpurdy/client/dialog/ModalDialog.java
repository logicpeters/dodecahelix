package org.dmp.gwtpurdy.client.dialog;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.dialog.ModalDialogPresenter.Display;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *   Modal window -- centered window that masks the 
 * 
 * @author dmpuser
 *
 */
public class ModalDialog extends PopupPanel implements Display {
    
    private static final String MODAL_MASK_DIV_ID = "pgwt-mdl-mask";
    
    private Widget innerWidget;
    
    public ModalDialog() {
        super(false);
        
        addMaskDivIfMissing();

        this.setStyleName(BaseResource.INSTANCE.baseCss().modalDialog());
        this.addStyleName(BaseResource.INSTANCE.baseCss().bigFont());
        this.addStyleName(BaseResource.INSTANCE.baseCss().unroundedBorder());
        //this.addStyleName(BaseResource.INSTANCE.baseCss().colorSchemeLightGrey());
        
        //DOM.setStyleAttribute(this.getElement(), "zIndex", "800001");
        this.setModal(true);
    }

    private void addMaskDivIfMissing() {
        RootPanel modalMaskDiv = RootPanel.get(MODAL_MASK_DIV_ID);
        if (modalMaskDiv==null) {
            SimplePanel simple = new SimplePanel();
            RootPanel.get().add(simple);
            simple.getElement().setId(MODAL_MASK_DIV_ID);
        }
    }

    public ModalDialog(Widget innerWidget) {
        this();
        setInnerWidget(innerWidget);
    }
    
    public void setInnerWidget(Widget innerWidget) {
        this.innerWidget = innerWidget;
        this.setWidget(innerWidget);
        this.center();
        this.hide();
    }
    
    public void showDisplay() {
        RootPanel.get(MODAL_MASK_DIV_ID).addStyleName(BaseResource.INSTANCE.baseCss().modalDialogMask());
        center();
        show();
    }
    
    public void hideDisplay() {
        RootPanel.get(MODAL_MASK_DIV_ID).removeStyleName(BaseResource.INSTANCE.baseCss().modalDialogMask());
        hide();
    }

    public Widget getInnerWidget() {
        return innerWidget;
    }

    @Override
    public void configure(DialogConfig dialogConfig) {
        String dialogWidth = dialogConfig.getDialogWidth();
        String dialogHeight = dialogConfig.getDialogHeight();
        if (dialogWidth!=null) {
            this.setWidth(dialogWidth);
        }
        if (dialogHeight!=null) {
            this.setHeight(dialogHeight);
        }
    }
    
}

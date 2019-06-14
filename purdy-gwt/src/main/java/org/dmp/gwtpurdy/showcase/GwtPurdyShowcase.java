package org.dmp.gwtpurdy.showcase;

import org.dmp.gwtpurdy.client.dialog.ModalDialogEvent;
import org.dmp.gwtpurdy.client.dialog.ModalDialogPresenter;
import org.dmp.gwtpurdy.client.layout.CenteredPanel;
import org.dmp.gwtpurdy.showcase.demo.DefaultFormDemoPresenter;
import org.dmp.gwtpurdy.showcase.demo.DefaultFormDemoView;
import org.dmp.gwtpurdy.showcase.demo.Demo;
import org.dmp.gwtpurdy.showcase.demo.DialogDemoPresenter;
import org.dmp.gwtpurdy.showcase.demo.DialogDemoView;
import org.dmp.gwtpurdy.showcase.demo.DisplayDemoEvent;
import org.dmp.gwtpurdy.showcase.demo.GridDemoView;
import org.dmp.gwtpurdy.showcase.demo.LabelsOnTopDemoView;
import org.dmp.gwtpurdy.showcase.demo.LoginFormDialog;
import org.dmp.gwtpurdy.showcase.demo.MultiColumnDemoView;
import org.dmp.gwtpurdy.showcase.demo.StatusBarDemoView;
import org.dmp.gwtpurdy.showcase.demo.TreeListDemoView;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GwtPurdyShowcase implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {

        int appWidth = 800;
        int appHeight = 600;

        ShowcaseView scView = new ShowcaseView(appWidth, appHeight);
        CenteredPanel centerPanel = new CenteredPanel(scView, appWidth, appHeight);
        RootPanel.get().add(centerPanel);

        // Create and add demo's
        DefaultFormDemoView defaultFormView = new DefaultFormDemoView();
        scView.addDemo(defaultFormView);

        LabelsOnTopDemoView labelsTopView = new LabelsOnTopDemoView();
        scView.addDemo(labelsTopView);

        MultiColumnDemoView multiColumnView = new MultiColumnDemoView();
        scView.addDemo(multiColumnView);
        
        DialogDemoView dialogView = new DialogDemoView();
        scView.addDemo(dialogView);
        
        GridDemoView gridView = new GridDemoView();
        scView.addDemo(gridView);
        
        StatusBarDemoView statusView = new StatusBarDemoView();
        scView.addDemo(statusView);
        
        TreeListDemoView treeListView = new TreeListDemoView();
        scView.addDemo(treeListView);

        final EventBus eventBus = new SimpleEventBus();
        ShowcasePresenter scPresenter = new ShowcasePresenter(scView, eventBus);
        
        new DefaultFormDemoPresenter(defaultFormView, eventBus);
        new DialogDemoPresenter(dialogView, eventBus);
        
        // Use this for all modal dialog presenters
        ModalDialogPresenter dialogPresenter = new ModalDialogPresenter(eventBus);
        
        // add any custom dialogs here (i.e; login form)
        LoginFormDialog customDialog = new LoginFormDialog();
        dialogPresenter.addDialog(LoginFormDialog.DIALOG_ID, customDialog);

        eventBus.addHandler(DisplayDemoEvent.TYPE, scPresenter);
        eventBus.addHandler(ModalDialogEvent.TYPE, dialogPresenter);

        eventBus.fireEvent(new DisplayDemoEvent(Demo.DEFAULT_FORM));
    }

}

package org.dmp.gwtpurdy.showcase;

import java.util.ArrayList;
import java.util.List;

import org.dmp.gwtpurdy.client.base.BaseResource;
import org.dmp.gwtpurdy.client.base.component.TitleBar;
import org.dmp.gwtpurdy.client.color.ColorResource;
import org.dmp.gwtpurdy.client.color.ColorScheme;
import org.dmp.gwtpurdy.client.layout.SimpleDeckPanel;
import org.dmp.gwtpurdy.client.list.ListItem;
import org.dmp.gwtpurdy.client.list.ListView;
import org.dmp.gwtpurdy.showcase.ShowcasePresenter.Display;
import org.dmp.gwtpurdy.showcase.demo.Demo;
import org.dmp.gwtpurdy.showcase.demo.DemoView;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlowPanel;

public class ShowcaseView extends DockLayoutPanel implements Display {
	
	private SimpleDeckPanel demoPanel;
	private ListView demoListView;

    public ShowcaseView(Integer appWidth, Integer appHeight) {
    	super(Unit.PX);
        this.setWidth(appWidth.toString());
        this.setHeight(appHeight.toString());
    	
        BaseResource.INSTANCE.baseCss().ensureInjected();
        ColorResource.INSTANCE.colorCss().ensureInjected();
        ShowcaseResources.INSTANCE.showcaseCss().ensureInjected();
        
    	this.addStyleName(BaseResource.INSTANCE.baseCss().scrollable());
        //this.addStyleName(BaseResource.INSTANCE.baseCss().textureLightStraw());
        this.addStyleName(BaseResource.INSTANCE.baseCss().bigFont());
        this.addStyleName(BaseResource.INSTANCE.baseCss().window());
        this.addStyleName(BaseResource.INSTANCE.baseCss().unroundedBorder());
        this.addStyleName(BaseResource.INSTANCE.baseCss().reset());
        
        TitleBar title = new TitleBar("Purdy-GWT Showcase");
        title.setColor(ColorScheme.DARK_BLUE);
        this.addNorth(title, 40);
        
        demoListView = new ListView();
        List<ListItem> demoList = new ArrayList<ListItem>();
        for (Demo demo : Demo.values()) {
        	ListItem li = new ListItem(demo.name());
        	li.setDisplay(demo.getDisplayName());
        	li.setIconUrl(demo.getIconUrl());
            demoList.add(li);
        }        
        demoListView.loadList(demoList);
        
        FlowPanel demoListFrame = new FlowPanel();
        demoListFrame.addStyleName(ShowcaseResources.INSTANCE.showcaseCss().showcaseDemoList());
        demoListFrame.add(demoListView);
        this.addWest(demoListFrame, 200);
        
        demoPanel = new SimpleDeckPanel();
        this.add(demoPanel);
    }
	
	@Override
	public void showDemo(Demo demo) {
		demoPanel.showCard(demo.name());
	}
	
	public void addDemo(DemoView demo) {
		demoPanel.addCard(demo.getDemoDefinition().name(),demo.asWidget());
	}

	@Override
	public HasClickHandlers getDemoList() {
		return demoListView;
	}

	@Override
	public String getDemoListValue() {
		return demoListView.getSelectedItem().getValue();
	}

}

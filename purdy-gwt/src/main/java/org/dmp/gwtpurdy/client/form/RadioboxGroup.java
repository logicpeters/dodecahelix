package org.dmp.gwtpurdy.client.form;

import java.util.HashMap;
import java.util.Map;

import org.dmp.gwtpurdy.client.base.Alignment;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 *   Arranges a grouping of radio buttons in either vertical or horizontal position
 *   
 * @author dpeters
 *
 */
public class RadioboxGroup extends Composite implements FormField {

	private CellPanel layoutPanel;
	private Map<String, FormRadiobox> radioBoxes = new HashMap<String, FormRadiobox>();

	public RadioboxGroup(Alignment alignment) {
		if (Alignment.VERTICAL.equals(alignment)) {
			layoutPanel = new VerticalPanel();
		} else {
			layoutPanel = new HorizontalPanel();
		}
		
		this.initWidget(layoutPanel);
	}

	public void addRadioBoxEntry(String value, SafeHtml display) {
		final FormRadiobox radiobox = new FormRadiobox(value);
		HorizontalPanel radioboxEntry = new HorizontalPanel();
		radioboxEntry.add(radiobox);

		HTML radioboxLabel = new HTML(display);
		radioboxLabel.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                selectRadiobox(radiobox.getValue());
            }
		});
		radioboxEntry.add(radioboxLabel);
		radioboxEntry.setCellVerticalAlignment(radioboxLabel,
				HasVerticalAlignment.ALIGN_MIDDLE);

		layoutPanel.add(radioboxEntry);

		radioBoxes.put(radiobox.getValue(), radiobox);

		radiobox.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				selectRadiobox(radiobox.getValue());
			}
		});
	}

	protected void selectRadiobox(String value) {
		for (String rbKey : radioBoxes.keySet()) {
			if (rbKey.equalsIgnoreCase(value)) {
				radioBoxes.get(rbKey).check();
			} else {
				radioBoxes.get(rbKey).uncheck();
			}
		}
	}

	@Override
	public boolean isFixedWidth() {
		return false;
	}

	@Override
	public String getStringValue() {
		// find the checked radiobox and return its value (or null if none checked)
		String value = null;
		for (FormRadiobox radioBox : radioBoxes.values()) {
			if (radioBox.isChecked()) {
				value = radioBox.getValue();
			}
		}
		return value;
	}

}

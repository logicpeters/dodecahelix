package org.dmp.gwtpurdy.client.layout;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *   Simple replacement for GWT's awkward DeckPanel class
 *   
 *   Display's widget based on a key
 * 
 * @author dpeters
 *
 */
public class SimpleDeckPanel extends SimplePanel {
	
	private Map<String, Widget> cards = new HashMap<String, Widget>();
	
	public SimpleDeckPanel() {
	}
	
	public void addCard(String cardKey, Widget card) {
		cards.put(cardKey, card);
	}
	
	public void showCard(String cardKey) {
		this.setWidget(cards.get(cardKey));
	}

}

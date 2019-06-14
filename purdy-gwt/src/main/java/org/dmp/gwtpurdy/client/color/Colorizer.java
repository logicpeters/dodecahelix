package org.dmp.gwtpurdy.client.color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;

public class Colorizer {
    
    private final static Map<ColorScheme, String> colorStyles = new HashMap<ColorScheme, String>();
    static {
        colorStyles.put(ColorScheme.BASE, ColorResource.INSTANCE.colorCss().colorSchemeBase());
        colorStyles.put(ColorScheme.LIGHT_GREY, ColorResource.INSTANCE.colorCss().colorSchemeLightGrey());
        colorStyles.put(ColorScheme.LIGHT_BLUE, ColorResource.INSTANCE.colorCss().colorSchemeLightBlue());
        colorStyles.put(ColorScheme.LIGHT_RED, ColorResource.INSTANCE.colorCss().colorSchemeLightRed());
        colorStyles.put(ColorScheme.LIGHT_GREEN, ColorResource.INSTANCE.colorCss().colorSchemeLightGreen());
        colorStyles.put(ColorScheme.DARK_GREY, ColorResource.INSTANCE.colorCss().colorSchemeDarkGrey());
        colorStyles.put(ColorScheme.DARK_BLUE, ColorResource.INSTANCE.colorCss().colorSchemeDarkBlue());
        colorStyles.put(ColorScheme.DARK_RED, ColorResource.INSTANCE.colorCss().colorSchemeDarkRed());
        colorStyles.put(ColorScheme.DARK_GREEN, ColorResource.INSTANCE.colorCss().colorSchemeDarkGreen());
    };

    public static void colorWidget(Widget widget, ColorScheme scheme) {

        // remove any current color styles
        String currentStyles = widget.getStyleName();
        Collection<String> allColorStyles = colorStyles.values();
        for (String colorStyle : allColorStyles) {
            if (currentStyles.indexOf(colorStyle)>=0) {
                widget.removeStyleName(colorStyle);
            }
        }
        
        String style = colorStyles.get(scheme);
        widget.addStyleName(style);
    }
    

    public static void colorize(Colorizable colorizable, ColorScheme scheme) {
        colorWidget(colorizable.asWidget(), scheme);
    }
    
    public static String getStyleForScheme(ColorScheme scheme) {
        String style = colorStyles.get(scheme);
        return style;
    }
    
}

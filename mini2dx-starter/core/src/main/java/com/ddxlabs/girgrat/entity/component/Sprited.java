package com.ddxlabs.girgrat.entity.component;

import com.artemis.Component;
import org.mini2Dx.core.graphics.Sprite;

/**
 *  Entity backed with a sprite image
 */
public class Sprited extends Component {

    private Sprite sprite;

    public Sprited(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }
}

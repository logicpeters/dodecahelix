package com.ddxlabs.girgrat.entity.component;

import com.artemis.Component;
import org.mini2Dx.core.engine.geom.CollisionBox;

/**
 * Created on 2/13/2017.
 */
public class Box extends Component {

    private CollisionBox box;

    public Box() {
    }

    public Box(float width, float height) {
        box = new CollisionBox(0,0 , width, height);
    }

    public Box(float width, float height, float initialXpos, float initialYpos) {
        box = new CollisionBox(initialXpos, initialYpos, width, height);
    }

    public CollisionBox getBox() {
        return box;
    }

}

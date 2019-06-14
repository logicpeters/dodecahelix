package com.ddxlabs.girgrat.entity.component;

import com.artemis.Component;
import org.mini2Dx.core.engine.geom.CollisionBox;
import org.mini2Dx.core.engine.geom.CollisionCircle;

/**
 * Created on 4/19/2017.
 */
public class Sphere extends Component {

    private CollisionCircle sphere;

    public Sphere() {
    }

    public Sphere(float radius) {
        sphere = new CollisionCircle(0,0 , radius);
    }

    public Sphere(float radius, float initialXpos, float initialYpos) {
        sphere = new CollisionCircle(initialXpos, initialYpos, radius);
    }

    public CollisionCircle getCircle() {
        return sphere;
    }
}

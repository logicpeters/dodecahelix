package com.ddxlabs.girgrat.entity.component;

import com.artemis.Component;

/**
 * Created on 2/14/2017.
 */
public class Rotated extends Component {

    private float angle = 0;

    public Rotated(float angle) {
        this.angle = angle;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void rotateRight(float angle) {
        this.angle += angle;
    }

    public void rotateLeft(float angle) {
        this.angle -= angle;
    }
}

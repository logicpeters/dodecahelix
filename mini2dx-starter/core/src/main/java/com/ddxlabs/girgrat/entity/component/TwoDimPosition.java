package com.ddxlabs.girgrat.entity.component;

import com.artemis.Component;

/**
 * Created on 4/24/2017.
 */
public class TwoDimPosition extends Component {

    private float x;
    private float y;

    public TwoDimPosition() {
    }

    public TwoDimPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}

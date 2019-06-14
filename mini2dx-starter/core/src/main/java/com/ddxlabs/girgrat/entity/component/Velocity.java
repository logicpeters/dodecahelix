package com.ddxlabs.girgrat.entity.component;

import com.artemis.Component;

/**
 * Created on 2/14/2017.
 */
public class Velocity extends Component {

    private float velocityX;
    private float velocityY;

    public Velocity() {
        velocityX = 0f;
        velocityY = 0f;
    }

    public Velocity(float xVelocity, float yVelocity) {
        this.velocityX = xVelocity;
        this.velocityY = yVelocity;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }
}

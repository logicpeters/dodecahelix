package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.system.InterpolatingEntitySystem;
import com.badlogic.gdx.Gdx;
import com.ddxlabs.girgrat.entity.component.Box;
import com.ddxlabs.girgrat.entity.component.Sphere;
import com.ddxlabs.girgrat.entity.component.Velocity;
import org.mini2Dx.core.di.annotation.Singleton;

/**
 * Created on 2/14/2017.
 *
 * Bounces the entity when it reaches the window edge.
 */
@Singleton
public class BoundaryBounceSystem extends InterpolatingEntitySystem {

    private ComponentMapper<Sphere> sphereMapper;
    private ComponentMapper<Velocity> velocityMapper;

    private float boundaryX;
    private float boundaryY;

    public BoundaryBounceSystem() {
        super(Aspect.all(Sphere.class, Velocity.class));

        boundaryX = Gdx.graphics.getWidth();
        boundaryY = Gdx.graphics.getHeight();
    }

    @Override
    protected void update(int entityId, float delta) {
        Velocity v = velocityMapper.get(entityId);
        Sphere sphere = sphereMapper.get(entityId);

        float sphereX = sphere.getCircle().getX();
        float sphereY = sphere.getCircle().getY();
        float radius = sphere.getCircle().getRadius();

        float reverseVelocityX = - v.getVelocityX();
        float reverseVelocityY = - v.getVelocityY();

        if ( ((sphereX-radius)<0) && (v.getVelocityX()<0)) {
            // left bound
            v.setVelocityX(reverseVelocityX);
        }

        if ( ((sphereX+radius)>boundaryX) && (v.getVelocityX()>0)) {
            // right bound
            v.setVelocityX(reverseVelocityX);
        }

        if ( ((sphereY-radius)<0) && (v.getVelocityY()<0)) {
            // top bound
            v.setVelocityY(reverseVelocityY);
        }

        if ( ((sphereY+radius)>boundaryY) && (v.getVelocityY()>0)) {
            // bottom bound
            v.setVelocityY(reverseVelocityY);
        }
    }

    @Override
    protected void interpolate(int entityId, float alpha) {
    }
}

package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.system.InterpolatingEntitySystem;
import com.ddxlabs.girgrat.entity.component.Box;
import com.ddxlabs.girgrat.entity.component.Sphere;
import com.ddxlabs.girgrat.entity.component.Velocity;
import org.mini2Dx.core.di.annotation.Singleton;

/**
 * Created on 4/19/2017.
 */
@Singleton
@Wire
public class MoveSphereSystem extends InterpolatingEntitySystem {

    private ComponentMapper<Sphere> sphereMapper;
    private ComponentMapper<Velocity> velocityMapper;

    public MoveSphereSystem() {
        super(Aspect.all(Sphere.class, Velocity.class));
    }

    @Override
    protected void update(int entityId, float delta) {
        Sphere sphere = sphereMapper.get(entityId);
        Velocity v = velocityMapper.get(entityId);

        sphere.getCircle().preUpdate();

        float newX = sphere.getCircle().getX() + v.getVelocityX();
        float newY = sphere.getCircle().getY() + v.getVelocityY();

        sphere.getCircle().setX(newX);
        sphere.getCircle().setY(newY);
    }

    @Override
    protected void interpolate(int entityId, float alpha) {
        Sphere sphere = sphereMapper.get(entityId);
        sphere.getCircle().interpolate(null, alpha);
    }
}

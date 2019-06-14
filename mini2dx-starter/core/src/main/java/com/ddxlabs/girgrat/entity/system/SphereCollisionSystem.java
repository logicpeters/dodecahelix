package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.system.InterpolatingEntitySystem;
import com.badlogic.gdx.utils.IntArray;
import com.ddxlabs.girgrat.entity.component.Sphere;
import com.ddxlabs.girgrat.entity.component.Velocity;
import org.mini2Dx.core.di.annotation.Singleton;
import org.mini2Dx.core.engine.geom.CollisionCircle;

/**
 * Handles bounce-like collision
 */
@Singleton
public class SphereCollisionSystem extends InterpolatingEntitySystem {

    private ComponentMapper<Sphere> sphereMapper;
    private ComponentMapper<Velocity> velocityMapper;

    private float boundaryX;
    private float boundaryY;

    IntArray allSphereIds = new IntArray();

    public SphereCollisionSystem() {
        super(Aspect.all(Sphere.class, Velocity.class));
    }

    @Override
    protected void update(int entityId, float delta) {
        Sphere sphere = sphereMapper.get(entityId);
        Velocity v = velocityMapper.get(entityId);

        if (!allSphereIds.contains(entityId)) {
            allSphereIds.add(entityId);
            System.out.println("entity added.  current count = " + allSphereIds.size);
        }

        for (int i = 0; i< allSphereIds.size; i++) {
            int key = allSphereIds.get(i);
            if (key != entityId) {
                CollisionCircle otherCircle = sphereMapper.get(key).getCircle();

                // TODO - this does not work for a rotated box
                if (otherCircle.intersects(sphere.getCircle())) {
                    System.out.println("Collision!");

                    float circleX = sphere.getCircle().getX();
                    float circleY = sphere.getCircle().getY();
                    float velocityX = v.getVelocityX();
                    float velocityY = v.getVelocityY();

                    velocityX = calculateCollision(otherCircle.getX(), velocityX, circleX);
                    v.setVelocityX(velocityX);

                    velocityY = calculateCollision(otherCircle.getY(), velocityY, circleY);
                    v.setVelocityY(velocityY);
                }
            }
        }
    }

    private float calculateCollision(float colliderPosition, float sourceVelocity, float sourcePosition) {
        if (colliderPosition > sourcePosition) {
            return -Math.abs(sourceVelocity);
        }
        if (colliderPosition < sourcePosition) {
            return Math.abs(sourceVelocity);
        }
        return sourceVelocity;
    }

    @Override
    protected void interpolate(int entityId, float alpha) {
        Sphere sphere = sphereMapper.get(entityId);
        sphere.getCircle().interpolate(null, alpha);
    }

}

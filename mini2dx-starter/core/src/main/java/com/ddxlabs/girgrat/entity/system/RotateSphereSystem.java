package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.system.InterpolatingEntitySystem;
import com.ddxlabs.girgrat.entity.component.Rotated;
import com.ddxlabs.girgrat.entity.component.Sphere;
import org.mini2Dx.core.di.annotation.Singleton;

/**
 * Created on 4/19/2017.
 */
@Singleton
@Wire
public class RotateSphereSystem extends InterpolatingEntitySystem {

    private ComponentMapper<Sphere> sphereMapper;
    private ComponentMapper<Rotated> rotationMapper;

    public RotateSphereSystem() {
        super(Aspect.all(Sphere.class, Rotated.class));
    }

    @Override
    protected void update(int entityId, float delta) {
        Rotated rotated = rotationMapper.get(entityId);
        Sphere sphere = sphereMapper.get(entityId);
        sphere.getCircle().preUpdate();
        rotated.rotateRight(2.5f);
    }

    @Override
    protected void interpolate(int entityId, float alpha) {
        Sphere sphere = sphereMapper.get(entityId);
        sphere.getCircle().interpolate(null, alpha);
    }

}

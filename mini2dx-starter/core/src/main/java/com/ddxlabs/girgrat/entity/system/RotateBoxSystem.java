package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.system.InterpolatingEntitySystem;
import com.ddxlabs.girgrat.entity.component.Box;
import com.ddxlabs.girgrat.entity.component.Rotated;
import org.mini2Dx.core.di.annotation.Singleton;

/**
 * Created on 2/14/2017.
 */
@Singleton
@Wire
public class RotateBoxSystem extends InterpolatingEntitySystem {

    private ComponentMapper<Box> boxMapper;
    private ComponentMapper<Rotated> rotationMapper;

    public RotateBoxSystem() {
        super(Aspect.all(Box.class, Rotated.class));
    }

    @Override
    protected void update(int entityId, float delta) {
        Rotated rotated = rotationMapper.get(entityId);
        Box box = boxMapper.get(entityId);

        box.getBox().preUpdate();

        float angle = rotated.getAngle();
        angle += 2.5f;
        rotated.setAngle(angle);
        box.getBox().setRotation(angle);
    }

    @Override
    protected void interpolate(int entityId, float alpha) {
        Box box = boxMapper.get(entityId);
        box.getBox().interpolate(null, alpha);
    }
}

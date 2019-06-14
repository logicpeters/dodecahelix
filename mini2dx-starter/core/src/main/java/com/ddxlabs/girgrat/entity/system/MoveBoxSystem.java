package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.system.InterpolatingEntitySystem;
import com.ddxlabs.girgrat.entity.component.Box;
import com.ddxlabs.girgrat.entity.component.Velocity;
import org.mini2Dx.core.di.annotation.Singleton;

/**
 * Created on 2/13/2017.
 */
@Singleton
@Wire
public class MoveBoxSystem extends InterpolatingEntitySystem {

    private ComponentMapper<Box> boxMapper;
    private ComponentMapper<Velocity> velocityMapper;

    public MoveBoxSystem() {
        super(Aspect.all(Box.class, Velocity.class));
    }

    @Override
    protected void update(int entityId, float delta) {
        Box box = boxMapper.get(entityId);
        Velocity v = velocityMapper.get(entityId);

        box.getBox().preUpdate();

        float newX = box.getBox().getX() + v.getVelocityX();
        float newY = box.getBox().getY() + v.getVelocityY();

        box.getBox().setX(newX);
        box.getBox().setY(newY);
    }

    @Override
    protected void interpolate(int entityId, float alpha) {
        Box box = boxMapper.get(entityId);
        box.getBox().interpolate(null, alpha);
    }
}

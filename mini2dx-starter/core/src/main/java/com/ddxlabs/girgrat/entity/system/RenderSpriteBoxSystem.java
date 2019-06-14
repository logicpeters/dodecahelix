package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.system.RenderingEntitySystem;
import com.ddxlabs.girgrat.entity.component.Box;
import com.ddxlabs.girgrat.entity.component.Sprited;
import org.mini2Dx.core.di.annotation.Singleton;
import org.mini2Dx.core.graphics.Graphics;

/**
 * Created on 2/14/2017.
 */
@Singleton
@Wire
public class RenderSpriteBoxSystem extends RenderingEntitySystem {

    private ComponentMapper<Box> boxMapper;
    private ComponentMapper<Sprited> spriteMapper;

    public RenderSpriteBoxSystem() {
        super(Aspect.all(Box.class, Sprited.class));
    }

    @Override
    protected void render(int entityId, Graphics g) {
        Box box = boxMapper.get(entityId);
        Sprited sprited = spriteMapper.get(entityId);

        float rotationPointX = box.getBox().getRenderX() + box.getBox().getRenderWidth()/2;
        float rotationPointY = box.getBox().getRenderY() + box.getBox().getRenderHeight()/2;
        g.rotate(box.getBox().getRotation(), rotationPointX, rotationPointY);
        g.drawSprite(sprited.getSprite(), box.getBox().getRenderX(), box.getBox().getRenderY());
    }
}

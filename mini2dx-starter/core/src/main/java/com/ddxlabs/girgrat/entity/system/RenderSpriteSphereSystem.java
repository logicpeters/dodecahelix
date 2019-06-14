package com.ddxlabs.girgrat.entity.system;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.system.RenderingEntitySystem;
import com.ddxlabs.girgrat.entity.component.Rotated;
import com.ddxlabs.girgrat.entity.component.Sphere;
import com.ddxlabs.girgrat.entity.component.Sprited;
import org.mini2Dx.core.di.annotation.Singleton;
import org.mini2Dx.core.graphics.Graphics;

/**
 * Created on 4/19/2017.
 */
@Singleton
@Wire
public class RenderSpriteSphereSystem extends RenderingEntitySystem {

    private ComponentMapper<Sphere> sphereMapper;
    private ComponentMapper<Sprited> spriteMapper;
    private ComponentMapper<Rotated> rotationMapper;

    public RenderSpriteSphereSystem() {
        super(Aspect.all(Sphere.class, Sprited.class, Rotated.class));
    }

    @Override
    protected void render(int entityId, Graphics g) {
        Sphere sphere = sphereMapper.get(entityId);
        Sprited sprited = spriteMapper.get(entityId);
        Rotated rotated = rotationMapper.get(entityId);

        float radius = sphere.getCircle().getRadius();

        // scale the sprite
        float spriteWidth = sprited.getSprite().getWidth();
        float spriteHeight = sprited.getSprite().getHeight();
        float sphereWidth = radius * 2;
        float sphereHeight = radius * 2;

        // rotate the sprite
        float rotationPointX = sphere.getCircle().getX();
        float rotationPointY = sphere.getCircle().getY();
        g.rotate(rotated.getAngle(), rotationPointX, rotationPointY);

        // draw sprite from the top left corner?
        g.drawSprite(sprited.getSprite(),
            (sphere.getCircle().getX()-radius),
            (sphere.getCircle().getY()-radius));
    }
}

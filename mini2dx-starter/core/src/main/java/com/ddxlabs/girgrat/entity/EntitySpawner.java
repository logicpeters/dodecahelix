package com.ddxlabs.girgrat.entity;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.Gdx;
import com.ddxlabs.girgrat.assets.SpriteAssets;
import com.ddxlabs.girgrat.assets.SpriteId;
import com.ddxlabs.girgrat.entity.component.*;
import org.mini2Dx.core.di.annotation.Autowired;
import org.mini2Dx.core.di.annotation.Singleton;
import org.mini2Dx.core.graphics.Sprite;

import java.util.Random;

/**
 * Created on 2/14/2017.
 */
@Singleton
public class EntitySpawner {

    private int livingEntities;
    private int bumpCounter;
    private int spawnFrequency;
    private Random random;

    @Autowired
    private SpriteAssets spriteAssets;

    public EntitySpawner() {
        bumpCounter = 0;

        // spawn new every 3 seconds
        spawnFrequency = 60 * 3;

        random = new Random(System.currentTimeMillis());
    }

    public void spawnBump(World world) {
        bumpCounter++;
        if (bumpCounter>spawnFrequency) {
            spawnEntity(world);
            bumpCounter = 0;
        }
    }

    public void spawnEntity(World world) {
        if (livingEntities<10) {
            Entity entity = world.createEntity();

            float velocityX = (random.nextFloat() * 10f) - 5f;
            float velocityY = (random.nextFloat() * 10f) - 5f;

            Sprite sprite = null;
            int spriteNum = new Random(System.currentTimeMillis()).nextInt(5);
            switch (spriteNum) {
                case 0: sprite = spriteAssets.getSprite(SpriteId.ALIEN_BLUE); break;
                case 1: sprite = spriteAssets.getSprite(SpriteId.ALIEN_GREEN); break;
                case 2: sprite = spriteAssets.getSprite(SpriteId.ALIEN_YELLOW); break;
                case 3: sprite = spriteAssets.getSprite(SpriteId.ALIEN_BEIGE); break;
                default: sprite = spriteAssets.getSprite(SpriteId.ALIEN_RED);
            }
            float radius = sprite.getWidth() / 2;

            entity.edit()
                .add(new Sphere(radius, Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2))
                .add(new Velocity(velocityX, velocityY))
                .add(new Sprited(sprite))
                .add(new Rotated(0));

            livingEntities++;
        }
    }

}

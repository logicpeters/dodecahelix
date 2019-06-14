package com.ddxlabs.girgrat.entity;

import com.artemis.MdxWorld;
import com.ddxlabs.girgrat.entity.world.MdxWorldBuilder;
import org.mini2Dx.core.di.annotation.Autowired;
import org.mini2Dx.core.di.annotation.Singleton;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;

/**
 * Created on 2/13/2017.
 */
@Singleton
public class InGameEntityScreen extends BasicGameScreen {

    public static int ID = 3;

    private MdxWorld world;

    @Autowired
    private EntitySpawner spawner;

    @Autowired
    private MdxWorldBuilder worldBuilder;

    @Override
    public void initialise(GameContainer gc) {

        world = worldBuilder.build();
    }

    @Override
    public void update(GameContainer gc, ScreenManager<? extends GameScreen> screenManager, float delta) {
        world.setDelta(delta);
        world.process();

        spawner.spawnBump(world);
    }

    @Override
    public void interpolate(GameContainer gc, float alpha) {
        world.setAlpha(alpha);
        world.interpolate();
    }

    @Override
    public void render(GameContainer gc, Graphics g) {
        world.render(g);
    }

    @Override
    public int getId() {
        return ID;
    }
}

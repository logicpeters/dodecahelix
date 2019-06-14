package com.ddxlabs.girgrat;

import com.ddxlabs.girgrat.assets.GlobalAssetManager;
import com.ddxlabs.girgrat.entity.InGameEntityScreen;

import org.mini2Dx.core.di.annotation.Autowired;
import org.mini2Dx.core.di.annotation.Singleton;
import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.BasicGameScreen;
import org.mini2Dx.core.screen.ScreenManager;
import org.mini2Dx.core.screen.transition.FadeInTransition;
import org.mini2Dx.core.screen.transition.FadeOutTransition;

/**
 * Created on 2/13/2017.
 *
 * Loading screen to wait while all assets are loaded.
 */
@Singleton
public class LoadingScreen extends BasicGameScreen {

    public static int ID = 1;

    @Autowired
    GlobalAssetManager assetManager;

    private float minLoadingTime = 4f;
    private boolean metMinimumTime = false;

    public void initialise(GameContainer gc) {
        // queue assets for AssetManager
        assetManager.queueAssets();
    }

    public void update(GameContainer gc, ScreenManager screenManager, float delta) {
        if (minLoadingTime > 0f) {
            minLoadingTime -= delta;
            if (minLoadingTime < 0f) {
                metMinimumTime = true;
            }
        }

        // wait for asset manager to finish loading assets
        if (metMinimumTime && assetManager.update()) {
            screenManager.enterGameScreen(InGameEntityScreen.ID,
                new FadeOutTransition(),
                new FadeInTransition());
        }
    }

    public void interpolate(GameContainer gc, float alpha) {
    }

    public void render(GameContainer gc, Graphics g) {
        g.drawString("Loading...", 32, 32);
    }

    public int getId() {
        return ID;
    }
}

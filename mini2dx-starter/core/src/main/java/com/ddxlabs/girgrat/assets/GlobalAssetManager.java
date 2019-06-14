package com.ddxlabs.girgrat.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.di.annotation.Autowired;
import org.mini2Dx.core.di.annotation.Singleton;

/**
 * Created on 2/15/2017.
 *
 * Libgdx asset manager as a singleton bean for dependency injection.
 */
@Singleton
public class GlobalAssetManager extends AssetManager {

    /**
     *   Queues the assets to be loaded (during the loading screen)
     */
    public void queueAssets() {

        // queue sprites
        for (SpriteId spriteId : SpriteId.values()) {
            this.load(spriteId.getFilePath(), Texture.class);
        }
    }
}

package com.ddxlabs.girgrat.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.di.annotation.Autowired;
import org.mini2Dx.core.di.annotation.PostInject;
import org.mini2Dx.core.di.annotation.Singleton;
import org.mini2Dx.core.graphics.Sprite;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2/13/2017.
 */
@Singleton
public class SpriteAssets {

    @Autowired
    private GlobalAssetManager assetManager;

    Map<SpriteId, Sprite> spriteMap;

    public SpriteAssets() {
        spriteMap = new HashMap<SpriteId, Sprite>();
    }

    @PostInject
    public void initAssets() {
    }

    public Sprite getSprite(SpriteId spriteId) {
        Sprite sprite = spriteMap.get(spriteId);
        if (sprite==null) {
            sprite = new Sprite(assetManager.get(spriteId.getFilePath(), Texture.class));
            spriteMap.put(spriteId, sprite);
        }

        return sprite;
    }
}

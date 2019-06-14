package com.ddxlabs.girgrat;

import com.ddxlabs.girgrat.entity.InGameEntityScreen;
import org.mini2Dx.core.Mdx;
import org.mini2Dx.core.game.ScreenBasedGame;

public class Girgrat extends ScreenBasedGame {

    public static final String GAME_IDENTIFIER = "com.ddxlabs.girgrat";

    @Override
    public void initialise() {
        try {
            Mdx.di.scan("com.ddxlabs.girgrat");

            InGameEntityScreen entityScreen = Mdx.di.getBean(InGameEntityScreen.class);
            LoadingScreen loadingScreen = Mdx.di.getBean(LoadingScreen.class);

            this.addScreen(loadingScreen);
            this.addScreen(entityScreen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getInitialScreenId() {
        return LoadingScreen.ID;
    }

}

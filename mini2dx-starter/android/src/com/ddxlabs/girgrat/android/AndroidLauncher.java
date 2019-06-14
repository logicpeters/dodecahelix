package com.ddxlabs.girgrat.android;

import org.mini2Dx.android.AndroidMini2DxConfig;

import com.badlogic.gdx.backends.android.AndroidMini2DxGame;

import android.os.Bundle;

import com.ddxlabs.girgrat.Girgrat;

public class AndroidLauncher extends AndroidMini2DxGame {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidMini2DxConfig config = new AndroidMini2DxConfig(Girgrat.GAME_IDENTIFIER);
        initialize(new Girgrat(), config);
    }
}

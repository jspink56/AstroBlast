package com.astroblast.screen.menu;
// Created by John on 25-Sep-2017

import com.astroblast.AstroBlastGame;
import com.astroblast.config.GameConfig;
import com.astroblast.util.GdxUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class MenuScreenBase extends ScreenAdapter {

	protected final AstroBlastGame game;
	protected final AssetManager assetManager;

	private Viewport viewport;
	private Stage stage;


	public MenuScreenBase(AstroBlastGame game) {
		this.game = game;
		assetManager = game.getAssetManager();
	}

	@Override
	public void show() {
		viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
		stage = new Stage(viewport, game.getBatch());
		// uncomment for stage debugging
		//stage.setDebugAll(true);

		Gdx.input.setInputProcessor(stage);

		stage.addActor(createUI());
	}

	protected abstract Actor createUI();

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render(float delta) {
		GdxUtils.clearScreen();

		stage.act();
		stage.draw();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		stage.dispose();
	}
}

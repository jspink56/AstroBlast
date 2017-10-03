package com.astroblast.screen.game;
// Created by John on 25-Sep-2017

import com.astroblast.AstroBlastGame;
import com.astroblast.screen.menu.MenuScreen;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public class GameScreen implements Screen {

	private final AstroBlastGame game;
	private final AssetManager assetManager;

	private GameController controller;
	private GameRenderer renderer;

	public GameScreen(AstroBlastGame game) {
		this.game = game;
		assetManager = game.getAssetManager();
	}

	@Override
	public void show() {
		controller = new GameController(game);
		renderer = new GameRenderer(game.getBatch(), assetManager, controller);
	}

	@Override
	public void render(float delta) {
		controller.update(delta);
		renderer.render(delta);

		if(controller.isGameOver()) {
			game.setScreen(new MenuScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		renderer.resize(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		renderer.dispose();
	}
}

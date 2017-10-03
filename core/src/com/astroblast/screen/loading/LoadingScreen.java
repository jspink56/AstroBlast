package com.astroblast.screen.loading;
// Created by John on 25-Sep-2017

import com.astroblast.AstroBlastGame;
import com.astroblast.assets.AssetDescriptors;
import com.astroblast.assets.AssetPaths;
import com.astroblast.config.GameConfig;
import com.astroblast.screen.menu.MenuScreen;
import com.astroblast.util.GdxUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LoadingScreen extends ScreenAdapter {

	// == constants ==
	private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f;
	private static final float PROGRESS_BAR_HEIGHT = 20f;
	private static final float LOAD_WIDTH = GameConfig.HUD_WIDTH * 0.7f;
	private static final float LOAD_HEIGHT = 50f;
	private static final float LOAD_X = (GameConfig.HUD_WIDTH - LOAD_WIDTH) / 2f;
	private static final float LOAD_Y = ((GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f) + 50f;

	// == attributes ==
	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer renderer;

	private float progress;
	private float waitTime = 0.75f;

	private final AstroBlastGame game;
	private final AssetManager assetManager;
	private boolean changeScreen;

	private SpriteBatch batch;
	private Texture loadingTexture;


	// == constructor ==
	public LoadingScreen(AstroBlastGame game) {
		this.game = game;
		assetManager = game.getAssetManager();
		batch = new SpriteBatch();
		loadingTexture = new Texture(Gdx.files.internal(AssetPaths.LOADING));
	}

	// == public methods
	@Override
	public void show() {

		camera = new OrthographicCamera();
		viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera);
		renderer = new ShapeRenderer();

		assetManager.load(AssetDescriptors.FONT);
		assetManager.load(AssetDescriptors.GAME_PLAY);
		assetManager.load(AssetDescriptors.UI_SKIN);
		assetManager.load(AssetDescriptors.HIT_SOUND);
		assetManager.load(AssetDescriptors.ASTEROIDS);
		assetManager.load(AssetDescriptors.STARFIELD);
	}

	@Override
	public void render(float delta) {

		update(delta);

		Color oldColor = renderer.getColor().cpy();

		GdxUtils.clearScreen();
		viewport.apply();
		renderer.setProjectionMatrix(camera.combined);
		renderer.setColor(Color.RED);
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		draw();
		renderer.end();
		renderer.setColor(oldColor);

		batch.begin();
		batch.draw(loadingTexture,
				LOAD_X, LOAD_Y,
				LOAD_WIDTH, LOAD_HEIGHT);
		batch.end();

		if (changeScreen) {
			game.setScreen(new MenuScreen(game));
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void hide() {
		// NOTE: screens don't dispose automatically
		dispose();
	}

	@Override
	public void dispose() {
		renderer.dispose();
		batch.dispose();
		loadingTexture.dispose();
	}

	// == private methods
	private void update(float delta) {

		//waitMillis(400);

		// progress is between 0 and 1
		progress = assetManager.getProgress();

		// update returns true when all assets are loaded
		if (assetManager.update()) {
			waitTime -= delta;

			if (waitTime <= 0) {
				changeScreen = true;
			}
		}
	}

	private void draw() {
		float progressBarX = (GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH) / 2f;
		float progressBarY = (GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT) / 2f;

		renderer.rect(progressBarX, progressBarY,
				progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT);
	}

	private static void waitMillis(long millis) {
		try {
			Thread.sleep(millis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

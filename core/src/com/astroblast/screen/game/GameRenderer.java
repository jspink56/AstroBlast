package com.astroblast.screen.game;
// Created by John on 25-Sep-2017

import com.astroblast.assets.AssetDescriptors;
import com.astroblast.assets.RegionNames;
import com.astroblast.config.GameConfig;
import com.astroblast.entity.Asteroid;
import com.astroblast.entity.Player;
import com.astroblast.util.GdxUtils;
import com.astroblast.util.ViewportUtils;
import com.astroblast.util.debug.DebugCameraController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameRenderer implements Disposable {

	private static final Logger log = new Logger(GameRenderer.class.getName(), Logger.DEBUG);

	private OrthographicCamera camera;
	private Viewport viewport;
	private ShapeRenderer renderer;

	private OrthographicCamera hudCamera;
	private Viewport hudViewport;

	private BitmapFont font;
	private final GlyphLayout layout = new GlyphLayout();
	private DebugCameraController debugCameraController;
	private final GameController controller;
	private final AssetManager assetManager;
	private final SpriteBatch batch;

	private TextureRegion playerRegion;

	private Array<Sprite> backgrounds;

	private Animation asteroidAnimation;

	// == constructor ==
	public GameRenderer(SpriteBatch batch, AssetManager assetManager, GameController controller) {
		this.assetManager = assetManager;
		this.controller = controller;
		this.batch = batch;
		init();
	}

	// == initialise class ==
	private void init() {

		camera = new OrthographicCamera();
		viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
		renderer = new ShapeRenderer();
		hudCamera = new OrthographicCamera();
		hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera);
		font = assetManager.get(AssetDescriptors.FONT);

		// create debug camera controller
		debugCameraController = new DebugCameraController();
		debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

		TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
		TextureAtlas asteroidAtlas = assetManager.get(AssetDescriptors.ASTEROIDS);

		playerRegion = gamePlayAtlas.findRegion(RegionNames.SHIP);

		// animated asteroids
		asteroidAnimation = new Animation(0.1f,
				asteroidAtlas.findRegions(RegionNames.ASTEROIDS),
				Animation.PlayMode.LOOP);

		backgrounds = controller.getBackgrounds();

	}

	// == public methods ==
	public void render(float delta) {
		//batch.totalRenderCalls = 0;

		// not wrapping inside alive because we want to control camera
		// even when the game is over
		debugCameraController.handleDebugInput(delta);
		debugCameraController.applyTo(camera);

		if (Gdx.input.isTouched() && !controller.isGameOver()) {
			Vector2 screenTouch = new Vector2(Gdx.input.getX(), Gdx.input.getY());
			Vector2 worldTouch = viewport.unproject(new Vector2(screenTouch));

//			System.out.println("screenTouch= " + screenTouch);
//			System.out.println("worldTouch= " + worldTouch);

			Player player = controller.getPlayer();
			worldTouch.x = MathUtils.clamp(worldTouch.x, 0f, GameConfig.WORLD_WIDTH - player.getWidth());
			player.setX(worldTouch.x);
		}

		GdxUtils.clearScreen();

		renderGamePlay();

		// render ui/hud
		renderUI();

		//renderDebug();

		//System.out.println("totalRenderCalls= " + batch.totalRenderCalls);

	}

	private void renderGamePlay() {
		viewport.apply();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		// draw background
		for (Sprite bg : backgrounds) {
			batch.draw(bg, bg.getX(), bg.getY(),
					GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);
		}

		// draw player
		Player player = controller.getPlayer();
		batch.draw(playerRegion,
				player.getX(), player.getY(),
				player.getWidth(), player.getHeight()
		);

		// draw asteroids
		Array<Asteroid> asteroids = controller.getAsteroids();
		TextureRegion asteroidRegion = (TextureRegion) asteroidAnimation.getKeyFrame(controller.getAnimationTime());

		for (Asteroid asteroid : asteroids) {
			batch.draw(asteroidRegion,
					asteroid.getX(), asteroid.getY(),
					asteroid.getWidth(), asteroid.getHeight()
			);
		}

		batch.end();

	}

	@Override
	public void dispose() {
		renderer.dispose();
	}

	public void resize(int width, int height) {
		viewport.update(width, height, true);
		hudViewport.update(width, height, true);
		ViewportUtils.debugPixelPerUnit(viewport);
	}

	// == private methods ==
	private void renderUI() {
		hudViewport.apply();
		batch.setProjectionMatrix(hudCamera.combined);
		batch.begin();

		String livesText = "LIVES: " + controller.getLives();
		layout.setText(font, livesText);
		font.draw(batch, livesText, 20, GameConfig.HUD_HEIGHT - layout.height);

		String scoreText = "SCORE: " + controller.getDisplayScore();
		layout.setText(font, scoreText);
		font.draw(batch, scoreText, GameConfig.HUD_WIDTH - layout.width - 20, GameConfig.HUD_HEIGHT - layout.height);

		batch.end();

	}

	private void renderDebug() {
		viewport.apply();
		renderer.setProjectionMatrix(camera.combined);
		renderer.begin(ShapeRenderer.ShapeType.Line);

		drawDebug();

		renderer.end();
		ViewportUtils.drawGrid(viewport, renderer);
	}

	private void drawDebug() {

		Player player = controller.getPlayer();
		player.drawDebug(renderer);

		Array<Asteroid> asteroids = controller.getAsteroids();

		// draw asteroids
		for (Asteroid asteroid : asteroids) {
			asteroid.drawDebug(renderer);
		}
	}
}

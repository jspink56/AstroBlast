package com.astroblast.screen.game;
// Created by John on 25-Sep-2017

import com.astroblast.AstroBlastGame;
import com.astroblast.assets.AssetDescriptors;
import com.astroblast.common.GameManager;
import com.astroblast.config.DifficultyLevel;
import com.astroblast.config.GameConfig;
import com.astroblast.entity.Asteroid;
import com.astroblast.entity.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;

public class GameController {

	// == constants ==
	private static final Logger log = new Logger(GameController.class.getName(), Logger.DEBUG);
	private static final int NUM_BKGDS = 4;

	// == attributes ==
	private Player player;
	private Array<Asteroid> asteroids = new Array<Asteroid>();
	private Array<Sprite> backgrounds = new Array<Sprite>();

	private float asteroidTimer;
	private float scoreTimer;
	private float animationTime;

	private int lives = GameConfig.LIVES_START;
	private int displayScore;
	private Pool<Asteroid> asteroidPool;
	private Sound hit;

	private final float startPlayerX = (GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE) / 2f;
	private final float startPlayerY = 1f - GameConfig.PLAYER_SIZE / 2f;
	private final AstroBlastGame game;
	private final AssetManager assetManager;
	private TextureAtlas starfieldAtlas;

	// == constructor ==

	public GameController(AstroBlastGame game) {
		this.game = game;
		assetManager = game.getAssetManager();
		init();
	}

	// == init ==
	private void init() {
		// create player
		player = new Player();

		// position player
		player.setPosition(startPlayerX, startPlayerY);

		// create asteroid pool
		asteroidPool = Pools.get(Asteroid.class, 40);

		// create seamless starfield background
		starfieldAtlas = assetManager.get(AssetDescriptors.STARFIELD);
		backgrounds = starfieldAtlas.createSprites("starfield");
		int i = 0;
		for (Sprite bg : backgrounds) {
			bg.setPosition(0f, i * GameConfig.WORLD_HEIGHT);
			i++;
		}

		hit = assetManager.get(AssetDescriptors.HIT_SOUND);

	}

	// == public methods ==
	public void update(float delta) {

		animationTime += delta;

		if (isGameOver()) {
			return;
		}

		updateBackground();
		updatePlayer();
		updateAsteroids(delta);
		updateScoreNew(delta);

		if (isPlayerCollidingWithAsteroid()) {
			log.debug("asteroid collision detected!");
			//lives--;

			if (isGameOver()) {
				log.debug("Game Over!!!");
				GameManager.INSTANCE.updateHighScore(displayScore);
				animationTime = 0f;
				backgrounds.clear();
			} else {
				restartGame();
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public int getLives() {
		return lives;
	}

	public int getDisplayScore() {
		return displayScore;
	}

	public Array<Asteroid> getAsteroids() {
		return asteroids;
	}

	public boolean isGameOver() {
		return lives <= 0;
	}

	public float getAnimationTime() {
		return animationTime;
	}

	public Array<Sprite> getBackgrounds() {
		return backgrounds;
	}

	// == private methods ==
	private boolean isPlayerCollidingWithAsteroid() {
		for (Asteroid asteroid : asteroids) {
			if (asteroid.isNotHit() && asteroid.isPlayerColliding(player)) {
				hit.play();
				return true;
			}
		}

		return false;
	}

	private void updateBackground() {

		for (Sprite bg : backgrounds) {
			float yPos = bg.getY() - GameConfig.BACKGROUND_SCROLL_SPEED;

			// check for wrap round
			if (yPos < -((NUM_BKGDS - 1) * GameConfig.WORLD_HEIGHT)) {
				yPos = GameConfig.WORLD_HEIGHT;
			}

			bg.setPosition(bg.getX(), yPos);
		}
	}

	private void updatePlayer() {
		float xSpeed = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			xSpeed = GameConfig.MAX_PLAYER_X_SPEED;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
		}

		player.setX(player.getX() + xSpeed);

		blockPlayerFromLeavingTheWorld();
	}

	private void blockPlayerFromLeavingTheWorld() {
		float playerX = MathUtils.clamp(
				player.getX(),
				0,
				GameConfig.WORLD_WIDTH - player.getWidth()
		);

		player.setPosition(playerX, player.getY());
	}

	private void updateAsteroids(float delta) {
		for (Asteroid asteroid : asteroids) {
			asteroid.update();
		}

		createNewAsteroid(delta);
		removePassedAsteroids();
	}

	private void createNewAsteroid(float delta) {
		asteroidTimer += delta;

		if (asteroids.size > 5) {
			return;
		}

		float timeAdjust = MathUtils.random(-0.55f, 0.55f);

		if (asteroidTimer >= GameConfig.ASTEROID_SPAWN_TIME + timeAdjust) {
			float min = 0;
			float max = GameConfig.WORLD_WIDTH - GameConfig.ASTEROID_SIZE;
			float asteroidX = MathUtils.random(min, max);
			float asteroidY = GameConfig.WORLD_HEIGHT;

			DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
			Asteroid asteroid = asteroidPool.obtain();
			asteroid.setYSpeed(difficultyLevel.getAsteroidSpeed());
			asteroid.setPosition(asteroidX, asteroidY);

			asteroids.add(asteroid);
			asteroidTimer = 0f;
		}
	}

	private void removePassedAsteroids() {
		if (asteroids.size > 0) {
			Asteroid first = asteroids.first();

			float minAsteroidY = -GameConfig.ASTEROID_SIZE;
			if (first.getY() < minAsteroidY) {
				asteroids.removeValue(first, true);
				asteroidPool.free(first);
			}
		}
	}

	private void updateScoreNew(float delta) {
		scoreTimer += delta;

		if (scoreTimer >= GameConfig.SCORE_MAX_TIME) {
			displayScore += 5;
			scoreTimer = 0f;
		}
	}

	private void restartGame() {
		asteroidPool.freeAll(asteroids);
		asteroids.clear();
		player.setPosition(startPlayerX, startPlayerY);
	}
}

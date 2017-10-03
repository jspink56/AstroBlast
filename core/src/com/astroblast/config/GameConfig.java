package com.astroblast.config;
// Created by John on 25-Sep-2017

public class GameConfig {

	public static final float WIDTH = 480f;            	// pixels
	public static final float HEIGHT = 800f;        	// pixels

	public static final float HUD_WIDTH = WIDTH;        // world units
	public static final float HUD_HEIGHT = HEIGHT;    	// world units

	public static final float WORLD_WIDTH = 6.0f;    	// world units
	public static final float WORLD_HEIGHT = 10.0f;    // world units

	public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;	// world units
	public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f;	// world units

	public static final float MAX_PLAYER_X_SPEED = 0.1f;        	// max player speed
	public static final float ASTEROID_SPAWN_TIME = 0.75f;        	// spawn asteroid every interval
	public static final float SCORE_MAX_TIME = 1.25f;            	// add score every interval

	public static final int LIVES_START = 3;                    	// lives at start

	public static final float EASY_ASTEROID_SPEED = 0.02f;
	public static final float MEDIUM_ASTEROID_SPEED = 0.15f;
	public static final float HARD_ASTEROID_SPEED = 0.18f;

	public static final float PLAYER_BOUNDS_RADIUS = 0.5f;                        // world units
	public static final float PLAYER_SIZE = PLAYER_BOUNDS_RADIUS * 2.0f;

	public static final float ASTEROID_BOUNDS_RADIUS = 0.3f;                    // world units
	public static final float ASTEROID_SIZE = ASTEROID_BOUNDS_RADIUS * 2.0f;

	public static final float BACKGROUND_SCROLL_SPEED =0.01f;					// world units

	private GameConfig() {
	}
}

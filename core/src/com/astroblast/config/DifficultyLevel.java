package com.astroblast.config;
// Created by John on 25-Sep-2017

public enum DifficultyLevel {
	EASY(GameConfig.EASY_ASTEROID_SPEED),
	MEDIUM(GameConfig.MEDIUM_ASTEROID_SPEED),
	HARD(GameConfig.HARD_ASTEROID_SPEED);

	private final float asteroidSpeed;

	DifficultyLevel(float obstacleSpeed) {
		this.asteroidSpeed = obstacleSpeed;
	}

	public float getAsteroidSpeed() {
		return asteroidSpeed;
	}

	public boolean isEasy() {
		return this == EASY;
	}

	public boolean isMedium() {
		return this == MEDIUM;
	}

	public boolean isHard() {
		return this == HARD;
	}
}

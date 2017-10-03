package com.astroblast.entity;
// Created by John on 25-Sep-2017

import com.astroblast.config.GameConfig;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;

public class Asteroid extends GameObjectBase implements Pool.Poolable {

	private float ySpeed = GameConfig.MEDIUM_ASTEROID_SPEED;
	private boolean hit = false;

	public Asteroid() {
		super(GameConfig.ASTEROID_BOUNDS_RADIUS);
		setSize(GameConfig.ASTEROID_SIZE, GameConfig.ASTEROID_SIZE);
	}

	public Asteroid(float x, float y) {
		super(GameConfig.ASTEROID_BOUNDS_RADIUS);
		setPosition(x, y);
	}

	public void update() {
		setY(getY() - ySpeed);
	}

	public boolean isPlayerColliding(Player player) {
		Circle playerBounds = player.getBounds();

		// check if playerBounds overlaps obstacle bounds
		hit = Intersector.overlaps(playerBounds, getBounds());
		return hit;

// == original code from lecture ==
//		boolean overlaps = Intersector.overlaps(playerBounds, getBounds());
//		hit = overlaps;
//		return overlaps;

	}

	public boolean isNotHit() {
		return !hit;
	}

	public void setYSpeed(float ySpeed) {
		this.ySpeed = ySpeed;
	}

	@Override
	public void reset() {
		hit = false;
	}
}

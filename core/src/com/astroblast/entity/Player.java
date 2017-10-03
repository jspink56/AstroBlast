package com.astroblast.entity;
// Created by John on 25-Sep-2017

import com.astroblast.config.GameConfig;

public class Player extends GameObjectBase {

	public Player() {
		super(GameConfig.PLAYER_BOUNDS_RADIUS);
		setSize(GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE);
	}
}

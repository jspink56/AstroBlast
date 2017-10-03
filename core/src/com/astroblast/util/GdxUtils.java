package com.astroblast.util;
// Created by John on 25-Sep-2017

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;

public class GdxUtils {

	public static void clearScreen() {
		// clear the screen to black
		clearScreen(Color.BLACK);
	}

	public static void clearScreen(Color color) {
		// clear the screen to specified color
		Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	private GdxUtils() {
	}
}

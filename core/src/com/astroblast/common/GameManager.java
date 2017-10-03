package com.astroblast.common;
// Created by John on 25-Sep-2017

import com.astroblast.AstroBlastGame;
import com.astroblast.config.DifficultyLevel;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameManager {

	public static final GameManager INSTANCE = new GameManager();

	private static final String HIGH_SCORE_KEY = "highscore";
	private static final String DIFFICULTY_KEY = "difficulty";

	private Preferences PREFS;
	private int highScore;
	private DifficultyLevel difficultyLevel = DifficultyLevel.MEDIUM;


	private GameManager() {
		PREFS = Gdx.app.getPreferences(AstroBlastGame.class.getSimpleName());
		highScore = PREFS.getInteger(HIGH_SCORE_KEY, 0);
		String difficultyName = PREFS.getString(DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name());
		difficultyLevel = DifficultyLevel.valueOf(difficultyName);
	}

	public void updateHighScore(int score) {
		if (score < highScore) {
			return;
		}

		highScore = score;
		PREFS.putInteger(HIGH_SCORE_KEY, highScore);
		PREFS.flush();
	}

	public String getHighScoreString() {
		return String.valueOf(highScore);
	}

	public DifficultyLevel getDifficultyLevel() {
		return difficultyLevel;
	}

	public void updateDifficulty(DifficultyLevel newDifficultyLevel) {
		if (difficultyLevel == newDifficultyLevel) {
			return;
		}

		difficultyLevel = newDifficultyLevel;
		PREFS.putString(DIFFICULTY_KEY, difficultyLevel.name());
		PREFS.flush();
	}
}

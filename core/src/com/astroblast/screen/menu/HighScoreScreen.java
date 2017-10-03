package com.astroblast.screen.menu;
// Created by John on 25-Sep-2017

import com.astroblast.AstroBlastGame;
import com.astroblast.assets.AssetDescriptors;
import com.astroblast.assets.RegionNames;
import com.astroblast.common.GameManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;

public class HighScoreScreen extends MenuScreenBase {
	private static final Logger log = new Logger(HighScoreScreen.class.getName(), Logger.DEBUG);

	public HighScoreScreen(AstroBlastGame game) {
		super(game);
	}

	@Override
	protected Actor createUI() {
		Table table = new Table();

		TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
		Skin uiskin = assetManager.get(AssetDescriptors.UI_SKIN);
		TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

		// background
		table.setBackground(new TextureRegionDrawable(backgroundRegion));

		// highscore text
		Label highScoreText = new Label("HIGHSCORE", uiskin);

		// highscore label
		String highScoreString = GameManager.INSTANCE.getHighScoreString();
		Label highScoreLabel = new Label(highScoreString, uiskin);

		// back button
		TextButton backButton = new TextButton("BACK",uiskin);
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				back();
			}
		});

		// setup tables
		Table contentTable = new Table(uiskin);
		contentTable.defaults().pad(20);
		contentTable.setBackground(RegionNames.PANEL);

		contentTable.add(highScoreText).row();
		contentTable.add(highScoreLabel).row();
		contentTable.add(backButton);

		contentTable.center();

		table.add(contentTable);
		table.center();
		table.setFillParent(true);
		table.pack();

		return table;

	}

	private void back() {
		log.debug("back()");
		game.setScreen(new MenuScreen(game));
	}
}

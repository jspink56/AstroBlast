package com.astroblast.assets;
// Created by John on 25-Sep-2017

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetDescriptors {

	public static final AssetDescriptor<BitmapFont> FONT =
			new AssetDescriptor<BitmapFont>(AssetPaths.UI_FONT, BitmapFont.class);

	public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
			new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

	public static final AssetDescriptor<Skin> UI_SKIN =
			new AssetDescriptor<Skin>(AssetPaths.UI_SKIN, Skin.class);

	public static final AssetDescriptor<Sound> HIT_SOUND =
			new AssetDescriptor<Sound>(AssetPaths.HIT, Sound.class);

	public static final AssetDescriptor<TextureAtlas> ASTEROIDS =
			new AssetDescriptor<TextureAtlas>(AssetPaths.ASTEROIDS, TextureAtlas.class);

	public static final AssetDescriptor<TextureAtlas> STARFIELD =
			new AssetDescriptor<TextureAtlas>(AssetPaths.STARFIELD, TextureAtlas.class);

	private AssetDescriptors() {}
}

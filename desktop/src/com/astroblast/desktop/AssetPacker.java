package com.astroblast.desktop;
// Created by John on 26-Sep-2017

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AssetPacker {

	private static final boolean DRAW_DEBUG_OUTLINE = false;

	private static final String RAW_ASSETS_PATH = "desktop/assets-raw";
	private static final String ASSETS_PATH = "android/assets";

	public static void main(String[] args) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		settings.debug = DRAW_DEBUG_OUTLINE;
		settings.maxWidth = 1024;

//		TexturePacker.process(settings,
//				RAW_ASSETS_PATH + "/gameplay",
//				ASSETS_PATH + "/gameplay",
//				"gameplay"
//		);

//		TexturePacker.process(settings,
//				RAW_ASSETS_PATH + "/asteroids",
//				ASSETS_PATH + "/asteroids",
//				"asteroids"
//		);

//		TexturePacker.process(settings,
//				RAW_ASSETS_PATH + "/skin",
//				ASSETS_PATH + "/ui",
//				"uiskin"
//		);

		// larger image size to accommodate 4 starfield textures
		settings.maxWidth = 2048;
		TexturePacker.process(settings,
				RAW_ASSETS_PATH + "/starfield",
				ASSETS_PATH + "/starfield",
				"starfield"
		);

	}
}

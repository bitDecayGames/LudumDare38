package com.bitdecay.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.screen.SplashScreen;
import com.bitdecay.game.trait.ICanSetScreen;
import com.bitdecay.game.util.RunMode;
import com.bytebreakstudios.animagic.texture.AnimagicTextureAtlas;
import com.bytebreakstudios.animagic.texture.AnimagicTextureAtlasLoader;

/**
 * The actual game object used by libgdx.
 */
public class MyGame extends Game implements ICanSetScreen{
    // statics
    public static AssetManager ASSET_MANAGER = new AssetManager();
    public static AnimagicTextureAtlas ATLAS;
    public static RunMode RUN_MODE;
    public static BitmapFont FONT;

    static {
        GdxNativesLoader.load();
    }

    public MyGame(RunMode runMode){
        super();
        MyGame.RUN_MODE = runMode;
    }

    public void queueAssetsForLoad() {
        ASSET_MANAGER.setLoader(AnimagicTextureAtlas.class, new AnimagicTextureAtlasLoader(new InternalFileHandleResolver()));
        FileHandleResolver resolver = new InternalFileHandleResolver();
        ASSET_MANAGER.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        ASSET_MANAGER.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        ASSET_MANAGER.load("img/packed/main.atlas", AnimagicTextureAtlas.class);

        FreetypeFontLoader.FreeTypeFontLoaderParameter myBigFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        myBigFont.fontFileName = "font/acknowtt.ttf";
        myBigFont.fontParameters.size = 100;
        ASSET_MANAGER.load("font/acknowtt.ttf", BitmapFont.class, myBigFont);

        FONT = new BitmapFont(Gdx.files.internal("font/bit.fnt"));
    }

    @Override
    public void create() {
        queueAssetsForLoad();
        ASSET_MANAGER.finishLoading();
        ATLAS = ASSET_MANAGER.get("img/packed/main.atlas", AnimagicTextureAtlas.class);

        if (RUN_MODE == RunMode.DEV) setScreen(new GameScreen(this));
        else if (RUN_MODE == RunMode.PROD) setScreen(new SplashScreen(this));
    }
}

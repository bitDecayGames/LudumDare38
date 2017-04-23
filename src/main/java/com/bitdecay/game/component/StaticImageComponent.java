package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;

/**
 * This component extends the drawable component and it only draws a single static image.
 */
public class StaticImageComponent extends DrawableComponent {

    private TextureRegion image;

    public StaticImageComponent(String path) {
        image = MyGame.ATLAS.findRegion(path);
    }

    @Override
    public TextureRegion image() {
        return image;
    }

    public void setImage(TextureRegion region) {
        image = region;
    }
}

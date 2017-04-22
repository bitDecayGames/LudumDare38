package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.trait.IUpdate;
import com.bytebreakstudios.animagic.animation.Animation;
import com.bytebreakstudios.animagic.animation.FrameRate;

/**
 * This component extends the drawable component and loops through an animation at a constant rate
 */
public class AnimatedImageComponent extends DrawableComponent implements IUpdate {

    public Animation animation;

    public AnimatedImageComponent(String path, float secondsPerFrame) {
        animation = new Animation(path, Animation.AnimationPlayState.REPEAT, FrameRate.perFrame(secondsPerFrame), MyGame.ATLAS.findRegions(path).toArray(TextureRegion.class));
    }

    @Override
    public void update(float delta){ animation.update(delta);}

    @Override
    public TextureRegion image() {
        return animation.getFrame();
    }
}

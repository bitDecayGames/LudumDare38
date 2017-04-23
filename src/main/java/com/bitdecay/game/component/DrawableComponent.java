package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * This component is for extending other drawable component types.  Ie: animated, static, etc
 */
public abstract class DrawableComponent extends AbstractComponent {
    public float scaleX = 1;
    public float scaleY = 1;
    public boolean isVisible = true;

    public abstract TextureRegion image();
}

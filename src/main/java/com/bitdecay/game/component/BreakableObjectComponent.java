package com.bitdecay.game.component;

import com.bitdecay.game.MyGame;
import com.bytebreakstudios.animagic.texture.AnimagicTextureRegion;

/**
 * Created by Monday on 4/22/2017.
 */
public class BreakableObjectComponent extends AbstractComponent {

    public AnimagicTextureRegion image;
    public float breakVelocity;
    public float breakWidth;
    public float breakHeight;

    public BreakableObjectComponent(String brokenImage, float breakVelocity, float breakWidth, float breakHeight) {
        image = MyGame.ATLAS.findRegion(brokenImage);
        this.breakVelocity = breakVelocity;
        this.breakWidth = breakWidth;
        this.breakHeight = breakHeight;
    }
}

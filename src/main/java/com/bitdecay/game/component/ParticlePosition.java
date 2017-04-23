package com.bitdecay.game.component;

/**
 * Created by Monday on 4/23/2017.
 */
public class ParticlePosition extends AbstractComponent {
    public float xOffset;
    public float yOffset;

    public float x;
    public float y;

    public ParticlePosition(float xOffset, float yOffset) {

        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
}

package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.ParticleFactory;

/**
 * Created by Monday on 4/22/2017.
 */
public class BreakableObjectComponent extends AbstractComponent {

    public String imagePath;
    public float breakVelocity;
    public float breakWidth;
    public float breakHeight;
    public ParticleFactory.ParticleChoice particle;

    public boolean broken;

    public BreakableObjectComponent(String brokenImage, float breakVelocity, float breakWidth, float breakHeight, ParticleFactory.ParticleChoice particle) {
        imagePath = brokenImage;
        this.breakVelocity = breakVelocity;
        this.breakWidth = breakWidth;
        this.breakHeight = breakHeight;
        this.particle = particle;
    }
}

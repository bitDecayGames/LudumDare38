package com.bitdecay.game.component;

import com.bitdecay.game.gameobject.ParticleFactory;
import com.bitdecay.game.util.SoundLibrary;

/**
 * Created by Monday on 4/22/2017.
 */
public class BreakableObjectComponent extends AbstractComponent {

    public String imagePath;
    public float breakVelocity;
    public float breakWidth;
    public float breakHeight;
    public ParticleFactory.ParticleChoice particle;
    public String soundName;

    public boolean broken;

    public BreakableObjectComponent(String brokenImage, float breakVelocity, float breakWidth, float breakHeight, ParticleFactory.ParticleChoice particle, String sound) {
        imagePath = brokenImage;
        this.breakVelocity = breakVelocity;
        this.breakWidth = breakWidth;
        this.breakHeight = breakHeight;
        this.particle = particle;
        soundName = sound;
    }
    public BreakableObjectComponent(String brokenImage, float breakVelocity, float breakWidth, float breakHeight, ParticleFactory.ParticleChoice particle) {
        imagePath = brokenImage;
        this.breakVelocity = breakVelocity;
        this.breakWidth = breakWidth;
        this.breakHeight = breakHeight;
        this.particle = particle;
    }
}

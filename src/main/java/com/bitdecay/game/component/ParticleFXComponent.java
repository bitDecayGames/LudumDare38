package com.bitdecay.game.component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleFXComponent extends DrawableComponent {
    public ParticleEffect effect;
    public boolean continuous;

    public boolean started;
    public float timePassed;

    public boolean requestStart = true;
    public boolean requestStop;

    public ParticleFXComponent(boolean continuous, FileHandle effectFile, FileHandle particleDir) {
        this.continuous = continuous;
        effect = new ParticleEffect();
        effect.load(effectFile, particleDir);
        effect.scaleEffect(.01f);
    }

    @Override
    public TextureRegion image() {
        return null;
    }
}

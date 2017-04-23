package com.bitdecay.game.component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleFXComponent extends AbstractComponent {
    public ParticleEffect effect;
    public boolean continuous;

    public boolean started;
    public float timePassed;

    public ParticleFXComponent(boolean continuous, FileHandle effectFile, FileHandle particleDir) {
        this.continuous = continuous;
        effect = new ParticleEffect();
        effect.load(effectFile, particleDir);
        effect.scaleEffect(.01f);
    }
}

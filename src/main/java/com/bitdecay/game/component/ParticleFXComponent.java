package com.bitdecay.game.component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleFXComponent extends AbstractComponent {
    public ParticleEffect effect;
    public float duration;

    public boolean started;
    public float timePassed;

    public ParticleFXComponent(float duration, FileHandle effectFile, FileHandle particleDir) {
        this.duration = duration;
        effect = new ParticleEffect();
        effect.load(effectFile, particleDir);
        effect.scaleEffect(.01f);
    }
}

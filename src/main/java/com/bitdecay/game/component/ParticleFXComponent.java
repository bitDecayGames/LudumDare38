package com.bitdecay.game.component;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleFXComponent extends DrawableComponent {

    public static ParticleEmitter.ScaledNumericValue zeroEmission = new ParticleEmitter.ScaledNumericValue();

    public ParticleEffect effect;
    public boolean continuous;

    public boolean started;
    public float timePassed;

    public boolean requestStart = true;
    public boolean requestStop;

    public Map<ParticleEmitter, ParticleEmitter.ScaledNumericValue> saved = new HashMap<>();

    public ParticleFXComponent(boolean continuous, FileHandle effectFile, FileHandle particleDir) {
        this.continuous = continuous;
        effect = new ParticleEffect();
        effect.load(effectFile, particleDir);
        effect.scaleEffect(.01f);

        for (ParticleEmitter particleEmitter : effect.getEmitters()) {
            if (!saved.containsKey(particleEmitter)) {
                saved.put(particleEmitter, new ParticleEmitter.ScaledNumericValue());
            }
            ParticleEmitter.ScaledNumericValue scaledNumericValue = saved.get(particleEmitter);
            scaledNumericValue.setLow(particleEmitter.getEmission().getLowMin(), particleEmitter.getEmission().getLowMax());
            scaledNumericValue.setHigh(particleEmitter.getEmission().getHighMin(), particleEmitter.getEmission().getHighMax());
        }
    }

    @Override
    public TextureRegion image() {
        return null;
    }
}

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
    public String effectName;
    public boolean continuous;

    public boolean started;
    public float timePassed;

    public boolean requestStart = true;
    public boolean requestStop;

    public static Map<String, ParticleEffect> particleCache = new HashMap<>();
    public static Map<String, Map<ParticleEmitter, ParticleEmitter.ScaledNumericValue>> saved = new HashMap<>();

    public ParticleFXComponent(boolean continuous, FileHandle effectFile, FileHandle particleDir) {
        this.continuous = continuous;
        this.effectName = effectFile.name();

        if (!particleCache.containsKey(effectName)) {
            System.out.println("Caching new effect: " + effectName);
            ParticleEffect forCache = new ParticleEffect();
            forCache.load(effectFile, particleDir);
            forCache.scaleEffect(.01f);
            particleCache.put(effectName, forCache);
        }

        effect = new ParticleEffect(particleCache.get(effectName));

        if (!saved.containsKey(effectName)) {
            saved.put(effectName, new HashMap<>(effect.getEmitters().size));
        }

        Map<ParticleEmitter, ParticleEmitter.ScaledNumericValue> emitterSaves = saved.get(effectName);

        for (ParticleEmitter particleEmitter : effect.getEmitters()) {
            if (!emitterSaves.containsKey(particleEmitter)) {
                emitterSaves.put(particleEmitter, new ParticleEmitter.ScaledNumericValue());
            }
            ParticleEmitter.ScaledNumericValue scaledNumericValue = emitterSaves.get(particleEmitter);
            scaledNumericValue.setLow(particleEmitter.getEmission().getLowMin(), particleEmitter.getEmission().getLowMax());
            scaledNumericValue.setHigh(particleEmitter.getEmission().getHighMin(), particleEmitter.getEmission().getHighMax());
        }
    }

    @Override
    public TextureRegion image() {
        return null;
    }
}

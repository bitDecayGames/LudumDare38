package com.bitdecay.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.bitdecay.game.component.ParticleFXComponent;
import com.bitdecay.game.component.ParticlePosition;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleFactory {

    public static MyGameObject getParticleObject(ParticleChoice particle, Vector2 pos, float angle) {
        MyGameObject particleEffect = new MyGameObject();
        ParticleFXComponent particleComp = null;
        switch(particle) {
            case MAIL:
                particleComp = getMailParticle(1f);
                break;
            case POOP:
                particleComp = getPoopParticle(1f);
                break;
            case BLOOD:
                particleComp = getBloodParticle(1f);
                break;
            case TRASH:
                particleComp = getTrashParticle(1f);
                break;
            case WATER:
                particleComp = getWaterParticle(1f);
                break;
            case EXHAUST:
                particleComp = getExhaustParticle(.5f);
                break;
            case NONE:
                return null;
        }
        updateParticleAngle(particleComp, angle);
        particleEffect.addComponent(particleComp);
        particleEffect.addComponent(new PositionComponent(pos.x, pos.y));
        particleEffect.addComponent(new ParticlePosition(0, 0));
        particleEffect.addComponent(new RotationComponent(0));
        return particleEffect;
    }

    private static void updateParticleAngle(ParticleFXComponent particleComp, float angle) {
        Array<ParticleEmitter> emitters = particleComp.effect.getEmitters();
        for (int i = 0; i < emitters.size; i++) {
            ParticleEmitter.ScaledNumericValue val = emitters.get(i).getAngle();
            float h1 = val.getHighMin() + angle;
            float h2 = val.getHighMax() + angle;
            float l1 = val.getLowMin() + angle;
            float l2 = val.getLowMax() + angle;
            val.setHigh(h1, h2);
            val.setLow(l1, l2);
        }
    }

    public enum ParticleChoice {
        MAIL,
        POOP,
        BLOOD,
        TRASH,
        WATER,
        EXHAUST,
        NONE
    }

    public static ParticleFXComponent getMailParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/mail.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getPoopParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/poopoo.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getBloodParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/blood.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getTrashParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/trash.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getWaterParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/water.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getExhaustParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/exhaust.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getParticleFX(float duration, String particleFile, String particleDir) {
        return new ParticleFXComponent(duration, Gdx.files.internal(particleFile), Gdx.files.internal(particleDir));
    }
}

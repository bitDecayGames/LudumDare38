package com.bitdecay.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleFactory {

    public static MyGameObject getParticleObject(ParticleChoice particle, Vector2 pos) {
        MyGameObject particleEffect = new MyGameObject();
        switch(particle) {
            case MAIL:
                particleEffect.addComponent(getMailParticle(1f));
                break;
            case POOP:
                particleEffect.addComponent(getPoopParticle(1f));
                break;
            case BLOOD:
                particleEffect.addComponent(getBloodParticle(1f));
                break;
            case TRASH:
                particleEffect.addComponent(getTrashParticle(1f));
                break;
            case WATER:
                particleEffect.addComponent(getWaterParticle(1f));
                break;
            case EXHAUST:
                particleEffect.addComponent(getExhaustParticle(.5f));
                break;
            case NONE:
                return null;
        }
        particleEffect.addComponent(new PositionComponent(pos.x, pos.y));
        particleEffect.addComponent(new ParticlePosition(0, 0));
        particleEffect.addComponent(new RotationComponent(0));
        return particleEffect;
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

    public static AbstractComponent getMailParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/mail.p", "src/main/resources/img/packable/main/particles/");
    }

    public static AbstractComponent getPoopParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/poopoo.p", "src/main/resources/img/packable/main/particles/");
    }

    public static AbstractComponent getBloodParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/blood.p", "src/main/resources/img/packable/main/particles/");
    }

    public static AbstractComponent getTrashParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/trash.p", "src/main/resources/img/packable/main/particles/");
    }

    public static AbstractComponent getWaterParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/water.p", "src/main/resources/img/packable/main/particles/");
    }

    public static AbstractComponent getExhaustParticle(float duration) {
        return getParticleFX(duration, "src/main/resources/particle/exhaust.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getParticleFX(float duration, String particleFile, String particleDir) {
        return new ParticleFXComponent(duration, Gdx.files.internal(particleFile), Gdx.files.internal(particleDir));
    }
}

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
        switch (particle) {
            case MAIL:
                particleComp = getMailParticle();
                break;
            case POOP:
                particleComp = getPoopParticle();
                break;
            case BLOOD:
                particleComp = getBloodParticle();
                break;
            case TRASH:
                particleComp = getTrashParticle();
                break;
            case WATER:
                particleComp = getWaterParticle();
                break;
            case EXHAUST:
                particleComp = getExhaustParticle();
                break;
            case FENCE:
                particleComp = getFenceParticle();
                break;
            case HEADSTONE:
                particleComp = getHeadstoneParticle();
                break;
            case NONE:
            default:
                return null;
        }
        if (angle != 0) {
            updateParticleAngle(particleComp, angle);
        }
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
        FENCE,
        HEADSTONE,
        NONE
    }

    public static ParticleFXComponent getMailParticle() {
        return getParticleFX(false, "src/main/resources/particle/mail.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getPoopParticle() {
        return getParticleFX(false, "src/main/resources/particle/poopoo.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getBloodParticle() {
        return getParticleFX(false, "src/main/resources/particle/blood.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getTrashParticle() {
        return getParticleFX(false, "src/main/resources/particle/trash.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getWaterParticle() {
        return getParticleFX(false, "src/main/resources/particle/water.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getExhaustParticle() {
        return getParticleFX(true, "src/main/resources/particle/exhaust.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getFireParticle() {
        return getParticleFX(true, "src/main/resources/particle/fire.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getSkidParticle() {
        return getParticleFX(true, "src/main/resources/particle/skid.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getFenceParticle() {
        return getParticleFX(false, "src/main/resources/particle/fence.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getHeadstoneParticle() {
        return getParticleFX(false, "src/main/resources/particle/headstone.p", "src/main/resources/img/packable/main/particles/");
    }

    public static ParticleFXComponent getParticleFX(boolean continuous, String particleFile, String particleDir) {
        return new ParticleFXComponent(continuous, Gdx.files.internal(particleFile), Gdx.files.internal(particleDir));
    }
}

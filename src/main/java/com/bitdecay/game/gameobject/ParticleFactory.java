package com.bitdecay.game.gameobject;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.ParticleFXComponent;
import com.bitdecay.game.component.PositionComponent;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleFactory {

    public static MyGameObject getParticleObject(ParticleChoice particle, Vector2 pos) {
        switch(particle) {
            case MAIL:
                return getMailParticle(pos.x, pos.y, 1f);
            case POOP:
                return getPoopParticle(pos.x, pos.y, 1f);
            case BLOOD:
                return getBloodParticle(pos.x, pos.y, 1f);
            case TRASH:
                return getTrashParticle(pos.x, pos.y, 1f);
            case WATER:
                return getWaterParticle(pos.x, pos.y, 4f);
            case NONE:
                break;
        }
        return null;
    }

    public enum ParticleChoice {
        MAIL,
        POOP,
        BLOOD,
        TRASH,
        WATER,
        NONE
    }

    public static MyGameObject getMailParticle(float x, float y, float duration) {
        MyGameObject particleEffect = getParticleWithFiles(duration, "src/main/resources/particle/mail.p", "src/main/resources/img/packable/main/particles/");
        particleEffect.addComponent(new PositionComponent(x, y));
        return particleEffect;
    }

    public static MyGameObject getPoopParticle(float x, float y, float duration) {
        MyGameObject particleEffect = getParticleWithFiles(duration, "src/main/resources/particle/poopoo.p", "src/main/resources/img/packable/main/particles/");
        particleEffect.addComponent(new PositionComponent(x, y));
        return particleEffect;
    }

    public static MyGameObject getBloodParticle(float x, float y, float duration) {
        MyGameObject particleEffect = getParticleWithFiles(duration, "src/main/resources/particle/blood.p", "src/main/resources/img/packable/main/particles/");
        particleEffect.addComponent(new PositionComponent(x, y));
        return particleEffect;
    }

    public static MyGameObject getTrashParticle(float x, float y, float duration) {
        MyGameObject particleEffect = getParticleWithFiles(duration, "src/main/resources/particle/trash.p", "src/main/resources/img/packable/main/particles/");
        particleEffect.addComponent(new PositionComponent(x, y));
        return particleEffect;
    }

    public static MyGameObject getWaterParticle(float x, float y, float duration) {
        MyGameObject particleEffect = getParticleWithFiles(duration, "src/main/resources/particle/water.p", "src/main/resources/img/packable/main/particles/");
        particleEffect.addComponent(new PositionComponent(x, y));
        return particleEffect;
    }

    private static MyGameObject getParticleWithFiles(float duration, String particleFile, String particleDir) {
        MyGameObject particleEffect = new MyGameObject();
        particleEffect.addComponent(new ParticleFXComponent(duration, Gdx.files.internal(particleFile), Gdx.files.internal(particleDir)));
        return particleEffect;
    }
}

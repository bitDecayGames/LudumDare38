package com.bitdecay.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bitdecay.game.component.ParticleFXComponent;
import com.bitdecay.game.component.ParticlePosition;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractDrawableSystem;

/**
 * Created by Monday on 4/22/2017.
 */
public class ParticleSystem extends AbstractDrawableSystem {
    public ParticleSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                ParticleFXComponent.class,
                ParticlePosition.class
        );
    }

    @Override
    public void draw(SpriteBatch spriteBatch, OrthographicCamera camera) {
        spriteBatch.begin();
        for (MyGameObject gob : gobs) {
            gob.forEachComponentDo(ParticleFXComponent.class, fx -> gob.forEachComponentDo(ParticlePosition.class, pos -> {
                fx.effect.setPosition(pos.x, pos.y);
                if (!fx.started) {
                    if (!fx.continuous) {
                        fx.effect.allowCompletion();
                    }
                    System.out.println("Starting particle");
                    System.out.println("Continuous: " + fx.continuous);
                    fx.effect.start();
                    fx.started = true;
                }
                float delta = Gdx.graphics.getDeltaTime();
                fx.effect.update(delta);
                fx.effect.draw(spriteBatch);
                fx.timePassed += delta;
                if (fx.effect.isComplete()) {
                    if (fx.continuous) {
                        fx.effect.start();
                    } else {
                        gob.removeComponent(ParticleFXComponent.class);
                    }
                }
            }));
        }
        spriteBatch.end();
    }
}

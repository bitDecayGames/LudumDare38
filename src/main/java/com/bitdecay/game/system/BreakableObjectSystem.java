package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.ParticleFactory;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Monday on 4/22/2017.
 */
public class BreakableObjectSystem extends AbstractForEachUpdatableSystem {


    public BreakableObjectSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                BreakableObjectComponent.class,
                StaticImageComponent.class,
                PhysicsComponent.class,
                SizeComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(BreakableObjectComponent.class, breaker -> {
            gob.forEachComponentDo(PhysicsComponent.class, phys -> {
                gob.forEachComponentDo(SizeComponent.class, size -> {
                    Vector2 linearVelocity = phys.body.getLinearVelocity();
                    if (linearVelocity.len() > breaker.breakVelocity & !breaker.broken) {
                        breaker.broken = true;
                        gob.forEachComponentDo(DrawableComponent.class, drawable -> {
                            gob.removeComponentInstance(drawable);
                        });
                        gob.addComponent(new StaticImageComponent(breaker.imagePath));
                        size.set(breaker.breakWidth, breaker.breakHeight);
                        if (breaker.particle != null) {
                            MyGameObject particleObject = ParticleFactory.getParticleObject(breaker.particle, phys.body.getWorldCenter());
                            if (particleObject != null) {
                                room.getGameObjects().add(particleObject);
                            }
                        }
                        // TODO figure out how to get these to feel 'good'
//                            phys.body.setTransform(phys.body.getPosition(), linearVelocity.angle());
                    }
                });
            });
        });
    }
}
package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.BreakableObjectComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.SizeComponent;
import com.bitdecay.game.component.StaticImageComponent;
import com.bitdecay.game.gameobject.MyGameObject;
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
            gob.forEachComponentDo(StaticImageComponent.class, image -> {
                gob.forEachComponentDo(PhysicsComponent.class, phys -> {
                    gob.forEachComponentDo(SizeComponent.class, size -> {
                        Vector2 linearVelocity = phys.body.getLinearVelocity();
                        if (linearVelocity.len() > breaker.breakVelocity) {
                            image.setImage(breaker.image);
                            size.set(breaker.breakWidth, breaker.breakHeight);
                            // TODO figure out how to get these to feel 'good'
//                            phys.body.setTransform(phys.body.getPosition(), linearVelocity.angle());
                        }
                    });
                });
            });
        });
    }
}
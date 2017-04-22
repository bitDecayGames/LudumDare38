package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.TireFrictionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Monday on 4/22/2017.
 */
public class TireFrictionSystem extends AbstractForEachUpdatableSystem {


    public TireFrictionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                TireFrictionComponent.class,
                PhysicsComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(TireFrictionComponent.class, friction -> gob.forEachComponentDo(PhysicsComponent.class, phys -> {
            Vector2 lateralVelocity = getLateralVelocity(phys);
            updateFriction(phys, friction, lateralVelocity);
        }));
    }

    private void updateFriction(PhysicsComponent phys, TireFrictionComponent friction, Vector2 lateralVelocity) {
        if (!friction.tireLocked) {
            updateLateralFriction(phys, friction, lateralVelocity);
        }
        updateRollingFriction(phys, friction);
    }

    private void updateRollingFriction(PhysicsComponent phys, TireFrictionComponent friction) {
        phys.body.applyLinearImpulse(phys.body.getLinearVelocity().cpy().scl(-0.05f), phys.body.getWorldCenter(), true);
    }

    private void updateLateralFriction(PhysicsComponent phys, TireFrictionComponent friction, Vector2 lateralVelocity) {
        Vector2 neededImpulse = lateralVelocity.scl(-phys.body.getMass());
        if (neededImpulse.len() > friction.maxForce) {
            neededImpulse.nor().scl(friction.maxForce);
        }
        phys.body.applyLinearImpulse(neededImpulse, phys.body.getWorldCenter(), true);
        phys.body.applyAngularImpulse(.1f * phys.body.getInertia() * -phys.body.getAngularVelocity(), true);
    }

    private Vector2 getLateralVelocity(PhysicsComponent phys) {
        Vector2 currentRightVector = phys.body.getWorldVector(new Vector2(1, 0));
        return currentRightVector.scl(currentRightVector.dot(phys.body.getLinearVelocity()));
    }
}

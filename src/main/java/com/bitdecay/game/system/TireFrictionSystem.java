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
            updateFriction(phys, friction);
        }));
    }

    private void updateFriction(PhysicsComponent phys, TireFrictionComponent friction) {
        updateRollingFriction(phys, friction);
        updateLateralFriction(phys, friction);
    }

    private void updateRollingFriction(PhysicsComponent phys, TireFrictionComponent friction) {
        Vector2 rollingVelocity = getRollingVelocity(phys);
        Vector2 neededImpulse = rollingVelocity.scl(-phys.body.getMass());
        phys.body.applyLinearImpulse(neededImpulse.scl(0.05f), phys.body.getWorldCenter(), true);
    }

    private void updateLateralFriction(PhysicsComponent phys, TireFrictionComponent friction) {
        Vector2 lateralVelocity = getLateralVelocity(phys);
        Vector2 neededImpulse = lateralVelocity.scl(-phys.body.getMass());
        if (friction.tireLocked && phys.body.getLinearVelocity().len() > friction.lockedTireGripVelocity) {
            // let the tires slide around if the tires are locked up
            if (neededImpulse.len() > friction.driftingMaxForce) {
                neededImpulse.nor().scl(friction.driftingMaxForce);
            }
        } else if (neededImpulse.len() > friction.maxForce) {
            neededImpulse.nor().scl(friction.maxForce);
        }
        phys.body.applyLinearImpulse(neededImpulse, phys.body.getWorldCenter(), true);
        phys.body.applyAngularImpulse(.1f * phys.body.getInertia() * -phys.body.getAngularVelocity(), true);
    }

    private Vector2 getLateralVelocity(PhysicsComponent phys) {
        Vector2 currentRightVector = phys.body.getWorldVector(new Vector2(1, 0));
        return currentRightVector.scl(currentRightVector.dot(phys.body.getLinearVelocity()));
    }

    private Vector2 getRollingVelocity(PhysicsComponent phys) {
        Vector2 currentRollingVector = phys.body.getWorldVector(new Vector2(0, 1));
        return currentRollingVector.scl(currentRollingVector.dot(phys.body.getLinearVelocity()));
    }
}

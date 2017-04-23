package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.ModifiedSteeringComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.TireFrictionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Monday on 4/22/2017.
 */
public class TireFrictionSystem extends AbstractForEachUpdatableSystem {

    float rollingDampening = 0.00f;

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
            TireFrictionComponent.TireFrictionData data;
            if(gob.hasComponent(ModifiedSteeringComponent.class)) {
                data = gob.getComponent(ModifiedSteeringComponent.class).get().modifiedFriction.copy();
                data.weightOnTire = friction.data.weightOnTire;
            } else {
                data = friction.data;
            }

            updateFriction(phys, friction.tireLocked, data);
        }));
    }

    private void updateFriction(PhysicsComponent phys, boolean tiresLocked, TireFrictionComponent.TireFrictionData data) {
        updateRollingFriction(phys, tiresLocked, data);
        updateLateralFriction(phys, tiresLocked, data);
    }

    private void updateRollingFriction(PhysicsComponent phys, boolean tiresLocked, TireFrictionComponent.TireFrictionData data) {
        Vector2 rollingVelocity = getRollingVelocity(phys);
        float mass = phys.body.getMass() + data.weightOnTire;
        Vector2 neededImpulse = rollingVelocity.scl(-mass);
        phys.body.applyLinearImpulse(neededImpulse.scl(rollingDampening), phys.body.getWorldCenter(), true);
    }

    private void updateLateralFriction(PhysicsComponent phys, boolean tiresLocked, TireFrictionComponent.TireFrictionData data) {
        Vector2 lateralVelocity = getLateralVelocity(phys);
        float mass = phys.body.getMass() + data.weightOnTire;
        Vector2 neededImpulse = lateralVelocity.scl(-mass);
        if (tiresLocked && phys.body.getLinearVelocity().len() > data.lockedTireGripVelocity) {
            // let the tires slide around if the tires are locked up
            if (neededImpulse.len() > data.driftingMaxForce) {
                neededImpulse.nor().scl(data.driftingMaxForce);
            }
        } else if (neededImpulse.len() > data.rollingMaxForce) {
            neededImpulse.nor().scl(data.rollingMaxForce);
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

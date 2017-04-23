package com.bitdecay.game.system;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.ModifiedSteeringComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.TireFrictionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.physics.TireFrictionData;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Monday on 4/22/2017.
 */
public class TireFrictionSystem extends AbstractForEachUpdatableSystem {

    float rollingFrictionCoefficient = 0.01f;
    float brakingFrictionCoefficient = 0.1f;

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
            TireFrictionData data;
            if (gob.hasComponent(ModifiedSteeringComponent.class)) {
                data = gob.getComponent(ModifiedSteeringComponent.class).get().modifiedFriction.copy();
                data.weightOnTire = friction.data.weightOnTire;
            } else {
                data = friction.data;
            }

            updateFriction(phys, friction.tireLocked, data);
        }));
    }

    private void updateFriction(PhysicsComponent phys, boolean tiresLocked, TireFrictionData data) {
        updateRollingFriction(phys, tiresLocked, data);
        updateLateralFriction(phys, tiresLocked, data);
    }

    private void updateRollingFriction(PhysicsComponent phys, boolean tiresLocked, TireFrictionData data) {
        Vector2 rollingVelocity = getRollingVelocity(phys);
        float mass = phys.body.getMass() + data.weightOnTire;
        Vector2 neededImpulse = rollingVelocity.scl(-mass);

        if (tiresLocked && isTravellingBackwards(phys)) {
            // once they are backwards, apply normal braking
            neededImpulse.scl(brakingFrictionCoefficient);
        } else {
            // tire is rolling along
            neededImpulse.scl(rollingFrictionCoefficient);
        }

        phys.body.applyLinearImpulse(neededImpulse, phys.body.getWorldCenter(), true);
    }

    private void updateLateralFriction(PhysicsComponent phys, boolean tireLocked, TireFrictionData data) {
        Vector2 lateralVelocity = getLateralVelocity(phys);
        float mass = phys.body.getMass() + data.weightOnTire;
        Vector2 neededImpulse = lateralVelocity.scl(-mass);

        if (tireLocked) {
            boolean travellingBackwards = isTravellingBackwards(phys);
            if (!travellingBackwards && phys.body.getLinearVelocity().len() > data.maxVelocityForLockedTiresToExperienceLateralFriction) {
                neededImpulse.set(0, 0);
            } else {
                // let the tires slide around if the tires are locked up
                if (neededImpulse.len() > data.maxLateralForceWhileWheelsLocked) {
                    neededImpulse.nor().scl(data.maxLateralForceWhileWheelsLocked);
                }
            }
        } else if (neededImpulse.len() > data.maxLateralForceWhileInTraction) {
            neededImpulse.nor().scl(data.maxLateralForceWhileInTraction);
        }
        phys.body.applyLinearImpulse(neededImpulse, phys.body.getWorldCenter(), true);
        phys.body.applyAngularImpulse(.1f * phys.body.getInertia() * -phys.body.getAngularVelocity(), true);
    }

    private boolean isTravellingBackwards(PhysicsComponent phys) {
        float travelAngle = phys.body.getLinearVelocity().angle();
        if (travelAngle > 360) {
            travelAngle -= 360;
        } else if (travelAngle < 0) {
            travelAngle += 360;
        }
        float aimedAngle = 90 + MathUtils.radDeg * phys.body.getAngle(); // tires are pointed 'up' when at zero degrees
        while (aimedAngle > 360) {
            aimedAngle -= 360;
        }

        while (aimedAngle < 0) {
            aimedAngle += 360;
        }
//        System.out.println("Travel angle: " + travelAngle);
//        System.out.println("Aimed angle: " + aimedAngle);

        float difference = travelAngle - aimedAngle;
        if (difference > 180) {
            difference -= 360;
        } else if (difference < -180) {
            difference += 360;
        }
        if (Math.abs(difference) > 175) {
            // we are pointed within 5 degrees of backwards
//            System.out.println("YOU BACKWARDS, HO");
            return true;
        } else {
            return false;
        }
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

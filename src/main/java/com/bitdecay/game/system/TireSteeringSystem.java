package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.RevoluteJointComponent;
import com.bitdecay.game.component.SteerableComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.InputHelper;

/**
 * This system is in charge
 */
public class TireSteeringSystem extends AbstractForEachUpdatableSystem {


    public TireSteeringSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                SteerableComponent.class,
                RevoluteJointComponent.class,
                PhysicsComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(SteerableComponent.class, steering -> gob.forEachComponentDo(PhysicsComponent.class, phys -> gob.forEachComponentDo(RevoluteJointComponent.class, joint  -> {
            if (InputHelper.isKeyPressed(Input.Keys.LEFT, Input.Keys.A)) {
                setLimits(joint, steering.maxAngleRads);
            } else if (InputHelper.isKeyPressed(Input.Keys.RIGHT, Input.Keys.D)) {
                setLimits(joint, -steering.maxAngleRads);
            } else {
                setLimits(joint, 0);
            }
        })));
    }

    private void setLimits(RevoluteJointComponent joint, float angle) {
        joint.join.setLimits(angle, angle);
    }

}

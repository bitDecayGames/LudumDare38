package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.ControlComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.InputHelper;
import com.bitdecay.game.util.VectorMath;

/**
 * This system is in charge
 */
public class ControlSystem extends AbstractForEachUpdatableSystem {


    public ControlSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(ControlComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(ControlComponent.class, cont -> gob.forEachComponentDo(PhysicsComponent.class, phys -> {
            float degrees = MathUtils.radiansToDegrees * phys.body.getAngle();
            Vector2 pos = phys.body.getPosition().cpy();
            Vector2 normal = VectorMath.rotatePointByDegreesAroundZero(0, 1, degrees);
            Vector2 speed = normal.cpy().scl(cont.speed);
            Vector2 front = pos.cpy().add(normal);
            Vector2 back = pos.cpy().sub(normal);
            Vector2 perp = VectorMath.rotatePointByDegreesAroundZero(-1, 0, degrees).scl(cont.angularSpeed);
            if (InputHelper.isKeyPressed(Input.Keys.UP, Input.Keys.W)) phys.body.applyLinearImpulse(speed, back, true);
            if (InputHelper.isKeyPressed(Input.Keys.DOWN, Input.Keys.S)) phys.body.applyLinearImpulse(speed.cpy().scl(-1), back, true);
            if (InputHelper.isKeyPressed(Input.Keys.LEFT, Input.Keys.A)) phys.body.applyTorque(cont.angularSpeed, true);
            if (InputHelper.isKeyPressed(Input.Keys.RIGHT, Input.Keys.D)) phys.body.applyTorque(-cont.angularSpeed, true);
        }));
    }

}

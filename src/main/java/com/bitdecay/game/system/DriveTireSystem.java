package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.component.DriveTireComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.InputHelper;
import com.bitdecay.game.util.VectorMath;

/**
 * Created by Monday on 4/22/2017.
 */
public class DriveTireSystem extends AbstractForEachUpdatableSystem {


    public DriveTireSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                DriveTireComponent.class,
                PhysicsComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(DriveTireComponent.class, drive -> gob.forEachComponentDo(PhysicsComponent.class, phys -> {
            if (!drive.hasPower) {
                return;
            }
            float degrees = MathUtils.radiansToDegrees * phys.body.getAngle();
            Vector2 pos = phys.body.getPosition().cpy();
            Vector2 normal = VectorMath.rotatePointByDegreesAroundZero(0, 1, degrees);
            Vector2 speed = normal.cpy().scl(drive.speed);
            Vector2 back = pos.cpy().sub(normal);
            if (InputHelper.isKeyPressed(Input.Keys.UP, Input.Keys.W)) phys.body.applyForce(speed, back, true);
            if (InputHelper.isKeyPressed(Input.Keys.DOWN, Input.Keys.S)) phys.body.applyForce(speed.cpy().scl(-1), back, true);
        }));
    }

}
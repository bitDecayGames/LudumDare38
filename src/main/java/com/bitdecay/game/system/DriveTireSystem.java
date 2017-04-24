package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.ai.AIControlComponent;
import com.bitdecay.game.component.DriveTireComponent;
import com.bitdecay.game.component.FuelComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.InputHelper;
import com.bitdecay.game.util.VectorMath;
import com.bitdecay.game.util.Xbox360Pad;

public class DriveTireSystem extends AbstractForEachUpdatableSystem {

    public DriveTireSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                DriveTireComponent.class,
                PhysicsComponent.class,
                FuelComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(FuelComponent.class, fuel -> gob.forEachComponentDo(DriveTireComponent.class, drive -> gob.forEachComponentDo(PhysicsComponent.class, phys -> {
            if (fuel.currentFuel <= 0) {
                return;
            }

            if (!drive.hasPower) {
                return;
            }

            float degrees = MathUtils.radiansToDegrees * phys.body.getAngle();
            Vector2 pos = phys.body.getPosition().cpy();
            Vector2 normal = VectorMath.rotatePointByDegreesAroundZero(0, 1, degrees);
            Vector2 back = pos.cpy().sub(normal);

            Vector2 rollingVelocity = getRollingVelocity(phys);
            float neededForce = drive.maxSpeed - rollingVelocity.len();
            neededForce *= drive.acceleration;
            Vector2 tirePowerVector = normal.scl(neededForce);

            AIControlComponent aiComp = AIControlComponent.get(gob);

            if ((playerUp() && aiComp == null) || (aiComp != null && aiComp.up())) {
                phys.body.applyForce(tirePowerVector, back, true);
                fuel.currentFuel-= fuel.fuelBurnRate * delta;
            }

            if ((playerDown() && aiComp == null) || (aiComp != null && aiComp.down())) {
                phys.body.applyForce(tirePowerVector.cpy().scl(-1), back, true);
                fuel.currentFuel-= fuel.fuelBurnRate * delta;
            }
        })));
    }

    private boolean playerUp() {
        return InputHelper.isKeyPressed(Input.Keys.UP, Input.Keys.W) || InputHelper.isButtonPressed(Xbox360Pad.A, Xbox360Pad.RT, Xbox360Pad.UP);
    }

    private boolean playerDown() {
        return InputHelper.isKeyPressed(Input.Keys.DOWN, Input.Keys.S) || InputHelper.isButtonPressed(Xbox360Pad.B, Xbox360Pad.LT, Xbox360Pad.DOWN);
    }

    private Vector2 getRollingVelocity(PhysicsComponent phys) {
        Vector2 currentRollingVector = phys.body.getWorldVector(new Vector2(0, 1));
        return currentRollingVector.scl(currentRollingVector.dot(phys.body.getLinearVelocity()));
    }
}
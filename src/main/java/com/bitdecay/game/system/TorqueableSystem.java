package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.bitdecay.game.ai.AIControlComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.TorqueableComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.InputHelper;
import com.bitdecay.game.util.Xbox360Pad;

/**
 * This system is in charge
 */
public class TorqueableSystem extends AbstractForEachUpdatableSystem {


    public TorqueableSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                TorqueableComponent.class,
                PhysicsComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(TorqueableComponent.class, tor -> gob.forEachComponentDo(PhysicsComponent.class, phys -> {
            AIControlComponent aiComp = AIControlComponent.get(gob);

            if ((playerLeft() && aiComp == null) || (aiComp != null && aiComp.left())) {
                torqueIt(tor.speed, phys);
            } else if ((playerRight() && aiComp == null) || (aiComp != null && aiComp.right())) {
                torqueIt(-tor.speed, phys);
            }
        }));
    }

    private boolean playerLeft() {
        return InputHelper.isKeyPressed(Input.Keys.LEFT, Input.Keys.A) || InputHelper.isButtonPressed(Xbox360Pad.DPAD_LEFT, Xbox360Pad.LEFT);
    }

    private boolean playerRight() {
        return InputHelper.isKeyPressed(Input.Keys.RIGHT, Input.Keys.D) || InputHelper.isButtonPressed(Xbox360Pad.DPAD_RIGHT, Xbox360Pad.RIGHT);
    }

    private void torqueIt(float torque, PhysicsComponent phys){
        phys.body.applyTorque(torque, true);
    }

}

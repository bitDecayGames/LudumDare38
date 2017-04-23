package com.bitdecay.game.system;

import com.badlogic.gdx.Input;
import com.bitdecay.game.component.DriveTireComponent;
import com.bitdecay.game.component.TireFrictionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.InputHelper;
import com.bitdecay.game.util.Xbox360Pad;

/**
 * Created by Monday on 4/22/2017.
 */
public class EBrakeSystem extends AbstractForEachUpdatableSystem {


    public EBrakeSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                DriveTireComponent.class,
                TireFrictionComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(DriveTireComponent.class, drive -> gob.forEachComponentDo(TireFrictionComponent.class, friction -> {
            boolean brakesEngaged = InputHelper.isKeyPressed(Input.Keys.SPACE) || InputHelper.isButtonPressed(Xbox360Pad.X);
            drive.hasPower = !brakesEngaged;
            friction.tireLocked = brakesEngaged;
        }));
    }
}
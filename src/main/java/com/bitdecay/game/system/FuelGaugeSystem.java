package com.bitdecay.game.system;

import com.bitdecay.game.component.FuelComponent;
import com.bitdecay.game.component.PlayerTireComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.ui.HUD;

/**
 * Created by Monday on 4/22/2017.
 */
public class FuelGaugeSystem extends AbstractForEachUpdatableSystem {

    public FuelGaugeSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                FuelComponent.class,
                PlayerTireComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(FuelComponent.class, fuel -> {
            HUD.instance().fuel.setPercent(fuel.currentFuel/fuel.maxFuel);
        });
    }
}
package com.bitdecay.game.room;

import com.bitdecay.game.component.FuelComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.ui.UIElements;

/**
 * Created by Monday on 4/22/2017.
 */
public class FuelGaugeSystem extends AbstractForEachUpdatableSystem {


    private UIElements uiElements;

    public FuelGaugeSystem(AbstractRoom room, UIElements uiElements) {
        super(room);

        this.uiElements = uiElements;
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                FuelComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(FuelComponent.class, fuel -> {
            uiElements.fuel.setPercent(fuel.currentFuel/fuel.maxFuel);
        });
    }
}

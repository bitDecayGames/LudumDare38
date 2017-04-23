package com.bitdecay.game.system;

import com.badlogic.gdx.math.MathUtils;
import com.bitdecay.game.component.HungerComponent;
import com.bitdecay.game.component.PlayerControlComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.ui.HUD;

/**
 * Created by Monday on 4/22/2017.
 */
public class HungerGaugeSystem extends AbstractForEachUpdatableSystem {

    public HungerGaugeSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                HungerComponent.class,
                PlayerControlComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(HungerComponent.class, hunger -> {
            hunger.currentFullness -= delta * hunger.digestionRate;
            hunger.currentFullness = MathUtils.clamp(hunger.currentFullness, 0, hunger.maxFullness);
            HUD.instance().body.setFoodLevel(hunger.currentFullness/hunger.maxFullness);
        });
    }
}
package com.bitdecay.game.system;

import com.badlogic.gdx.math.MathUtils;
import com.bitdecay.game.component.PlayerControlComponent;
import com.bitdecay.game.component.PoopooComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.ui.HUD;

/**
 * Created by Monday on 4/22/2017.
 */
public class PoopGaugeSystem extends AbstractForEachUpdatableSystem {

    public PoopGaugeSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                PoopooComponent.class,
                PlayerControlComponent.class
        );
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(PoopooComponent.class, poopoo -> {
            poopoo.currentPoopoo += delta * poopoo.poopooCreationRate;
            poopoo.currentPoopoo = MathUtils.clamp(poopoo.currentPoopoo, 0, poopoo.maxPoopoo);

            HUD.instance().body.setPoopLevel(poopoo.currentPoopoo/poopoo.maxPoopoo);
        });
    }
}
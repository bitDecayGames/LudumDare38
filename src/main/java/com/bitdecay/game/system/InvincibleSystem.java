package com.bitdecay.game.system;

import com.bitdecay.game.component.DrawableComponent;
import com.bitdecay.game.component.PlayerControlComponent;
import com.bitdecay.game.component.StaticImageComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Luke on 4/23/2017.
 */
public class InvincibleSystem extends AbstractForEachUpdatableSystem {

    public InvincibleSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
            gob.forEach(DrawableComponent.class, draw -> {

                    draw.color.r = 1;
                    draw.color.b = 0;
                    draw.color.g = 0;
            });
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                InvincibleComponent.class,
                PlayerControlComponent.class
        );
    }
}

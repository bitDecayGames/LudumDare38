package com.bitdecay.game.system;

import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.trait.IUpdate;

/**
 * Call update on any IUpdate components
 */
public class SimpleUpdateSystem extends AbstractForEachUpdatableSystem {
    public SimpleUpdateSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(IUpdate.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(IUpdate.class, up -> up.update(delta));
    }
}

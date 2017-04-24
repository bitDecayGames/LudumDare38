package com.bitdecay.game.system;

import com.bitdecay.game.component.FollowOtherPositionComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Monday on 4/23/2017.
 */
public class FollowPositionSystem extends AbstractForEachUpdatableSystem {
    public FollowPositionSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(FollowOtherPositionComponent.class, follow -> { gob.forEachComponentDo(PositionComponent.class, pos -> {
            pos.set(follow.pos.toVector2());
        });});
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(
                PositionComponent.class,
                FollowOtherPositionComponent.class);
    }
}

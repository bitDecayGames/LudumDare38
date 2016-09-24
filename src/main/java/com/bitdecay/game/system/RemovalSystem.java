package com.bitdecay.game.system;

import com.bitdecay.game.component.RemoveNowComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractSystem;
import com.bitdecay.game.trait.IRemovable;

/**
 * This system will remove objects that have the RemoveNowComponent and call the remove method on any components in that gob with the IRemoveable interface.
 */
public class RemovalSystem extends AbstractSystem {
    public RemovalSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        // logic goes here because this method is only called when gobs changes
        if (gob.hasComponent(RemoveNowComponent.class)){
            gob.forEach(IRemovable.class, removableComponent -> removableComponent.remove(room));
            room.getGameObjects().remove(gob);
        }
        return false;
    }
}

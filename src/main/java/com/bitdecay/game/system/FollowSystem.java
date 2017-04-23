package com.bitdecay.game.system;

import com.bitdecay.game.component.FollowComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

public class FollowSystem extends AbstractForEachUpdatableSystem{

    public FollowSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(FollowComponent.class, followComponent -> {
            gob.forEachComponentDo(PhysicsComponent.class, follower -> {
                if(followComponent.target != null){
                    followComponent.target.forEachComponentDo(PhysicsComponent.class, target -> {
                        if(target.body != null) {
                            follower.body.setTransform(target.body.getPosition().cpy(), target.body.getAngle());
                        }
                    });
                }
            });
        });
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(FollowComponent.class, PhysicsComponent.class);
    }
}

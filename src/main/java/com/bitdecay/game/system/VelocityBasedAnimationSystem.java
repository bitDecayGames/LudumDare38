package com.bitdecay.game.system;

import com.bitdecay.game.component.AnimatedImageComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.VelocityBasedAnimationSpeedComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bytebreakstudios.animagic.animation.FrameRate;

/**
 * Update the animation speed of an object based on it's velocity, just read the fucking name of the class
 */
public class VelocityBasedAnimationSystem extends AbstractForEachUpdatableSystem {

    public VelocityBasedAnimationSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PhysicsComponent.class, VelocityBasedAnimationSpeedComponent.class, AnimatedImageComponent.class);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEachComponentDo(PhysicsComponent.class, phy -> gob.forEachComponentDo(VelocityBasedAnimationSpeedComponent.class, velAnim -> gob.forEachComponentDo(AnimatedImageComponent.class, anim ->{
            float vel = phy.body.getLinearVelocity().cpy().scl(velAnim.speedScale).len();
            anim.animation.setFrameRate(FrameRate.perFrame(vel == 0 ? 0 : 1 / vel));
        })));
    }
}

package com.bitdecay.game.system;

import com.badlogic.gdx.physics.box2d.JointEdge;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;

/**
 * Created by Luke on 4/23/2017.
 */
public class DeathSystem extends AbstractForEachUpdatableSystem {

    public DeathSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    protected void forEach(float delta, MyGameObject gob) {
        gob.forEach(HealthComponent.class, health -> {
            if(health.isDead()){
               gob.addComponent(new RemoveNowComponent());
                for (JointEdge joint:gob.getComponent(PhysicsComponent.class).get().body.getJointList()) {
                    ((MyGameObject) joint.other.getUserData()).addComponent(new RemoveNowComponent());
                }

                PhysicsComponent phys = gob.getComponent(PhysicsComponent.class).get();
                GameObjectFactory.createCarCass(room.getGameObjects(),
                        phys.body.getWorld(),
                        phys.body.getPosition(),
                        phys.body.getAngle());
            }
        });
        gob.forEach(PoopooComponent.class, poop -> {
            if(poop.currentPoopoo >= poop.maxPoopoo){

                gob.removeComponent(PlayerControlComponent.class);
                gob.removeComponent(CameraFollowComponent.class);

                for (JointEdge joint:gob.getComponent(PhysicsComponent.class).get().body.getJointList()) {
                    MyGameObject other = ((MyGameObject) joint.other.getUserData());
                    other.removeComponent(PlayerControlComponent.class);
                    other.removeComponent(DriveTireComponent.class);
                    other.removeComponent(SteerableComponent.class);
                    other.removeComponent(CameraFollowComponent.class);
                    other.removeComponent(PlayerTireComponent.class);
                }
            }
        });
        gob.forEach(HungerComponent.class, hunger -> {
            if(hunger.currentFullness <= 0){

                gob.removeComponent(PlayerControlComponent.class);
                gob.removeComponent(CameraFollowComponent.class);

                for (JointEdge joint:gob.getComponent(PhysicsComponent.class).get().body.getJointList()) {
                    MyGameObject other = ((MyGameObject) joint.other.getUserData());
                    other.removeComponent(PlayerControlComponent.class);
                    other.removeComponent(DriveTireComponent.class);
                    other.removeComponent(SteerableComponent.class);
                    other.removeComponent(CameraFollowComponent.class);
                    other.removeComponent(PlayerTireComponent.class);
                }
            }
        });
        gob.forEach(FuelComponent.class, fuel ->{
            if(fuel.currentFuel <= 0) {
                gob.forEach(PhysicsComponent.class, phys -> {
                    if(phys.body.getLinearVelocity().len() <= 0.1f){
                        for (JointEdge joint:phys.body.getJointList()) {
                            ((MyGameObject) joint.other.getUserData()).addComponent(new RemoveNowComponent());
                        }
                    }
                });
            }
        });
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(HealthComponent.class)
                || gob.hasComponents(PoopooComponent.class)
                || gob.hasComponents(HungerComponent.class)
                || gob.hasComponents(FuelComponent.class);
    }
}

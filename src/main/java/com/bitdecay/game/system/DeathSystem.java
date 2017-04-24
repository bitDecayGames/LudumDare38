package com.bitdecay.game.system;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
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

                for (JointEdge joint:gob.getComponent(PhysicsComponent.class).get().body.getJointList()) {
                    ((MyGameObject) joint.other.getUserData()).removeComponent(DriveTireComponent.class);
                    ((MyGameObject) joint.other.getUserData()).removeComponent(SteerableComponent.class);
                }
            }
        });
        gob.forEach(HungerComponent.class, hunger -> {
            if(hunger.currentFullness <= 0){

                for (JointEdge joint:gob.getComponent(PhysicsComponent.class).get().body.getJointList()) {
                    ((MyGameObject) joint.other.getUserData()).removeComponent(DriveTireComponent.class);
                    ((MyGameObject) joint.other.getUserData()).removeComponent(SteerableComponent.class);
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

package com.bitdecay.game.system;

import com.badlogic.gdx.physics.box2d.JointEdge;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractForEachUpdatableSystem;
import com.bitdecay.game.util.SoundLibrary;

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
                        SoundLibrary.playSound("Explosion");
            }
        });
        gob.forEach(PoopooComponent.class, poop -> {
            if(poop.currentPoopoo >= poop.maxPoopoo){

                gob.removeComponent(PlayerControlComponent.class);
                gob.removeComponent(CameraFollowComponent.class);
                if(!poop.deathPoop){
                    SoundLibrary.playSound("DeathPoop");
                    poop.deathPoop = true;
                }

                for (JointEdge joint:gob.getComponent(PhysicsComponent.class).get().body.getJointList()) {
                    MyGameObject other = ((MyGameObject) joint.other.getUserData());
                    other.removeComponent(PlayerControlComponent.class);
                    other.removeComponent(DriveTireComponent.class);
                    other.removeComponent(SteerableComponent.class);
                    other.removeComponent(CameraFollowComponent.class);
                    other.removeComponent(PlayerTireComponent.class);
                }
            }
            if(poop.currentPoopoo/poop.maxPoopoo >= 0.85f && !poop.poopSoundPlayed){
                SoundLibrary.playSound("HighPoop");
                poop.poopSoundPlayed = true;
            }else if(poop.currentPoopoo/poop.maxPoopoo < 0.85f){
                poop.poopSoundPlayed = false;
            }
        });
        gob.forEach(HungerComponent.class, hunger -> {
            if(hunger.currentFullness <= 0){

                gob.removeComponent(PlayerControlComponent.class);
                gob.removeComponent(CameraFollowComponent.class);
                if(!hunger.starvingSound){

                    SoundLibrary.playSound("StarvingSound");
                    hunger.starvingSound = true;
                }

                for (JointEdge joint:gob.getComponent(PhysicsComponent.class).get().body.getJointList()) {
                    MyGameObject other = ((MyGameObject) joint.other.getUserData());
                    other.removeComponent(PlayerControlComponent.class);
                    other.removeComponent(DriveTireComponent.class);
                    other.removeComponent(SteerableComponent.class);
                    other.removeComponent(CameraFollowComponent.class);
                    other.removeComponent(PlayerTireComponent.class);
                }
            }
            if(hunger.currentFullness/hunger.maxFullness<= 0.15f && !hunger.hungerSoundPlayed){
                SoundLibrary.playSound("HungrySound");
                hunger.hungerSoundPlayed = true;
            }else if(hunger.currentFullness/hunger.maxFullness > 0.15f){
                hunger.hungerSoundPlayed = false;
            }
        });
        gob.forEach(FuelComponent.class, fuel ->{
            if(fuel.currentFuel <= 0) {
                if(!fuel.carStallSound){
                    SoundLibrary.playSound("CarStall");
                    fuel.carStallSound = true;
                }
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

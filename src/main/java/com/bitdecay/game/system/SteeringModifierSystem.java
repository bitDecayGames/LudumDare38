package com.bitdecay.game.system;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.util.ContactDistributer;

/**
 * Created by Luke on 4/22/2017.
 */
public class SteeringModifierSystem extends AbstractUpdatableSystem implements ContactListener {

    public SteeringModifierSystem(AbstractRoom room, ContactDistributer contactDistrib) {
        super(room);
        contactDistrib.listeners.add(this);
    }


    @Override
    public void beginContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();

        boolean objectAIsZone = objectA != null &&
                objectA.hasComponents(SteeringModifierComponent.class, PhysicsComponent.class);
        boolean objectBIsZone = objectB != null &&
                objectB.hasComponents(SteeringModifierComponent.class, PhysicsComponent.class);

        boolean objectAIsTire = objectA != null &&
                objectA.hasComponents(TireFrictionComponent.class, SteerableComponent.class, DriveTireComponent.class,PhysicsComponent.class);
        boolean objectBIsTire = objectB != null &&
                objectB.hasComponents(TireFrictionComponent.class, SteerableComponent.class, DriveTireComponent.class, PhysicsComponent.class);

        if (objectAIsZone && objectBIsTire) {
//            objectB.forEach(TireFrictionComponent.class, TFC -> TFC.);
//            objectB.forEach(DriveTireComponent.class, DTC -> SC.);
            System.out.println("entering: objectB is a tire");
        } else if (objectAIsTire && objectBIsZone) {
//            objectA.forEach(TireFrictionComponent.class, TFC -> TFC.);
//            objectA.forEach(DriveTireComponent.class, DTC -> SC.);
            System.out.println("entering: objectA is a tire");
        }
        System.out.println("exiting: objectA is a tire");
    }

    @Override
    public void endContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();

        boolean objectAIsZone = objectA != null &&
                objectA.hasComponents(SteeringModifierComponent.class, PhysicsComponent.class);
        boolean objectBIsZone = objectB != null &&
                objectB.hasComponents(SteeringModifierComponent.class, PhysicsComponent.class);

        boolean objectAIsTire = objectA != null &&
                objectA.hasComponents(TireFrictionComponent.class,DriveTireComponent.class, SteerableComponent.class,PhysicsComponent.class);
        boolean objectBIsTire = objectB != null &&
                objectB.hasComponents(TireFrictionComponent.class,DriveTireComponent.class, SteerableComponent.class, PhysicsComponent.class);

        if (objectAIsZone && objectBIsTire) {
//            objectB.forEach(TireFrictionComponent.class, TFC -> TFC.);
//            objectB.forEach(SteerableComponent.class, SC -> SC.);
            System.out.println("exiting: objectB is a tire");
        } else if (objectAIsTire && objectBIsZone) {
//            objectA.forEach(TireFrictionComponent.class, TFC -> TFC.);
//            objectA.forEach(SteerableComponent.class, SC -> SC.);
            System.out.println("exiting: objectA is a tire");
        }
        System.out.println("exiting: objectA is a tire");
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(SteeringModifierComponent.class);
    }
}

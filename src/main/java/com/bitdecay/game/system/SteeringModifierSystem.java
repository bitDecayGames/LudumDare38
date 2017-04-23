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
                objectA.hasComponents(SteeringModifierComponent.class);
        boolean objectBIsZone = objectB != null &&
                objectB.hasComponents(SteeringModifierComponent.class);

        boolean objectAIsTire = objectA != null &&
                objectA.hasComponents(TireFrictionComponent.class);
        boolean objectBIsTire = objectB != null &&
                objectB.hasComponents(TireFrictionComponent.class);

        if (objectAIsZone && objectBIsTire) {
            ModifiedSteeringComponent modifiedSteeringComp = new ModifiedSteeringComponent(objectA.getComponent(SteeringModifierComponent.class).get().modifiedFriction);
            objectB.addComponent(modifiedSteeringComp);
        } else if (objectAIsTire && objectBIsZone) {
            ModifiedSteeringComponent modifiedSteeringComp = new ModifiedSteeringComponent(objectB.getComponent(SteeringModifierComponent.class).get().modifiedFriction);
            objectA.addComponent(modifiedSteeringComp);
        }
//        System.out.println("exiting: objectA is a tire");
    }

    @Override
    public void endContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();

        boolean objectAIsZone = objectA != null &&
                objectA.hasComponents(SteeringModifierComponent.class);
        boolean objectBIsZone = objectB != null &&
                objectB.hasComponents(SteeringModifierComponent.class);

        boolean objectAIsTire = objectA != null &&
                objectA.hasComponents(TireFrictionComponent.class);
        boolean objectBIsTire = objectB != null &&
                objectB.hasComponents(TireFrictionComponent.class);

        if (objectAIsZone && objectBIsTire) {
            objectB.removeComponent(ModifiedSteeringComponent.class);
        } else if (objectAIsTire && objectBIsZone) {
            objectA.removeComponent(ModifiedSteeringComponent.class);
        }
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

package com.bitdecay.game.system;

import com.badlogic.gdx.physics.box2d.*;
import com.bitdecay.game.component.DamageComponent;
import com.bitdecay.game.component.HealthComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractSystem;
import com.bitdecay.game.util.ContactDistributer;

/**
 * Created by Luke on 4/22/2017.
 */
public class HealthSystem extends AbstractSystem implements ContactListener {

    public HealthSystem(AbstractRoom room, ContactDistributer contactDistrib) {
        super(room);
        contactDistrib.listeners.add(this);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return false;
    }

    @Override
    public void beginContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();
        if(objectA != null && objectB != null){
            applyDamage(objectA, objectB);
            applyDamage(objectB, objectA);

            //for testing only
//            objectA.forEachComponentDo(HealthComponent.class, health -> System.out.println(health.currentHealth));
//            objectB.forEachComponentDo(HealthComponent.class, health -> System.out.println(health.currentHealth));
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }

    public void applyDamage(MyGameObject attacker, MyGameObject attacked){
        attacker.forEach(DamageComponent.class, dmg -> attacked.forEach(HealthComponent.class, health -> health.set(health.currentHealth - dmg.damage)));
    }
}

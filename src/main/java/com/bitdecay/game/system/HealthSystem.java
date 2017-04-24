package com.bitdecay.game.system;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.*;
import com.bitdecay.game.component.DamageComponent;
import com.bitdecay.game.component.DrawableComponent;
import com.bitdecay.game.component.HealthComponent;
import com.bitdecay.game.component.TimerComponent;
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

            if(!objectB.hasComponent(InvincibleComponent.class)){
                applyDamage(objectA, objectB);
            }
            if(!objectA.hasComponent(InvincibleComponent.class)){
                applyDamage(objectB, objectA);
            }
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
        attacker.forEach(DamageComponent.class, dmg -> attacked.forEach(HealthComponent.class, health ->{

            health.set(health.currentHealth - dmg.damage);

            attacked.forEachComponentDo(DrawableComponent.class, drawable -> {
                float min = 0.3f;
                float grey = ((health.currentHealth / health.maxHealth) * (1 - min)) + min;

                drawable.color.set(grey, grey, grey, 1);
            });
            if(!attacked.hasComponent(InvincibleComponent.class) && !attacked.hasFreshComponent(InvincibleComponent.class)){
                InvincibleComponent i = new InvincibleComponent(attacked.getComponent(DrawableComponent.class).map((d)->d.color.cpy()).orElse(Color.WHITE.cpy()));
                attacked.addComponent(i);
                attacked.addComponent(new TimerComponent(2, ()->{

                    attacked.forEachComponentDo(InvincibleComponent.class, grace -> attacked.forEachComponentDo(DrawableComponent.class, draw -> {

                        draw.color = grace.origColor.cpy();
                    }));
                    attacked.removeComponentInstance(i);
                    attacked.removeComponent(TimerComponent.class);
                }));
            }

        }));
    }
}

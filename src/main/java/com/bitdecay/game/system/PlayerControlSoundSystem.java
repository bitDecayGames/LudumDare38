package com.bitdecay.game.system;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.bitdecay.game.component.PlayerControlComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractSystem;
import com.bitdecay.game.util.ContactDistributer;
import com.bitdecay.game.util.SoundLibrary;

/**
 * Created by Luke on 4/24/2017.
 */
public class PlayerControlSoundSystem extends AbstractSystem implements ContactListener {

    public PlayerControlSoundSystem(AbstractRoom room, ContactDistributer contactDistrib){
        super(room);
        contactDistrib.listeners.add(this);
    }

    @Override
    public void beginContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();
        MyGameObject player = null;
        log.info("inside begin contact");

        PlayerControlComponent playerComp = null;
        if(objectA != null && objectB != null) {
            if (objectA.hasComponent(PlayerControlComponent.class)) {
                player = objectA;
                log.info("found player");
                playerComp = player.getComponent(PlayerControlComponent.class).get();
            } else if (objectB.hasComponent(PlayerControlComponent.class)) {
                player = objectB;
                log.info("found player");
                playerComp = player.getComponent(PlayerControlComponent.class).get();
            }
            if (player != null && playerComp != null && playerComp.sound != null) {
                log.info("playing player sound");
                SoundLibrary.playSound(playerComp.sound);
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

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(PlayerControlComponent.class);
    }
}

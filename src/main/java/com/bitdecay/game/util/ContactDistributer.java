package com.bitdecay.game.util;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactDistributer implements ContactListener{
    public List<ContactListener> listeners = new ArrayList<>();
    protected Logger log = LogManager.getLogger(this.getClass());

    private Map<Long, Contact> ongoingContacts = new HashMap<>();

    @Override
    public void beginContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.beginContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (ContactListener listener : listeners) {
            listener.endContact(contact);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        for (ContactListener listener : listeners) {
            listener.preSolve(contact, manifold);
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        for (ContactListener listener : listeners) {
            listener.postSolve(contact, contactImpulse);
        }
    }
}

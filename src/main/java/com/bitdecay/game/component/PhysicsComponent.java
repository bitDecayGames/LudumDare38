package com.bitdecay.game.component;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IInitializable;
import com.bitdecay.game.trait.IRemovable;

/**
 * The component in charge of tracking the BitBody of the object
 */
public class PhysicsComponent extends AbstractComponent implements IRemovable {
    public Body body;

    public PhysicsComponent(Body body) {
        super();
        this.body = body;
    }

    @Override
    public void remove(AbstractRoom room) {
    }
}

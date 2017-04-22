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
public class PhysicsComponent extends AbstractComponent implements IInitializable, IRemovable {
    public Body body;
    public BodyDef bodyDef;
    public Shape shape;
    public FixtureDef fixtureDef;

    public PhysicsComponent(BodyDef bodyDef, Shape shape, FixtureDef fixtureDef) {
        super();
        this.bodyDef = bodyDef;
        this.shape = shape;
        this.fixtureDef = fixtureDef;
    }

    @Override
    public boolean isInitialized() {
        return body != null;
    }

    @Override
    public void initialize(AbstractRoom room) {
    }

    @Override
    public void remove(AbstractRoom room) {
    }
}

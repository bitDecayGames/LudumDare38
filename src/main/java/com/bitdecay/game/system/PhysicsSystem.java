package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;

/**
 * This system is in charge of updating the position and size component with data from the physics component
 */
public class PhysicsSystem extends AbstractUpdatableSystem {
    public World world;

    public PhysicsSystem(AbstractRoom room) {
        super(room);
        world = new World(Vector2.Zero, false);
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PhysicsComponent.class, PositionComponent.class);
    }

    @Override
    public void update(float delta) {
        world.step(delta, 1, 1);
        gobs.forEach(gob -> gob.forEach(PhysicsComponent.class, phy -> {
            if (!phy.isInitialized()) {
                Body body = world.createBody(phy.bodyDef);
                body.createFixture(phy.fixtureDef);
                phy.body = body;
            }
            gob.forEachComponentDo(PositionComponent.class, pos -> pos.set(phy.body.getPosition()));
        }));
    }
}

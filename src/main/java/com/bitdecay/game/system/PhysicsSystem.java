package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.component.RotationComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;

/**
 * This system is in charge of updating the position and size component with data from the physics component
 */

public class PhysicsSystem extends AbstractUpdatableSystem {
    private static PhysicsSystem I;
    public static PhysicsSystem instance(){
        return I;
    }

    public World world;
    private boolean DEBUG = false;
    public Box2DDebugRenderer debugger = null;

    public PhysicsSystem(AbstractRoom room) {
        super(room);
        I = this;
        world = new World(Vector2.Zero, false);
        debugger = new Box2DDebugRenderer();
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponents(PhysicsComponent.class);
    }

    @Override
    public void update(float delta) {
        world.step(delta, 1, 1);
        gobs.forEach(gob -> gob.forEach(PhysicsComponent.class, phy -> {
            gob.forEachComponentDo(PositionComponent.class, pos -> pos.set(phy.body.getPosition()));
            gob.forEachComponentDo(RotationComponent.class, rot -> rot.set(phy.body.getAngle()));
        }));
        if (debugger != null && DEBUG) debugger.render(world, room.camera.combined);
    }
}

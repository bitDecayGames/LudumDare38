package com.bitdecay.game.room;


import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.bitdecay.game.component.CameraFollowComponent;
import com.bitdecay.game.component.ControlComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.PositionComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    PhysicsSystem phys = null;
    Box2DDebugRenderer debug = null;

    public DemoRoom(GameScreen gameScreen) {
        super(gameScreen);

        debug = new Box2DDebugRenderer();

        // systems must be added before game objects
        new InitializationSystem(this);
        phys = new PhysicsSystem(this);
        new ControlSystem(this);
        new TimerSystem(this);
        new CameraUpdateSystem(this);
        new RespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new ShapeDrawSystem(this);
        new DrawSystem(this);
        new RemovalSystem(this);

        MyGameObject o = new MyGameObject();
        o.addComponent(new PositionComponent(0, 0));
        o.addComponent(makePhysicsThing(0, 0));
        o.addComponent(new CameraFollowComponent());
        o.addComponent(new ControlComponent(1.0f, 50, 1));
        gobs.add(o);

        for (int x = -10; x < 20; x += 1) for (int y = -10; y < 20; y += 1) {
            gobs.add(makeRectThing(x * 4, y * 8));
        }

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        debug.render(phys.world, camera.combined);
    }

    public MyGameObject makeRectThing(float x, float y){
        return (new MyGameObject()).addComponent(new PositionComponent(0, 0)).addComponent(makePhysicsThing(x, y));
    }

    public PhysicsComponent makePhysicsThing(float x, float y){
        BodyDef carBodyDef = new BodyDef();
        carBodyDef.type = BodyDef.BodyType.DynamicBody;
        carBodyDef.position.set(x, y);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.5f, 1);

        FixtureDef carFixtureDef = new FixtureDef();
        carFixtureDef.shape = shape;
        carFixtureDef.friction = 0.5f;
        carFixtureDef.density = 10;

        return new PhysicsComponent(carBodyDef, shape, carFixtureDef);
    }
}

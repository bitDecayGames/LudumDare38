package com.bitdecay.game.room;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.screen.GameScreen;

/**
 * Created by Monday on 4/21/2017.
 */
public class GameRoom extends AbstractRoom {
    public GameRoom(GameScreen gameScreen) {
        super(gameScreen);

        // for some reason we have to initialize a world before we make shapes, or it will crash
        World world = new World(Vector2.Zero, true);

        BodyDef carBodyDef = new BodyDef();
        carBodyDef.type = BodyDef.BodyType.DynamicBody;
        carBodyDef.position.set(0,0);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.5f, 1);

        FixtureDef carFixtureDef = new FixtureDef();
        carFixtureDef.shape = shape;
        carFixtureDef.friction = 0.5f;

        PhysicsComponent phys = new PhysicsComponent(carBodyDef, shape, carFixtureDef);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }
}

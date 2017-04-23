package com.bitdecay.game.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bitdecay.game.component.*;
import com.bitdecay.game.system.PhysicsSystem;

public class StaticGameObjectFactory {
    public static MyGameObject create(PhysicsSystem phys, Vector2 pos, Vector2 size, int damage){
        MyGameObject gameObject  = new MyGameObject();

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + size.x/2, pos.y + size.y/2);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = phys.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(size.x, size.y);

        FixtureDef carFixtureDef = new FixtureDef();
        carFixtureDef.shape = shape;

        body.createFixture(carFixtureDef);
        PhysicsComponent physComp = new PhysicsComponent(body);

        gameObject.addComponent(physComp);
        gameObject.addComponent(new PositionComponent(pos.x, pos.y));
        gameObject.addComponent(new OriginComponent(0.5f, 0.5f));
        gameObject.addComponent(new RotationComponent(0));
        gameObject.addComponent(new SizeComponent(size.x, size.y));
        gameObject.addComponent(new DamageComponent(damage));

        return gameObject;
    }
}

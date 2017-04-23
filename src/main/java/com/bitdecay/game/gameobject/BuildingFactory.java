package com.bitdecay.game.gameobject;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bitdecay.game.component.*;
import com.bitdecay.game.system.PhysicsSystem;

public class BuildingFactory {
    public static MyGameObject autoShop(PhysicsSystem phys, Vector2 pos) {
        return makeBuilding(phys, pos, new Vector2(2,2), "collidables/trash_lid");
    }

    public static MyGameObject makeBuilding(PhysicsSystem phys, Vector2 pos, Vector2 size, String imageName){
        MyGameObject building  = new MyGameObject();

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x, pos.y);
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = phys.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        Vector2 box2dSize = (new Vector2(size)).scl(0.5f);
        shape.setAsBox(box2dSize.x, box2dSize.y);

        FixtureDef carFixtureDef = new FixtureDef();
        carFixtureDef.shape = shape;

        body.createFixture(carFixtureDef);
        PhysicsComponent physComp = new PhysicsComponent(body);

        building.addComponent(physComp);
        building.addComponent(new PositionComponent(pos.x, pos.y));
        building.addComponent(new OriginComponent(0.5f, 0.5f));
        building.addComponent(new RotationComponent(0));
        building.addComponent(new StaticImageComponent(imageName));
        building.addComponent(new SizeComponent(size.x, size.y));

        return building;
    }
}

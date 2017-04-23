package com.bitdecay.game.gameobject;

import com.badlogic.gdx.physics.box2d.*;
import com.bitdecay.game.component.*;
import com.bitdecay.game.system.PhysicsSystem;

/**
 * Created by Monday on 4/22/2017.
 */
public class GameObjectFactory {

    public static MyGameObject makeTrashBin(PhysicsSystem phys,float x, float y){

        MyGameObject trashBin  = new MyGameObject();

        BodyDef trashBinBodyDef = new BodyDef();
        trashBinBodyDef.position.set(x,y);
        trashBinBodyDef.type = BodyDef.BodyType.DynamicBody;
        trashBinBodyDef.linearDamping = 2;
        trashBinBodyDef.angularDamping = 1;
        Body trashBinBody = phys.world.createBody(trashBinBodyDef);

        CircleShape trashBinShape = new CircleShape();
        trashBinShape.setRadius(.75f);

        Fixture trashBinFix = trashBinBody.createFixture(trashBinShape,.25f);

        PhysicsComponent physComp = new PhysicsComponent(trashBinBody);
        trashBin.addComponent(physComp);
        trashBin.addComponent(new PositionComponent(x,y));
        trashBin.addComponent(new OriginComponent(.5f,.5f));
        trashBin.addComponent(new RotationComponent(0));
        trashBin.addComponent(new StaticImageComponent("collidables/trash_lid"));
        trashBin.addComponent(new SizeComponent(1.5f,1.5f));
        trashBin.addComponent(new BreakableObjectComponent("collidables/trash_flying", 1, 1.5f,1.5f));


        return trashBin;
    }

    public static MyGameObject makeDumpster(PhysicsSystem phys,float x, float y){
        MyGameObject dumpster  = new MyGameObject();

        BodyDef dumpsterBodyDef = new BodyDef();
        dumpsterBodyDef.position.set(x,y);
        dumpsterBodyDef.type = BodyDef.BodyType.DynamicBody;
        dumpsterBodyDef.linearDamping = 5;
        dumpsterBodyDef.angularDamping = 2.5f;
        Body dumpsterBody = phys.world.createBody(dumpsterBodyDef);

        PolygonShape dumpsterShape = new PolygonShape();
        dumpsterShape.setAsBox(1.65f,1.2f);

        Fixture dumpsterFix = dumpsterBody.createFixture(dumpsterShape,3);

        PhysicsComponent physComp = new PhysicsComponent(dumpsterBody);
        dumpster.addComponent(physComp);
        dumpster.addComponent(new PositionComponent(x,y));
        dumpster.addComponent(new OriginComponent(.5f,.5f));
        dumpster.addComponent(new RotationComponent(0));
        dumpster.addComponent(new StaticImageComponent("collidables/dumpster"));
        dumpster.addComponent(new SizeComponent(3.3f,2.4f));

        return dumpster;
    }

    public static MyGameObject makeCart(PhysicsSystem phys,float x, float y){
        MyGameObject cart  = new MyGameObject();

        BodyDef cartBodyDef = new BodyDef();
        cartBodyDef.position.set(x,y);
        cartBodyDef.type = BodyDef.BodyType.DynamicBody;
        cartBodyDef.linearDamping = .75f;
        cartBodyDef.angularDamping = .75f;
        Body cartBody = phys.world.createBody(cartBodyDef);

        PolygonShape cartShape = new PolygonShape();
        cartShape.setAsBox(.8f,.5f);

        Fixture cartFix = cartBody.createFixture(cartShape,.5f);

        PhysicsComponent physComp = new PhysicsComponent(cartBody);
        cart.addComponent(physComp);
        cart.addComponent(new PositionComponent(x,y));
        cart.addComponent(new OriginComponent(.5f,.5f));
        cart.addComponent(new RotationComponent(0));
        cart.addComponent(new StaticImageComponent("collidables/cart"));
        cart.addComponent(new SizeComponent(1.6f,1));

        return cart;
    }

    public static MyGameObject makeToilet(PhysicsSystem phys,float x, float y){
        MyGameObject toilet  = new MyGameObject();

        BodyDef toiletBodyDef = new BodyDef();
        toiletBodyDef.position.set(x,y);
        toiletBodyDef.type = BodyDef.BodyType.DynamicBody;
        toiletBodyDef.linearDamping = 3;
        toiletBodyDef.angularDamping = 3;
        Body toiletBody = phys.world.createBody(toiletBodyDef);

        PolygonShape toiletShape = new PolygonShape();
        toiletShape.setAsBox(.8f,.9f);

        Fixture toiletFix = toiletBody.createFixture(toiletShape,3);

        PhysicsComponent physComp = new PhysicsComponent(toiletBody);
        toilet.addComponent(physComp);
        toilet.addComponent(new PositionComponent(x,y));
        toilet.addComponent(new OriginComponent(.5f,.5f));
        toilet.addComponent(new RotationComponent(0));
        toilet.addComponent(new StaticImageComponent("collidables/toilet"));
        toilet.addComponent(new SizeComponent(1.9f,1.8f));
        toilet.addComponent(new BreakableObjectComponent("collidables/toilet_flying", 1, 1.6f,2.9f));

        return toilet;
    }

    public static MyGameObject makeTrashBag(PhysicsSystem phys,float x, float y){

        MyGameObject trashBag  = new MyGameObject();

        BodyDef trashBagBodyDef = new BodyDef();
        trashBagBodyDef.position.set(x,y);
        trashBagBodyDef.type = BodyDef.BodyType.DynamicBody;
        trashBagBodyDef.linearDamping = 10;
        trashBagBodyDef.angularDamping = 1;
        Body trashBagBody = phys.world.createBody(trashBagBodyDef);

        CircleShape trashBagShape = new CircleShape();
        trashBagShape.setRadius(.6f);

        Fixture trashBagFix = trashBagBody.createFixture(trashBagShape,.05f);

        PhysicsComponent physComp = new PhysicsComponent(trashBagBody);
        trashBag.addComponent(physComp);
        trashBag.addComponent(new PositionComponent(x,y));
        trashBag.addComponent(new OriginComponent(.5f,.5f));
        trashBag.addComponent(new RotationComponent(0));
        trashBag.addComponent(new StaticImageComponent("collidables/trashbag"));
        trashBag.addComponent(new SizeComponent(1.2f,1.2f));
        trashBag.addComponent(new BreakableObjectComponent("collidables/trashbag_flying", 1, 1.2f,1.8f));


        return trashBag;
    }

    public static MyGameObject makeFirehydrant(PhysicsSystem phys,float x, float y){
        MyGameObject hydrant  = new MyGameObject();

        BodyDef hydrantBodyDef = new BodyDef();
        hydrantBodyDef.position.set(x,y);
        hydrantBodyDef.type = BodyDef.BodyType.DynamicBody;
        hydrantBodyDef.linearDamping = .5f;
        hydrantBodyDef.angularDamping = 3;
        Body hydrantBody = phys.world.createBody(hydrantBodyDef);

        CircleShape hydrantShape = new CircleShape();
        hydrantShape.setRadius(.3f);

        Fixture hydrantFix = hydrantBody.createFixture(hydrantShape,35f);

        PhysicsComponent physComp = new PhysicsComponent(hydrantBody);
        hydrant.addComponent(physComp);
        hydrant.addComponent(new PositionComponent(x,y));
        hydrant.addComponent(new OriginComponent(.5f,.5f));
        hydrant.addComponent(new RotationComponent(0));
        hydrant.addComponent(new StaticImageComponent("collidables/hydrant"));
        hydrant.addComponent(new DamageComponent(2));
        hydrant.addComponent(new SizeComponent(.6f,.6f));
        hydrant.addComponent(new BreakableObjectComponent("collidables/hydrant_flying", 2, .6f, 0.9f));

        return hydrant;
    }

}

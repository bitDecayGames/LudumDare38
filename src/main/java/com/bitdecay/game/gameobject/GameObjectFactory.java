package com.bitdecay.game.gameobject;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.bitdecay.game.component.*;
import com.bitdecay.game.component.money.MoneyComponent;
import com.bitdecay.game.physics.FrictionDataFactory;
import com.bitdecay.game.physics.TireFrictionData;
import com.bitdecay.game.system.PhysicsSystem;
import com.bitdecay.game.util.CarType;
import com.bitdecay.game.util.ZoneType;

import java.util.function.Consumer;

public class GameObjectFactory {

    public static MyGameObject makeTrashBin(PhysicsSystem phys, float x, float y) {

        MyGameObject trashBin = new MyGameObject();

        BodyDef trashBinBodyDef = new BodyDef();
        trashBinBodyDef.position.set(x, y);
        trashBinBodyDef.type = BodyDef.BodyType.DynamicBody;
        trashBinBodyDef.linearDamping = 2;
        trashBinBodyDef.angularDamping = 1;
        Body trashBinBody = phys.world.createBody(trashBinBodyDef);

        CircleShape trashBinShape = new CircleShape();
        trashBinShape.setRadius(.75f);

        Fixture trashBinFix = trashBinBody.createFixture(trashBinShape, .25f);

        PhysicsComponent physComp = new PhysicsComponent(trashBinBody);
        trashBin.addComponent(physComp);
        trashBin.addComponent(new PositionComponent(x, y));
        trashBin.addComponent(new OriginComponent(.5f, .5f));
        trashBin.addComponent(new RotationComponent(0));
        trashBin.addComponent(new StaticImageComponent("collidables/trash_lid"));
        trashBin.addComponent(new SizeComponent(1.5f, 1.5f));
        trashBin.addComponent(new BreakableObjectComponent("collidables/trash_flying", 3, 1.5f, 1.5f, ParticleFactory.ParticleChoice.TRASH));

        return trashBin;
    }

    public static MyGameObject makeDumpster(PhysicsSystem phys, float x, float y) {
        MyGameObject dumpster = new MyGameObject();

        BodyDef dumpsterBodyDef = new BodyDef();
        dumpsterBodyDef.position.set(x, y);
        dumpsterBodyDef.type = BodyDef.BodyType.DynamicBody;
        dumpsterBodyDef.linearDamping = 5;
        dumpsterBodyDef.angularDamping = 2.5f;
        Body dumpsterBody = phys.world.createBody(dumpsterBodyDef);

        PolygonShape dumpsterShape = new PolygonShape();
        dumpsterShape.setAsBox(1.65f, 1.2f);

        Fixture dumpsterFix = dumpsterBody.createFixture(dumpsterShape, 3);

        PhysicsComponent physComp = new PhysicsComponent(dumpsterBody);
        dumpster.addComponent(physComp);
        dumpster.addComponent(new PositionComponent(x, y));
        dumpster.addComponent(new OriginComponent(.5f, .5f));
        dumpster.addComponent(new RotationComponent(0));
        dumpster.addComponent(new StaticImageComponent("collidables/dumpster"));
        dumpster.addComponent(new SizeComponent(3.3f, 2.4f));

        return dumpster;
    }

    public static MyGameObject makeCart(PhysicsSystem phys, float x, float y) {
        MyGameObject cart = new MyGameObject();

        BodyDef cartBodyDef = new BodyDef();
        cartBodyDef.position.set(x, y);
        cartBodyDef.type = BodyDef.BodyType.DynamicBody;
        cartBodyDef.linearDamping = .75f;
        cartBodyDef.angularDamping = .75f;
        Body cartBody = phys.world.createBody(cartBodyDef);

        PolygonShape cartShape = new PolygonShape();
        cartShape.setAsBox(.5f, .8f);

        Fixture cartFix = cartBody.createFixture(cartShape, .5f);

        PhysicsComponent physComp = new PhysicsComponent(cartBody);
        cart.addComponent(physComp);
        cart.addComponent(new PositionComponent(x, y));
        cart.addComponent(new OriginComponent(.5f, .5f));
        cart.addComponent(new RotationComponent(0));
        cart.addComponent(new StaticImageComponent("collidables/cart"));
        cart.addComponent(new SizeComponent(1, 1.6f));
        cart.addComponent(new TireFrictionComponent(FrictionDataFactory.getCartFriction()));

        return cart;
    }

    public static MyGameObject makeToilet(PhysicsSystem phys, float x, float y) {
        MyGameObject toilet = new MyGameObject();

        BodyDef toiletBodyDef = new BodyDef();
        toiletBodyDef.position.set(x, y);
        toiletBodyDef.type = BodyDef.BodyType.DynamicBody;
        toiletBodyDef.linearDamping = 3;
        toiletBodyDef.angularDamping = 3;
        Body toiletBody = phys.world.createBody(toiletBodyDef);

        PolygonShape toiletShape = new PolygonShape();
        toiletShape.setAsBox(.8f, .9f);

        Fixture toiletFix = toiletBody.createFixture(toiletShape, 3);

        PhysicsComponent physComp = new PhysicsComponent(toiletBody);
        toilet.addComponent(physComp);
        toilet.addComponent(new PositionComponent(x, y));
        toilet.addComponent(new OriginComponent(.5f, .5f));
        toilet.addComponent(new RotationComponent(0));
        toilet.addComponent(new StaticImageComponent("collidables/toilet"));
        toilet.addComponent(new SizeComponent(1.9f, 1.8f));
        toilet.addComponent(new BreakableObjectComponent("collidables/toilet_flying", 1, 1.6f, 2.9f, ParticleFactory.ParticleChoice.POOP));

        return toilet;
    }

    public static MyGameObject makeTrashBag(PhysicsSystem phys, float x, float y) {

        MyGameObject trashBag = new MyGameObject();

        BodyDef trashBagBodyDef = new BodyDef();
        trashBagBodyDef.position.set(x, y);
        trashBagBodyDef.type = BodyDef.BodyType.DynamicBody;
        trashBagBodyDef.linearDamping = 10;
        trashBagBodyDef.angularDamping = 1;
        Body trashBagBody = phys.world.createBody(trashBagBodyDef);

        CircleShape trashBagShape = new CircleShape();
        trashBagShape.setRadius(.6f);

        Fixture trashBagFix = trashBagBody.createFixture(trashBagShape, .05f);

        PhysicsComponent physComp = new PhysicsComponent(trashBagBody);
        trashBag.addComponent(physComp);
        trashBag.addComponent(new PositionComponent(x, y));
        trashBag.addComponent(new OriginComponent(.5f, .5f));
        trashBag.addComponent(new RotationComponent(0));
        trashBag.addComponent(new StaticImageComponent("collidables/trashbag"));
        trashBag.addComponent(new SizeComponent(1.2f, 1.2f));
        trashBag.addComponent(new BreakableObjectComponent("collidables/trashbag_flying", 1, 1.2f, 1.8f, ParticleFactory.ParticleChoice.TRASH));


        return trashBag;
    }

    public static MyGameObject makeFirehydrant(PhysicsSystem phys, float x, float y) {
        MyGameObject hydrant = new MyGameObject();

        BodyDef hydrantBodyDef = new BodyDef();
        hydrantBodyDef.position.set(x, y);
        hydrantBodyDef.type = BodyDef.BodyType.DynamicBody;
        hydrantBodyDef.linearDamping = .5f;
        hydrantBodyDef.angularDamping = 3;
        Body hydrantBody = phys.world.createBody(hydrantBodyDef);

        CircleShape hydrantShape = new CircleShape();
        hydrantShape.setRadius(.3f);

        Fixture hydrantFix = hydrantBody.createFixture(hydrantShape, 35f);

        PhysicsComponent physComp = new PhysicsComponent(hydrantBody);
        hydrant.addComponent(physComp);
        hydrant.addComponent(new PositionComponent(x, y));
        hydrant.addComponent(new OriginComponent(.5f, .5f));
        hydrant.addComponent(new RotationComponent(0));
        hydrant.addComponent(new StaticImageComponent("collidables/hydrant"));
        hydrant.addComponent(new DamageComponent(2));
        hydrant.addComponent(new SizeComponent(.6f, .6f));
        hydrant.addComponent(new BreakableObjectComponent("collidables/hydrant_flying", 0, .6f, 0.9f, ParticleFactory.ParticleChoice.WATER));

        return hydrant;
    }

    public static MyGameObject makeMailbox(PhysicsSystem phys, float x, float y) {
        MyGameObject mailbox = new MyGameObject();

        BodyDef mailboxBodyDef = new BodyDef();
        mailboxBodyDef.position.set(x, y);
        mailboxBodyDef.type = BodyDef.BodyType.DynamicBody;
        mailboxBodyDef.linearDamping = 3f;
        mailboxBodyDef.angularDamping = 3;
        Body mailboxBody = phys.world.createBody(mailboxBodyDef);

        PolygonShape mailboxShape = new PolygonShape();
        mailboxShape.setAsBox(.4f, .4f);

        Fixture mailboxFix = mailboxBody.createFixture(mailboxShape, 15);

        PhysicsComponent physComp = new PhysicsComponent(mailboxBody);
        mailbox.addComponent(physComp);
        mailbox.addComponent(new PositionComponent(x, y));
        mailbox.addComponent(new OriginComponent(.5f, .5f));
        mailbox.addComponent(new RotationComponent(0));
        mailbox.addComponent(new StaticImageComponent("collidables/mailbox"));
        mailbox.addComponent(new SizeComponent(.8f, .8f));
        mailbox.addComponent(new BreakableObjectComponent("collidables/mailbox_flying", 1, .85f, 1.2f, ParticleFactory.ParticleChoice.MAIL));

        return mailbox;
    }

    public static MyGameObject makePerson(PhysicsSystem phys, float x, float y) {
        MyGameObject obj = new MyGameObject();

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.linearDamping = .5f;
        bodyDef.angularDamping = 3;
        Body body = phys.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(.5f, .5f);

        body.createFixture(shape, 35f);

        PhysicsComponent physComp = new PhysicsComponent(body);
        obj.addComponent(physComp);
        obj.addComponent(new PositionComponent(x, y));
        obj.addComponent(new OriginComponent(.5f, .5f));
        obj.addComponent(new RotationComponent(0));
        AnimatedImageComponent slow = new AnimatedImageComponent("person/walk", 0f);
        obj.addComponent(slow);
        AnimatedImageComponent fast = new AnimatedImageComponent("person/run", 0f);
        fast.scaleY = 1.5f;
        obj.addComponent(fast);
        obj.addComponent(new MultiTieredVelocityAnimationComponent(3, 10, slow, fast));
        obj.addComponent(new VelocityBasedAnimationSpeedComponent(5f));
        obj.addComponent(new DriveTireComponent(25, 5));
        obj.addComponent(new TorqueableComponent(30));
        obj.addComponent(new FuelComponent(1, 0));
        obj.addComponent(new SizeComponent(1f, 1f));
        obj.addComponent(new BreakableObjectComponent("person/flyForward", 2, 1f, 1.5f, ParticleFactory.ParticleChoice.BLOOD));

        return obj;
    }

    public static MyGameObject makeGrassField(PhysicsSystem phys, float x, float y) {
        MyGameObject field = new MyGameObject();

        BodyDef fieldBodyDef = new BodyDef();
        fieldBodyDef.position.set(x, y);
        fieldBodyDef.type = BodyDef.BodyType.StaticBody;
        Body fieldBody = phys.world.createBody(fieldBodyDef);

        PolygonShape fieldShape = new PolygonShape();
        fieldShape.setAsBox(20f, 20);

        Fixture fieldFix = fieldBody.createFixture(fieldShape, 0);
        fieldFix.setSensor(true);

        PhysicsComponent physComp = new PhysicsComponent(fieldBody);
        physComp.body.setUserData(field);
        field.addComponent(physComp);
        field.addComponent(new PositionComponent(x, y));
        field.addComponent(new OriginComponent(.5f, .5f));

        field.addComponent(new TireFrictionModifierComponent(FrictionDataFactory.getGrassFriction()));
//        field.addComponent(new StaticImageComponent("collidables/dumpster"));
//        field.addComponent(new SizeComponent(25, 10));

        return field;
    }

    private static void addZoneComponent(MyGameObject zone, Consumer<MyGameObject> modifyGameObj) {
        ZoneComponent zComp = new ZoneComponent(10.0f, (gameObj) -> {
            zone.getComponent(ZoneComponent.class).get().active = false;
            zone.addComponent(new TimerComponent(5, () -> {
                zone.getComponent(ZoneComponent.class).get().active = true;
                zone.removeComponent(TimerComponent.class);
            }));
            modifyGameObj.accept(gameObj);
        });
        zComp.active = true;
        zComp.canDeactivate = false;
        zone.addComponent(zComp);
    }

    public static void createZone(MyGameObjects gobs, PhysicsSystem phys, float x, float y, float width, float length, float rotation, ZoneType zoneType) {
        MyGameObject zone = new MyGameObject();

        BodyDef zoneBodyDef = new BodyDef();
        zoneBodyDef.type = BodyDef.BodyType.StaticBody;
        zoneBodyDef.position.set(x, y);
        Body zoneBody = phys.world.createBody(zoneBodyDef);

        PositionComponent zonePos = new PositionComponent(x, y);
        zone.addComponent(zonePos);

        OriginComponent zoneOrigin = new OriginComponent(.5f, .5f);
        zone.addComponent(zoneOrigin);

        StaticImageComponent zoneImage = new StaticImageComponent("target");
        zone.addComponent(zoneImage);

        SizeComponent zoneSize = new SizeComponent(width, length);
        zone.addComponent(zoneSize);

        WaypointComponent waypoint = new WaypointComponent(zoneType);
        zone.addComponent(waypoint);

        PhysicsComponent zonePhys = new PhysicsComponent(zoneBody);
        zonePhys.body.setUserData(zone);
        zone.addComponent(zonePhys);

        PolygonShape zoneShape = new PolygonShape();
        zoneShape.setAsBox(width / 2, length / 2);

        Fixture zoneFix = zoneBody.createFixture(zoneShape, 0);
        zoneFix.setSensor(true);

        switch (zoneType) {
            case BATHROOM:
                addZoneComponent(zone, (gameObj) -> {
                    gameObj.forEachComponentDo(PoopooComponent.class, poo -> poo.currentPoopoo = 0);
                });
                break;
            case FOOD:
                addZoneComponent(zone, (gameObj) -> {
                    gameObj.forEachComponentDo(HungerComponent.class, hungry -> hungry.currentFullness = hungry.maxFullness);
                });
                break;
            case FUEL:
                addZoneComponent(zone, (gameObj) -> {
                    gameObj.forEachComponentDo(FuelComponent.class, fuel -> fuel.currentFuel = fuel.maxFuel);
                });
                break;
            case REPAIR:
            case OBJECTIVE:
            case PICKUP:
                break;
            default:
        }

        gobs.add(zone);
    }

    public static void createCar(MyGameObjects gobs, PhysicsSystem phys, Vector2 pos, CarType type, boolean addWayPoint) {

        float carWidth = 2;
        float carHeight = 4;
        float halfWidth = carWidth / 2;
        float halfHeight = carHeight / 2;

        float tireWidth = carWidth / 8;
        float tireHeight = carWidth / 3;

        // create our car
        BodyDef carBodyDef = new BodyDef();
        carBodyDef.type = BodyDef.BodyType.DynamicBody;
        carBodyDef.position.set(pos.x, pos.y);

        Body carBody = phys.world.createBody(carBodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth, halfHeight);
        shape.set(new float[]{
                -1, -2,
                -1, 1.8f,
                -.7f, 1.9f,
                -.2f, 2,
                .2f, 2,
                .7f, 1.9f,
                1, 1.8f,
                1, -2

        });

        FixtureDef carFixtureDef = new FixtureDef();
        carFixtureDef.shape = shape;
        carFixtureDef.friction = 0f;
        carFixtureDef.density = 1;

        carBody.createFixture(carFixtureDef);

        // create car entity
        MyGameObject car = new MyGameObject();
        car.addComponent(new PositionComponent(0, 0));
        PhysicsComponent carPhysics = new PhysicsComponent(carBody);
        car.addComponent(carPhysics);
        carPhysics.body.setUserData(car);

        car.addComponent(ParticleFactory.getExhaustParticle());
        car.addComponent(new ParticlePosition(.5f, -2));

        // Car type section
        int health;
        String imageName;

        switch (type) {
            case PLAYER:
                health = 10;
                imageName = "player/taxi/taxi";
                // Add camera and other stats
                car.addComponent(new CameraFollowComponent());
                car.addComponent(new PlayerControlComponent());
                car.addComponent(new HungerComponent(100, 0.5f));
                car.addComponent(new PoopooComponent(100, 0.5f));
                car.addComponent(new MoneyComponent(100));
                break;
            case COP:
                health = 20;
                imageName = "cop/cop";
                break;
            case NPC:
                health = 2;
                imageName = "player/taxi/taxi";
                break;
            default:
                health = 1;
                imageName = "player/taxi/taxi";
        }
        car.addComponent(new HealthComponent(health));
        car.addComponent(new StaticImageComponent(imageName));

        //car damage section
        DamageComponent carDamage = new DamageComponent(2);
        car.addComponent(carDamage);

        //waypoint section
        if (addWayPoint) car.addComponent(new WaypointComponent(ZoneType.OBJECTIVE));
        car.addComponent(new DrawOrderComponent(100));
        car.addComponent(new SizeComponent(2, 4));
        car.addComponent(new RotationComponent(0));
        car.addComponent(new OriginComponent(.5f, .5f));
        gobs.add(car);

        // TIRE DATA
        TireFrictionData frontTireData = FrictionDataFactory.getStreetFriction();
        frontTireData.weightOnTire = carBody.getMass() / 4;

        TireFrictionData rearTireData = frontTireData.copy();
        rearTireData.weightOnTire = carBody.getMass() / 10;

        // /////////////////////////////////
        // create our front left tire
        Body frontLeftTire = makeTire(phys, 6, tireWidth, tireHeight);

        RevoluteJointDef frontLeftTireJointDef = new RevoluteJointDef();
        frontLeftTireJointDef.bodyA = carBody;
        frontLeftTireJointDef.enableLimit = true;
        frontLeftTireJointDef.lowerAngle = 0;
        frontLeftTireJointDef.upperAngle = 0;
        frontLeftTireJointDef.localAnchorB.setZero();
        frontLeftTireJointDef.localAnchorB.add(-tireWidth / 2, 0);

        frontLeftTireJointDef.bodyB = frontLeftTire;
        frontLeftTireJointDef.localAnchorA.set(-1f, 1.25f);
        RevoluteJoint frontLeftJoint = (RevoluteJoint) phys.world.createJoint(frontLeftTireJointDef);

        gobs.add(makeTireObject(frontLeftTire, frontLeftJoint, frontTireData, type, false, false));

        // /////////////////////////////////
        // create front right tire
        Body frontRightTire = makeTire(phys, 6, tireWidth, tireHeight);

        RevoluteJointDef frontRightTireJointDef = new RevoluteJointDef();
        frontRightTireJointDef.bodyA = carBody;
        frontRightTireJointDef.enableLimit = true;
        frontRightTireJointDef.lowerAngle = 0;
        frontRightTireJointDef.upperAngle = 0;
        frontRightTireJointDef.localAnchorB.setZero();
        frontRightTireJointDef.localAnchorB.add(tireWidth / 2, 0);


        frontRightTireJointDef.bodyB = frontRightTire;
        frontRightTireJointDef.localAnchorA.set(1f, 1.25f);
        RevoluteJoint frontRightJoint = (RevoluteJoint) phys.world.createJoint(frontRightTireJointDef);

        gobs.add(makeTireObject(frontRightTire, frontRightJoint, frontTireData, type, false, true));

        // /////////////////////////////////
        // create back right tire
        Body backRightTire = makeTire(phys, 1, tireWidth, tireHeight);

        RevoluteJointDef backRightTireJointDef = new RevoluteJointDef();
        backRightTireJointDef.bodyA = carBody;
        backRightTireJointDef.enableLimit = true;
        backRightTireJointDef.lowerAngle = 0;
        backRightTireJointDef.upperAngle = 0;
        backRightTireJointDef.localAnchorB.setZero();
        backRightTireJointDef.localAnchorB.add(-tireWidth / 2, 0);

        backRightTireJointDef.bodyB = backRightTire;
        backRightTireJointDef.localAnchorA.set(-1f, -1.25f);
        RevoluteJoint backRightTireJoint = (RevoluteJoint) phys.world.createJoint(backRightTireJointDef);


        FuelComponent sharedFuelComponent = new FuelComponent(100, 0.25f);

        MyGameObject BRtire = makeTireObject(backRightTire, backRightTireJoint, rearTireData, type, true, true);
        BRtire.addComponent(sharedFuelComponent);
        gobs.add(BRtire);


        // /////////////////////////////////
        // create back left tire
        Body backLeftTire = makeTire(phys, 1, tireWidth, tireHeight);

        RevoluteJointDef backLeftTireJointDef = new RevoluteJointDef();
        backLeftTireJointDef.bodyA = carBody;
        backLeftTireJointDef.enableLimit = true;
        backLeftTireJointDef.lowerAngle = 0;
        backLeftTireJointDef.upperAngle = 0;
        backLeftTireJointDef.localAnchorB.setZero();
        backLeftTireJointDef.localAnchorB.add(tireWidth / 2, 0);

        backLeftTireJointDef.bodyB = backLeftTire;
        backLeftTireJointDef.localAnchorA.set(1f, -1.25f);
        RevoluteJoint backLeftTireJoint = (RevoluteJoint) phys.world.createJoint(backLeftTireJointDef);

        MyGameObject BLtire = makeTireObject(backLeftTire, backLeftTireJoint, rearTireData, type, true, false);
        BLtire.addComponent(sharedFuelComponent);

        gobs.add(BLtire);
    }

    private static Body makeTire(PhysicsSystem phys, float density, float width, float height) {
        BodyDef tireBodyDef = new BodyDef();
        tireBodyDef.type = BodyDef.BodyType.DynamicBody;
        Body tireBody = phys.world.createBody(tireBodyDef);

        PolygonShape tireShape = new PolygonShape();
        tireShape.setAsBox(width / 2, height / 2);

        Fixture tireFix = tireBody.createFixture(tireShape, 1);
        tireFix.setFriction(5);
        tireFix.setDensity(density);

        return tireBody;
    }

    private static MyGameObject makeTireObject(Body body, RevoluteJoint joint, TireFrictionData tireData, CarType type, boolean rear, boolean right) {
        float maxSpeed = 30;
        float acceleration = 5;

        MyGameObject tire = new MyGameObject();
        PhysicsComponent tirePhysics = new PhysicsComponent(body);
        tirePhysics.body.setUserData(tire);
        tire.addComponent(tirePhysics);
        tire.addComponent(new TireFrictionComponent(tireData));
        if (rear && type == CarType.PLAYER) {
            tire.addComponent(new DriveTireComponent(maxSpeed, acceleration));
            tire.addComponent(new PlayerTireComponent());
        } else {
            if (type == CarType.PLAYER) {
                tire.addComponent(new SteerableComponent(MathUtils.PI / 4));
            }
            tire.addComponent(new RevoluteJointComponent(joint));
        }
        tire.addComponent(new PositionComponent(0, 0));
        String path;
        if (right) path = "player/tireRight";
        else path = "player/tireLeft";
        tire.addComponent(new AnimatedImageComponent(path, 0.0f));
        tire.addComponent(new VelocityBasedAnimationSpeedComponent(1f));
        tire.addComponent(new DrawOrderComponent(90));
        tire.addComponent(new SizeComponent(.25f, 2/3.0f));
        tire.addComponent(new RotationComponent(0));
        tire.addComponent(new OriginComponent(.5f, .5f));
        return tire;
    }
}

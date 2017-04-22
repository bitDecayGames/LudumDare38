package com.bitdecay.game.room;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    PhysicsSystem phys = null;

    public DemoRoom(GameScreen gameScreen) {
        super(gameScreen);

        // systems must be added before game objects
        new InitializationSystem(this);
        phys = new PhysicsSystem(this);
        new TireSteeringSystem(this);
        new DriveTireSystem(this);
        new TireFrictionSystem(this);
        new EBrakeSystem(this);
        new TimerSystem(this);
        new SimpleUpdateSystem(this);
        new CameraUpdateSystem(this);
        new VelocityBasedAnimationSystem(this);
        new RespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new ShapeDrawSystem(this);
        new DrawSystem(this);
        new WaypointSystem(this);
        new RemovalSystem(this);
        new HealthSystem(this, phys.world);

        createCar(0, 0, false, false);

        for (int x = -2; x < 2; x += 1)
            for (int y = -2; y < 2; y += 1) {
                createCar(x * 30, y * 30, true, x % 2 == 0 && y % 2 == 0);
            }

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    private void createCar(float x, float y, boolean npc, boolean addWayPoint) {

        float carWidth = 2;
        float carHeight = 4;
        float halfWidth = carWidth / 2;
        float halfHeight = carHeight / 2;

        float tireWidth = carWidth / 8;
        float tireHeight = carWidth / 3;

        // create our car
        BodyDef carBodyDef = new BodyDef();
        carBodyDef.type = BodyDef.BodyType.DynamicBody;
        carBodyDef.position.set(x, y);

        Body carBody = phys.world.createBody(carBodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(halfWidth, halfHeight);

        FixtureDef carFixtureDef = new FixtureDef();
        carFixtureDef.shape = shape;
        carFixtureDef.friction = 0f;
        carFixtureDef.density = 1;

        carBody.createFixture(carFixtureDef);

        // create car entity
        MyGameObject car = new MyGameObject();
        car.addComponent(new PositionComponent(0, 0));
        PhysicsComponent carPhysics = new PhysicsComponent(null, null, null);
        car.addComponent(carPhysics);
        carPhysics.body = carBody;
        carPhysics.body.setUserData(car);

        //car health section
        HealthComponent carHealth;
        if(npc){
            carHealth = new HealthComponent(3);
        }else{
            carHealth = new HealthComponent(10);
        }
        car.addComponent(carHealth);

        //car damage section
        DamageComponent carDamage = new DamageComponent(2);
        car.addComponent(carDamage);

        //camera section
        if (!npc) car.addComponent(new CameraFollowComponent());

        //waypoint section
        if (addWayPoint) car.addComponent(new WaypointComponent(Color.GREEN.cpy()));
        car.addComponent(new StaticImageComponent("player/taxi/taxi"));
        car.addComponent(new DrawOrderComponent(100));
        car.addComponent(new SizeComponent(2, 4));
        car.addComponent(new RotationComponent(0));
        car.addComponent(new OriginComponent(.5f, .5f));
        gobs.add(car);

        // TIRE DATA
        TireFrictionComponent.TireFrictionData frontTireData = new TireFrictionComponent.TireFrictionData();
        frontTireData.rollingMaxForce = 8;
        frontTireData.driftingMaxForce = .5f;
        frontTireData.lockedTireGripVelocity = 0;
        frontTireData.weightOnTire = carBody.getMass() / 4;

        TireFrictionComponent.TireFrictionData rearTireData = frontTireData.copy();
        rearTireData.driftingMaxForce = 0f;
        rearTireData.lockedTireGripVelocity = 1f;
        rearTireData.weightOnTire = carBody.getMass() / 10;

        // /////////////////////////////////
        // create our front left tire
        Body frontLeftTire = makeTire(6, tireWidth, tireHeight);

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

        gobs.add(makeTireObject(frontLeftTire, frontLeftJoint, frontTireData, npc, false, false));

        // /////////////////////////////////
        // create front right tire
        Body frontRightTire = makeTire(6, tireWidth, tireHeight);

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

        gobs.add(makeTireObject(frontRightTire, frontRightJoint, frontTireData, npc, false, true));

        // /////////////////////////////////
        // create back right tire
        Body backRightTire = makeTire(1, tireWidth, tireHeight);

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


        FuelComponent sharedFuelComponent = new FuelComponent(100, 2);

        MyGameObject BRtire = makeTireObject(backRightTire, backRightTireJoint, rearTireData, npc, true, true);
        BRtire.addComponent(sharedFuelComponent);
        gobs.add(BRtire);


        // /////////////////////////////////
        // create back left tire
        Body backLeftTire = makeTire(1, tireWidth, tireHeight);

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

        MyGameObject BLtire = makeTireObject(backLeftTire, backLeftTireJoint, rearTireData, npc, true, false);
        BLtire.addComponent(sharedFuelComponent);

        gobs.add(BLtire);
    }

    private Body makeTire(float density, float width, float height) {
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

    private MyGameObject makeTireObject(Body body, RevoluteJoint joint, TireFrictionComponent.TireFrictionData tireData, boolean npc, boolean rear, boolean right) {
        float maxSpeed = 30;
        float acceleration = 5;

        MyGameObject tire = new MyGameObject();
        PhysicsComponent tirePhysics = new PhysicsComponent(null, null, null);
        tirePhysics.body = body;
        tire.addComponent(tirePhysics);
        tire.addComponent(new TireFrictionComponent(tireData));
        if (rear && !npc) tire.addComponent(new DriveTireComponent(maxSpeed, acceleration));
        else {
            if (!npc) tire.addComponent(new SteerableComponent(MathUtils.PI / 4));
            tire.addComponent(new RevoluteJointComponent(joint));
        }
        tire.addComponent(new PositionComponent(0, 0));
        String path;
        if (right) path = "player/tireRight"; else path = "player/tireLeft";
        tire.addComponent(new AnimatedImageComponent(path, 0.0f));
        tire.addComponent(new VelocityBasedAnimationSpeedComponent(1f));
        tire.addComponent(new DrawOrderComponent(90));
        tire.addComponent(new SizeComponent(.5f, .8f));
        tire.addComponent(new RotationComponent(0));
        tire.addComponent(new OriginComponent(.5f, .5f));
        return tire;
    }
}

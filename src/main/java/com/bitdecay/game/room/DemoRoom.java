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
    Box2DDebugRenderer debug = null;

    public DemoRoom(GameScreen gameScreen) {
        super(gameScreen);

        debug = new Box2DDebugRenderer();

        // systems must be added before game objects
        new InitializationSystem(this);
        phys = new PhysicsSystem(this);
        new TireSteeringSystem(this);
        new DriveTireSystem(this);
        new TireFrictionSystem(this);
        new EBrakeSystem(this);
        new TimerSystem(this);
        new CameraUpdateSystem(this);
        new RespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new DespawnSystem(this, Integer.MIN_VALUE, Integer.MAX_VALUE, -1000, Integer.MAX_VALUE);
        new ShapeDrawSystem(this);
        new DrawSystem(this);
        new WaypointSystem(this);
        new RemovalSystem(this);

        createCar(0, 0, false, false);

        for (int x = -2; x < 2; x += 1)
            for (int y = -2; y < 2; y += 1) {
                createCar(x * 30, y * 30, true, x % 3 == 0 && y % 3 == 0);
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
        if (!npc) car.addComponent(new CameraFollowComponent());
        if (addWayPoint) car.addComponent(new WaypointComponent(Color.GREEN.cpy()));
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

        float maxSpeed = 30;
        float acceleration = 5;


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

        // create front left tire entity
        MyGameObject tire1 = new MyGameObject();
        PhysicsComponent tire1Physics = new PhysicsComponent(null, null, null);
        tire1Physics.body = frontLeftTire;
        tire1.addComponent(tire1Physics);
        if (!npc) tire1.addComponent(new SteerableComponent(MathUtils.PI / 4));
        tire1.addComponent(new RevoluteJointComponent(frontLeftJoint));
        tire1.addComponent(new TireFrictionComponent(frontTireData));
        gobs.add(tire1);

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

        // create front right tire entity
        MyGameObject tire2 = new MyGameObject();
        PhysicsComponent tire2Physics = new PhysicsComponent(null, null, null);
        tire2Physics.body = frontRightTire;
        tire2.addComponent(tire2Physics);
        if (!npc) tire2.addComponent(new SteerableComponent(MathUtils.PI / 4));
        tire2.addComponent(new RevoluteJointComponent(frontRightJoint));
        tire2.addComponent(new TireFrictionComponent(frontTireData));
        gobs.add(tire2);

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

        // create back right tire entity
        MyGameObject tire3 = new MyGameObject();
        PhysicsComponent tire3Physics = new PhysicsComponent(null, null, null);
        tire3Physics.body = backRightTire;
        tire3.addComponent(tire3Physics);
        if (!npc) tire3.addComponent(new DriveTireComponent(maxSpeed, acceleration));
//        tire3.addComponent(new SteerableComponent(-MathUtils.PI/16));
//        tire3.addComponent(new RevoluteJointComponent(backRightTireJoint));
        tire3.addComponent(new TireFrictionComponent(rearTireData));
        gobs.add(tire3);

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

        // create back left tire entity
        MyGameObject tire4 = new MyGameObject();
        PhysicsComponent tire4Physics = new PhysicsComponent(null, null, null);
        tire4Physics.body = backLeftTire;
        tire4.addComponent(tire4Physics);
        if (!npc) tire4.addComponent(new DriveTireComponent(maxSpeed, acceleration));
//        tire4.addComponent(new SteerableComponent(-MathUtils.PI/16));
//        tire4.addComponent(new RevoluteJointComponent(backLeftTireJoint));
        tire4.addComponent(new TireFrictionComponent(rearTireData));
        gobs.add(tire4);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        debug.render(phys.world, camera.combined);
    }

    public Body makeTire(float density, float width, float height) {
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
}

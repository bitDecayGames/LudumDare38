package com.bitdecay.game.room;


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
        new RemovalSystem(this);

        createCar();

//        for (int x = -10; x < 20; x += 1)
//            for (int y = -10; y < 20; y += 1) {
//                gobs.add(makeRectThing(x * 4, y * 8));
//            }

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    private void createCar() {
        // create our car
        BodyDef carBodyDef = new BodyDef();
        carBodyDef.type = BodyDef.BodyType.DynamicBody;
        carBodyDef.position.set(0, 0);

        Body carBody = phys.world.createBody(carBodyDef);

        PolygonShape shape = new PolygonShape();
        shape.set(new float[]{
                .5f, 0,
                1, .8f,
                .9f, 2f,
                .2f, 3,
                -.5f, 0,
                -1, .8f,
                -.9f, 2f,
                -.2f, 3
        });

        FixtureDef carFixtureDef = new FixtureDef();
        carFixtureDef.shape = shape;
        carFixtureDef.friction = 0f;
        carFixtureDef.density = 1;

        carBody.createFixture(carFixtureDef);

        // create car entity
        MyGameObject car = new MyGameObject();
        car.addComponent(new PositionComponent(0, 0));
        PhysicsComponent carPhysics = new PhysicsComponent(null, null, null);
        carPhysics.body = carBody;
        car.addComponent(new CameraFollowComponent());
        gobs.add(car);

        // create our front left tire
        Body frontLeftTire = makeTire(6);

        RevoluteJointDef frontLeftTireJointDef = new RevoluteJointDef();
        frontLeftTireJointDef.bodyA = carBody;
        frontLeftTireJointDef.enableLimit = true;
        frontLeftTireJointDef.lowerAngle = 0;
        frontLeftTireJointDef.upperAngle = 0;
        frontLeftTireJointDef.localAnchorB.setZero();

        frontLeftTireJointDef.bodyB = frontLeftTire;
        frontLeftTireJointDef.localAnchorA.set(-1, 3.5f);
        RevoluteJoint frontLeftJoint = (RevoluteJoint) phys.world.createJoint(frontLeftTireJointDef);

        // create front left tire entity
        MyGameObject tire1 = new MyGameObject();
        tire1.addComponent(new PositionComponent(0, 0));
        PhysicsComponent tire1Physics = new PhysicsComponent(null, null, null);
        tire1Physics.body = frontLeftTire;
        tire1.addComponent(tire1Physics);
        tire1.addComponent(new SteerableComponent(MathUtils.PI/4));
        tire1.addComponent(new RevoluteJointComponent(frontLeftJoint));
        tire1.addComponent(new TireFrictionComponent(8, .5f, 0));
        gobs.add(tire1);

        Body frontRightTire = makeTire(6);

        RevoluteJointDef frontRightTireJointDef = new RevoluteJointDef();
        frontRightTireJointDef.bodyA = carBody;
        frontRightTireJointDef.enableLimit = true;
        frontRightTireJointDef.lowerAngle = 0;
        frontRightTireJointDef.upperAngle = 0;
        frontRightTireJointDef.localAnchorB.setZero();

        frontRightTireJointDef.bodyB = frontRightTire;
        frontRightTireJointDef.localAnchorA.set(1, 3.5f);
        RevoluteJoint frontRightJoint = (RevoluteJoint) phys.world.createJoint(frontRightTireJointDef);

        // create front right tire entity
        MyGameObject tire2 = new MyGameObject();
        tire2.addComponent(new PositionComponent(0, 0));
        PhysicsComponent tire2Physics = new PhysicsComponent(null, null, null);
        tire2Physics.body = frontRightTire;
        tire2.addComponent(tire2Physics);
        tire2.addComponent(new SteerableComponent(MathUtils.PI/4));
        tire2.addComponent(new RevoluteJointComponent(frontRightJoint));
        tire2.addComponent(new TireFrictionComponent(8, .5f, 0));
        gobs.add(tire2);

        Body backRightTire = makeTire(1);

        RevoluteJointDef backRightTireJointDef = new RevoluteJointDef();
        backRightTireJointDef.bodyA = carBody;
        backRightTireJointDef.enableLimit = true;
        backRightTireJointDef.lowerAngle = 0;
        backRightTireJointDef.upperAngle = 0;
        backRightTireJointDef.localAnchorB.setZero();

        backRightTireJointDef.bodyB = backRightTire;
        backRightTireJointDef.localAnchorA.set(1, -1.5f);
        RevoluteJoint backRightTireJoint = (RevoluteJoint) phys.world.createJoint(backRightTireJointDef);

        // create back right tire entity
        MyGameObject tire3 = new MyGameObject();
        tire3.addComponent(new PositionComponent(0, 0));
        PhysicsComponent tire3Physics = new PhysicsComponent(null, null, null);
        tire3Physics.body = backRightTire;
        tire3.addComponent(tire3Physics);
        tire3.addComponent(new DriveTireComponent(100, 3));
//        tire3.addComponent(new SteerableComponent(-MathUtils.PI/16));
//        tire3.addComponent(new RevoluteJointComponent(backRightTireJoint));
        tire3.addComponent(new TireFrictionComponent(8, 0f, 1));
        gobs.add(tire3);

        Body backLeftTire = makeTire(1);

        RevoluteJointDef backLeftTireJointDef = new RevoluteJointDef();
        backLeftTireJointDef.bodyA = carBody;
        backLeftTireJointDef.enableLimit = true;
        backLeftTireJointDef.lowerAngle = 0;
        backLeftTireJointDef.upperAngle = 0;
        backLeftTireJointDef.localAnchorB.setZero();

        backLeftTireJointDef.bodyB = backLeftTire;
        backLeftTireJointDef.localAnchorA.set(-1, -1.5f);
        RevoluteJoint backLeftTireJoint = (RevoluteJoint) phys.world.createJoint(backLeftTireJointDef);

        // create back left tire entity
        MyGameObject tire4 = new MyGameObject();
        tire4.addComponent(new PositionComponent(0, 0));
        PhysicsComponent tire4Physics = new PhysicsComponent(null, null, null);
        tire4Physics.body = backLeftTire;
        tire4.addComponent(tire4Physics);
        tire4.addComponent(new DriveTireComponent(100, 3));
//        tire4.addComponent(new SteerableComponent(-MathUtils.PI/16));
//        tire4.addComponent(new RevoluteJointComponent(backLeftTireJoint));
        tire4.addComponent(new TireFrictionComponent(8, 0f, 1));
        gobs.add(tire4);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        debug.render(phys.world, camera.combined);
    }

//    public MyGameObject makeRectThing(float x, float y) {
//        return (new MyGameObject()).addComponent(new PositionComponent(0, 0)).addComponent(makePhysicsThing(x, y));
//    }

//    public PhysicsComponent makePhysicsThing(float x, float y) {
//        BodyDef carBodyDef = new BodyDef();
//        carBodyDef.type = BodyDef.BodyType.DynamicBody;
//        carBodyDef.position.set(x, y);
//
//        Body carBody = phys.world.createBody(carBodyDef);
//
//        PolygonShape shape = new PolygonShape();
//        shape.set(new float[]{
//                .5f, 0,
//                1, .8f,
//                .9f, 2f,
//                .2f, 3,
//                -.5f, 0,
//                -1, .8f,
//                -.9f, 2f,
//                -.2f, 3
//        });
//
//        FixtureDef carFixtureDef = new FixtureDef();
//        carFixtureDef.shape = shape;
//        carFixtureDef.friction = 0.5f;
//        carFixtureDef.density = 10;
//
//        carBody.createFixture(carFixtureDef);
//
//        Body frontLeftTire = makeTire();
//
//        RevoluteJointDef frontLeftTireJointDef = new RevoluteJointDef();
//        frontLeftTireJointDef.bodyA = carBody;
//        frontLeftTireJointDef.enableLimit = true;
//        frontLeftTireJointDef.lowerAngle = 0;
//        frontLeftTireJointDef.upperAngle = 0;
//        frontLeftTireJointDef.localAnchorB.setZero();
//
//        frontLeftTireJointDef.bodyB = frontLeftTire;
//        frontLeftTireJointDef.localAnchorA.set(-1, 2.5f);
//        phys.world.createJoint(frontLeftTireJointDef);
//
//        Body frontRightTire = makeTire();
//
//        RevoluteJointDef frontRightTireJointDef = new RevoluteJointDef();
//        frontRightTireJointDef.bodyA = carBody;
//        frontRightTireJointDef.enableLimit = true;
//        frontRightTireJointDef.lowerAngle = 0;
//        frontRightTireJointDef.upperAngle = 0;
//        frontRightTireJointDef.localAnchorB.setZero();
//
//        frontRightTireJointDef.bodyB = frontRightTire;
//        frontRightTireJointDef.localAnchorA.set(1, 2.5f);
//        phys.world.createJoint(frontRightTireJointDef);
//
//        Body backRightTire = makeTire();
//
//        RevoluteJointDef backRightTireJointDef = new RevoluteJointDef();
//        backRightTireJointDef.bodyA = carBody;
//        backRightTireJointDef.enableLimit = true;
//        backRightTireJointDef.lowerAngle = 0;
//        backRightTireJointDef.upperAngle = 0;
//        backRightTireJointDef.localAnchorB.setZero();
//
//        backRightTireJointDef.bodyB = backRightTire;
//        backRightTireJointDef.localAnchorA.set(1, 0f);
//        phys.world.createJoint(backRightTireJointDef);
//
//        Body backLeftTire = makeTire();
//
//        RevoluteJointDef backLeftTireJointDef = new RevoluteJointDef();
//        backLeftTireJointDef.bodyA = carBody;
//        backLeftTireJointDef.enableLimit = true;
//        backLeftTireJointDef.lowerAngle = 0;
//        backLeftTireJointDef.upperAngle = 0;
//        backLeftTireJointDef.localAnchorB.setZero();
//
//        backLeftTireJointDef.bodyB = backLeftTire;
//        backLeftTireJointDef.localAnchorA.set(-1, 0);
//        phys.world.createJoint(backLeftTireJointDef);
//
//        return new PhysicsComponent(carBodyDef, shape, carFixtureDef);
//    }

    public Body makeTire(float density) {
        BodyDef tireBodyDef = new BodyDef();
        tireBodyDef.type = BodyDef.BodyType.DynamicBody;
        Body tireBody = phys.world.createBody(tireBodyDef);

        PolygonShape tireShape = new PolygonShape();
        tireShape.setAsBox(.25f, .5f);

        Fixture tireFix = tireBody.createFixture(tireShape, 1);
        tireFix.setFriction(5);
        tireFix.setDensity(density);

        return tireBody;
    }
}

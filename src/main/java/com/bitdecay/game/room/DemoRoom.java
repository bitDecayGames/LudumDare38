package com.bitdecay.game.room;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bitdecay.game.component.*;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;
import com.bitdecay.game.ui.Fuel;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.ui.UIElements;
import com.bitdecay.game.util.ContactDistributer;
import com.bitdecay.game.util.ZoneType;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    PhysicsSystem phys = null;

    private Stage stage;
    private UIElements uiElements;

    public DemoRoom(GameScreen gameScreen) {
        super(gameScreen);

        createStage();

        // systems must be added before game objects
        phys = new PhysicsSystem(this);
        ContactDistributer contactDistrib = new ContactDistributer();
        phys.world.setContactListener(contactDistrib);
        new InitializationSystem(this);
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
        new HealthSystem(this, contactDistrib);
        new ZoneUpdateSystem(this, contactDistrib);

        // various gauge things
        new FuelGaugeSystem(this, uiElements);
        new HungerGaugeSystem(this, uiElements);
        new PoopGaugeSystem(this, uiElements);

        createCar(0, 0, false, false);

//        for (int x = -2; x < 2; x += 1)
//            for (int y = -2; y < 2; y += 1) createCar(x * 30, y * 30, true, x % 2 == 0 && y % 2 == 0);

        gobs.add(GameObjectFactory.makeTrashBin(phys,-5,5));
        gobs.add(GameObjectFactory.makeDumpster(phys,-5,10));
        gobs.add(GameObjectFactory.makeCart(phys,-5,15));
        gobs.add(GameObjectFactory.makeToilet(phys,-5,20));
        gobs.add(GameObjectFactory.makeTrashBag(phys,-5,25));
        gobs.add(GameObjectFactory.makeFirehydrant(phys,0,15));

        createZone(10, 0, 6, 10, 0, ZoneType.BATHROOM);
        createZone(20, 16, 6, 10, 0, ZoneType.FUEL);
        createZone(-10, 0, 6, 10, 0, ZoneType.FOOD);

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        //      hud.update(room.getGameObjects());
//        stage.getViewport().update(width, height, true);

        stage.act(delta);
        stage.draw();
    }

    private void createStage() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        uiElements = new UIElements();

        Fuel fuel = new Fuel(screenSize());
        uiElements.fuel = fuel;
        stage.addActor(fuel);

        uiElements.hud = new HUD(screenSize());
        stage.addActor(uiElements.hud);

        stage.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                uiElements.hud.toggle();
                return true;
            }
        });
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
        PhysicsComponent carPhysics = new PhysicsComponent(carBody);
        car.addComponent(carPhysics);
        carPhysics.body.setUserData(car);

        //car health section
        HealthComponent carHealth;
        if (npc) {
            carHealth = new HealthComponent(3);
        } else {
            carHealth = new HealthComponent(10);
        }
        car.addComponent(carHealth);

        //car damage section
        DamageComponent carDamage = new DamageComponent(2);
        car.addComponent(carDamage);

        //camera section
        if (!npc) {
            car.addComponent(new CameraFollowComponent());
            car.addComponent(new PlayerControlComponent());
            car.addComponent(new HungerComponent(100, 10));
            car.addComponent(new PoopooComponent(100, 5));
        }

        //waypoint section
        if (addWayPoint) car.addComponent(new WaypointComponent(ZoneType.OBJECTIVE));
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
        PhysicsComponent tirePhysics = new PhysicsComponent(body);
        tire.addComponent(tirePhysics);
        tire.addComponent(new TireFrictionComponent(tireData));
        if (rear && !npc) {
            tire.addComponent(new DriveTireComponent(maxSpeed, acceleration));
            tire.addComponent(new PlayerTireComponent());
        } else {
            if (!npc) {
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
        tire.addComponent(new SizeComponent(.5f, .8f));
        tire.addComponent(new RotationComponent(0));
        tire.addComponent(new OriginComponent(.5f, .5f));
        return tire;
    }

    private void createZone(float x, float y, float width, float length, float rotation, ZoneType zoneType){
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

        ZoneComponent zComp;
        switch (zoneType) {
            case BATHROOM:
                zComp = new ZoneComponent(() -> {
                    System.out.println("You take a poo here");
                    zone.getComponent(ZoneComponent.class).get().active = false;
                    zone.addComponent(new TimerComponent(5, () -> {
                            zone.getComponent(ZoneComponent.class).get().active = true;
                            zone.removeComponent(TimerComponent.class);
                    }));
                    gobs.forEach(gob -> gob.forEachComponentDo(PoopooComponent.class, poo ->
                        poo.currentPoopoo = 0));
                });
                zComp.active = true;
                zComp.canDeactivate = false;
                zone.addComponent(zComp);
                break;
            case FOOD:
                zComp = new ZoneComponent(() -> {
                    System.out.println("You eat the food here");
                    zone.getComponent(ZoneComponent.class).get().active = false;
                    zone.addComponent(new TimerComponent(5, () -> {
                            zone.getComponent(ZoneComponent.class).get().active = true;
                            zone.removeComponent(TimerComponent.class);
                    }));
                    gobs.forEach(gob -> gob.forEachComponentDo(HungerComponent.class, hungry ->
                        hungry.currentFullness = hungry.maxFullness));
                });
                zComp.active = true;
                zComp.canDeactivate = false;
                zone.addComponent(zComp);
                break;
            case FUEL:
                zComp = new ZoneComponent(() -> {
                    System.out.println("You fuel the car here");
                    zone.getComponent(ZoneComponent.class).get().active = false;
                    zone.addComponent(new TimerComponent(5, () -> {
                            zone.getComponent(ZoneComponent.class).get().active = true;
                            zone.removeComponent(TimerComponent.class);
                    }));
                    gobs.forEach(gob -> gob.forEachComponentDo(FuelComponent.class, fuel ->
                        fuel.currentFuel = fuel.maxFuel));
                });
                zComp.active = true;
                zComp.canDeactivate = false;
                zone.addComponent(zComp);
                break;
            case REPAIR:
            case OBJECTIVE:
            default:
        }

//        if(removable) {
//            ZoneComponent zComp = new ZoneComponent(() -> {
//                System.out.println("I talk once then I go way!!!!");
//                phys.world.destroyBody(zone.getComponent(PhysicsComponent.class).get().body);
//                zone.addComponent(new RemoveNowComponent());
//            });
//            zComp.active = true;
//            zComp.canDeactivate = true;
//            zone.addComponent(zComp);
//        } else {
//            ZoneComponent zComp = new ZoneComponent(() -> {
//                System.out.println("I TALK SOOO MUCH AND YOU CANT STOP ME!!");
//                zone.getComponent(ZoneComponent.class).get().active = false;
//                zone.addComponent(new TimerComponent(5, () -> {
//                        zone.getComponent(ZoneComponent.class).get().active = true;
//                        zone.removeComponent(TimerComponent.class);
//                }));
//            });
//            zComp.active = true;
//            zComp.canDeactivate = false;
//            zone.addComponent(zComp);
//        }

        gobs.add(zone);
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}

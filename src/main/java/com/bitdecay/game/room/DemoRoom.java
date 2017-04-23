package com.bitdecay.game.room;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bitdecay.game.gameobject.StaticGameObjectFactory;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;
import com.bitdecay.game.ui.Fuel;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.ui.UIElements;
import com.bitdecay.game.util.CarType;
import com.bitdecay.game.util.ContactDistributer;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    PhysicsSystem phys = null;

    private Stage stage;
    private UIElements uiElements;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;

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
        new WaypointSystem(this, uiElements);
        new RemovalSystem(this);
        new HealthSystem(this, contactDistrib);
        new ZoneUpdateSystem(this, contactDistrib);
        new TireFrictionModifierSystem(this, contactDistrib);
        new TorqueableSystem(this);

        new ParticlePositionSystem(this);

        new ParticleSystem(this);

        // various gauge things
        new FuelGaugeSystem(this, uiElements);
        new HungerGaugeSystem(this, uiElements);
        new PoopGaugeSystem(this, uiElements);

        new MoneySystem(this, uiElements, stage);

        new ObjectiveSystem(this, uiElements);

        new BreakableObjectSystem(this);
        GameObjectFactory.createCar(gobs, phys, new Vector2(), CarType.PLAYER, false);

        gobs.add(GameObjectFactory.makeTrashBin(phys,-5,5));
        gobs.add(GameObjectFactory.makeDumpster(phys,-5,10));
        gobs.add(GameObjectFactory.makeCart(phys,-5,15));
        gobs.add(GameObjectFactory.makeToilet(phys,-5,20));
        gobs.add(GameObjectFactory.makeTrashBag(phys,-5,25));
        gobs.add(GameObjectFactory.makeFirehydrant(phys,-5,30));
        gobs.add(GameObjectFactory.makeMailbox(phys,0,15));
        gobs.add(GameObjectFactory.makeGrassField(phys,20,20));

//        gobs.add(GameObjectFactory.makePerson(phys,5,5));

//        GameObjectFactory.createZone(gobs, phys, 10, 0, 6, 10, 0, ZoneType.BATHROOM);
//        GameObjectFactory.createZone(gobs, phys, 20, 16, 6, 10, 0, ZoneType.FUEL);
//        GameObjectFactory.createZone(gobs, phys, -10, 0, 6, 10, 0, ZoneType.FOOD);

        map = new TmxMapLoader().load(Gdx.files.internal("img/tiled/town.tmx").path());
        renderer = new OrthogonalTiledMapRenderer(map, 1/40f);

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    @Override
    public void render(float delta) {
        renderer.setView(camera);
        renderer.render();
        super.render(delta);
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

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}

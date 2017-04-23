package com.bitdecay.game.room;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.StaticGameObjectFactory;
import com.bitdecay.game.pathfinding.Node;
import com.bitdecay.game.pathfinding.NodeComponent;
import com.bitdecay.game.pathfinding.NodeSystem;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;
import com.bitdecay.game.ui.Fuel;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.ui.UIElements;
import com.bitdecay.game.util.CarType;
import com.bitdecay.game.util.ContactDistributer;
import com.bitdecay.game.util.Tuple;
import com.bitdecay.game.util.ZoneType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    public ArrayList<Vector2> spawnPoints;

    PhysicsSystem phys = null;

    private Stage stage;
    private UIElements uiElements;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;

    float scaleFactor = 1/40f;
    float worldOffsetY = 1f;
    float worldOffsetX = 1f;

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
        new HealthSystem(this, contactDistrib);
        new ZoneUpdateSystem(this, contactDistrib);
        new TireFrictionModifierSystem(this, contactDistrib);
        new TorqueableSystem(this);
        new FollowSystem(this);
        new DeathSystem(this);

        new ParticlePositionSystem(this);

        new ParticleSystem(this);

        // various gauge things
        new FuelGaugeSystem(this, uiElements);
        new HungerGaugeSystem(this, uiElements);
        new PoopGaugeSystem(this, uiElements);

        new MoneySystem(this, uiElements, stage);

        // ObjectiveSystem is based on objects added to the world, it needs to go after those.
        new ObjectiveSystem(this, uiElements);

        new BreakableObjectSystem(this);
        new RemovalSystem(this);
        new NodeSystem(this);
        GameObjectFactory.createCar(gobs, phys, new Vector2(), CarType.PLAYER, false);
        GameObjectFactory.createCarCass(gobs, phys.world,new Vector2(5,20),0);

        gobs.add(GameObjectFactory.makePerson(phys,5,5));
        gobs.add(GameObjectFactory.makePerson(phys,15,5));
        gobs.add(GameObjectFactory.makePerson(phys,-5,5));

        gobs.add(GameObjectFactory.createZone(10, 0, 6, 10, 0, ZoneType.BATHROOM));
        gobs.add(GameObjectFactory.createZone(20, 16, 6, 10, 0, ZoneType.FUEL));
        gobs.add(GameObjectFactory.createZone(-10, 0, 6, 10, 0, ZoneType.FOOD));

        loadTileMapAndStartingObjects();

        Node a = new Node(new Vector2());
        Node b = new Node(new Vector2(4, 4));
        Node c = new Node(new Vector2(-3, 3));
        c.connectTo(b);
        Node d = new Node(new Vector2(6, 6));
        Node e = new Node(new Vector2(-6, 6));
        e.connectTo(d);

        Node[] nodes = new Node[] {
            a, b, c, d, e
        };

        Arrays.stream(nodes).forEach(node -> {
            MyGameObject temp = new MyGameObject();
            temp.addComponent(new NodeComponent(node));
            gobs.add(temp);
        });

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    private void loadTileMapAndStartingObjects() {
        map = new TmxMapLoader().load(Gdx.files.internal("img/tiled/town.tmx").path());
        renderer = new OrthogonalTiledMapRenderer(map, scaleFactor);

        MapLayers mapLayers = map.getLayers();

        TiledMapTileLayer collidablesLayer = (TiledMapTileLayer) mapLayers.get("Collidables");

        for (int x = 0; x < collidablesLayer.getWidth(); x++) {
            for (int y = 0; y < collidablesLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = collidablesLayer.getCell(x, y);
                if (cell != null) {
                    String objName = (String) cell.getTile().getProperties().get("obj_name");
                    if (objName != null) {
                        System.out.printf("Creating new object from tiled map (%d,%d): %s\n", x, y, objName);
                        createObjectFromName(objName, x, y);
                    }
                }
            }
        }

        TiledMapTileLayer buildingsLayer = (TiledMapTileLayer) mapLayers.get("Buildings");

        for (int x = 0; x < buildingsLayer.getWidth(); x++) {
            for (int y = 0; y < buildingsLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = buildingsLayer.getCell(x, y);
                if (cell != null) {
                    String objName = (String) cell.getTile().getProperties().get("obj_name");
                    if (objName != null) {
                        String stringWidthTiles = (String)cell.getTile().getProperties().get("width_tiles");
                        String stringHeightTiles = (String)cell.getTile().getProperties().get("height_tiles");
                        int widthTiles = Integer.parseInt(stringWidthTiles);
                        int heightTiles = Integer.parseInt(stringHeightTiles);
                        createBuildingCollisionBox(objName, x, y, widthTiles, heightTiles);
                    }
                }
            }
        }

        spawnPoints = new ArrayList<>();
        TiledMapTileLayer spawnpointsLayer = (TiledMapTileLayer) mapLayers.get("Spawn");

        for (int x = 0; x < spawnpointsLayer.getWidth(); x++) {
            for (int y = 0; y < spawnpointsLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = spawnpointsLayer.getCell(x, y);
                if (cell != null) {
                    spawnPoints.add(new Vector2(x,y));
                }
            }
        }
    }

    private void createBuildingCollisionBox(String name, float x, float y, int widthTiles, int heightTiles){
        x = (widthTiles/2f) + (x * 2);
        y = (heightTiles/2f) + (y * 2);
        System.out.printf("Creating static body for %s at (%f,%f) of size (%d,%d)\n", name, x, y, widthTiles, heightTiles);
        gobs.add(StaticGameObjectFactory.create(phys, new Vector2(x,y), new Vector2(widthTiles, heightTiles), 1));
    }


    private void createObjectFromName(String name, float x, float y) {

        x = worldOffsetX + (x * 2);
        y = worldOffsetY + (y * 2);

        switch(name) {
            case "mail":
                gobs.add(GameObjectFactory.makeMailbox(phys, x, y));
                break;
            case "hydrant":
                gobs.add(GameObjectFactory.makeFirehydrant(phys, x, y));
                break;
            case "dumpster":
                gobs.add(GameObjectFactory.makeDumpster(phys, x, y));
                break;
            case "bag":
                gobs.add(GameObjectFactory.makeTrashBag(phys,x,y));
                break;
            case "bin":
                gobs.add(GameObjectFactory.makeTrashBin(phys,x,y));
                break;
            case "potty":
                gobs.add(GameObjectFactory.makeToilet(phys,x,y));
                break;
            case "cart":
                gobs.add(GameObjectFactory.makeCart(phys,x,y));
                break;
            default:
                System.out.println("Item name not recognized. Not spawning in an object");
        }
    }

    @Override
    public void draw(SpriteBatch spriteBatch) {
        renderer.setView(camera);
        renderer.render();
        super.draw(spriteBatch);
        stage.act(1/60f);
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

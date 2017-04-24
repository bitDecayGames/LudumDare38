package com.bitdecay.game.room;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.pfa.DefaultGraphPath;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.bitdecay.game.ai.AIControlSystem;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.StaticGameObjectFactory;
import com.bitdecay.game.pathfinding.*;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.screen.GameScreen;
import com.bitdecay.game.system.*;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.util.CarType;
import com.bitdecay.game.util.ContactDistributer;
import com.bitdecay.game.util.ZoneType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * The demo room is just a super simple example of how to add systems and game objects to a room.
 */
public class DemoRoom extends AbstractRoom {

    public ArrayList<Vector2> spawnPoints;

    PhysicsSystem phys = null;

    private Stage stage;
    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    //    TiledMap roofMap;
//    OrthogonalTiledMapRenderer roofRenderer;
    NodeGraph graph;

    float scaleFactor = 1/40f;
    float worldOffsetY = 1f;
    float worldOffsetX = 1f;

    public DemoRoom(GameScreen gameScreen) {
        super(gameScreen);

        createStage();

        // Graph/Nodes
        graph = new NodeGraph();
//        graph.removeNode(graph.getNodes().get(22));
//        graph.removeNode(graph.getNodes().get(23));
//        graph.removeNode(graph.getNodes().get(24));

        // systems must be added before game objects
        phys = new PhysicsSystem(this);
        ContactDistributer contactDistrib = new ContactDistributer();
        phys.world.setContactListener(contactDistrib);
        new InitializationSystem(this);
        new InvincibleSystem(this);
        new FollowPositionSystem(this);
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
        new HealthSystem(this, contactDistrib);
        new ZoneUpdateSystem(this, contactDistrib);
        new TireFrictionModifierSystem(this, contactDistrib);
        new TorqueableSystem(this);
        new FollowSystem(this);
        new DeathSystem(this);

        new ParticlePositionSystem(this);
        new ParticleSystem(this);

        // various gauge things
        new FuelGaugeSystem(this);
        new HungerGaugeSystem(this);
        new PoopGaugeSystem(this);

        new MoneySystem(this, stage);

        // ObjectiveSystem is based on objects added to the world, it needs to go after those.
        new ObjectiveSystem(this);

        new BreakableObjectSystem(this);
        new RemovalSystem(this);
        new NodeSystem(this);
        GameObjectFactory.createCar(gobs, phys, new Vector2(280, 0), CarType.PLAYER, false);
        new AIControlSystem(this, graph);
        GameObjectFactory.createCarCass(gobs, phys.world,new Vector2(5,20),0);

        gobs.add(GameObjectFactory.makePerson(phys,5,5));
        gobs.add(GameObjectFactory.makePerson(phys,15,5));
        gobs.add(GameObjectFactory.makePerson(phys,-5,5));

        gobs.add(GameObjectFactory.createZone(10, 0, 6, 10, 0, ZoneType.BATHROOM, null));
        gobs.add(GameObjectFactory.createZone(20, 16, 6, 10, 0, ZoneType.FUEL, null));
        gobs.add(GameObjectFactory.createZone(-10, 0, 6, 10, 0, ZoneType.FOOD, null));

        loadTileMapAndStartingObjects();

        // Add debug graph layer
        Arrays.stream(graph.getNodes().toArray()).forEach(node -> {
            MyGameObject temp = new MyGameObject();
            temp.addComponent(new NodeComponent(node));
            gobs.add(temp);
        });

        // this is required to be at the end here so that the systems have the latest gobs
        systemManager.cleanup();
    }

    private void loadTileMapAndStartingObjects() {
        map = new TmxMapLoader().load(Gdx.files.internal("img/tiled/world.tmx").path());
        renderer = new OrthogonalTiledMapRenderer(map, scaleFactor);

//        roofMap = new TmxMapLoader().load(Gdx.files.internal("img/tiled/world.tmx").path());
//        roofRenderer = new OrthogonalTiledMapRenderer(roofMap, scaleFactor);

        MapLayers mapLayers = map.getLayers();

        // Background to graph nodes.
        TiledMapTileLayer backgroundLayer = (TiledMapTileLayer) mapLayers.get("Background");

        float graphScale = 2.05f;
        Vector2 offset = (new Vector2(1, 1)).scl(0.35f);
        graph.populate(backgroundLayer.getWidth(), backgroundLayer.getHeight(), graphScale, offset);

        int nodeIdx = backgroundLayer.getHeight() * backgroundLayer.getWidth() - 1;
        for (int y = backgroundLayer.getHeight() - 1; y >= 0; y--) {
            for (int x = backgroundLayer.getWidth() - 1; x >= 0; x--) {
                TiledMapTileLayer.Cell cell = backgroundLayer.getCell(x, y);
                if (cell == null) {
                    graph.removeNode(nodeIdx);
                }
                nodeIdx--;
            }
        }

        graph.syncIndicies();

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
//        roofRenderer.setView(camera);
//        roofRenderer.render();
        stage.act(1/60f);
        stage.draw();
    }

    private void createStage() {
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        new HUD();
        stage.addActor(HUD.instance());

        stage.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                HUD.instance().phone.toggle();
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

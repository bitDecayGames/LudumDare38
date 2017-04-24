package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bitdecay.game.Launcher;
import com.bitdecay.game.component.PlayerBodyComponent;
import com.bitdecay.game.component.ZoneComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.util.ContactDistributer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ZoneUpdateSystem extends AbstractUpdatableSystem implements ContactListener{

    Map<Fixture, Fixture> ongoingZoneToPlayerCollisions = new HashMap<>();
    private float minZoneTriggerVelocity = (float) Launcher.conf.getDouble("tuning.minZoneTriggerVelocity");

    public ZoneUpdateSystem(AbstractRoom room, ContactDistributer contactDistrib) {
        super(room);
        contactDistrib.listeners.add(this);
    }

    @Override
    public void update(float delta) {
        for (Map.Entry<Fixture, Fixture> zoneToPlayer : ongoingZoneToPlayerCollisions.entrySet()) {
            Fixture zoneFixture = zoneToPlayer.getKey();
            Body zoneBody = zoneFixture.getBody();

            Fixture playerFixture = zoneToPlayer.getValue();
            Body playerBody = playerFixture.getBody();
            MyGameObject player = (MyGameObject) playerBody.getUserData();

            Optional<ZoneComponent> zoneOpt = ((MyGameObject) zoneBody.getUserData()).getComponent(ZoneComponent.class);
            if(checkIfPlayerInZone(playerFixture, zoneFixture, zoneOpt.map(z -> z.strict).orElse(true))){
                if(playerBody.getLinearVelocity().len() < minZoneTriggerVelocity){
                    zoneOpt.ifPresent(theZone -> {
                        if(theZone.active) {
                            theZone.execute(player);
                            if(theZone.canDeactivate){
                                log.info("Deactivate zone");
                                theZone.active = false;
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(ZoneComponent.class);
    }

    @Override
    public void beginContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();
//        if (objectA != null && objectB != null) log.info("Begin contact: A({}) -> B({})", objectA.name(), objectB.name());

        boolean objectAIsZone = objectA != null && objectA.getComponent(ZoneComponent.class).map(z -> z.active).orElse(false);
        boolean objectBIsZone = objectB != null && objectB.getComponent(ZoneComponent.class).map(z -> z.active).orElse(false);

        boolean objectAIsPlayer = objectA != null && objectA.hasComponents(PlayerBodyComponent.class);
        boolean objectBIsPlayer = objectB != null && objectB.hasComponents(PlayerBodyComponent.class);

        if (objectAIsZone && objectBIsPlayer && contact.getFixtureB().getShape() instanceof PolygonShape) {
//            log.info("Add to map: A({}) -> B({}) total: {}", objectA.name(), objectB.name(), ongoingZoneToPlayerCollisions.size());
            ongoingZoneToPlayerCollisions.put(contact.getFixtureA(), contact.getFixtureB());
        } else if (objectAIsPlayer && objectBIsZone && contact.getFixtureA().getShape() instanceof PolygonShape) {
//            log.info("Add to map: A({}) -> B({}) total: {}", objectA.name(), objectB.name(), ongoingZoneToPlayerCollisions.size());
            ongoingZoneToPlayerCollisions.put(contact.getFixtureB(), contact.getFixtureA());
        }
    }

    public boolean checkIfPlayerInZone(Fixture playerFixture, Fixture zoneFixture, boolean strict) {
        if(strict) {
            PolygonShape playerPoly = (PolygonShape) playerFixture.getShape();

            for (int i = 0; i < playerPoly.getVertexCount(); i++) {
                Vector2 playerPoint = new Vector2();
                playerPoly.getVertex(i, playerPoint);
                Vector2 worldPoint = playerFixture.getBody().getWorldPoint(playerPoint);
                if (!zoneFixture.testPoint(worldPoint.x, worldPoint.y)) {
                    return false;
                }
            }
            return true;
        } else {
            PolygonShape playerPoly = (PolygonShape) playerFixture.getShape();

            for (int i = 0; i < playerPoly.getVertexCount(); i++) {
                Vector2 playerPoint = new Vector2();
                playerPoly.getVertex(i, playerPoint);
                Vector2 worldPoint = playerFixture.getBody().getWorldPoint(playerPoint);
                if (zoneFixture.testPoint(worldPoint.x, worldPoint.y)) {
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public void endContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();

//        if (objectA != null && objectB != null) log.info("End contact: A({}) -> B({})", objectA.name(), objectB.name());

        boolean objectAIsZone = objectA != null && objectA.hasComponents(ZoneComponent.class);
        boolean objectBIsZone = objectB != null && objectB.hasComponents(ZoneComponent.class);

        boolean objectAIsPlayer = objectA != null && objectA.hasComponents(PlayerBodyComponent.class);
        boolean objectBIsPlayer = objectB != null && objectB.hasComponents(PlayerBodyComponent.class);

        if (objectAIsZone && objectBIsPlayer) {
            ongoingZoneToPlayerCollisions.remove(contact.getFixtureA());
        } else if (objectAIsPlayer && objectBIsZone) {
            ongoingZoneToPlayerCollisions.remove(contact.getFixtureB());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
}

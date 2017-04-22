package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.PlayerControlComponent;
import com.bitdecay.game.component.ZoneComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.util.ContactDistributer;

import java.util.HashMap;
import java.util.Map;

public class ZoneUpdateSystem extends AbstractUpdatableSystem implements ContactListener{

    Map<Fixture, Fixture> ongoingZoneToPlayerCollisions = new HashMap<>();

    public ZoneUpdateSystem(AbstractRoom room, ContactDistributer contactDistrib) {
        super(room);
        contactDistrib.listeners.add(this);
    }

    @Override
    public void update(float delta) {
        for (Map.Entry<Fixture, Fixture> zoneToPlayer : ongoingZoneToPlayerCollisions.entrySet()) {
            if(checkPlayerStoppedInZone(zoneToPlayer.getValue(), zoneToPlayer.getKey())){
                if(zoneToPlayer.getValue().getBody().getLinearVelocity().len() < .1){
                    ((MyGameObject) zoneToPlayer.getKey().getBody().getUserData()).getComponent(ZoneComponent.class).get().execute();
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

        boolean objectAIsZone = objectA != null &&
                objectA.hasComponents(ZoneComponent.class, PhysicsComponent.class) &&
                objectA.getComponent(ZoneComponent.class).get().active;
        boolean objectBIsZone = objectB != null &&
                objectB.hasComponents(ZoneComponent.class, PhysicsComponent.class) &&
                objectB.getComponent(ZoneComponent.class).get().active;

        boolean objectAIsPlayer = objectA != null &&
                objectA.hasComponents(PlayerControlComponent.class, PhysicsComponent.class);
        boolean objectBIsPlayer = objectB != null &&
                objectB.hasComponents(PlayerControlComponent.class, PhysicsComponent.class);

        if (objectAIsZone && objectBIsPlayer) {
            ongoingZoneToPlayerCollisions.put(contact.getFixtureA(), contact.getFixtureB());
        } else if (objectAIsPlayer && objectBIsZone) {
            ongoingZoneToPlayerCollisions.put(contact.getFixtureB(), contact.getFixtureA());
        }
    }

    public boolean checkPlayerStoppedInZone(Fixture playerFixture, Fixture zoneFixture) {
        PolygonShape playerPoly = (PolygonShape) playerFixture.getShape();

        for(int i = 0;i < playerPoly.getVertexCount();i++){
            Vector2 playerPoint = new Vector2();
            playerPoly.getVertex(i, playerPoint);
            Vector2 worldPoint = playerFixture.getBody().getWorldPoint(playerPoint);
            if(!zoneFixture.testPoint(worldPoint.x, worldPoint.y)){
                return false;
            }
        }
        return true;
    }

    @Override
    public void endContact(Contact contact) {
        MyGameObject objectA = (MyGameObject) contact.getFixtureA().getBody().getUserData();
        MyGameObject objectB = (MyGameObject) contact.getFixtureB().getBody().getUserData();

        boolean objectAIsZone = objectA != null && objectA.hasComponents(ZoneComponent.class, PhysicsComponent.class);
        boolean objectBIsZone = objectB != null && objectB.hasComponents(ZoneComponent.class, PhysicsComponent.class);

        boolean objectAIsPlayer = objectA != null && objectA.hasComponents(PlayerControlComponent.class, PhysicsComponent.class);
        boolean objectBIsPlayer = objectB != null && objectB.hasComponents(PlayerControlComponent.class, PhysicsComponent.class);

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

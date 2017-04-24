package com.bitdecay.game.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.bitdecay.game.component.PersonComponent;
import com.bitdecay.game.component.ZoneComponent;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.ui.HUD;
import com.bitdecay.game.ui.Phone;
import com.bitdecay.game.util.Tuple;
import com.bitdecay.game.util.ZoneType;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectiveSystem extends AbstractUpdatableSystem{
    List<Tuple<MyGameObject, MyGameObject>> objectives = new ArrayList<>();

    final private int MAXOBJECTIVES = 3;
    private int currentObjectives = 0;
    private int peopleInTheWorld = 0;

    private Vector2 zone1 = new Vector2(5, 30);
    private Vector2 zone2 = new Vector2(15, 30);
    private Vector2 zone3 = new Vector2(-5, 30);

    private List<Vector2> zoneCoordsList = new ArrayList<>();

    public ObjectiveSystem(AbstractRoom room) {
        super(room);
//        HUD.instance().phone.tasks = objectives;
        zoneCoordsList.add(zone1);
        zoneCoordsList.add(zone2);
        zoneCoordsList.add(zone3);
    }

    @Override
    public void update(float delta){
        gobs.forEach(gob -> gob.forEach(PersonComponent.class, person -> peopleInTheWorld++));

        if(currentObjectives < MAXOBJECTIVES && peopleInTheWorld >= 3){
            for(; currentObjectives < MAXOBJECTIVES; currentObjectives ++){
                createObjective();
            }
        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return (gob.hasComponent(PersonComponent.class) || gob.hasComponent(ZoneComponent.class));
    }

    private void createObjective(){
        Random randomizer = new Random();
        List<MyGameObject> currentHoomans = objectives.stream().map(tup -> tup.x).collect(Collectors.toList());
        List<MyGameObject> currentTargetZones = objectives.stream().map(tup -> tup.y).collect(Collectors.toList());

        List<MyGameObject> hoomanGobs = gobs.stream().filter(gob -> gob.hasComponent(PersonComponent.class) &&
              !currentHoomans.contains(gob)).collect(Collectors.toList());
        MyGameObject targetHooman = hoomanGobs.get(randomizer.nextInt(hoomanGobs.size()));

        Vector2 targetZoneCoords = zoneCoordsList.get(randomizer.nextInt(zoneCoordsList.size()));
        MyGameObject targetZone = GameObjectFactory.createZone(targetZoneCoords.x, targetZoneCoords.y, 15, 15, 0, ZoneType.OBJECTIVE, (gameObj) -> {

        });

        room.addGob(GameObjectFactory.createZone(targetHooman, 10000, 10000, 5, 5, 0, ZoneType.OBJECTIVE, (gameObj) -> room.addGob(targetZone)));

        objectives.add(new Tuple<>(targetHooman, targetZone));
    }
}

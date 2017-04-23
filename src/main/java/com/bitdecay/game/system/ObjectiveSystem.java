package com.bitdecay.game.system;

import com.bitdecay.game.component.ObjectiveComponent;
import com.bitdecay.game.component.PersonComponent;
import com.bitdecay.game.component.PhysicsComponent;
import com.bitdecay.game.component.ZoneComponent;
import com.bitdecay.game.gameobject.GameObjectFactory;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.gameobject.MyGameObjects;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.ui.UIElements;
import com.bitdecay.game.util.Tuple;
import com.bitdecay.game.util.ZoneType;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectiveSystem extends AbstractUpdatableSystem{
    List<Tuple<MyGameObject, MyGameObject>> objectives = new ArrayList<>();

    final private int MAXOBJECTIVES = 3;
    private int currentObjectives = 0;
    private int peopleInTheWorld = 0;

    public ObjectiveSystem(AbstractRoom room, UIElements uiElements) {
        super(room);
        uiElements.hud.phone.objectives = objectives;
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

        List<MyGameObject> zoneGobs = gobs.stream().filter(gob -> gob.hasComponent(ZoneComponent.class) &&
                !gob.getComponent(ZoneComponent.class).get().strict &&
                !currentTargetZones.contains(gob)).collect(Collectors.toList());
        MyGameObject targetZone = null;//zoneGobs.get(randomizer.nextInt(zoneGobs.size()));

        room.addGob(GameObjectFactory.createZone(targetHooman, 10000, 10000, 5, 5, 0, ZoneType.OBJECTIVE, (lah) -> {}));

        objectives.add(new Tuple<>(targetHooman, targetZone));
    }
}

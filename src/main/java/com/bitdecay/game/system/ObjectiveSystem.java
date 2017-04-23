package com.bitdecay.game.system;

import com.bitdecay.game.component.ObjectiveComponent;
import com.bitdecay.game.component.ZoneComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;
import com.bitdecay.game.ui.UIElements;
import com.bitdecay.game.util.Tuple;

import java.util.*;
import java.util.stream.Collectors;

public class ObjectiveSystem extends AbstractUpdatableSystem{
    List<Tuple<MyGameObject, MyGameObject>> objectives = new ArrayList<>();

    final private int MAXOBJECTIVES = 3;
    private int currentObjectives = 0;

    public ObjectiveSystem(AbstractRoom room, UIElements uiElements) {
        super(room);
        uiElements.hud.phone.objectives = objectives;
    }

    @Override
    public void update(float delta){
//        if(currentObjectives < MAXOBJECTIVES){
//            for(; currentObjectives < MAXOBJECTIVES; currentObjectives ++){
//                createObjective();
//            }
//        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(ObjectiveComponent.class);
    }

    private void createObjective(){
        Random randomizer = new Random();

        List<MyGameObject> zoneGobs = gobs.stream().filter(gob -> gob.hasComponent(ZoneComponent.class)).collect(Collectors.toList());
        MyGameObject targetZone = zoneGobs.get(randomizer.nextInt(zoneGobs.size()));

        //TODO pick random hooman
        MyGameObject targetHooman = new MyGameObject();

        objectives.add(new Tuple<>(targetHooman, targetZone));
    }

//    public Tuple<MyGameObject, MyGameObject> selectObjective(MyGameObject targetZone){
//
//    }
}

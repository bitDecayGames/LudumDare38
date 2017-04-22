package com.bitdecay.game.system;

import com.bitdecay.game.component.ObjectiveComponent;
import com.bitdecay.game.gameobject.MyGameObject;
import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.system.abstracted.AbstractUpdatableSystem;

public class ObjectiveSystem extends AbstractUpdatableSystem{

    //TODO increase to 5
    private int maxObjectives = 1;
    private int currentObjectives = 0;
    public boolean haveDisplayedObjectives = false;

    public ObjectiveSystem(AbstractRoom room) {
        super(room);
    }

    @Override
    public void update(float delta){
        if(!haveDisplayedObjectives){
            DisplayObjectives();
            haveDisplayedObjectives = true;
        }

        if(currentObjectives < maxObjectives){
            for(;currentObjectives < maxObjectives;currentObjectives ++){
                CreateObjective();
            }
            haveDisplayedObjectives = false;
        }
    }

    @Override
    protected boolean validateGob(MyGameObject gob) {
        return gob.hasComponent(ObjectiveComponent.class);
    }

    private void CreateObjective(){

    }

    public void DisplayObjectives(){

    }
}

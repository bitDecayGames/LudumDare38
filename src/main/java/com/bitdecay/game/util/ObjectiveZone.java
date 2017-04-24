package com.bitdecay.game.util;

import com.badlogic.gdx.math.Vector2;
import com.bitdecay.game.trait.IUpdate;

public class ObjectiveZone implements IUpdate {

    public String name = "That dank ass\nBurger dick";
    public Vector2 position = null;
    public String flavorText = "I just need a sandwich...\nI'm hungry";
    private float originalTimerValue = 0;
    public float timer = -1;

    public ObjectiveZone(String name, Vector2 position, String flavorText, float timer){
        this.name = name;
        this.position = position;
        if ("".equalsIgnoreCase(flavorText)) this.flavorText = null;
        else this.flavorText = flavorText;
        this.originalTimerValue = timer;
        this.timer = timer;
    }

    public ObjectiveZone copy(){
        return new ObjectiveZone(name, position.cpy(), flavorText, originalTimerValue);
    }

    @Override
    public void update(float delta) {
        if (originalTimerValue > 0 && timer >= 0) {
            timer -= delta;
        }
    }

    public boolean isOutOfTime(){
        return originalTimerValue > 0 && timer <= 0;
    }
}

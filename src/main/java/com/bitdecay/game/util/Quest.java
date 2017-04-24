package com.bitdecay.game.util;

import com.bitdecay.game.gameobject.MyGameObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class Quest {

    public String personName = "Jason Voorhees";
    public String icon = "uiStuff/missions/pokey";
    public float reward = 0;
    public List<ObjectiveZone> targetZones = null;
    public Consumer<MyGameObject> onCompletion = null;

    public Quest(String personName, String icon, float reward, List<ObjectiveZone> targetZones, Consumer<MyGameObject> onCompletion){
        this.personName = personName;
        this.icon = icon;
        this.reward = reward;
        this.targetZones = targetZones;
        this.onCompletion = onCompletion;
    }

    public Optional<ObjectiveZone> currentZone(){
        if (targetZones != null && targetZones.size() > 0) return Optional.of(targetZones.get(0));
        return Optional.empty();
    }
}

package com.bitdecay.game.util;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.bitdecay.game.MyGame;
import com.bitdecay.game.gameobject.MyGameObject;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

public class Quest {

    public String personName = "Jason Voorhees";
    public String icon = "uiStuff/missions/pokey";
    public float reward = 0;
    public List<ObjectiveZone> targetZones = null;
    public BiConsumer<Quest, MyGameObject> onZoneTrigger = null;
    public BiConsumer<Quest, MyGameObject> onCompletion = null;
    public boolean isActive = false;

    public Quest(String personName, String icon, float reward, List<ObjectiveZone> targetZones, BiConsumer<Quest, MyGameObject> onZoneTrigger, BiConsumer<Quest, MyGameObject> onCompletion){
        this.personName = personName;
        this.icon = icon;
        this.reward = reward;
        this.targetZones = targetZones;
        this.onZoneTrigger = onZoneTrigger;
        this.onCompletion = onCompletion;
    }

    public Quest removeCurrentZone(){
        if (targetZones != null && targetZones.size() > 0) targetZones.remove(0);
        return this;
    }

    public Optional<ObjectiveZone> currentZone(){
        if (targetZones != null && targetZones.size() > 0) return Optional.of(targetZones.get(0));
        return Optional.empty();
    }

    public TextureRegion getIcon(){
        return MyGame.ATLAS.findRegion(icon);
    }

    public Quest copy(BiConsumer<Quest, MyGameObject> onZoneTrigger, BiConsumer<Quest, MyGameObject> onCompletion){
        return new Quest(personName, icon, reward, targetZones.stream().map(ObjectiveZone::copy).collect(Collectors.toList()), onZoneTrigger, onCompletion);
    }

    @Override
    public String toString() {
        return "Quest(" + personName + ")";
    }
}

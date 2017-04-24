package com.bitdecay.game.util;

import com.badlogic.gdx.math.Vector2;

public class ObjectiveZone {

    public String name = "That dank ass\nBurger dick";
    public Vector2 position = null;
    public String flavorText = "I just need a sandwich...\nI'm hungry";

    public ObjectiveZone(String name, Vector2 position, String flavorText){
        this.name = name;
        this.position = position;
        this.flavorText = flavorText;
    }

    public ObjectiveZone(String name, Vector2 position){
        this.name = name;
        this.position = position;
        this.flavorText = "";
    }
}

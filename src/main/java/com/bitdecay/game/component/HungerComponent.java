package com.bitdecay.game.component;

/**
 * Created by Monday on 4/22/2017.
 */
public class HungerComponent extends AbstractComponent {
    public float maxFullness;
    public float currentFullness;
    public float digestionRate;

    public HungerComponent(float maxFullness, float digestionRate) {
        this.maxFullness = maxFullness;
        currentFullness = maxFullness;
        this.digestionRate = digestionRate;
    }
}

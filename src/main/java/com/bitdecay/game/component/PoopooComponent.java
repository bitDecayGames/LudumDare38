package com.bitdecay.game.component;

/**
 * Created by Monday on 4/22/2017.
 */
public class PoopooComponent extends AbstractComponent {

    public float maxPoopoo;
    public float currentPoopoo;
    public float poopooCreationRate;

    public PoopooComponent(float maxPoopoo, float poopooCreationRate) {
        this.maxPoopoo = maxPoopoo;
        this.currentPoopoo = 0;
        this.poopooCreationRate = poopooCreationRate;
    }
}

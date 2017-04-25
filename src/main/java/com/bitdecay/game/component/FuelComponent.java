package com.bitdecay.game.component;

/**
 * Created by Luke on 4/22/2017.
 */
public class FuelComponent extends AbstractComponent {

    public float maxFuel;
    public float currentFuel;
    public float fuelBurnRate;
    public boolean carStallSound = false;

    public FuelComponent(float max, float burnRate){
        maxFuel = max;
        currentFuel = max;
        fuelBurnRate = burnRate;
    }

}

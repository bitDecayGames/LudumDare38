package com.bitdecay.game.component;

import com.bitdecay.game.ui.Fuel;

/**
 * Created by Luke on 4/22/2017.
 */
public class FuelComponent extends AbstractComponent {

    public int maxFuel;
    public int currentFuel;

    public FuelComponent(int max, int current){
        maxFuel = max;
        currentFuel = current;
    }

}

package com.bitdecay.game.component;

/**
 * Created by Monday on 4/22/2017.
 */
public class TireFrictionComponent extends AbstractComponent {
    public float maxForce;
    public boolean tireLocked = false;

    public TireFrictionComponent(float maxForce){
        this.maxForce = maxForce;
    }
}

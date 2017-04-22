package com.bitdecay.game.component;

/**
 * Created by Monday on 4/22/2017.
 */
public class DriveTireComponent extends AbstractComponent {
    public float speed = 0;
    public float breakSpeed = 0;
    public boolean hasPower = true;

    public DriveTireComponent(float maxSpeed, float breakSpeed){
        this.speed = maxSpeed;
        this.breakSpeed = breakSpeed;
    }
}

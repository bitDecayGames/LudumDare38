package com.bitdecay.game.component;

/**
 * Created by Monday on 4/22/2017.
 */
public class DriveTireComponent extends AbstractComponent {
    public float maxSpeed = 0;
    public float acceleration = 0;
    public boolean hasPower = true;

    public DriveTireComponent(float maxSpeed, float acceleration){
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }
}

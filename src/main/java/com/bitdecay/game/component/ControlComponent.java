package com.bitdecay.game.component;

/**
 * This component is to flag an object as able to be controlled by the player and has the movement style of a car
 */
public class ControlComponent extends AbstractComponent {
    public float speed = 0;
    public float angularSpeed = 0;
    public float breakSpeed = 0;

    public ControlComponent(float speed, float angularSpeed, float breakSpeed){
        this.speed = speed;
        this.angularSpeed = angularSpeed;
        this.breakSpeed = breakSpeed;
    }
}

package com.bitdecay.game.component;

/**
 * This component is to flag an object as able to be controlled by the player and has the movement style of a car
 */
public class SteerableComponent extends AbstractComponent {
    public float maxAngleRads;

    public SteerableComponent(float maxAngle){
        this.maxAngleRads = maxAngle;
    }
}

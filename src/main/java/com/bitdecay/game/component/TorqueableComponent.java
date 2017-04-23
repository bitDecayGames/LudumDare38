package com.bitdecay.game.component;

/**
 * This component is allows an entity to be torqued, torque it real good
 */
public class TorqueableComponent extends AbstractComponent {

    public float speed = 0;

    public TorqueableComponent(float speed){
        this.speed = speed;
    }
}

package com.bitdecay.game.component;

/**
 * Speed up an animation based on its velocity
 */
public class VelocityBasedAnimationSpeedComponent extends AbstractComponent {

    public float speedScale = 0;

    public VelocityBasedAnimationSpeedComponent(float speedScale){
        this.speedScale = speedScale;
    }

    public VelocityBasedAnimationSpeedComponent set(float speedScale) {
        this.speedScale = speedScale;
        return this;
    }
}

package com.bitdecay.game.component;

/**
 * Tracks the rotation of an object
 */
public class RotationComponent extends AbstractComponent {
    public float rotation = 0;

    public RotationComponent(float rotation){
        this.rotation = rotation;
    }

    public float toDegrees(){ return (float) Math.toDegrees(rotation); }

    public RotationComponent set(float rotation) {
        this.rotation = rotation;
        return this;
    }
}

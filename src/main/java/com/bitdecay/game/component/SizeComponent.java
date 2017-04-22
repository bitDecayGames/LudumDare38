package com.bitdecay.game.component;

/**
 * Used when drawing something to know what size to draw it at
 */
public class SizeComponent extends AbstractComponent {
    public float w = 0;
    public float h = 0;

    public SizeComponent(float width, float height){
        this.w = width;
        this.h = height;
    }

    public SizeComponent set(float width, float height) {
        w = width;
        h = height;
        return this;
    }
}

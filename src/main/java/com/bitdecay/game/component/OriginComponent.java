package com.bitdecay.game.component;

import com.badlogic.gdx.math.Vector2;

/**
 * The origin of the object, tracked in 0-1, which describes the percentage of width and height (local point)
 */
public class OriginComponent extends AbstractComponent {
    public float x = 0;
    public float y = 0;

    public OriginComponent(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Immutable
     * @return new Vector2
     */
    public Vector2 toVector2(){
        return new Vector2(x, y);
    }

    public OriginComponent set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public OriginComponent set(Vector2 v) {
        return set(v.x, v.y);
    }
}

package com.bitdecay.game.component;

import com.badlogic.gdx.graphics.Color;

/**
 * Use this component to mark an object on or off screen with a little indicator
 */
public class WaypointComponent extends AbstractComponent {
    public Color color = null;

    public WaypointComponent(Color color){
        this.color = color;
    }

    public WaypointComponent set(Color color) {
        this.color = color;
        return this;
    }
}

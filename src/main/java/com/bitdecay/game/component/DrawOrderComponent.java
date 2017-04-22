package com.bitdecay.game.component;

/**
 * Used to order the drawing of drawables, the larger the number the closer to the screen
 */
public class DrawOrderComponent extends AbstractComponent {
    public int order = 0;

    public DrawOrderComponent(int order){
        this.order = order;
    }

    public DrawOrderComponent set(int order) {
        this.order = order;
        return this;
    }
}

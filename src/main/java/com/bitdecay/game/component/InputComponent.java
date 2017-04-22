package com.bitdecay.game.component;

/**
 * This component is to add controls to a physical body in Jump
 */
public abstract class InputComponent extends AbstractComponent {

    private boolean enabled = true;

    public void enable() {
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public boolean isEnabled() {
        return enabled;
    }
}

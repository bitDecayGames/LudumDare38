package com.bitdecay.game.component;

import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IInitializable;
import com.bitdecay.game.trait.IRemovable;

/**
 * The component in charge of tracking the BitBody of the object
 */
public class PhysicsComponent extends AbstractComponent implements IInitializable, IRemovable {

    private boolean initialized = false;

    private PhysicsComponent(float width, float height, int jumpStrength, int jumpCount, float jumpVariableHeightWindow, int deceleration, int acceleration, int airAcceleration){

    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize(AbstractRoom room) {
        initialized = true;
    }

    @Override
    public void remove(AbstractRoom room) {
        initialized = false;
    }
}

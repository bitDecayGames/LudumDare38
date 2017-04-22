package com.bitdecay.game.component;

import com.bitdecay.game.room.AbstractRoom;
import com.bitdecay.game.trait.IInitializable;

/**
 * This component is to add keyboard controls to a physical body in Jump
 */
public class KeyboardInputComponent extends InputComponent implements IInitializable {

    private boolean initialized = false;

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public void initialize(AbstractRoom room) {
        initialized = true;
    }
}

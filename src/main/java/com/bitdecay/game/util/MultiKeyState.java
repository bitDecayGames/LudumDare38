package com.bitdecay.game.util;

import java.util.List;

/**
 * For use with jump controls
 */
public class MultiKeyState {

    private int[] keys;

    public MultiKeyState(int... keys) {
        this.keys = keys;
    }

    public MultiKeyState(List<Integer> keys){
        this.keys = new int[keys.size()];
        for (int i = 0; i < keys.size(); i++) this.keys[i] = keys.get(i);
    }

    public boolean isJustPressed() {
        return InputHelper.isKeyJustPressed(keys);
    }

    public boolean isPressed() {
        return InputHelper.isKeyPressed(keys);
    }
}

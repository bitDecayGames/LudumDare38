package com.bitdecay.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * Helps to allow for multi-key checks on JustPressed and Pressed.
 */
public final class InputHelper {
    private static Logger log = Logger.getLogger("InputHelper");
    private InputHelper(){}

    public static boolean isKeyJustPressed(int... keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (Gdx.input.isKeyJustPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isKeyJustPressed(Collection<Integer> keyboardKeys){
        for (Integer keyboardKey : keyboardKeys) if (Gdx.input.isKeyJustPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isKeyPressed(int... keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (Gdx.input.isKeyPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isKeyPressed(Collection<Integer> keyboardKeys){
        for (int keyboardKey : keyboardKeys) if (Gdx.input.isKeyPressed(keyboardKey)) return true;
        return false;
    }

    public static boolean isButtonPressed(int... buttons){
        if (Controllers.getControllers().size > 0) {
            Controller c = Controllers.getControllers().first();
            for (int button : buttons) {
                float trigger = c.getAxis(button - 100);
                float upOrLeft = c.getAxis(button - 200);
                float downOrRight = c.getAxis(button - 300);
                if(c.getButton(button) || trigger > 0.7f || upOrLeft > 0.7f || downOrRight < -0.7f) return true;
            }
        }
        return false;
    }
}

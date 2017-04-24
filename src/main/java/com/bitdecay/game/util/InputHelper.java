package com.bitdecay.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.bitdecay.game.util.Xbox360Pad.buttons;

/**
 * Helps to allow for multi-key checks on JustPressed and Pressed.
 */
public final class InputHelper {

    private static List<Integer> previousPressedButtons = new ArrayList<>();
    private static Logger log = LogManager.getLogger(InputHelper.class);
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

    public static boolean isButtonJustPressed(int button){
        return !previousPressedButtons.contains(button) && isButtonPressed(button);
    }

    public static void update(float delta){
        previousPressedButtons = buttons.stream().filter(InputHelper::isButtonPressed).collect(Collectors.toList());
    }
}

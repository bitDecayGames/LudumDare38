package com.bitdecay.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.bitdecay.game.gameobject.MyGameObjects;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class HUD extends Group {
    Phone phone;
    Body body;

    public HUD(Vector2 screenSize) {
        super();

        float screenEdgeOffset = screenSize.x * 0.025f;

        phone = new Phone(screenSize);
        phone.setPosition(screenEdgeOffset, 0);
        this.addActor(phone);

        body = new Body(screenSize);
        body.setPosition(screenSize.x - body.getWidth() + screenEdgeOffset, 0);
        this.addActor(body);

        setBounds(0, -screenSize.y, screenSize.x, screenSize.y);

        body.setPoopLevelMax(100);
        body.setFoodLevelMax(100);
    }

    public void toggle() {
        MoveToAction action;

        if (getY() + getHeight() > 0) {
            action = moveTo(getX(), -getHeight());
        } else {
            action = moveTo(getX(),0);
        }
        action.setDuration(0.25f);

        this.addAction(action);
    }

    float foodLevel = 100;
    float poopLevel = 0;

    public void update(MyGameObjects gobs) {
        // TODO This is where we can update the various stats on the HUD
        // For now stub some stuff in.

        foodLevel--;
        if (foodLevel < 0) {
            foodLevel = 100;
        }

        poopLevel++;
        if (poopLevel > 100) {
            poopLevel = 0;
        }

        body.setFoodLevel(foodLevel);
        body.setPoopLevel(poopLevel);
    }
}

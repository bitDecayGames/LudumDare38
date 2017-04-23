package com.bitdecay.game.ui;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class HUD extends Group {
    public Phone phone;
    public Body body;

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
    }

    public void toggle() {
        MoveToAction action;

        if (getY() + getHeight() > 0) {
            action = moveTo(getX(), -getHeight());
        } else {
            action = moveTo(getX(),0);
        }
        action.setDuration(0.25f);

        addAction(action);
    }
}

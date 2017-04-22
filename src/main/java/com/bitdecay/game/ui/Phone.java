package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class Phone extends Group {

    private ShapeRenderer renderer;

    public Phone (Vector2 screenSize) {
        super();
        renderer = new ShapeRenderer();

        float height = screenSize.y;
        float width = height / 2;

        float x = screenSize.x / 2 - width / 2;

        setBounds(x, -height, width, height);

        Image phone = new Image(MyGame.ATLAS.findRegion("uiStuff/phone"));
        phone.setBounds(0, 0, width, height);
        this.addActor(phone);

//        Image person = new Image(MyGame.ATLAS.findRegion("uiStuff/foodBio"));
//        this.addActor(person);
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
}

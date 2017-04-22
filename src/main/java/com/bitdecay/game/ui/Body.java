package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

public class Body extends Group {
    Image body;
    FillRect foodRect;
    FillRect poopRect;

    public Body(Vector2 screenSize) {
        super();

        // Build body
        body = new Image(MyGame.ATLAS.findRegion("uiStuff/foodBio"));

        // Food
        foodRect = new FillRect();
        foodRect.setColor(Color.GREEN);
        // MAGIC
        foodRect.setBounds(body.getWidth() / 5, body.getHeight() * 0.37f, body.getWidth() * 0.60f, body.getHeight() * 0.33f);

        // Poop
        poopRect = new FillRect();
        poopRect.setColor(Color.BROWN);
        // MAGIC
        poopRect.setBounds(body.getWidth() / 5, 5, body.getWidth() * 0.60f, body.getHeight() * 0.37f);

        addActor(foodRect);
        addActor(poopRect);
        addActor(body);

        float scale = screenSize.y / body.getHeight() - 1;
        scaleBy(scale);
    }

    @Override
    public float getWidth() {
        return body.getWidth();
    }
}

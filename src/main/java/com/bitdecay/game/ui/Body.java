package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bitdecay.game.MyGame;

public class Body extends Group {
    // UI
    Image body;
    FillRect foodRect;
    FillRect poopRect;
    float poopStartY;
    // Values
    float foodLevel;
    float foodLevelMax;
    float poopLevel;
    float poopLevelMax;

    public Body(Vector2 screenSize) {
        super();

        // Body
        body = new Image(MyGame.ATLAS.findRegion("uiStuff/foodBio"));

        // Poop
        poopRect = new FillRect();
        poopRect.setColor(Color.BROWN);
        // MAGIC
        poopRect.setBounds(body.getWidth() / 5, body.getHeight() * 0.09f, body.getWidth() * 0.60f, 1);

        // Food
        foodRect = new FillRect();
        foodRect.setColor(Color.GREEN);
        // MAGIC
        foodRect.setBounds(poopRect.getX(), poopRect.getY() + poopMax(), poopRect.getWidth(), foodMax());

        // Make poop start at bottom of food to make it fill from the top.
        poopStartY = foodRect.getY() - poopRect.getHeight();
        poopRect.setY(poopStartY);

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

    private float poopMax() {
        return body.getHeight() * 0.30f;
    }

    private float foodMax() {
        return body.getHeight() * 0.31f;
    }

    public void setFoodLevel(float value) {
        foodLevel = value;
    }

    public void setPoopLevel(float value) {
        poopLevel = value;
    }

    public void setFoodLevelMax(float value) {
        foodLevelMax = value;
    }

    public void setPoopLevelMax(float value) {
        poopLevelMax = value;
    }

    public void act(float delta) {
        // Poop
        float poopScale = poopLevel / poopLevelMax;
        float poopDisplayHeight = poopMax() * poopScale;

        poopRect.setY(-poopDisplayHeight + poopStartY);
        poopRect.setHeight(poopDisplayHeight);

        // Food
        float foodScale = foodLevel / foodLevelMax;
        float foodDisplayHeight = foodMax() * foodScale;

        foodRect.setHeight(foodDisplayHeight);
    }
}

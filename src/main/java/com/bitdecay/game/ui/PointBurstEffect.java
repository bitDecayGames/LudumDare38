package com.bitdecay.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bitdecay.game.MyGame;

public class PointBurstEffect extends Group {
    final float DURATION = 2;

    public PointBurstEffect(String iconName, String text) {
        Image icon = new Image(MyGame.ATLAS.findRegion(iconName));
        icon.setScale(0.5f);
        addActor(icon);

        Label textLabel = new Label(text, new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        textLabel.setFontScale(2);
        textLabel.moveBy(icon.getWidth() * 0.55f , icon.getHeight() / 2 - textLabel.getHeight() * 1.5f);
        addActor(textLabel);

        float screenHeight = Gdx.graphics.getHeight();
        setPosition(Gdx.graphics.getWidth() / 2, screenHeight / 2);

        MoveToAction moveTo = Actions.moveTo(getX(), screenHeight);
        moveTo.setDuration(DURATION);
        addAction(moveTo);

        AlphaAction alphaAction = Actions.alpha(0, DURATION);
        addAction(alphaAction);

        // TODO Play sound here?
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!hasActions()) {
            addAction(Actions.removeActor());
        }
    }
}

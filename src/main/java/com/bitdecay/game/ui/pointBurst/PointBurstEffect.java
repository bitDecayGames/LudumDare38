package com.bitdecay.game.ui.pointBurst;

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

    public PointBurstEffect(Image mainIcon, Image secondaryIcon, String text) {
        addActor(mainIcon);

        if (secondaryIcon != null) {
            secondaryIcon.setPosition(secondaryIcon.getWidth() * -0.55f, mainIcon.getY());
            addActor(secondaryIcon);
        }

        Label textLabel = new Label(text, new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        textLabel.setFontScale(2);
        textLabel.moveBy(mainIcon.getWidth() * mainIcon.getScaleX(), mainIcon.getHeight() * mainIcon.getScaleY() / 2 - textLabel.getHeight() * 0.55f);
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

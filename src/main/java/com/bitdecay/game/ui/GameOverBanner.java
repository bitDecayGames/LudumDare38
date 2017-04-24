package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.bitdecay.game.MyGame;

/**
 * Created by Monday on 4/24/2017.
 */
public class GameOverBanner extends Group {

    public GameOverBanner() {
        super();

        Label actor = new Label("GAME OVER", new Label.LabelStyle(MyGame.FONT, Color.RED));
        actor.setFontScale(10);
//        actor.setSize(Gdx.graphics.getWidth() * .8f, Gdx.graphics.getHeight() * .5f);
//        actor.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth() / 2);
        actor.setOrigin(Align.center);
        this.addActor(actor);
    }
}

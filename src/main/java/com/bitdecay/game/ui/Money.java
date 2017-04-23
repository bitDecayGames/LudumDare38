package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.bitdecay.game.MyGame;

public class Money extends Group {
    Label text;

    public Money(Vector2 screenSize) {
        Image symbol = new Image(MyGame.ATLAS.findRegion("uiStuff/btc-512"));
        symbol.setScale(0.10f);
        symbol.setPosition(screenSize.x * 0.025f, screenSize.y * 0.78f);
        addActor(symbol);

        text = new Label("", new Label.LabelStyle(MyGame.FONT, Color.BLACK));
        text.setFontScale(3.0f);
        text.setPosition(symbol.getX() * 3.0f, symbol.getY() * 1.05f);
        addActor(text);
    }

    public void setMoney(float value) {
        text.setText(Float.toString(value));
    }
}

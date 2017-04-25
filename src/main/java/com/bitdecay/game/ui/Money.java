package com.bitdecay.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.bitdecay.game.MyGame;

public class Money extends Table {

    private Label text;
    public float moneyValue;

    public Money(float fontScale) {
        add(new ImageButton(new TextureRegionDrawable(MyGame.ATLAS.findRegion("uiStuff/btc-512"))));
        text = new Label("100.0", new Label.LabelStyle(MyGame.FONT, Color.WHITE));
        text.setFontScale(fontScale);
        add(text).row();

    }

    public void setMoney(float value) {
        moneyValue = value;
        text.setText(Float.toString(value));
    }
}
